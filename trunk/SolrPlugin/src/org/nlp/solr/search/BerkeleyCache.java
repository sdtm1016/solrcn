package org.nlp.solr.search;

import java.io.File;
import java.io.Serializable;

import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.solr.common.SolrException;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.apache.solr.search.CacheRegenerator;
import org.apache.solr.search.QueryResultKey;
import org.apache.solr.search.SolrCache;
import org.apache.solr.search.SolrCacheBase;
import org.apache.solr.search.SolrIndexSearcher;

public class BerkeleyCache<K, V> extends SolrCacheBase implements SolrCache<K,V>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private StoredSortedMap<String, String> map; //原锟斤拷锟侥达拷锟斤拷
	 StoredMap<QueryResultKey, Object> map; // 希锟斤拷某锟斤拷锟斤拷锟�

	private Environment exampleEnv;
	private Database myClassDb;
	private Database store;
	private DatabaseConfig dbConfig;
	private String DataDir = "z:/";
	private final String databaseName = "Cache";
	
	public BerkeleyCache() {

	}
	
	public BerkeleyCache(String DataDir) {
		this.DataDir = DataDir;
		
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setTransactional(false);
		envConfig.setAllowCreate(true);
		File envDir = new File(this.DataDir);
		// 锟铰斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		exampleEnv = new Environment(envDir, envConfig);
		
		dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		dbConfig.setTransactional(false);
		// 锟斤拷锟斤拷锟斤拷锟芥储锟斤拷锟斤拷息锟斤拷锟斤拷菘锟�
		// 锟斤拷锟斤拷锟芥储锟斤拷锟斤拷息锟斤拷锟斤拷菘锟斤拷锟捷库，锟斤拷要锟斤拷锟杰癸拷锟芥储锟截革拷锟侥关硷拷锟斤拷
		dbConfig.setSortedDuplicates(false);

		myClassDb = exampleEnv.openDatabase(null, databaseName, dbConfig);
	}

	@SuppressWarnings("unchecked")
	public <EnvironmenConfig> void initMap() {

		try {
			
			EnvironmentConfig envConfig = new EnvironmentConfig();
			envConfig.setTransactional(false);
			envConfig.setAllowCreate(true);
			File envDir = new File(this.DataDir);
			exampleEnv = new Environment(envDir, envConfig);
			
			dbConfig = new DatabaseConfig();
			dbConfig.setAllowCreate(true);
			dbConfig.setTransactional(false);
			dbConfig.setSortedDuplicates(false);

			myClassDb = exampleEnv.openDatabase(null, databaseName, dbConfig);
			// 锟斤拷始锟斤拷catalog锟斤拷
			StoredClassCatalog catalog = new StoredClassCatalog(myClassDb);

//			TupleBinding<K> keyBinding = TupleBinding.getPrimitiveBinding((Class<K>)String.class);
			
			SerialBinding<QueryResultKey> keyBinding = new SerialBinding<QueryResultKey>(catalog, QueryResultKey.class);
			// 锟斤拷value锟斤拷为锟斤拷锟斤拷锟斤拷锟斤拷谢锟斤拷锟绞斤拷娲�
			SerialBinding<Object> valueBinding = new SerialBinding<Object>(catalog, Object.class);
			store = exampleEnv.openDatabase(null, databaseName, dbConfig);
			// 锟斤拷锟斤拷锟斤拷荽娲⒂筹拷锟�
			// StoredSortedMap<String, String> map = new StoredSortedMap<String,
			// String>(store, keyBinding, valueBinding, true); //原锟斤拷锟侥达拷锟斤拷
			map = new StoredSortedMap<QueryResultKey, Object>(store, keyBinding, valueBinding, true); // 希锟斤拷锟斤拷锟斤拷某锟斤拷锟斤拷锟�

			// 锟酵放伙拷锟斤拷锟斤拷锟斤拷
			// exampleEnv.syncReplication();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


//	public void set(K key, V value) {
//		if(map == null){
//		    synchronized(this){
//		        if(map == null){
//		        	initMap(key,value);
//		        }
//		    }
//		}
//		map.put(key, value);
//	}
//
//	public V get(String key) {
//		return  map.get(key);
//	}

	 /* An instance of this class will be shared across multiple instances
	   * of an LRUCache at the same time.  Make sure everything is thread safe.
	   */
	  private static class CumulativeStats {
	    AtomicLong lookups = new AtomicLong();
	    AtomicLong hits = new AtomicLong();
	    AtomicLong inserts = new AtomicLong();
	    AtomicLong evictions = new AtomicLong();
	  }

	  private CumulativeStats stats;

	  // per instance stats.  The synchronization used for the map will also be
	  // used for updating these statistics (and hence they are not AtomicLongs
	  private long lookups;
	  private long hits;
	  private long inserts;
	  private long evictions;

	  private long warmupTime = 0;

	  
	  private String description="LRU Cache";

	  @Override
	  public Object init(java.util.Map args, Object persistence, CacheRegenerator regenerator) {
	    super.init(args, regenerator);
	    String str = (String)args.get("size");
	    final int limit = str==null ? 1024 : Integer.parseInt(str);
	    str = (String)args.get("initialSize");
	    final int initialSize = Math.min(str==null ? 1024 : Integer.parseInt(str), limit);
	    description = generateDescription(limit, initialSize);

	    //init bdb
	    initMap();
		
	    if (persistence==null) {
	      // must be the first time a cache of this type is being created
	      persistence = new CumulativeStats();
	    }

	    stats = (CumulativeStats)persistence;

	    return persistence;
	  }

	  /**
	   * 
	   * @return Returns the description of this cache. 
	   */
	  private String generateDescription(int limit, int initialSize) {
	    String description = "LRU Cache(maxSize=" + limit + ", initialSize=" + initialSize;
	    if (isAutowarmingOn()) {
	      description += ", " + getAutowarmDescription();
	    }
	    description += ')';
	    return description;
	  }

	  @Override
	  public int size() {
	    synchronized(map) {
	      return map.size();
	    }
	  }

	  @Override
	  public V put(K key, V value) {
	    synchronized (map) {
	      if (getState() == State.LIVE) {
	        stats.inserts.incrementAndGet();
	      }

	      // increment local inserts regardless of state???
	      // it does make it more consistent with the current size...
	      inserts++;
		
	      return (V) map.put((QueryResultKey)key,value);
	    }
	  }

	  @Override
	  public V get(K key) {
//	    synchronized (map) {
		  
		  System.out.println();
		  System.out.println(key.getClass().getName());
		  System.out.println(key.toString());
		  System.out.println()
		  ;
		  if(map.size()==0){
			  return null;
		  }
	      Object val = map.get(key);
	      if (getState() == State.LIVE) {
	        // only increment lookups and hits if we are live.
	        lookups++;
	        stats.lookups.incrementAndGet();
	        if (val!=null) {
	          hits++;
	          stats.hits.incrementAndGet();
	        }
	      }
	      return (V) val;
//	    }
	  }

	  @Override
	  public void clear() {
	    synchronized(map) {
	      map.clear();
	    }
	  }

	  @Override
	  public void warm(SolrIndexSearcher searcher, SolrCache<K,V> old) {
//	    if (regenerator==null) return;
//	    long warmingStartTime = System.currentTimeMillis();
//	    BerkeleyCache<K,V> other = (BerkeleyCache<K,V>)old;
//
//	    // warm entries
//	    if (isAutowarmingOn()) {
//	      Object[] keys,vals = null;
//
//	      // Don't do the autowarming in the synchronized block, just pull out the keys and values.
//	      synchronized (other.map) {
//	        
//	        int sz = autowarm.getWarmCount(other.map.size());
//	        
//	        keys = new Object[sz];
//	        vals = new Object[sz];
//
//	        Iterator<Map.Entry<K, V>> iter = other.map.entrySet().iterator();
//
//	        // iteration goes from oldest (least recently used) to most recently used,
//	        // so we need to skip over the oldest entries.
//	        int skip = other.map.size() - sz;
//	        for (int i=0; i<skip; i++) iter.next();
//
//
//	        for (int i=0; i<sz; i++) {
//	          Map.Entry<K,V> entry = iter.next();
//	          keys[i]=entry.getKey();
//	          vals[i]=entry.getValue();
//	        }
//	      }
//
//	      // autowarm from the oldest to the newest entries so that the ordering will be
//	      // correct in the new cache.
//	      for (int i=0; i<keys.length; i++) {
//	        try {
//	          boolean continueRegen = regenerator.regenerateItem(searcher, this, old, keys[i], vals[i]);
//	          if (!continueRegen) break;
//	        }
//	        catch (Throwable e) {
//	          SolrException.log(log,"Error during auto-warming of key:" + keys[i], e);
//	        }
//	      }
//	    }
//
//	    warmupTime = System.currentTimeMillis() - warmingStartTime;
	  }


	  @Override
	  public void close() {
			store.close();
			myClassDb.close();
			exampleEnv.close();
	  }


	  //////////////////////// SolrInfoMBeans methods //////////////////////


	  @Override
	  public String getName() {
	    return LRUCache.class.getName();
	  }

	  @Override
	  public String getDescription() {
	     return description;
	  }

	  @Override
	  public String getSource() {
	    return "$URL: https://svn.apache.org/repos/asf/lucene/dev/branches/lucene_solr_4_1/solr/core/src/java/org/apache/solr/search/LRUCache.java $";
	  }

	  @Override
	  public NamedList getStatistics() {
	    NamedList lst = new SimpleOrderedMap();
	    synchronized (map) {
	      lst.add("lookups", lookups);
	      lst.add("hits", hits);
	      lst.add("hitratio", calcHitRatio(lookups,hits));
	      lst.add("inserts", inserts);
	      lst.add("evictions", evictions);
	      lst.add("size", map.size());
	    }
	    lst.add("warmupTime", warmupTime);
	    
	    long clookups = stats.lookups.get();
	    long chits = stats.hits.get();
	    lst.add("cumulative_lookups", clookups);
	    lst.add("cumulative_hits", chits);
	    lst.add("cumulative_hitratio", calcHitRatio(clookups,chits));
	    lst.add("cumulative_inserts", stats.inserts.get());
	    lst.add("cumulative_evictions", stats.evictions.get());
	    
	    return lst;
	  }

	  @Override
	  public String toString() {
	    return name() + getStatistics().toString();
	  }
	  
	public static void main(String[] args) {
		BerkeleyCache<String, String> cache = new BerkeleyCache<String, String>("z:/");
		cache.put("key1", "data1");
		cache.put("key1", "data2");
		System.out.println(cache.get("key1"));
		cache.close();

	}

}
