package org.solr.admin.util;

public class CollectionUtil {
	private String name;
	private int numShards;
	private int replicationFactor;
	private int maxShardsPerNode = 1;
	private String createNodeSet;
	private String configName;
	private String BASE_URL = "http://localhost:8983/solr";

	private final static String CREATE_URL = "/admin/collections?action=CREATE";

	private final static String RELOAD_URL = "/admin/collections?action=RELOAD";
	
	private final static String SPLIT_SHARD_URL = "/admin/collections?action=SPLITSHARD";
	
	private final static String DELETE_SHARD_URL = "/admin/collections?action=DELETESHARD";
	
	private final static String CREATE_COLLECTION_ALIAS_URL = "/admin/collections?action=CREATEALIAS";
	
	private final static String DELETE_COLLECTION_ALIAS_URL = "/admin/collections?action=DELETEALIAS";
	
	private static final String DELETE_COLLECTION_URL = "/admin/collections?action=DELETE";
	
	public CollectionUtil(String name, int numShards, int replicationFactor) {
		this.name = name;
		this.numShards = numShards;
		this.replicationFactor = replicationFactor;
	}

	public String createCollection() {

		StringBuilder sb = new StringBuilder(CREATE_URL);
		sb.insert(0,BASE_URL);
		sb.append("&name=" + name);
		sb.append("&numShards=" + numShards);
		sb.append("&replicationFactor=" + replicationFactor);
		if (!createNodeSet.isEmpty())
			sb.append("&createNodeSet=" + createNodeSet);
		if (!configName.isEmpty())
			sb.append("&collection.configName=" + configName);
		return sb.toString();
	}
	
	/**
	 * @param name
	 * @param numShards
	 * @param replicationFactor
	 * @param createNodeSet
	 * Allows defining the nodes to spread the new collection across.
	 * If not provided, the CREATE operation will create shard-replica spread across all live Solr nodes. 
	 * The format is a comma-separated list of node_names, 
	 * such as localhost:8983_solr,localhost:8984_solr,localhost:8985_solr.
	 * @param configName
	 * 
	 * @return
	 */
	public static String createCollection(String BASE_URL, String name, int numShards, int replicationFactor,String createNodeSet, String configName) {
		
		StringBuilder sb = new StringBuilder(CREATE_URL);
		sb.insert(0,BASE_URL);
		sb.append("&name=" + name);
		sb.append("&numShards=" + numShards);
		sb.append("&replicationFactor=" + replicationFactor);
		if (createNodeSet!=null && !createNodeSet.isEmpty())
			sb.append("&createNodeSet=" + createNodeSet);
		if (configName!=null && !configName.isEmpty())
			sb.append("&collection.configName=" + configName);
		return sb.toString();
	}

	
	/**
	 * @return
	 */
	public String reloadCollection(){
		StringBuilder sb = new StringBuilder(RELOAD_URL);
		sb.insert(0,BASE_URL);
		sb.append("&name=" + name);
		return sb.toString();
	}
	
	/**
	 * @param name
	 * @return
	 */
	public static String reloadCollection(String BASE_URL, String name){
		StringBuilder sb = new StringBuilder(RELOAD_URL);
		sb.insert(0,BASE_URL);
		sb.append("&name=" + name);
		return sb.toString();
	}

	/**
	 * @param shard
	 * @return
	 */
	public String splitShard(String shard){
		StringBuilder sb = new StringBuilder(SPLIT_SHARD_URL);
		sb.insert(0,BASE_URL);
		sb.append("&name=" + name);
		sb.append("&shard=" + shard);
		return sb.toString();
	}	
	
	/**
	 * @param name
	 * @param shard
	 * @return
	 */
	public static String splitShard(String BASE_URL, String name, String shard){
		StringBuilder sb = new StringBuilder(SPLIT_SHARD_URL);
		sb.insert(0,BASE_URL);
		sb.append("&name=" + name);
		sb.append("&shard=" + shard);
		return sb.toString();
	}	
	
	/**
	 * @param shard
	 * @return
	 */
	public String deleteShard(String shard) {
		StringBuilder sb = new StringBuilder(DELETE_SHARD_URL);
		sb.insert(0,BASE_URL);
		sb.append("&shard=" + shard);
		sb.append("&collection=" + name);
		return sb.toString();
	}

	/**
	 * @param name
	 * @param shard
	 * @return
	 */
	public static String deleteShard(String BASE_URL, String name, String shard) {
		StringBuilder sb = new StringBuilder(DELETE_SHARD_URL);
		sb.insert(0,BASE_URL);
		sb.append("&shard=" + shard);
		sb.append("&collection=" + name);
		return sb.toString();
	}	
	
	
	/**
	 * @param aliasName
	 * @param collectionlist
	 * /admin/collections?action=CREATEALIAS&name=testalias&collections=anotherCollection,testCollection
	 * @return
	 */
	public String createCollectionAlias(String aliasName, String collectionlist){
		StringBuilder sb = new StringBuilder(CREATE_COLLECTION_ALIAS_URL);
		sb.insert(0,BASE_URL);
		sb.append("&name=" + aliasName);
		sb.append("&collections=" + collectionlist);
		return sb.toString();
	}
	
	
	public String deleteCollectionAlias(){
		StringBuilder sb = new StringBuilder(DELETE_COLLECTION_ALIAS_URL);
		sb.insert(0,BASE_URL);
		sb.append("&name=" + name);
		return sb.toString();
	}
	
	/**
	 * @param name
	 * @return
	 */
	public static String deleteCollectionAlias(String BASE_URL, String name){
		StringBuilder sb = new StringBuilder(DELETE_COLLECTION_ALIAS_URL);
		sb.insert(0,BASE_URL);
		sb.append("&name=" + name);
		return sb.toString();
	}	
	
	public String delteCollection(){
		StringBuilder sb = new StringBuilder(DELETE_COLLECTION_URL);
		sb.insert(0,BASE_URL);
		sb.append("&name=" + name);
		return sb.toString();
	}
	
	/**
	 * @param name
	 * @return
	 */
	public static String delteCollection(String BASE_URL, String name){
		StringBuilder sb = new StringBuilder(DELETE_COLLECTION_URL);
		sb.insert(0,BASE_URL);
		sb.append("&name=" + name);
		return sb.toString();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumShards() {
		return numShards;
	}

	public void setNumShards(int numShards) {
		this.numShards = numShards;
	}

	public int getReplicationFactor() {
		return replicationFactor;
	}

	public void setReplicationFactor(int replicationFactor) {
		this.replicationFactor = replicationFactor;
	}

	public int getMaxShardsPerNode() {
		return maxShardsPerNode;
	}

	public void setMaxShardsPerNode(int maxShardsPerNode) {
		this.maxShardsPerNode = maxShardsPerNode;
	}

	public String getCreateNodeSet() {
		return createNodeSet;
	}

	public void setCreateNodeSet(String createNodeSet) {
		this.createNodeSet = createNodeSet;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}
	
	@Override
	public String toString() {
		
		return "{"
		+ "\"name\":\"" + name + "\","
		+ "\"numShards\":\"" + numShards + "\","
		+ "\"replicationFactor\":\"" + replicationFactor + "\","
		+ "\"maxShardsPerNode\":\"" + maxShardsPerNode + "\","
		+ "\"createNodeSet\":\"" + createNodeSet + "\","
		+ "\"configName\":\"" + configName + "\","
		+ "}";
	}

}
