package org.nlp.dictionary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.nlp.dictionary.WordAttribute;


public class LastPart implements Serializable  {

	private static final long serialVersionUID = -2002574674068692770L;
	
	private ArrayList<String> lastParts = new ArrayList<String>();
	private HashMap<Integer,WordAttribute> wordsRecord = new HashMap<Integer,WordAttribute>();
	private int maxLength=0;
	
	public LastPart(String words, WordAttribute wordAttri){
		if(maxLength<words.length())
			maxLength=words.length();
		lastParts.add(words);
		wordsRecord.put(lastParts.size()-1, wordAttri);
	}
	
	public void addRecord(String words, WordAttribute wordAttri){
		if(maxLength<words.length())
			maxLength=words.length();
		lastParts.add(words);
		wordsRecord.put(lastParts.size()-1, wordAttri);
	}

	public ArrayList<String> getLastParts() {
		return lastParts;
	}

	public void setLastParts(ArrayList<String> lastParts) {
		this.lastParts = lastParts;
	}

	public HashMap<Integer, WordAttribute> getWordsRecord() {
		return wordsRecord;
	}

	public void setWordsRecord(HashMap<Integer, WordAttribute> wordsRecord) {
		this.wordsRecord = wordsRecord;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
}
