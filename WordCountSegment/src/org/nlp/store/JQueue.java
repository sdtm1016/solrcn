package org.nlp.store;

import java.util.NoSuchElementException;

/*
 * 队列数组实现
 *  Create Queue by Array
 */
public class JQueue<E> {
	public E[] data;
	public int size;
	public int front;
	public int rear;
	int capacity = 10;

	public JQueue() {
		data = (E[]) new Object[capacity];
		size = 0;
	}

	public JQueue(int capacity) {
		if (capacity < 0)
			throw new IllegalArgumentException("capacity < 0");
		data = (E[]) new Object[capacity];
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E enQueue(E e) {
		if (size == data.length) {
			// capacity = 2 * capacity
			if (rear == 0) {
				E[] bigger = (E[]) new Object[data.length * 2];
				for (int i = front; i < data.length - front; i++) {
					bigger[i] = data[i];
				}
				bigger[data.length] = e;
				rear = data.length;
				capacity = 2 * capacity;
				front = 0;
				data = bigger;

			} else {
				// capacity = 2 * capacity
				E[] bigger = (E[]) new Object[data.length * 2];
				for (int i = front; i < data.length - front + 1; i++) {
					bigger[i - front] = data[i];
				}
				for (int i = 0; i < rear; i++) {
					bigger[i + data.length - 1] = data[i];
				}
				data = bigger;
				front = 0;
				rear = data.length;
				capacity = 2 * capacity;
			}

		}
		if (size == 0) {
			front = 0;
			rear = 0;
		}
		data[rear] = e;
		rear = (rear + 1) % capacity;
		size++;
		return e;
	}

	// Delete the top
	public E deQueue() {
		if (size == 0) {
			throw new NoSuchElementException("Queue underflow");
		}
		E e = data[front];
		data[front] = null;
		front = (front + 1) % capacity;
		size--;
		return e;
	}
	//
	public E peek() {
		if (size == 0) {
			throw new NoSuchElementException("Queue underflow");
		}
		E e = data[front];
		return e;
	}
}
