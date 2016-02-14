package mhfc.net.common.worldedit;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;

public class RegionSpliterator implements Spliterator<CuboidRegion> {
	private CuboidRegion region;

	public RegionSpliterator(CuboidRegion region) {
		Objects.requireNonNull(region);
		this.region = region;
	}

	private CuboidRegion splitRegion() {
		if (region == null) {
			return null;
		}
		Vector minPoint = region.getMinimumPoint();
		Vector maxPoint = region.getMaximumPoint();
		Vector maxFirst;
		Vector minSecond;
		int diffX = maxPoint.getBlockX() - minPoint.getBlockX(), diffY = maxPoint.getBlockY() - minPoint.getBlockY(),
				diffZ = maxPoint.getBlockZ() - minPoint.getBlockZ();
		if (diffX * diffY * diffZ < 2) {
			return null;
		}
		if (diffX > diffY) {
			if (diffX > diffZ) {
				diffX /= 2;
				maxFirst = minPoint.add(diffX, diffY, diffZ);
				minSecond = minPoint.add(diffX + 1, 0, 0);
			} else {
				diffZ /= 2;
				maxFirst = minPoint.add(diffX, diffY, diffZ);
				minSecond = minPoint.add(0, 0, diffZ + 1);
			}
		} else {
			if (diffY > diffZ) {
				diffY /= 2;
				maxFirst = minPoint.add(diffX, diffY, diffZ);
				minSecond = minPoint.add(0, diffY + 1, 0);
			} else {
				diffZ /= 2;
				maxFirst = minPoint.add(diffX, diffY, diffZ);
				minSecond = minPoint.add(0, 0, diffZ + 1);
			}
		}

		region = new CuboidRegion(minPoint, maxFirst);
		return new CuboidRegion(minSecond, maxPoint);
	}

	@Override
	public boolean tryAdvance(Consumer<? super CuboidRegion> action) {
		if (region == null)
			return false;
		action.accept(region);
		region = null;
		return true;
	}

	@Override
	public RegionSpliterator trySplit() {
		if (region == null)
			return null;
		RegionSpliterator newSplit = new RegionSpliterator(splitRegion());
		return newSplit;
	}

	@Override
	public long estimateSize() {
		return (region == null ? 0 : 1);
	}

	public int estimateBlocksAtOnce() {
		return region == null ? 0 : region.getArea();
	}

	@Override
	public int characteristics() {
		return DISTINCT | SIZED | NONNULL | IMMUTABLE | SUBSIZED;
	}
}
