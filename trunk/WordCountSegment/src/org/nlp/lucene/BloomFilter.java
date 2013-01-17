package org.nlp.lucene;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.FilteringTokenFilter;
import org.apache.lucene.util.Version;
import org.nlp.algo.Bloom;

public final class BloomFilter extends FilteringTokenFilter {

	// private final CharArraySet stopWords;
	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	Bloom bloom;

	public BloomFilter(Version matchVersion, TokenStream input, String ModelFile) {
		super(true, input);
//		if (ModelFile != null && !ModelFile.isEmpty()) {
			Bloom.loadModel(ModelFile);
//		}
		bloom = new Bloom();
	}

	public BloomFilter(Version matchVersion, TokenStream input) {
		super(true, input);
		Bloom.loadModel("model");
		bloom = new Bloom();
	}	
	
	@Override
	protected boolean accept() {
		// return !stopWords.contains(termAtt.buffer(), 0, termAtt.length());
		// System.out.format("%s=%s",termAtt.toString(),bloom.contains(termAtt.toString()));
		return bloom.contains(termAtt.toString());
		// return false;
	}
}
