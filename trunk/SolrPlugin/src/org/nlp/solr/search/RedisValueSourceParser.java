package org.nlp.solr.search;

import org.apache.lucene.queries.function.ValueSource;
import org.apache.solr.search.FunctionQParser;
import org.apache.solr.search.ValueSourceParser;

public class RedisValueSourceParser extends ValueSourceParser {

	public ValueSource parse(FunctionQParser fqp) {
		return new RedisValueSource();
	}

}