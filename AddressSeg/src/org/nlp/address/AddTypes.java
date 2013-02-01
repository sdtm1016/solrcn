package org.nlp.address;

import java.util.Iterator;

public class AddTypes implements Iterable<AddTypes.AddressTypeInf> {
	public static class Node {
		public AddressTypeInf item;
		public Node next;

		Node(AddressTypeInf item) {
			this.item = item;
			next = null;
		}
	}

	public static class AddressTypeInf {
		public AddressType pos; // 类型
		public int weight = 0; // 频率
		public long code; // 语义编码

		public AddressTypeInf(AddressType p, int f, long semanticCode) {
			pos = p;
			weight = f;
			code = semanticCode;
		}

		public String toString() {
			return pos + ":" + weight;
		}
	}

	private Node head, tail;

	public AddTypes() {
		head = null;
		tail = null;
	}

	public void put(AddressTypeInf item) {
		Node t = tail;
		tail = new Node(item);
		if (head == null)
			head = tail;
		else
			t.next = tail;
	}

	public void insert(AddressTypeInf item) {
		// one element
		if (head == tail) {
			if (head.item.pos.compareTo(item.pos) > 0) {
				Node t = head;
				head = new Node(item);
				head.next = t;
			} else {
				Node t = tail;
				tail = new Node(item);
				t.next = tail;
			}
			return;
		}
		Node t = head;

		while (t.next != null) {
			if (t.next.item.pos.compareTo(item.pos) > 0)
				break;
			t = t.next;
		}
		Node p = t.next;
		t.next = new Node(item);
		t.next.next = p;
	}

	public Node getHead() {
		return head;
	}

	public AddressTypeInf findType(AddressType toFind) {
		if (head == null)
			return null;
		Node t = head;
		while (t != null && t.item != null) {
			if (t.item.pos.equals(toFind)) {
				return t.item;
			}
			t = t.next;
		}

		return null;
	}

	/**
	 * @return
	 */
	public int size() {
		int count = 0;

		// if (head == null)
		// return count;
		Node t = head;
		while (t != null) {
			count++;
			t = t.next;
		}

		return count;
	}

	public int totalCost() {
		int cost = 0;

		Node t = head;
		while (t != null) {
			cost += t.item.weight;
			t = t.next;
		}

		return cost;
	}

	public Iterator<AddTypes.AddressTypeInf> iterator() {
		// System.out.println("iterator");
		return new LinkIterator(head);
	}

	/** Adapter to provide descending iterators via ListItr.previous */
	private class LinkIterator implements Iterator<AddTypes.AddressTypeInf> {
		Node itr;

		public LinkIterator(Node begin) {
			itr = begin;
		}

		public boolean hasNext() {
			return itr != null;
		}

		public AddressTypeInf next() {
			Node cur = itr;
			itr = itr.next;
			return cur.item;
		}

		public void remove() {
			// itr.remove();
		}
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();
		Node pCur = head;

		while (pCur != null) {
			buf.append(pCur.item.toString());
			buf.append(' ');
			pCur = pCur.next;
		}

		return buf.toString();
	}

}
