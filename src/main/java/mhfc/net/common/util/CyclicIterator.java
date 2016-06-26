package mhfc.net.common.util;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class CyclicIterator<E> implements ListIterator<E> {
	private final List<E> list;
	private ListIterator<E> iterator;

	public CyclicIterator(List<E> list) {
		this(list, 0);
	}

	public CyclicIterator(List<E> list, int index) {
		this.list = Objects.requireNonNull(list);
		this.iterator = list.listIterator(index);
	}

	@Override
	public boolean hasNext() {
		if (!iterator.hasNext()) {
			iterator = list.listIterator();
		}
		return iterator.hasNext();
	}

	@Override
	public boolean hasPrevious() {
		if (!iterator.hasPrevious()) {
			iterator = list.listIterator(list.size() - 1);
			iterator.next();
		}
		return iterator.hasPrevious();
	}

	@Override
	public E next() {
		hasNext();
		return iterator.next();
	}

	@Override
	public E previous() {
		hasPrevious();
		return iterator.previous();
	}

	@Override
	public int nextIndex() {
		hasNext();
		return iterator.nextIndex();
	}

	@Override
	public int previousIndex() {
		hasPrevious();
		return iterator.previousIndex();
	}

	@Override
	public void remove() {
		this.iterator.remove();
	}

	@Override
	public void set(E e) {
		this.iterator.set(e);
	}

	@Override
	public void add(E e) {
		this.iterator.add(e);
	}
}
