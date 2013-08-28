package org.nlp.lucene;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.nlp.impl.TokenImpl;

/**
 * Factory for {@link LowerCaseFilter}.
 * 
 * <pre class="prettyprint" >
 * &lt;fieldType name="text_address" class="solr.TextField" positionIncrementGap="100"&gt;
 *   &lt;analyzer&gt;
 *     &lt;tokenizer class="solr.WhitespaceTokenizerFactory"/&gt;
 *     &lt;filter class="solr.AddressFilterFactory"/&gt;
 *   &lt;/analyzer&gt;
 * &lt;/fieldType&gt;
 * </pre>
 * 
 */
public class NewWordsFilterFactory extends TokenFilterFactory {
	protected NewWordsFilterFactory(Map<String, String> args) {
		super(args);
		String dicPath = args.get("dicPath");
		if (dicPath == null || dicPath.isEmpty()) {
			dicPath = "SogouLabDic.dic";
		}
		TokenImpl.initDic(dicPath);
	}

	public NewWordsFilter create(TokenStream input) {
		return new NewWordsFilter(input);
	}

}
