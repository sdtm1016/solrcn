/*
 * Created on 2004-8-26
 *
 */
package org.nlp.address;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 分词和标注
 */
public class AddressTagger {
	public static ContextStatAddress contexStatAddress = ContextStatAddress
			.getInstance();
	public static DicAddress dictAddress = DicAddress.getInstance(); // 初始化词典
	public static UnknowGrammar grammar = UnknowGrammar.getInstance();
	static double minValue = Double.NEGATIVE_INFINITY / 2;

	/**
	 * 计算节点i的最佳前驱节点
	 * 
	 * @param adjList
	 *            切分词图
	 * @param i
	 *            节点编号
	 */
	public static void getPrev(AdjList g, int i, AddTokenInf[] prevNode,
			double[] prob) {
		Iterator<AddTokenInf> it = g.getPrev(i);
		double maxProb = minValue;
		AddTokenInf maxID = null;

		// 向左查找所有候选词，得到前驱词集合，从中挑选最佳前趋词
		while (it.hasNext()) {
			AddTokenInf itr = it.next();
			double currentProb = prob[itr.start] + itr.logProb;
			if (currentProb > maxProb) {
				maxID = itr;
				maxProb = currentProb;
			}
		}
		prob[i] = maxProb;
		prevNode[i] = maxID;
		return;
	}

	/**
	 * 获得最有可能匹配的DocToken链表
	 * 
	 * @param g
	 * @param endToken
	 * @return maxProb maxProb中的所有元素都是最大概率匹配上的序列
	 */
	public static ArrayList<AddressToken> maxProb(AdjList g) {
		// System.out.println(g);
		AddTokenInf[] prevNode = new AddTokenInf[g.verticesNum];
		double[] prob = new double[g.verticesNum];
		for (int index = 1; index < g.verticesNum; index++) {
			getPrev(g, index, prevNode, prob);
		}

		// for (int i = 1; i < prevNode.length; i++) {
		// if(prevNode[i] == null)
		// continue;
		// System.out.println("i: "+i+"    "+prevNode[i].toString());
		// }

		ArrayList<AddTokenInf> ret = new ArrayList<AddTokenInf>(g.verticesNum);
		for (int i = (g.verticesNum - 1); i > 0; i = prevNode[i].start)// 从右向左取词候选词
		{
			ret.add(prevNode[i]);
			// System.out.println("ret "+prevNode[i]);
		}

		Collections.reverse(ret);
		mergeUnknow(ret);

		// System.out.println("ret.size() "+ret.size());
		// for (int i = 0; i < ret.size(); i++) {
		// Iterator<AddTypeInf> it = ret.get(i).data.iterator();
		// System.out.println("data:"+ret.get(i).toString());
		// System.out.println(g.toString());
		// }

		AddressType[] bestTag = hmm(ret);
		// for (int i = 0; i < bestTag.length; i++) {
		// Iterator<AddTypeInf> it = ret.get(i).data.iterator();
		// System.out.println("tag:"+bestTag[i]);
		// System.out.println(g.toString());
		// }

		ArrayList<AddressToken> list = new ArrayList<AddressToken>();
		for (int i = 0; i < ret.size(); i++) {
			AddTokenInf tokenInf = ret.get(i);
			// System.out.println("maxProb:----------" + tokenInf.data.size());
			AddressToken addressToken = new AddressToken(tokenInf.start,
					tokenInf.end, tokenInf.termText, bestTag[i]);
			list.add(addressToken);
		}
		return list;
	}

