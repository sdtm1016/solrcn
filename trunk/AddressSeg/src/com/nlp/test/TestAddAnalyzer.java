package com.nlp.test;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.nlp.address.lucene.AddressAnalyzer;




public class TestAddAnalyzer {

	public static void main(String[] args) throws IOException {
		// text to tokenize
		final String text = "泸州市龙马潭区北苑街1号1栋1单元1楼1号附1号";
			//"上海市徐汇区东新路99弄38号402";
			//"上海市浦东新区耀华路99弄16号10402";
		
		AddressAnalyzer analyzer = new AddressAnalyzer();
		TokenStream stream = analyzer.tokenStream("field", new StringReader(
				text));

		// get the TermAttribute from the TokenStream
		CharTermAttribute termAtt = stream.addAttribute(CharTermAttribute.class);
		TypeAttribute typeAtt = stream.addAttribute(TypeAttribute.class);
		OffsetAttribute offsetAtt = stream.addAttribute(OffsetAttribute.class);

		stream.reset();

		// print all tokens until stream is exhausted
		while (stream.incrementToken()) {
			System.out.print(termAtt.toString()+" ");
			System.out.print(offsetAtt.startOffset()+" "+offsetAtt.endOffset()+" ");
			System.out.println( typeAtt.type());
		}

		stream.end();
		stream.close();
	}

}
