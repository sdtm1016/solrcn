package org.nlp.tran;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.nlp.algo.Bloom;

public class tranbloom {
	public static int num, wordcount = 0;

	static Bloom bl = new Bloom();
	public static int maxlen = 0;
	public static ArrayList<String> len;
	final static String path = "D:/sougo/dichot";
	

	// 非递归
	public static void scanDirNoRecursion(String path) throws Exception {
		LinkedList<File> list = new LinkedList<File>();
		File dir = new File(path);
		File file[] = dir.listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isDirectory())
				list.add(file[i]);
			else {
				read(file[i].getAbsolutePath());
				num++;
			}
		}
		File tmp;
		while (!list.isEmpty()) {
			tmp = (File) list.removeFirst();// 首个目录
			if (tmp.isDirectory()) {
				file = tmp.listFiles();
				if (file == null)
					continue;
				for (int i = 0; i < file.length; i++) {
					if (file[i].isDirectory())
						list.add(file[i]);// 目录则加入目录列表，关键
					else {
						System.out.println(file[i]);
						num++;
					}
				}
			} else {
				System.out.println(tmp);
				num++;
			}
		}
	}

	// 递归
	public static void scanDirRecursion(File file) {
		try {
			if (file.canRead()) {
				if (file.isDirectory()) {
					String[] files = file.list();
					if (files != null) {
						for (int i = 0; i < files.length; i++) {
							scanDirRecursion(new File(file, files[i]));
						}
					}
				} else {
					// if (file.getName().endsWith("ppt"))
					System.out.println(file);
					num++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void read(String filePath) throws Exception {
		String line;
		String word;

		int count;
		long tagTime = System.currentTimeMillis();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			line = br.readLine();
			count = 0;
			while (line != null) {
				word = line.split("[  	]")[0];
				if (word.length() > maxlen)
					maxlen = word.length();
				if (filePath.contains("count")){
					bl.addEn(word);
				}else{
					bl.add(word);	
				}					
				
				count++;
				wordcount++;
				line = br.readLine();
			}
			br.close();
			System.out.format("%s add %s words use %s ms\n", filePath, count,
					(System.currentTimeMillis() - tagTime));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] argc) throws Exception {
		long a = System.currentTimeMillis();
		String model = "model_new_all";
		num = 0;
		len = new ArrayList<String>();
		scanDirNoRecursion(path);		
		bl.saveModel(model);
		
		
		System.out.println("人是:"+bl.contains("人是"));
		Bloom lm = new Bloom(model);		
		lm.loadModel(model);
		System.out.println("乒乓球:"+lm.contains("乒乓球"));
		System.out.println("人是:"+lm.contains("人是"));
		System.out.println("ce:"+lm.contains("ce"));
		System.out.println("ae:"+lm.contains("ae"));
		System.out.println("件进行合:"+lm.contains("件进行合"));
		System.out.println("件进行合:"+lm.containsEn("件进行合"));
		
		System.out.println("了一:"+lm.contains("了一"));
		System.out.println("了完:"+lm.contains("了完"));
		System.out.println("天天:"+lm.contains("天天"));
		System.out.println("========================");		
		System.out.println("最长词语:" + maxlen);
		System.out.println("词语总数:" + wordcount);
		System.out.println("文件总数:" + num);
		System.out.print("总耗时:");
		System.out.println(System.currentTimeMillis() - a);
	}
}
