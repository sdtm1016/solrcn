/*
 * Created on 2004-8-28
 * change log:
 * add ' to SEPERATOR to fix bug  'it'
 * 
 * Add process error
 */
package org.nlp.address;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

/**
 * 
 * 分词的输入，输出类
 * 
 */
public class AddTokenizer extends Tokenizer {
	private CharTermAttribute termAtt;// 词属性
	private OffsetAttribute offsetAtt;// 位置属性
	private TypeAttribute typeAtt;// 类型属性
	private static final int IO_BUFFER_SIZE = 4096;
	private char[] ioBuffer = new char[IO_BUFFER_SIZE];

	private boolean done;
	private int tokenEnd = 0;
	private int tokenStart = 0;
	ArrayList<AddressToken> al;

	public AddTokenizer(Reader reader) {
		super(reader);
		this.typeAtt = addAttribute(TypeAttribute.class);
		this.offsetAtt = addAttribute(OffsetAttribute.class);
		this.termAtt = addAttribute(CharTermAttribute.class);
		this.done = false;
	}

	public void resizeIOBuffer(int newSize) {
		if (ioBuffer.length < newSize) {
			// Not big enough; create a new array with slight
			// over allocation and preserve content
			final char[] newCharBuffer = new char[newSize];
			System.arraycopy(ioBuffer, 0, newCharBuffer, 0, ioBuffer.length);
			ioBuffer = newCharBuffer;
		}
	}

	@Override
	public boolean incrementToken() throws IOException {	 
		clearAttributes();
		if (tokenEnd < al.size()) {
			AddressToken token = al.get(tokenEnd);
			// termAtt.setTermBuffer(token.termText);
			termAtt.setEmpty().append(token.termText);
			offsetAtt.setOffset(token.start, token.end);
			typeAtt.setType(token.type.toString());
			++tokenEnd;
			return true;
		}

//		if (!done) {
			
			done = true;
			tokenStart = 0;
			tokenEnd = 0;
			while (true) {
				final int length = input.read(ioBuffer, tokenStart,
						ioBuffer.length - tokenStart);
				if (length == -1)
					break;
				tokenStart += length;
				if (tokenStart == ioBuffer.length)
					resizeIOBuffer(tokenStart * 2);
//			}
			
			if (ioBuffer.length > 0){
			
			String companyName = new String(ioBuffer, 0, tokenStart);

//			al = AddressTagger.basicTag(companyName);
			}
		}

		return false;
	}

	@Override
	public void reset() throws IOException {
		tokenStart = tokenEnd = 0;

	}

	@Override
	public void end() {
		// set final offset
		final int finalOffset = correctOffset(tokenEnd);
		offsetAtt.setOffset(finalOffset, finalOffset);
	}
}
