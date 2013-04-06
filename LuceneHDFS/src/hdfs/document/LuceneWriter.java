package hdfs.document;
 
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogDocMergePolicy;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
 
public class LuceneWriter {
     
    private Path perm;
    private Path temp;
    private FileSystem fs;
    private IndexWriter writer;
     
    public void open(JobConf job, String name) throws IOException{
        this.fs = FileSystem.get(job);
        perm = new Path(FileOutputFormat.getOutputPath(job), name); 
         
        // 临时本地路径，存储临时的索引结果
        temp = job.getLocalPath("index/_" + Integer.toString(new Random().nextInt()));
        fs.delete(perm, true);
         
        // 配置IndexWriter(个人对Lucene索引的参数不是太熟悉)
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_41);
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_41, analyzer);
        conf.setMaxBufferedDocs(100000);        
        LogMergePolicy mergePolicy = new LogDocMergePolicy();
        mergePolicy.setMergeFactor(100000);
        mergePolicy.setMaxMergeDocs(100000);
        conf.setMergePolicy(mergePolicy);       
        conf.setRAMBufferSizeMB(256);
        conf.setMergePolicy(mergePolicy);       
         
        writer = new IndexWriter(FSDirectory.open(new File(fs.startLocalOutput(perm, temp).toString())),
                                conf);
    }
    public void close() throws IOException{
        // 索引优化和IndexWriter对象关闭
        writer.commit();
        writer.close();
         
        // 将本地索引结果拷贝到HDFS上
        fs.completeLocalOutput(perm, temp);
        fs.createNewFile(new Path(perm,"index.done"));      
    }
     
    /*
    * 接受HDFSDocument对象，从中读取信息并建立索引
    */
    public void write(HDFSDocument doc) throws IOException{
 
        String key = null;
        HashMap<String, String> fields = doc.getFields();
        Iterator<String> iter = fields.keySet().iterator();
        while(iter.hasNext()){
            key = iter.next();
                         
            Document luceneDoc = new Document();
             
            // 如果使用Field.Index.ANALYZED选项，则默认情况下会对中文进行分词。
            // 如果这时候采用Term的形式进行检索，将会出现检索失败的情况。
            luceneDoc.add(new StringField("key", key, Field.Store.YES));
            luceneDoc.add(new StringField("value", fields.get(key), Field.Store.YES));                      
            writer.addDocument(luceneDoc);
        }
    }
}