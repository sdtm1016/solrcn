package org.nlp.store;

public class Graph<E> {
	public boolean[][] edges;// edges 边
	public E[] labels;// 顶点对应标签
	public int size;
	public Graph(int n) {
		this.size=n;
		this.edges = new boolean[size][size];
		this.labels = (E[]) new Object[size];

	}
	// 连接两个顶点 =加边
	public void addEdge(int first, int second) {
		edges[first][second] = true;
	}

	public E getLabels(int vertex) {
		return labels[vertex];
	}

	public void setLabels(int vertex, E label) {
		this.labels[vertex] = label;
	}

	// 得到该顶点的所有连接点
	public int[] neighbors(int vertex) {
		int count = 0;
		// neighbors count
		for (int i = 0; i < size; i++) {
			if (edges[vertex][i])
				count++;
		}
		int neighbor[] = new int[count];
		int k = 0;
		for (int i = 0; i < size; i++) {
			if (edges[vertex][i])
				neighbor[k++] = i;
		}
		return neighbor;

	}

	// 顶点标签总数，也就是顶点总数
	public int getSize() {
		return size;
	}

	/*
	 * depth-first 深度优先： 发现一条路一直走到终点，
	 * 再返回到最深的有分支的 点再进行其他分支的搜索，
	 * 直到搜索完毕 
	 * 步骤：
	 * 1、标记顶点
	 * 2、移动到顶点一个未被标记的连接点 next.
	 * 3、如果next 没有连接点，则回溯到上一层 ，如果回溯到起始点，
	 * 所以不能再回溯到其他顶点，遍历结束
	 */

	public void InitDepthFirst() {
		int size = this.getSize();
		boolean marked[] = new boolean[size];
		depthFirst(0, marked);

	}

	public void depthFirst(int vertex, boolean[] marked) {
		int[] neighbor = this.neighbors(vertex);
		marked[vertex] = true;// 1、标记顶点，
		System.out.print(this.getLabels(vertex) + "--> ");
		for (int next : neighbor) {
			if (!marked[next])// 2、移动到顶点一个未被标记的连接点
				depthFirst(next, marked);
		}

	}

	/*
	 * breadth-first 广度优先：
	 * 从开始节点a找与它相邻的节点 全部节点,
	 * 假设有b,c,d,e，然后再找b所有未遍历的相邻的
	 * 节点，然后再找c的，依次进行下去
	 * 
	 * 具体操作步骤：
	 *  1、从队列前端删除一个顶点v 
	 *  2、对于v未标记的邻接点u:标记u为true,
	 *  然后将 将 u放入队列中
	 */
	public void InitBreadthFirst() {
		int size = this.getSize();
		boolean marked[] = new boolean[size];
		JQueue<Integer> queue = new JQueue<Integer>(size);
		queue.enQueue(0);
		marked[0] = true;
		breadthFirst(0, marked, queue);

	}

	public void breadthFirst(int vertex, boolean[] marked, JQueue<Integer> queue) {
		int[] neighbor = this.neighbors(vertex);
		queue.deQueue();// 1、从队列前端删除一个顶点v
		System.out.print(this.getLabels(vertex) + "--> ");
		for (int n : neighbor) {
			if (!marked[n]) {
				marked[n] = true;// 2、 标记u=true,然后将 将 u放入队列中
				queue.enQueue(n);
			}
		}
		if (!queue.isEmpty())// 直到队列为空，结束递归
			breadthFirst(queue.peek(), marked, queue);

	}

	public static void main(String[] args) {
		Graph<String> graph = new Graph<String>(7);
		for (int i = 0; i < graph.getSize(); i++) {
			graph.setLabels(i, "V" + i);
		}
		
		/*0-1计算语言学
		 *	   编号:词(POS)
		 *   1.计算语言学(0) 2.有(1) 3.意思(2)
		 *   4.计算(0) 5.语言学(1) 6.有(2) 意思(3)
		 *   计算(0) 7.语言(1) 8.学(2) 有(3) 意思(4)   
		 *   
		 */
		graph.addEdge(0, 1);
		graph.addEdge(0, 4);
		graph.addEdge(1, 2);
		graph.addEdge(2, 3);
		graph.addEdge(3, 4);

		
		
		graph.InitDepthFirst();
		System.out.println();
		graph.InitBreadthFirst();

	}

}
