/*
 * Created on 2005-9-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.nlp.address;

/**
 * 
 * 有向边，边的权重不能是负数。
 * 
 * This class models an edge from vertex X to vertex Y -- pretty
 * straightforward.
 * 
 * It is a little wasteful to keep the starting vertex, since the adjacency list
 * will do this for us -- but it makes the code neater in other places (makes
 * the Edge independent of the Adj. List
 */
public class AddTokenInf {
	public String termText;
	public AddTypes data;
	public int start;
	public int end;
	public double logProb;
    
	public AddTokenInf(int vertexFrom, int vertexTo, String word,AddTypes d,double p) {
		start = vertexFrom;
		end = vertexTo;
		termText = word;
		data = d;
		logProb = p;
		//if(d!=null){
		//	cost = d.totalCost();
		//}
	}
	
	public String toString() {
		return "text:" + termText + " start:" + start + " end:" + end
				 +"--------data:"+data;
	}
}