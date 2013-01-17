package org.nlp.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;
import org.apache.lucene.util.Version;

public class BloomAnalyzer extends Analyzer{
	private final Version matchVersion;
	
	public BloomAnalyzer(Version matchVersion){
		this.matchVersion = matchVersion;
	}
	
	@Override
	protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
//		final Tokenizer tokenizer = new WhitespaceTokenizer(matchVersion, reader);
		final Tokenizer tokenizer = new SentenceTokenizer(reader);
		TokenStream result = new NGramTokenFilter(tokenizer,2,5);
		 result = new BloomFilter(matchVersion, result);
		return new TokenStreamComponents(tokenizer, result);
//		return new TokenStreamComponents(tokenizer);
	}
}
