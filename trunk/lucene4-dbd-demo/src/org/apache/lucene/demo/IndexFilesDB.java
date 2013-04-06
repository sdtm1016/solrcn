package org.apache.lucene.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.je.JEDirectory;
import org.apache.lucene.util.Version;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.Transaction;

public class IndexFilesDB {
	public static void main(String[] argv) throws DatabaseException,
			CorruptIndexException, LockObtainFailedException, IOException {
		Random rnd = new Random();
		long cacheSize = 1024000;
		String path = "d:/bdb";
		EnvironmentConfig envConfig = new EnvironmentConfig();
		DatabaseConfig dbConfig = new DatabaseConfig();
		envConfig.setTransactional(true);
		envConfig.setAllowCreate(true);
		dbConfig.setTransactional(true);
		dbConfig.setAllowCreate(true);
		dbConfig.setOverrideBtreeComparator(true);
		envConfig.setCacheSize(cacheSize);
		
		Environment env = new Environment(new File(path), envConfig);
		Transaction txn = env.beginTransaction(null, null);
		Database index = env.openDatabase(txn, "__index__", dbConfig);
		Database blocks = env.openDatabase(txn, "__blocks__", dbConfig);
		txn.commit();
		txn = env.beginTransaction(null, null);
		JEDirectory directory = new JEDirectory(txn, index, blocks);
		
		//just for lucene3.x
//		@SuppressWarnings("deprecation")
//		IndexWriter writer = new IndexWriter(directory, new StandardAnalyzer( Version.LUCENE_CURRENT), true,IndexWriter.MAX_TERM_LENGTH);
		
		//update for lucene4.x
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_41, new StandardAnalyzer( Version.LUCENE_41));
		
		IndexWriter writer = new IndexWriter(directory, iwc);
	
		
		
		long tagTime = System.currentTimeMillis();
		int count = 0;
		for (int i = 0; i < 10; i++) {
			Collection<Document> docs = new ArrayList<Document>();
			for (int j = 0; j < 100000; j++) {
				Document doc = new Document();
				String seed = System.currentTimeMillis()+ "_" + rnd.nextInt(99999999);
				doc.add(new StringField("id", "id" + count, Field.Store.YES));
				doc.add(new StringField("title", "title" + seed+ seed, Field.Store.YES));
				docs.add(doc);
//				writer.addDocument(doc);
				count++;
			}
			writer.addDocuments(docs);
			System.out.format("add %s data ok... %s ms\n", count,
					count*1000/(System.currentTimeMillis() - tagTime));
		}
		
		

		tagTime = System.currentTimeMillis();		
//		writer.optimize();
		System.out.format("optimize ok... %s ms\n",
				(System.currentTimeMillis() - tagTime));
		writer.close();
		directory.close();
		tagTime = System.currentTimeMillis();
		txn.commit();
		System.out.format("txn commit ok... %s ms\n",
				(System.currentTimeMillis() - tagTime));

		index.close();
		blocks.close();
		env.close();
		System.out.println("Indexing Complete");

	}
}
