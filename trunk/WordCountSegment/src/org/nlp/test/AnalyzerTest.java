package org.nlp.test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;
import org.nlp.lucene.BloomAnalyzer;

public class AnalyzerTest {

	public static void main(String[] args) throws IOException {
		// text to tokenize
		final String text = "中华人民共和国,上海市徐汇区东新路99弄38号402";
		// "上海市徐汇区东新路99弄38号402";
		// "上海市浦东新区耀华路99弄16号10402";
		Reader reader = new StringReader(text);
		 BloomAnalyzer analyzer = new BloomAnalyzer(Version.LUCENE_40);
		// MaxWordAnalyzer analyzer = new MaxWordAnalyzer(Version.LUCENE_40);
		
//		SimpleAnalyzer simpleAnalyzer = new SimpleAnalyzer(Version.LUCENE_40);
		TokenStream stream = analyzer.tokenStream("", reader);


		// get the TermAttribute from the TokenStream
		CharTermAttribute termAtt = stream
				.addAttribute(CharTermAttribute.class);
		TypeAttribute typeAtt = stream.addAttribute(TypeAttribute.class);
		OffsetAttribute offsetAtt = stream.addAttribute(OffsetAttribute.class);

		NGramTokenFilter nGramTokenFilter = new NGramTokenFilter(stream, 2, 5);

		
		while (nGramTokenFilter.incrementToken()) {
			System.out.format("word:%s\tstart:%s\tend:%s\ttype:%s\n",
					termAtt.toString(), offsetAtt.startOffset(),
					offsetAtt.endOffset(), typeAtt.type());
		}

		stream.end();
		stream.close();
	}

}
