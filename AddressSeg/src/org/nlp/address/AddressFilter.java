package org.nlp.address;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public class AddressFilter extends TokenFilter {
	private CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
	private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);

	private int tokStart; // only used if the length changed before this filter
	private int tokEnd; // only used if the length changed before this filter
	private boolean hasIllegalOffsets; // only if the length changed before this
										// filter
	private AddressTagger wordSegmenter;
	private List<AddressToken> tokenBuffer;
	private Iterator<AddressToken> tokenIter;

	protected AddressFilter(TokenStream input) {
		super(input);
		AddressTagger wordSegmenter = new AddressTagger();		
	}

	@Override
	public boolean incrementToken() throws IOException {

		if (tokenIter == null || !tokenIter.hasNext()) {
			if (input.incrementToken()) {
				tokStart = offsetAtt.startOffset();
				tokEnd = offsetAtt.endOffset();
				hasIllegalOffsets = (tokStart + termAtt.length()) != tokEnd;
				tokenBuffer = wordSegmenter.basicTag(termAtt.toString());
				tokenIter = tokenBuffer.iterator();
				if (!tokenIter.hasNext())
					return false;

			} else {
				return false;
			}
		}

		clearAttributes();

		AddressToken nextWord = tokenIter.next();

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
