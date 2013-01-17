package org.nlp.ngram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ngrams {

	private double N = 1024908267229L; // 字典中词总数
	HashMap<String, Double> data;		//(词,词频)
	private HashMap<String, String> tokens; //(句子,分词结果)
		
	public static void main(String[] args) throws IOException {
		
		String text = "itwasabrightcolddayinaprilandtheclockswerestrikingthirteen";
//		String text = "乒乓球拍卖完了";
		ngrams seg = new ngrams();
	
		String s = seg.segment(text);
		System.out.println(text + "->" + s);

	}
	
	/*
	 * 返回分词结果
	 * */
	public HashMap<String, String> next(){
		return tokens;
	}
	
	public ngrams() {
		double N = 0.0;		
		tokens = new HashMap<String, String>();
		data = datafile("data/count_1w.txt");
//		data = datafile("data/SogouLabDic.dic");
		for (Iterator<Double> iterator = data.values().iterator(); iterator.hasNext();) {
			N += (Double)iterator.next();			
		}
		this.N = N;
	}

	/* 分词算法*/
	public String segment(String text) {
		String dst = "";		
		double p = 0.0f;
		List<String> result = new ArrayList<String>();		
		
		//用空格切分字符串text
		 result.addAll(splits(text," "));

		for (int n = 0; n < result.size(); n++) {
			String[] candidate = result.get(n).split(" ");
			String first = candidate[0];
			String rem = "";
			if (candidate.length > 1){
				if (tokens.get(candidate[1]) == null){
					rem = segment(candidate[1]);
				}else{
					rem  = tokens.get(candidate[1]);	
				}				
			}
//			System.out.format("===========================\n%s\t%s\t\t%s\n",first,rem,Pwords(first + " " + rem));
			if (Pwords(first + " " + rem) > p){
				p = Pwords(first + " " + rem);
				dst = first + " " + rem;				
			}			
		}
		//添加结果到最佳切分字典
		tokens.put(text, dst);		
		return dst;
	}

	
	/*
	 * 尝试用arraylist方式存放词  替换空格切分词
	 * 主要是想尝试一下这种方法是否比segment(String text)便捷
	 * 
	 * */
	public String segmentList(String text) {
		String dst = "";		
		double p = 0.0f;
		List<String[]> result = new ArrayList<String[]>();

		 result.addAll(splits(text));

		for (int n = 0; n < result.size(); n++) {
			String[] candidate = result.get(n);
			String first = candidate[0];
			String rem = "";
			if (candidate.length > 1){
				if (tokens.get(candidate[1]) == null){
					rem = segment(candidate[1]);
				}else{
					rem  = tokens.get(candidate[1]);	
				}				
			}
//			System.out.format("===========================\n%s\t%s\t\t%s\n",first,rem,Pwords(first + " " + rem));
			
			if (Pwords(first + " " + rem) > p){
				p = Pwords(first + " " + rem);
				dst = first + " " + rem;				
			}			
		}
		//添加结果到最佳切分字典
		tokens.put(text, dst);
		return dst;
	}
	
	/*
	 * 
	 * 返回用空格切割字符串List
	 * 
	 * */
	private static List<String> splits(String str, String SplitChar) {		
		List<String> result = new ArrayList<String>();
		int maxSplits = 20;
		maxSplits = Math.min(maxSplits, str.length());
//		maxSplits = str.length()-1;
		for (int n = 0; n < maxSplits; n++) {
			result.add(str.substring(0, n + 1) + SplitChar + str.substring(n + 1));
		}
		return result;
	}

	
	/*
	 * 
	 * 字符串数组List方式返回切割结果
	 * 主要是想尝试一下这种方法是否比List<String>便捷
	 * 
	 * */
	private static List<String[]> splits(String str) {		
		List<String[]> result = new ArrayList<String[]>();
		int maxSplits = 20;
		maxSplits = Math.min(maxSplits, str.length());
//		maxSplits = str.length()-1;
		for (int n = 0; n < maxSplits; n++) {
			String[] dst = {str.substring(0, n + 1),str.substring(n + 1)};
			result.add(dst);
//			result.add(str.substring(0, n + 1) + " " + str.substring(n + 1));
		}
		return result;
	}	
	
	
	// 以空格切分词输入求概率
	public double Pwords(String text) {
		// System.out.format("============%s===========\n",text);
		if (text == null || text.isEmpty()) {
			return 0.0;
		}
		String[] words = text.split(" ");		
		List<Double> floatList = new ArrayList<Double>();
		for (int i = 0; i < words.length; i++) {
			
			floatList.add(Pw(words[i]));		
		}
		return product(floatList);
	}
	
	
	// 以字符串数组输入求概率
	public double Pwords(String[] words) {
		// System.out.format("============%s===========\n",text);
		if (words.length == 0) {
			return 0.0;
		}
		List<Double> floatList = new ArrayList<Double>();
		for (int i = 0; i < words.length; i++) {

			floatList.add(Pw(words[i]));
		}
		return product(floatList);
	}
	
	// 以字符串数组输入求概率
	public double Pwords(List<String> words) {
		// System.out.format("============%s===========\n",text);
		if (words.size() == 0) {
			return 0.0;
		}
		List<Double> floatList = new ArrayList<Double>();
		for (int i = 0; i < words.size(); i++) {

			floatList.add(Pw(words.get(i)));
		}
		return product(floatList);
	}	

	/* 
	 * 
	 * 假设first和remaning之间是没有依赖关系的
	 * 求first,remaning联合概率
	 * P(first,remaning) = P(first) × P(remaining) 
	 *  
	 */
	public double product(List<Double> nums) {
		double result = 1.0f;
		for (int i = 0; i < nums.size(); i++) {
			result *= nums.get(i);
			// System.out.println("nums:"+nums.get(i));
		}
		// System.out.println("product:"+result);
		return result;
	}

	/*
	 * 
	 * 求某个字符串的全概率
	 * P(key)
	 * 
	 * */ 
	public double Pw(String key) {
		return new Pdist(data, N, avoid_long_words(key)).get(key);
	}

	/*
	 * "Read key,value pairs from file."
	 * 读取字典数据格式：
	 * key[tab]value
	 * key：词
	 * value：词频
	 * 
	 * */ 
	public HashMap<String, Double> datafile(String name) {
		String line;
		String[] word;
		BufferedReader br;
		HashMap<String, Double> data = new HashMap<String, Double>();
		try {
			br = new BufferedReader(new FileReader(name));
			line = br.readLine();
			while (line != null) {
				word = line.split("\t");				
				data.put(word[0], Double.parseDouble(word[1]));
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/*
	 * "Estimate the probability of an unknown word."
	 * 未登词处理
	 * 假设字符(key)串没有出现在字典中，那么认为该词频为1
	 * 该字符串全概率P(key) = 1(次) / (N(总词语数量)* 惩罚值）
	 * 惩罚值=10的字符串长度次方
	 * 中文每个词的字符长度较短，可以考虑长度的n倍，n设置为2-6之间比较合理
	 * 
	 * */ 
	public double avoid_long_words(String key) {		
		return 1.0 / (N * Math.pow(10.0, key.length()*4)); //中文每个字两个字节
//		return 1.0 / (N * Math.pow(10.0, key.length())); 
	}

	/*词频字典类*/
	private class Pdist {
		double N;
		double missingfn = 1.0 / N;
		HashMap<String, Double> Pw;

		/*
		 * 
		 * 初始化词频字典
		 * 
		 * */
		Pdist(HashMap<String, Double> Pw, double N, double missingfn) {
			this.Pw = Pw;
			this.N = N;
			this.missingfn = missingfn;
		}

		/*
		 * 获得某个词的全概率
		 * */
		public double get(String key) {
			if (Pw.get(key) != null) {
//			System.out.println(key+".........found........."+Pw.get(key) / N);				
				return Pw.get(key) / N;
			}
//			System.out.println(key+".........lost........."+missingfn);
			return missingfn;
		}

	}
}
