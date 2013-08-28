package org.nlp.lucene;

import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeSource.AttributeFactory;
import org.nlp.impl.BloomSegmentImpl;

public class BloomSegmentTokenizerFactory extends TokenizerFactory  {
	protected BloomSegmentTokenizerFactory(Map<String, String> args) {
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

	String modelFile = "model";
	int mode = 0;
	


	@Override
	public Tokenizer create(AttributeFactory factory, Reader input) {
		return new BloomSegmentTokenizer(input, mode);
	}
	
}
