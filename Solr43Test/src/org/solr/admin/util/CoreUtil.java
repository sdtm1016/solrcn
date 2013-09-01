package org.solr.admin.util;

/**
 * @author longkeyy
 * 
 */
public class CoreUtil {

	private String collection = "collection1";
	private String shard = "shard1";
	private String name = collection + "_" + shard + "_core1";
	private String instanceDir = name;
	private String dataDir = "data";
	private String config = "solrconfig.xml";
	private String schema = "schema.xml";
	private String BASE_URL = "http://localhost:8983/solr";
	
	private String defalutInstanceDir = name;
	private String defaultDataDir = "data";
	private String defaultConfig = "solrconfig.xml";
	private String defaultSchema = "schema.xml";
	
	
	private final static String CREATE_URL = "/admin/cores?wt=json&indexInfo=false&action=CREATE";

	private final static String UNLOAD_URL = "/admin/cores?wt=json&action=UNLOAD";

	/**
	 * @param name
	 */
	CoreUtil(String name) {
		this.name = name;
	}

	/**
	 * @param name
	 * @param shard
	 * @param collection
	 */
	public CoreUtil(String name, String shard, String collection) {
		this.collection = collection;
		this.shard = shard;
		this.name = name;
	}

	public String createCore() {

		StringBuilder sb = new StringBuilder(CREATE_URL);
		sb.insert(0,BASE_URL);
		sb.append("&name=" + name);
		sb.append("&instanceDir=" + instanceDir);
		sb.append("&dataDir=" + dataDir);
		sb.append("&config=" + config);
		sb.append("&schema=" + schema);
		sb.append("&collection=" + collection);
		sb.append("&shard=" + shard);

		return sb.toString();
	}

	public String createCore(String BASE_URL, String name) {

		StringBuilder sb = new StringBuilder(CREATE_URL);
		sb.insert(0,BASE_URL);
		sb.append("&name=" + name);
		sb.append("&instanceDir=" + instanceDir);
		sb.append("&dataDir=" + dataDir);
		sb.append("&config=" + config);
		sb.append("&schema=" + schema);
		sb.append("&collection=" + collection);
		sb.append("&shard=" + shard);

		return sb.toString();
	}	
	
	public String unloadCore() {
		return unloadCore(false,false);
	}		
	
	public static String unloadCore(String BASE_URL, String name){
		return unloadCore(BASE_URL, name, false,false);
	}
	
	public String unloadCore(boolean deleteDataDir,boolean deleteIndex) {
		StringBuilder sb = new StringBuilder(UNLOAD_URL);
		sb.insert(0,BASE_URL);
		sb.append("&core=" + name);
		sb.append("&deleteDataDir=" + deleteDataDir);
		sb.append("&deleteIndex=" + deleteIndex);
		return sb.toString();
	}	
	
	public static String unloadCore(String BASE_URL,String name, boolean deleteDataDir,boolean deleteIndex) {
		StringBuilder sb = new StringBuilder(UNLOAD_URL);
		sb.insert(0,BASE_URL);
		sb.append("&core=" + name);
		sb.append("&deleteDataDir=" + deleteDataDir);
		sb.append("&deleteIndex=" + deleteIndex);
		return sb.toString();
	}
	
	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getShard() {
		return shard;
	}

	public void setShard(String shard) {
		this.shard = shard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstanceDir() {
		return instanceDir;
	}

	public void setInstanceDir(String instanceDir) {
		this.instanceDir = instanceDir;
	}

	public String getDataDir() {
		return dataDir;
	}

	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	public void setBaseUrl(String baseUrl) {
		this.BASE_URL = baseUrl;
	}

	public String getBaseUrl() {
		return BASE_URL;
	}

	@Override
	public String toString() {
		return "{"
		+ "\"name\":\"" + name + "\","
		+ "\"collection\":\"" + collection + "\","
		+ "\"shard\":\"" + shard + "\","
		+ "\"instanceDir\":\"" + instanceDir + "\","
		+ "\"dataDir\":\"" + dataDir + "\","
		+ "\"config\":\"" + config + "\","
		+ "\"schema\":\"" + schema + "\""
		+ "}";
	}

}
