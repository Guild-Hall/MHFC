package mhfc.net.common.world.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * This rectangler packer organizes the rectangles as follows:
 * 
 * <ul>
 * <li>Corners are always placed onto existing corners.
 * <li>Corners are said outgoing be outer corners if their inner angle is 90
 * degrees, and inner corners if theirs is 270 degrees.
 * <li>There shall never be two outer corners following each other on the
 * bounding line.
 * </ul>
 * 
 * @author WorldSEnder
 *
 */
public class SimpleRectanglePlacer {
	static enum Direction {
		RIGHT, //
		UP, //
		LEFT, //
		DOWN;

		public boolean isHorizontal() {
			switch (this) {
				case RIGHT :
				case LEFT :
					return true;
				case UP :
				case DOWN :
					return false;
				default :
					throw new IllegalArgumentException("Invalid this");
			}
		}

		public int edgeLength(CornerPosition from, CornerPosition to) {
			switch (this) {
				case RIGHT :
					return to.posX - from.posX;
				case UP :
					return to.posY - from.posY;
				case LEFT :
					return from.posX - to.posX;
				case DOWN :
					return from.posY - to.posY;
				default :
					throw new IllegalArgumentException("Invalid this");
			}
		}

		public Direction inverted() {
			switch (this) {
				case RIGHT :
					return Direction.LEFT;
				case UP :
					return Direction.DOWN;
				case LEFT :
					return Direction.RIGHT;
				case DOWN :
					return Direction.UP;
				default :
					throw new IllegalArgumentException("Invalid this");
			}
		}

		public CornerPosition add(CornerPosition start, int edgeLength) {
			switch (this) {
				case RIGHT :
					return new CornerPosition(start.posX + edgeLength,
							start.posY);
				case UP :
					return new CornerPosition(start.posX,
							start.posY + edgeLength);
				case LEFT :
					return new CornerPosition(start.posX - edgeLength,
							start.posY);
				case DOWN :
					return new CornerPosition(start.posX,
							start.posY - edgeLength);
				default :
					throw new IllegalArgumentException("Invalid this");
			}
		}

		public int compare(CornerPosition x, CornerPosition y) {
			switch (this) {
				case RIGHT :
					return Integer.compare(x.posX, y.posX);
				case UP :
					return Integer.compare(x.posY, y.posY);
				case LEFT :
					return Integer.compare(y.posX, x.posX);
				case DOWN :
					return Integer.compare(y.posY, x.posY);
				default :
					throw new IllegalArgumentException("Invalid this");
			}
		}
	}

	static enum CornerType {
		// Counter-clockwise
		DOWN_RIGHT(true, Direction.DOWN, Direction.RIGHT), //
		RIGHT_UP(true, Direction.RIGHT, Direction.UP), //
		UP_LEFT(true, Direction.UP, Direction.LEFT), //
		LEFT_DOWN(true, Direction.LEFT, Direction.DOWN), //
		DOWN_LEFT(false, Direction.DOWN, Direction.LEFT), //
		LEFT_UP(false, Direction.LEFT, Direction.UP), //
		UP_RIGHT(false, Direction.UP, Direction.RIGHT), //
		RIGHT_DOWN(false, Direction.RIGHT, Direction.DOWN);

		public final boolean isOuter;
		public final Direction incoming, outgoing;

		private CornerType(boolean isOuter, Direction from, Direction to) {
			this.isOuter = isOuter;
			this.incoming = from;
			this.outgoing = to;
		}

		public boolean isOuter() {
			return isOuter;
		}

		public static CornerType fromIncoming(Direction dir, boolean outer) {
			switch (dir) {
				case RIGHT :
					return outer ? CornerType.RIGHT_UP : RIGHT_DOWN;
				case UP :
					return outer ? CornerType.UP_LEFT : UP_RIGHT;
				case LEFT :
					return outer ? LEFT_DOWN : LEFT_UP;
				case DOWN :
					return outer ? CornerType.DOWN_RIGHT : DOWN_LEFT;
				default :
					throw new IllegalArgumentException("Invalid direction");
			}
		}

