package org.solr;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PostHttp {

	public static void main(String[] args) {
		
		ArrayList<HashMap<String,String>> maps = new ArrayList<HashMap<String,String>>();		
		HashMap<String,String> map = new HashMap<String,String>();
		
		map.put("id", "100");
		map.put("name", "longkeyy");
		
		maps.add(new HashMap<String,String>(map));
		
		map.put("id", "200");
		map.put("name", "netcat");
		
		maps.add(new HashMap<String,String>(map));
				
		Gson gson = new Gson();
		String json = gson.toJson(maps);
		
		
		Type type = new TypeToken<ArrayList<HashMap<String,String>>>(){}.getType();
		
		ArrayList<HashMap<String,String>> fromJson = gson.fromJson(json, type);
		
		HashMap<String, String> hashMap = fromJson.get(1);
		System.out.println(hashMap.get("id"));
		
		System.out.println(fromJson.size());
		
		
		
		
		System.out.println(json);
		
	}
}
