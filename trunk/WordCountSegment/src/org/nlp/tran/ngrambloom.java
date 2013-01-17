package org.nlp.tran;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.nlp.algo.Bloom;
import org.nlp.impl.TokenImpl;

public class ngrambloom {

	static TokenImpl impl = new TokenImpl();
	final static String filePath = "data/SogouLabDic.dic";

	public static void main(final String[] argv) throws IOException {
		String text = null;
		long tagTime;
		Reader reader;
		text = "度娘参与的拆旧”和“仿古”的大戏正在中国城市加速上演。一边，部分“中国历史文化名城”岌岌可危，历史文化街区频频告急；一边，55亿再造凤凰，千亿重塑汴京，仿制古城遍地开花。这一切正成为中国城市化进程中的独特风景。";

		// System.out.println(Integer.toHexString(text.getBytes("UTF-8")[1]&0xff));
		// System.out.println(text.getBytes().length);
		// System.out.println(Integer.toString(text.getBytes("UTF-8")[1]&0xff));

		NGramTokenizer nGramTokenizer = null;
		Bloom bloom = new Bloom();
		System.out.println("文本长：" + text.length());
		tagTime = System.currentTimeMillis();
		Set<String> set = new HashSet<String>();
		int count = 0;
		for (int i = 0; i < 10000; i++) {
			reader = new StringReader(text);
			nGramTokenizer = new NGramTokenizer(reader, 2, 5);

			CharTermAttribute termAtt = nGramTokenizer
					.addAttribute(CharTermAttribute.class);

			TypeAttribute typeAtt = nGramTokenizer
					.addAttribute(TypeAttribute.class);
			OffsetAttribute offsetAtt = nGramTokenizer
					.addAttribute(OffsetAttribute.class);

			// nGramTokenizer.reset();

			while (nGramTokenizer.incrementToken()) {
				// System.out.print(termAtt.toString() + " ");
				// System.out.print(offsetAtt.startOffset() + " "
				// + offsetAtt.endOffset() + " ");
				// System.out.println(typeAtt.type());
				set.add(termAtt.toString());
				// bloomFilter.add(termAtt.toString());
				// bloomFilter.contains(termAtt.toString());

				// System.out.println(termAtt.toString());
			}
			for (Iterator<String> it = set.iterator(); it.hasNext();) {
				count++;
				bloom.contains(it.next().toString());
			}
			set.clear();
			nGramTokenizer.end();
			nGramTokenizer.close();
		}
		System.out.format("2-5元切词时间(%s)：%s\n", count,
				(System.currentTimeMillis() - tagTime));

	}
}
