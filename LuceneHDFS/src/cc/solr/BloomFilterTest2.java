package cc.solr;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;

public class BloomFilterTest2 {
	public static void main(String[] args) {
		Funnel<CharSequence> funnel = Funnels.stringFunnel();
		BloomFilter<CharSequence> bf =BloomFilter.create(funnel , 100, 0.001);
		bf.put("o");
		bf.put("k");
		System.out.println(bf.mightContain("o"));
		System.out.println(bf.mightContain("1"));
		
	}

}
