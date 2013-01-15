package org.nlp.solr.search;

import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.core.SolrCore;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QParserPlugin;

/**
 * 扩展的 lucene QParser 使用它支持自定义的 query parser.
 *
 * @author chenlb 2010-8-28下午03:01:46
 */
public class ExtLuceneQParserPlugin extends QParserPlugin {

	public static String NAME = "extlucene";

	@Override
	public QParser createParser(String qstr, SolrParams localParams,
			SolrParams params, SolrQueryRequest req) {

		return new ExtLuceneQParser(qstr, params, params, req);
	}

	/**
	 * invoke args=null at {@link SolrCore#initQParsers()}
	 */
	public void init(@SuppressWarnings("rawtypes") NamedList args) {
		//args is null nothing to do
	}

}
