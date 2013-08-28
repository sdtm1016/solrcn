package org.nlp.lucene;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.nlp.algo.Bloom;

public class BloomFilterFactory extends TokenFilterFactory {

	protected BloomFilterFactory(Map<String, String> args) {
		super(args);
		String modelFile = args.get("words");
		if (modelFile != null && !modelFile.isEmpty()) {
			this.modelFile = modelFile ;
		}
		Bloom.loadModel(this.modelFile);	
	}

	String modelFile = "model";

	public TokenStream create(TokenStream input) {
		return new BloomFilter(luceneMatchVersion, input, this.modelFile);
	}

}
