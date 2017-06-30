package mhfc.net.common.util.math;

import java.util.Objects;

/**
 * Represents a bounding box that is _not_ aligned to the axis. In any case where an axis aligned box is required, the
 * fully-enclosing-axis-aligned box is returned instead. All other calculations are made with the actual box in
 * mind.<br>
 * //TODO: Careful, this means that some symmetries might break, such as {@link AxisAlignedBB#union()}
 *
 */
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class NonAxisAlignedBB extends AxisAlignedBB {
	private Subspace3 localSubspace;

	public NonAxisAlignedBB(BlockPos pos) {
		super(pos);
		setupFromAxisAligned();
	}

	public NonAxisAlignedBB(BlockPos pos1, BlockPos pos2) {
		super(pos1, pos2);
		setupFromAxisAligned();
	}

	public NonAxisAlignedBB(double x1, double y1, double z1, double x2, double y2, double z2) {
		super(x1, y1, z1, x2, y2, z2);
		setupFromAxisAligned();
	}

	private NonAxisAlignedBB(Subspace3 subspace) {
		this(subspace.getWrappingBoundingBox(), subspace);
	}

	private NonAxisAlignedBB(AxisAlignedBB fakeBox, Subspace3 subspace) {
		super(fakeBox.minX, fakeBox.minY, fakeBox.minZ, fakeBox.maxX, fakeBox.maxY, fakeBox.maxZ);
		setLocal(subspace);
	}

	private void setLocal(Subspace3 subspace) {
		this.localSubspace = Objects.requireNonNull(subspace);
	}

	private void setupFromAxisAligned() {
		setLocal(Subspace3.createAxisAligned(this));
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof NonAxisAlignedBB) {
			NonAxisAlignedBB otherBB = (NonAxisAlignedBB) other;
			return this.localSubspace.equals(otherBB.localSubspace);
		}
		return super.equals(other);
	}

	@Override
	public double calculateXOffset(AxisAlignedBB other, double offsetX) {
		return super.calculateXOffset(other, offsetX);
	}

	@Override
	public double calculateYOffset(AxisAlignedBB other, double offsetY) {
		return super.calculateYOffset(other, offsetY);
	}

	@Override
	public double calculateZOffset(AxisAlignedBB other, double offsetZ) {
		return super.calculateZOffset(other, offsetZ);
	}

	/**
	 * Expands the bounding box by adding globalX to maxX, globalY to maxY, globalZ to maxZ
	 *
	 * @param globalX
	 * @param globalY
	 * @param globalZ
	 * @return
	 */
	private AxisAlignedBB expandCore(double globalX, double globalY, double globalZ) {
		Subspace3 expandedSubspace = localSubspace.expand(globalX, globalY, globalZ);
		return expandedSubspace == null
				? super.expand(globalX, globalY, globalZ).offset(-globalX / 2, -globalY / 2, -globalZ / 2)
				: new NonAxisAlignedBB(expandedSubspace);
	}

	@Override
	public AxisAlignedBB expand(double x, double y, double z) {
		return expandCore(2 * x, 2 * y, 2 * z).offset(-x, -y, -z);
	}

	@Override
	public double getAverageEdgeLength() {
		return localSubspace.getAvgEdgeLength();
	}

	@Override
	public NonAxisAlignedBB offset(BlockPos pos) {
		return offset(pos.getX(), pos.getY(), pos.getZ());
	}

	public NonAxisAlignedBB offset(Vec3d offset) {
		return offset(offset.xCoord, offset.yCoord, offset.zCoord);
	}

	@Override
	public NonAxisAlignedBB offset(double x, double y, double z) {
		if (x == 0 && y == 0 && z == 0) {
			return this;
		}
		return new NonAxisAlignedBB(localSubspace.offset(x, y, z));
	}

	/**
	 * Rotates the non axis aligned bounding box around the axis (aX, aY, aZ) by angleRad rads.
	 *
	 * @param aX
	 * @param aY
	 * @param aZ
	 * @param angleRad
	 * @return
	 */
	public NonAxisAlignedBB rotate(double aX, double aY, double aZ, double angleRad) {
		if (angleRad == 0 || aX == 0 && aY == 0 && aZ == 0) {
			return this;
		}
		return new NonAxisAlignedBB(localSubspace.rotate(aX, aY, aZ, angleRad));
	}

	@Override
	public boolean isVecInside(Vec3d vec) {
		return localSubspace.isPointInside(vec);
	}

	@Override
	public AxisAlignedBB addCoord(double x, double y, double z) {
		// Not what the doc says. If x > 0 add to maxX, x < 0: subtract from minX
		// And so on for y, z
		// Used to add (interpolated) motion to the bounding box
		double offX = x >= 0 ? 0 : x;
		double offY = y >= 0 ? 0 : y;
		double offZ = z >= 0 ? 0 : z;
		return expandCore(Math.abs(x), Math.abs(y), Math.abs(z)).offset(offX, offY, offZ);
	}

	@Override
	public RayTraceResult calculateIntercept(Vec3d start, Vec3d end) {
		return localSubspace.calculateIntercept(start, end);
	}

	@Override
	public AxisAlignedBB setMaxY(double y2) {
		// Whatever... i dont care, this method make no sense any way
		throw new UnsupportedOperationException();
	}

	@Override
	public AxisAlignedBB union(AxisAlignedBB other) {
		throw new UnsupportedOperationException();
	}

	public static NonAxisAlignedBB wrapping(AxisAlignedBB bb) {
		if (bb instanceof NonAxisAlignedBB) {
			return (NonAxisAlignedBB) bb;
		}
		return new NonAxisAlignedBB(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
	}
}
