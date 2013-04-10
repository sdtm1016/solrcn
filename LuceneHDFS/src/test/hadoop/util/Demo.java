package test.hadoop.util;
import org.apache.hadoop.fs.FileSystem;
import test.hadoop.util.HDFSUtil;
public class Demo {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		FileSystem fs = HDFSUtil.getFileSystem("master", 9000);
		// FsUtil.listConfig(fs);
		String dirName = "demo.txt";
		// FsUtil.mkdirs(fs, dirName);
		// FsUtil.rmdirs(fs, dirName);
		// FsUtil.upload(fs, "D:/help/js", dirName);
		// FsUtil.download(fs, "D:/help/js2", dirName);
		HDFSUtil.write(fs, dirName, "test-1\n");
		HDFSUtil.append(fs, dirName, "test-2\n");
		System.out.println(HDFSUtil.getLength(fs, "demo.txt"));
		//		
		 String content = HDFSUtil.read(fs, dirName);
		 
		//
		 System.out.println(content);
		HDFSUtil.listFile(fs, "/");
//		HDFSUtil.listNode(fs);
//		HDFSUtil.download(fs, "z:/index", "/");
		fs.close();
	}
}
