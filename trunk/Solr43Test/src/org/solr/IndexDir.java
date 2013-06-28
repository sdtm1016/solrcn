package org.solr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class IndexDir {
	private CloudSolrServer server;
	private String zkHost = "localhost:9080";
	private String collection = "collection1";
	private long count = 0;
	private long addTime = 0;
	private ArrayList<String> fileList = new ArrayList<String>();

	IndexDir() {
		getSolrServer();
	}

	IndexDir(String zkHost, String collection) {
		this.zkHost = zkHost;
		this.collection = collection;
		getSolrServer();
	}

	private void getSolrServer() {
		try {
			server = new CloudSolrServer(zkHost);
			server.setDefaultCollection(collection);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private void serachFiles(String dir) {

		File root = new File(dir);

		File[] filesOrDirs = root.listFiles();

		for (int i = 0; i < filesOrDirs.length; i++) {
			if (filesOrDirs[i].isDirectory()) {
				serachFiles(filesOrDirs[i].getAbsolutePath());
			} else {
				fileList.add(filesOrDirs[i].getAbsolutePath());
			}
		}

	}

	List<String> AnalyzeFile(String pathname) {
		List<String> result = new ArrayList<String>();

		try {
			BufferedReader br;
			br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "UTF-8"));
			String line = "";
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public void CreateIndex(String dir) throws SolrServerException, IOException {
		long elapsedTime = 0;
		long persec = 10000;
		serachFiles(dir);
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < fileList.size(); i++) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileList.get(i)), "UTF-8"));
			String line = "";
			
			while ((line = br.readLine()) != null) {
				SolrInputDocument doc = new SolrInputDocument();
				doc.setField("id", java.util.UUID.randomUUID());
				String[] content = line.split("	");
				for (int k = 0,len = content.length; k < len; k++) {
					if (k < 22) {
						doc.setField("data" + k + "_cjk", content[k]);
					} else {
						doc.addField("text", content[k]);
					}

				}
				docs.add(doc);
				count++;
				if (docs.size() > persec) {
					long useTime = server.add(docs).getElapsedTime();				
					persec = docs.size()*1000/useTime;
					System.out.format("add %s docs, use %s ms, avg %s per/sec\n",docs.size(),useTime,persec);
					docs.clear();
					addTime += useTime;
					
				}
			}

			
			if (docs.size() > 0) {
				elapsedTime = server.add(docs).getElapsedTime();
				System.out.format("add %s, use %s, avg %s per/sec\n",docs.size(),elapsedTime,docs.size()*1000/elapsedTime);
				addTime += elapsedTime;
			}
			
			br.close();
		}


		long endTime = System.currentTimeMillis();
		System.out.format("total use %s, index %s use %s ms, avg %s per/sec", endTime - startTime, count, addTime, count * 1000 / addTime);
		try {
			server.commit(true, true);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws SolrServerException, IOException {
		IndexDir id = new IndexDir();
		String dir = "e:/data";
		id.CreateIndex(dir);
	}

}
