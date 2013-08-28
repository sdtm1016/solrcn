package cc.solr.lucene.store;

import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.core.CachingDirectoryFactory.CloseListener;
import org.apache.solr.core.DirectoryFactory;

import cc.solr.lucene.store.hdfs.HdfsDirectory;

public class HdfsDirectoryFactory extends DirectoryFactory {

	  HdfsDirectory hdfsDirectory;
	  
	  public static final String HDFS_HOME = "solr.hdfs.home";
	  
	  public static final String CONFIG_DIRECTORY = "solr.hdfs.confdir";
	  
	  private SolrParams params;
	  
	  private String hdfsDataDir;
	  
	  private String confDir;
	  
	@Override
	public void init(NamedList args) {
	    params = SolrParams.toSolrParams(args);
	    this.hdfsDataDir = params.get(HDFS_HOME);
	    if (this.hdfsDataDir != null && this.hdfsDataDir.length() == 0) {
	      this.hdfsDataDir = null;
	    }
	}

	@Override
	public void doneWithDirectory(Directory directory) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addCloseListener(Directory dir, CloseListener closeListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws IOException {
		hdfsDirectory.close();
	}

	@Override
	protected Directory create(String path, DirContext dirContext) throws IOException {
		hdfsDirectory = new HdfsDirectory(path);
		return hdfsDirectory;
	}

	@Override
	public boolean exists(String path) throws IOException {
		return hdfsDirectory.fileExists(path);
	}

	@Override
	public void remove(Directory dir) throws IOException {
		remove(dir, false);
	}

	@Override
	public void remove(Directory dir, boolean afterCoreClose) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String path, boolean afterCoreClose) throws IOException {
		hdfsDirectory.deleteFile(path);
	}

	@Override
	public void remove(String path) throws IOException {
		remove(path, false);
	}

	@Override
	public Directory get(String path, DirContext dirContext, String rawLockType) throws IOException {
		// TODO Auto-generated method stub
		return hdfsDirectory;
	}

	@Override
	public void incRef(Directory directory) {
		// TODO Auto-generated method stub

	}

	  @Override
	  public boolean isAbsolute(String path) {
	    return path.startsWith("hdfs:/");
	  }
	  
	  @Override
	  public boolean isPersistent() {
	    return true;
	  }

	@Override
	public void release(Directory directory) throws IOException {
		// TODO Auto-generated method stub

	}

}
