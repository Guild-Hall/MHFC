package mhfc.net.common.world.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import mhfc.net.common.util.CyclicIterator;
import mhfc.net.common.util.ICyclicList;
import mhfc.net.common.util.RewindableListIterator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

/**
 * This rectangler packer organizes the rectangles as follows:
 *
 * <ul>
 * <li>Corners are always placed onto existing corners.
 * <li>Corners are said outgoing be outer corners if their inner angle is 90 degrees, and inner corners if theirs is 270
 * degrees.
 * <li>There shall never be two outer corners following each other on the bounding line.
 * </ul>
 *
 * @author WorldSEnder
 *
 */
public class SimpleRectanglePlacer implements IRectanglePlacer {

	private static long rectangleSize(CornerPosition downLeft, CornerPosition upRight) {
		long diffX = Integer.toUnsignedLong(upRight.posX - downLeft.posX);
		long diffY = Integer.toUnsignedLong(upRight.posY - downLeft.posY);
		return diffX * diffY;
	}

	private static Corner forward(ListIterator<Corner> it, boolean forward) {
		return forward ? it.next() : it.previous();
	}

	private static Corner backward(ListIterator<Corner> it, boolean forward) {
		return forward ? it.previous() : it.next();
	}

	private static void addAfter(ListIterator<Corner> it, Corner corner, boolean forward) {
		if (forward) {
			it.add(corner);
			it.previous();
		} else {
			it.add(corner);
		}
	}

	// Never two adjacent Bs, thus the sequence S making up the bounding
	// line
	// must be
	// O := B A
	// I := A
	// S := (I|O)+
	// with count(I) == 4
	public static interface IRectanglePlacingFunction {
		CornerPosition tryPlace(
				Corner prev,
				Corner curr,
				Corner next,
				long currentBestSize,
				CornerPosition currentMinCorner,
				CornerPosition currentMaxCorner,
				int incomingSize,
				int outgoingSize,
				List<Corner> replacementOut);
	}

	public static class CornerList extends ArrayList<Corner> implements ICyclicList<Corner> {
		/**
		 *
		 */
		private static final long serialVersionUID = 5500364714034117962L;

		public CornerList() {
			super();
		}

		public CornerList(Collection<Corner> collection) {
			super(collection);
		}

		@Override
		public CyclicIterator<Corner> cyclicIterator() {
			return new CyclicIterator<Corner>(this);
		}

		@Override
		public CyclicIterator<Corner> cyclicIterator(int index) {
			int listSize = size();
			int realIndex = index % listSize;
			if (realIndex < 0) {
				realIndex += listSize;
			}
			return new CyclicIterator<Corner>(this, realIndex);
		}
	}

	public static final IRectanglePlacingFunction AfterOuterCorner = (
			Corner prev,
			Corner curr,
			Corner next,
			long currentBestSize,
			CornerPosition currentMinCorner,
			CornerPosition currentMaxCorner,
			int incomingSize,
			int outgoingSize,
			List<Corner> replacementOut) -> {
		if (!curr.type.isOuter() || !next.type.isOuter()) {
			return null;
		}
		int cmpOut = Integer.compare(curr.type.outgoing.edgeLength(curr.pos, next.pos), outgoingSize);
		CornerPosition diagonalCorner = curr.type.incoming.add(curr.pos, incomingSize);
		diagonalCorner = curr.type.outgoing.add(diagonalCorner, outgoingSize);
		CornerPosition currMin = CornerPosition.totalMin(diagonalCorner, currentMinCorner);
		CornerPosition currMax = CornerPosition.totalMax(diagonalCorner, currentMaxCorner);
		long currRectSize = SimpleRectanglePlacer.rectangleSize(currMin, currMax);
		if (Long.compareUnsigned(currRectSize, currentBestSize) >= 0) {
			return null;
		}
		CornerPosition placedAt = CornerPosition.totalMin(curr.pos, diagonalCorner);
		CornerPosition upper = curr.type.incoming.add(curr.pos, incomingSize);
		Corner upperC = new Corner(curr.type, upper);
		Corner diagonal = new Corner(upperC.type.followUp(true), diagonalCorner);
		CornerPosition branching = curr.type.outgoing.add(curr.pos, outgoingSize);
		if (cmpOut > 0) {
			Corner branchC = new Corner(diagonal.type.followUp(false), branching);
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
			Corner newBranch = new Corner(diagonal.type.followUp(true), branching);
			Corner newNext = new Corner(next.type.rebranch(true), next.pos);
			replacementOut.add(prev);
			replacementOut.add(upperC);
			replacementOut.add(diagonal);
			replacementOut.add(newBranch);
			replacementOut.add(newNext);
		}
		return placedAt;
	};

