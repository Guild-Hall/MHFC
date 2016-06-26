package mhfc.net.common.worldedit;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;

public class RegionSpliterator implements Spliterator<CuboidRegion> {
	private CuboidRegion region;
	private int maxSizePerDim;

	public RegionSpliterator(CuboidRegion region, int minSizePerDim) {
		if (minSizePerDim <= 0) {
			throw new IllegalArgumentException("size must be greater than 0");
		}
		this.region = Objects.requireNonNull(region);
		this.maxSizePerDim = minSizePerDim;
	}

	private int ceiledDiv(int x, int n) {
		// Ceiled integer division:
		int answer = x / n;
		if (x % n != 0) {
			answer++;
		}
		return answer;
	}

	/**
	 * @param size
	 *            must be greater than maxSizePerDim
	 * @return a new size for this region, the rest is for the other one
	 * @see #getSplits(int)
	 */
	private int split(int size) {
		// We want to find the next smaller sizeN = maxSizePerDim * 2^n < size
		// for maximum n and split there. This will result in a predictable number of splits
		if (size < maxSizePerDim) {
			throw new IllegalArgumentException("size must be great than maxSizePerDim");
		}
		return size >> 1;
	}

	/**
	 * Calculates how many splits will be made along an axis with size size
	 *
	 * @param size
	 *            the size in the polled dimension
	 * @return the number of spliterators that will result in that direction
	 * @see #split(int)
	 */
	private int getSplits(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("size must be greater or equal 0");
		}
		// FIXME: fix this, gives wrong sizes
		return ceiledDiv(size, maxSizePerDim);
	}

	private CuboidRegion splitRegion() {
		if (region == null) {
			return null;
		}
		Vector minPoint = region.getMinimumPoint();
		Vector maxPoint = region.getMaximumPoint();
		int sizeX = region.getWidth(), sizeY = region.getHeight(), sizeZ = region.getLength();
		Vector maxFirst;
		Vector minSecond;
		if (sizeX > maxSizePerDim) {
			int split = split(sizeX);
			maxFirst = minPoint.add(split - 1, sizeY - 1, sizeZ - 1);
			minSecond = minPoint.add(split, 0, 0);
		} else if (sizeY > maxSizePerDim) {
			int split = split(sizeY);
			maxFirst = minPoint.add(sizeX - 1, split - 1, sizeZ - 1);
			minSecond = minPoint.add(0, split, 0);
		} else if (sizeZ > maxSizePerDim) {
			int split = split(sizeZ);
			maxFirst = minPoint.add(sizeX - 1, sizeY - 1, split - 1);
			minSecond = minPoint.add(0, 0, split);
		} else {
			return null;
		}
		CuboidRegion ours = new CuboidRegion(minPoint, maxFirst);
		CuboidRegion other = new CuboidRegion(minSecond, maxPoint);
		region = ours;
		return other;
	}

	@Override
	public boolean tryAdvance(Consumer<? super CuboidRegion> action) {
		if (region == null) {
			return false;
		}
		action.accept(region);
		region = null;
		return true;
	}

	@Override
	public RegionSpliterator trySplit() {
		if (region == null) {
			return null;
		}
		CuboidRegion splitOff = splitRegion();
		if (splitOff == null) {
			return null;
		}
		RegionSpliterator newSplit = new RegionSpliterator(splitOff, maxSizePerDim);
		return newSplit;
	}

	@Override
	public long estimateSize() {
		return region == null
				? 0
				: 1L * getSplits(region.getHeight()) * getSplits(region.getWidth()) * getSplits(region.getLength());
	}

	@Override
	public int characteristics() {
		return DISTINCT | SIZED | NONNULL | IMMUTABLE | SUBSIZED;
	}
}
