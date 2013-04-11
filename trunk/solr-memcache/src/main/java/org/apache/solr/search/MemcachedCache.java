package org.apache.solr.search;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import net.spy.memcached.MemcachedClient;

import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.apache.solr.core.SolrCore;
import org.apache.solr.core.SolrEventListener;

/**
 * copy from LRUCache and modify, implement memcached cache for solr cache
 * 
 * @author chenlb2008@gmail.com (http://blog.chenlb.com) 2009-6-3 pm 03:06:30
 */
public class MemcachedCache implements SolrCache, SolrEventListener {
	public static Logger log = Logger.getLogger(MemcachedCache.class.getName());
	
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

	private static long indexVersion;
	private MemcachedClient memcachedClient;
	private int expTime = 30 * 60;	//30 minute
	
	private String name;
	private String[] memcachedHosts;
	private int defaultPort = 11211;
	private String keyPrefix = "";
	
	
	private State state;
	//private CacheRegenerator regenerator;
	private String description="Memcached Cache";

	public Object init(Map args, Object persistence, CacheRegenerator regenerator) {
		state=State.CREATED;
		//this.regenerator = regenerator;
		name = (String)args.get("name");
		String prefix = (String)args.get("keyPrefix");
		if(prefix != null) {
			keyPrefix = prefix;
		}
		
		String hosts = (String)args.get("memcachedHosts");
		if(hosts == null) {
			throw new RuntimeException("memcachedHosts must be set!");
		}
		memcachedHosts = hosts.split(",");
		String port = (String)args.get("defaultPort");
		if(port != null) {
			defaultPort = Integer.parseInt(port);
		}
		String exp = (String)args.get("expTime");
		if(exp != null) {
			expTime = Integer.parseInt(exp);
		}
		description = "MemcachedCache("+Arrays.toString(memcachedHosts)+", defaultPort:"+defaultPort+", expTime:"+expTime+", keyPrefix:"+keyPrefix+")";

		//init MemcachedClient
		InetSocketAddress[] nets = new InetSocketAddress[memcachedHosts.length];
		for(int i=0; i<memcachedHosts.length; i++) {
			String host = memcachedHosts[i];
			String[] net = host.split(":");
			int p = defaultPort;
			if(net.length > 1) {
				p = Integer.parseInt(net[1]);
			}
			nets[i] = new InetSocketAddress(net[0], p);
		}

		try {
			memcachedClient = new MemcachedClient(nets);
		} catch (IOException e) {
			throw new RuntimeException("new MemcachedClient I/O error", e);
		}
		
		if (persistence==null) {
			// must be the first time a cache of this type is being created
			persistence = new CumulativeStats();
		}

		stats = (CumulativeStats)persistence;

		return persistence;
	}

	public String name() {
		return name;
	}

	public int size() {
		return 0;
	}

	public Object put(Object key, Object value) {
		//synchronized (map) {
		if (state == State.LIVE) {
			stats.inserts.incrementAndGet();
		}

		// increment local inserts regardless of state???
		// it does make it more consistent with the current size...
		inserts++;
		memcachedClient.set(toKeyString(key), expTime, value);
		return value;
		//}
	}

	//private static final String KBlank = "";
	private String toKeyString(Object key) {
		return keyPrefix+indexVersion+"-"+key.hashCode();
	}
	
	public Object get(Object key) {
		//synchronized (map) {
		Object val = memcachedClient.get(toKeyString(key));
		if (state == State.LIVE) {
			// only increment lookups and hits if we are live.
			lookups++;
			stats.lookups.incrementAndGet();
			if (val!=null) {
				hits++;
				stats.hits.incrementAndGet();
			}
		}
		return val;
		//}
	}

	public void clear() {
		
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public void warm(SolrIndexSearcher searcher, SolrCache old) throws IOException {
		
	}

	private void readIndexVersion(SolrIndexSearcher searcher) {
		indexVersion = searcher.getReader().getVersion();
		log.info("read index version="+indexVersion);
	}
	
	public void close() {
		memcachedClient.shutdown();
		log.info("memcachedClient closed!");
	}


	//////////////////////// SolrInfoMBeans methods //////////////////////


	public String getName() {
		return MemcachedCache.class.getName();
	}

	public String getVersion() {
		return SolrCore.version;
	}

	public String getDescription() {
		return description;
	}

	public Category getCategory() {
		return Category.CACHE;
	}

	public String getSourceId() {
		return "$Id: MemcachedCache.java 2009-5-8 14:18:34 chenlb $";
	}

	public String getSource() {
		return "";
	}

	public URL[] getDocs() {
		return null;
	}


	// returns a ratio, not a percent.
	private static String calcHitRatio(long lookups, long hits) {
		if (lookups==0) return "0.00";
		if (lookups==hits) return "1.00";
		int hundredths = (int)(hits*100/lookups);   // rounded down
		if (hundredths < 10) return "0.0" + hundredths;
		return "0." + hundredths;

		/*** code to produce a percent, if we want it...
	    int ones = (int)(hits*100 / lookups);
	    int tenths = (int)(hits*1000 / lookups) - ones*10;
	    return Integer.toString(ones) + '.' + tenths;
		 ***/
	}

	public NamedList getStatistics() {
		NamedList lst = new SimpleOrderedMap();
		//synchronized (map) {
		lst.add("lookups", lookups);
		lst.add("hits", hits);
		lst.add("hitratio", calcHitRatio(lookups,hits));
		lst.add("inserts", inserts);
		lst.add("evictions", evictions);
		lst.add("size", size());
		// }

		lst.add("warmupTime", warmupTime);

		long clookups = stats.lookups.get();
		long chits = stats.hits.get();
		lst.add("cumulative_lookups", clookups);
		lst.add("cumulative_hits", chits);
		lst.add("cumulative_hitratio", calcHitRatio(clookups,chits));
		lst.add("cumulative_inserts", stats.inserts.get());
		lst.add("cumulative_evictions", stats.evictions.get());

		lst.add("indexVersion", indexVersion);
		return lst;
	}

/*	public String toString() {
		return name + getStatistics().toString();
	}*/

	@Override
	public void init(NamedList args) {
		
	}

	@Override
	public void newSearcher(SolrIndexSearcher newSearcher,
			SolrIndexSearcher currentSearcher) {
		
		log.info("old index version="+indexVersion+" ...");
		readIndexVersion(newSearcher);
	}

	@Override
	public void postCommit() {
		
	}
}
