package org.nlp.address;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;


public class AddressAnalyzer extends Analyzer {
	@Override
	protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
		final Tokenizer tokenizer = new SentenceTokenizer(reader);
	    TokenStream result = new AddressFilter(tokenizer);
		return  new TokenStreamComponents(tokenizer, result);
	}		
}
