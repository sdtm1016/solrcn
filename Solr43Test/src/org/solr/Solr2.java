package org.solr;

import java.io.File;
import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.ContentStream;
import org.apache.solr.handler.extraction.ExtractingParams;

/**
 * @author EDaniel
 */
public class Solr2 {

  public static void main(String[] args) {
    try {
      //Solr cell can also index MS file (2003 version and 2007 version) types.
      
      String fileName="G:/3.doc";
      //this will be unique Id used by Solr to index the file contents.
      String solrId = "22"; 
      
      indexFilesSolrCell(fileName, solrId);
      
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }

  /**
   * Method to index all types of files into Solr. 
   * @param fileName
   * @param solrId
   * @throws IOException
   * @throws SolrServerException
   */
  public static void indexFilesSolrCell(String fileName, String solrId) 
    throws IOException, SolrServerException {
    

    
	String zkHost = "localhost:9983";
	String defaultCollection = "collection1";
	CloudSolrServer server = new CloudSolrServer(zkHost);
	server.setDefaultCollection(defaultCollection);
    
    ContentStreamUpdateRequest up 
      = new ContentStreamUpdateRequest("/update/extract");
    
    up.addFile(new File(fileName), zkHost);
    
    up.setParam("literal.id", solrId);
    up.setParam("uprefix", "attr_");
   // up.setParam(ExtractingParams.EXTRACT_ONLY, "true");
    up.setParam("fmap.content", "content");
    
    up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
    
   server.request(up);
    /* QueryResponse rsp = server.query(new SolrQuery("*:*"));  
    System.out.println(rsp.getResults().getNumFound());  */

  }
}