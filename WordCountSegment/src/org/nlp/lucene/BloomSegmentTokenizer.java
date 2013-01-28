package org.nlp.lucene;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeSource;
import org.nlp.impl.BloomSegmentImpl;
import org.nlp.impl.TokendWords;

/**
 * Tokenizes input text into sentences.
 * <p>
 * The output tokens can then be broken into words with {@link WordTokenFilter}
 * </p>
 * 
 * @lucene.experimental
 */
public final class BloomSegmentTokenizer extends Tokenizer {

	/**
	 * End of sentence punctuation: 。，！？�?!?;
	 */
	private final static String PUNCTION = "。，！？；,!?;";

	private final static String SPACES = " 　\t\r\n";

	private final StringBuilder buffer = new StringBuilder();
	

	private int tokenStart = 0, tokenEnd = 0;
	private boolean hasIllegalOffsets;
	
	BloomSegmentImpl wordSegmenter;
	private List<TokendWords> tokenBuffer;
	private Iterator<TokendWords> tokenIter;

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
	private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);

	private int mode = 0;
	private String modelFile = "model";
	
	public BloomSegmentTokenizer(Reader reader) {
		super(reader);
		init();
	}
	
	public BloomSegmentTokenizer(Reader reader, int mode) {
		super(reader);		
		this.mode = mode;
		init();
	}
	
	public BloomSegmentTokenizer(Reader reader, int mode, String modelFile) {
		super(reader);		
		this.mode = mode;
		this.modelFile = modelFile;
		init();
	}
	
	public BloomSegmentTokenizer(AttributeSource source, Reader reader) {
		super(source, reader);
	}

	public BloomSegmentTokenizer(AttributeFactory factory, Reader reader) {
		super(factory, reader);
	}

	private void init(){
		wordSegmenter = new BloomSegmentImpl();
		BloomSegmentImpl.initDic(modelFile);
	}	
	
	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();
		buffer.setLength(0);
		int ci;
		char ch;

		tokenStart = tokenEnd;
		ci = input.read();
		ch = (char) ci;

		while (true) {
			if (ci == -1) {
				break;
			} else{				
				buffer.append(ch);
				ci = input.read();
				ch = (char) ci;
				tokenEnd++;		
			}			
		}
		
		
		if (buffer.length() > 0){
			System.out.println("buffer:"+buffer.toString());
			hasIllegalOffsets = (tokenStart + termAtt.length()) != tokenEnd;
			tokenBuffer = wordSegmenter.getTokens(buffer.toString(),mode);
			tokenIter = tokenBuffer.iterator();
			}			
		
		if (!tokenIter.hasNext()) {
			return false;
		} else {
			TokendWords nextWord = tokenIter.next();

			termAtt.copyBuffer(nextWord.next(), 0, nextWord.next().length);
			if (hasIllegalOffsets) {
				offsetAtt.setOffset(tokenStart, tokenEnd);
			} else {
				offsetAtt.setOffset(nextWord.start, nextWord.end);
			}
			typeAtt.setType("word");
			return true;
		}
				
	}

	@Override
	public void reset() throws IOException {
		tokenStart = tokenEnd = 0;
	}

	@Override
	public void end() {
		// set final offset
		final int finalOffset = correctOffset(tokenEnd);
		offsetAtt.setOffset(finalOffset, finalOffset);
	}
}
