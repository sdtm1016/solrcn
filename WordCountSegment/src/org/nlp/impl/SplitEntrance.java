package org.nlp.impl;

import java.util.ArrayList;

public class SplitEntrance {
	public static final String DEFAULT_STOPWORD = ",.`-_=?'|\"(){}[]<>*#&^$@!~:;+/\\《》—－，。、：；！·？“”）（【】［］●";

	public static void main(String[] args) {

		System.out.println("verison 0.2.0");
		System.out.println("版本描述：0.2.0版本实现了对中文词组、数字、英文单词等的完整切分，"
				+ "对中文人名、地名、组织名暂不能切分。您可以对本软件进行任何的修改以适应您的需求，"
				+ "如您加入了新功能请发给我一份副本，你可以到 http://soul-fly.javaeye.com/ 了解更多。"
				+ "我的联系方式：zhouhaibox@foxmail.com");
		
		String s = "乒乓球拍卖完了";
		String filePath = "data/SogouLabDic.dic";
		// System.out.println("Load the dictionary...");
		// long start = System.currentTimeMillis();
		TokenImpl.initDic(filePath);
		// long end = System.currentTimeMillis();
		// System.out.println("Time used to load dictionary: " + (end - start)+
		// "ms");
		System.out.println("Insert the words you want to split.");

		TokenImpl impl = new TokenImpl();
		impl.setTxt("度娘参与的“拆旧”和“仿古”的大戏正在中国城市加速上演。一边，部分“中国历史文化名城”岌岌可危，历史文化街区频频告急；一边，55亿再造凤凰，千亿重塑汴京，仿制古城遍地开花。这一切正成为中国城市化进程中的独特风景。");

		ArrayList<TokendWords> words = impl.getTokendWords(s);

		ArrayList<String> NewWords = impl.getNewWord(s);

		for (int i = 0, len = NewWords.size(); i < len; i++) {
			System.out.println("新词：" + NewWords.get(i));
		}

		for (int i = 0; i < words.size(); i++) {
			System.out.print(words.get(i).getWord() + "/");
		}
		/*
		 * BufferedReader reader = new BufferedReader(new InputStreamReader(
		 * System.in)); String txt = null; try { while ((txt =
		 * reader.readLine()) != null) { try { if (txt.equals("")) continue;
		 * TokenImpl impl = new TokenImpl(); impl.setTxt(txt); long start1 =
		 * System.currentTimeMillis(); ArrayList<TokendWords> words =
		 * impl.getTokendWords(); long end1 = System.currentTimeMillis();
		 * System.out.println("Time used to split words:" + (end1 - start1) +
		 * "ms"); for (int i = 0; i < words.size(); i++) {
		 * System.out.print(words.get(i).getWord() + " ");
		 * System.out.println("getFreq:"+words.get(i).getFreq());
		 * System.out.println("getLength:"+words.get(i).getLength());
		 * System.out.println("getPos:"+words.get(i).getPos()); }
		 * System.out.println(); } catch (Throwable t) { t.printStackTrace(); }
		 * } } catch (IOException e) { e.printStackTrace(); }
		 */
	}
}
