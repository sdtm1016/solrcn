package org.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.algo.util.ConsistentHash;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.cloud.Slice;
import org.apache.solr.common.cloud.SolrZkClient;
import org.apache.solr.common.cloud.ZkStateReader;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class testCloud {
	public static final Logger LOGGER = LoggerFactory.getLogger(testCloud.class);
	static ConsistentHash<String> shardPartion;
	static Set<String> liveNodes;
	static HashFunction hashFunction = Hashing.murmur3_32();
	static int numberOfReplicas = 0;
	static String zkHost = "192.168.0.50:2181";
	static String collection = "collection1";
	static CloudSolrServer cloudSolrServer;
	static ZooKeeper zooKeeper;
	static SolrZkClient solrZkClient;

	final static String clusterstate = "/clusterstate.json";
	final static String livenodes = "/live_nodes";

	static {
		try {
			cloudSolrServer = new CloudSolrServer(zkHost);
			cloudSolrServer.setDefaultCollection(collection);
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void init(){
		cloudSolrServer.shutdown();
		cloudSolrServer.connect();
		solrZkClient = cloudSolrServer.getZkStateReader().getZkClient();
		zooKeeper = solrZkClient.getSolrZooKeeper();
		try {
			solrZkClient.exists(ZkStateReader.CLUSTER_STATE, createWatcher(), true);
			solrZkClient.getChildren(ZkStateReader.LIVE_NODES_ZKNODE, createWatcher(), true);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Watcher createWatcher() {
		return new Watcher() {

			public void process(WatchedEvent event) {
				LOGGER.warn("process : " + event.toString());
				System.err.println("process : " + event.toString());				
				switch (event.getType()) {
				case NodeDataChanged:
					try {
						System.out.println(new String(zooKeeper.getData(event.getPath(), this, new Stat())));
//						zooKeeper.exists(event.getPath(), this);
						updatePartion();

					} catch (KeeperException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;

				case NodeChildrenChanged:
					try {
						zooKeeper.getChildren(event.getPath(), this);
						updatePartion();
					} catch (KeeperException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
				default:
					break;
				}
			}
		};
	}

	
	ArrayList<HttpSolrServer> httpSolrServers;
	public static void checkLive(){
		HttpSolrServer httpSolrServer = new HttpSolrServer(collection);
		try {
			httpSolrServer.ping();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void updatePartion() {
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		init();
		Collection<Slice> slices = cloudSolrServer.getZkStateReader().getClusterState().getSlices(collection);
		numberOfReplicas = slices.size();
		cloudSolrServer.getZkStateReader().getClusterState().getSlices(collection);
		liveNodes = cloudSolrServer.getZkStateReader().getClusterState().getLiveNodes();
		if (liveNodes.size() > 0) {
			for (String liveNode : liveNodes) {
				System.out.println(liveNode);
			}
		} else {
			System.out.println("no liveNode");
		}
		ArrayList<String> nodes = new ArrayList<String>();

		if (numberOfReplicas > 0) {
			for (Slice slice : slices) {
				System.out.println(slice.getName());
				nodes.add(slice.getName());
			}
		} else {
			nodes.add("shard1");
			System.out.println("no slice");
		}
		shardPartion = new ConsistentHash<String>(hashFunction, 1024, nodes);
	}

	public static void main(String[] args) throws Exception {

		// zooKeeper = new ZooKeeper(zkHost, 20000, new Watcher() {
		// // 监控所有被触发的事件
		// public void process(WatchedEvent event) {
		// switch (event.getType()) {
		// case NodeDataChanged:
		// LOGGER.warn("process : " + event.toString());
		// System.err.println("process : " + event.toString());
		// System.out.println(event.getPath());
		// try {
		// zooKeeper.exists(event.getPath(), this);
		// } catch (KeeperException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// break;
		// default:
		// LOGGER.warn("process : " + event.toString());
		// System.err.println("process : " + event.getType());
		// break;
		// }
		// }
		// });

		// registerPath(ZkStateReader.CLUSTER_STATE);
		// registerPath(ZkStateReader.LIVE_NODES_ZKNODE);
		// registerPath(ZkStateReader.ALIASES);
		// ZooKeeper zooKeeper = new ZooKeeper(zkHost, 300000, watcher);
		// zooKeeper.exists(ZkStateReader.LIVE_NODES_ZKNODE, true);
		// zooKeeper.exists(ZkStateReader.CLUSTER_STATE, true);

		// ZooKeeper zooKeeper = new ZooKeeper(zkHost, 300000, watcher, true);
		// zooKeeper.getData(ZkStateReader.CLUSTER_STATE, true, new Stat());
		// new ZooKeeper(zkHost, 300000, watcher);
		// Status();
		// Collection<SolrInputDocument> docs = new
		// ArrayList<SolrInputDocument>();
		// SolrInputDocument doc = new SolrInputDocument();
		// doc.addField("id", 1);
		// docs.add(doc);
		// cloudSolrServer.add(docs);
		while (true) {
//			int i = 0;
			Thread.sleep(1000);
//			if (i%10 == 0){
//				updatePartion();
//			}
		}
	}
}
