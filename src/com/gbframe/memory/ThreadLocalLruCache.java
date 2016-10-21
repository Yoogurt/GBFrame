package com.gbframe.memory;

import com.gbframe.throwable.IllegalThreadAccessException;

public class ThreadLocalLruCache<K, V> extends LruCache<K, V> {

	protected Thread localThread;

	public ThreadLocalLruCache(int length) {
		super(length);
		localThread = Thread.currentThread();
	}

	@Override
	public V get(K key) {
		if (!Thread.currentThread().equals(localThread))
			throw new IllegalThreadAccessException();
		return super.get(key);
	}

	@Override
	protected void clear() {
		if (!Thread.currentThread().equals(localThread))
			throw new IllegalThreadAccessException();
		super.clear();
	}

	@Override
	public boolean put(K key, V val) {
		if (!Thread.currentThread().equals(localThread))
			throw new IllegalThreadAccessException();
		return super.put(key, val);
	}

}
