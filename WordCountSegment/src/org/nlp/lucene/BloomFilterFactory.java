package org.nlp.lucene;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoaderAware;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.nlp.algo.Bloom;

public class BloomFilterFactory extends TokenFilterFactory implements
		ResourceLoaderAware {

	String modelFile = "model";

	@Override
	public void init(Map<String, String> args) {
		super.init(args);
		assureMatchVersion();
	}

	@Override
	public void inform(ResourceLoader loader) throws IOException {
		String modelFile = args.get("words");
		if (modelFile != null && !modelFile.isEmpty()) {
			this.modelFile = modelFile ;
		}
		Bloom.loadModel(this.modelFile);
	}
	
	public TokenStream create(TokenStream input) {
		return new BloomFilter(luceneMatchVersion, input, this.modelFile);
	}
}