	/**
	 * 
	 * @param word
	 * @return AdjListDoc 表示一个列表图
	 */
	public static AdjList getAdjList(String addressStr) {
		if (addressStr == null || addressStr.length() == 0) {
			return null;
		}

		int atomCount;

		// 存放匹配的节点信息
		// int fee;
		int start = 0;
		atomCount = addressStr.length();

		AdjList g = new AdjList(atomCount + 1); // 初始化在Dictionary中词组成的图

		while (true) // 在这里开始进行分词
		{
			ArrayList<DicAddress.MatchRet> matchRet = dictAddress.matchAll(
					addressStr, start);
			if (matchRet.size() > 0) {// 匹配上
				// fee = 100;
				for (DicAddress.MatchRet ret : matchRet) {
					String termText = addressStr.substring(start, ret.end);
					double logProb = Math.log(ret.posInf.totalCost())
							- Math.log(dictAddress.n);
					AddTokenInf tokenInf = new AddTokenInf(start, ret.end,
							termText, ret.posInf, logProb);

					g.addEdge(tokenInf);
				}
				start++;
			} else { // 没匹配上
				// fee = -10;
				double logProb = Math.log(1) - Math.log(dictAddress.n);
				g.addEdge(new AddTokenInf(start, start + 1, addressStr
						.substring(start, start + 1), null, logProb));
				start++;
			}
			if (start >= atomCount) {
				break;
			}
		}
//		System.out.println("total:" + dictAddress.n + " " + Long.MAX_VALUE);

		return g;
	}

	/**
	 * 消除歧义方法
	 * 
	 * @param ret
	 *            要消除歧义的集合
	 * @return 估计的标注类型序列
	 */
	public static AddressType[] hmm(ArrayList<AddTokenInf> ret) {
		AddTypes startType = new AddTypes();
		startType.put(new AddTypes.AddressTypeInf(AddressType.Start, 1, 0));
		ret.add(0, new AddTokenInf(-1, 0, "Start", startType, 0));

		AddTypes endType = new AddTypes();
		endType.put(new AddTypes.AddressTypeInf(AddressType.End, 100, 100));
		ret.add(new AddTokenInf(-1, 0, "End", endType, 0));

		// for (int i = 0; i < ret.size(); i++) {
		// Iterator<AddTypeInf> it = ret.get(i).data.iterator();
		// System.out.println("data:"+ret.get(i).toString());
		// System.out.println(g.toString());
		// }

		int stageLength = ret.size();// 开始阶段和结束阶段
		int[][] prob = new int[stageLength][];// 累积概率
		for (int i = 0; i < stageLength; ++i) {
			prob[i] = new int[AddressType.values().length];
			for (int j = 0; j < AddressType.values().length; ++j)
				prob[i][j] = Integer.MIN_VALUE;
		}

		AddressType[][] bestPre = new AddressType[stageLength][];// 最佳前驱，也就是前一个标注是什么
		for (int i = 0; i < ret.size(); ++i) {
			bestPre[i] = new AddressType[AddressType.values().length];
		}

		prob[0][AddressType.Start.ordinal()] = 1;

		for (int stage = 1; stage < stageLength; stage++) {
			AddTokenInf nexInf = ret.get(stage);
			if (nexInf.data == null) {
				continue;
			}
			Iterator<AddTypes.AddressTypeInf> nextIt = nexInf.data.iterator();
			while (nextIt.hasNext()) {
				AddTypes.AddressTypeInf nextTypeInf = nextIt.next();

				AddTokenInf preInf = ret.get(stage - 1);
				if (preInf.data == null) {
					continue;
				}

				Iterator<AddTypes.AddressTypeInf> preIt = preInf.data
						.iterator();

				while (preIt.hasNext()) {
					AddTypes.AddressTypeInf preTypeInf = preIt.next();
					// 上一个结点到下一个结点的转移概率
					int trans = contexStatAddress.getContextPossibility(
							preTypeInf.pos, nextTypeInf.pos);
					int currentprob = prob[stage - 1][preTypeInf.pos.ordinal()];
					currentprob = currentprob + trans + nextTypeInf.weight;
					if (prob[stage][nextTypeInf.pos.ordinal()] <= currentprob) {
						// System.out.println("find new max:"+prob+" old:"+
						// nextTypeInf.prob);
						prob[stage][nextTypeInf.pos.ordinal()] = currentprob;
						bestPre[stage][nextTypeInf.pos.ordinal()] = preTypeInf.pos;
					}
				}
			}
		}

		AddressType endTag = AddressType.End;

		// System.out.println("stageLength "+stageLength);
		AddressType[] bestTag = new AddressType[stageLength];
		for (int i = (stageLength - 1); i > 1; i--) {
			// System.out.println("i-1 "+(i-1));
			bestTag[i - 1] = bestPre[i][endTag.ordinal()];
			endTag = bestTag[i - 1];
		}
		AddressType[] resultTag = new AddressType[stageLength - 2];
		System.arraycopy(bestTag, 1, resultTag, 0, resultTag.length);

		ret.remove(stageLength - 1);
		ret.remove(0);
		return resultTag;
	}

