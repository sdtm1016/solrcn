package org.solr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.algo.util.ConsistentHash;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.cloud.Slice;
import org.apache.solr.common.cloud.SolrZkClient;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * @author Administrator
 * 
 */
public class CreateIndex {
	static long startTime = 0;
	static long indexCount = 0;
	static long endTime;
	static long tagTime;
	final static Random rnd = new Random();

	static String Base_URL = "http://localhost:8983/solr/collection1";
	static String zkHost = "localhost:9983";
	static String defaultCollection = "collection1";
	static int zkClientTimeout = 60000;
	static int zkConnectTimeout = 5000;

	static ConsistentHash<String> shardPartion;
	static HashFunction hashFunction = Hashing.murmur3_128();
	static int numberOfReplicas = 0;

	static ArrayList<String> Dict = new ArrayList<String>();

	public static BitSet fromByteArray(byte[] bytes) {
		BitSet bits = new BitSet();
		for (int i = 0; i < bytes.length * 8; i++) {
			if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
				bits.set(i);
			}
		}
		return bits;
	}

	public static byte[] toByteArray(BitSet bits) {
		byte[] bytes = new byte[bits.length() / 8 + 1];
		for (int i = 0; i < bits.length(); i++) {
			if (bits.get(i)) {
				bytes[bytes.length - i / 8 - 1] |= 1 << (i % 8);
			}
		}
		return bytes;
	}

	public static void Status() {
		CloudSolrServer solrServer;
		try {
			solrServer = new CloudSolrServer(zkHost);
			solrServer.setDefaultCollection(defaultCollection);
			solrServer.setZkClientTimeout(zkClientTimeout);
			solrServer.setZkConnectTimeout(zkConnectTimeout);
			solrServer.connect();
			Collection<Slice> slices = solrServer.getZkStateReader().getClusterState().getSlices(defaultCollection);
			numberOfReplicas = slices.size();

			ArrayList<String> nodes = new ArrayList<String>();

			if (numberOfReplicas > 1) {
				for (Slice slice : slices) {
					System.out.println(slice.getName());
					nodes.add(slice.getName());
				}
			} else {
				nodes.add("shard1");
			}

			shardPartion = new ConsistentHash<String>(hashFunction, numberOfReplicas, nodes);
			// shardPartion.get(i);

			SolrZkClient zkClient = solrServer.getZkStateReader().getZkClient();

			BitSet bitSet = new BitSet();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void ConRun(int rps, int c) throws SolrServerException, IOException {
		ConcurrentUpdateSolrServer server;
		server = new ConcurrentUpdateSolrServer(Base_URL, 10000, 5);

		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

		int i = 0;
		for (i = 0; i < c; i++) {
			tagTime = System.currentTimeMillis();

			for (int j = 0; j < rps; j++) {

				SolrInputDocument doc = new SolrInputDocument();
				doc.setField("id", j + "[" + System.currentTimeMillis() + "]" + rnd.nextInt(99999999));
				doc.setField("title", "name" + (System.currentTimeMillis()));
				docs.add(doc);
			}
			server.add(docs);
			System.out.println("add data ok... " + (System.currentTimeMillis() - tagTime));
			docs.clear();
		}

		tagTime = System.currentTimeMillis();
		System.out.println("add docs ok... " + (System.currentTimeMillis() - tagTime));
		server.commit();
		System.out.println("commit ok... " + (System.currentTimeMillis() - tagTime));

		tagTime = System.currentTimeMillis();
		server.optimize();
		System.out.println("optimize ok... " + (System.currentTimeMillis() - tagTime));
		server.shutdown();
	}

	public static void MutilCore(int rps, int c) throws SolrServerException, IOException {
		HttpSolrServer server;
		server = new HttpSolrServer(Base_URL);

		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

		int i = 0;
		long count = 0;
		tagTime = System.currentTimeMillis();
		for (i = 0; i < c; i++) {
			int type = (int) (count / 10);
			for (int j = 0; j < rps; j++) {

				SolrInputDocument doc = new SolrInputDocument();
//				doc.setField("id", java.util.UUID.randomUUID());
				doc.setField("id", count);
				doc.setField("txt_en", "hello world");
				doc.setField("name_s", "name_" + type);
				doc.setDocumentBoost((float) (Math.sqrt(count)*10000));
				docs.add(doc);
				count++;
			}
			server.add(docs);
			System.out.format("add data [%s] ok... %s ms,%sper/sec\n", count, (System.currentTimeMillis() - tagTime),
					(count * 1000 / (System.currentTimeMillis() - tagTime)));
			docs.clear();
		}

		System.out.println("add docs ok... " + (System.currentTimeMillis() - tagTime));
		server.commit();
		System.out.println("commit ok... " + (System.currentTimeMillis() - tagTime));

		// tagTime = System.currentTimeMillis();
		// server.optimize();
		// System.out.println("optimize ok... " + (System.currentTimeMillis() -
		// tagTime));
		server.shutdown();
	}

	public static void CloudRun(int rps, int c) throws SolrServerException, IOException, InterruptedException {
		CloudSolrServer solrServer = new CloudSolrServer(zkHost);
		solrServer.setDefaultCollection(defaultCollection);
		solrServer.setZkClientTimeout(zkClientTimeout);
		solrServer.setZkConnectTimeout(zkConnectTimeout);
		solrServer.connect();
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		tagTime = System.currentTimeMillis();
		long count = 1;
		int j = 0;
		for (int i = 0; i < c; i++) {
			for (j = 0; j < rps; j++) {
				SolrInputDocument doc = new SolrInputDocument();
				doc.setField("id", count);
				doc.setDocumentBoost((float)count/200000);
//				System.out.println(doc.getDocumentBoost());
				// doc.setField("_shard_", "shard" + String.valueOf(((count %
				// Dict.size()) % 5000) + 1));
				// doc.setField("type_i", count % 7);
				// doc.setField("name_s",
				// "english name 锟叫伙拷锟斤拷锟今共和癸拷,english name 锟叫伙拷锟斤拷锟今共和癸拷");// +
				// System.currentTimeMillis());
				// doc.setField("name_s", Dict.get((int) (count %
				// Dict.size())));
				doc.setField("name_s", count % 5000);
				doc.setField("contnet_en", "hello wrold");
				doc.setField("_shard_", "shard" + (i % 6 + 1) );
				doc.setField("_shard_", "shard1");
				docs.add(doc);
				count++;
			}

			try {
				solrServer.add(docs);
				System.out.format("add data [%s] ok... %s ms,%sper/sec\n", count, (System.currentTimeMillis() - tagTime),
						(count * 1000 / (System.currentTimeMillis() - tagTime)));
			} catch (SolrServerException e) {
				System.out.println(e.getMessage());
				Thread.sleep(1000);
				solrServer.shutdown();
				solrServer = new CloudSolrServer(zkHost);
				i--;
				continue;
			} catch (IOException e) {
				Thread.sleep(1000);
				solrServer.shutdown();
				solrServer = new CloudSolrServer(zkHost);
				i--;
				continue;
			}
			j = j * (i + 1);
			docs.clear();
		}

		tagTime = System.currentTimeMillis();
		solrServer.commit();
		System.out.println("commit ok... " + (System.currentTimeMillis() - tagTime));

	}

	public static void MultiRun(int rps, int c, final int threadCounts) throws SolrServerException, IOException {

		ExecutorService pool = Executors.newFixedThreadPool(threadCounts);

		CloudSolrServer[] cloudSolrServer = new CloudSolrServer[threadCounts];
		for (int i = 0; i < threadCounts; i++) {
			cloudSolrServer[i] = new CloudSolrServer(zkHost);
			cloudSolrServer[i].setDefaultCollection("collection" + (i % 5));
			cloudSolrServer[i].setZkClientTimeout(zkClientTimeout);
			cloudSolrServer[i].setZkConnectTimeout(zkConnectTimeout);
		}

		tagTime = System.currentTimeMillis();
		for (int i = 0; i < c; i++) {
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			for (int j = 0; j < rps; j++) {

				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", java.util.UUID.randomUUID());
				doc.addField("type", i);
				doc.addField("name", "name" + System.currentTimeMillis());
				docs.add(doc);
			}
			pool.execute(new IndexThread(cloudSolrServer[(i % threadCounts)], docs));

			if (i % 10 == 0 && i > 10) {
				try {
					System.out.println("do sleep...");
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("start threads ok... " + (System.currentTimeMillis() - tagTime));
	}



	public static void init(String[] args) {
		if (args == null || args.length < 1)
			return;
		for (int i = 0; i < args.length; i++) {
			String v = String.valueOf(args[i].charAt(1));
			if ("c".equals(v)) {
				count = Integer.parseInt(args[i + 1]);
				i++;
			} else if ("r".equals(v)) {
				request = Integer.parseInt(args[i + 1]);
				i++;
			} else if ("u".equals(v)) {
				Base_URL = args[i + 1];
				i++;
			} else if ("t".equals(v)) {
				type = Integer.parseInt(args[i + 1]);
				i++;
			} else if ("m".equals(v)) {
				m = Integer.parseInt(args[i + 1]);
				i++;
			} else if ("z".equals(v)) {
				zkHost = args[i + 1];
				i++;
			}
		}

	}
	
	static int count = 200;
	static int request = 100000;
	static int type = 1;
	static int m = 1;
	
	public static void main(String[] args) throws Exception {
		init(args);
		switch (type) {
		case 0:
			MutilCore(request, count);
			break;
		case 1:
			CloudRun(request, count);
			break;
		case 2:
			MultiRun(request, count, m);
			break;
		default:
			CloudRun(request, count);
			break;
		}
	}
}

class IndexThread implements Runnable {
	private Collection<SolrInputDocument> docs;
	private CloudSolrServer cloudSolrServer;

	public IndexThread(CloudSolrServer cloudSolrServer, Collection<SolrInputDocument> docs) {
		this.docs = docs;
		this.cloudSolrServer = cloudSolrServer;
	}

	public void run() {
		long tagTime;
		try {
			tagTime = System.currentTimeMillis();
			if (CreateIndex.startTime == 0) {
				CreateIndex.startTime = tagTime;
			}
			cloudSolrServer.add(docs);
			CreateIndex.indexCount += 100000;
			System.out.println(Thread.currentThread().getName() + "index ok... " + (System.currentTimeMillis() - tagTime));

			System.out.format("run=%s,count=%s,avg=%s\n", System.currentTimeMillis() - CreateIndex.startTime, CreateIndex.indexCount, CreateIndex.indexCount
					* 1000 / (System.currentTimeMillis() - CreateIndex.startTime));

			// tagTime = System.currentTimeMillis();
			// cloudSolrServer.commit();
			// System.out.println(Thread.currentThread().getName() +
			// "commit ok... "
			// + (System.currentTimeMillis() - tagTime));
			//
			// tagTime = System.currentTimeMillis();
			// cloudSolrServer.optimize();
			// System.out.println(Thread.currentThread().getName() +
			// "optimize ok... "
			// + (System.currentTimeMillis() - tagTime));
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
