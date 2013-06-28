package org.nlp.solr.handler.component;

import java.io.IOException;

import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.BytesRef;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.apache.solr.handler.component.ResponseBuilder;
import org.apache.solr.handler.component.SearchComponent;
import org.apache.solr.request.UnInvertedField;
import org.apache.solr.search.SolrIndexSearcher;

public class ExampleSearchComponent extends SearchComponent {
	String[] fieldNames = null;

	@Override
	public void prepare(ResponseBuilder builder) throws IOException {
		fieldNames = builder.req.getParams().get("exampleFields", "")
				.split(",");
	}

	@Override
	public void process(ResponseBuilder rb) throws IOException {
		long totalMemorySize = 0;
	    
		if (fieldNames != null) {
			
			SolrIndexSearcher searcher = rb.req.getSearcher();
			final AtomicReader indexReader = rb.req.getSearcher().getAtomicReader();
			NamedList<Object> termsResult = new SimpleOrderedMap<Object>();
			
			Fields lfields = indexReader.fields();
			
			for (String field : lfields) {
				NamedList<Integer> fieldTerms = new NamedList<Integer>();
				termsResult.add(field, fieldTerms);
			}
			
		   
			for (String fieldName : fieldNames) {
				UnInvertedField field = UnInvertedField.getUnInvertedField(
						fieldName, searcher);
				TermsEnum ordTermsEnum = field.getOrdTermsEnum(searcher.getAtomicReader());
				rb.rsp.add("ordTermsEnum", ordTermsEnum.next());
				BytesRef next = ordTermsEnum.next();
				termsResult.add(fieldName, next);
				
				totalMemorySize += field.memSize();
			}
			 rb.rsp.add("terms", termsResult);
		}
		
		rb.rsp.add("example", totalMemorySize);		
	}

	@Override
	public String getDescription() {
		return "ExampleSearchComponent";
	}

	@Override
	public String getSource() {
		return "";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}