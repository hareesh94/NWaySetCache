package com.example.nwaysetcache;

import java.time.Instant;

public class CacheEntry<V> {
	private int tag;
	private V data;
	private long timestamp;

	public CacheEntry(int tag, V data) {
		this.tag = tag;
		this.data = data;
		this.timestamp = Instant.now().toEpochMilli();
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public V getData() {
		return data;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public int getTag() {
		return tag;
	}
}
