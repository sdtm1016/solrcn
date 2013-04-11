MemcachedCache instead of solr queryresultCache (default LRUCache)

config in solrconfig.xml to use solr-memcache

add newSearcher and firstSearcher Listener, such as:

<listener event="newSearcher" class="solr.MemcachedCache" />
<listener event="firstSearcher" class="solr.MemcachedCache" />

use listener only for get index version, to create memcached key

indexVersion is static long field of MemcachedCache.java.

//originalKey is QueryResultKey
memcached key = keyPrefix+indexVersion+"-"+originalKey.hashCode() 

<!--
MemcachedCache params:

memcachedHosts (required), "," split.
name (optional) no default.
expTime (optional) default 1800 s (= 30 minute)
defaultPort (optional) default 11211
keyPrefix (optional) default ""

-->

<queryResultCache
	class="solr.MemcachedCache"
	memcachedHosts="192.168.0.100,192.168.0.101:1234,192.168.0.103"
	expTime="21600"
	defaultPort="11511"
	keyPrefix="shard-1-"/>
	
dep jar:
	memcached-2.2.jar
	spy-2.4.jar
	
solr-memcache.patch for solr 1.3

if download and unzip to d:/apache-solr-1.3.0

copy patch-build.xml and solr-memcache.patch to (d:/apache-solr-1.3.0)

	D:\apache-solr-1.3.0>ant -f patch-build.xml -Dpatch.file=solr-memcache.patch
	Buildfile: patch-build.xml
	
	apply-patch:
	    [patch] patching file src/java/org/apache/solr/search/DocSet.java
	
	BUILD SUCCESSFUL
	Total time: 0 seconds

if exist d:/apache-solr-1.3.0/contrib/solr-memcache, if no exist you can unzip solr-memcache.zip to that dir

and dist

	D:\apache-solr-1.3.0>ant dist
	...
	
look D:\apache-solr-1.3.0\dist\apache-solr-memcache-1.3.0.jar

