package org.nlp.lucene;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.nlp.impl.BloomSegmentImpl;

public class BloomSegmentFilterFactory extends TokenFilterFactory {
	String modelFile = "model";
	int mode = 0;
	
	protected BloomSegmentFilterFactory(Map<String, String> args) {
		super(args);
		String modelFile = args.get("words");
		if (modelFile != null && !modelFile.isEmpty()) {
			this.modelFile = modelFile ;
		}
				
		String mode = args.get("mode");
		
		if (mode != null && !mode.isEmpty()) {
									
			this.mode = Integer.parseInt(mode);
		}
		
		BloomSegmentImpl.initDic(modelFile);
	}


	
	public TokenStream create(TokenStream input) {
			return new BloomSegmentFilter(input,mode);
	}
}
