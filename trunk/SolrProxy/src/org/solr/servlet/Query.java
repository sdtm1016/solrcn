package org.solr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.solr.config.SolrProperties;
import org.solr.util.HttpUtil;

@WebServlet("/select")
public class Query extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final SolrProperties SolrMap = SolrProperties.getInstance();
	String source = "chuangyisucai";

	public Query() {
		super();
	}

	@SuppressWarnings("unchecked")
	private void services(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getParameter("q") == null || request.getParameter("q").trim().isEmpty()) {
			return;
		}
		Map<String, String[]> parameterMap = request.getParameterMap();
			String collection = request.getParameter("collection");
			if (collection != null){
				parameterMap.remove("collection");
				source = collection;
			}
			
		Map<String, Object> result = HttpUtil.get(SolrMap.getSelectUrl(source), parameterMap);

		Map<String, List<String>> headerFields = (Map<String, List<String>>) result.get("headers");
		if (headerFields != null) {
			Iterator<Entry<String, List<String>>> it = headerFields.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, List<String>> entry = it.next();
				String key = entry.getKey();
				List<String> value = entry.getValue();
				for (String string : value) {
					response.addHeader(key, string);
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.println(result.get("body"));
		response.flushBuffer();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		services(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		services(request, response);
	}

}
