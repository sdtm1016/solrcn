package cc.solr;

import org.junit.Test;



import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

public class BloomFilterTest {
	class Person {
		int id;
		String firstName;
	}
	
	Funnel<Person> personFunnel = new Funnel<Person>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void funnel(Person person, PrimitiveSink into) {
			into.putInt(person.id).putString(person.firstName, Charsets.UTF_8);
		}
	};
	
	@Test
	public void test(){
		Person a = new Person();
		a.id=0;a.firstName="a";
		Person b = new Person();
		b.id=1;b.firstName="b";
		Person c = new Person();
		c.id=2;c.firstName="c";
		
		BloomFilter<Person> friends = BloomFilter.create(personFunnel, 500, 0.01);
		friends.put(a);
		friends.put(b);
		friends.put(c);
		
		Person B = new Person();
		B.id=3;B.firstName="b";
		System.out.println(friends.mightContain(B));
		System.out.println(friends.mightContain(b));
	}
}
