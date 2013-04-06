package cc.solr;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.store.IndexOutput;

public class HdfsIndexOutput extends IndexOutput {

	protected FileSystem fs;
	protected Path file;
	protected FSDataOutputStream dos;
	protected long length = 0L;

	public HdfsIndexOutput(FileSystem fs, Path path, String name) {
		this.fs = fs;
		this.file = new Path(path + "/" + name);
		try {
			if (fs.exists(this.file)) {
				this.dos = fs.append(this.file);
			} else {
				this.dos = fs.create(this.file);
			}
			this.length = fs.getFileStatus(file).getLen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		dos.close();
	}

	@Override
	public void flush() throws IOException {
		dos.flush();
	}

	@Override
	public long getFilePointer() {
		try {
			return dos.getPos();
		} catch (IOException e) {
			return 0;
		}
	}

	@Override
	public long length() throws IOException {
		return this.length;
	}

	@Override
	@Deprecated
	public void seek(long pos) throws IOException {
		System.out.println("I want to seek" + pos +"....");
		// TODO Auto-generated method stub
	}

	@Override
	public void writeByte(byte b) throws IOException {
		dos.writeByte(b);
	}

	@Override
	public void writeBytes(byte[] b, int offset, int len) throws IOException {
		dos.write(b, offset, len);
	}

}
