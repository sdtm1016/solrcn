package org.nlp.address.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;


public class AddressAnalyzer extends Analyzer {
	@Override
	protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
		final Tokenizer tokenizer = new SentenceTokenizer(reader);
//		final Tokenizer tokenizer = new AddTokenizer(reader);
	    TokenStream result = new AddressFilter(tokenizer);
		return  new TokenStreamComponents(tokenizer, result);
	}		
}
