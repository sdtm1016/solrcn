package org.nlp.tran;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class ngramDict {
	public static void main(String[] args) throws IOException {
		ngramSplite("中华人民共和国");
	}
	public static void ngramSplite(String text) throws IOException {

		int minGram = 2;
		int maxGram = 5;

		NGramTokenizer nGramtoken = new NGramTokenizer(new StringReader(text), minGram, maxGram);
		CharTermAttribute termAtt = nGramtoken
				.addAttribute(CharTermAttribute.class);

		while (nGramtoken.incrementToken()) {
			System.out.println(termAtt.toString());
		}

		nGramtoken.close();
	}
}
