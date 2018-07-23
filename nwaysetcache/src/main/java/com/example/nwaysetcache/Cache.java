package com.example.nwaysetcache;

public interface Cache<K, V> {

	/**
	 * This method is used to retrieve data related to given key.
	 * @param <K>
	 * @param <V>
	 * 
	 * @param key
	 *            Key to be retrieved.
	 * @return Actual data.
	 */
	 V get(K key);

	/**
	 * This method allows data to be written associated with given key.
	 * @param <K>
	 * @param <V>
	 * 
	 * @param key
	 *            Key associated with given data.
	 * @param value
	 *            Actual data to be written.
	 */
	 void put(K key, V value);

	/**
	 * Clears/Resets the cache.
	 * 
	 */
	void clear();

	/**
	 * This method is used to remove data from cache related to given key.
	 * 
	 * @param key
	 *            Key to be retrieved.
	 * @return Actual data.
	 */
	 V remove(K key);

}
