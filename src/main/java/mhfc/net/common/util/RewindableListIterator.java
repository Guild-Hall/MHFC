package mhfc.net.common.util;

import java.util.ListIterator;

public class RewindableListIterator<E> implements ListIterator<E>, IRewindable {
	private final ListIterator<E> iterator;
	private int offset;
	private boolean isActive;
	private boolean wasLastNext;

	public RewindableListIterator(ListIterator<E> iterator) {
		this.iterator = iterator;
		this.offset = 0;
		this.isActive = false;
	}

	@Override
	public void mark() {
		this.offset = 0;
		this.isActive = true;
	}

	@Override
	public void clearMark() {
		this.offset = 0;
		this.isActive = false;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public boolean hasPrevious() {
		return iterator.hasPrevious();
	}

	@Override
	public int nextIndex() {
		return iterator.nextIndex();
	}

	@Override
	public int previousIndex() {
		return iterator.previousIndex();
	}

	@Override
	public void set(E e) {
		iterator.set(e);
	}

	@Override
	public E next() {
		E element = iterator.next();
		offset++;
		wasLastNext = true;
		return element;
	}

	@Override
	public E previous() {
		E element = iterator.previous();
		offset--;
		wasLastNext = false;
		return element;
	}

	@Override
	public void remove() {
		iterator.remove();
		if (wasLastNext && offset <= 0) {
			return;
		}
		if (!wasLastNext && offset >= 0) {
			return;
		}
		@SuppressWarnings("unused")
		int _unused = wasLastNext ? --offset : ++offset;
	}

	@Override
	public void add(E e) {
		iterator.add(e);
		if (offset >= 0) {
			offset++;
		}
	}

	@Override
	public RewindableListIterator<E> rewind() {
		if (!isActive) {
			throw new IllegalStateException("Not marked");
		}
		if (offset > 0) {
			while (offset-- > 0) {
				iterator.previous();
			}
		} else if (offset < 0) {
			while (offset++ < 0) {
				iterator.next();
			}
		}
		return this;
	}
}
