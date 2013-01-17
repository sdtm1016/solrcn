/**
 * 
 */
package org.nlp.impl;

/**
 * @author Tony
 *
 */
public class Sentence {

	private boolean isNeedSplit = false;
	
	private String sentence;
	
	
	public Sentence(String sentence){
		this.sentence = sentence;
	}
	
	public Sentence(String sentence, boolean isNeedSplit){
		this.sentence = sentence;
		this.isNeedSplit = isNeedSplit;
	}

	public boolean isNeedSplit() {
		return isNeedSplit;
	}

	public void setNeedSplit(boolean isNeedSplit) {
		this.isNeedSplit = isNeedSplit;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
}
