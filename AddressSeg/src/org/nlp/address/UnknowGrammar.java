package org.nlp.address;

import java.util.ArrayList;

/**
 * 未登录地名识别规则
 * 
 * @author luogang
 * @2010-3-23
 */
public class UnknowGrammar {

	/**
	 * An inner class of Ternary Search Trie that represents a node in the trie.
	 */
	public final class TSTNode {

		/** The key to the node. */
		public ArrayList<AddressSpan> data = null;

		/** The relative nodes. */
		protected TSTNode loKID;
		protected TSTNode eqKID;
		protected TSTNode hiKID;

		/** The char used in the split. */
		protected AddressType splitchar;

		/**
		 * Constructor method.
		 * 
		 *@param splitchar
		 *            The char used in the split.
		 */
		protected TSTNode(AddressType splitchar) {
			this.splitchar = splitchar;
		}

		public String toString() {
			return "splitchar:" + splitchar;
		}
	}

	/** The base node in the trie. */
	public TSTNode root;

	public void addProduct(ArrayList<AddressType> key,
			ArrayList<AddressSpan> lhs) {
		if (root == null) {
			root = new TSTNode(key.get(0));
		}
		TSTNode node = null;
		if (key.size() > 0 && root != null) {
			TSTNode currentNode = root;
			int charIndex = 0;
			while (true) {
				if (currentNode == null)
					break;
				int charComp = key.get(charIndex).compareTo(
						currentNode.splitchar);
				if (charComp == 0) {
					charIndex++;
					if (charIndex == key.size()) {
						node = currentNode;
						break;
					}
					currentNode = currentNode.eqKID;
				} else if (charComp < 0) {
					currentNode = currentNode.loKID;
				} else {
					currentNode = currentNode.hiKID;
				}
			}
			ArrayList<AddressSpan> occur2 = null;
			if (node != null) {
				occur2 = node.data;
			}
			if (occur2 != null) {
				// occur2.insert(pi);
				return;
			}
			currentNode = getOrCreateNode(key);
			currentNode.data = lhs;
		}
	}

	public MatchRet matchLong(ArrayList<AddressToken> key, int offset,
			MatchRet matchRet) {

		if (key == null || root == null || "".equals(key)
				|| offset >= key.size()) {
			matchRet.end = offset;
			matchRet.lhs = null;
			return matchRet;
		}
		int ret = offset;
		ArrayList<AddressSpan> retPOS = null;

		// System.out.println("enter");
		TSTNode currentNode = root;
		int charIndex = offset;
		while (true) {
			if (currentNode == null) {
				// System.out.println("ret "+ret);
				matchRet.end = ret;
				matchRet.lhs = retPOS;
				return matchRet;
			}
			int charComp = key.get(charIndex).type
					.compareTo(currentNode.splitchar);

			if (charComp == 0) {
				// System.out.println("comp:"+key.get(charIndex).type);
				charIndex++;

				if (currentNode.data != null && charIndex > ret) {
					ret = charIndex;
					retPOS = currentNode.data;
					// System.out.println("ret pos:"+retPOS);
				}
				if (charIndex == key.size()) {
					matchRet.end = ret;
					matchRet.lhs = retPOS;
					return matchRet;
				}
				currentNode = currentNode.eqKID;
			} else if (charComp < 0) {
				currentNode = currentNode.loKID;
			} else {
				currentNode = currentNode.hiKID;
			}
		}
	}

	public static void replace(ArrayList<AddressToken> key, int offset,
			ArrayList<AddressSpan> spans) {
		int j = 0;
		for (int i = offset; i < key.size(); ++i) {
			AddressSpan span = spans.get(j);
			AddressToken token = key.get(i);
			StringBuilder newText = new StringBuilder();
			int newStart = token.start;
			int newEnd = token.end;
			AddressType newType = span.type;

			for (int k = 0; k < span.length; ++k) {
				token = key.get(i + k);
				newText.append(token.termText);
				newEnd = token.end;
			}
			AddressToken newToken = new AddressToken(newStart, newEnd, newText
					.toString(), newType);

			for (int k = 0; k < span.length; ++k) {
				key.remove(i);
			}
			key.add(i, newToken);
			j++;
			if (j >= spans.size()) {
				return;
			}
		}
	}

