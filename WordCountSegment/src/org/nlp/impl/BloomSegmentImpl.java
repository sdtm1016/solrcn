package org.nlp.impl;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.nlp.algo.Bloom;

/**
 * @author KmaDou
 *
 */
public class BloomSegmentImpl {
	static Bloom bloom;
	static String defaultModel = "model";
	
	BloomSegmentImpl(){
		this(defaultModel);
	}
	
	BloomSegmentImpl(String model) {
		if (bloom == null)
			bloom = new Bloom("model");
	}
	
	public static List<String> getListTokens(String text){		
		StringBuffer NewWord = new StringBuffer();
		List<String> Tonkens = new ArrayList<String>();
		List<String> NewTonkens = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		int lastOffset = 0;
				
		BitSet bitSet = new BitSet(text.length());
		int minGramSize = 2;
		int maxGramSize = 5;
		
		int length = text.length();
		
		//进行minGramSize到maxGramSize元切词
		for (int gramSize = minGramSize; gramSize <= maxGramSize; gramSize++) {
			//第gramSize元切词
			for (int i = 0; i <= length - gramSize; i++) {
				//用bloom过滤器保留在词典里的词
				if (bloom.contains(text.substring(i, i + gramSize))) {
					Tonkens.add(text.substring(i, i + gramSize));
					bitSet.set(i, i + gramSize);
				}
			}		
		}		
		
		//新词识别
		for (int i = 0; i < text.length(); i++) {
			if (!bitSet.get(i)) {

				if ((NewWord.length() == 0) || (i - lastOffset) < 2) {
					NewWord.append(text.charAt(i));
				} else {
					NewTonkens.add(NewWord.toString());
					NewWord.delete(0, NewWord.length());
					NewWord.append(text.charAt(i));
				}
				lastOffset = i;
			}
			if (i == text.length()-1 && NewWord.length() > 0) {
				NewTonkens.add(NewWord.toString());
			}
		}	
		
		result.addAll(Tonkens);
		result.addAll(NewTonkens);
		return result;
	}
	
	//分词
	public static List<TokendWords> getBaseTokens(String text){
		List<TokendWords> result = new ArrayList<TokendWords>();

		int start = 0;
		int pos = 0;
		BitSet bitSet = new BitSet(text.length());
		int minGramSize = 2;
		int maxGramSize = 5;
		
		int length = text.length();
		
		//进行minGramSize到maxGramSize元切词
		for (int gramSize = minGramSize; gramSize <= maxGramSize; gramSize++) {
			//第gramSize元切词
			for (int i = 0; i <= length - gramSize; i++) {
				//用bloom过滤器保留在词典里的词
				if (bloom.contains(text.substring(i, i + gramSize))) {
					start = i;
					//当前切割的词语:text.substring(i, i + gramSize)
					result.add(new TokendWords(text.substring(i, i + gramSize), 1l,new String[] { "Word" }, gramSize, pos, start));
					bitSet.set(i, i + gramSize);
					pos++;
				}
			}		
		}				
		return result;		
	}
	
