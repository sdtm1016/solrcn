package org.nlp.solr.search;

import java.io.IOException;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.DocValues;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.search.FieldCache;

import redis.clients.jedis.Jedis;

public class RedisValueSource extends ValueSource {

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public DocValues getValues(Map context, AtomicReaderContext reader)
//			throws IOException {
//		String idField;
//		FieldCache.DEFAULT.getDocTermOrds(reader, field)
//		final String[] lookup = ((Object) FieldCache.DEFAULT).getStrings(reader, idField);
//		final Jedis jedis = new Jedis("localhost");
//		String redisValue;
//		String redisKey;
//		String v = jedis.hget(redisKey, redisValue);
//		final JSONObject obj;
//		if (v != null) {
//			obj = (JSONObject) JSONValue.parse(v);
//		} else {
//			obj = new JSONObject();
//		}
//		jedis.disconnect();
//		return new DocValues() {
//
//			@Override
//			public float floatVal(int doc) {
//				final String id = lookup[doc];
//				Object v = obj.get(id);
//				if (v != null) {
//					try {
//						return Float.parseFloat(v.toString());
//					} catch (NumberFormatException e) {
//						return 0;
//					}
//				}
//				return 0;
//			}
//
//			@Override
//			public int intVal(int doc) {
//				final String id = lookup[doc];
//				Object v = obj.get(id);
//				if (v != null) {
//					try {
//						return Integer.parseInt(v.toString());
//					} catch (NumberFormatException e) {
//						return 0;
//					}
//				}
//				return 0;
//			}
//
//			@Override
//			public String strVal(int doc) {
//				final String id = lookup[doc];
//				Object v = obj.get(id);
//				return v != null ? v.toString() : null;
//			}
//
//			@Override
//			public String toString(int doc) {
//				return strVal(doc);
//			}
//		};
//	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FunctionValues getValues(Map context,
			AtomicReaderContext readerContext) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
