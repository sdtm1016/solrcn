package org.nlp.test;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.nlp.lucene.patch.MyUAX29URLEmailAnalyzer;


public class MyUAX29URLEmailTest1 {
	
	public void test1() throws IOException{
		
	
		/*String str="我确定我写的算法iphone是下面那， Performance Monitoring - http://sematext.com/spm/index.htmlSearch " +
				" On Tue, Dec 25, 2012 at 9:13 PM, Mark Miller <[EMAIL PROTECTED]> wrote: test@example.org 你好 test@example.org";*/
		
		/*String str="张磊,感谢您申请北京北方银证软件开发有限公司的高级JAVA工程师（北京）发件人：" +
				"中华英才网<ApplyCV_79@mychinahr.com>eqewrwer 是第三代www   *&::;;；；；；时间：2010-05-17 08:33:03收件人：rfv1116@tom.com<rfv1116@tom.com>";
		*/
		String str="Java的中文处理学习笔记：Hello Unicode(2) (笔记by 车东)" +
		"www.chedong.com/tech/hello_unicode_2.html - 网页快照2007年4月12日 – char[1]='e' byte=101 short=101 BASIC_LATIN ... 但从相应的UNICODE MAPPING和SHORT值我们可以知道字符是正确的中文 ...";
		
		str="张一凡的电话13699191946,在上海市浦东新区耀华路99弄16号10402,上海市徐汇区东新路99弄38号402";
		
		System.out.println(str);
		MyUAX29URLEmailAnalyzer analyzer=new MyUAX29URLEmailAnalyzer(Version.LUCENE_40);
		//UAX29URLEmailAnalyzer analyzer=new UAX29URLEmailAnalyzer(Version.LUCENE_40);
		
		TokenStream tokenStream=analyzer.tokenStream("", new StringReader(str));
		
		
		
		   CharTermAttribute termAtt = (CharTermAttribute) tokenStream.getAttribute(CharTermAttribute.class);
		   tokenStream.reset();
		while(tokenStream.incrementToken()){
			String token = new String(termAtt.buffer(),0,termAtt.length());
	        System.out.println("token:"+token);
	        
		}
		tokenStream.close();
	}
	public static void main(String[] args) throws IOException {
		MyUAX29URLEmailTest1 test=new MyUAX29URLEmailTest1();
		test.test1();
		
		
	}

}
