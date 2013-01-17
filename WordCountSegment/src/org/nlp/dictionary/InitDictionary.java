/**
 * 
 */
/**
 * 
 */
package org.nlp.dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import org.nlp.algo.compressObject;

/**
 * 核心字典类，双字哈希字典
 * 
 * @author Tony
 * 
 */
public class InitDictionary {

	private static HashMap<String, PartOne> firstWordMap = new HashMap<String, PartOne>();
	private HashMap<String, PartTwo> secendWordMap = new HashMap<String, PartTwo>();
	private long counter = 0;
	private static String DefaultDic = "SogouLabDic.dic";
	
	/** 词性,可以没有 */
	private String[] attri = { "null" };

	/** 词频 */
	private long freq = 1l;

	public InitDictionary() {
	}

	public void loadFile(String filePath) {

		try {
			InputStreamReader isr;
			File dic = new File(filePath);
			if (!dic.exists()) {
				InputStream fs = InitDictionary.class.getClassLoader()
						.getResourceAsStream(filePath);
				isr = new InputStreamReader(fs, "utf-8");
			} else {
				// System.out.println(filePath);
				FileInputStream fs = new FileInputStream(filePath);
				isr = new InputStreamReader(fs, "utf-8");
			}

			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				counter++;
				insertWord(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// write to disk.
//		writeDicToFile();
	}

	private void insertWord(String s) {
		String words = "";

		boolean singleWord = true;// 是否为一个词语，可能为单字
		String[] temp = s.split("\\t+");// "标准	146828897	N,ADJ" 对它进行切分

		if (temp.length == 2) {
			if (temp[0].length() == 1) {
				words = temp[0];
			} else {
				words = temp[0];
				singleWord = false;
			}
			freq = Long.parseLong(temp[1].trim());
		} else if (temp.length == 3) {
			if (temp[0].length() == 1) {
				words = temp[0];
			} else {
				words = temp[0];
				singleWord = false;
			}
			freq = Long.parseLong(temp[1].trim());
			attri = temp[2].trim().split(",");
		}
		// 处理单字的情况
		if (singleWord) {
			WordAttribute wordAttri = new WordAttribute(freq, attri);
			PartOne flag = new PartOne(false, null, wordAttri);
			firstWordMap.put(words, flag);
		} else {
			firstWord(words);
		}
	}

	/**
	 * 处理词语的第一个字，在firstWordMap里进行查找，如果没有记录则添加第一个字到firstWordMap里；
	 * 找到记录则在该单字对应的的secendWordMap里面查找词语的第二个字，在secendWordMap里没有找到
	 * 记录则添加第二个字到secendWordMap里面；如果在secendWordMap里面找到了第二个字的积累，则
	 * 在第二字对应的lastPart里面添加词语的剩余部分。
	 * 
	 * @param words
	 */
	private void firstWord(String words) {
		String firstWord;
		String leftWords;
		firstWord = words.substring(0, 1);

		// 处理字长为2的词语
		if (firstWordMap.containsKey(firstWord)) {
			if (words.length() == 2) {
				String secendWord = words.substring(1);
				WordAttribute wordAttri = new WordAttribute(freq, attri);
				if (firstWordMap.get(firstWord).getNextWord()
						.containsKey(secendWord)) {
					firstWordMap.get(firstWord).getNextWord().get(secendWord)
							.setFlag(true);
					firstWordMap.get(firstWord).getNextWord().get(secendWord)
							.setWordAttri(wordAttri);
					return;
				}
				PartTwo flagTwo = new PartTwo(true, null, wordAttri);
				firstWordMap.get(firstWord).getNextWord()
						.put(secendWord, flagTwo);
				return;
			}
			leftWords = words.substring(1);
			secendWord(firstWord, leftWords);

		} else {
			leftWords = words.substring(1);
			secendWord(firstWord, leftWords);
		}
	}

	/**
	 * 处理词语的第一个字，在firstWordMap里进行查找，如果没有记录则添加第一个字到firstWordMap里；
	 * 找到记录则在该单字对应的的secendWordMap里面查找词语的第二个字，在secendWordMap里没有找到
	 * 记录则添加第二个字到secendWordMap里面；如果在secendWordMap里面找到了第二个字的记录，则
	 * 在第二字对应的lastPart里面添加词语的剩余部分。
	 * 
	 * @param words
	 */
	private void secendWord(String firstWord, String words) {
		String secendWord;
		String leftWords;

		try {
			if (words.length() > 1) {
				secendWord = words.substring(0, 1);
				leftWords = words.substring(1);
				PartOne partOne = firstWordMap.get(firstWord);

				if (partOne == null) {
					WordAttribute wordAttri = new WordAttribute(freq, attri);
					LastPart lastPart = new LastPart(leftWords, wordAttri);
					// 把lastPart挂到secendPart上
					PartTwo flagTwo = new PartTwo(false, lastPart, null);

					secendWordMap = new HashMap<String, PartTwo>();
					secendWordMap.put(secendWord, flagTwo);
					PartOne flagOne = new PartOne(false, secendWordMap, null);
					firstWordMap.put(firstWord, flagOne);
					return;
				}

				if (partOne.getNextWord().containsKey(secendWord)) {
					WordAttribute wordAttri = new WordAttribute(freq, attri);
					LastPart lastPart = partOne.getNextWord().get(secendWord)
							.getLastPart();
					if (lastPart == null) {
						lastPart = new LastPart(leftWords, wordAttri);
						WordAttribute wt = partOne.getNextWord()
								.get(secendWord).getWordAttri();
						PartTwo flagTwo = new PartTwo(true, lastPart, wt);
						firstWordMap.get(firstWord).getNextWord()
								.put(secendWord, flagTwo);
					} else {
						lastPart.addRecord(leftWords, wordAttri);
					}
					return;
				} else {
					lastPart(firstWord, secendWord, leftWords);
				}
			} else {
				secendWord = words;
				WordAttribute wordAttri = new WordAttribute(freq, attri);
				PartTwo flagTwo = new PartTwo(true, null, wordAttri);
				secendWordMap = new HashMap<String, PartTwo>();
				secendWordMap.put(secendWord, flagTwo);

				PartOne flagOne = new PartOne(false, secendWordMap, null);
				firstWordMap.put(firstWord, flagOne);
			}
		} catch (Exception e) {
			System.out.println(counter);
			e.printStackTrace();
		}
	}

	private void lastPart(String firstWord, String secendWord, String leftWords) {
		WordAttribute wordAttri = new WordAttribute(freq, attri);
		LastPart lastPart = new LastPart(leftWords, wordAttri);
		// 把lastPart挂到secendPart上
		PartTwo flagTwo = new PartTwo(false, lastPart, null);
		firstWordMap.get(firstWord).getNextWord().put(secendWord, flagTwo);
	}

	/**
	 * 对象序列化操作，把词典对象firstWordMap保存到磁盘上
	 */
	public void writeDicToFile() {
		String fileName = "coreDic.dic";
		File dic = new File("data/" + fileName);
		try {
			if (!dic.exists()) {
				dic.createNewFile();
				ObjectOutputStream oos = new ObjectOutputStream(
						new FileOutputStream(dic));
				oos.writeObject(compressObject.writeCompressObject(firstWordMap));
//				oos.writeObject(firstWordMap);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取被保存在磁盘上的序列化对象到内存中
	 */
	@SuppressWarnings("unchecked")
	public void readDicFromDisk() {
		String fileName = "coreDic.dic";
		File dic = new File("data/" + fileName);
		if (dic.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dic));
				firstWordMap = (HashMap<String, PartOne>)compressObject.readCompressObject(ois.readObject());
				ois.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} else {
			loadFile(DefaultDic);
		}
	}

	public static HashMap<String, PartOne> getFirstWordMap() {
		return firstWordMap;
	}

	public static void setFirstWordMap(HashMap<String, PartOne> firstWordMap) {
		InitDictionary.firstWordMap = firstWordMap;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String filePath = DefaultDic;
		InitDictionary init = new InitDictionary();
		init.loadFile(filePath);
//		init.readDicFromDisk();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