	public static List<TokendWords> getNewTokens(String text){
		List<TokendWords> result = new ArrayList<TokendWords>();
		StringBuffer NewWord = new StringBuffer();
		
		int lastOffset = 0;
		int start = 0;
		int pos = 0;
		BitSet bitSet = new BitSet(text.length());
		int minGramSize = 2;
		int maxGramSize = 5;
		
		int length = text.length();
		
		//进行minGramSize到maxGramSize元切词
		for (int gramSize = minGramSize; gramSize <= maxGramSize; gramSize++) {
			//第gramSize元切词
			for (int i = 0; i <= length - gramSize; i++) {
				//用bloom过滤器保留在词典里的词
				if (bloom.contains(text.substring(i, i + gramSize))) {
//					start = i;
					//当前切割的词语:text.substring(i, i + gramSize)
//					result.add(new TokendWords(text.substring(i, i + gramSize), 1l,new String[] { "Word" }, gramSize, pos, start));
					bitSet.set(i, i + gramSize);
//					pos++;
				}
			}		
		}		
		
		//新词识别
		for (int i = 0; i < text.length(); i++) {
			if (!bitSet.get(i)) {//判断当前字附是否已经作为已识别词语
				//如果新词缓存长度为0认为是新词的第一个字
				if (NewWord.length() == 0) {
					start = i;
					NewWord.append(text.charAt(i));
					//设置新词开始位置					
				} else if ((i - lastOffset) == 1) { //当前位置和上一个字符偏移量为1则添加到新词缓冲区
					NewWord.append(text.charAt(i));
				} else {// 偏移量不等于1,说明上一个词已经结束,记录新词,并开始新一轮新词记录					
					result.add(new TokendWords(NewWord.toString(), 1l,new String[] { "NewWord" }, NewWord.length(), pos, start));
					pos++;					
					NewWord.delete(0, NewWord.length());
					start = i;
					NewWord.append(text.charAt(i));
				}
				lastOffset = i;
			}
			if (i == text.length()-1 && NewWord.length() > 0) {//最后一次循环将要退出时检查新词缓冲区内是否有字
				result.add(new TokendWords(NewWord.toString(), 1l,new String[] { "NewWord" }, NewWord.length(), pos, start));				
			}
		}		
		
		return result;
	}	
	
	
	
	public static List<TokendWords> getTokens(String text){
		List<TokendWords> result = new ArrayList<TokendWords>();
		StringBuffer NewWord = new StringBuffer();
		
		int lastOffset = 0;
		int start = 0;
		int pos = 0;
		BitSet bitSet = new BitSet(text.length());
		int minGramSize = 2;
		int maxGramSize = 5;
		
		int length = text.length();
		
		//进行minGramSize到maxGramSize元切词
		for (int gramSize = minGramSize; gramSize <= maxGramSize; gramSize++) {
			//第gramSize元切词
			for (int i = 0; i <= length - gramSize; i++) {
				//用bloom过滤器保留在词典里的词
				if (bloom.contains(text.substring(i, i + gramSize))) {
					start = i;
					//当前切割的词语:text.substring(i, i + gramSize)
					result.add(new TokendWords(text.substring(i, i + gramSize), 1l,new String[] { "Word" }, gramSize, pos, start));
					bitSet.set(i, i + gramSize);
					pos++;
				}
			}		
		}		
		
		//新词识别
		for (int i = 0; i < text.length(); i++) {
			if (!bitSet.get(i)) {//判断当前字附是否已经作为已识别词语
				//如果新词缓存长度为0认为是新词的第一个字
				if (NewWord.length() == 0) {
					start = i;
					NewWord.append(text.charAt(i));
					//设置新词开始位置					
				} else if ((i - lastOffset) == 1) { //当前位置和上一个字符偏移量为1则添加到新词缓冲区
					NewWord.append(text.charAt(i));
				} else {// 偏移量不等于1,说明上一个词已经结束,记录新词,并开始新一轮新词记录					
					result.add(new TokendWords(NewWord.toString(), 1l,new String[] { "NewWord" }, NewWord.length(), pos, start));
					pos++;					
					NewWord.delete(0, NewWord.length());
					start = i;
					NewWord.append(text.charAt(i));
				}
				lastOffset = i;
			}
			if (i == text.length()-1 && NewWord.length() > 0) {//最后一次循环将要退出时检查新词缓冲区内是否有字
				result.add(new TokendWords(NewWord.toString(), 1l,new String[] { "NewWord" }, NewWord.length(), pos, start));				
			}
		}		
		
		return result;
	}	
	
	
	public static void main(String[] args) {
		String s = "张一凡的电话13699191946,在上海市浦东新区耀华路99弄16号10402,上海市徐汇区东新路99弄38号402";
		BloomSegmentImpl bloomSegmentImpl = new BloomSegmentImpl();
		System.out.println(bloomSegmentImpl.getListTokens(s));
	}
}
