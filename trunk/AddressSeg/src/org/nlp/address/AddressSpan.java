package org.nlp.address;

/**
 * 
     * 用来操作AddressType.type类和并给其定义规则
     * @author Administer
     * @2010-3-18
 */

public class AddressSpan {
	public int length;
	public AddressType type;
	
	public AddressSpan(int l,AddressType t)
	{
		length = l;
		type = t;
	}
	
	public String toString()
	{
		return type+":"+length;
	}
}
