import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.PostingsFormat;
import org.apache.lucene.codecs.bloom.BloomFilteringPostingsFormat;
import org.apache.lucene.codecs.bloom.DefaultBloomFilterFactory;
import org.apache.lucene.codecs.lucene41.Lucene41PostingsFormat;
import org.apache.lucene.codecs.lucene42.Lucene42Codec;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.NoMergePolicy;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.NoLockFactory;
import org.apache.lucene.util.Version;

import cc.solr.lucene.store.hdfs.HdfsDirectory;

public class LuceneHDFSMarged {

	public static void Index() throws IOException {
		IndexWriter writer;
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_43, new StandardAnalyzer(Version.LUCENE_43));
		iwc.setMergePolicy(NoMergePolicy.NO_COMPOUND_FILES);
		iwc.setRAMBufferSizeMB(512);
		iwc.setCodec(new Lucene42Codec() {
			BloomFilteringPostingsFormat postingOptions = new BloomFilteringPostingsFormat(new Lucene41PostingsFormat(), new DefaultBloomFilterFactory());

			@Override
			public PostingsFormat getPostingsFormatForField(String field) {
				return postingOptions;
			}
		});
		HdfsDirectory directory = new HdfsDirectory("hdfs://sc1:9000/user/hadoop/index3");

		try {
			directory.setLockFactory(NoLockFactory.getNoLockFactory());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			writer = new IndexWriter(directory, iwc);
			long count = 0;
			long tagTime = System.currentTimeMillis();
			ArrayList<Document> docs;
			for (int j = 0; j < 10; j++) {
				docs = new ArrayList<Document>();
				for (int i = 0; i < 10000; i++) {
					Document doc = new Document();
					doc.add(new StringField("id", "id" + i, Field.Store.YES));
					doc.add(new StringField("title", "title" + i, Field.Store.YES));
					doc.add(new LongField("time", System.currentTimeMillis() + i, Field.Store.YES));
					doc.add(new NumericDocValuesField("time", System.currentTimeMillis()));
					docs.add(doc);
					count++;
					// writer.addDocument(doc);
				}
				writer.addDocuments(docs);
				if (count % 5000000 == 0)
					writer.commit();
				// docs.clear();
				System.out.format("add data [%s] ok... %s ms,%sper/sec\n", count, (System.currentTimeMillis() - tagTime),
						(count * 1000 / (System.currentTimeMillis() - tagTime)));
			}

			tagTime = System.currentTimeMillis();
			System.out.println("start commit... ");
			writer.commit();
			System.out.println("commit ok... " + (System.currentTimeMillis() - tagTime));

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void search() throws IOException {
		Version matchVersion = Version.LUCENE_43;
		HdfsDirectory directory = new HdfsDirectory("hdfs://sc1:9000/user/hadoop/index3");
		IndexReader reader;
		try {
			reader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer(matchVersion);
			String field = "id";
			String keyword = "*:*";
			QueryParser parser = new QueryParser(matchVersion, field, analyzer);
			Query query = parser.parse(keyword);
			SortField sortField = new SortField("time", Type.LONG);
			sortField.missingValue = Long.MAX_VALUE;
			Sort sort = new Sort(sortField);

			TopFieldDocs results = searcher.search(query, 5 * 10, sort);

			// TopDocs results = searcher.search(query, 5 * 10);
			ScoreDoc[] hits = results.scoreDocs;
			int numTotalHits = results.totalHits;
			System.out.println(numTotalHits + " total matching documents");
			for (int i = 1; i <= hits.length; i++) {
				Document doc = searcher.doc(hits[hits.length - i].doc);
				String path = doc.get("id");
				String time = doc.get("time");
				System.out.println(path + "\t" + time);
			}

			// hits = searcher.search(query, 200).scoreDocs;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Index();
		System.out.println("done....");
		search();

	}
}
