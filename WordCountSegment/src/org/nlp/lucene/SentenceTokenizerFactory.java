package org.nlp.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

public class SentenceTokenizerFactory extends TokenizerFactory{

	@Override
	public Tokenizer create(Reader in) {
		return new SentenceTokenizer(in);
	}

}
