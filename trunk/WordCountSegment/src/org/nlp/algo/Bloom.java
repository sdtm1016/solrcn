package org.nlp.algo;

import java.io.Serializable;
import java.util.BitSet;

public class Bloom implements Serializable {
	private static final long serialVersionUID = 1L;

	public static boolean init = false;

	/* BitSet初始分配2^24个bit */
	private static int DEFAULT_SIZE = 1 << 28;
//	private static int DEFAULT_SIZE = Integer.MAX_VALUE;

	public static final int ELEM_NUM = 20 * 1000 * 10000 / 2; // 欲容纳的元素个数
	public static final double PERCENTAGE = 0.001; // 希望的误差率
	GeneralHashFunctionLibrary GHFL = new GeneralHashFunctionLibrary();

	/* 不同哈希函数的种子，一般应取质数 */
//	private static final int[] seeds = new int[] { 5, 7, 11, 13, 31, 37, 61 };
	private static BitSet[] bits = new BitSet[4]; //12 china , 34 english

//	private static BitSet bits;
	/* 哈希函数对象 */
//	private SimpleHash[] func = new SimpleHash[seeds.length];

	public Bloom() {

		if (!init) {
			DEFAULT_SIZE = (int) Math.abs(ELEM_NUM * Math.log(PERCENTAGE) / (Math.log(2) * Math.log(2))) + 1;
			bits[0] = new BitSet(DEFAULT_SIZE);
			bits[1] = new BitSet(DEFAULT_SIZE);
			bits[2] = new BitSet(DEFAULT_SIZE);
			bits[3] = new BitSet(DEFAULT_SIZE);			
	
//			bits[0] = new BitSet(2147483647);
//			bits[1] = new BitSet(2147483647);
		}
//		for (int i = 0; i < seeds.length; i++) {
//			func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
//		}
	}

	public Bloom(final int ELEM_NUM) {
		DEFAULT_SIZE = (int) Math.abs(ELEM_NUM * Math.log(PERCENTAGE)
				/ (Math.log(2) * Math.log(2))) + 1;
		bits[0] = new BitSet(DEFAULT_SIZE);
		bits[1] = new BitSet(DEFAULT_SIZE);
		bits[2] = new BitSet(DEFAULT_SIZE);
		bits[3] = new BitSet(DEFAULT_SIZE);		
//		bits[0] = new BitSet(DEFAULT_SIZE);
//		bits[1] = new BitSet(DEFAULT_SIZE);
//		for (int i = 0; i < seeds.length; i++) {
//			func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
//		}
	}

	public Bloom(String modelFile) {
		loadModel(modelFile);
	}

	// 将字符串标记到bits中
	public void add(final String value) {
		
		int hash = GHFL.FNVHash1(value);
//		int hash = MurmurHash.hash32(value);
		if (hash > 0){			
			bits[0].set(hash);
		}else{
			bits[1].set(Math.abs(hash));
		}
//		
//		int hash1 = GHFL.FNVHash1(value);
//		if (hash1 > 0) {
//			bits[0].set(hash1);
//		} else {
//			bits[1].set(Math.abs(hash1));
//		}	
		
//		bits[0].set((int) (Math.abs(GHFL.APHash(value)) % 2147483647), true);
//		bits[0].set((int) (Math.abs(GHFL.BKDRHash(value)) % 2147483647), true);
//		bits[0].set((int) (Math.abs(GHFL.BPHash(value)) % 2147483647), true);
//		bits[0].set((int) (Math.abs(GHFL.DJBHash(value)) % 2147483647), true);
//		bits[0].set((int) (Math.abs(GHFL.FNVHash(value)) % 2147483647), true);
//		bits[0].set((int) (Math.abs(GHFL.JSHash(value)) % 2147483647), true);
//		bits[0].set((int) (Math.abs(GHFL.RSHash(value)) % 2147483647), true);
//		bits[0].set((int) (Math.abs(GHFL.SDBMHash(value)) % 2147483647), true);
		// for (SimpleHash f : func) {
		// bits.set(f.hash(value), true);
		// }
	}

	
	public void addEn(final String value) {
		
		int hash = GHFL.FNVHash1(value);
//		int hash = MurmurHash.hash32(value);
		if (hash > 0){			
			bits[2].set(hash);
		}else{
			bits[3].set(Math.abs(hash));
		}
	}
	
