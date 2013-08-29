package org.solr.admin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.common.cloud.Replica;
import org.apache.solr.common.cloud.Slice;
import org.apache.solr.common.params.CoreAdminParams.CoreAdminAction;

public class CoreAdmin {

	final static String CoreCreate = "/admin/cores?wt=json&indexInfo=false&action=CREATE&name=:name&instanceDir=:instanceDir&dataDir=:dataDir&config=:config&schema=:schema&collection=:collection&shard=:shard";

	final static String CoreUnload =  "/admin/cores?wt=json&action=UNLOAD&core=:core&deleteDataDir=:deleteDataDir&deleteIndex=:deleteIndex";
	
	public void CoreAdminHandler(CoreAdminAction action) {

	}
	
	
	public static String HttpGet(final String url){
		HttpClient httpClient = HttpClientUtil.createClient(null);
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(httpget);
			InputStream content = response.getEntity().getContent();
			Header contentEncoding = response.getEntity().getContentEncoding();
			if(contentEncoding==null){
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();				
				while ((num = content.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				byte[] b = baos.toByteArray();
				baos.flush();
				baos.close();				
				return new String(b, "UTF-8");
			}else{
				System.out.println(contentEncoding.getValue());
			}
			
			System.out.println(response.getEntity().getContentType().toString().split("charset="));
			
			System.out.println(content);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

	
	public static String DeleteCore(final String baseUrlForNodeName,final String core){
		String CoreUnload =  "/admin/cores?wt=json&action=UNLOAD&core=:core&deleteDataDir=:deleteDataDir&deleteIndex=:deleteIndex";
		String CmdUrl = baseUrlForNodeName + CoreUnload.replace(":core", core).replace(":deleteDataDir", "true").replace(":deleteIndex", "true");
		HttpClient httpClient = HttpClientUtil.createClient(null);
		System.out.println(CmdUrl);
		HttpGet httpget = new HttpGet(CmdUrl);
		try {
			HttpResponse response = httpClient.execute(httpget);
			InputStream content = response.getEntity().getContent();
			Header contentEncoding = response.getEntity().getContentEncoding();
			if(contentEncoding==null){
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();				
				while ((num = content.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				byte[] b = baos.toByteArray();
				baos.flush();
				baos.close();				
				return new String(b, "UTF-8");
			}else{
				System.out.println(contentEncoding.getValue());
			}
			
			System.out.println(response.getEntity().getContentType().toString().split("charset="));
			
			System.out.println(content);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

	
	public static void main(String[] args) throws MalformedURLException {
//		String zkHost = "localhost:2181";
//		CloudSolrServer server = new CloudSolrServer(zkHost);
//		 new CloudSolrServer(zkHost, null);
//		server.connect();
//		Set<String> liveNodes = server.getZkStateReader().getClusterState().getLiveNodes();
//		
//		Set<String> collections = server.getZkStateReader().getClusterState().getCollections();
//		for (String collection : collections) {
//			Collection<Slice> activeSlices = server.getZkStateReader().getClusterState().getActiveSlices(collection);
//			
//			for (Slice slice : activeSlices) {
//				if(slice.getReplicas().size()>0){
//					Iterator<Replica> it = slice.getReplicas().iterator();
//					while(it.hasNext()){
//						Replica next = it.next();
//						String nodeName = next.getNodeName();
//						String name = (String) next.getProperties().get("core");
//						System.out.println("Corename:"+name);
//						String baseUrlForNodeName = server.getZkStateReader().getZkClient().getBaseUrlForNodeName(nodeName);
//						System.out.println(DeleteCore(baseUrlForNodeName,name));
//					}
//					
//				}
//			}
//			
//		}
		
//		for (String liveNode : liveNodes) {
//			String baseUrlForNodeName = server.getZkStateReader().getZkClient().getBaseUrlForNodeName(liveNode);
//			System.out.println(baseUrlForNodeName);
//		}
		
		String[] nodes = new String[]{"http://localhost:8983/solr","http://localhost:8984/solr","http://localhost:8985/solr","http://localhost:8986/solr"};
		
//		
		for (int i = 0; i < nodes.length; i++) {
			System.out.println(nodes[i]);
		}
		
		int totleDays = 8;
		int collectionsPerDay = 24;
		int numShards = 4;
		int counts = collectionsPerDay*numShards*totleDays;
		
		for (int i = 0; i < counts; i++) {
			String collection = "collection" + (i/numShards+1);
			String shard = "shard" + (i % numShards + 1);
			String name = collection + "_" + shard + "_core" + (i % numShards + 1);
			String instanceDir = name;
			String dataDir = "data";
			String config = "solrconfig.xml";
			String schema = "schema.xml";
			
			String coreConf = CoreCreate.replace(":name", name)
					.replace(":instanceDir", instanceDir)
					.replace(":dataDir", dataDir).replace(":config", config)
					.replace(":schema", schema).replace(":collection", collection)
					.replace(":shard", shard);
			System.out.format("create:%s,%s,%s\n",collection,shard,name);
			String CreateCmd = nodes[(i%4)] + coreConf;
			
			String text = HttpGet(CreateCmd);
			System.out.println(text);
		}
		

	}

}
