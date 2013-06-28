package org.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

/**
 * @author Administrator
 * 
 */
public class CreateIndexBCP {
	static String Base_URL = "http://localhost:8983/solr/collection1";

	public static void MutilCore() throws SolrServerException, IOException {
		HttpSolrServer server;
		server = new HttpSolrServer(Base_URL);

		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", "01234567890123456789"); //dataID
		doc.setField("bcpContent_t", "中华人民共\t和国成立\t于1949\t年10月1日\t");	
		doc.setField("file_s", "/run/data/xxxxx.zip");

		server.add(doc);
		server.commit();

		server.shutdown();
	}

	public static void main(String[] args) throws Exception {
		MutilCore();
	}

}