	// 判断字符串是否已经被bits标记
	public boolean contains(final String value) {
				
		if (value == null) {
			return false;
		}
		
		boolean ret = true;
		int hash = GHFL.FNVHash1(value);
//		int hash = MurmurHash.hash32(value);
		
		if (hash > 0) {
			ret = ret && bits[0].get(hash);
		} else {
			ret = ret && bits[1].get(Math.abs(hash));
		}
		
//		int hash = GHFL.FNVHash1(value);
//		int hash1 = GHFL.FNVHash1(value);
//		if (hash1 > 0) {
//			ret = ret && bits[0].get(hash1);
//		} else {
//			ret = ret && bits[1].get(Math.abs(hash1));
//		}		
		

//		ret = ret
//				&& bits[0].get((int) (Math.abs(GHFL.APHash(value) % 2147483647)));
//		ret = ret
//				&& bits[0].get((int) (Math.abs(GHFL.BKDRHash(value) % 2147483647)));
//		ret = ret
//				&& bits[0].get((int) (Math.abs(GHFL.BPHash(value) % 2147483647)));
//		ret = ret
//				&& bits[0].get((int) (Math.abs(GHFL.DJBHash(value) % 2147483647)));
//		ret = ret
//				&& bits[0].get((int) (Math.abs(GHFL.FNVHash(value) % 2147483647)));
//		ret = ret
//				&& bits[0].get((int) (Math.abs(GHFL.JSHash(value) % 2147483647)));
//		ret = ret
//				&& bits[0].get((int) (Math.abs(GHFL.RSHash(value) % 2147483647)));
//		ret = ret
//				&& bits[0].get((int) (Math.abs(GHFL.SDBMHash(value) % 2147483647)));

		// for (SimpleHash f : func) {
		// ret = ret && bits.get(f.hash(value));
		// }
		return ret;
	}
	
	public boolean containsEn(final String value) {
		
		if (value == null) {
			return false;
		}
		
		boolean ret = true;
		int hash = GHFL.FNVHash1(value);
//		int hash = MurmurHash.hash32(value);
		if (hash > 0) {
			ret = ret && bits[2].get(hash);
		} else {
			ret = ret && bits[3].get(Math.abs(hash));
		}
		return ret;
	}

	public static int Count(int v) {
		
		int num = 0;
		while (v > 0) {
			v &= (v - 1);
			num++;
		}
		return num;
	}

	// 保存模型
	public void saveModel(final String modelFile) {
		compressObject.saveObjectToFile(modelFile,bits);
		
//		try {
//			ObjectOutputStream oos = new ObjectOutputStream(
//					new GZIPOutputStream(new FileOutputStream(new File(
//							modelFile))));
//			oos.writeObject(bits);
//			oos.close();
//		} catch (FileNotFoundException e) {
//			System.out.println("BloomFilter Model FileNotFound");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	// 加载模型
	public static void loadModel(final String modelFile) {
		if (init) {
			return;
		}
		
		
		System.out.println("load the model..." + modelFile);
//		try {
//			File dic = new File(modelFile);
//			ObjectInputStream ois;
//
//			if (!dic.exists()) {
//				InputStream fs = Bloom.class.getClassLoader()
//						.getResourceAsStream(modelFile);
//				ois = new ObjectInputStream(new GZIPInputStream(fs));
//
//			} else {
//				FileInputStream fs = new FileInputStream(new File(modelFile));
//				ois = new ObjectInputStream(new GZIPInputStream(fs));
//
//			}
//
//			bits = (BitSet) ois.readObject();
//			ois.close();
//
//			
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		bits = (BitSet[]) compressObject.loadObjectFromFile(modelFile);
		init = true;
	}

	/* 哈希函数类 */
	public static class SimpleHash implements Serializable {
		private static final long serialVersionUID = 1L;
		private int cap;
		private int seed;

		public SimpleHash(int cap, int seed) {
			this.cap = cap;
			this.seed = seed;
		}

		// hash函数，采用简单的加权和hash
		public int hash(String value) {
			int result = 0;
			int len = value.length();
			for (int i = 0; i < len; i++) {
				result = seed * result + value.charAt(i);
			}
			return (cap - 1) & result;
		}
	}
	
public static void main(String[] args) {
	System.out.println(1 << 30);
}
	
}