package org.nlp.lucene.analysis;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.nlp.algo.Bloom;

public final class NGramTokenFilter extends TokenFilter {
	public static final int DEFAULT_MIN_NGRAM_SIZE = 1;
	public static final int DEFAULT_MAX_NGRAM_SIZE = 2;
	public static String DEFAULT_MODEL_FILE = "model";

	private int minGram, maxGram;
	private String modelFile;
	private Bloom bloom;

	private char[] curTermBuffer;
	private int curTermLength;
	private int curGramSize;
	private int curPos;
	private int tokStart;
	private int tokEnd; // only used if the length changed before this filter
	private boolean hasIllegalOffsets; // only if the length changed before this
										// filter

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

	
	/**
	 * Creates NGramTokenFilter with given min and max n-grams.
	 * 
	 * @param input
	 *            {@link TokenStream} holding the input to be tokenized
	 * @param minGram
	 *            the smallest n-gram to generate
	 * @param maxGram
	 *            the largest n-gram to generate
	 */
	public NGramTokenFilter(TokenStream input, int minGram, int maxGram, String modelFile) {
		super(input);
		if (minGram < 1) {
			throw new IllegalArgumentException(
					"minGram must be greater than zero");
		}
		if (minGram > maxGram) {
			throw new IllegalArgumentException(
					"minGram must not be greater than maxGram");
		}
		
		if (modelFile == null || modelFile.isEmpty()) {
			this.modelFile = DEFAULT_MODEL_FILE;
		} else {
			this.modelFile = modelFile;
		}			
		
		this.minGram = minGram;
		this.maxGram = maxGram;
		bloom = new Bloom(this.modelFile);		
	}
	
	/**
	 * Creates NGramTokenFilter with given min and max n-grams.
	 * 
	 * @param input
	 *            {@link TokenStream} holding the input to be tokenized
	 * @param minGram
	 *            the smallest n-gram to generate
	 * @param maxGram
	 *            the largest n-gram to generate
	 */
	public NGramTokenFilter(TokenStream input, int minGram, int maxGram) {
		this(input, minGram, maxGram,DEFAULT_MODEL_FILE);
	}

	/**
	 * Creates NGramTokenFilter with default min and max n-grams.
	 * 
	 * @param input
	 *            {@link TokenStream} holding the input to be tokenized
	 */
	public NGramTokenFilter(TokenStream input) {
		this(input, DEFAULT_MIN_NGRAM_SIZE, DEFAULT_MAX_NGRAM_SIZE);
	}

	/** Returns the next token in the stream, or null at EOS. */
	@Override
	public final boolean incrementToken() throws IOException {
		while (true) {
			if (curTermBuffer == null) {
				if (!input.incrementToken()) {
					return false;
				} else {
					curTermBuffer = termAtt.buffer().clone();
					curTermLength = termAtt.length();
					curGramSize = minGram;
					curPos = 0;
					tokStart = offsetAtt.startOffset();
					tokEnd = offsetAtt.endOffset();
					// if length by start + end offsets doesn't match the term
					// text then assume
					// this is a synonym and don't adjust the offsets.
					hasIllegalOffsets = (tokStart + curTermLength) != tokEnd;
				}
			}
			while (curGramSize <= maxGram) {
				while (curPos + curGramSize <= curTermLength) { // while there
																// is input
					clearAttributes();
					termAtt.copyBuffer(curTermBuffer, curPos, curGramSize);
					if (hasIllegalOffsets) {
						offsetAtt.setOffset(tokStart, tokEnd);
					} else {
						offsetAtt.setOffset(tokStart + curPos, tokStart
								+ curPos + curGramSize);
					}
					curPos++;
					return bloom.contains(termAtt.toString());
				}
				curGramSize++; // increase n-gram size
				curPos = 0;
			}
			curTermBuffer = null;
		}
	}

	@Override
	public void reset() throws IOException {
		super.reset();
		curTermBuffer = null;
	}
}
