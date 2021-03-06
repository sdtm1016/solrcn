package org.nlp.lucene;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.nlp.impl.BloomSegmentImpl;
import org.nlp.impl.TokendWords;

public class BloomSegmentFilter extends TokenFilter {
	private CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
	private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);

	private int tokStart; // only used if the length changed before this filter
	private int tokEnd; // only used if the length changed before this filter
	private boolean hasIllegalOffsets; // only if the length changed before this
										// filter
	private BloomSegmentImpl wordSegmenter;
	private List<TokendWords> tokenBuffer;
	private Iterator<TokendWords> tokenIter;

	private int mode = 0;
	String modelFile = "model";
	
	public BloomSegmentFilter(TokenStream input) {
		super(input);
		init();
	}
	
	public BloomSegmentFilter(TokenStream input,int mode) {
		super(input);		
		this.mode = mode;
		init();
	}
	
	public BloomSegmentFilter(TokenStream input,int mode, String modelFile) {
		super(input);		
		this.mode = mode;
		this.modelFile = modelFile;
		init();
	}
	
	private void init(){
		wordSegmenter = new BloomSegmentImpl();
		BloomSegmentImpl.initDic(modelFile);
	}
	
	
	@Override
	public boolean incrementToken() throws IOException {

		if (tokenIter == null || !tokenIter.hasNext()) {
			if (input.incrementToken()) {
				tokStart = offsetAtt.startOffset();
				tokEnd = offsetAtt.endOffset();
				hasIllegalOffsets = (tokStart + termAtt.length()) != tokEnd;				
				tokenBuffer = wordSegmenter.getTokens(termAtt.toString(),mode);				
				tokenIter = tokenBuffer.iterator();
				if (!tokenIter.hasNext())
					return false;

			} else {
				return false;
			}
		}

		clearAttributes();

		TokendWords nextWord = tokenIter.next();

		termAtt.copyBuffer(nextWord.next(), 0, nextWord.next().length);
		if (hasIllegalOffsets) {
			offsetAtt.setOffset(tokStart, tokEnd);
		} else {
			offsetAtt.setOffset(nextWord.start, nextWord.end);
		}
		typeAtt.setType("word");
		return true;
	}

	@Override
	public void reset() throws IOException {
		super.reset();
		tokenIter = null;
	}
}
