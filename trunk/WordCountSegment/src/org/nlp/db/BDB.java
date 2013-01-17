package org.nlp.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

public class BDB {
	private Environment env;

	public Environment getEnv() {
		return env;
	}

	private Database db;

	public BDB() {
	}

	/**
	 * 构建数据库的开发环境
	 * 
	 * @param path
	 *            数据库开发环境的目录
	 * @param cacheSize
	 *            配置缓存大小
	 */
	public void setUp(String path, long cacheSize) {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		// 当设置为true时，说明若没有数据库的环境时，可以打开。否则就不能打开
		envConfig.setAllowCreate(true);
		// envConfig.setReadOnly(true);
		envConfig.setCacheSize(cacheSize);
		// envConfig.setCachePercent(50);
		// 设置事务
		// envConfig.setTransactional(true);
		// 当提交事务的时候是否把缓存中的内容同步到磁盘中去。true 表示不同步，也就是说不写磁盘
		// envConfig.setTxnNoSync(true);
		// 当提交事务的时候，是否把缓冲的log写到磁盘上,true 表示不同步，也就是说不写磁盘
		// envConfig.setTxnWriteNoSync(true);
		// 设置单个日至文件大小
		envConfig.setConfigParam("je.log.fileMax", "4096000000");
		try {
			env = new Environment(new File(path), envConfig);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构建数据库
	 * 
	 * @param dbName
	 *            数据库的名称
	 */
	public void open(String dbName) {
		DatabaseConfig dbConfig = new DatabaseConfig();
		// 设置数据的是否可以创建的属性
		dbConfig.setAllowCreate(true);
		try {
			db = env.openDatabase(null, dbName, dbConfig);
			env.cleanLog();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭berkeley db
	 */
	public void close() {
		try {
			if (db != null) {
				db.close();
			}
			if (env != null) {
				env.cleanLog();
				env.close();
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过key得到数据
	 * 
	 * @param key
	 *            berkeley db中的key
	 * @return
	 * @throws Exception
	 */
	public String get(String key) throws Exception {
		DatabaseEntry queryKey = new DatabaseEntry();
		DatabaseEntry value = new DatabaseEntry();
		queryKey.setData(key.getBytes("UTF-8"));
		OperationStatus status = db
				.get(null, queryKey, value, LockMode.DEFAULT);
		if (status == OperationStatus.SUCCESS) {
			return new String(value.getData(), "utf-8");
		}
		return null;
	}

	public byte[] getByte(String Key) throws Exception {
		byte[] theKey = Key.getBytes("UTF-8");
		DatabaseEntry queryKey = new DatabaseEntry();
		DatabaseEntry value = new DatabaseEntry();
		queryKey.setData(theKey);
		OperationStatus status = db
				.get(null, queryKey, value, LockMode.DEFAULT);
		if (status == OperationStatus.SUCCESS) {
			return value.getData();
		}
		return null;
	}

	/**
	 * 向berkeley db中存入数据
	 * 
	 * @param key
	 *            存入berkeley db时的键
	 * @param value
	 *            存入berkeley db时的值
	 * @return
	 * @throws Exception
	 */
	public boolean put(String key, String value) throws Exception {
		byte[] theKey = key.getBytes("UTF-8");
		byte[] theValue = value.getBytes("UTF-8");
		/**
		 * Berkeley DB中的记录包括两个字段，就是键和值，
		 * 并且这些键和值都必须是com.sleepycat.je.DatabaseEntry类的实例。
		 */
		OperationStatus status = db.put(null, new DatabaseEntry(theKey),
				new DatabaseEntry(theValue));
		if (status == OperationStatus.SUCCESS) {
			return true;
		}
		return false;
	}

	/**
	 * 向berkeley db中存入对象
	 * 
	 * @param key
	 *            存入berkeley db时的键
	 * @param object
	 *            存入berkeley db时的值
	 * @return
	 * @throws Exception
	 */
	public boolean putObj(String key, Object object) {
		try {
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(o);
			out.writeObject(object);
			out.flush();
			out.close();
			putByte(key.getBytes("UTF-8"), o.toByteArray());
			o.close();
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 向berkeley db中存入对象
	 * 
	 * @param key
	 *            存入berkeley db时的键
	 * @param object
	 *            存入berkeley db时的值
	 * @throws Exception
	 */
	public boolean putObjZip(String key, Object object) {
		try {
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			GZIPOutputStream gzout = new GZIPOutputStream(o);
			ObjectOutputStream out = new ObjectOutputStream(gzout);
			out.writeObject(object);
			out.flush();
			out.close();
			putByte(key.getBytes("UTF-8"), o.toByteArray());
			o.close();
			gzout.close();
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 通过key得到对象
	 * 
	 * @param key
	 *            berkeley db中的key
	 * @param gzip
	 *            是否经过压缩
	 * @return
	 * @throws Exception
	 */
	public Object getObj(String key) {
		Object object = null;
		try {
			byte[] data = getByte(key);
			ByteArrayInputStream i = new ByteArrayInputStream(data);
			ObjectInputStream in = new ObjectInputStream(i);
			object = in.readObject();
			i.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return (object);
		}
		return (object);
	}

	/**
	 * 通过key得到对象
	 * 
	 * @param key
	 *            berkeley db中的key
	 * @return
	 * @throws Exception
	 */
	public Object getObjZip(String key, boolean gzip) {
		Object object = null;
		try {
			byte[] data = getByte(key);
			ByteArrayInputStream i = new ByteArrayInputStream(data);
			GZIPInputStream gzin = new GZIPInputStream(i);
			ObjectInputStream in = new ObjectInputStream(gzin);
			object = in.readObject();
			i.close();
			gzin.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return (object);
		}
		return (object);
	}

	private boolean putByte(byte[] theKey, byte[] theValue) throws Exception {
		// byte[] theKey = key.getBytes("UTF-8");
		// byte[] theValue = value.getBytes("UTF-8");
		/**
		 * Berkeley DB中的记录包括两个字段，就是键和值，
		 * 并且这些键和值都必须是com.sleepycat.je.DatabaseEntry类的实例。
		 */
		OperationStatus status = db.put(null, new DatabaseEntry(theKey),
				new DatabaseEntry(theValue));
		if (status == OperationStatus.SUCCESS) {
			return true;
		}
		return false;
	}

	/**
	 * 通过
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean del(String key) throws Exception {
		DatabaseEntry queryKey = new DatabaseEntry();
		queryKey.setData(key.getBytes("UTF-8"));
		OperationStatus status = db.delete(null, queryKey);
		if (status == OperationStatus.SUCCESS) {
			return true;
		}
		return false;
	}

	public void list() throws DatabaseException {
		Cursor cursor = null;
		DatabaseEntry foundKey = new DatabaseEntry();
		DatabaseEntry foundData = new DatabaseEntry();
		try {
			cursor = db.openCursor(null, null);
			foundKey.setData("wm2".getBytes("UTF-8"));
			OperationStatus retVal = cursor.getSearchKeyRange(foundKey,
					foundData, LockMode.DEFAULT);
			System.out.println(cursor.count());
			while (retVal == OperationStatus.SUCCESS) {
				String keyString = new String(foundKey.getData(), "UTF-8");
				String dataString = new String(foundData.getData(), "UTF-8");

				System.out.println("Key | Data : " + keyString + " | "
						+ dataString.length() + "");
				System.out.println("Key: " + keyString);
				retVal = cursor.getNextDup(foundKey, foundData,
						LockMode.DEFAULT);
			}

		} catch (DatabaseException e) {
			System.err.println("Error accessing database." + e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}

	}

}
