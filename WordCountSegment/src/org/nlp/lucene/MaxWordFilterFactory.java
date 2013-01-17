package org.nlp.lucene;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoaderAware;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.nlp.impl.MaxWordImpl;

public class MaxWordFilterFactory extends TokenFilterFactory implements
		ResourceLoaderAware {

	@Override
	public void init(Map<String, String> args) {
		super.init(args);
		assureMatchVersion();
	}

	@Override
	public void inform(ResourceLoader loader) throws IOException {
		String dicPath = args.get("dicPath");
		String forwardbool=args.get("isForward");
		boolean isForward=false;
		if (dicPath == null || dicPath.isEmpty()) {
			dicPath = "SogouLabDic.dic";
		}
		if(forwardbool != null && !forwardbool.isEmpty()){
			isForward=Boolean.parseBoolean(forwardbool);
		}
		MaxWordImpl.init(dicPath,isForward);
	}

	public TokenStream create(TokenStream input) {
		return new MaxWordFilter(input);
	}

}
