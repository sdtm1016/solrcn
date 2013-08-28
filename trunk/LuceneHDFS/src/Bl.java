public class Bl {

	private static int DEFAULT_SIZE = 1 << 28;

	public static final int ELEM_NUM = 50 * 10000; // 欲容纳的元素个数
	public static final double PERCENTAGE = 0.001; // 希望的误差率
	byte[] LowBitSet;
	byte[] HighBitSet;

	private static final boolean HIGH = false;
	private static final boolean LOW = true;

	Bl() {
		DEFAULT_SIZE = (int) Math.abs(ELEM_NUM * Math.log(PERCENTAGE) / (Math.log(2) * Math.log(2))) + 1;
		LowBitSet = new byte[DEFAULT_SIZE];
		HighBitSet = new byte[DEFAULT_SIZE];
	}

	int AddWithoutArithmetic(int num1,int num2)    
	{    
	    if(num2==0) return num1;//没有进位的时候完成运算    
	    int sum,carry;    
	    sum=num1^num2;//完成第一步没有进位的加法运算    
	    carry=(num1&num2)<<1;//完成第二步进位并且左移运算    
	    return AddWithoutArithmetic(sum,carry);//进行递归，相加    
	}    
	
	public void add(int n) {

		if (content(HIGH, n)) {

			if (content(LOW, n)) {
				// 11
			} else {
				// 10
				del(HIGH, n);
				add(LOW, n);
			}
		} else {

			if (content(LOW, n)) {
				// 01
				del(LOW, n);
				add(HIGH, n);
			} else {
				// 00
				add(LOW, n);
			}
		}
	}	

	public void add(final boolean bit, final int n) {
		//(byte) (1 << (n & 7)) 跟8取模，然后转换成要设置的bit。
		if (bit) {
			LowBitSet[n >> 3 % DEFAULT_SIZE] |= (byte) (1 << (n & 7));
		} else {
			HighBitSet[n >> 3 % DEFAULT_SIZE] |= (byte) (1 << (n & 7));
		}
	}

	public void del(final int n) {
		if (content(HIGH, n) && !content(LOW, n)) {
			// 10
			del(HIGH,n);
			add(LOW,n);
		} else{
			//00,01,11
				del(LOW, n);
		}
	}		
	
	public void del(final boolean bit, final int n) {
		if (bit) {
			LowBitSet[n >> 3 % DEFAULT_SIZE] &= (byte) (1 << (n & 7)^255);
		} else {
			HighBitSet[n >> 3 % DEFAULT_SIZE] &= (byte) (1 << (n & 7)^255);
		}
	}	

	public boolean content(int n) {
		return (content(LOW, n) || content(HIGH, n));
	}

	public boolean content(final boolean bit, int n) {
		if (bit) {
			return ((LowBitSet[n >> 3 % DEFAULT_SIZE] & (byte) 1 << (n & 7)) == 1 << (n & 7));
		} else {
			return ((HighBitSet[n >> 3 % DEFAULT_SIZE] & (byte) 1 << (n & 7)) == 1 << (n & 7));
		}

	}

	public static void main(String[] args) {

		Bl bl = new Bl();
		bl.add(1000);
		bl.add(1008);
		bl.add(1008);
		
		bl.del(1008);
		for (int i = 1000; i <= 1010; i++) {
			System.out.println("i:" + i + "," + bl.content(i));
		}
		
		bl.del(1008);
		System.out.println("i:" + 1008 + "," + bl.content(1008));
		
		System.out.println((1 << (11 & 7))^255);
	}
}
