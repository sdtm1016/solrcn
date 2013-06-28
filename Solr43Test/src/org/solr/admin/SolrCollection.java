package org.solr.admin;

public class SolrCollection {
	String name = "collection1";
	int numShards = 1;
	int replicationFactor = 1;
	String configName;
	int maxShardsPerNode = 48;
	String createNodeSetStr;

	public void CREATE(String name, int numShards, int replicationFactor,String configName) {

	}

	public void DELETE() {

	}

	public void RELOAD(String name) {

	}

	public void SYNCSHARD(String collection, String shard) {

	}

	public void CREATEALIAS() {

	}

	public void DELETEALIAS() {

	}

	public void SPLITSHARD() {

	}
}
