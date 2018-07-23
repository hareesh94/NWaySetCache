package com.example.clientcache;

import com.example.nwaysetcache.CacheEntry;
import com.example.nwaysetcache.NSetCache;

public class ClientCache<K,V> extends NSetCache<K,V> {
	 
	public ClientCache(int numSet, int numEntry, String replacementAlgo) {
		super(numSet, numEntry, replacementAlgo);
	}
	
	public static <V> int customReplacementAlgo(int startIndex, int endIndex, CacheEntry<V>[] cacheArray) {
		//client can implement his custom replacement algo
	        return endIndex;
	    }
	
	public static  <K, V> V getCacheEntryFromDB(K key){
		//Implement cache miss case here
		return (V) "DBValue";
	}
}
