package org.nlp.algo;


/** 
 *
 * murmur hash 2.0.
 * 
 * The murmur hash is a relative fast hash function from
 * http://murmurhash.googlepages.com/ for platforms with efficient
 * multiplication.
 * 
 * This is a re-implementation of the original C code plus some
 * additional features.
 * 
 * Public domain.
 * 
 * @author Viliam Holub
 * @version 1.0.2
 *
 */
public final class MurmurHash {
    
    // all methods static; private constructor. 
    private MurmurHash() {}

    /** 
     * Generates 32 bit hash from byte array of the given length and
     * seed.
     * 
     * @param data byte array to hash
     * @param length length of the array to hash
     * @param seed initial seed value
     * @return 32 bit hash of the given array
     */
    
	static final int[] h32_seed = {
		0xd92e493e, 0x8b50903b, 0xc3372a7b, 0x48f07e9e,
		0x8a5e4a6e, 0x57916df4, 0xa346171f, 0x1e319c86,
		0x9e1a03cd, 0x9f973e6c, 0x2d8c77f5, 0xabed8751,
		0x296708b6, 0x24f8078b, 0x111b1553, 0xa7da1996,
		0xfe776c70};
	
	static final long[] h64_seed = {
		0x0822b1481a92e97bl, 0xf8a9223fef0822ddl, 0x4b49e56affae3a89l,
		0xc970296e32e1d1c1l, 0xe2f9f88789f1b08fl, 0x2b0459d9b4c10c61l,
		0x377e97ea9197ee89l, 0xd2ccad460751e0e7l, 0xff162ca8d6da8c47l,
		0xf12e051405769857l, 0xdabba41293d5b035l, 0xacf326b0bb690d0el,
		0x0617f431bc1a8e04l, 0x15b81f28d576e1b2l, 0x28c1fe59e4f8e5bal,
		0x694dd315c9354ca9l, 0xa97052a8f088ae6cl};
	
    public static int hash32( final byte[] data, int length, int seed) {
        // 'm' and 'r' are mixing constants generated offline.
        // They're not really 'magic', they just happen to work well.
        final int m = 0x5bd1e995;
        final int r = 24;

        // Initialize the hash to a random value
        int h = seed^length;
        int length4 = length/4;

        for (int i=0; i<length4; i++) {
            final int i4 = i*4;
            int k = (data[i4+0]&0xff) +((data[i4+1]&0xff)<<8)
                    +((data[i4+2]&0xff)<<16) +((data[i4+3]&0xff)<<24);
            k *= m;
            k ^= k >>> r;
            k *= m;
            h *= m;
            h ^= k;
        }
        
        // Handle the last few bytes of the input array
        switch (length%4) {
        case 3: h ^= (data[(length&~3) +2]&0xff) << 16;
        case 2: h ^= (data[(length&~3) +1]&0xff) << 8;
        case 1: h ^= (data[length&~3]&0xff);
                h *= m;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;

        return h;
    }
    

    /** 
     * Generates 32 bit hash from byte array with default seed value.
     * 
     * @param data byte array to hash
     * @param length length of the array to hash
     * @return 32 bit hash of the given array
     */
    public static int hash32( final byte[] data, int length) {
        return hash32( data, length, 0x9747b28c); 
    }


    /** 
     * Generates 32 bit hash from a string.
     * 
     * @param text string to hash
     * @return 32 bit hash of the given string
     */
    public static int hash32( final String text) {
        final byte[] bytes = text.getBytes(); 
        return hash32( bytes, bytes.length);
    }


    /** 
     * Generates 32 bit hash from a substring.
     * 
     * @param text string to hash
     * @param from starting index
     * @param length length of the substring to hash
     * @return 32 bit hash of the given string
     */
    public static int hash32( final String text, int from, int length) {
        return hash32( text.substring( from, from+length));
    }
    

    /** 
     * Generates 64 bit hash from byte array of the given length and seed.
     * 
     * @param data byte array to hash
     * @param length length of the array to hash
     * @param seed initial seed value
     * @return 64 bit hash of the given array
     */
    public static long hash64( final byte[] data, int length, int seed) {
        final long m = 0xc6a4a7935bd1e995L;
        final int r = 47;

        long h = (seed&0xffffffffl)^(length*m);

        int length8 = length/8;

        for (int i=0; i<length8; i++) {
            final int i8 = i*8;
            long k =  ((long)data[i8+0]&0xff)      +(((long)data[i8+1]&0xff)<<8)
                    +(((long)data[i8+2]&0xff)<<16) +(((long)data[i8+3]&0xff)<<24)
                    +(((long)data[i8+4]&0xff)<<32) +(((long)data[i8+5]&0xff)<<40)
                    +(((long)data[i8+6]&0xff)<<48) +(((long)data[i8+7]&0xff)<<56);
            
            k *= m;
            k ^= k >>> r;
            k *= m;
            
            h ^= k;
            h *= m; 
        }
        
        switch (length%8) {
        case 7: h ^= (long)(data[(length&~7)+6]&0xff) << 48;
        case 6: h ^= (long)(data[(length&~7)+5]&0xff) << 40;
        case 5: h ^= (long)(data[(length&~7)+4]&0xff) << 32;
        case 4: h ^= (long)(data[(length&~7)+3]&0xff) << 24;
        case 3: h ^= (long)(data[(length&~7)+2]&0xff) << 16;
        case 2: h ^= (long)(data[(length&~7)+1]&0xff) << 8;
        case 1: h ^= (long)(data[length&~7]&0xff);
                h *= m;
        };
     
        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        return h;
    }
    

    /** 
     * Generates 64 bit hash from byte array with default seed value.
     * 
     * @param data byte array to hash
     * @param length length of the array to hash
     * @return 64 bit hash of the given string
     */
    public static long hash64( final byte[] data, int length) {
        return hash64( data, length, 0xe17a1465);
    }


    /** 
     * Generates 64 bit hash from a string.
     * 
     * @param text string to hash
     * @return 64 bit hash of the given string
     */
    public static long hash64( final String text) {
        final byte[] bytes = text.getBytes(); 
        return hash64( bytes, bytes.length);
    }


    /** 
     * Generates 64 bit hash from a substring.
     * 
     * @param text string to hash
     * @param from starting index
     * @param length length of the substring to hash
     * @return 64 bit hash of the given array
     */
    public static long hash64( final String text, int from, int length) {
        return hash64( text.substring( from, from+length));
    }
}

