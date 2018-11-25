package mhfc.net.common.util;

import com.google.common.collect.SetMultimap;

import java.util.Map;

/**
 * A bidirectional multi map. This means that every value is associated with at max one key.
 *
 * @author WorldSEnder
 *
 * @param <K>
 * @param <V>
 */
public interface BiMultiMap<K, V> extends SetMultimap<K, V> {

	/**
	 * Returns a map that associates each value in this map with its corresponding key. Changes to the returned map are
	 * reflected in this map and vice-versa.
	 *
	 * @return
	 */
	Map<V, K> reverse();
}
