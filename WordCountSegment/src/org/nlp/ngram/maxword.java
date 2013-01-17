package org.nlp.ngram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class maxword {
	HashMap<String, Double> data;

	// List<TokendWords> tokens;

	public maxword() {
		// tokens = new ArrayList<TokendWords>();
		data = datafile("data/SogouLabDic.dic");
		// data = new HashMap<String, Double>();
		// data.put("意思", 1.0);
		// data.put("课程", 1.0);
		// data.put("语言学", 1.0);
		// data.put("计算", 1.0);
	}

	public HashMap<String, Double> datafile(String name) {
		String line;
		String[] word;
		BufferedReader br;
		HashMap<String, Double> data = new HashMap<String, Double>();
		try {
			br = new BufferedReader(new FileReader(name));
			line = br.readLine();
			while (line != null) {
				word = line.split("\t");
				data.put(word[0], Double.parseDouble(word[1]));
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public List<String> backwardSegment(String text) {
		List<String> result = new ArrayList<String>();		
		String key = "";
		int textlen = text.length();
		int maxlen = 5;
		int start = 0;
		int end = 0;
		int pos = 0;

		for (int offset = textlen; offset > 0; offset--) {

			for (int len = maxlen; len > 0; len--) {

				end = offset;
				start = Math.max(offset - len, 0);
				key = text.substring(start, end);

				if (data.get(key) != null) {
					offset = start + 1;
//					System.out.println("key:" + key + " offset:" + (offset - 1));
					pos++;
					break;
				}
				
				if (key.length() == 1) {
					offset = start + 1;
					pos++;
//					System.out.println("key:" + key + " offset:" + (offset - 1));
					break;
				}
			}
			
			result.add(key);
		}
		
		Collections.reverse(result);

		return result;
	}

	public List<String> forwardSegment(String text) {
		List<String> result = new ArrayList<String>();
		String key = "";
		int textlen = text.length();
		int maxlen = 5;
		int start = 0;
		int end = 0;
		int pos = 0;

		for (int offset = 0; offset < textlen; offset++) {
			for (int len = maxlen; len > 0; len--) {
				start = offset;
				end = Math.min(start + len, textlen);
				key = text.substring(start, end);
				if (data.get(key) != null) {
					offset = end - 1;
					// System.out.println("key:"+key+" offset:"+(offset-1));
					pos++;
					break;
				}
				if (key.length() == 1) {
					offset = end - 1;
					pos++;
					// System.out.println("key:"+key+" offset:"+(offset-1));
					break;
				}
			}
			// tokens.add(new TokendWords(key, 1, null, key.length(),pos,
			// start));
			result.add(key);
		}
		return result;
	}

	public static void main(String[] args) {

		String s = "小明天天上学校";
		List<String> tw = new ArrayList<String>();
		tw.add("乒乓球");
		tw.add("拍卖");
		tw.add("完了");
		maxword mw = new maxword();
		System.out.println(mw.backwardSegment(s));
		System.out.println(mw.forwardSegment(s));

	}
}
