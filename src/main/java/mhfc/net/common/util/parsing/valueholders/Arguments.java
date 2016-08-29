package mhfc.net.common.util.parsing.valueholders;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.google.common.collect.Iterators;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

public class Arguments implements Iterable<IValueHolder> {
	public static class PermissiveIterator<E> implements Iterator<E> {
		private final Iterator<E> base;
		private final Supplier<E> defaultE;

		public PermissiveIterator(Iterator<E> base, E defaultE) {
			this.base = Objects.requireNonNull(base);
			this.defaultE = () -> defaultE;
		}

		public PermissiveIterator(Iterator<E> base, Supplier<E> defaultE) {
			this.base = Objects.requireNonNull(base);
			this.defaultE = Objects.requireNonNull(defaultE);
		}

		@Override
		public boolean hasNext() {
			return base.hasNext();
		}

		@Override
		public E next() {
			return base.next();
		}

		/**
		 * Returns the next object from the contained iterator if that has a next one. Else the object from the
		 * contained {@link Supplier} is returned.<br>
		 * Thus this method can even return objects when the iterator is finished.
		 *
		 * @return
		 */
		public E permissiveNext() {
			return hasNext() ? next() : defaultE.get();
		}

		/**
		 * Makes this iterator usable in an infinite iteration. The iterator returned will always have a next element
		 * which is either the next element from the contained {@link Iterator} or the element from the
		 * {@link Supplier}.<br>
		 * Keep in mind that this will not tie this iterator. Items that are iterated over by the returned iterator will
		 * not be available in this iterator anymore.
		 *
		 * @return
		 */
		public Iterator<E> iteratePermissive() {
			return Iterators.concat(this.base, Stream.generate(this.defaultE).iterator());
		}
	}

	private final IValueHolder[] args;

	public Arguments(IValueHolder... expressions) {
		this.args = mhfc.net.common.util.Objects.requireNonNullDeep(expressions);
	}

	public int getArgCount() {
		return args.length;
	}

	/**
	 * permissive, if out of range, will return
	 *
	 * @param index
	 * @return
	 */
	public IValueHolder getArgument(int index) {
		if (index >= args.length) {
			return getOutOfRange();
		}
		return args[index];
	}

	private Holder getOutOfRange() {
		return Holder.empty();
	}

	/**
	 * Returns a {@link PermissiveIterator} that will return {@link Noop#instance} when it runs out of regular
	 * arguments.
	 */
	@Override
	public PermissiveIterator<IValueHolder> iterator() {
		return new PermissiveIterator<IValueHolder>(Arrays.stream(args).iterator(), this::getOutOfRange);
	}

	@Override
	public String toString() {
		return this.args.toString();
	}

}