	/**
	 * Returns the node indexed by key, creating that node if it doesn't exist,
	 * and creating any required intermediate nodes if they don't exist.
	 * 
	 *@param key
	 *            A <code>String</code> that indexes the node that is returned.
	 *@return The node object indexed by key. This object is an instance of an
	 *         inner class named <code>TernarySearchTrie.TSTNode</code>.
	 *@exception NullPointerException
	 *                If the key is <code>null</code>.
	 *@exception IllegalArgumentException
	 *                If the key is an empty <code>String</code>.
	 */
	protected TSTNode getOrCreateNode(ArrayList<AddressType> key)
			throws NullPointerException, IllegalArgumentException {
		if (key == null) {
			throw new NullPointerException(
					"attempt to get or create node with null key");
		}
		if ("".equals(key)) {
			throw new IllegalArgumentException(
					"attempt to get or create node with key of zero length");
		}
		if (root == null) {
			root = new TSTNode(key.get(0));
		}
		TSTNode currentNode = root;
		int charIndex = 0;
		while (true) {
			int charComp = key.get(charIndex).compareTo(currentNode.splitchar);
			if (charComp == 0) {
				charIndex++;
				if (charIndex == key.size()) {
					return currentNode;
				}
				if (currentNode.eqKID == null) {
					currentNode.eqKID = new TSTNode(key.get(charIndex));
				}
				currentNode = currentNode.eqKID;
			} else if (charComp < 0) {
				if (currentNode.loKID == null) {
					currentNode.loKID = new TSTNode(key.get(charIndex));
				}
				currentNode = currentNode.loKID;
			} else {
				if (currentNode.hiKID == null) {
					currentNode.hiKID = new TSTNode(key.get(charIndex));
				}
				currentNode = currentNode.hiKID;
			}
		}
	}

	public static final class Prefix {
		private byte value;

		public Prefix(byte a) {
			value = a;
		}

		/** Match the word exactly */
		public static final Prefix Match = new Prefix((byte) 0);
		/** MisMatch the word */
		public static final Prefix MisMatch = new Prefix((byte) 1);
		/** Match the prefix */
		public static final Prefix MatchPrefix = new Prefix((byte) 2);

		public String toString() {
			if (value == Match.value)
				return "Match";
			else if (value == MisMatch.value)
				return "MisMatch";
			else if (value == MatchPrefix.value)
				return "MatchPrefix";
			return "Invalid";
		}
	}

	public static class MatchRet {
		public int end;
		public ArrayList<AddressSpan> lhs;

		public MatchRet(int e, ArrayList<AddressSpan> d) {
			end = e;
			lhs = d;
		}

		public String toString() {
			return end + ":" + lhs;
		}
	}

	private UnknowGrammar() {
		// 1
		ArrayList<AddressSpan> lhs = new ArrayList<AddressSpan>();
		ArrayList<AddressType> rhs = new ArrayList<AddressType>(); // right-hand

		// ==============================================以下是省的部分
		// 北京市
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Municipality);
		rhs.add(AddressType.SuffixCity);
		lhs.add(new AddressSpan(2, AddressType.Municipality));

		addProduct(rhs, lhs);

