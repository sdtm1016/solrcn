package org.solr.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

public class HttpUtil {

	public static Map<String, Object> get(String url, final Map<String, String[]> parameterMap) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		String charset = "UTF-8";
		StringBuilder sp = new StringBuilder();
		Iterator<Entry<String, String[]>> it = parameterMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String[]> entry = it.next();
			String key = entry.getKey();
			String[] value = entry.getValue();
			for (String string : value) {
				try {
					sp.append(key + "=" + java.net.URLEncoder.encode(string,charset) + "&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					result.put("error", e.getMessage());
				}
			}
		}

//		url = sp.deleteCharAt(sp.length() - 1) == null ? url + "?" + sp : url;

		 if (sp.length() > 0) {
		 sp.deleteCharAt(sp.length() - 1);
		 url += "?" + sp;
		 }
		 
		try {
			URL serverUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
			conn.setConnectTimeout(20000);

			conn.setRequestMethod("GET");// "POST" ,"GET"
			conn.addRequestProperty("Accept", "*/*");

//			conn.addRequestProperty("Accept-Language", "zh-cn");
			conn.addRequestProperty("Accept-Encoding", "gzip, deflate");
			conn.addRequestProperty("Cache-Control", "no-cache");
			conn.addRequestProperty("Accept-Charset", charset);
//			conn.addRequestProperty(
//					"User-Agent",
//					"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 1.1.4322; .NET4.0C; .NET4.0E)");
			conn.connect();
			
			

			InputStream ins = conn.getInputStream();
			
			result.put("headers", conn.getHeaderFields());

			// 处理GZIP压缩的
			if (null != conn.getHeaderField("Content-Encoding") && conn.getHeaderField("Content-Encoding").equals("gzip")) {
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
				result.put("body", new String(b, charset).trim());
			}

			
			InputStreamReader inr = new InputStreamReader(ins, charset);
			BufferedReader br = new BufferedReader(inr);

			String line = "";
			StringBuffer sb = new StringBuffer();
			
			do {
				sb.append(line);
				line = br.readLine();
			} while (line != null);
			result.put("body", sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", e.getMessage());
		}
		
		return result;

	}

}
