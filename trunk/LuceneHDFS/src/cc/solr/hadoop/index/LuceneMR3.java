package cc.solr.hadoop.index;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.util.hash.Hash;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NoLockFactory;
import org.apache.lucene.util.Version;

import cc.solr.lucene.hdfsro.WHdfsDirectory;

public class LuceneMR3 extends Configured implements Tool {

	public static class MapClass extends Mapper<LongWritable, Text, NullWritable, NullWritable> {

		ArrayList<Document> docs = new ArrayList<Document>();
		Directory ramDirectory;
		WHdfsDirectory directory;
		String outputDir;
		IndexWriter writer;
		IndexWriter ramWriter;
		String fileName;
		Version matchVersion = Version.LUCENE_43;

		@Override
		public void setup(Context context) throws IOException {
			java.util.Random r=new java.util.Random(); 
			Hash instance = Hash.getInstance(Hash.MURMUR_HASH);			
			String hash = String.valueOf(Math.abs(instance.hash((System.currentTimeMillis() + "_" + r.nextLong()).getBytes())));
			outputDir = context.getConfiguration().get("mapred.output.dir") + "/index_" + hash;
			InputSplit inputSplit = context.getInputSplit();
			fileName = ((FileSplit) inputSplit).getPath().toString();

			IndexWriterConfig iwc = new IndexWriterConfig(matchVersion, new CJKAnalyzer(matchVersion));
			iwc.setMaxBufferedDocs(Integer.MAX_VALUE);
			iwc.setRAMBufferSizeMB(512);
			directory = new WHdfsDirectory("hdfs://master:9000/"+outputDir);
			directory.setLockFactory(NoLockFactory.getNoLockFactory());
			writer = new IndexWriter(directory, iwc);
		}

		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			Document doc = new Document();

			doc.add(new TextField("id", fileName + key.toString(), Field.Store.YES));
			String[] str = value.toString().split("\t");

			for (int i = 0; i < str.length; i++) {
				if (str[i].trim().isEmpty())
					continue;
				doc.add(new TextField("data_" + i + "_cjk", str[i], Field.Store.YES));
			}
			
			doc.add(new LongField("_version_", 1L, Field.Store.YES));

			docs.add(doc);

			if (docs.size() > 10000) {
				writer.addDocuments(docs);
				docs.clear();
			}
		}

		@Override
		public void cleanup(Context context) throws IOException {

			if (docs.size() > 0) {
				writer.addDocuments(docs);
				docs.clear();
			}

			writer.commit();
			writer.close();
		}
	}

	public static class Reduce extends Reducer<Text, Text, Text, IntWritable> {
		public int max = -1;

		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			max = -1;
			for (Text val : values) {
				String[] str = val.toString().split("\t", -2);
				if (Integer.parseInt(str[3]) > max)
					max = Integer.parseInt(str[3]);
			}
			context.write(new Text(key), new IntWritable(max));
		}
	}

	public static class AgePartitioner extends Partitioner<Text, Text> {
		@Override
		public int getPartition(Text key, Text value, int numReduceTasks) {
			String[] str = value.toString().split("\t");
			int age = Integer.parseInt(str[1]);
			if (numReduceTasks == 0) {
				return 0;
			}
			if (age <= 20)
				return 0;
			else if (age > 20 && age <= 50)
				return 1 % numReduceTasks;
			else
				return 2 % numReduceTasks;
		}
	}

	@Override
	public void setConf(Configuration conf) {

	}

	@Override
	public Configuration getConf() {
		return new Configuration();
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage: LuceneMR <in> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "Lucene Index");
		job.setJarByClass(LuceneMR3.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		job.setMapperClass(MapClass.class);
		job.setMapOutputKeyClass(NullWritable.class);
		job.setMapOutputValueClass(NullWritable.class);
		// //job.setCombinerClass(Reduce.class);
		// job.setPartitionerClass(AgePartitioner.class);
		// job.setReducerClass(Reduce.class);
		job.setNumReduceTasks(0);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(NullOutputFormat.class);

		// job.setOutputFormatClass(TextOutputFormat.class);
		// job.setOutputKeyClass(NullWritable.class);
		// job.setOutputValueClass(NullWritable.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new LuceneMR3(), args);
		System.exit(res);
	}

}
