package com.gbframe.memory;

import java.util.LinkedList;

public abstract class LruCache<K, V> {

	protected LinkedList<Node> internal_list;
	protected final int length;

	public LruCache(int length) {
		this.length = length;
		internal_list = new LinkedList<Node>();
	}

	public int size() {
		return internal_list.size();
	}

	public boolean put(K key, V val) {
		return internalPut(key, val);
	}

	protected boolean internalPut(K key, V val) {

		if (internal_list.size() < length) {
			if (internal_list.contains(key))
				return false;
			return internal_list.add(new Node().key(key).val(val));
		}

		internal_list.remove(0);
		return internal_list.add(new Node().key(key).val(val));

	}

	protected boolean internalPut(Node node) {

		if (internal_list.size() < length) {
			if (internal_list.contains(node.key))
				return false;
			return internal_list.add(node);
		}

		internal_list.remove(0);
		return internal_list.add(node);

	}

	public V get(K key) {

		return internalGet(key);

	}

	protected V internalGet(K key) {

		if (key == null)
			throw new NullPointerException("key == null");

		for (int i = internal_list.size(); i > -1; i--) {
			Node tmp = internal_list.get(i);
			if (tmp.key.equals(key)) {

				internal_list.remove(i);
				internalPut(tmp);

				return tmp.val;
			}

		}

		return null;

	}

	protected void clear() {
		internal_list.clear();
	}

	protected class Node {
		K key;
		V val;

		public Node key(K key) {
			this.key = key;
			return this;
		}

		public Node val(V val) {
			this.val = val;
			return this;
		}

	}

}
