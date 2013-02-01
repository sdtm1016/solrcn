/*
 * Created on 2005-9-7
 *
 */
package org.nlp.address;

/**
 *  邻接矩阵表示的切分词图
     * @author luogang
     * @2010-3-18
 */
import java.util.Iterator;

public class AdjList {
	private AddressTokenLinkedList list[];// AdjList的图的结构
	public int verticesNum;

	/**
	 * Constructor: Allocates right amount of space
	 */
	public AdjList(int verticesNum) {
		this.verticesNum = verticesNum;
		list = new AddressTokenLinkedList[verticesNum];
		
		// initialize all of the linked lists in the array
		for (int index = 0; index < verticesNum; index++) {
			list[index] = new AddressTokenLinkedList();
		}
	}

	/**
	 * add an edge to the graph
	 */
	public void addEdge(AddTokenInf newEdge) {
		list[newEdge.end].put(newEdge);
	}

	/**
	 * Returns an iterator that contains the Edges leading to adjacent nodes
	 */
	public Iterator<AddTokenInf> getPrev(int vertex) {
		AddressTokenLinkedList ll = list[vertex];
		if(ll == null)
			return null;
		return ll.iterator();
	}

	/**
	 * Feel free to make this prettier, if you'd like
	 */
	public String toString() {
		StringBuilder temp = new StringBuilder();

		for (int index = 0; index < verticesNum; index++) {
			if(list[index] == null)
			{
				continue;
			}
			temp.append("node:");
			temp.append(index);
			temp.append(": ");
			temp.append(list[index].toString());
			temp.append("\n");
		}

		return temp.toString();
	}
}
