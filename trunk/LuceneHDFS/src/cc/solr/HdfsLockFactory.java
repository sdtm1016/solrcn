package cc.solr;

import java.io.IOException;

import org.apache.lucene.store.Lock;
import org.apache.lucene.store.LockFactory;

public class HdfsLockFactory extends LockFactory {

	@Override
	public Lock makeLock(String lockName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearLock(String lockName) throws IOException {
		// TODO Auto-generated method stub

	}

}
