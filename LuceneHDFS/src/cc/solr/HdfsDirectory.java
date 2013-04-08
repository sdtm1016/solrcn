package cc.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.IndexOutput;
import org.apache.lucene.store.LockFactory;

public class HdfsDirectory extends Directory {

	Path path;
	FileSystem fs;

	public HdfsDirectory() {
		this.fs = getFileSystem("localhost", 9000);
		this.path = fs.getWorkingDirectory();
	}

	public void ensurePath(String path){
		if (path.isEmpty()){
			this.path = fs.getWorkingDirectory();
		}else if (path.startsWith("/")){
			this.path = new Path(path);
		}else{
			this.path = new Path(fs.getWorkingDirectory() + "/" + path);
		}
		try {
			if (!fs.exists(this.path))
				fs.mkdirs(this.path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HdfsDirectory(String path) {
		this.fs = getFileSystem("localhost", 9000);
		ensurePath(path);
	}

	public HdfsDirectory(String url, String path) {
		Configuration config = new Configuration();
		config.set("fs.default.name", url);
		try {
			this.fs = FileSystem.get(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ensurePath(path);
	}	
	
	public synchronized static FileSystem getFileSystem(String ip, int port) {

		FileSystem fs = null;
		String url = "hdfs://" + ip + ":" + String.valueOf(port);
		Configuration config = new Configuration();
		config.set("fs.default.name", url);
		try {
			fs = FileSystem.get(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fs;
	}
	
	@Override
	public void close() throws IOException {
		fs.close();
	}

	@Override
	public IndexOutput createOutput(String name, IOContext arg1)
			throws IOException {
		// TODO Auto-generated method stub
		return new HdfsIndexOutput(fs, path, name);
	}
	
	@Override
	public void deleteFile(String fileName) throws IOException {
		try {
			fs.delete(new Path(this.path +"/" + fileName), true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean fileExists(String fileName) throws IOException {
		return fs.exists(new Path(this.path +"/" + fileName));
	}

	@Override
	public long fileLength(String fileName) throws IOException {
		return fs.getFileStatus(new Path(this.path +"/" + fileName)).getLen();
	}

	@Override
	public String[] listAll() throws IOException {
		return listAll(this.fs,this.path);
	}

	public String[] listAll(FileSystem fs, Path path) {
		ArrayList<String> dirList = new ArrayList<String>();
		try {
			String relativePath = "";
			FileStatus[] fList = fs.listStatus(path);
			for (FileStatus f : fList) {
				if (null != f) {
					relativePath = new StringBuffer()
							.append(f.getPath().getParent()).append("/")
							.append(f.getPath().getName()).toString();
					if (f.isDir()) {
						continue;
					} else {
						dirList.add(relativePath);						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
//		System.out.println(dirList);
		return dirList.toArray(new String[dirList.size()]);
	}
	
	@Override
	public IndexInput openInput(String name, IOContext arg1) throws IOException {
		return new HdfsIndexInput(fs, path, name);
	}
	
	@Override
	public void sync(Collection<String> arg0) throws IOException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void setLockFactory(LockFactory lockFactory) throws IOException {
		super.setLockFactory(lockFactory);
	}

}
