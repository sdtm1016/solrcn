/**
 * 
 */
package org.nlp.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * @author Tony
 *
 */
public class StringUtil {

	public static final String SEPERATOR_C = "。！？：；…~·#……&*（）——+=|、，“”‘’";

	public static final String SEPERATOR_E= "!?:;~`!#^&*()+=\\|\"\"'',/";

	public static final String SEPERATOR_NOCHINESEFOLLOW = "-_.@$%@￥%";

	public static final String SEPERATOR_NEWLINE_TAB = "\n\r 　\t";
	
	
	/**
	 * 判断字符串是否是字母或者数字或者为连字符如-_.等
	 * @param str
	 * @return
	 */
	public static boolean isAlphanumeric(String str) {
		if (str != null) {
			byte[] bs = str.getBytes();
			for (byte b : bs) {
				if (b < 48 || b > 57 && b < 65 || b > 90 && b < 97 || b > 122){
					if("-._".indexOf(str) != -1)
						return true;
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 判断一个字符串是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str != null) {
			try {
				str = str.trim();
				Double.parseDouble(str);
				return true;
			} catch (NumberFormatException e) {
			}
		}
		return false;
	}
	
	/**
	 * 判断字符串是否全是汉字
	 * @param str
	 * @return
	 */
	public static boolean isAllChinese(String str) {
		if (str != null) {
			str = quan2ban(str);
			if (str != null) {
				if (str.length() * 2 == str.getBytes().length)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断字符串是否全不是汉字
	 * @param str
	 * @return
	 */
	public static boolean isNoChinese(String str) {
		if (str != null) {
			str = quan2ban(str);
			if (str != null) {
				if (str.length() == str.getBytes().length)
					return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 把表示数字含义的字符串转你成整形
	 * @param str
	 *            要转换的字符串
	 * @return 如果是有意义的整数，则返回此整数值。否则，返回-1。
	 */
	public static int c2int(String str) {
		if (str != null)
			try {
				int i = new Integer(str).intValue();
				return i;
			} catch (NumberFormatException e) {

			}
		return -1;
	}
	
	/**
	 * 用UTF-8be的编码方式把含有全角编码的字符串转成半角编码的字符串
	 * @param str
	 * @return
	 */
	public static String quan2ban(String str) {
		String result = null;
		if (str != null) {
			try {
				byte[] uniBytes = str.getBytes("utf-16be");
				byte[] b = new byte[uniBytes.length];
				for (int i = 0; i < b.length; i++) {
					if (uniBytes[i] == -1) {
						b[i] = 0;
						if (i + 1 < uniBytes.length)
							b[++i] = (byte) (uniBytes[i] + 0x20);
					} else
						b[i] = uniBytes[i];
				}
				result = new String(b, "utf-16be");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 是否是字母
	 * @param str
	 * @return
	 */
	public static boolean isLetter(String str) {
		if (str != null) {
			byte b[];
			str = str.trim();
			b = str.toUpperCase().getBytes();
			for (int i = 0; i < b.length; i++) {
				if (b[i] < 65 || b[i] > 90)
					return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 对字符串进行原子分隔
	 * @param str
	 * @return
	 */
	public static String[] atomSplit(String str) {		
		if (str==null) 
			return null;
		
		String[] result = null;
		int nLen=str.length();
		result = new String[nLen];			
		for (int i = 0; i < nLen; i++) {
			result[i] = str.substring(i, i + 1);
		}
		return result;
	}
	
	
	/**
	 * 读取文本
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> readFile(String fileName) throws IOException {
		ArrayList<String> result = null;
		FileInputStream fin = null;
		InputStreamReader in = null;
		BufferedReader br = null;
		File file = null;
		String line = null;

		if (fileName != null) {
			file = new File(fileName);
			if (file.exists()) {
				result = new ArrayList<String>();
				try {
					fin = new FileInputStream(file);
					in = new InputStreamReader(fin);
					br = new BufferedReader(in);
					while ((line = br.readLine()) != null) {
						result.add(line);
					}
				} catch (IOException e) {
					throw new IOException();
				}
			}
		}
		
		return result;
	}
}
