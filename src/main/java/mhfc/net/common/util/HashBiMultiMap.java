package mhfc.net.common.util;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.SetMultimap;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.StreamSupport;

public class HashBiMultiMap<K, V> implements BiMultiMap<K, V> {

	private SetMultimap<K, V> backingMultimap;
	private Map<V, K> reverseMap;

	public HashBiMultiMap() {
		this.backingMultimap = HashMultimap.create();
		this.reverseMap = new HashMap<>();
	}

	@Override
	public void clear() {
		backingMultimap.clear();
		reverseMap.clear();
	}

	@Override
	public boolean containsEntry(Object key, Object value) {
		return backingMultimap.containsEntry(key, value);
	}

	@Override
	public boolean containsKey(Object key) {
		return backingMultimap.containsEntry(key, key);
	}

	@Override
	public boolean containsValue(Object value) {
		return reverseMap.containsKey(value);
	}

	@Override
	public boolean isEmpty() {
		return backingMultimap.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return backingMultimap.keySet();
	}

	@Override
	public boolean put(K key, V value) {
		boolean modified = backingMultimap.put(key, value);
		if (!modified) {
			// value was already associated with key
			return false;
		}
		if (reverseMap.containsKey(value)) {
			K currentKey = reverseMap.get(value);
			boolean changed = backingMultimap.remove(currentKey, value);
			assert changed;
		}
		reverseMap.put(value, key);
		return true;
	}

	@Override
	public boolean putAll(K key, Iterable<? extends V> values) {
		java.util.Objects.requireNonNull(values);

		return StreamSupport.stream(values.spliterator(), false).anyMatch(v -> put(key, v));
	}

	@Override
	public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
		return multimap.entries().stream().anyMatch(e -> put(e.getKey(), e.getValue()));
	}

	@Override
	public boolean remove(Object key, Object value) {
		boolean removed = backingMultimap.remove(key, value);
		K oldKey = reverseMap.remove(value);
		// !removed => (oldKey == null)
		assert !(!removed) || oldKey == null;
		return removed;
	}

	@Override
	public Set<V> removeAll(Object key) {
		Set<V> oldValues = backingMultimap.removeAll(key);
		oldValues.forEach(v -> reverseMap.remove(v));
		return oldValues;
	}

	@Override
	public Set<V> replaceValues(K key, Iterable<? extends V> values) {
		Set<V> oldValues = removeAll(key);
		putAll(key, values);
		return oldValues;
	}

	@Override
	public int size() {
		return backingMultimap.size();
	}

	@Override
	public int hashCode() {
		return reverseMap.hashCode() * 31;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HashBiMultiMap)) {
			return false;
		}
		HashBiMultiMap<?, ?> other = (HashBiMultiMap<?, ?>) obj;
		return reverseMap.equals(other.reverseMap);
	}

	@Override
	public Multiset<K> keys() {
		throw new NotImplementedException("difficulty is that returned set must write changes to this multi map");
	}

	@Override
	public Map<K, Collection<V>> asMap() {
		throw new NotImplementedException("difficulty is that returned map must write changes to this multi map");
	}

	@Override
	public Collection<V> values() {
		throw new NotImplementedException("difficulty is that returned set must write changes to this multi map");
	}

	@Override
	public Set<Entry<K, V>> entries() {
		throw new NotImplementedException("difficulty is that returned set must write changes to this multi map");
	}

	@Override
	public Set<V> get(K key) {
		return this.new EntryBucket(key);
	}

	private class EntryBucket extends AbstractSet<V> {
		private final K key;
		private final Set<V> underlyingEntries;

		public EntryBucket(K key) {
			this.key = key;
			this.underlyingEntries = backingMultimap.get(key);
		}

		@Override
		public Iterator<V> iterator() {
			return new Iterator<V>() {
				private final Iterator<V> underlyingIt = underlyingEntries.iterator();
				private V currentValue;

				@Override
				public boolean hasNext() {
					return underlyingIt.hasNext();
				}

				@Override
				public V next() {
					return currentValue = underlyingIt.next();
				}

				@Override
				public void remove() {
					underlyingIt.remove();
					HashBiMultiMap.this.reverseMap.remove(currentValue);
				}
			};
		}

		@Override
		public boolean add(V value) {
			return HashBiMultiMap.this.put(key, value);
		}

		@Override
		public int size() {
			return this.underlyingEntries.size();
		}
	}

	@Override
	public Map<V, K> reverse() {
		return this.new ReverseMap();
	}

	private class ReverseMap extends AbstractMap<V, K> {
		private class EntrySet extends AbstractSet<java.util.Map.Entry<V, K>> {
			@Override
			public Iterator<java.util.Map.Entry<V, K>> iterator() {
				return new Iterator<Map.Entry<V, K>>() {
					private final Iterator<Map.Entry<V, K>> underlyingIt = reverseMap.entrySet().iterator();
					private Entry<V, K> currentEntry;

					@Override
					public boolean hasNext() {
						return underlyingIt.hasNext();
					}

					@Override
					public java.util.Map.Entry<V, K> next() {
						return currentEntry = underlyingIt.next();
					}

					@Override
					public void remove() {
						V key = currentEntry.getKey();
						K value = currentEntry.getValue();
						HashBiMultiMap.this.remove(value, key);
					}
				};
			}

			@Override
			public boolean add(java.util.Map.Entry<V, K> e) {
				return HashBiMultiMap.this.put(e.getValue(), e.getKey());
			}

			@Override
			public int size() {
				return HashBiMultiMap.this.reverseMap.size();
			}
		}

		@Override
		public Set<java.util.Map.Entry<V, K>> entrySet() {
			return this.new EntrySet();
		}

		@Override
		public K put(V key, K value) {
			K oldValue = get(key);
			HashBiMultiMap.this.put(value, key);
			return oldValue;
		}

		@Override
		public K get(Object key) {
			return HashBiMultiMap.this.reverseMap.get(key);
		}
	}

}
