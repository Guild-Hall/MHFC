package mhfc.net.common.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

import com.google.common.collect.Lists;

/**
 * <b>No thread safety guarantees.</b><br>
 * A queue which iterates over an underlying iterator until it is consumed. It will only iterate when the head element
 * of this queue is consumed itself, therefore it is lazy. Since the Java interface for Queues requires to support
 * Collection as well, the queue will act like an infinitely huge collection as long as it is not empty. <br>
 * Null elements are not allowed to appear in the Iterable.<br>
 * Be careful with certain methods as their use results in the construction of a list thus draining all elements from
 * this queue which is potentially infinitely big. These methods are:<br>
 * <ul>
 * <li>{@link LazyQueue#size()}</li>
 * <li>{@link LazyQueue#contains(Object)}</li>
 * <li>{@link LazyQueue#containsAll(Collection)}</li>
 * <li>{@link LazyQueue#toArray()}</li>
 * <li>{@link LazyQueue#toArray(Object[]))}</li>
 * </ul>
 * {@link LazyQueue#iterator()} will contruct an iterator that drains this queue but is still lazy.
 * 
 * @author HeroicKatora
 *
 * @param <E>
 */
public class LazyQueue<E> implements Queue<E> {

	Iterator<E> iter;
	E bufferEl;

	public LazyQueue(Iterator<E> iter) {
		this.iter = iter;
	}

	public LazyQueue(Iterable<E> orig) {
		this(orig.iterator());
	}

	private List<E> toList() {
		return Lists.newArrayList(this.iterator());
	}

	@Override
	public int size() {
		return isEmpty() ? 0 : toList().size();
	}

	@Override
	public boolean isEmpty() {
		return !iter.hasNext() && bufferEl == null;
	}

	@Override
	public boolean contains(Object o) {
		return toList().contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			LazyQueue<E> ref = LazyQueue.this;

			@Override
			public boolean hasNext() {
				return !ref.isEmpty();
			}

			@Override
			public E next() {
				return ref.remove();
			}
		};
	}

	@Override
	public Object[] toArray() {
		return toList().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return toList().toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return toList().containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E e) {
		throw new IllegalStateException();
	}

	@Override
	public boolean offer(E e) {
		return false;
	}

	/**
	 * Advances the internal state and returns the previous first element.<br>
	 * Throws {@link NullPointerException} when the new element is null.
	 */
	private E advance() throws NullPointerException {
		E storage = bufferEl;
		bufferEl = iter.next();
		if (bufferEl == null)
			throw new NullPointerException("Null elements are not permitted");
		return storage;
	}

	private void ensureState() throws NullPointerException, NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		if (bufferEl == null) // This only happens at the first element
			advance();
	}

	@Override
	public E remove() {
		ensureState();
		return poll();
	}

	@Override
	public E poll() {
		try {
			ensureState();
		} catch (NoSuchElementException e) {
			return null;
		}
		if (iter.hasNext()) {
			return advance();
		} else {
			E last = bufferEl;
			bufferEl = null;
			return last;
		}
	}

	@Override
	public E element() {
		ensureState();
		return bufferEl;
	}

	@Override
	public E peek() {
		try {
			ensureState();
		} catch (NoSuchElementException e) {
			return null;
		}
		return bufferEl;
	}

}
