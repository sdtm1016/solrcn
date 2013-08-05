package cc.solr.test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.fs.FileSystem;
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
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.NoLockFactory;
import org.apache.lucene.util.Version;

import cc.solr.hadoop.util.HDFSUtil;
import cc.solr.lucene.hdfsro.WHdfsDirectory;

public class LuceneHDFSIndexAndDownLoad {
	final static Version matchVersion = Version.LUCENE_43;
	
	public static void Index(String HDFSURL,String IndexPath) throws IOException {
		IndexWriter writer;
		IndexWriterConfig iwc = new IndexWriterConfig(matchVersion,
				new StandardAnalyzer(matchVersion));
		iwc.setMergePolicy(NoMergePolicy.NO_COMPOUND_FILES);
		iwc.setRAMBufferSizeMB(512);
		WHdfsDirectory directory = new WHdfsDirectory("hdfs://test0:9000/index2");
				
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
					doc.add(new StringField("title", "title" + i + "01234567890123456789012345678901234567890123456789", Field.Store.YES));
					docs.add(doc);
					count++;
				}
				writer.addDocuments(docs);
//				docs = new ArrayList<Document>();
				docs.clear();
				System.out .format("add data [%s] ok... %s ms,%sper/sec\n",
						count,
						(System.currentTimeMillis() - tagTime),
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
	
	public static void search() throws IOException{
		WHdfsDirectory directory = new WHdfsDirectory("hdfs://192.168.0.50:9000/index");
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

	public static void downloadIndex(Directory ... dirs) throws IOException{
		IndexWriterConfig iwc = new IndexWriterConfig(matchVersion, new StandardAnalyzer(matchVersion));
		iwc.setMergePolicy(NoMergePolicy.NO_COMPOUND_FILES);
		iwc.setRAMBufferSizeMB(512);
		IndexWriter writer = new IndexWriter(dirs[dirs.length-1], iwc);
		
		for (int i = 0; i < dirs.length-1; i++) {
			writer.addIndexes(dirs[i]);
		}
		writer.commit();
	}
	
	public static void main(String[] args) throws IOException {
		String HDFSUrl = "hdfs://test0:9000";
		String IndexPath = "index2";
		String locPath = "e:/index";
		FileSystem fs = HDFSUtil.getFileSystem("192.168.0.50", 9000);
		HDFSUtil.rmdirs(fs, IndexPath);
		Index(HDFSUrl,IndexPath);
		Index(HDFSUrl,"index4");
		System.out.println("done....");
		
		WHdfsDirectory hdfsDir = new WHdfsDirectory("hdfs://test0:9000/index2");
		WHdfsDirectory hdfsDir1 = new WHdfsDirectory("hdfs://test0:9000/index4");
		NIOFSDirectory locDir = new NIOFSDirectory(new File(locPath));
		downloadIndex(hdfsDir,hdfsDir1,locDir);
//		HDFSUtil.download(fs, "e:/index", IndexPath);

	}
}
