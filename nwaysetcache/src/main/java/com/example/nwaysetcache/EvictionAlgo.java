package com.example.nwaysetcache;

public class EvictionAlgo {
	
	

	 /**
     * LRU algorithm to evaluate index to be evicted.
	 * @param <V>
     * 
     * @param startIndex Start index.
     * @param endIndex End index.
     * @param cacheArray cache
     * @return Index to be evicted.
     */
    public static <V> int lruReplacementAlgo(int startIndex, int endIndex, CacheEntry<V>[] cacheArray) {
        int lruIndex = startIndex;
        long lruTimestamp = cacheArray[startIndex].getTimestamp();
        for (int i = startIndex; i <= endIndex ; i++) {
            long currentTimestamp = cacheArray[i].getTimestamp();
            if (lruTimestamp >= currentTimestamp) {
                lruIndex = i;
                lruTimestamp = currentTimestamp;
            }
        }
        return lruIndex;
    }

    /**
     * MRU algorithm to evaluate index to be evicted.
     * @param <V>
     * 
     * @param startIndex Start index.
     * @param endIndex End index.
     * @param cacheArray cache
     * @return Index to be evicted.
     */
    public static <V> int mruReplacementAlgo(int startIndex, int endIndex, CacheEntry<V>[] cacheArray) {
        int mruIndex = startIndex;
        long mruTimestamp = cacheArray[startIndex].getTimestamp();
        for (int i = startIndex; i <= endIndex ; i++) {
            long currentTimestamp = cacheArray[i].getTimestamp();
            if (mruTimestamp <= currentTimestamp) {
                mruIndex = i;
                mruTimestamp = currentTimestamp;
            }
        }
        return mruIndex;
    }
}
