/**
 * 
 */
package org.nlp.dictionary;

import java.io.Serializable;
import java.util.HashMap;

import org.nlp.dictionary.PartTwo;
import org.nlp.dictionary.WordAttribute;


/**
 * @author Tony
 *
 */
public class PartOne implements Serializable{

	private static final long serialVersionUID = -1577955674206508974L;
	
	private boolean flag;
	private WordAttribute wordAttri;
	private HashMap<String,PartTwo> nextWord;
	
	public PartOne(boolean flag, HashMap<String,PartTwo> nextWord, WordAttribute wordAttri){
		this.flag = flag;
		this.nextWord = nextWord;
		this.wordAttri = wordAttri;
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public HashMap<String, PartTwo> getNextWord() {
		return nextWord;
	}
	public void setNextWord(HashMap<String, PartTwo> nextWord) {
		this.nextWord = nextWord;
	}

	public WordAttribute getWordAttri() {
		return wordAttri;
	}

	public void setWordAttri(WordAttribute wordAttri) {
		this.wordAttri = wordAttri;
	}
}
