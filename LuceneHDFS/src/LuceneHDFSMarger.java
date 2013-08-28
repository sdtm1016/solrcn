import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import cc.solr.lucene.hdfsro.WHdfsDirectory;

public class LuceneHDFSMarger {

	public static void DownloadIndex() throws IOException, ParseException{
		Version matchVersion = Version.LUCENE_43;
		System.out.println("start....");
		long tagTime = System.currentTimeMillis();
		WHdfsDirectory hdfsDir = new WHdfsDirectory("hdfs://master:9000/user/KmaDou/index1");
//		hdfsDir.setLockFactory(NoLockFactory.getNoLockFactory());
		IndexWriterConfig iwc = new IndexWriterConfig(matchVersion, null);
		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		Directory locDir = new SimpleFSDirectory(new File("e:/index"));

		IndexWriter indexWriter = new IndexWriter(locDir, iwc);
//		 IndexReader hdfsReader = DirectoryReader.open(hdfsDir);
//		 indexWriter.addIndexes(hdfsReader);
		indexWriter.addIndexes(hdfsDir);
		indexWriter.forceMerge(1);
		// indexWriter.prepareCommit();
		// hdfsDir.close();
		// indexWriter.commit();
		indexWriter.close();
		System.out.println("done...." + (System.currentTimeMillis() - tagTime));
		IndexReader reader = DirectoryReader.open(locDir);
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
	}
	
	
	public static void main(String[] args) throws IOException, ParseException {
		DownloadIndex();

	}
}
