package org.solr.admin.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.solr.client.solrj.impl.HttpClientUtil;

public class HttpUtil {
	public static String Get(final String url) {
		HttpClient httpClient = HttpClientUtil.createClient(null);
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(httpget);
			InputStream content = response.getEntity().getContent();
			Header contentEncoding = response.getEntity().getContentEncoding();
			if (contentEncoding == null) {
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((num = content.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				byte[] b = baos.toByteArray();
				baos.flush();
				baos.close();
				return new String(b, "UTF-8");
			} else {
				System.out.println(contentEncoding.getValue());
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
	
	
	public static String Post(final String url) {
		HttpClient httpClient = HttpClientUtil.createClient(null);
		HttpPost httpPost = new HttpPost(url);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			InputStream content = response.getEntity().getContent();
			Header contentEncoding = response.getEntity().getContentEncoding();
			if (contentEncoding == null) {
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((num = content.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				byte[] b = baos.toByteArray();
				baos.flush();
				baos.close();
				return new String(b, "UTF-8");
			} else {
				System.out.println(contentEncoding.getValue());
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
	
	

}
