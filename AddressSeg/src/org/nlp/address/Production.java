package org.nlp.address;

import java.util.ArrayList;

/**
 * A production is used in a context-sensitive grammar.
 * each production has the form ��A�� �� ���æ� 
 *
 */
public class Production {
	public ArrayList<AddressType> lhs; //left-hand side symbol.
	public ArrayList<AddressType> rhs; //right-hand side symbol(s).
	
	/**
     * Creates a Production where lhs generates rhs (lhs -&gt; rhs<subscript>1</subscript>...rhs<subscript>n</subscript>).
     *
	 * @param lhs  The symbol on the left-hand side of the generation operator.
	 * @param rhs  The symbol(s) on the right-hand side of the generation operator.
	 */
    public Production(ArrayList<AddressType> lhs, ArrayList<AddressType> rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}
    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
	public boolean equals(Object obj){
		if (obj instanceof Production 
				&& toString().equals(obj.toString())) {
				return true;
		}
		return false;
	}

    /**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return toString().hashCode();
	}

    /**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = lhs + " ->";
		for (int i = 0; i < rhs.size(); i++) {
			s += " " + rhs.get(i);
		}		
		return s;
	}
}