		public CornerType followUp(boolean outer) {
			return fromIncoming(outgoing, outer);
		}
		/**
		 * branches in the other direction. if this is an outer corner, the
		 * returned corner will be innner..
		 * 
		 * @return
		 */
		public CornerType rebranch(boolean rebranchInc) {
			return rebranchInc
					? fromIncoming(incoming.inverted(), !isOuter())
					: fromIncoming(incoming, !isOuter());
		}

		@Override
		public String toString() {
			return (isOuter ? "." : "!") + super.toString();
		}
	}
	// Never two adjacent Bs, thus the sequence S making up the bounding
	// line
	// must be
	// O := B A
	// I := A
	// S := (I|O)+
	// with count(I) == 4

	public static class CornerPosition {
		public final int posX;
		public final int posY;

		public CornerPosition(int posX, int posY) {
			this.posX = posX;
			this.posY = posY;
		}

		public static CornerPosition totalMin(CornerPosition x,
				CornerPosition y) {
			if (x.posX <= y.posX && x.posY <= y.posY) {
				return x;
			}
			if (y.posX <= x.posX && y.posY <= x.posY) {
				return y;
			}
			return new CornerPosition(java.lang.Math.min(x.posX, y.posX),
					java.lang.Math.min(x.posY, y.posY));
		}

		public static CornerPosition totalMax(CornerPosition x,
				CornerPosition y) {
			if (x.posX >= y.posX && x.posY >= y.posY) {
				return x;
			}
			if (y.posX >= x.posX && y.posY >= x.posY) {
				return y;
			}
			return new CornerPosition(java.lang.Math.max(x.posX, y.posX),
					java.lang.Math.max(x.posY, y.posY));
		}

		@Override
		public String toString() {
			return "(" + this.posX + ", " + this.posY + ")";
		}
	}

	static class Corner {
		public final CornerType type;
		public final CornerPosition pos;

		public Corner(CornerType type, int posX, int posZ) {
			this(type, new CornerPosition(posX, posZ));
		}

		public Corner(CornerType type, CornerPosition pos) {
			this.type = Objects.requireNonNull(type);
			this.pos = Objects.requireNonNull(pos);
		}

		@Override
		public String toString() {
			return String.format("{type: %s, coords: %s}", this.type, this.pos);
		}
	}

	static class RewindableListIterator<E> implements ListIterator<E> {
		private final ListIterator<E> iterator;
		private int offset;
		private boolean isActive;
		private boolean wasLastNext;

		public RewindableListIterator(ListIterator<E> iterator) {
			this.iterator = iterator;
			this.offset = 0;
			this.isActive = false;
		}

		public void mark() {
			this.offset = 0;
			this.isActive = true;
		}

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
			if (wasLastNext && offset <= 0)
				return;
			if (!wasLastNext && offset >= 0)
				return;
			@SuppressWarnings("unused")
			int _unused = wasLastNext ? --offset : ++offset;
		}

		@Override
		public void add(E e) {
			iterator.add(e);
			if (offset > 0)
				offset++;
		}

