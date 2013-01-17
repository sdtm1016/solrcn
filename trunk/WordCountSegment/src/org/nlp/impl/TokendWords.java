/**
 * 
 */
package org.nlp.impl;

/**
 * @author Tony
 * 
 */
public class TokendWords {

	private String word;
	private long freq;
	private String[] attri;
	private int length;
	private int pos;
	public int start;
	public int end;

	public TokendWords(String word, long freq, String[] attri, int length,
			int pos, int start) {
		this.word = word;
		this.freq = freq;
		this.attri = attri;
		this.length = length;
		this.pos = pos;
		this.start = start + 1;
		this.end = start + length;
	}

	public String getWord() {
		return word;
	}

	public char[] next() {
		return word.toCharArray();
	}

	public void setWord(String word) {
		this.word = word;
	}

	public long getFreq() {
		return freq;
	}

	public void setFreq(long freq) {
		this.freq = freq;
	}

	public String[] getAttri() {
		return attri;
	}

	public void setAttri(String[] attri) {
		this.attri = attri;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

}
