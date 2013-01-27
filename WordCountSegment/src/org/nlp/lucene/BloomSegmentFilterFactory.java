package org.nlp.lucene;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoaderAware;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.nlp.impl.BloomSegmentImpl;

public class BloomSegmentFilterFactory extends TokenFilterFactory implements
		ResourceLoaderAware {

	String modelFile = "model";
	String mode = "full";

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
				
		String mode = args.get("mode");
		
		if (mode != null && !mode.isEmpty()) {
			this.mode = mode ;
		}
		
		BloomSegmentImpl.initDic(modelFile);
	}
	
	public TokenStream create(TokenStream input) {
		if (mode.equals("base")) {
			return new BloomSegmentBaseTokensFilter(input);
		} else if (mode.equals("new")) {
			return new BloomSegmentNewTokensFilter(input);
		} else {
			return new BloomSegmentFilter(input);
		}
	}
}