		public RewindableListIterator<E> rewind() {
			if (!isActive)
				throw new IllegalStateException("Not marked");
			if (offset > 0) {
				while (offset-- > 0)
					iterator.previous();
			} else if (offset < 0) {
				while (offset++ < 0)
					iterator.next();
			}
			return this;
		}
	}

	static class CornerList extends ArrayList<Corner> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5500364714034117962L;

		private class CyclicIterator
				implements
					ListIterator<SimpleRectanglePlacer.Corner> {
			private ListIterator<Corner> basic;

			public CyclicIterator() {
				this(0);
			}

			public CyclicIterator(int index) {
				this.basic = CornerList.this.listIterator(index);
			}
			@Override
			public boolean hasNext() {
				if (!basic.hasNext()) {
					basic = CornerList.this.listIterator();
				}
				return basic.hasNext();
			}

			@Override
			public boolean hasPrevious() {
				if (!basic.hasPrevious()) {
					basic = CornerList.this
							.listIterator(CornerList.this.size() - 1);
					basic.next();
				}
				return basic.hasPrevious();
			}

			@Override
			public Corner next() {
				hasNext();
				return basic.next();
			}

			@Override
			public Corner previous() {
				hasPrevious();
				return basic.previous();
			}

			@Override
			public int nextIndex() {
				hasNext();
				return basic.nextIndex();
			}

			@Override
			public int previousIndex() {
				hasPrevious();
				return basic.previousIndex();
			}

			@Override
			public void remove() {
				this.basic.remove();
			}

			@Override
			public void set(Corner e) {
				this.basic.set(e);
			}

			@Override
			public void add(Corner e) {
				this.basic.add(e);
			}
		}

		public CornerList() {
			super();
		}

		public ListIterator<Corner> cyclicIterator() {
			return new CyclicIterator();
		}

		public ListIterator<Corner> cyclicIterator(int index) {
			int listSize = CornerList.this.size();
			int realIndex = index % listSize;
			if (realIndex < 0)
				realIndex += listSize;
			return new CyclicIterator(realIndex);
		}
	}

	private CornerList corners = new CornerList();
	private CornerPosition minCorner = null, maxCorner = null;

	public SimpleRectanglePlacer() {}

	private static long rectangleSize(CornerPosition downLeft,
			CornerPosition upRight) {
		long diffX = Integer.toUnsignedLong(upRight.posX - downLeft.posX);
		long diffY = Integer.toUnsignedLong(upRight.posY - downLeft.posY);
		return diffX * diffY;
	}

	private static interface RectanglePlacingFunction {
		CornerPosition tryPlace(Corner prev, Corner curr, Corner next,
				long currentBestSize, CornerPosition currentMinCorner,
				CornerPosition currentMaxCorner, int incomingSize,
				int outgoingSize, List<Corner> replacementOut);
	}

	private static final List<RectanglePlacingFunction> placingFunctions;

	static {
		placingFunctions = Arrays.asList(
				SimpleRectanglePlacer::tryPlaceAtInnerCorner,
				SimpleRectanglePlacer::tryPlaceAfterOuterCorner,
				SimpleRectanglePlacer::tryPlaceBeforeOuterCorner);
	}

	public CornerPosition addRectangle(final int sizeX, final int sizeY) {
		if (corners.isEmpty()) {
			corners.add(new Corner(CornerType.DOWN_RIGHT, 0, 0));
			corners.add(new Corner(CornerType.RIGHT_UP, sizeX, 0));
			corners.add(new Corner(CornerType.UP_LEFT, sizeX, sizeY));
			corners.add(new Corner(CornerType.LEFT_DOWN, 0, sizeY));
			minCorner = new CornerPosition(0, 0);
			maxCorner = new CornerPosition(sizeX, sizeY);
			return corners.get(0).pos;
		}
		CornerPosition newMinCorner = minCorner, newMaxCorner = maxCorner;
		long bestSize = -1; // !!Unsigned!!
		CornerPosition placedAt = null;
		int midCornerIndex = -1;
		List<Corner> replacementResult = new ArrayList<>();

		boolean hasDoneIndex0 = false;
		ListIterator<Corner> previousCorner, currentCorner, nextCorner;
		List<Corner> replacementBuffer = new ArrayList<>();
		previousCorner = corners.cyclicIterator();
		currentCorner = corners.cyclicIterator();
		nextCorner = corners.cyclicIterator();
		previousCorner.previous();
		nextCorner.next();
		while (currentCorner.nextIndex() != 0 || !hasDoneIndex0) {
			hasDoneIndex0 = true;
			Corner prev = previousCorner.next();
			Corner curr = currentCorner.next();
			Corner next = nextCorner.next();
			boolean incHorizontal = curr.type.incoming.isHorizontal();
			// boolean outHorizontal = !incHorizontal;
			int incomingSize = incHorizontal ? sizeX : sizeY;
			int outgoingSize = !incHorizontal ? sizeX : sizeY;
			for (RectanglePlacingFunction func : placingFunctions) {
				CornerPosition placedAtInner = func.tryPlace(prev, curr, next,
						bestSize, newMinCorner, newMaxCorner, incomingSize,
						outgoingSize, replacementBuffer);
				if (placedAtInner != null) {
					replacementResult.clear();
					replacementResult.addAll(replacementBuffer);
					placedAt = placedAtInner;
					midCornerIndex = currentCorner.previousIndex();
					for (Corner c : replacementBuffer) {
						newMaxCorner = CornerPosition.totalMax(c.pos,
								newMaxCorner);
						newMinCorner = CornerPosition.totalMin(c.pos,
								newMinCorner);
					}
					bestSize = rectangleSize(newMinCorner, newMaxCorner);
				}
				replacementBuffer.clear();
			}
		}
		this.minCorner = newMinCorner;
		this.maxCorner = newMaxCorner;
		ListIterator<Corner> replacingIt = corners
				.cyclicIterator(midCornerIndex);
		replacingIt.previous();
		replacingIt.remove();
		replacingIt.next();
		replacingIt.remove();
		replacingIt.next();
		replacingIt.remove();
		RewindableListIterator<Corner> beforeIt = new RewindableListIterator<>(
				replacingIt);
		beforeIt.mark();
		for (Corner c : replacementResult) {
			beforeIt.add(c);
		}
		beforeIt.previous();
		beforeIt.previous();
		fixInvariant(beforeIt, true);
		beforeIt.rewind();
		beforeIt.next();
		beforeIt.next();
		fixInvariant(beforeIt, false);
		return placedAt;
	}

	static CornerPosition tryPlaceAfterOuterCorner(Corner prev, Corner curr,
			Corner next, long currentBestSize, CornerPosition currentMinCorner,
			CornerPosition currentMaxCorner, int incomingSize, int outgoingSize,
			List<Corner> replacementOut) {
		if (!curr.type.isOuter()) {
			return null;
		}
		int cmpOut = Integer.compare(
				curr.type.outgoing.edgeLength(curr.pos, next.pos),
				outgoingSize);
		// Then try place it after the corner
		if (!next.type.isOuter() && cmpOut <= 0) {
			return null;
		}
		CornerPosition diagonalCorner = curr.type.incoming.add(curr.pos,
				incomingSize);
		diagonalCorner = curr.type.outgoing.add(diagonalCorner, outgoingSize);
		CornerPosition currMin = CornerPosition.totalMin(diagonalCorner,
				currentMinCorner);
		CornerPosition currMax = CornerPosition.totalMax(diagonalCorner,
				currentMaxCorner);
		long currRectSize = rectangleSize(currMin, currMax);
		if (Long.compareUnsigned(currRectSize, currentBestSize) <= 0) {
			return null;
		}
		CornerPosition placedAt = CornerPosition.totalMin(curr.pos,
				diagonalCorner);
		CornerPosition upper = curr.type.incoming.add(curr.pos, incomingSize);
		Corner upperC = new Corner(curr.type, upper);
		Corner diagonal = new Corner(upperC.type.followUp(true),
				diagonalCorner);
		CornerPosition branching = curr.type.outgoing.add(curr.pos,
				outgoingSize);
		Corner branchC = new Corner(diagonal.type.followUp(false), branching);
		if (cmpOut > 0) {
			replacementOut.add(prev);
			replacementOut.add(upperC);
			replacementOut.add(diagonal);
			replacementOut.add(branchC);
			replacementOut.add(next);
		} else if (cmpOut == 0) {
			replacementOut.add(prev);
			replacementOut.add(upperC);
			replacementOut.add(diagonal);
		} else {
			Corner newBranch = new Corner(branchC.type.followUp(false),
					branchC.pos);
			Corner newNext = new Corner(next.type.rebranch(true), next.pos);
			replacementOut.add(prev);
			replacementOut.add(upperC);
			replacementOut.add(diagonal);
			replacementOut.add(newBranch);
			replacementOut.add(newNext);
		}
		return placedAt;
	}

	static CornerPosition tryPlaceBeforeOuterCorner(Corner prev, Corner curr,
			Corner next, long currentBestSize, CornerPosition currentMinCorner,
			CornerPosition currentMaxCorner, int incomingSize, int outgoingSize,
			List<Corner> replacementOut) {
		if (!curr.type.isOuter()) {
			return null;
		}
		int cmpInc = Integer.compare(
				curr.type.incoming.edgeLength(prev.pos, curr.pos),
				incomingSize);
		if (!prev.type.isOuter() && cmpInc <= 0) {
			return null;
		}
		CornerPosition diagonalCorner = curr.type.incoming.inverted()
				.add(curr.pos, incomingSize);
		diagonalCorner = curr.type.outgoing.inverted().add(diagonalCorner,
				outgoingSize);
		CornerPosition currMin = CornerPosition.totalMin(diagonalCorner,
				currentMinCorner);
		CornerPosition currMax = CornerPosition.totalMax(diagonalCorner,
				currentMaxCorner);
		long currRectSize = rectangleSize(currMin, currMax);
		if (Long.compareUnsigned(currRectSize, currentBestSize) >= 0) {
			return null;
		}
		CornerPosition placedAt = CornerPosition.totalMin(curr.pos,
				diagonalCorner);
		CornerPosition branching = curr.type.incoming.inverted().add(curr.pos,
				incomingSize);
		Corner branchC = new Corner(prev.type.followUp(false), branching);
		Corner diagonal = new Corner(branchC.type.followUp(true),
				diagonalCorner);
		CornerPosition upper = curr.type.outgoing.inverted().add(curr.pos,
				outgoingSize);
		Corner upperC = new Corner(curr.type, upper);
		if (cmpInc > 0) {
			replacementOut.add(prev);
			replacementOut.add(branchC);
			replacementOut.add(diagonal);
			replacementOut.add(upperC);
			replacementOut.add(next);
		} else if (cmpInc == 0) {
			replacementOut.add(diagonal);
			replacementOut.add(upperC);
			replacementOut.add(next);
		} else {
			Corner newPrev = new Corner(prev.type.rebranch(false), prev.pos);
			Corner newBranch = new Corner(newPrev.type.followUp(true),
					branchC.pos);
			replacementOut.add(newPrev);
			replacementOut.add(newBranch);
			replacementOut.add(diagonal);
			replacementOut.add(upperC);
			replacementOut.add(next);
		}
		return placedAt;
	}

	static CornerPosition tryPlaceAtInnerCorner(Corner prev, Corner curr,
			Corner next, long currentBestSize, CornerPosition currentMinCorner,
			CornerPosition currentMaxCorner, int incomingSize, int outgoingSize,
			List<Corner> replacementOut) {
		if (curr.type.isOuter())
			return null;
		CornerPosition diagonalCorner = curr.type.incoming.inverted()
				.add(curr.pos, incomingSize);
		diagonalCorner = curr.type.outgoing.add(diagonalCorner, outgoingSize);
		CornerPosition currMin = CornerPosition.totalMin(diagonalCorner,
				currentMinCorner);
		CornerPosition currMax = CornerPosition.totalMax(diagonalCorner,
				currentMaxCorner);
		long currRectSize = rectangleSize(currMin, currMax);
		if (Long.compareUnsigned(currRectSize, currentBestSize) >= 0) {
			return null; // Marker for failure
		}
		CornerPosition placedAt = CornerPosition.totalMin(curr.pos,
				diagonalCorner);
		int cmpInc = Integer.compare(
				curr.type.incoming.edgeLength(prev.pos, curr.pos),
				incomingSize);
		int cmpOut = Integer.compare(
				curr.type.outgoing.edgeLength(curr.pos, next.pos),
				outgoingSize);
		if (cmpInc > 0) {
			CornerPosition branching = curr.type.incoming.inverted()
					.add(curr.pos, incomingSize);
			Corner branchingC = new Corner(prev.type.followUp(false),
					branching);
			replacementOut.add(prev);
			replacementOut.add(branchingC);
		} else if (cmpInc == 0) {
			// Don't add anything
		} else {
			CornerPosition branching = curr.type.incoming.inverted()
					.add(curr.pos, incomingSize);
			Corner newPrev = new Corner(prev.type.rebranch(false), prev.pos);
			Corner branchC = new Corner(newPrev.type.followUp(true), branching);
			replacementOut.add(newPrev);
			replacementOut.add(branchC);
		}
		replacementOut
				.add(new Corner(curr.type.followUp(true), diagonalCorner));
		if (cmpOut > 0) {
			CornerPosition merging = curr.type.outgoing.add(curr.pos,
					outgoingSize);
			Corner mergingC = new Corner(
					curr.type.followUp(true).followUp(false), merging);
			replacementOut.add(mergingC);
			replacementOut.add(next);
		} else if (cmpOut == 0) {
			// Add nothing
		} else {
			CornerPosition merging = curr.type.outgoing.add(curr.pos,
					outgoingSize);
			Corner newNext = new Corner(next.type.rebranch(true), next.pos);
			Corner mergeC = new Corner(curr.type.followUp(true).followUp(true),
					merging);
			replacementOut.add(mergeC);
			replacementOut.add(newNext);
		}
		return placedAt;
	}

	static void fixInvariant(ListIterator<Corner> iterator, boolean forward) {
		Corner c = forward(iterator, forward);
		if (!c.type.isOuter())
			backward(iterator, forward);
		while (true) {
			Corner firstB = forward(iterator, forward);
			Corner secondB = forward(iterator, forward);
			if (firstB.type.isOuter() || secondB.type.isOuter()) {
				return;
			}
			Corner afterBB = forward(iterator, forward);
			backward(iterator, forward); // AfterBB
			backward(iterator, forward); // SecondBB
			backward(iterator, forward); // FirstBB
			Corner beforeBB = backward(iterator, forward);
			int cmp = secondB.type.outgoing.compare(beforeBB.pos, afterBB.pos);
			if (cmp < 0) {
				iterator.remove();
				forward(iterator, forward); // FirstBB
				iterator.remove();
				forward(iterator, forward); // SecondBB
				iterator.remove();
				CornerPosition newCorner = secondB.type.outgoing.isHorizontal()
						? new CornerPosition(beforeBB.pos.posX,
								afterBB.pos.posY)
						: new CornerPosition(afterBB.pos.posX,
								beforeBB.pos.posY);
				iterator.add(new Corner(secondB.type, newCorner));
				backward(iterator, forward);
			} else if (cmp == 0) {
				iterator.remove();
				forward(iterator, forward); // FirstBB
				iterator.remove();
				forward(iterator, forward); // SecondBB
				iterator.remove();
				forward(iterator, forward); // AfterBB
				iterator.remove();
			} else {
				forward(iterator, forward); // BeforeBB
				forward(iterator, forward); // FirstBB
				iterator.remove();
				forward(iterator, forward); // SecondBB
				iterator.remove();
				forward(iterator, forward); // AfterBB
				iterator.remove();
				CornerPosition newCorner = secondB.type.outgoing.isHorizontal()
						? new CornerPosition(afterBB.pos.posX,
								beforeBB.pos.posY)
						: new CornerPosition(beforeBB.pos.posX,
								afterBB.pos.posY);
				iterator.add(new Corner(firstB.type, newCorner));
			}
		}
	}

	private static Corner forward(ListIterator<Corner> it, boolean forward) {
		return forward ? it.next() : it.previous();
	}

	private static Corner backward(ListIterator<Corner> it, boolean forward) {
		return forward ? it.previous() : it.next();
	}

	@Override
	public String toString() {
		return corners.toString();
	}
}
