package cc.solr.lucene.hdfsro;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.store.IndexInput;

public class HdfsIndexInput extends IndexInput {

	protected FileSystem fs;
	protected Path file;
	protected FSDataInputStream dis;
	protected long length = 0L;
	protected boolean isOpen = false;

	protected HdfsIndexInput(FileSystem fs, Path path, String name) {
		super(name);
		this.fs = fs;
		this.file = new Path(path + "/" + name);
		
		try {
			if (fs.exists(this.file)) {
				dis = fs.open(file);
				this.length = fs.getFileStatus(file).getLen();
				isOpen = true;
			}else{
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		if (isOpen)
			dis.close();
	}

	@Override
	public long getFilePointer() {
		try {
			if (isOpen)
				return dis.getPos();
			else
				return 0;
		} catch (IOException e) {
			return 0;
		}
	}

	@Override
	public long length() {
		return this.length;
	}

	@Override
	public void seek(long pos) throws IOException {
		if (isOpen)
			dis.seek(pos);
	}

	@Override
	public byte readByte() throws IOException {
		
		if (isOpen) {			
			byte readByte = dis.readByte();
			return readByte;
		} else {
			return 0;
		}
	}

	@Override
	public void readBytes(byte[] b, int offset, int len) throws IOException {
		if (isOpen){
			dis.read(b, offset, len);
		}
			
	}

}