	public static final IRectanglePlacingFunction BeforeOuterCorner = (
			Corner prev,
			Corner curr,
			Corner next,
			long currentBestSize,
			CornerPosition currentMinCorner,
			CornerPosition currentMaxCorner,
			int incomingSize,
			int outgoingSize,
			List<Corner> replacementOut) -> {
		if (!curr.type.isOuter() || !prev.type.isOuter()) {
			return null;
		}
		int cmpInc = Integer.compare(curr.type.incoming.edgeLength(prev.pos, curr.pos), incomingSize);
		CornerPosition diagonalCorner = curr.type.incoming.inverted().add(curr.pos, incomingSize);
		diagonalCorner = curr.type.outgoing.inverted().add(diagonalCorner, outgoingSize);
		CornerPosition currMin = CornerPosition.totalMin(diagonalCorner, currentMinCorner);
		CornerPosition currMax = CornerPosition.totalMax(diagonalCorner, currentMaxCorner);
		long currRectSize = SimpleRectanglePlacer.rectangleSize(currMin, currMax);
		if (Long.compareUnsigned(currRectSize, currentBestSize) >= 0) {
			return null;
		}
		CornerPosition placedAt = CornerPosition.totalMin(curr.pos, diagonalCorner);
		CornerPosition branching = curr.type.incoming.inverted().add(curr.pos, incomingSize);
		Corner branchC = new Corner(prev.type.followUp(false), branching);
		Corner diagonal = new Corner(branchC.type.followUp(true), diagonalCorner);
		CornerPosition upper = curr.type.outgoing.inverted().add(curr.pos, outgoingSize);
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
			Corner newBranch = new Corner(newPrev.type.followUp(true), branchC.pos);
			replacementOut.add(newPrev);
			replacementOut.add(newBranch);
			replacementOut.add(diagonal);
			replacementOut.add(upperC);
			replacementOut.add(next);
		}
		return placedAt;
	};

	public static final IRectanglePlacingFunction AtInnerCorner = (
			Corner prev,
			Corner curr,
			Corner next,
			long currentBestSize,
			CornerPosition currentMinCorner,
			CornerPosition currentMaxCorner,
			int incomingSize,
			int outgoingSize,
			List<Corner> replacementOut) -> {
		if (curr.type.isOuter()) {
			return null;
		}
		CornerPosition diagonalCorner = curr.type.incoming.inverted().add(curr.pos, incomingSize);
		diagonalCorner = curr.type.outgoing.add(diagonalCorner, outgoingSize);
		CornerPosition currMin = CornerPosition.totalMin(diagonalCorner, currentMinCorner);
		CornerPosition currMax = CornerPosition.totalMax(diagonalCorner, currentMaxCorner);
		long currRectSize = SimpleRectanglePlacer.rectangleSize(currMin, currMax);
		if (Long.compareUnsigned(currRectSize, currentBestSize) >= 0) {
			return null; // Marker for failure
		}
		CornerPosition placedAt = CornerPosition.totalMin(curr.pos, diagonalCorner);
		int cmpInc = Integer.compare(curr.type.incoming.edgeLength(prev.pos, curr.pos), incomingSize);
		int cmpOut = Integer.compare(curr.type.outgoing.edgeLength(curr.pos, next.pos), outgoingSize);
		if (cmpInc > 0) {
			CornerPosition branching = curr.type.incoming.inverted().add(curr.pos, incomingSize);
			Corner branchingC = new Corner(prev.type.followUp(false), branching);
			replacementOut.add(prev);
			replacementOut.add(branchingC);
		} else if (cmpInc == 0) {
			// Don't add anything
		} else {
			CornerPosition branching = curr.type.incoming.inverted().add(curr.pos, incomingSize);
			Corner newPrev = new Corner(prev.type.rebranch(false), prev.pos);
			Corner branchC = new Corner(newPrev.type.followUp(true), branching);
			replacementOut.add(newPrev);
			replacementOut.add(branchC);
		}
		replacementOut.add(new Corner(curr.type.followUp(true), diagonalCorner));
		if (cmpOut > 0) {
			CornerPosition merging = curr.type.outgoing.add(curr.pos, outgoingSize);
			Corner mergingC = new Corner(curr.type.followUp(true).followUp(false), merging);
			replacementOut.add(mergingC);
			replacementOut.add(next);
		} else if (cmpOut == 0) {
			// Add nothing
		} else {
			CornerPosition merging = curr.type.outgoing.add(curr.pos, outgoingSize);
			Corner newNext = new Corner(next.type.rebranch(true), next.pos);
			Corner mergeC = new Corner(curr.type.followUp(true).followUp(true), merging);
			replacementOut.add(mergeC);
			replacementOut.add(newNext);
		}
		return placedAt;
	};

	private static final List<IRectanglePlacingFunction> placingFunctions = Arrays.asList(
			SimpleRectanglePlacer.AfterOuterCorner,
			SimpleRectanglePlacer.BeforeOuterCorner,
			SimpleRectanglePlacer.AtInnerCorner);

	private CornerList corners = new CornerList();
	private CornerPosition minCorner, maxCorner;

	public SimpleRectanglePlacer() {
		minCorner = new CornerPosition(0, 0);
		maxCorner = new CornerPosition(0, 0);
	}

	@Override
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
			for (IRectanglePlacingFunction func : SimpleRectanglePlacer.placingFunctions) {
				replacementBuffer.clear();
				CornerPosition placedAtInner = func.tryPlace(
						prev,
						curr,
						next,
						bestSize,
						newMinCorner,
						newMaxCorner,
						incomingSize,
						outgoingSize,
						replacementBuffer);
				if (placedAtInner == null) {
					continue;
				}
				replacementResult.clear();
				replacementResult.addAll(replacementBuffer);
				placedAt = placedAtInner;
				midCornerIndex = currentCorner.previousIndex();
				for (Corner c : replacementBuffer) {
					newMaxCorner = CornerPosition.totalMax(c.pos, newMaxCorner);
					newMinCorner = CornerPosition.totalMin(c.pos, newMinCorner);
				}
				bestSize = SimpleRectanglePlacer.rectangleSize(newMinCorner, newMaxCorner);
			}
		}
		this.minCorner = newMinCorner;
		this.maxCorner = newMaxCorner;
		ListIterator<Corner> replacingIt = corners.cyclicIterator(midCornerIndex);
		replacingIt.previous();
		replacingIt.remove(); // Before
		replacingIt.next();
		replacingIt.remove(); // Current
		replacingIt.next();
		replacingIt.remove(); // Next
		RewindableListIterator<Corner> beforeIt = new RewindableListIterator<>(replacingIt);
		beforeIt.mark();
		for (Corner c : replacementResult) {
			beforeIt.add(c);
		}
		beforeIt.previous();
		SimpleRectanglePlacer.fixInvariant(beforeIt, true);
		beforeIt.rewind();
		beforeIt.next();
		SimpleRectanglePlacer.fixInvariant(beforeIt, false);
		return placedAt;
	}

	static void fixInvariant(ListIterator<Corner> iterator, boolean forward) {
		while (true) {
			Corner firstB = SimpleRectanglePlacer.forward(iterator, forward);
			Corner secondB = SimpleRectanglePlacer.forward(iterator, forward);
			if (firstB.type.isOuter() || secondB.type.isOuter()) {
				return;
			}
			Corner afterBB = SimpleRectanglePlacer.forward(iterator, forward);
			SimpleRectanglePlacer.backward(iterator, forward); // AfterBB
			SimpleRectanglePlacer.backward(iterator, forward); // SecondBB
			SimpleRectanglePlacer.backward(iterator, forward); // FirstBB
			Corner beforeBB = SimpleRectanglePlacer.backward(iterator, forward);
			int cmp = secondB.type.outgoing.compare(beforeBB.pos, afterBB.pos);
			if (cmp < 0) {
				SimpleRectanglePlacer.forward(iterator, forward); // BeforeBB
				iterator.remove();
				SimpleRectanglePlacer.forward(iterator, forward); // FirstBB
				iterator.remove();
				SimpleRectanglePlacer.forward(iterator, forward); // SecondBB
				iterator.remove();
				CornerPosition newCorner = secondB.type.outgoing.isHorizontal()
						? new CornerPosition(beforeBB.pos.posX, afterBB.pos.posY)
						: new CornerPosition(afterBB.pos.posX, beforeBB.pos.posY);
				SimpleRectanglePlacer.addAfter(iterator, new Corner(secondB.type, newCorner), forward);
			} else if (cmp == 0) {
				iterator.remove();
				SimpleRectanglePlacer.forward(iterator, forward); // FirstBB
				iterator.remove();
				SimpleRectanglePlacer.forward(iterator, forward); // SecondBB
				iterator.remove();
				SimpleRectanglePlacer.forward(iterator, forward); // AfterBB
				iterator.remove();
			} else {
				SimpleRectanglePlacer.forward(iterator, forward); // BeforeBB
				SimpleRectanglePlacer.forward(iterator, forward); // FirstBB
				iterator.remove();
				SimpleRectanglePlacer.forward(iterator, forward); // SecondBB
				iterator.remove();
				SimpleRectanglePlacer.forward(iterator, forward); // AfterBB
				iterator.remove();
				CornerPosition newCorner = secondB.type.outgoing.isHorizontal()
						? new CornerPosition(afterBB.pos.posX, beforeBB.pos.posY)
						: new CornerPosition(beforeBB.pos.posX, afterBB.pos.posY);
				SimpleRectanglePlacer.addAfter(iterator, new Corner(firstB.type, newCorner), forward);
			}
		}
	}

	private void writeCornerPosition(NBTTagCompound nbtTag, CornerPosition pos) {
		nbtTag.setInteger("x", pos.posX);
		nbtTag.setInteger("y", pos.posY);
	}

	private CornerPosition readCornerPosition(NBTTagCompound nbtTag) {
		return new CornerPosition(nbtTag.getInteger("x"), nbtTag.getInteger("y"));
	}

	@Override
	public void saveTo(NBTTagCompound nbtTag) {
		NBTTagList listOfCornerPos = new NBTTagList();
		for (Corner c : this.corners) {
			NBTTagCompound cornerTag = new NBTTagCompound();
			writeCornerPosition(cornerTag, c.pos);
			cornerTag.setString("type", c.type.name());
			listOfCornerPos.appendTag(cornerTag);
		}
		NBTTagCompound minCornerTag = new NBTTagCompound();
		NBTTagCompound maxCornerTag = new NBTTagCompound();
		writeCornerPosition(minCornerTag, minCorner);
		writeCornerPosition(maxCornerTag, maxCorner);
		nbtTag.setTag("minCorner", minCornerTag);
		nbtTag.setTag("maxCorner", maxCornerTag);
		nbtTag.setTag("corners", listOfCornerPos);
	}

	@Override
	public void readFrom(NBTTagCompound nbtTag) {
		NBTTagList listOfCornerPos = nbtTag.getTagList("corners", NBT.TAG_COMPOUND);
		NBTTagCompound minCornerTag = nbtTag.getCompoundTag("minCorner");
		NBTTagCompound maxCornerTag = nbtTag.getCompoundTag("maxCorner");
		maxCorner = readCornerPosition(maxCornerTag);
		minCorner = readCornerPosition(minCornerTag);
		corners.clear();
		for (int i = 0; i < listOfCornerPos.tagCount(); i++) {
			NBTTagCompound cornerTag = listOfCornerPos.getCompoundTagAt(i);
			CornerType type = CornerType.valueOf(cornerTag.getString("type"));
			CornerPosition pos = readCornerPosition(cornerTag);
			corners.add(new Corner(type, pos));
		}
	}

	/**
	 * Returns a copy of the current corners of the outline of the kept area. All corners are in anti-clockwise order.
	 *
	 * @return
	 */
	public CornerList getCorners() {
		return new CornerList(corners);
	}

	@Override
	public String toString() {
		return corners.toString();
	}
}
