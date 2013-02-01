package org.nlp.address;


import java.io.IOException;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoaderAware;
import org.apache.lucene.analysis.util.TokenFilterFactory;

/**
 * Factory for {@link LowerCaseFilter}. 
 * <pre class="prettyprint" >
 * &lt;fieldType name="text_address" class="solr.TextField" positionIncrementGap="100"&gt;
 *   &lt;analyzer&gt;
 *     &lt;tokenizer class="solr.WhitespaceTokenizerFactory"/&gt;
 *     &lt;filter class="solr.AddressFilterFactory"/&gt;
 *   &lt;/analyzer&gt;
 * &lt;/fieldType&gt;</pre> 
 *
 */
public class AddressFilterFactory extends TokenFilterFactory implements
		ResourceLoaderAware{
  @Override
  public void init(Map<String,String> args) {
    super.init(args);
    assureMatchVersion();
  }

  public AddressFilter create(TokenStream input) {
    return new AddressFilter(input);
  }

@Override
public void inform(ResourceLoader arg0) throws IOException {
	String dicPath = args.get("dicPath");
	DicAddress dictAddress = DicAddress.getInstance();
	
}

}
