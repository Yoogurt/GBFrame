package com.gbframe.memory;

public class ConcurrentLruCache<K, V> extends LruCache<K, V> {

	public ConcurrentLruCache(int length) {
		super(length);
	}

	@Override
	public boolean put(K key, V val) {
		if (internal_list.size() < length)
			return super.put(key, val);
		else
			return false;
	}

	@Override
	protected boolean internalPut(K key, V val) {
		synchronized (internal_list) {
			if (internal_list.size() < length)
				return super.internalPut(key, val);
			else
				return false;
		}
	}

	@Override
	protected boolean internalPut(LruCache<K, V>.Node node) {
		synchronized (internal_list) {
			if (internal_list.size() < length)
				return super.internalPut(node);
			else
				return false;
		}
	}

	@Override
	protected V internalGet(K key) {
		synchronized (internal_list) {
			return super.internalGet(key);
		}
	}

	@Override
	protected void clear() {
		synchronized (internal_list) {
			super.clear();
		}
	}

}
