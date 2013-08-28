package org.nlp.tran;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public class ngramDict {
	public static void main(String[] args) throws IOException {
		ngramSplite("中华人民共和国成立于1949年10月1日");
	}
	public static void ngramSplite(String text) throws IOException {

		int minGram = 1;
		int maxGram = text.length();

		NGramTokenizer nGramtoken = new NGramTokenizer(new StringReader(text), minGram, maxGram);
		CharTermAttribute termAtt = nGramtoken
				.addAttribute(CharTermAttribute.class);
		OffsetAttribute offsetAtt = nGramtoken
		.addAttribute(OffsetAttribute.class);
		TypeAttribute typeAtt = nGramtoken.addAttribute(TypeAttribute.class);
		while (nGramtoken.incrementToken()) {
			System.out.println(termAtt.toString());
			System.out.println(offsetAtt.startOffset());
		}

		nGramtoken.close();
	}
}