		// 北京市朝阳区高碑店乡高碑店
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Municipality);
		rhs.add(AddressType.County);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixTown);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.End);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Municipality));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(1, AddressType.End));

		addProduct(rhs, lhs);
		
		// 中国江苏南京市江苏省
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Country);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.SuffixProvince);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Country));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(2, AddressType.Province));

		addProduct(rhs, lhs);
		// 中国江苏江阴市江苏省
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Country);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.SuffixProvince);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Country));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(2, AddressType.Province));

		addProduct(rhs, lhs);
		// 中国江苏苏州市吴中区江苏省
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Country);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.SuffixProvince);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Country));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(2, AddressType.Province));

		addProduct(rhs, lhs);
		// 中国江苏苏州市吴中区江苏省
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixProvince);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(2, AddressType.Province));

		addProduct(rhs, lhs);
		// 河南郑州市河南省
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.SuffixIndicationFacility);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixProvince);
		lhs.add(new AddressSpan(3, AddressType.Province));

		addProduct(rhs, lhs);

		// 河南郑州市河南省
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Province);
		rhs.add(AddressType.SuffixProvince);
		lhs.add(new AddressSpan(2, AddressType.Province));

		addProduct(rhs, lhs);

		// =============================以下是市部分
		
		// 中国江苏南京市
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		
		rhs.add(AddressType.City);
		rhs.add(AddressType.SuffixCity);
		lhs.add(new AddressSpan(2, AddressType.City));
		

		addProduct(rhs, lhs);
		// 中国江苏南京市
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Country);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.SuffixCity);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Country));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(2, AddressType.City));
		

		addProduct(rhs, lhs);

		// 中国江苏南京市南京市
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Country);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.City);
		rhs.add(AddressType.SuffixCity);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Country));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(2, AddressType.City));

		addProduct(rhs, lhs);
		// 中国江苏南京市栖霞区南京市
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Country);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.City);
		rhs.add(AddressType.SuffixCity);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Country));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(2, AddressType.City));

		addProduct(rhs, lhs);
		// 广东省东莞市市
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.SuffixCity);
		rhs.add(AddressType.SuffixCity);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(2, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.Other));

		addProduct(rhs, lhs);

		// 广东省东莞市市
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Other);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.Other));
		lhs.add(new AddressSpan(3, AddressType.Street));

		addProduct(rhs, lhs);

		// 河南省郑州市
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.SuffixCity);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(2, AddressType.City));

		addProduct(rhs, lhs);

		// =================================以下是县区部分
		// 北京市朝阳区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(2, AddressType.County));

		addProduct(rhs, lhs);

		// 北京市大兴区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(2, AddressType.County));

		addProduct(rhs, lhs);
		//		
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Country);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Country));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);
		// 中国江苏常州市江苏省常州市武进区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Country);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.SuffixProvince);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Country));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(2, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);
		// 中国江苏如东县
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Country);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCounty);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Country));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);
		// 中国 江苏 无锡市惠山区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Country);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Country));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);
		// 广东省广州市白云区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);
		// 东城区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);
		// 近郊密云县
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Other);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Other));
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);

		// 中国江苏海安县海安县
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Country);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Country));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);
		// 中国江苏常州市新北区江苏常州市新北区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Country);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Country));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);
		// 中国江苏无锡市惠山区无锡市惠山区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Country);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Country));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);

		// 中原区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(3, AddressType.County));
		addProduct(rhs, lhs);

		// 万江区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.No);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(3, AddressType.County));
		addProduct(rhs, lhs);

		// 道里区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);
		// 河南新郑机场台商投资区建设路南侧
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Province);
		rhs.add(AddressType.County);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixCounty);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		lhs.add(new AddressSpan(2, AddressType.District));
		lhs.add(new AddressSpan(1, AddressType.Street));
		addProduct(rhs, lhs);

		// 彭水县
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);

		// 市区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.SuffixCity);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);
		// 中国 江苏 南京市 雨花台区铁心桥星河工业园8号
		// 2010.5.26
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);
		// 江宁区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);

		// ========================以下是镇乡
		// 石排镇
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.City);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixBuildingNo);
		rhs.add(AddressType.SuffixTown);
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(3, AddressType.Town));
		addProduct(rhs, lhs);

		// 昌平镇
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixTown);
		lhs.add(new AddressSpan(2, AddressType.Town));
		addProduct(rhs, lhs);

		// =================================以下是街道号等

		// 惠山经济开发区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixDistrict);
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.District));
		addProduct(rhs, lhs);
		// 洛社配套区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixDistrict);
		lhs.add(new AddressSpan(2, AddressType.District));
		addProduct(rhs, lhs);

		// 玄武大道
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixStreet);
		lhs.add(new AddressSpan(2, AddressType.Street));
		addProduct(rhs, lhs);
		//		
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixStreet);
		lhs.add(new AddressSpan(2, AddressType.Street));
		addProduct(rhs, lhs);

		// 2号楼
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.No);
		rhs.add(AddressType.SuffixBuildingUnit);
		lhs.add(new AddressSpan(2, AddressType.BuildingUnit));
		addProduct(rhs, lhs);
		// 东路
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixStreet);
		lhs.add(new AddressSpan(2, AddressType.SuffixStreet));
		addProduct(rhs, lhs);
		// 六路
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.No);
		rhs.add(AddressType.SuffixStreet);
		lhs.add(new AddressSpan(2, AddressType.SuffixStreet));
		addProduct(rhs, lhs);
		// 学院路
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.SuffixStreet);
		lhs.add(new AddressSpan(2, AddressType.Street));
		addProduct(rhs, lhs);

		// 精神病医院
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixLandMark);
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		addProduct(rhs, lhs);
		// 台城大厦
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.SuffixLandMark);
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		addProduct(rhs, lhs);
		// // 文峰大厦
		// lhs = new ArrayList<AddressSpan>();
		// rhs = new ArrayList<AddressType>(); // right-hand .
		//		
		// rhs.add(AddressType.County);
		// rhs.add(AddressType.SuffixLandMark);
		// lhs.add(new AddressSpan(2, AddressType.LandMark));
		// addProduct(rhs, lhs);
		// 五公里处
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.No);
		rhs.add(AddressType.SuffixIndicationPosition);
		lhs.add(new AddressSpan(2, AddressType.IndicationPosition));
		addProduct(rhs, lhs);
		// 四幢
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.No);
		rhs.add(AddressType.SuffixBuildingNo);
		lhs.add(new AddressSpan(2, AddressType.BuildingNo));
		addProduct(rhs, lhs);

		// distract
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.DetailDesc);
		rhs.add(AddressType.SuffixDistrict);
		lhs.add(new AddressSpan(3, AddressType.District));
		addProduct(rhs, lhs);

		// 玉村镇
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Village);
		rhs.add(AddressType.SuffixTown);
		lhs.add(new AddressSpan(2, AddressType.Town));
		addProduct(rhs, lhs);

		// 广东省东莞市长安镇107国道长安酒店斜对面
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixLandMark);
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		addProduct(rhs, lhs);

		// 江苏省南京市新街口洪武北路青石街24号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(3, AddressType.Street));
		addProduct(rhs, lhs);

		// 东莞市厚街镇新厚沙路新塘村路口直入出100米
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.SuffixTown);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.Street));
		addProduct(rhs, lhs);

		// 重庆市渝北区两路镇龙兴街84号号码一支路五星小区对面
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.SuffixTown);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.Street));
		addProduct(rhs, lhs);
		// 江苏省南京市高淳县开发区商贸区998号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixDistrict);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixCounty);
		rhs.add(AddressType.No);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.SuffixDistrict));
		lhs.add(new AddressSpan(2, AddressType.District));
		lhs.add(new AddressSpan(2, AddressType.No));
		addProduct(rhs, lhs);
		// 广东省东莞市厚街镇家具大道国际家具大道
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.SuffixTown);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.Street));
		addProduct(rhs, lhs);

		// 江苏省南京市江宁区淳化镇淳化居委会
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.End);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Town));
		lhs.add(new AddressSpan(3, AddressType.DetailDesc));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 海淀区西三环新兴桥西北角(新兴宾馆门口)
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.StartSuffix);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixLandMark);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		lhs.add(new AddressSpan(2, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.StartSuffix));
		lhs.add(new AddressSpan(2, AddressType.DetailDesc));
		addProduct(rhs, lhs);

		// 朝阳区建国门外永安里新华保险大厦南侧(119中学西侧)
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.District);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.County);
		rhs.add(AddressType.LandMark);
		lhs.add(new AddressSpan(1, AddressType.District));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		addProduct(rhs, lhs);

		// 沙田西太隆工业区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.District);
		lhs.add(new AddressSpan(3, AddressType.District));
		addProduct(rhs, lhs);

		// 东城区
		// lhs = new ArrayList<AddressSpan>();
		// rhs = new ArrayList<AddressType>(); // right-hand .
		//
		// rhs.add(AddressType.RelatedPos);
		// rhs.add(AddressType.District);
		// lhs.add(new AddressSpan(2, AddressType.County));
		// addProduct(rhs, lhs);

		// 东城区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.SuffixStreet);
		lhs.add(new AddressSpan(2, AddressType.Street));
		addProduct(rhs, lhs);

		// 大岭山工业区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.District);
		rhs.add(AddressType.SuffixDistrict);
		lhs.add(new AddressSpan(2, AddressType.District));
		addProduct(rhs, lhs);

		// 锦厦新村
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixVillage);
		lhs.add(new AddressSpan(2, AddressType.Village));
		addProduct(rhs, lhs);

		// 第二工业区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.No);
		rhs.add(AddressType.SuffixDistrict);
		lhs.add(new AddressSpan(2, AddressType.District));
		addProduct(rhs, lhs);

		// 花园新村
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.SuffixVillage);
		lhs.add(new AddressSpan(2, AddressType.Village));
		addProduct(rhs, lhs);

		// 北京市朝阳区霞光里66号远洋新干线A座908室
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.No);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.Symbol);
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(3, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.Symbol));
		addProduct(rhs, lhs);
		// 雨花台区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.County);
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);

		// 新寓二村
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.Village);
		lhs.add(new AddressSpan(2, AddressType.Village));
		addProduct(rhs, lhs);

		// 港口路
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.SuffixDistrict);
		rhs.add(AddressType.SuffixStreet);
		lhs.add(new AddressSpan(2, AddressType.Street));
		addProduct(rhs, lhs);

		// //新风中路
		// lhs = new ArrayList<AddressSpan>();
		// rhs = new ArrayList<AddressType>(); // right-hand .
		//
		// rhs.add(AddressType.Unknow);
		// rhs.add(AddressType.RelatedPos);
		// lhs.add(new AddressSpan(2, AddressType.Street));
		// addProduct(rhs, lhs);

		// 学前路
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Street);
		rhs.add(AddressType.SuffixStreet);
		lhs.add(new AddressSpan(2, AddressType.Street));
		addProduct(rhs, lhs);

		//
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(2, AddressType.Street));
		addProduct(rhs, lhs);

		// 哈尔滨市哈平路集中区黄海路39号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.City);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.SuffixCounty);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(2, AddressType.District));
		lhs.add(new AddressSpan(1, AddressType.Street));
		addProduct(rhs, lhs);

		// 广东省东莞市市区红山西路红街二巷9号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(3, AddressType.Street));
		addProduct(rhs, lhs);

		// 东莞市横沥镇中山路576号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.City);
		rhs.add(AddressType.SuffixStreet);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Town));
		lhs.add(new AddressSpan(2, AddressType.Street));
		addProduct(rhs, lhs);

		// 东城区北锣鼓巷沙络胡同7号院(近安定门地铁A口)
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.County);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.Street));
		addProduct(rhs, lhs);

		// 东城区北三环和平里东街小街桥北(美廉美东北角)
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.RelatedPos);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		addProduct(rhs, lhs);

		// 广东省广州市白云区广园中路景泰直街东2巷2号认真英语大厦903
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.Street));
		addProduct(rhs, lhs);

		// 广东省广州市从化市太平镇太平经济技术开发区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.County);
		rhs.add(AddressType.District);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Town));
		lhs.add(new AddressSpan(2, AddressType.District));
		addProduct(rhs, lhs);

		// 广东省广州市番禺区大石街冼村城岗大街3巷10号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Village);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.Village));
		lhs.add(new AddressSpan(3, AddressType.Street));
		addProduct(rhs, lhs);

		// 海淀区大钟寺四道口路1号(近学院南路)
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.County);
		rhs.add(AddressType.District);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.StartSuffix);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.District));
		lhs.add(new AddressSpan(2, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.StartSuffix));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.SuffixLandMark));
		lhs.add(new AddressSpan(1, AddressType.IndicationPosition));
		addProduct(rhs, lhs);
		// 朝阳区来广营西路88号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.City);
		rhs.add(AddressType.SuffixCounty);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(2, AddressType.County));
		addProduct(rhs, lhs);

		// 道镇闸口村东莞电化集团进宝工业区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Start);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.SuffixTown);
		rhs.add(AddressType.Village);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(2, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.Village));
		addProduct(rhs, lhs);
		// 道镇闸口村东莞电化集团进宝工业区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.Town);
		rhs.add(AddressType.Village);
		rhs.add(AddressType.City);
		rhs.add(AddressType.District);
		lhs.add(new AddressSpan(1, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.Village));
		lhs.add(new AddressSpan(2, AddressType.District));
		addProduct(rhs, lhs);
		// 江苏省南京市高淳县淳溪镇镇兴路288号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.County);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.SuffixTown);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Town));
		lhs.add(new AddressSpan(2, AddressType.Street));
		addProduct(rhs, lhs);

		// 重庆市巫溪县城厢镇镇泉街
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .

		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.Town);
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Town));
		addProduct(rhs, lhs);
		// 北京市密云县檀营乡二村
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.SuffixCounty);
		rhs.add(AddressType.Town);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(2, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Town));
		addProduct(rhs, lhs);
		// 重庆市永川市双竹镇石梯坎村
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCity);
		rhs.add(AddressType.Town);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(2, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Town));
		addProduct(rhs, lhs);

		// 重庆市合川区市合阳镇文明街97号
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixTown);
		lhs.add(new AddressSpan(2, AddressType.Town));
		addProduct(rhs, lhs);

		// 江苏省南京市溧水县大东门街29号3楼
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixBuildingUnit);
		rhs.add(AddressType.SuffixStreet);
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(3, AddressType.Street));
		addProduct(rhs, lhs);
		// 河南省郑州市惠济区桥南新区金桥路2号
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixDistrict);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(3, AddressType.District));
		lhs.add(new AddressSpan(1, AddressType.Street));
		addProduct(rhs, lhs);

		// 渝北区龙湖花园美食街
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixLandMark);
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		addProduct(rhs, lhs);
		// 北京市房山区韩村河镇韩村河村
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.Village);
		rhs.add(AddressType.SuffixIndicationFacility);
		rhs.add(AddressType.SuffixTown);
		rhs.add(AddressType.Village);
		rhs.add(AddressType.Village);
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(3, AddressType.Town));
		lhs.add(new AddressSpan(2, AddressType.Village));
		addProduct(rhs, lhs);
		// 北京市房山区韩村河镇尤家坟村
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.Village);
		rhs.add(AddressType.SuffixIndicationFacility);
		rhs.add(AddressType.SuffixTown);
		rhs.add(AddressType.Village);
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(3, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.Village));
		addProduct(rhs, lhs);
		// 北京市海淀区罗庄南里3号楼
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.County);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.BuildingUnit);
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(3, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.BuildingUnit));
		addProduct(rhs, lhs);
		// 道镇闸口村东莞电化集团进宝工业区
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Town);
		rhs.add(AddressType.Village);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.District);
		lhs.add(new AddressSpan(1, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.Village));
		lhs.add(new AddressSpan(2, AddressType.District));
		addProduct(rhs, lhs);
		// 鼓楼区草场门大街阳光广场龙江体育馆内地图
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.County);
		rhs.add(AddressType.LandMark);
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		addProduct(rhs, lhs);

		// 广东省东莞市市区红山西路红街二巷9号
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.City);
		rhs.add(AddressType.District);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.District));
		lhs.add(new AddressSpan(3, AddressType.Street));
		addProduct(rhs, lhs);
		// 广东省广州市白云区机场路新市西街17号
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(2, AddressType.Street));
		addProduct(rhs, lhs);
		// 广东省广州市海珠区工业大道南金城一街29号
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(2, AddressType.Street));
		addProduct(rhs, lhs);
		// 广东省广州市海珠区泰宁村南晒场2号13B
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixVillage);
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Village));
		addProduct(rhs, lhs);
		// 广东省广州市天河区龙口中路3号帝景苑C栋14E房
		// 2010.5.24
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Street));
		addProduct(rhs, lhs);
		// 海淀区学院路明光北里8号
		// 2010.5.25
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.County);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixLandMark);
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(3, AddressType.LandMark));
		addProduct(rhs, lhs);

		// 重庆市渝中区嘉陵江滨江路
		// 2010.5.25
		// lhs = new ArrayList<AddressSpan>();
		// rhs = new ArrayList<AddressType>(); // right-hand .
		// rhs.add(AddressType.Province);
		// rhs.add(AddressType.County);
		// rhs.add(AddressType.County);
		// rhs.add(AddressType.Street);
		// lhs.add(new AddressSpan(1, AddressType.Province));
		// lhs.add(new AddressSpan(1, AddressType.County));
		// lhs.add(new AddressSpan(2, AddressType.Street));
		// addProduct(rhs, lhs);
		// 重庆市沙坪坝区马家岩临江装饰城14-5号
		// 2010.5.25
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.District);
		rhs.add(AddressType.County);
		rhs.add(AddressType.LandMark);
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.District));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		addProduct(rhs, lhs);
		// 中国 江苏 无锡市滨湖区 无锡前桥洋溪大桥南（振兴仓储有限公司）
		// 2010.5.25
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.StartSuffix);
		rhs.add(AddressType.County);
		rhs.add(AddressType.LandMark);
		lhs.add(new AddressSpan(1, AddressType.StartSuffix));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		addProduct(rhs, lhs);
		// 中国 江苏 无锡市北塘区 新兴工业区
		// 2010.5.25
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixDistrict);
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.District));
		addProduct(rhs, lhs);
		// 中国 江苏 苏州市吴中区 吴江市盛泽和服商区D幢16号
		// 2010.5.25
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.County);
		rhs.add(AddressType.LandMark);
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		addProduct(rhs, lhs);
		// 东莞市东城大道方中大厦2楼
		// 2010.5.25
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.City);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.LandMark);
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		addProduct(rhs, lhs);
		// 江苏省南京市玄武区南拘中山东路301号
		// 2010.5.25
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.District);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.District));
		lhs.add(new AddressSpan(2, AddressType.Street));
		addProduct(rhs, lhs);
		// 河南郑州市河南省郑州市南关街民乐东里38号
		// 2010.5.25
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Street);
		rhs.add(AddressType.County);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixLandMark);
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(3, AddressType.LandMark));
		addProduct(rhs, lhs);
		// 广东省东莞市大岭山镇连平下高田村
		// 2010.5.26
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.County);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.Village);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.End);
		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Town));
		lhs.add(new AddressSpan(3, AddressType.Village));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 东莞市东城区花园新村市场路20号
		// 2010.5.26
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.City);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.SuffixCounty);
		rhs.add(AddressType.Village);
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Village));
		addProduct(rhs, lhs);
		// 北京市丰台区右安门外玉林里26号楼1单元301室
		// 2010.5.26
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.District);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.BuildingUnit);
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.District));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.BuildingUnit));
		addProduct(rhs, lhs);

		// 北京市密云县工业开发区
		// 2010.5.26
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Province);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.SuffixCounty);
		rhs.add(AddressType.SuffixDistrict);
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(2, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.SuffixDistrict));
		addProduct(rhs, lhs);
		// 北京市密云县密云镇白檀村
		// 2010.5.26
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.SuffixTown);
		rhs.add(AddressType.Village);
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.Village));
		addProduct(rhs, lhs);
		// 朝阳区博大中路荣华桥东(近亦庄)
		// 2010.5.26
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.StartSuffix);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.Town);
		lhs.add(new AddressSpan(1, AddressType.StartSuffix));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.DetailDesc));
		addProduct(rhs, lhs);
		// 海淀区学院南路68号吉安大厦C座汇智楼111室
		// 2010.5.26
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.SuffixLandMark);
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		addProduct(rhs, lhs);
		// 中国 江苏 江阴市 永康五金城大街49-51号
		// 2010.5.26
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Province);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCity);
		rhs.add(AddressType.Street);
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(2, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		addProduct(rhs, lhs);
		// 巩义市站街镇粮管所内
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.SuffixTown);
		rhs.add(AddressType.LandMark);
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		addProduct(rhs, lhs);
		// 河南省郑州市管城区南五里堡村西堡103号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.No);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.Village);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(3, AddressType.Village));
		addProduct(rhs, lhs);
		// 鼓楼东街
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.District);
		rhs.add(AddressType.SuffixStreet);

		lhs.add(new AddressSpan(1, AddressType.Street));
		addProduct(rhs, lhs);
		// 从化市
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCity);
		rhs.add(AddressType.Street);

		lhs.add(new AddressSpan(2, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		addProduct(rhs, lhs);
		// 北京西站
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixIndicationFacility);

		lhs.add(new AddressSpan(2, AddressType.SuffixIndicationFacility));
		addProduct(rhs, lhs);
		
		// 西站
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixIndicationFacility);

		lhs.add(new AddressSpan(2, AddressType.SuffixIndicationFacility));
		addProduct(rhs, lhs);

		// 北门
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixBuildingUnit);

		lhs.add(new AddressSpan(2, AddressType.SuffixBuildingUnit));
		addProduct(rhs, lhs);
		

		//科技大学北门
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.SuffixBuildingUnit);

		lhs.add(new AddressSpan(2, AddressType.BuildingUnit));
		addProduct(rhs, lhs);
		// 一里
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.No);
		rhs.add(AddressType.SuffixLandMark);

		lhs.add(new AddressSpan(2, AddressType.SuffixLandMark));
		addProduct(rhs, lhs);
		// 西桥
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixLandMark);

		lhs.add(new AddressSpan(2, AddressType.SuffixLandMark));
		addProduct(rhs, lhs);

		//天华园 一里
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.District);
		rhs.add(AddressType.SuffixLandMark);

		lhs.add(new AddressSpan(2, AddressType.LandMark));
		addProduct(rhs, lhs);
		//东方太阳城社区
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.SuffixDistrict);

		lhs.add(new AddressSpan(2, AddressType.District));
		addProduct(rhs, lhs);
		//北京市东城区南河沿大街华龙街二段c座一层
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);

		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(3, AddressType.Street));
		addProduct(rhs, lhs);
		
		//11-A
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.Symbol);

		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(2, AddressType.No));
		addProduct(rhs, lhs);

		// 四川省大邑县甲子路５４号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.County);
		rhs.add(AddressType.No);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 云南省昆明市红河谷商铺Ｂ－４
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixBuildingUnit);
		rhs.add(AddressType.Symbol);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(3, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 云南省昆明市滇池路路口省人大对面
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.SuffixCity);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(2, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(2, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 广东省广州市荔湾区西塱麦村北约５２
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.Village);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Village));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 广东省广州市白云区黄边二横路７０
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.No);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(3, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 广东省广州市越秀区下塘宝汉直街８
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 广东省广州市天河区员村路２２６
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Village);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 四川省成都市九里堤南支路２１
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(4, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 四川省新津县五津男装３１号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.County);
		rhs.add(AddressType.No);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 广东省广州市天河区东圃镇大观路中海康城
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 四川省成都市茶店子横街１２
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 云南省昆明市一二一大街１３４号云南民族学院图书馆
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.No);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.SuffixIndicationFacility);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(4, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 云南省昆明市广福路五甲河公共汽车站鲁班家装旁
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.SuffixIndicationFacility);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(4, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 武汉市洪山区鲁磨路地质大学旁新成都火锅对面
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(3, AddressType.Unknow));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 上海长宁区长宁路１２７７弄中山公寓１５栋２０２室
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Municipality);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.BuildingNo);
		rhs.add(AddressType.BuildingUnit);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Municipality));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.BuildingNo));
		lhs.add(new AddressSpan(1, AddressType.BuildingUnit));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 成都市一环路西二段21号成都体院旁
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.City);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(2, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 江北区建新北路65号海关外贸大厦旁
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(3, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 思明区莲花北路25号(二村市场旁)
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.StartSuffix);
		rhs.add(AddressType.Village);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.StartSuffix));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 拱墅区潮王路45号东方豪园文豪阁2604
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 高新区高新技术产业开发区前进大街2699号吉林大学前卫南区北门商贸楼2楼
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.District);
		rhs.add(AddressType.District);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixCounty);
		rhs.add(AddressType.SuffixBuildingUnit);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.BuildingUnit);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.District));
		lhs.add(new AddressSpan(1, AddressType.District));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		lhs.add(new AddressSpan(4, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.SuffixBuildingUnit));
		lhs.add(new AddressSpan(1, AddressType.SuffixLandMark));
		lhs.add(new AddressSpan(1, AddressType.BuildingUnit));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 文三路398号东信大厦裙房2层
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.BuildingUnit);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		lhs.add(new AddressSpan(2, AddressType.BuildingUnit));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 海曙区公园路118弄2号鼓楼步行街
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(2, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 海曙区公园路118弄2号鼓楼步行街
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.No);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.BuildingUnit);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(3, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.BuildingUnit));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 市南区广西路11号(工商银行对面)
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.SuffixCity);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixCounty);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.StartSuffix);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(3, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.StartSuffix));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 市南区广西路11号(工商银行对面)
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.BuildingUnit);
		rhs.add(AddressType.StartSuffix);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.BuildingUnit));
		lhs.add(new AddressSpan(1, AddressType.StartSuffix));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 广东省广州市从化市广场路１０２号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 四川省成都市龙潭寺东路
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(4, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 广东省广州市番禺区市良路
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixCounty);
		rhs.add(AddressType.SuffixCity);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.County));
		lhs.add(new AddressSpan(3, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 云南省寻甸回族彝族自治县
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.County);
		rhs.add(AddressType.County);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(2, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 上海市普陀区陕西北路１５５８号千路公寓Ｃ座２１０２室
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Municipality);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.No);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.Symbol);
		rhs.add(AddressType.SuffixBuildingNo);
		rhs.add(AddressType.No);
		rhs.add(AddressType.SuffixBuildingUnit);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Municipality));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(3, AddressType.LandMark));
		lhs.add(new AddressSpan(2, AddressType.SuffixBuildingNo));
		lhs.add(new AddressSpan(2, AddressType.SuffixBuildingUnit));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 宛平南路99弄新汇公寓2号2201室
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.No);
		rhs.add(AddressType.No);
		rhs.add(AddressType.SuffixBuildingUnit);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(2, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(2, AddressType.SuffixBuildingUnit));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 朝阳区光华路甲8号和乔丽致酒店1楼
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.Conj);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.BuildingUnit);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.BuildingUnit));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 东莞市寮步镇横坑三星工业区(博士科技大楼后)
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.No);
		rhs.add(AddressType.District);
		rhs.add(AddressType.StartSuffix);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Town));
		lhs.add(new AddressSpan(3, AddressType.District));
		lhs.add(new AddressSpan(1, AddressType.StartSuffix));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 广东省东莞市莞城金牛路121号东日电脑市场一期401室
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.BuildingUnit);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(2, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.BuildingUnit));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 郑州市航海东路2号富田太阳城25号702
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.No);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 江苏省南京市玄武区南拘中山东路301号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.District);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(3, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 东莞市常平镇塘角埔区环城路
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixCounty);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Town));
		lhs.add(new AddressSpan(2, AddressType.County));
		lhs.add(new AddressSpan(3, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 哈尔滨市进乡街附近
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.No);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(2, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 东莞市厚街镇新厚沙路新塘村路口直入出100米
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.City);
		rhs.add(AddressType.Town);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Village);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.IndicationPosition);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.Town));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.Village));
		lhs.add(new AddressSpan(2, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(1, AddressType.IndicationPosition));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 哈尔滨市进乡街附近
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.SuffixLandMark);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.SuffixDistrict);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(3, AddressType.District));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 河南省郑州市金水区文化路北环路交叉口北50米路东北辰公寓a2508
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Province);
		rhs.add(AddressType.City);
		rhs.add(AddressType.County);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Crossing);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.IndicationPosition);
		rhs.add(AddressType.SuffixStreet);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.RelatedPos);
		rhs.add(AddressType.LandMark);
		rhs.add(AddressType.Symbol);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Province));
		lhs.add(new AddressSpan(1, AddressType.City));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.Crossing));
		lhs.add(new AddressSpan(1, AddressType.RelatedPos));
		lhs.add(new AddressSpan(1, AddressType.IndicationPosition));
		lhs.add(new AddressSpan(2, AddressType.RelatedPos));
		lhs.add(new AddressSpan(2, AddressType.LandMark));
		lhs.add(new AddressSpan(1, AddressType.Symbol));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);

		// 重庆市九龙坡区石坪桥横街特5号1楼44号
		lhs = new ArrayList<AddressSpan>();
		rhs = new ArrayList<AddressType>(); // right-hand .
		rhs.add(AddressType.Start);
		rhs.add(AddressType.Municipality);
		rhs.add(AddressType.County);
		rhs.add(AddressType.District);
		rhs.add(AddressType.Street);
		rhs.add(AddressType.Unknow);
		rhs.add(AddressType.No);
		rhs.add(AddressType.BuildingUnit);
		rhs.add(AddressType.No);
		rhs.add(AddressType.End);

		lhs.add(new AddressSpan(1, AddressType.Start));
		lhs.add(new AddressSpan(1, AddressType.Municipality));
		lhs.add(new AddressSpan(1, AddressType.County));
		lhs.add(new AddressSpan(2, AddressType.Street));
		lhs.add(new AddressSpan(1, AddressType.Unknow));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.BuildingUnit));
		lhs.add(new AddressSpan(1, AddressType.No));
		lhs.add(new AddressSpan(1, AddressType.End));
		addProduct(rhs, lhs);
	}

	private static UnknowGrammar dicGrammar = new UnknowGrammar();

	/**
	 * 
	 * @return the singleton of basic dictionary
	 */
	public static UnknowGrammar getInstance() {
		return dicGrammar;
	}
}
