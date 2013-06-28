package org.apache.solr.update.processor.ext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.update.AddUpdateCommand;
import org.apache.solr.update.processor.UpdateRequestProcessor;
import org.apache.solr.update.processor.UpdateRequestProcessorFactory;

import com.aliasi.classify.ScoredClassification;
import com.aliasi.classify.ScoredClassifier;

 
/**
 * @author KmaDou
 *<updateRequestProcessorChain name="mlinterceptor" default="true">
      <processor class="org.apache.solr.update.processor.ext.CategorizeDocumentFactory">
        <str name="inputField">content</str>
        <str name="outputField">category</str>
        <str name="defaultCategory">Others</str>
        <str name="model">/home/selvam/bayes-model</str>
      </processor>
      <processor class="solr.RunUpdateProcessorFactory"/>
      <processor class="solr.LogUpdateProcessorFactory"/>
    </updateRequestProcessorChain>
    
    <requestHandler name="/update" class="solr.XmlUpdateRequestHandler">
     <lst name="defaults">
      <str name="update.processor">mlinterceptor</str>
    </lst>
  </requestHandler>
 */
public class CategorizeDocumentFactory extends UpdateRequestProcessorFactory
{
  SolrParams params;
  String modelFile = "NaiveBayesClassifier";
  ScoredClassifier<CharSequence> compiledClassifier = null;
      
  public void init( NamedList args )
  {
	  if (compiledClassifier != null)
		  return;
    params = SolrParams.toSolrParams((NamedList) args);
    
    String modelPath = params.get("model");

    
	try {
		ObjectInputStream oi = new ObjectInputStream(new FileInputStream(
				modelFile));
		compiledClassifier = (ScoredClassifier<CharSequence>) oi
				.readObject();
		oi.close();
	} catch (IOException ie) {
		System.out
				.println("IO Error: Model file " + modelFile + " missing");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}    

  }
  @Override
  public UpdateRequestProcessor getInstance(SolrQueryRequest req, SolrQueryResponse rsp, UpdateRequestProcessor next)
  {
    return new CategorizeDocument(next);
  }
 
 
  public class CategorizeDocument extends UpdateRequestProcessor
    {
      private StandardAnalyzer analyzer;

	public CategorizeDocument( UpdateRequestProcessor next) {
        super( next );
      }
 
      @Override
      public void processAdd(AddUpdateCommand cmd) throws IOException {
        try{            
            SolrInputDocument doc = cmd.getSolrInputDocument();
            String inputField = params.get("inputField");
            String outputField = params.get("outputField");
            String input = (String) doc.getFieldValue(inputField);
            ArrayList<String> tokenList = new ArrayList<String>(256);
            analyzer = new StandardAnalyzer(Version.LUCENE_40);
            TokenStream ts = analyzer.tokenStream(inputField, new StringReader(input));
            while (ts.incrementToken()) {
                tokenList.add(ts.getAttribute(CharTermAttribute.class).toString());
            }
            String[] tokens = tokenList.toArray(new String[tokenList.size()]);
            //Call the LingPipe classification process
        	ScoredClassification classification = compiledClassifier.classify(tokens.toString());
            String result =  classification.bestCategory();
            if (result != null && result != "") {
              doc.addField(outputField, result);
            }
         }
         catch(IOException e1){
           e1.printStackTrace();
         }
         catch(Exception e){
           e.printStackTrace();
         }
        super.processAdd(cmd);
      }
    }
}