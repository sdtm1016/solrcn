package org.nlp.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;

public class WordCountAnalyzer extends Analyzer {
	@Override
	protected TokenStreamComponents createComponents(String fieldName,
			Reader reader) {
		final Tokenizer tokenizer = new SentenceTokenizer(reader);
		TokenStream result = new WordCountFilter(tokenizer);
		return new TokenStreamComponents(tokenizer, result);
	}
}