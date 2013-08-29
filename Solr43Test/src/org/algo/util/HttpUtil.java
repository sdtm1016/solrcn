package org.algo.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class HttpUtil {
	private static String sendHttpMessage(String url, String method, String contents) {
		try {
			
			URL serverUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) serverUrl
					.openConnection();
			conn.setConnectTimeout(20000);
	
			conn.setRequestMethod(method);// "POST" ,"GET"
			
			conn.addRequestProperty("Accept", "*/*");
			conn.addRequestProperty("Accept-Language", "zh-cn");
			conn.addRequestProperty("Accept-Encoding", "gzip, deflate");
			conn.addRequestProperty("Cache-Control", "no-cache");
			conn.addRequestProperty("Accept-Charset", "UTF-8");
			conn.addRequestProperty("Content-type", "application/json");			
			
			if (method.equalsIgnoreCase("GET")) {
				conn.connect();
			}
	
			else if (method.equalsIgnoreCase("POST")) {
	
				conn.setDoOutput(true);
				conn.connect();
				conn.getOutputStream().write(contents.getBytes());
			} else {
				throw new RuntimeException("your method is not implement");
			}
	
			InputStream ins = conn.getInputStream();
	
			// 澶勭悊GZIP鍘嬬缉鐨?
			if (null != conn.getHeaderField("Content-Encoding")
					&& conn.getHeaderField("Content-Encoding").equals("gzip")) {
				byte[] b = null;
				GZIPInputStream gzip = new GZIPInputStream(ins);
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((num = gzip.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				b = baos.toByteArray();
				baos.flush();
				baos.close();
				gzip.close();
				ins.close();
				return new String(b, "UTF-8").trim();
			}
	
			String charset = "UTF-8";
			InputStreamReader inr = new InputStreamReader(ins, charset);
			BufferedReader br = new BufferedReader(inr);
	
			String line = "";
			StringBuffer sb = new StringBuffer();
			do {
				sb.append(line);
				line = br.readLine();
			} while (line != null);			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();			
			return "";
		}
	
	}

}
