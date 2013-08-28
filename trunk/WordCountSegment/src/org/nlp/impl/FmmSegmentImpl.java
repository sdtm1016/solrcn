package org.nlp.impl;
import java.util.*;

public class FmmSegmentImpl {

	Object[] rootNode = new Object[65536];
	
	public void addNode(char[] term) {
		Object[] Node = rootNode;
		for (char edge : term) {//每个字符是edge
			if (Node[edge] == null)
				Node[edge] = new Object[65536];
			Node = (Object[]) Node[edge];
		}
	}
	
	public List<String> FMM(char[] sentance) {
		List<String> termlist = new ArrayList<String>(sentance.length >> 1);
		for (int start = 0, end; start < sentance.length;){
			end = TrieFMMEnd(sentance, start);
			if (end == start) {
				String singleterm = new String(sentance, start, 1);
				termlist.add(singleterm);
				start++;
			} else {
				String newterm = new String(sentance, start, end - start);
				termlist.add(newterm);
				start = end;
			}
		}
		return termlist;
	}
	
	public int TrieFMMEnd(char[] sentance, int i) {
		for (Object[] Node = rootNode; i < sentance.length && (Node = (Object[]) Node[sentance[i]]) != null; )
			i++;
		return i;
	}

	public static void main(String[] args) {
		String[] terms = { "张", "华", "平", "说", "的", "确", "实", "在", "理", "的确", "确实", "实在", "在理", "张华", "张华平"};
		FmmSegmentImpl segment = new FmmSegmentImpl();
		for (String term : terms)
			segment.addNode(term.toCharArray());
		System.out.println(segment.FMM("张华平说的确实在理".toCharArray()));
	}
}