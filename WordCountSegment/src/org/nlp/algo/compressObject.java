package org.nlp.algo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class compressObject {
	// 将Data类型数据对象序列化对象压缩,返回字节数组,压缩后的对象数组可写入文件保存或用于网络传输
	public static byte[] writeCompressObject(Object obj) {
		byte[] data_ = null;
		try {
			// 建立字节数组输出流
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			// 建立gzip压缩输出流
			GZIPOutputStream gzout = new GZIPOutputStream(o);
			// 建立对象序列化输出流
			ObjectOutputStream out = new ObjectOutputStream(gzout);
			out.writeObject(obj);
			out.flush();
			out.close();
			gzout.close();
			// 返回压缩字节流
			data_ = o.toByteArray();
			o.close();

		} catch (IOException e) {
			System.out.println(e);
		}
		return (data_);
	}

	// 将压缩字节数组还原为Data类型数据对象
	public static Object readCompressObject(Object data_) {
		Object obj = null;
		try {
			// 建立字节数组输入流
			ByteArrayInputStream i = new ByteArrayInputStream((byte[]) data_);
			// 建立gzip解压输入流
			GZIPInputStream gzin = new GZIPInputStream(i);
			// 建立对象序列化输入流
			ObjectInputStream in = new ObjectInputStream(gzin);
			// 按制定类型还原对象
			obj = in.readObject();
			i.close();
			gzin.close();
			in.close();
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		return (obj);
	}

	// 保存模型
	public static void saveObjectToFile(String fileName, Object obj) {

		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new GZIPOutputStream(new FileOutputStream(
							new File(fileName))));
			oos.writeObject(obj);
			oos.close();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 加载模型
	public static Object loadObjectFromFile(String fileName) {
		Object obj = null;
		try {
			File dir = new File(fileName);
			ObjectInputStream ois;

			if (!dir.exists()) {				
				InputStream fs = compressObject.class.getClassLoader()
						.getResourceAsStream(fileName);
				ois = new ObjectInputStream(new GZIPInputStream(fs));

			} else {
				FileInputStream fs = new FileInputStream(new File(fileName));
				ois = new ObjectInputStream(new GZIPInputStream(fs));

			}
			obj = ois.readObject();
			ois.close();
		} catch (IOException e) {			
			e.printStackTrace();
			return (obj);
		} catch (ClassNotFoundException e) {
			System.out.println("FileNotFound");
			return (obj);
		}
		return (obj);
	}

}