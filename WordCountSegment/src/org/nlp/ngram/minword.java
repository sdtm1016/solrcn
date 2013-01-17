package org.nlp.ngram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class minword {
	HashMap<String, Double> data;

	// List<TokendWords> tokens;

	public minword() {
		// tokens = new ArrayList<TokendWords>();
		// data = datafile("data/SogouLabDic.dic");
		data = new HashMap<String, Double>();
		data.put("意思", 1.0);
		data.put("课程", 1.0);
		data.put("语言", 1.0);
		data.put("语言学", 1.0);
		data.put("计算", 1.0);
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
		int maxLen = 5;
		int textLen = text.length();
		String key = "";
		for (int i = textLen; i > 0; i--) {
			int count = 0;
			int end = Math.max(i - maxLen, 0);
			for (int j = i - 2; j >= end; j--) {
				key = text.substring(j, i);
				if (data.get(key) != null) {
					result.add(key);// 添加到列表
					count++;
					i -= key.length() - 1;
					break;
				}
			}
			if (count == 0)
				result.add(text.substring(i - 1, i));

		}
		Collections.reverse(result);
		return result;
	}

	public List<String> forwardSegment(String text) {
		List<String> result = new ArrayList<String>();
		int maxLen = 5;
		int textLen = text.length();
		String key = "";
		for (int i = 0; i < textLen; i++) {
			int count = 0;
			int end = Math.min(i + maxLen, textLen);
			for (int j = i + 2; j <= end; j++) {
				key = text.substring(i, j);
				if (data.get(key) != null) {
					result.add(key);// 添加到列表
					count++;
					i += key.length() - 1;
					break;
				}
			}
			if (count == 0)
				result.add(text.substring(i, i + 1));

		}
		return result;
	}

	public List<String> forwardSegment1(String text) {
		List<String> result = new ArrayList<String>();
		String key = "";
		int textlen = text.length();
		int maxlen = 5;
		int start = 0;
		int end = 0;
		int pos = 0;
		boolean found = false;

		for (int offset = 0; offset < textlen; offset++) {
			key = text.substring(offset, Math.min(offset + maxlen, textlen));
			for (int n = 1, len = key.length(); n <= len; n++) {
				if (data.get(key.substring(0, n)) != null) {
					key = key.substring(0, n);
					start = offset;
					end = offset + n;
					pos++;
					offset = offset + n - 1;
					found = true;
					break;
				}
			}
			if (!found) {
				key = text.substring(offset, offset + 1);
				start = offset;
				end = offset + 1;
				pos++;
			}
			System.out.format("key=%s,start=%s,end=%s,len=%s,pos=%s\n", key,
					start, end, key.length(), pos);
			result.add(key);
			found = false;
		}
		return result;
	}

	public static void main(String[] args) {

		String s = "计算语言学课程屌丝觉得有意思";
		minword mw = new minword();
		// System.out.println(mw.forwardSegment(s));
		System.out.println(mw.backwardSegment(s));

	}
}
