/**
 * 
 */
package org.nlp.dictionary;

import java.io.Serializable;

import org.nlp.dictionary.LastPart;
import org.nlp.dictionary.WordAttribute;



/**
 * @author Tony
 *
 */
public class PartTwo implements Serializable{

	private static final long serialVersionUID = -4850542697922185370L;
	
	private boolean flag;
	private WordAttribute wordAttri;
	private LastPart lastPart;
	
	public PartTwo(boolean flag, LastPart lastPart, WordAttribute wordAttri){
		this.flag = flag;
		this.lastPart = lastPart;
		this.wordAttri = wordAttri;
	}


	public boolean isFlag() {
		return flag;
	}


	public void setFlag(boolean flag) {
		this.flag = flag;
	}


	public WordAttribute getWordAttri() {
		return wordAttri;
	}

	public void setWordAttri(WordAttribute wordAttri) {
		this.wordAttri = wordAttri;
	}

	public LastPart getLastPart() {
		return lastPart;
	}

	public void setLastPart(LastPart lastPart) {
		this.lastPart = lastPart;
	}
}
