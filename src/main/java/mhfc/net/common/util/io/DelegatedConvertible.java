package mhfc.net.common.util.io;

public class DelegatedConvertible<T, K> {
	private final T value;
	private final K delegateKey;

	public DelegatedConvertible(K delegateKey, T value) {
		this.value = value;
		this.delegateKey = delegateKey;
	}

	public K getDelegateKey() {
		return delegateKey;
	}

	public T getValue() {
		return value;
	}
}
