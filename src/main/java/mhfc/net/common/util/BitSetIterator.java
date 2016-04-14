package mhfc.net.common.util;

import java.util.BitSet;
import java.util.Iterator;
import java.util.Objects;

public class BitSetIterator implements Iterator<Integer> {
	private int nextIndex;
	private final BitSet set;

	public BitSetIterator(BitSet bitset) {
		this.set = Objects.requireNonNull(bitset);
		nextIndex = this.set.nextSetBit(0);
	}

	@Override
	public boolean hasNext() {
		return nextIndex != -1;
	}

	@Override
	public Integer next() {
		int current = nextIndex;
		nextIndex = this.set.nextSetBit(current + 1);
		return current;
	}

	public static Iterable<Integer> asIndexIterable(final BitSet bitset) {
		return () -> new BitSetIterator(bitset);
	}
}
