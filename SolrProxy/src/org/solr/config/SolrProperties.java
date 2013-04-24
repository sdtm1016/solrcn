package org.solr.config;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;

public class SolrProperties {
	final private String configFile = "solr.properties";
	private LinkedHashMap<String, String> SolrMap = new LinkedHashMap<String, String>();
	public static SolrProperties solrProperties = new SolrProperties();

	public static SolrProperties getInstance() {
		return solrProperties;
	}

	private SolrProperties() {
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream(configFile));
			for (Iterator<Object> it = properties.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				String value = properties.getProperty(key);
				SolrMap.put(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getSelectUrl(String key) {
		if (key ==null || key.isEmpty()){
			return "http://localhost:8983/solr/collection1/select";
		}
		return SolrMap.get(key) + "/select";
	}

}
