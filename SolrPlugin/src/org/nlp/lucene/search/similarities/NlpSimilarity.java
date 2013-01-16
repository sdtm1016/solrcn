package org.nlp.lucene.search.similarities;

import org.apache.lucene.search.similarities.DefaultSimilarity;

public class NlpSimilarity extends DefaultSimilarity {
	@Override
	public float idf(long docFreq, long numDocs) {
		return 1.0F;
	}

	@Override
	public float queryNorm(float sumOfSquaredWeights) {
		return 1.0F;
	}

	@Override
	public float tf(float freq) {
		return 1.0F;
	}

	@Override
	public float coord(int overlap, int maxOverlap) {
		return 1.0F;
	}

	@Override
	public String toString() {
		return "nlpSimilarity";
	}

}