package org.nlp.db;

import java.util.HashMap;

import org.nlp.dictionary.InitDictionary;
import org.nlp.dictionary.PartOne;

public class testDBD {

	public static void main(String[] argv) throws Exception {
		HashMap<String, PartOne> WordMap = new HashMap<String, PartOne>();
		HashMap<String, PartOne> wm = new HashMap<String, PartOne>();
		InitDictionary dic = new InitDictionary();
		dic.loadFile("data/SogouLabDic.dic");
		WordMap = dic.getFirstWordMap();		
		BDB bdb;
		byte[] readObj;
		bdb = new BDB();
		bdb.setUp("d:/bdb", 102400000);
		bdb.open("FirstWordMap");
		System.out.println("WordMaplen:"+WordMap.size());
//		bdb.put("wmc".getBytes("UTF-8"), compressObject.writeCompressObject(WordMap));
		
//		HashMap<String, PartOne> wm1 = (HashMap<String, PartOne>) compressObject.readCompressObject(bdb.get("wmc".getBytes("UTF-8")));
//		System.out.println("wm1:"+wm1.size());
		
		bdb.putObj("wm3", WordMap);
//		wm = (HashMap<String, PartOne>) compressObject.readCompressObject(bdb.getbyte("wm3"));
		bdb.putObjZip("wm2", WordMap);
		wm = (HashMap<String, PartOne>) bdb.getObj("wm3");
		System.out.println(wm.size());
		
		
		bdb.close();
		
		
		
		
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		ObjectOutputStream oos = new ObjectOutputStream(baos);
//		oos.writeObject(WordMap);
//		oos.close();
//
//		byte[] array = baos.toByteArray();
//		System.out.println(array.length);
//		BDB bdb = new BDB();
//		bdb.setUp("d:/bdb", 10240000);
//		bdb.open("FirstWordMap");
//		bdb.put("FirstWordMap".getBytes("UTF-8"), array);
		
//		byte[] readObj = bdb.get("FirstWordMap".getBytes("UTF-8"));
		
//		bdb.close();
//		long start = System.currentTimeMillis();
//		bdb = new BDB();
//		bdb.setUp("d:/bdb", 10240000);
//		bdb.open("FirstWordMap");
//		readObj = bdb.get("FirstWordMap".getBytes("UTF-8"));
//		
//		System.out.println(System.currentTimeMillis() - start);
//		System.out.println(readObj.length);

	}
}
