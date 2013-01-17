package org.nlp.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.nlp.dictionary.InitDictionary;

public class MaxWordImpl {

	private static HashMap<String, Double> dicMap;
	private static boolean forward;
	/** 存放分词结果 */
	private List<TokendWords> words;

	public MaxWordImpl() {
		
	}
	
	private static void loadFile(String path){
		String line;
		String[] word;
		InputStreamReader isr;
		BufferedReader br;
		dicMap = new HashMap<String, Double>();
		File dic = new File(path);
		try {
			if (!dic.exists()) {
				InputStream fs = InitDictionary.class.getClassLoader()
						.getResourceAsStream(path);
				isr = new InputStreamReader(fs, "utf-8");
			} else {
				// System.out.println(filePath);
				FileInputStream fs = new FileInputStream(path);
				isr = new InputStreamReader(fs, "utf-8");
			}

			br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				word = line.split("\t");
				dicMap.put(word[0], Double.parseDouble(word[1]));
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void init(String path,boolean isforward) {
		if(dicMap==null){
			System.out.println("Load the dictionary..."+path);
			loadFile(path);
		}
		forward=isforward;
	}
	/**
	 * 反向切分
	 * @param text
	 * @return
	 */
	private List<TokendWords> backwardSegment(String text) {
		List<TokendWords> result = new ArrayList<TokendWords>();
		int maxLen = 5;
		int textLen = text.length();
		String key = "";
		int offset = textLen;
		int pos = 0;
		for (int i = offset; i > 0; i--) {

			int end = Math.max(i - maxLen, 0);
			for (int j = i - 2; j >= end; j--) {
				key = text.substring(j, i);
				if (dicMap.containsKey(key)) {
					pos++;
					TokendWords tws = new TokendWords(key, 1l,
							new String[] { "word" }, i - j, pos, j);
					result.add(tws);// 添加到列表
					offset = j;
				}
			}
			if (i <= offset) {
				pos++;
				key = text.substring(i - 1, i);
				TokendWords tws = new TokendWords(key, 1l,
						new String[] { "newWord" }, 1, pos, i - 1);
			    result.add(tws);
			}
		}
		Collections.reverse(result);
		return result;
	}
	/**
	 * 正向切分
	 * @param text
	 * @return
	 */
	private List<TokendWords> forwardSegment(String text) {
		List<TokendWords> result = new ArrayList<TokendWords>();
		int maxLen = 5;
		int textLen = text.length();
		String key = "";
		int offset = 0;
		int pos = 0;
		for (int i = offset; i < textLen; i++) {
//			int count = 0;
			int end = Math.min(i + maxLen, textLen);
			for (int j = i + 2; j <= end; j++) {
				key = text.substring(i, j);
				if (dicMap.containsKey(key)) {
					pos++;
					TokendWords tws = new TokendWords(key, 1l,
							new String[] { "word" }, j - i, pos, i);
					result.add(tws);// 添加到列表
					offset = j;
				}
			}
			if (i >= offset) {
				pos++;
				key = text.substring(i, i + 1);
				TokendWords tws = new TokendWords(key, 1l,
						new String[] { "newWord" }, 1, pos, i);
				result.add(tws);

			}

		}
		return result;
	}
	
	public List<TokendWords> getTokendWords(String text){
		if(forward){
			words=forwardSegment(text);
		}else{
			words=backwardSegment(text);
		}
		return words;
	}

	public static void main(String[] args) {

		String src = "新浪微博";
		MaxWordImpl mw = new MaxWordImpl();
		MaxWordImpl.init("data/SogouLabDic.dic", true);

		List<TokendWords> dst = mw.forwardSegment(src);
		for (int n = 0; n < dst.size(); n++) {
			System.out.format("%s:%s,pos:%s\n", dst.get(n).getAttri()[0], dst
					.get(n).getWord(), dst.get(n).getPos());
		}

	}
}
