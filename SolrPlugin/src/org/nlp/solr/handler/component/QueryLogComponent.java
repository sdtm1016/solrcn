/**
 * 
 */
package org.nlp.solr.handler.component;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.ShardParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.handler.component.QueryComponent;
import org.apache.solr.handler.component.ResponseBuilder;
import org.apache.solr.handler.component.SearchComponent;
import org.apache.solr.request.SolrQueryRequest;

/**
 * 
 * @author longkeyy
 * 
 */
public class QueryLogComponent extends QueryComponent {

	public static final String COMPONENT_NAME = "QueryLog";
	public static final String LogFileName = "QueryLog.log";
    
    /** 
     * 追加文件：使用FileWriter 
     *  
     * @param fileName 
     * @param content 
     */  
    public static void fileWriter(String fileName, String content) {  
    	if (fileName == null || content == null){
    		return;
    	}
        try {  
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件  
            FileWriter writer = new FileWriter(fileName, true);  
            writer.write(content);  
            writer.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }         

	@Override
	public void process(ResponseBuilder rb) throws IOException {
		super.process(rb);
	}

	@Override
	public void prepare(ResponseBuilder rb) throws IOException {
		super.prepare(rb);
	    SolrQueryRequest req = rb.req;	    
//	    SolrQueryResponse rsp = rb.rsp;
	    SolrParams params = req.getParams();
	    if (!params.getBool(COMPONENT_NAME, true)) {
	        return;
	      }
	    
	    if (params.get(ShardParams.IS_SHARD) != null && params.get(ShardParams.IS_SHARD).equals("true")){
	    	return;
	    }
	    
		String queryString = rb.getQueryString();
		
		if (queryString == null) {
			queryString = params.get(CommonParams.Q);
		}

		String[] queryTerm;
		if (queryString != null) {
			// 分离指定字段和关键字 term:keyword -> keyword
			String[] queryTermList = queryString.split(":");
			if (queryTermList.length < 2) {
				// 分离多关键字 keyword1 keyword2 -> keyword1,keyword2
				queryTerm = queryTermList[0].split(" ");
				for (int j = 0; j < queryTerm.length; j++) {
					fileWriter(LogFileName, queryTerm[j] + " 1\r");
				}												
			} else {				
				for (int i = 1; i <= queryTermList.length; i = i + 2) {
					// 分离多关键字 keyword1 keyword2 -> keyword1,keyword2
					queryTerm = queryTermList[i].split(" ");
					for (int j = 0; j < queryTerm.length; j++) {
						fileWriter(LogFileName, queryTerm[j] + " 1\r");
					}
				}
			}

		}			
					      	
	}

	@Override
	public String getDescription() {
		return "QueryLogComponent";
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
