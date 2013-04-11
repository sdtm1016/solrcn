package org.nlp.solr.search;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.valuesource.FieldCacheSource;

import redis.clients.jedis.Jedis;

public class RedisValueSource extends FieldCacheSource {


	public RedisValueSource(String field) {
		super(field);
		final Jedis jedis = new Jedis("localhost");
		// TODO Auto-generated constructor stub
	}

	@Override
	public FunctionValues getValues(Map context,
			AtomicReaderContext readerContext) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}



}
