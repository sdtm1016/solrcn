import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class LuceneHDFSSearcher {
	private IndexReader reader;
	private IndexSearcher searcher;	
	
	public LuceneHDFSSearcher(FSDirectory dir) throws CorruptIndexException,
			IOException {
		reader = DirectoryReader.open(dir);
		searcher = new IndexSearcher(reader);
	}

	public Document search(String value) throws CorruptIndexException,
			IOException {
		Query query = null;
		Term term = new Term("zip", value);
		query = new TermQuery(term);

		Document doc = null;
		TopDocs hits = searcher.search(query, 1);
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			doc = searcher.doc(scoreDoc.doc);
		}
		return doc;
	}

	public void close() throws IOException {
		// 关闭对象		
		reader.close();
	}

	public static void main(String arg[]) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		String index= "hdfs://master:9000/index/";
//		FSDirectory dir = new FSDirectory(fs, new Path( "hdfs://125.223.118.44:9000/IndexFiles/"), false, conf);
		FSDirectory dir = FSDirectory.open(new File(index));
		// System.out.println(dir.getLockID().toString());
		LuceneHDFSSearcher se = new LuceneHDFSSearcher(dir);
		String value = "";
		Document doc = se.search(value);
		se.close();
	}
}