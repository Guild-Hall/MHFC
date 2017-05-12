package mhfc.net.common.util.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class DelegatingConverter<A, AT, AC, B, BT, BC, K> implements IConverter<A, AC, B, BC> {

	private Map<K, IConverter<AT, AC, BT, BC>> registry = createStorageMap();

	public void registerConverter(K key, IConverter<AT, AC, BT, BC> converter) {
		Objects.requireNonNull(converter);
		IConverter<AT, AC, BT, BC> prev = registry.putIfAbsent(key, converter);
		if (prev != null) {
			throw new IllegalArgumentException("There was a already a converter registered for " + key + ": " + prev);
		}
	}

	protected Map<K, IConverter<AT, AC, BT, BC>> createStorageMap() {
		return new HashMap<>();
	}

	protected abstract K extractKeyFromA(A value);

	protected abstract AT extractConvertibleFromA(A value);

	protected abstract A createA(K key, AT value);

	protected abstract K extractKeyFromB(B value);

	protected abstract BT extractConvertibleFromB(B value);

	protected abstract B createB(K key, BT value);

	private IConverter<AT, AC, BT, BC> getConverter(K key) {
		IConverter<AT, AC, BT, BC> converter = registry.get(key);
		if (converter == null) {
			throw new IllegalStateException("No converter for " + key);
		}
		return converter;
	}
	@Override
	public A convertFrom(B value, BC context) {
		K key = extractKeyFromB(value);
		IConverter<AT, AC, BT, BC> converter = getConverter(key);
		BT convertible = extractConvertibleFromB(value);
		AT convertedValue = converter.convertFrom(convertible, context);
		return createA(key, convertedValue);
	}

	@Override
	public B convertTo(A value, AC context) {
		K key = extractKeyFromA(value);
		IConverter<AT, AC, BT, BC> converter = getConverter(key);
		AT convertible = extractConvertibleFromA(value);
		BT convertedValue = converter.convertTo(convertible, context);
		return createB(key, convertedValue);
	}
}
