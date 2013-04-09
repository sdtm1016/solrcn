package org.nlp.lucene.search.similarities;

import org.apache.lucene.search.similarities.Similarity;
import org.apache.solr.schema.SimilarityFactory;

public class NlpSimilarityFactory extends SimilarityFactory {
	public Similarity getSimilarity() {
		return new NlpSimilarity();
	}
}