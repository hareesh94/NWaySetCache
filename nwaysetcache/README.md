 Setup instructions:
 
 1. Download the project
 2. Perform a "mvn clean install" to install the nwaysetcache artifact (ie, the nwaysetcache jar file) into the local maven repository.
 3. Now, add a following dependency in your project pom.xml file to use the nwaysetcache.
     
      <dependency>
			<groupId>com.example</groupId>
			<artifactId>nwaysetcache</artifactId>
			<version>0.0.1-SNAPSHOT</version>
			<scope>compile</scope>
		</dependency>

4. Next,perform a "mvn eclipse:eclipse" on your project to update its classpath.
5. We can now add the nwaysetcache import to your projet file since the nwaysetcache jar file is in your project classpath.
	       
Example instantiation of cache:
	       
	        ClientCache<String,String> cache = new ClientCache<String,String>(8, 2, "LRU");
	        8 is number of sets in cache
	        2 is number of entries in each set
			 "LRU" is the replacement algorithm. Can "MRU" for most recent replacement algorithm and "CUSTOM" for
			 custom replacement algorithm.
			 
			 cache.put("Messi","Football"); - example for putting a value in the cache
			 cache.put("Sachin", "Cricket");
			 cache.get("Messi);  - example for getting a value from the cache
			 cache.clear();     - clears all the entries of the class.
			 cache.remove("Messi"); - removes particular entry from cache.
			 
			 
6. Clients can implement there custom replacement algorithm in ClientCache.java
	
	public static <V> int customReplacementAlgo(int startIndex, int endIndex, CacheEntry<V>[] cacheArray) {
		//client can implement his custom replacement algo
	        return endIndex;
	    }

7. In case of cache miss the value is fetched from database. Client can implement it in the following method in ClientCache.java

	public static  <K, V> V getCacheEntryFromDB(K key){
		//Implement cache miss case here
		return (V) "DBValue";
	}




 