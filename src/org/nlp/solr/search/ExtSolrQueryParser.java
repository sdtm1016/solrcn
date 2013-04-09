package org.nlp.solr.search;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.solr.schema.FieldType;
import org.apache.solr.schema.TextField;
import org.apache.solr.search.QParser;
import org.apache.solr.search.SolrQueryParser;

/**
 * 把 queryText 转为 and 关系的 queryparser.
 *
 * @author chenlb(chenlb2008@gmail.com) 2012-2-22
 */
public class ExtSolrQueryParser extends SolrQueryParser {


	public ExtSolrQueryParser(QParser parser, String defaultField, Analyzer analyzer) {
		super(parser, defaultField, analyzer);
	}

	public ExtSolrQueryParser(QParser parser, String defaultField) {
		super(parser, defaultField);
	}

	protected Query getFieldQuery(String field, String queryText, boolean quoted) throws ParseException {
		String myField = field == null ? defaultField : field;

		if(myField != null) {
			//SchemaField sf = schema.getField(myField);
			FieldType ft = schema.getField(myField).getType();
			if(ft instanceof TextField) {
				try {
					//System.out.println(queryText);
					//此字段的分词器
					if((ft.getQueryAnalyzer() == null ? ft.getAnalyzer() : ft.getQueryAnalyzer()) != null) {
						BooleanQuery bq = new BooleanQuery();//(true);
						TokenStream ts = (ft.getQueryAnalyzer() == null ? ft.getAnalyzer() : ft.getQueryAnalyzer()).tokenStream(field, new StringReader(queryText));
						//StringBuilder sb = new StringBuilder();
						int endOffset = 0;
						while(ts.incrementToken()) {
							CharTermAttribute ta = (CharTermAttribute) ts.getAttribute(CharTermAttribute.class);
							OffsetAttribute oa = (OffsetAttribute) ts.getAttribute(OffsetAttribute.class);
							//顺序增用 and  关系
							if(oa.startOffset() >= endOffset) {
								//sb.append(t.term()).append(' ');
								bq.add(new TermQuery(new Term(myField, ta.toString())), Occur.MUST);
								endOffset = oa.endOffset();
							} else {
								//可以用分词相交用 or 关系
								//这里也使用 and 关系
								bq.add(new TermQuery(new Term(myField, ta.toString())), Occur.MUST);
							}

						}
						return bq;
						/*if(sb.length() > 1) {	//有内容
							sb.setLength(sb.length() - 1);
							String nQueryText = sb.toString();

							if(parser.getReq().getParams().get(CommonParams.DEBUG_QUERY) != null) {
								System.out.println("queryText: "+queryText+" -> "+nQueryText);
							}
							queryText = nQueryText;
						}*/
					}

				} catch (Exception e) {
					throw new ParseException(e.getMessage());
				}
			}//TextField
		}// myField != null

		return super.getFieldQuery(field, queryText, quoted);
	}


}
