package org.nlp.ngram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.nlp.impl.TokendWords;

public class allword {
  HashMap<String,Double> data;
  
  // List<TokendWords> tokens;
  
  public allword() {
    // tokens = new ArrayList<TokendWords>();
    // data = datafile("data/SogouLabDic.dic");
    data = new HashMap<String,Double>();
    data.put("意思", 1.0);
    data.put("语言学", 1.0);
    data.put("语言", 1.0);
//    data.put("计算", 1.0);
//    data.put("计算语言学", 1.0);
  }
  
  public HashMap<String,Double> datafile(String name) {
    String line;
    String[] word;
    BufferedReader br;
    HashMap<String,Double> data = new HashMap<String,Double>();
    try {
      br = new BufferedReader(new FileReader(name));
      line = br.readLine();
      while (line != null) {
        word = line.split("\t");
        data.put(word[0], Double.parseDouble(word[1]));
        line = br.readLine();
      }
      br.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return data;
  }
  
  public List<TokendWords> backwardSegment(String text) {
    List<TokendWords> result = new ArrayList<TokendWords>();
    int maxLen = 5;
    int textLen = text.length();
    String key = "";
    int offset=textLen;
    int pos = 0;
    for (int i = offset; i > 0; i--) {
      
      int end = Math.max(i - maxLen, 0);
      for (int j = i - 2; j >= end; j--) {
        key = text.substring(j, i);
        if (data.get(key) != null) {
          pos++;
          TokendWords tws=new TokendWords(key,1l, new String[]{"word"}, i-j, pos,j);
          result.add(tws);// 添加到列表
          offset=j;
          
        }
      }
      if (i <= offset){
        pos++;
        key=text.substring(i - 1, i);
        TokendWords tws=new TokendWords(key,1l, new String[]{"unknown"}, 1, pos,i-1);
        result.add(tws);
        
      }
      
    }
    Collections.reverse(result);
    return result;
  }
  
  public List<TokendWords> forwardSegment(String text) {
    List<TokendWords> result = new ArrayList<TokendWords>();
    int maxLen = 5;
    int textLen = text.length();
    String key = "";
    int offset=0;
    int pos=0;
    for (int i = offset; i < textLen; i++) {
      int count = 0;
      int end = Math.min(i + maxLen, textLen);
      for (int j = i + 2; j <= end; j++) {
        key = text.substring(i, j);
        if (data.get(key) != null) {
          pos++;
          TokendWords tws=new TokendWords(key,1l, new String[]{"word"}, j-i, pos, i);
          result.add(tws);// 添加到列表
          offset=j;
          
        }
      }
      if (i>=offset){
        pos++;
        key=text.substring(i, i + 1);
        TokendWords tws=new TokendWords(key,1l, new String[]{"unknown"}, 1, pos, i);
        result.add(tws);
        
      }
      
    }
    return result;
  }
  
  public static void main(String[] args) {
    
    String src = "计算语言学屌丝意思";
    allword mw = new allword();
    
    List<TokendWords> dst =  mw.forwardSegment(src);
    for (int n = 0; n < dst.size(); n++) {
    	System.out.format("%s:%s,pos:%s\n", dst.get(n).getAttri()[0],dst.get(n).getWord(),dst.get(n).getPos());
    	
	}
            
  }
}
