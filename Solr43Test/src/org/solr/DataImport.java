package org.solr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;

public class DataImport {

	private CloudSolrServer cloudSolrServer;
	private HttpSolrServer httpSolrServer;
	private String zkHost = "localhost:2181";
	private String defaultCollection = "collection1";
	final String solrUrl = "http://localhost:8080/solr/" + defaultCollection;
	private Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();;
	private int docBufferSize = 3000;

	final String JdbcDriver = "com.mysql.jdbc.Driver";
	final String dbUrl = "jdbc:mysql://localhost:3306/test?user=root&password=123456";
	private ArrayList<String> tebles = new ArrayList<String>();
	private HashMap<String, String> fieldList = new HashMap<String, String>();;
	private HashMap<Integer, String> fieldName = new HashMap<Integer, String>();
	private Connection conn;
	private Statement stmt;

	public DataImport() {
		initDataBase(dbUrl);
		initSolr(solrUrl);
		//字段转换
		fieldList.put("guid", "id");
	}
	
	public DataImport(String dburl,String SolrUrl) {
		initDataBase(dbUrl);
		initSolr(solrUrl);
		//字段转换
		fieldList.put("guid", "id");
	}

	
	public DataImport(String dburl,String zkHost, String defaultCollection) {
		initDataBase(dbUrl);
		initSolr(solrUrl);
		//字段转换
		fieldList.put("guid", "id");
	}

	private void log(Exception e) {
		System.err.println(e.getMessage());
	}

	public String getZkHost() {
		return zkHost;
	}

	public void setZkHost(String zkHost) {
		this.zkHost = zkHost;
	}

	
	//初始化solr
	private void initSolr(String url) {
		final HttpClient httpClient;
		boolean followRedirects = false;
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, 128);
		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, 32);
		params.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS, followRedirects);
		httpClient = HttpClientUtil.createClient(params);

		httpSolrServer = new HttpSolrServer(url, httpClient);
	}

	
	//获得数据库中所有表名
	private void initDataBase(String url) {
		try {
			Class.forName(JdbcDriver).newInstance();
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
			String query = "show tables";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				tebles.add(rs.getString(1));
			}

		} catch (ClassNotFoundException e) {
			log(e);
		} catch (InstantiationException e) {
			log(e);
		} catch (IllegalAccessException e) {
			log(e);
		} catch (SQLException e) {
			log(e);
		}

	}


	//从数据库初始化索引架构
	private void initSchema(ResultSet rs) {
		try {
			for (int n = 1, columnCount = rs.getMetaData().getColumnCount(); n <= columnCount; n++) {
				String ColumnName = rs.getMetaData().getColumnName(n);
				if (fieldList.containsKey(ColumnName))
					ColumnName = fieldList.get(ColumnName);
				fieldName.put(n, ColumnName);
			}
		} catch (SQLException e) {
			log(e);
		}
	}

	
	//将数据库记录转化为索引对象
	private void buildDocument(ResultSet rs) {
		try {
			while (rs.next()) {
				SolrInputDocument doc = new SolrInputDocument();
				for (int n = 1, length = rs.getMetaData().getColumnCount(); n <= length; n++) {
					doc.addField(fieldName.get(n), rs.getObject(n));
				}
				docs.add(doc);
				if (docs.size() > docBufferSize) {
					commitDocuments(docs);
					docs.clear();
				}
			}
			if (docs.size() > 0) {
				commitDocuments(docs);
				docs.clear();
			}

		} catch (SQLException e) {
			log(e);
		}
	}

	
	//提交索引对象到solr
	private void commitDocuments(final Collection<SolrInputDocument> docs) {
		try {
			System.out.println("do commit . . .");
			httpSolrServer.add(docs, docBufferSize);
		} catch (SolrServerException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}
	}

	public void DataImportHander(final String query) {
		try {
			ResultSet rs = stmt.executeQuery(query);
			initSchema(rs);
			buildDocument(rs);
		} catch (SQLException e) {
			log(e);
		}

	}

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {

		long tagTime = System.currentTimeMillis();

		String query = "select * from t_nipic_info";
		DataImport demoMysql = new DataImport();
		demoMysql.DataImportHander(query);
		System.out.println("process ok... "
				+ (System.currentTimeMillis() - tagTime));

	}
}
