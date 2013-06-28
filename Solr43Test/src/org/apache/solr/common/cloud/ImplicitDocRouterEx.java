package org.apache.solr.common.cloud;

import java.util.Collection;
import java.util.Collections;

import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.Hash;

/**
 * This document router is for custom sharding
 */
public class ImplicitDocRouterEx extends DocRouter {
	public static final String NAME = "implicit";

	protected String getId(SolrInputDocument sdoc, SolrParams params) {
		Object idObj = sdoc.getFieldValue("id"); // blech
		String id = idObj != null ? idObj.toString() : "null"; // should only
																// happen on
																// client side
		return id;
	}

	@Override
	public Slice getTargetSlice(String id, SolrInputDocument sdoc, SolrParams params, DocCollection collection) {
		String shard = null;
		if (sdoc != null) {
			Object o = sdoc.getFieldValue("_shard_");
			if (o != null) {
				shard = o.toString();
			}
		}

		if (shard == null) {
			shard = params.get("_shard_");
		}

		if (shard != null) {
			Slice slice = collection.getSlice(shard);
			if (slice == null) {
				if (id == null)
					id = getId(sdoc, params);
				Collection<Slice> slices = collection.getSlices();
				Slice[] s = new Slice[slices.size()];
				slices.toArray(s);
				Hash.murmurhash3_x86_32(id, 0, id.length(), 0);
//				throw new SolrException(SolrException.ErrorCode.BAD_REQUEST, "No _shard_=" + shard + " in " + collection);
			}
			return slice;
		}

		return null; // no shard specified... use default.
	}

	@Override
	public boolean isTargetSlice(String id, SolrInputDocument sdoc, SolrParams params, String shardId, DocCollection collection) {
		// todo : how to handle this?
		return false;
	}

	@Override
	public Collection<Slice> getSearchSlicesSingle(String shardKey, SolrParams params, DocCollection collection) {
		if (shardKey == null) {
			return collection.getActiveSlices();
		}

		// assume the shardKey is just a slice name
		Slice slice = collection.getSlice(shardKey);
		if (slice == null) {
			throw new SolrException(SolrException.ErrorCode.BAD_REQUEST, "implicit router can't find shard " + shardKey + " in collection "
					+ collection.getName());
		}

		return Collections.singleton(slice);
	}

}
