package org.nlp.solr.handler;

import org.apache.solr.common.params.SolrParams;
import org.apache.solr.handler.StandardRequestHandler;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;


public class LogRequestHandler extends StandardRequestHandler {
	@Override
	public void handleRequestBody(SolrQueryRequest request, SolrQueryResponse response)
			throws Exception {
		//System.out.println("handleRequestBody");
//		Dictionary.getInstance().loadDbExDict();	
		SolrParams params = request.getParams();
		String string = params.get("q");
		response.add( "Log", string );
	}
}
