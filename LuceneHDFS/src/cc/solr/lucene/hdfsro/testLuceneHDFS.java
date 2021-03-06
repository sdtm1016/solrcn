package cc.solr.lucene.hdfsro;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.NoLockFactory;
import org.apache.lucene.util.Version;

public class testLuceneHDFS {

	public static void Index() {
		IndexWriter writer;
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_43, new WhitespaceAnalyzer(Version.LUCENE_43));
//		iwc.setMergePolicy(NoMergePolicy.NO_COMPOUND_FILES);
		iwc.setRAMBufferSizeMB(512);

		WHdfsDirectory directory = null;
		try {
			directory = new WHdfsDirectory("hdfs://master:9000/index2");
			directory.setLockFactory(NoLockFactory.getNoLockFactory());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			writer = new IndexWriter(directory, iwc);
			ArrayList<Document> docs = new ArrayList<Document>();
			long count = 0;
			long tagTime = System.currentTimeMillis();
			for (int j = 0; j < 1; j++) {
				for (int i = 0; i < 100000; i++) {
					Document doc = new Document();
					doc.add(new StringField("id", "" + i, Field.Store.YES));
					doc.add(new StringField("section", "" + i, Field.Store.YES));
					doc.add(new StringField("title", "title" + i, Field.Store.YES));
					doc.add(new TextField("desctrip", "中华 人民 共和国", Field.Store.YES));
					doc.add(new TextField(
							"text",
							"MaxSkipLevels is the max. number of skip levels stored for each term in the .frq file. A low value results in smaller indexes but less acceleration, a larger value results in slightly larger indexes but greater acceleration. See format of .frq file for more information about skip levels.",
							Field.Store.YES));
					doc.add(new LongField("_version_", tagTime, Field.Store.YES));
					docs.add(doc);
					count++;
					// writer.addDocument(doc);
				}
				writer.addDocuments(docs);
				docs.clear();
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

	public static void main(String[] args) throws IOException, ParseException {
//		Index();
		System.out.println("done....");
		WHdfsDirectory directory = new WHdfsDirectory("hdfs://master:9000/user/KmaDou/index1");
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
		String field = "id";
		String keyword = "*:*";
		QueryParser parser = new QueryParser(Version.LUCENE_43, field, analyzer);
		Query query = parser.parse(keyword);
		TopDocs results = searcher.search(query, 5 * 10);
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents");
		long tagTime = System.currentTimeMillis();
		hits = searcher.search(query, numTotalHits).scoreDocs;
		System.out.println(System.currentTimeMillis() - tagTime);
		for (int i = 0; i < 2; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String path = doc.get("id");
			System.out.println(path);
		}

	}
}
