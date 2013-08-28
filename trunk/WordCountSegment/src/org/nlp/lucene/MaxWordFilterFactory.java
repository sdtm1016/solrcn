package org.nlp.lucene;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.nlp.impl.MaxWordImpl;

public class MaxWordFilterFactory extends TokenFilterFactory {

	protected MaxWordFilterFactory(Map<String, String> args) {
		super(args);
		String dicPath = args.get("dicPath");
		String forwardbool = args.get("isForward");
		boolean isForward = false;
		if (dicPath == null || dicPath.isEmpty()) {
			dicPath = "SogouLabDic.dic";
		}
		if (forwardbool != null && !forwardbool.isEmpty()) {
			isForward = Boolean.parseBoolean(forwardbool);
		}
		MaxWordImpl.init(dicPath, isForward);
	}

	public TokenStream create(TokenStream input) {
		return new MaxWordFilter(input);
	}

}
