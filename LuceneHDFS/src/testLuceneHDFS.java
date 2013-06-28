import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.LogByteSizeMergePolicy;
import org.apache.lucene.index.LogDocMergePolicy;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.index.MergePolicy;
import org.apache.lucene.index.MergeScheduler;
import org.apache.lucene.index.NoMergePolicy;
import org.apache.lucene.index.TieredMergePolicy;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NoLockFactory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import cc.solr.lucene.store.hdfs.HdfsDirectory;

public class testLuceneHDFS {



	public static void main(String[] args) throws IOException {
		Version matchVersion = Version.LUCENE_43;
		System.out.println("start....");
		long tagTime = System.currentTimeMillis();
		HdfsDirectory hdfsDir = new HdfsDirectory("hdfs://master:9000/user/hadoop/index");
		
		hdfsDir.setLockFactory(NoLockFactory.getNoLockFactory());
		
		TieredMergePolicy tieredMergePolicy = new TieredMergePolicy();
		tieredMergePolicy.setUseCompoundFile(true);
		IndexWriterConfig iwc = new IndexWriterConfig(matchVersion, new CJKAnalyzer(matchVersion));
		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		iwc.setMergePolicy(NoMergePolicy.NO_COMPOUND_FILES);
		iwc.setMergePolicy(tieredMergePolicy);
		iwc.setRAMBufferSizeMB(512);
		iwc.setMaxBufferedDocs(Integer.MAX_VALUE);  
		
		Directory ramDir = new RAMDirectory();
		IndexWriter ramWriter = new IndexWriter(ramDir, iwc);
		IndexReader reader = DirectoryReader.open(hdfsDir);
		ramWriter.addIndexes(reader);
//		ramWriter.forceMerge(2);
		ramWriter.commit();
		
		IndexReader ramReader = DirectoryReader.open(ramDir);
		
		Directory locDir = new SimpleFSDirectory(new File("z:/index"));
		
		
		IndexWriter indexWriter = new IndexWriter(locDir, iwc);
//		IndexReader reader = DirectoryReader.open(directory);
//		indexWriter.addIndexes(reader);
		indexWriter.addIndexes(ramDir);
//		indexWriter.forceMerge(1);
		indexWriter.prepareCommit();
		hdfsDir.close();
		indexWriter.commit();
		indexWriter.close();
		System.out.println("done...." + (System.currentTimeMillis() - tagTime));
//		IndexReader reader = DirectoryReader.open(directory);
//			IndexSearcher searcher = new IndexSearcher(reader);
//			Analyzer analyzer = new StandardAnalyzer(matchVersion);
//			String field = "id";
//			String keyword = "*:*";
//			QueryParser parser = new QueryParser(matchVersion, field, analyzer);
//			Query query = parser.parse(keyword);
//			TopDocs results = searcher.search(query, 5 * 10);
//			ScoreDoc[] hits = results.scoreDocs;
//			int numTotalHits = results.totalHits;
//			System.out.println(numTotalHits + " total matching documents");
//			hits = searcher.search(query, numTotalHits).scoreDocs;
//			Document doc = searcher.doc(hits[hits.length - 1].doc);
//			String path = doc.get("id");
//			System.out.println(path);
	

	}
}
