package mhfc.net.common.core.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

public class KeyToInstanceRegistryData<KeyType, DataType> {
	private Map<KeyType, DataType> keyToDataMap = new HashMap<>();
	private Map<DataType, KeyType> dataToKeyMap = new IdentityHashMap<>();

	private void forceMapping(KeyType key, DataType data) {
		keyToDataMap.put(key, data);
		dataToKeyMap.put(data, key);
	}

	/**
	 * Tries to insert the given pair, throws {@link IllegalArgumentException} if either one of the two given values is
	 * already mapped.
	 */
	public void putMapping(KeyType key, DataType data) throws IllegalArgumentException {
		Objects.requireNonNull(key);
		Objects.requireNonNull(data);
		if (keyToDataMap.containsKey(key))
			throw new IllegalArgumentException("A data instance for the key " + key + " is already registered");
		if (dataToKeyMap.containsKey(data))
			throw new IllegalArgumentException("The data " + data + " is already registered");
		forceMapping(key, data);
	}

	/**
	 * Tries to insert the given pair, returns false if either one of the two given values is already mapped or inserts
	 * and returns true if it is not the case.
	 */
	public boolean offerMapping(KeyType key, DataType data) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(data);
		if (keyToDataMap.containsKey(key))
			return false;
		if (dataToKeyMap.containsKey(data))
			return false;
		forceMapping(key, data);
		return true;
	}

	public DataType getData(KeyType key) {
		return keyToDataMap.get(key);
	}

	public KeyType getKey(DataType instance) {
		return dataToKeyMap.get(instance);
	}

	public DataType removeData(KeyType key) {
		DataType data = keyToDataMap.remove(key);
		if (data == null)
			return null;
		dataToKeyMap.remove(data);
		return data;
	}

	public KeyType removeKey(DataType data) {
		KeyType key = dataToKeyMap.remove(data);
		if (key == null)
			return null;
		keyToDataMap.remove(key);
		return key;
	}

	public Map<KeyType, DataType> getFrozenDataMap() {
		return Collections.unmodifiableMap(keyToDataMap);
	}

	public Map<DataType, KeyType> getFrozenKeyMap() {
		return Collections.unmodifiableMap(dataToKeyMap);
	}
}
