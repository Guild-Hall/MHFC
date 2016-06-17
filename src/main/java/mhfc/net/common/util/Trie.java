package mhfc.net.common.util;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;

public class Trie<H, T> {
	private static abstract class BasicNode<H, T> {
		public abstract Optional<T> findFirstPrefix(Iterator<H> input);

		public abstract BasicNode<H, T> insert(Iterator<H> input, T data);
	}

	private class DataNode extends BasicNode<H, T> {
		private T data;
		private Map<H, BasicNode<H, T>> continuationNodes;

		public DataNode() {
			this.data = null;
			this.continuationNodes = mapMaker.get();
		}

		private BasicNode<H, T> getNodeFor(H input) {
			return continuationNodes.getOrDefault(input, END_NODE_INSTANCE);
		}

		private void putNodeFor(H input, BasicNode<H, T> newNode) {
			if (newNode == null || newNode == END_NODE_INSTANCE) {
				return;
			}
			continuationNodes.put(input, newNode);
		}

		@Override
		public Optional<T> findFirstPrefix(Iterator<H> input) {
			if (data != null) {
				return Optional.of(data);
			}
			if (!input.hasNext()) {
				return Optional.empty();
			}
			return getNodeFor(input.next()).findFirstPrefix(input);
		}

		@Override
		public BasicNode<H, T> insert(Iterator<H> input, T data) {
			if (!input.hasNext()) {
				Preconditions.checkArgument(data != null, "data " + this.data + "already exists for input " + input);
				this.data = data;
			} else {
				H trialValue = input.next();
				putNodeFor(trialValue, getNodeFor(trialValue).insert(input, data));
			}
			return this;
		}
	}

	private class EndNode extends BasicNode<H, T> {
		@Override
		public Optional<T> findFirstPrefix(Iterator<H> input) {
			return Optional.empty();
		}

		@Override
		public BasicNode<H, T> insert(Iterator<H> input, T data) {
			return new DataNode().insert(input, data);
		}
	}

	private EndNode END_NODE_INSTANCE = new EndNode();
	private Supplier<Map<H, BasicNode<H, T>>> mapMaker;
	private BasicNode<H, T> topNode = END_NODE_INSTANCE;

	public Trie() {
		this(Trie::newHashMapForType);
	}

	public Trie(Class<H> clazzH) {
		this(selectMapTypeFor(clazzH));
	}

	private Trie(Supplier<Map<H, BasicNode<H, T>>> mapMaker) {
		this.mapMaker = java.util.Objects.requireNonNull(mapMaker);
	}

	private static <U, V> Map<U, BasicNode<U, V>> newHashMapForType() {
		return new HashMap<>();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <U, V> Supplier<Map<U, BasicNode<U, V>>> newEnumMapUnchecked(Class<U> enumClass) {
		assert enumClass.isEnum();
		return () -> new EnumMap(enumClass);
	}

	private static <U, V> Supplier<Map<U, BasicNode<U, V>>> selectMapTypeFor(Class<U> clazzH) {
		if (clazzH != null && clazzH.isEnum()) {
			return newEnumMapUnchecked(clazzH);
		}
		return Trie::newHashMapForType;
	}

	/**
	 * Inserts a possiblity into the trie.
	 *
	 * @param sequence
	 * @param data
	 */
	public void insert(List<H> sequence, T data) {
		java.util.Objects.requireNonNull(data);
		this.topNode = topNode.insert(sequence.iterator(), data);
	}

	/**
	 * Finds the first data T that is a prefix of muster
	 *
	 * @param muster
	 * @return
	 */
	public Optional<T> findFirstPrefix(Iterable<H> muster) {
		return this.topNode.findFirstPrefix(muster.iterator());
	}
}
