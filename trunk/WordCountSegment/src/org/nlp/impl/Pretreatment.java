/**
 * 
 */
package org.nlp.impl;

import java.util.ArrayList;


/**
 * 预处理类，可以对数字和英文单词等进行处理。
 * 暂时只对待文章进行短句切分。
 * @author Tony
 *
 */
public class Pretreatment {

	private static ArrayList<Sentence> sens;

	public Pretreatment(){
	}
	
	public static ArrayList<String> splitToParagraph(String txt){
		ArrayList<String> paragraphList = new ArrayList<String>();
		String[] paragraphs = txt.split("\n");
		int size = paragraphs.length;
		for(int i = 0; i<size; i++){
			paragraphList.add(paragraphs[i]+"\n");
		}
		return paragraphList;
	}
	
	public static ArrayList<Sentence> splitToSentence(String src){
		ArrayList<Sentence> sentences = null;

		if (src != null) {
			sentences = new ArrayList<Sentence>();
			String s1 = "";
			String[] sl = StringUtil.atomSplit(src);

			for (int i = 0; i < sl.length; i++) {
				
				// "。！？：；…~·#……&*（）——+=|、，“”‘’";
				if (StringUtil.SEPERATOR_C.indexOf(sl[i]) != -1
						|| StringUtil.SEPERATOR_NEWLINE_TAB.indexOf(sl[i]) != -1
						|| StringUtil.SEPERATOR_C.indexOf(sl[i]) != -1
						|| StringUtil.SEPERATOR_E.indexOf(sl[i]) != -1) {
					if (StringUtil.SEPERATOR_NEWLINE_TAB.indexOf(sl[i]) == -1)
						s1 += sl[i];
					if (s1.length() > 0) {
						sentences.add(new Sentence(s1, true));
						s1 = "";
					}
					// 是回车、换行符、Tab键、空格，则不需要进行分析处理
					if (StringUtil.SEPERATOR_NEWLINE_TAB.indexOf(sl[i]) != -1) {
						sentences.add(new Sentence(sl[i]));
					} else if (StringUtil.SEPERATOR_C.indexOf(sl[i]) != -1
							|| StringUtil.SEPERATOR_E.indexOf(sl[i]) != -1){}
				} else
					s1 += sl[i];
			}

			if (s1.length() > 0) {
				sentences.add(new Sentence(s1, true));
			}
		}
		return sentences;
	}
	

	public static ArrayList<Sentence> getSens() {
		return sens;
	}

	public static void setSens(ArrayList<Sentence> sens) {
		Pretreatment.sens = sens;
	}

	public static void main(String[] args){
		String s = "这次主要是对Yard中文分词系统里面,关于最长词的归并算法做了优化，现在系统能够对“使用户满意的做法，" +
				"乒乓球拍卖完了”这样的句型进行正确切分了。但是目前一直没有找到合适的字频词频词典，所以最大概率分词算法还" +
				"没能加入到分词系统中。按计划打算在这周自己对1亿字左右的语料进行切分统计做个字频词频词典出来到时候分享给" +
				"大家。";

		ArrayList<Sentence> sents = Pretreatment.splitToSentence(s);
		for(Sentence sl : sents)
			System.out.println(sl.getSentence());
	}
}
