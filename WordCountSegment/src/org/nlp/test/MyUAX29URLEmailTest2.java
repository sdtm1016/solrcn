package org.nlp.test;

public class MyUAX29URLEmailTest2 {

	public static void main(String[] args) {
		char a='>';
		char b='》';
		char c='c';
		char d='磊';
		char e='（';
		char f='(';
		
	    Character.UnicodeBlock ub;
	    
	    ub = Character.UnicodeBlock.of(a);
	    System.out.println(" a:"+a+" &"+(char)a+"& "+ub.toString());
	    
	    ub = Character.UnicodeBlock.of(b);
	    System.out.println(" b:"+a+" &"+(char)b+"& "+ub.toString());
	    
	    ub = Character.UnicodeBlock.of(c);
	    System.out.println(" c:"+c+" &"+(char)c+"& "+ub.toString());
	    
	    ub = Character.UnicodeBlock.of(d);
	    System.out.println(" d:"+d+" &"+(char)d+"& "+ub.toString());
	    
	    ub = Character.UnicodeBlock.of(e);
	    System.out.println(" e:"+e+" &"+(char)e+"& "+ub.toString());
	    
	    ub = Character.UnicodeBlock.of(f);
	    System.out.println(" f:"+f+" &"+(char)f+"& "+ub.toString());
		
	}
}
