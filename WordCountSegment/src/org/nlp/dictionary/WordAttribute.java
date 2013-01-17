/**
 * 
 */
package org.nlp.dictionary;

import java.io.Serializable;

/**
 * @author Tony
 *
 */
public class WordAttribute implements Serializable{

	private static final long serialVersionUID = 471373305894716377L;
	/**
	 * 词频
	 */
	private long freq;
	/**
	 * 词性
	 */
	private String[] attri;
	/**
	 * 词语的首个单字
	 */
	
	public WordAttribute(long freq, String[] attri){
		this.freq = freq;
		this.attri = attri;
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
}
