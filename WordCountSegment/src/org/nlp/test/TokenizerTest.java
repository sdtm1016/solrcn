package org.nlp.test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.nlp.lucene.SentenceTokenizer;

public class TokenizerTest {
	
	public static String SentenceFilter(String src) {
		final String GOODCHAR = "[\\u0030-\\u0039\\u0041-\\u005A\\u0061-\\u007A\\u4E00-\\u9FA5\\uFF10-\\uFF19]+";
		StringBuilder result = new StringBuilder();
		char[] srcChar;
		srcChar = src.toCharArray();
		for (int i = 0; i < srcChar.length; i++) {
			char c = srcChar[i];
			if (String.valueOf(c).matches(GOODCHAR)) {
				result.append(c);
			}
		}
		return result.toString();
	}
public static void main(String[] args) throws IOException {
	String text ="你好,中华english人民共和国，欢1381000,0000迎您。";
	Reader reader = new StringReader(text);
	SentenceTokenizer sentenceTokenizer = new SentenceTokenizer(reader);	
	CharTermAttribute termAtt = sentenceTokenizer.addAttribute(CharTermAttribute.class);
	
	
	while(sentenceTokenizer.incrementToken()){
		System.out.println(SentenceFilter(termAtt.toString()));
	}
	
}
}
