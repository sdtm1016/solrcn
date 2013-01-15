/**
 * 
 */
package org.nlp.solr.handler.component;

import java.io.IOException;

import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.handler.component.ResponseBuilder;
import org.apache.solr.handler.component.SearchComponent;
import org.apache.solr.request.SolrQueryRequest;

/**
 * 
 * @author longkeyy
 * 
 */
public class QueryLogComponent extends SearchComponent {

	public static final String COMPONENT_NAME = "QueryLog";
	public static final int UNLIMITED_MAX_COUNT = -1;

	@Override
	public void process(ResponseBuilder rb) throws IOException {
		SolrParams params = rb.req.getParams();

		System.out.println("here:" + params.get("q"));
	}

	@Override
	public void prepare(ResponseBuilder rb) throws IOException {
		// do nothing
		System.out.println("prepare");
		SolrQueryRequest req = rb.req;
		SolrParams params = req.getParams();
		// if (!params.getBool(COMPONENT_NAME, true)) {
		// return;
		// }
		System.out.println("prepare_doing");
		String queryString = rb.getQueryString();
		if (queryString == null) {
			// this is the normal way it's set.
			queryString = params.get(CommonParams.Q);
			rb.setQueryString(queryString);
			System.out.println(queryString);
		} else {
			System.out.println("not null:" + queryString);
		}
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getSource() {
		return "";
	}

	@Override
	public String getVersion() {
		return "";
	}

}
