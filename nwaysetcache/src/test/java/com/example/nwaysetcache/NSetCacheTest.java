package com.example.nwaysetcache;

import com.example.clientcache.ClientCache;
import org.junit.Assert;
import org.junit.Test;

public class NSetCacheTest {
	
	/**
     * Test for basic read/write operation. Value should retrievable once 
     * written to cache.
     */
	 @Test
	    public void testBasicRW() {
	        ClientCache<String,String> cache = new ClientCache<String,String>(8, 2, "LRU");
	        cache.put("Lionel", "Messi");
	        cache.put("Christiano", "Ronaldo");
	        Assert.assertEquals(cache.get("Lionel"), "Messi");
	        Assert.assertEquals(cache.get("Christiano"), "Ronaldo");
	    }
	 
	 
	    /**
	     * To verify keys belongs to same set ends up in correct set as additional 
	     * entry. verification is done by accessing array by index, instead of 
	     * get() method.
	     */
	 @Test
	    public void testEntryForSameSet() {
	        ClientCache<Integer,String> cache = new ClientCache<Integer,String>(8, 2, "LRU");
	        cache.put(16, "Christiano");
	        cache.put(32, "Ronaldo");
	        Assert.assertEquals(cache.getCache()[0].getData(), "Christiano");
	        Assert.assertEquals(cache.getCache()[1].getData(), "Ronaldo");
	    }
	 
	 /**
	     * Test to verify consecutive write operation for same key with update 
	     * content replaces same entry in a same set with new data.
	     */
	    @Test
	    public void testIncrementalUpdateSameKey() {
	        ClientCache<Integer,String> cache = new ClientCache<Integer,String>(8, 2, "LRU");
	        cache.put(16, "Christiano");
	        cache.put(16, "Ronaldo");
	        Assert.assertEquals(cache.get(16), "Ronaldo");
	    }
	    
	    /**
	     * Testing LRU eviction algorithm, making sure, least recently used entry 
	     * is evicted in case of fully occupied entries of a given set.
	     * 
	     * @throws InterruptedException 
	     */
	    @Test
	    public void testLRU() throws InterruptedException {
	        ClientCache<Integer,String> cache = new ClientCache<Integer,String>(2, 2, "LRU");
	        cache.put(32, "Messi");
	        Thread.sleep(50);
	        cache.put(48, "Rhode");
	        Thread.sleep(50);
	        cache.put(51, "Ronaldo");
	        Thread.sleep(50);
	        cache.put(200, "Christiano");
	        Thread.sleep(50);
	        cache.put(52, "Chrio");
	        Assert.assertEquals(cache.get(32), "DBValue");
	        Assert.assertEquals(cache.get(48), "Rhode");
	     
	    }
	    
	    /**
	     * Testing MRU eviction algorithm, making sure, most recently used entry 
	     * is evicted in case of fully occupied entries of a given set.
	     * 
	     * @throws InterruptedException 
	     */
	    @Test
	    public void testMRU() throws InterruptedException {
	        ClientCache<Integer,String> cache = new ClientCache<Integer,String>(2, 2, "MRU");
	        cache.put(16, "Christiano");
	        Thread.sleep(50);
	        cache.put(32, "Messi");
	        Thread.sleep(50);
	        cache.put(48, "Ronaldo");
	        Thread.sleep(50);
	        cache.put(51, "kal");
	        Thread.sleep(50);
	        cache.put(100, "Raj");
	        Assert.assertEquals(cache.get(32), "DBValue");

	    }
}
