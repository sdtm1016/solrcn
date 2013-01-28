package org.nlp.lucene;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoaderAware;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.nlp.impl.BloomSegmentImpl;

public class BloomSegmentTokenizerFactory extends TokenizerFactory implements
		ResourceLoaderAware {
	String modelFile = "model";
	int mode = 0;
	
	@Override
	public void inform(ResourceLoader loader) throws IOException {
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

	@Override
	public Tokenizer create(Reader in) {
		return new BloomSegmentTokenizer(in, mode);
	}
	
}
