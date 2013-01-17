package org.nlp.lucene;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class MaxWordTokenizer extends Tokenizer {

	private CharTermAttribute termAtt;
	private static final int IO_BUFFER_SIZE = 4096;
	private char[] ioBuffer = new char[IO_BUFFER_SIZE];

	private boolean done;
	private int i = 0; // i是用来控制起始位置的变量
	private int upto = 0;

	protected MaxWordTokenizer(Reader input) {
		super(input);
		termAtt = (CharTermAttribute) addAttribute(CharTermAttribute.class);
		this.done = false;
	}

	public void resizeIOBuffer(int newSize) {
		if (ioBuffer.length < newSize) {
			final char[] newCharBuffer = new char[newSize];
			System.arraycopy(ioBuffer, 0, newCharBuffer, 0, ioBuffer.length);
			ioBuffer = newCharBuffer;
		}
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (!done) {
			clearAttributes();
			done = true;
			upto = 0;
			i = 0;
			while (true) {
				final int length = input.read(ioBuffer, upto, ioBuffer.length
						- upto);
				if (length == -1) {
					break;
				}
				upto += length;
				if (upto == ioBuffer.length) {
					resizeIOBuffer(upto * 2);
				}
			}

			if (i < upto) {
				char[] word = null; // dic.matchLong(ioBuffer,i,upto);正向最大匹配的结果
				if (word != null) {// 已经匹配上了
					termAtt.copyBuffer(word, 0, word.length);
					++i;
				}
				return true;

			}

			return false;
		}
		return false;
	}

}
