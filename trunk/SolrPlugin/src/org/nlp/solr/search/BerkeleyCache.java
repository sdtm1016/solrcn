package org.nlp.solr.search;

import java.io.File;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.apache.solr.search.CacheRegenerator;
import org.apache.solr.search.SolrCache;
import org.apache.solr.search.SolrCacheBase;
import org.apache.solr.search.SolrIndexSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

public class BerkeleyCache<K, V> extends SolrCacheBase implements SolrCache<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = LoggerFactory.getLogger(BerkeleyCache.class.getName());
	// private StoredSortedMap<String, String> map;
	StoredSortedMap<String, Object> map;

	private Environment dbEnvironment;
	private Database db;
	private Database store;
	private DatabaseConfig dbConfig;
	private String cachePath = "cache";
	private final String databaseName = "Cache";
	private String keyPrefix = "c";
	private static String indexVersion = "0.0";

	// private static int memCacheSize = 10240000;

	public <EnvironmenConfig> void initMap() {

		try {			
			EnvironmentConfig envConfig = new EnvironmentConfig();

			envConfig.setTransactional(false);
			envConfig.setAllowCreate(true);
			File envDir = new File(this.cachePath);
			if (!envDir.exists()){
				envDir.mkdir();
			}
			// envConfig.setCacheSize(memCacheSize);
			envConfig.setConfigParam("je.maxMemoryPercent", "10");
			envConfig.setConfigParam("je.evictor.criticalPercentage", "10");
			envConfig.setConfigParam("je.log.fileMax", "104857600");
			envConfig.setConfigParam("je.log.useNIO", "true");
			dbEnvironment = new Environment(envDir, envConfig);
			dbConfig = new DatabaseConfig();
			dbConfig.setAllowCreate(true);
			dbConfig.setTransactional(false);
			dbConfig.setSortedDuplicates(false);

			db = dbEnvironment.openDatabase(null, databaseName, dbConfig);
			// cacheConfig = dbEnvironment.openDatabase(null, databaseName,
			// dbConfig);
			// version = dbEnvironment.openDatabase(null, keyPrefix, dbConfig);
			// StoredClassCatalog catalog = new StoredClassCatalog(db);
			TupleBinding<String> keyBinding = TupleBinding.getPrimitiveBinding(String.class);
			SerialBinding<Object> valueBinding = new SerialBinding<Object>(new StoredClassCatalog(db), Object.class);
			store = dbEnvironment.openDatabase(null, databaseName, dbConfig);
			map = new StoredSortedMap<String, Object>(store, keyBinding, valueBinding, true);
			getCacheVersion();
			dbEnvironment.cleanLog();

			// dbEnvironment.syncReplication();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setCacheVersion() {

		map.put(keyPrefix, (Object) indexVersion);
		// OperationStatus status = version.put(null, new
		// DatabaseEntry(keyPrefix.getBytes()), new
		// DatabaseEntry(indexVersion.getBytes()));
		// if (status == OperationStatus.SUCCESS){
		// return true;
		// }else{
		// return false;
		// }
	}

	private void getCacheVersion() {

		// indexVersion = (String) map.get(keyPrefix) == null ? "0.0" : (String)
		// map.get(keyPrefix);

		String version = (String) map.get(keyPrefix);
		if (version == null) {
			setCacheVersion();
		} else {
			indexVersion = version;
			log.info("getCacheVersion " + indexVersion);
		}

		// DatabaseEntry value = new DatabaseEntry();
		// OperationStatus status = version.get(null, new
		// DatabaseEntry(keyPrefix.getBytes()), value, LockMode.DEFAULT);
		// if (status == OperationStatus.KEYEXIST) {
		// indexVersion = new String(value.getData());
		// }else{
		// setCacheVersion();
		// }
	}

	/*
	 * An instance of this class will be shared across multiple instances of an
	 * LRUCache at the same time. Make sure everything is thread safe.
	 */
	private static class CumulativeStats {
		AtomicLong lookups = new AtomicLong();
		AtomicLong hits = new AtomicLong();
		AtomicLong inserts = new AtomicLong();
		AtomicLong evictions = new AtomicLong();
	}

	private CumulativeStats stats;

	// per instance stats. The synchronization used for the map will also be
	// used for updating these statistics (and hence they are not AtomicLongs
	private long lookups;
	private long hits;
	private long inserts;
	private long evictions;

	private long warmupTime = 0;

	private String description = "Berkeley Cache";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object init(java.util.Map args, Object persistence, CacheRegenerator regenerator) {
		super.init(args, regenerator);

		String name = (String) args.get("name");
		if(name!=null){
			cachePath += name;
		}
		System.out.println(name+"............................................");
		
		String prefix = (String) args.get("keyPrefix");
		if (prefix != null) {
			keyPrefix = prefix;
		}

//		String path = (String) args.get("cachePath");
//		if (path != null) {
//			cachePath = path;
//		}
		description = generateDescription(keyPrefix, cachePath);

		// init map
		initMap();
		// exampleEnv.syncReplication();

		dbEnvironment.cleanLog();

		if (persistence == null) {
			// must be the first time a cache of this type is being created
			persistence = new CumulativeStats();
		}

		stats = (CumulativeStats) persistence;

		return persistence;
	}

	/**
	 * 
	 * @return Returns the description of this cache.
	 */
	private String generateDescription(String prefix, String path) {
		String description = this.description + "(keyPrefix=" + prefix + ", cachePath=" + path;
		if (isAutowarmingOn()) {
			description += ", " + getAutowarmDescription();
		}
		description += ')';
		return description;
	}

	@Override
	public int size() {
		// synchronized (map) {
		return map.size();
		// }
	}

	@Override
	public V put(K key, V value) {
		// synchronized (map) {
		if (getState() == State.LIVE) {
			stats.inserts.incrementAndGet();
		}

		// increment local inserts regardless of state???
		// it does make it more consistent with the current size...
		inserts++;
		// if (map == null) {
		// synchronized (this) {
		// if (map == null) {
		// initMap();
		// }
		// }
		// }
		return (V) map.put(toKeyString(key), value);
		// }
	}

	// private static final String KBlank = "";
	private String toKeyString(Object key) {
		return keyPrefix + indexVersion + "-" + key.hashCode();
	}

	@Override
	public V get(K key) {
		// synchronized (map) {

		System.out.println(toKeyString(key));
		log.info(toKeyString(key));
		if (map == null || map.size() == 0) {
			return null;
		}
		Object val = map.get(toKeyString(key));
		if (getState() == State.LIVE) {
			// only increment lookups and hits if we are live.
			lookups++;
			stats.lookups.incrementAndGet();
			if (val != null) {
				hits++;
				stats.hits.incrementAndGet();
			}
		}
		return (V) val;
		// }
	}

	@Override
	public void clear() {
		// synchronized (map) {
		// map.clear();
		// }
	}

	@Override
	public void warm(SolrIndexSearcher searcher, SolrCache<K, V> old) {
		indexVersion = searcher.getVersion();
		// Set<String> keySet = map.keySet();
		// for (String k : keySet) {
		// if (!k.startsWith(keyPrefix + indexVersion)) {
		// map.remove(k);
		// }
		// }
		// dbEnvironment.cleanLog();
		setCacheVersion();
		SortedMap<String, Object> subMap = map.subMap(keyPrefix + "0.0", keyPrefix + indexVersion);
		Set<String> keySet = subMap.keySet();
		for (String string : keySet) {
			System.out.println("removeing ..." + string);
			map.remove(string);
		}
		log.info("warm index version=" + indexVersion + "....................................................................");
	}

	@Override
	public void close() {
		store.close();
		db.close();
		dbEnvironment.cleanLog();
		dbEnvironment.close();
	}

	// ////////////////////// SolrInfoMBeans methods //////////////////////

	@Override
	public String getName() {
		return BerkeleyCache.class.getName();
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getSource() {
		return "$URL: https://www.solr.cc $";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public NamedList<Comparable> getStatistics() {
		NamedList lst = new SimpleOrderedMap();
		// synchronized (map) {
		lst.add("lookups", lookups);
		lst.add("hits", hits);
		lst.add("hitratio", calcHitRatio(lookups, hits));
		lst.add("inserts", inserts);
		lst.add("evictions", evictions);
		lst.add("size", map.size());
		// }
		lst.add("warmupTime", warmupTime);

		long clookups = stats.lookups.get();
		long chits = stats.hits.get();
		lst.add("cumulative_lookups", clookups);
		lst.add("cumulative_hits", chits);
		lst.add("cumulative_hitratio", calcHitRatio(clookups, chits));
		lst.add("cumulative_inserts", stats.inserts.get());
		lst.add("cumulative_evictions", stats.evictions.get());

		return lst;
	}

	@Override
	public String toString() {
		return name() + getStatistics().toString();
	}

	public static void main(String[] args) {
		BerkeleyCache<String, String> cache = new BerkeleyCache<String, String>();
		cache.initMap();
		cache.close();

	}
}
