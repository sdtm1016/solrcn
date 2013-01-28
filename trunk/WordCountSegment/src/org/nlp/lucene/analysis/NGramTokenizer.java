package org.nlp.lucene.analysis;


import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeSource;
import org.nlp.algo.Bloom;

/**
 * Tokenizes the input into n-grams of the given size(s).
 */
public final class NGramTokenizer extends Tokenizer {
  public static final int DEFAULT_MIN_NGRAM_SIZE = 1;
  public static final int DEFAULT_MAX_NGRAM_SIZE = 2;
  public static String DEFAULT_MODEL_FILE = "model";

  private Bloom bloom;
  private String modelFile;
  
  private int minGram, maxGram;
  private int gramSize;
  private int pos;
  private int inLen; // length of the input AFTER trim()
  private int charsRead; // length of the input
  private String inStr;
  private boolean started;
  
  private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
  private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

  /**
   * Creates NGramTokenizer with given min and max n-grams.
   * @param input {@link Reader} holding the input to be tokenized
   * @param minGram the smallest n-gram to generate
   * @param maxGram the largest n-gram to generate
   */
  public NGramTokenizer(Reader input, int minGram, int maxGram, String modelFile) {
    super(input);
    init(minGram, maxGram, modelFile);
  }
 
  
  /**
   * Creates NGramTokenizer with given min and max n-grams.
   * @param input {@link Reader} holding the input to be tokenized
   * @param minGram the smallest n-gram to generate
   * @param maxGram the largest n-gram to generate
   */
  public NGramTokenizer(Reader input, int minGram, int maxGram) {
    super(input);
    init(minGram, maxGram);
  }

  /**
   * Creates NGramTokenizer with given min and max n-grams.
   * @param source {@link AttributeSource} to use
   * @param input {@link Reader} holding the input to be tokenized
   * @param minGram the smallest n-gram to generate
   * @param maxGram the largest n-gram to generate
   */
  public NGramTokenizer(AttributeSource source, Reader input, int minGram, int maxGram) {
    super(source, input);
    init(minGram, maxGram);
  }

  /**
   * Creates NGramTokenizer with given min and max n-grams.
   * @param factory {@link org.apache.lucene.util.AttributeSource.AttributeFactory} to use
   * @param input {@link Reader} holding the input to be tokenized
   * @param minGram the smallest n-gram to generate
   * @param maxGram the largest n-gram to generate
   */
  public NGramTokenizer(AttributeFactory factory, Reader input, int minGram, int maxGram) {
    super(factory, input);
    init(minGram, maxGram);
  }

  /**
   * Creates NGramTokenizer with default min and max n-grams.
   * @param input {@link Reader} holding the input to be tokenized
   */
  public NGramTokenizer(Reader input) {
    this(input, DEFAULT_MIN_NGRAM_SIZE, DEFAULT_MAX_NGRAM_SIZE);
  }
  
  private void init(int minGram, int maxGram, String modelFile) {
	    if (minGram < 1) {
	      throw new IllegalArgumentException("minGram must be greater than zero");
	    }
	    if (minGram > maxGram) {
	      throw new IllegalArgumentException("minGram must not be greater than maxGram");
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
  
  private void init(int minGram, int maxGram) {
	  this.init(minGram,maxGram,DEFAULT_MODEL_FILE);
  }

  /** Returns the next token in the stream, or null at EOS. */
  @Override
  public boolean incrementToken() throws IOException {
    clearAttributes();
    if (!started) {
      started = true;
      gramSize = minGram;
      char[] chars = new char[1024];
      charsRead = 0;
      // TODO: refactor to a shared readFully somewhere:
      while (charsRead < chars.length) {
        int inc = input.read(chars, charsRead, chars.length-charsRead);
        if (inc == -1) {
          break;
        }
        charsRead += inc;
      }
      inStr = new String(chars, 0, charsRead).trim();  // remove any trailing empty strings 

      if (charsRead == chars.length) {
        // Read extra throwaway chars so that on end() we
        // report the correct offset:
        char[] throwaway = new char[1024];
        while(true) {
          final int inc = input.read(throwaway, 0, throwaway.length);
          if (inc == -1) {
            break;
          }
          charsRead += inc;
        }
      }

      inLen = inStr.length();
      if (inLen == 0) {
        return false;
      }
    }

    if (pos+gramSize > inLen) {            // if we hit the end of the string
      pos = 0;                           // reset to beginning of string
      gramSize++;                        // increase n-gram size
      if (gramSize > maxGram)            // we are done
        return false;
      if (pos+gramSize > inLen)
        return false;
    }

    int oldPos = pos;
    pos++;
    termAtt.setEmpty().append(inStr, oldPos, oldPos+gramSize);
    offsetAtt.setOffset(correctOffset(oldPos), correctOffset(oldPos+gramSize));
    if (!bloom.contains(termAtt.toString()))
    		return false;
    System.out.format("termAtt:%s,%s,%s\n", termAtt.toString(),oldPos,oldPos+gramSize);
    return true;
  }
  
  @Override
  public void end() {
    // set final offset
    final int finalOffset = correctOffset(charsRead);
    this.offsetAtt.setOffset(finalOffset, finalOffset);
  }    
  
  @Override
  public void reset() throws IOException {
    super.reset();
    started = false;
    pos = 0;
  }
}
