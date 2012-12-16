package com.mutantamoeba.ld25;

import java.util.Random;

/**
 * @author keili
 *
 */
public class RandomNumbers {
	static public Random rand = new Random();
	
	// wrapping java.util.Random functions
	static public float nextFloat() { return rand.nextFloat(); }
	static public double nextGaussian() { return rand.nextGaussian(); }	
	static public int nextInt() { return rand.nextInt(); }
	static public int nextInt(int lim) { return rand.nextInt(lim); }
	static public boolean nextBoolean() { return rand.nextBoolean(); }	
	static public void setSeed(long seed) { rand.setSeed(seed); }
	
	/** chooses the index of a list of probabilities at random
	 * 
	 * @param chances a list of integer weights
	 * @return the index of the chosen weight
	 */
	static public int weightedRandom(int... chances) {
	    int total = 0;
	    for (Integer chance:chances) {
	        total += chance;
	    }
	    int val = nextInt(total);
	    
	    int accum = 0;
	    for (int i=0;i<chances.length;i++) {
	        accum += chances[i];
	        if (val < accum) {
	            return i;
	        }
	    }
	    return 0;
	}
	
	/** generates a pseudorandom number based on input coordinates
	 * can be used to seed the random number generator, or just raw
	 * @param x
	 * @param y
	 * @return a random long
	 */
	static public long locHash(int x, int y) {
//	    return ((x * 0x1337) % 157931) * ((y * 319489) % 2696063) + 9876413;
	    return (((x * 10011101 + y) % 53781811) * ((y * 253124999 + x) % 285646799) + 9876413) ^ (x * y * 285646799);    
	}
	
	static public float locFloat(int x, int y) {
		return (float)(locHash(x, y) / (double)Long.MAX_VALUE);
	}
	
}
