package org.nlp.test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.nlp.lucene.BloomSegmentTokenizer;


public class AnalyzerTest {

	public static void main(String[] args) throws IOException {
		// text to tokenize
		String text = "中华人民共和国,上海市徐汇区东新路99弄38号402,中华人民共和国,电话13810000000邮编100044";
		text = "我如果有天死于非命，一定是笨死的【提示：此用户正在使用Q  Web：http://web.qq.com/】";
		
		// "上海市徐汇区东新路99弄38号402";
		// "上海市浦东新区耀华路99弄16号10402";
		Reader reader = new StringReader(text);
//		Analyzer analyzer = new BloomNewAnalyzer();
		// MaxWordAnalyzer analyzer = new MaxWordAnalyzer(Version.LUCENE_40);
		
//		SimpleAnalyzer simpleAnalyzer = new SimpleAnalyzer(Version.LUCENE_40);
//		TokenStream stream = analyzer.tokenStream("", reader);


		// get the TermAttribute from the TokenStream
//		CharTermAttribute termAtt = stream
//				.addAttribute(CharTermAttribute.class);
//		TypeAttribute typeAtt = stream.addAttribute(TypeAttribute.class);
//		OffsetAttribute offsetAtt = stream.addAttribute(OffsetAttribute.class);

//		NGramTokenizer nGramTokenFilter = new NGramTokenizer(reader, 2, 5);
//
//		
//		CharTermAttribute termAtt = nGramTokenFilter
//				.addAttribute(CharTermAttribute.class);
//		TypeAttribute typeAtt = nGramTokenFilter
//				.addAttribute(TypeAttribute.class);
//		OffsetAttribute offsetAtt = nGramTokenFilter
//				.addAttribute(OffsetAttribute.class);
//		while (nGramTokenFilter.incrementToken()) {
//			System.out.format("word:%s\tstart:%s\tend:%s\ttype:%s\n",
//					termAtt.toString(), offsetAtt.startOffset(),
//					offsetAtt.endOffset(), typeAtt.type());
//		}
//
//		stream.end();
//		stream.close();
//		
		
		Tokenizer tokenizer = new BloomSegmentTokenizer(reader,1,"model_new_all");
		
		CharTermAttribute termAtt = tokenizer
				.addAttribute(CharTermAttribute.class);
					
		while(tokenizer.incrementToken()){
		System.out.println("termAtt:"+termAtt.toString());
			
		}
		
		
	}
	
}


