package com.example.nwaysetcache;

import java.time.Instant;
import java.util.Arrays;

import com.example.clientcache.ClientCache;

public class NSetCache<K,V> implements Cache<K,V> {

	private final CacheEntry<V>[] cache;
	private final String algo;

	private final int setNumber;
	private final int entryNumber;

	/**
	 * Constructor. Initializes N-way associative cache of setNumber*entryNumber size.
	 * 
	 * @param setNumber
	 *            Number of sets in cache.
	 * @param entryNumber
	 *            Number of entries in each set.
	 */
	@SuppressWarnings("unchecked")
	public NSetCache(int setNumber, int entryNumber, String replacementAlgo) {
		this.setNumber = setNumber;
		this.entryNumber = entryNumber;
		this.algo = replacementAlgo;
		cache = new CacheEntry[this.setNumber * this.entryNumber];
	}

	public V get(K key) {
		int intKey = HashFunction.getHash(key);
		int startIndex = (intKey % setNumber) * entryNumber;
		int endIndex = startIndex + entryNumber - 1;
		V data = null;
		int emptySlot = 0;
		boolean isEmptySlotPresent = false;
		boolean entryFound = false;
		for (int i = startIndex; i <= endIndex; i++) {

			if (!isEmptySlotPresent && getCache()[i] == null) {
				emptySlot = i;
				isEmptySlotPresent = true;
				continue;
			}

			if (getCache()[i].getTag() == intKey) {
				data = getCache()[i].getData();
				getCache()[i].setTimestamp(Instant.now().toEpochMilli());
				entryFound = true;
				break;
			}
		}

		if (!entryFound) {
            data = ClientCache.getCacheEntryFromDB(key);
			CacheEntry<V> newCacheEntry = new CacheEntry<V>(intKey, data);
			entryFound = true;
			if (isEmptySlotPresent) {
				getCache()[emptySlot] = newCacheEntry;
				entryFound = true;
			}
			else{
				int evictedIndex = getEvictedIndex(algo, startIndex, endIndex, getCache());
				getCache()[evictedIndex] = newCacheEntry;
			}
		}
		return data;
	}

	public  void put(K key, V value) {
		int intKey = HashFunction.getHash(key);
		int startIndex = (intKey % setNumber) * entryNumber;
		int endIndex = startIndex + entryNumber - 1;
		CacheEntry<V> newCacheEntry = new CacheEntry<V>(intKey, value);
		int emptySlot = 0;
		boolean isEmptySlotPresent = false;
		boolean cacheUpdated = false;
		for (int i = startIndex; i <= endIndex; i++) {

			if (!isEmptySlotPresent && getCache()[i] == null) {
				emptySlot = i;
				isEmptySlotPresent = true;
				continue;
			}

			if (getCache()[i]!=null && getCache()[i].getTag() == intKey) {
				getCache()[i] = newCacheEntry;
				cacheUpdated = true;
				break;
			} 
		}

		if (isEmptySlotPresent && !cacheUpdated) {
			getCache()[emptySlot] = newCacheEntry;
		}

		else if(!cacheUpdated) {
			int evictedIndex = getEvictedIndex(algo, startIndex, endIndex, getCache());
			getCache()[evictedIndex] = newCacheEntry;
		}
	}

	public void clear() {
		for (int i = 0; i < getCache().length; i++) {
			getCache()[i] = null;
		}
	}

	public V remove(K key) {
		V data = null;
		int intKey = HashFunction.getHash(key);
		int startIndex = (intKey % setNumber) * entryNumber;
		int endIndex = startIndex + entryNumber - 1;
		for (int i = startIndex; i <= endIndex; i++) {
			if (getCache()[i].getTag() == intKey) {
				data = getCache()[i].getData();
				getCache()[i] = null;
				break;
			}
		}
		return data;
	}
	
	private int getEvictedIndex(String algo, int startIndex, int endIndex, CacheEntry<V>[] cacheArray) {
        if ("LRU".equals(algo)) {
            return EvictionAlgo.lruReplacementAlgo(startIndex, endIndex, cacheArray);
        } else if ("MRU".equals(algo)) {
            return EvictionAlgo.mruReplacementAlgo(startIndex, endIndex, cacheArray);
        } else if ("CUSTOM".equals(algo)) {
            return ClientCache.customReplacementAlgo(startIndex, endIndex, cacheArray);
        } else {
            throw new UnsupportedOperationException("Unsupported Replacement alogorithm usage.");
        }
    }

	public CacheEntry<V>[] getCache() {
		return cache;
	}
}
