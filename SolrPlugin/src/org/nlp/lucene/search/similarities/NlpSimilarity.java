package org.nlp.lucene.search.similarities;

import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.index.Norm;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.util.BytesRef;

public class NlpSimilarity extends DefaultSimilarity {
	  /** Implemented as <code>overlap / maxOverlap</code>. */
	  public float coord(int overlap, int maxOverlap) {
	    return overlap / (float)maxOverlap;
	  }

	  /** Implemented as <code>1/sqrt(sumOfSquaredWeights)</code>. */
	  public float queryNorm(float sumOfSquaredWeights) {
	    return (float)(1.0 / Math.sqrt(sumOfSquaredWeights));
	  }
	  
	  /** Implemented as
	   *  <code>state.getBoost()*lengthNorm(numTerms)</code>, where
	   *  <code>numTerms</code> is {@link FieldInvertState#getLength()} if {@link
	   *  #setDiscountOverlaps} is false, else it's {@link
	   *  FieldInvertState#getLength()} - {@link
	   *  FieldInvertState#getNumOverlap()}.
	   *
	   *  @lucene.experimental */
//	  @Override
//	  public void computeNorm(FieldInvertState state, Norm norm) {
//	    final int numTerms;
//	    if (discountOverlaps)
//	      numTerms = state.getLength() - state.getNumOverlap();
//	    else
//	      numTerms = state.getLength();
//	    norm.setByte(encodeNormValue(state.getBoost() * ((float) (1.0 / Math.sqrt(numTerms)))));
//	  }
	  /** Implemented as <code>sqrt(freq)</code>. */
	  @Override
	  public float tf(float freq) {
	    return (float)Math.sqrt(freq);
	  }
	    
	  /** Implemented as <code>1 / (distance + 1)</code>. */
	  @Override
	  public float sloppyFreq(int distance) {
	    return 1.0f / (distance + 1);
	  }
	  
	  /** The default implementation returns <code>1</code> */
	  @Override
	  public float scorePayload(int doc, int start, int end, BytesRef payload) {
	    return 1;
	  }

	  /** Implemented as <code>log(numDocs/(docFreq+1)) + 1</code>. */
	  @Override
	  public float idf(long docFreq, long numDocs) {
	    return (float)(Math.log(numDocs/(double)(docFreq+1)) + 1.0);
	  }
	    
	  /** 
	   * True if overlap tokens (tokens with a position of increment of zero) are
	   * discounted from the document's length.
	   */
	  protected boolean discountOverlaps = true;

	  /** Determines whether overlap tokens (Tokens with
	   *  0 position increment) are ignored when computing
	   *  norm.  By default this is true, meaning overlap
	   *  tokens do not count when computing norms.
	   *
	   *  @lucene.experimental
	   *
	   *  @see #computeNorm
	   */
	  public void setDiscountOverlaps(boolean v) {
	    discountOverlaps = v;
	  }

	  /**
	   * Returns true if overlap tokens are discounted from the document's length. 
	   * @see #setDiscountOverlaps 
	   */
	  public boolean getDiscountOverlaps() {
	    return discountOverlaps;
	  }

	@Override
	public String toString() {
		return "nlpSimilarity";
	}

}