	public static void mergeUnknow(ArrayList<AddTokenInf> tokens) {
		// 合并未知词
		for (int i = 0; i < tokens.size(); ++i) {
			AddTokenInf token = tokens.get(i);
			if (token.data != null) {
				continue;
			}
			// System.out.println("mergeUnknow:"+token);
			StringBuilder unknowText = new StringBuilder();
			int start = token.start;
			while (true) {
				unknowText.append(token.termText);
				tokens.remove(i);
				if (i >= tokens.size()) {
					int end = token.end; // token.end

					AddTypes item = new AddTypes();
					item.put(new AddTypes.AddressTypeInf(AddressType.Unknow,
							10, 0));
					AddTokenInf unKnowTokenInf = new AddTokenInf(start, end,
							unknowText.toString(), item, 0);
					tokens.add(i, unKnowTokenInf);
					break;
				}
				token = tokens.get(i);
				if (token.data != null) {
					int end = token.start;

					AddTypes item = new AddTypes();
					item.put(new AddTypes.AddressTypeInf(AddressType.Unknow,
							10, 0));
					AddTokenInf unKnowTokenInf = new AddTokenInf(start, end,
							unknowText.toString(), item, 0);
					tokens.add(i, unKnowTokenInf);
					break;
				}
			}
		}
	}

	/**
	 * 分词函数，将给定的一个名称分成若干个有意义的部分
	 * 
	 * @param word
	 * @return 一个ArrayList，其中每个元素是一个词
	 */
	public static ArrayList<AddressToken> basicTag(String addressStr) // 分词
	{
		AdjList g = getAdjList(addressStr);
		ArrayList<AddressToken> tokens = maxProb(g);
		return tokens;
	}

	public static List<String> getAddressTag(String addressStr) {
		AdjList g = getAdjList(addressStr);
		List<String> addressSet = new ArrayList<String>();
		ArrayList<AddressToken> tokens = maxProb(g);

		for (AddressToken add : tokens) {
			addressSet.add(add.termText);
		}

		return addressSet;
	}

	public static ArrayList<AddressToken> tag(String addressStr) // 分词
	{
		AdjList g = getAdjList(addressStr);
//		System.out.println(g);
		ArrayList<AddressToken> tokens = maxProb(g);
		// 增加开始和结束节点
		AddressToken startToken = new AddressToken(-1, 0, "Start",
				AddressType.Start);
		tokens.add(0, startToken);
		AddressToken endToken = new AddressToken(g.verticesNum - 1,
				g.verticesNum, "End", AddressType.End);
		tokens.add(endToken);

//		 for(AddressToken tk:tokens){
//		 System.out.println(tk.toString());
//		 }

		// 未登录词识别
		UnknowGrammar.MatchRet matchRet = new UnknowGrammar.MatchRet(0, null);
		int offset = 0;
		while (true) {
			matchRet = grammar.matchLong(tokens, offset, matchRet);

			// System.out.println(matchRet.lhs);
			// System.out.println(matchRet.end);
			if (matchRet.lhs != null) {
				UnknowGrammar.replace(tokens, offset, matchRet.lhs);
				// System.out.println("replace:"+matchRet.lhs);
				offset = 0;
			} else {
				++offset;
				if (offset >= tokens.size())
					break;
			}
		}
		
		tokens.remove(0);
		tokens.remove(endToken);
		return tokens;
	}
}