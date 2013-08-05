package cc.solr;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class SolrMR extends Configured implements Tool {

	public static class MapClass extends Mapper<LongWritable, Text, NullWritable, NullWritable> {

		ArrayList<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		CloudSolrServer[] servers;
		int collectionNums = 1;
		String fileName;
		String zkHost = "localhost:2181";
		String collection = "collection";
		int shard = 0;
		int shardNums = 6;
		Calendar calendar = Calendar.getInstance();
		long timeInMillis;
		final long day = 86400000;
		
		public void commit(){

		}
		
		@Override
		public void setup(Context context) {
			zkHost = context.getConfiguration().get("zkhost","localhost:2181");
			collectionNums = context.getConfiguration().getInt("collectionNums", 1);
			shardNums = context.getConfiguration().getInt("shardNums", 1);
			servers = new CloudSolrServer[collectionNums];
			calendar.set(2013, 6, 1, 0, 0, 0);
			timeInMillis = calendar.getTimeInMillis();
			
			InputSplit inputSplit = context.getInputSplit();
			fileName = ((FileSplit) inputSplit).getPath().toString();
			
			try {
				for (int i = 0; i < servers.length; i++) {
					servers[i] = new CloudSolrServer(zkHost);
					servers[i].setDefaultCollection(collection+(i+1));					
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			long rows = Long.parseLong(key.toString());
			int shardid = (int) ((rows % shardNums) + 1);
			long boost = timeInMillis + ((shardid-1) * day) + rows % 86400;
			
			SolrInputDocument doc = new SolrInputDocument();
			doc.setField("id", fileName + key.toString());			
			doc.setField("_shard_", "shard" + shardid);
			doc.setDocumentBoost(rows);
			doc.setField("time_dt", new Date(boost));
			doc.setField("rows_s", rows);
			
			String[] str = value.toString().split("\t");

			for (int i = 0; i < str.length; i++) {
				if(str[i].trim().isEmpty())
					continue;
				doc.setField("data_" + i + "_cjk", str[i]);
			}
			
			docs.add(doc);

			if (docs.size() > 1000) {
				
				try {
					
					for (int i = 0; i < servers.length; i++) {
						long now = i*day*shardNums;					
						for (int j = 0,len = docs.size(); j < len; j++) {
							long newtime = (long) docs.get(i).getDocumentBoost();
							docs.get(i).setDocumentBoost(now + newtime);
							docs.get(i).setField("time_dt", new Date(now+newtime));
						}
						servers[i].add(docs, 15000);
					}
					
				} catch (SolrServerException e) {
					e.printStackTrace();
				}
				
				docs.clear();
				
			}
		}

		@Override
		public void cleanup(Context context) {
			if (docs.size() > 0) {

				try {
					for (int i = 0; i < servers.length; i++) {
						servers[i].add(docs, 15000);
					}
				} catch (SolrServerException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				docs.clear();
			}
			
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

	public int run(String[] args) throws Exception {
		String ver = "1";
		Configuration conf = getConf();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length < 2) {
			System.err.println("Usage: SolrMR <in> <out> [zkHost] [collectionNums] [shardNums]");
			System.exit(2);
		}
		conf.set("zkhost", otherArgs[2]);
		conf.set("collectionNums", otherArgs[3]);
		conf.set("shardNums", otherArgs[4]);
		Job job = new Job(conf, "Solr Index v"+ver);
		job.setJarByClass(SolrMR.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		job.setMapperClass(MapClass.class);		
		// job.setMapOutputKeyClass(NullWritable.class);
		// job.setMapOutputValueClass(NullWritable.class);
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
		int res = ToolRunner.run(new Configuration(), new SolrMR(), args);
		System.exit(res);
	}

}
