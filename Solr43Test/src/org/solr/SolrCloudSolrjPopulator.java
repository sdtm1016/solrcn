package org.solr;

import java.io.IOException;
import java.util.Random;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class SolrCloudSolrjPopulator {

	/**
	 * @param numShards
	 * @param obj
	 * @return
	 */
	public static String partion(int numShards, Object obj) {

		return "shard" + (obj.hashCode() % numShards + 1);

	}

	/**
	 * @param numShards
	 * @return
	 */
	public static String partion(int numShards) {

		int min = 1;
		int max = numShards;
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return "shard" + s;

	}

	public static void partion(int numShards, SolrInputDocument doc, String fieldName) {
		doc.setField("_shard_", doc.get(fieldName).hashCode() % numShards + 1);
	}

	public static void main(String[] args) throws IOException, SolrServerException {
		final String zkHost = "localhost:9983";
		final String defaultCollection = "collection1";
		CloudSolrServer server = new CloudSolrServer(zkHost);
		server.setDefaultCollection(defaultCollection);
		server.connect();
		int numShards = server.getZkStateReader().getClusterState().getSlices(defaultCollection).size();

		for (int i = 0; i < 1000; ++i) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.setField("cat", "book");
			doc.setField("id", "book-" + i);
			doc.setField("_shard_", "shard1");
			doc.addField("name", "The Legend of Po part " + i);
			server.add(doc);
			if (i % 100 == 0)
				server.commit(); // periodically flush
		}
		server.commit();
	}
}