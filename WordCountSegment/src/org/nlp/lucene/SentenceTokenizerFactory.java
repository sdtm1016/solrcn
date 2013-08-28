package org.nlp.lucene;

import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeSource.AttributeFactory;

public class SentenceTokenizerFactory extends TokenizerFactory {


	protected SentenceTokenizerFactory(Map<String, String> args) {
		super(args);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Tokenizer create(AttributeFactory factory, Reader input) {
		return new SentenceTokenizer(factory, input);
	}

}
