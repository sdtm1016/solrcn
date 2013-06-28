import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
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
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.NoLockFactory;
import org.apache.lucene.util.Version;

import cc.solr.lucene.store.hdfs.HdfsDirectory;

public class LuceneHDFSMarged {

	public static void Index() throws IOException {
		IndexWriter writer;
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_43,
				new StandardAnalyzer(Version.LUCENE_43));
		iwc.setMergePolicy(NoMergePolicy.NO_COMPOUND_FILES);
		iwc.setRAMBufferSizeMB(512);
		HdfsDirectory directory = new HdfsDirectory("hdfs://master:9000/user/hadoop");
				
		try {
			directory.setLockFactory(NoLockFactory.getNoLockFactory());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			writer = new IndexWriter(directory, iwc);
			ArrayList<Document> docs = new ArrayList<Document>();
			long count = 0;
			long tagTime = System.currentTimeMillis();
			for (int j = 0; j < 100; j++) {
				for (int i = 0; i < 100000; i++) {
					Document doc = new Document();
					doc.add(new StringField("id", "id" + i, Field.Store.YES));
					doc.add(new StringField("title", "title" + i,
							Field.Store.YES));
					docs.add(doc);
					count++;
					// writer.addDocument(doc);
				}
				writer.addDocuments(docs);
				docs.clear();
				System.out
						.format("add data [%s] ok... %s ms,%sper/sec\n",
								count,
								(System.currentTimeMillis() - tagTime),
								(count * 1000 / (System.currentTimeMillis() - tagTime)));
			}

			tagTime = System.currentTimeMillis();
			System.out.println("start commit... ");
			writer.commit();
			System.out.println("commit ok... "
					+ (System.currentTimeMillis() - tagTime));

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Version matchVersion = Version.LUCENE_43;
//		Index();
		System.out.println("done....");
		HdfsDirectory directory = new HdfsDirectory("hdfs://master:9000/user/hadoop/index");
		IndexReader reader;
		try {
			reader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer(matchVersion);
			String field = "id";
			String keyword = "*:*";
			QueryParser parser = new QueryParser(matchVersion, field, analyzer);
			Query query = parser.parse(keyword);
			TopDocs results = searcher.search(query, 5 * 10);
			ScoreDoc[] hits = results.scoreDocs;
			int numTotalHits = results.totalHits;
			System.out.println(numTotalHits + " total matching documents");
			hits = searcher.search(query, numTotalHits).scoreDocs;
			Document doc = searcher.doc(hits[hits.length - 1].doc);
			String path = doc.get("id");
			System.out.println(path);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
