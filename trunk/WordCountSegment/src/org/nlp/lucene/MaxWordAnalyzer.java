package org.nlp.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.util.Version;

public class MaxWordAnalyzer extends Analyzer{
//	private final Version matchVersion;
	
	public MaxWordAnalyzer(Version matchVersion){
//		this.matchVersion = matchVersion;
	}
	
	public MaxWordAnalyzer(){
	}
	
	@Override
	protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
		
		
//		final Tokenizer tokenizer = new WhitespaceTokenizer(matchVersion, reader);
		final Tokenizer tokenizer = new SentenceTokenizer(reader);
//		TokenStream result = new NGramTokenFilter(tokenizer,2,5);
		TokenStream tokenStream = new MaxWordFilter(tokenizer);
//		new LengthFilter(result,2,3); //过滤过长的词或过短的词		
		
		return new TokenStreamComponents(tokenizer, tokenStream);
//		return new TokenStreamComponents(tokenizer);
	}
}
