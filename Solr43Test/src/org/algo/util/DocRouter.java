package org.algo.util;

import java.util.List;

import org.apache.solr.common.util.Hash;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

public class DocRouter {
	
	
	public static void main(String[] args) {
		List<String> servers = Lists.newArrayList("server1", "server2", "server3", "server4", "server5");

		
	    int bucket = Hashing.consistentHash(Hashing.md5().hashString("someId"), servers.size());
	    System.out.println("First time routed to: " + servers.get(bucket));

	    // one of the back end servers is removed from the (middle of the) pool
	    servers.remove(1);

	    bucket = Hashing.consistentHash(Hashing.md5().hashString("someId"), servers.size());
	    System.out.println("Second time routed to: " + servers.get(bucket));
	}
}
