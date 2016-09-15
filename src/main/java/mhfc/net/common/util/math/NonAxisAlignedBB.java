package mhfc.net.common.util.math;

import java.util.Objects;

import javax.vecmath.Matrix4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import net.minecraft.util.EnumFacing;
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
import net.minecraft.util.math.Vec3i;

public class NonAxisAlignedBB extends AxisAlignedBB {
	private AxisAlignedBB UNIT_BOX = new AxisAlignedBB(0, 0, 0, 1, 1, 1);

	private static Matrix4d newIdentity() {
		Matrix4d mat = new Matrix4d();
		mat.setIdentity();
		return mat;
	}

	private static Matrix4d newScale(double sX, double sY, double sZ) {
		return newScale(new Vector3d(sX, sY, sZ));
	}

	private static Matrix4d newScale(Vector3d scale) {
		Matrix4d mat = newIdentity();
		mat.m00 = scale.x;
		mat.m11 = scale.y;
		mat.m22 = scale.z;
		return mat;
	}

	private static Matrix4d newTranslation(double tX, double tY, double tZ) {
		return newTranslation(new Vector3d(tX, tY, tZ));
	}

	private static Matrix4d newTranslation(Vector3d translation) {
		Matrix4d mat = newIdentity();
		mat.setTranslation(translation);
		return mat;
	}

	private static Matrix4d newRotation(Quat4d rotate) {
		Matrix4d mat = newIdentity();
		mat.set(rotate);
		return mat;
	}

	private static Vector4d vecToPosition(Vec3d vec) {
		return new Vector4d(vec.xCoord, vec.yCoord, vec.zCoord, 1.0);
	}

	private static Vector4d vecToDirection(Vec3i vec) {
		return new Vector4d(vec.getX(), vec.getY(), vec.getZ(), 0);
	}

	private static Vec3d positionToVec(Vector4d pos) {
		return new Vec3d(pos.x / pos.w, pos.y / pos.w, pos.z / pos.w);
	}

	private static double maxPosition(double[] row) {
		double x1 = row[0], x2 = row[1], x3 = row[2], off = row[3];
		return off + Math.max(0, x1) + Math.max(0, x2) + Math.max(0, x3);
	}

	private static double minPosition(double[] row) {
		double x1 = row[0], x2 = row[1], x3 = row[2], off = row[3];
		return off + Math.min(0, x1) + Math.min(0, x2) + Math.min(0, x3);
	}

	private static AxisAlignedBB faking(Matrix4d actualBox) {
		assert actualBox.m30 == 0 && actualBox.m31 == 0 && actualBox.m32 == 0;
		double scale = actualBox.m33;
		assert scale != 0;

		double[] buffer = new double[4];
		actualBox.getRow(0, buffer);
		double maxX = maxPosition(buffer) / scale, minX = minPosition(buffer) / scale;
		actualBox.getRow(1, buffer);
		double maxY = maxPosition(buffer) / scale, minY = minPosition(buffer) / scale;
		actualBox.getRow(2, buffer);
		double maxZ = maxPosition(buffer) / scale, minZ = minPosition(buffer) / scale;
		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	private Matrix4d localToGlobal;
	private Matrix4d globalToLocal;

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

	private NonAxisAlignedBB(Matrix4d actualBox) {
		this(faking(actualBox), actualBox);
	}

	private NonAxisAlignedBB(AxisAlignedBB fakeBox, Matrix4d actualBox) {
		super(fakeBox.minX, fakeBox.minY, fakeBox.minZ, fakeBox.maxX, fakeBox.maxY, fakeBox.maxZ);
		setLocalToGlobal(new Matrix4d(actualBox));
	}

	private void setLocalToGlobal(Matrix4d newMatrix) {
		this.localToGlobal = Objects.requireNonNull(newMatrix);
		this.globalToLocal = new Matrix4d(localToGlobal);
		// FIXME: what to do when not invertible? In this case, the box degenerates to a plane/line/point
		this.globalToLocal.invert(this.localToGlobal);
	}

	private void setupFromAxisAligned() {
		Matrix4d mat = newScale(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
		mat.mul(newTranslation(this.minX, this.minY, this.minZ));
		setLocalToGlobal(mat);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof NonAxisAlignedBB) {
			NonAxisAlignedBB otherBB = (NonAxisAlignedBB) other;
			return localToGlobal.equals(otherBB.localToGlobal);
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
	private NonAxisAlignedBB expandCore(double globalX, double globalY, double globalZ) {
		// TODO: what exactly is the "closest fit" (note method is not static)
		// (note): maybe least rotation, arbitrary scale?
		double scaleX = (maxX - minX + globalX) / (maxX - minX);
		double scaleY = (maxY - minY + globalY) / (maxY - minY);
		double scaleZ = (maxZ - minZ + globalZ) / (maxZ - minZ);
		Matrix4d newLocal = new Matrix4d(localToGlobal);
		newLocal.m00 *= scaleX;
		newLocal.m01 *= scaleX;
		newLocal.m02 *= scaleX;

		newLocal.m10 *= scaleY;
		newLocal.m11 *= scaleY;
		newLocal.m12 *= scaleY;

		newLocal.m20 *= scaleZ;
		newLocal.m21 *= scaleZ;
		newLocal.m22 *= scaleZ;
		AxisAlignedBB newBox = faking(newLocal);
		double offX = minX - newBox.minX;
		double offY = minY - newBox.minY;
		double offZ = minZ - newBox.minZ;
		return new NonAxisAlignedBB(newBox, newLocal).offset(offX, offY, offZ);
	}

	@Override
	public NonAxisAlignedBB expand(double x, double y, double z) {
		return expandCore(2 * x, 2 * y, 2 * z).offset(-x, -y, -z);
	}

	private double getLength(int axis, Vector4d buffer) {
		localToGlobal.getColumn(0, buffer);
		assert buffer.w == 0;
		return buffer.length();
	}

	@Override
	public double getAverageEdgeLength() {
		Vector4d buffer = new Vector4d();
		double lengthSum = getLength(0, buffer) + getLength(1, buffer) + getLength(2, buffer);

		return lengthSum / 3;
	}

	@Override
	public NonAxisAlignedBB offset(BlockPos pos) {
		return offset(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public NonAxisAlignedBB offset(double x, double y, double z) {
		if (x == 0 && y == 0 && z == 0) {
			return this;
		}
		Matrix4d offsetLocal = new Matrix4d(localToGlobal);
		double w = offsetLocal.m33;
		offsetLocal.m03 += x * w;
		offsetLocal.m13 += y * w;
		offsetLocal.m23 += z * w;
		return new NonAxisAlignedBB(super.offset(x, y, z), offsetLocal);
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
		Quat4d quat = new Quat4d(aX, aY, aZ, angleRad / 2);
		quat.normalize();
		Matrix4d newLocal = newRotation(quat);
		newLocal.mul(localToGlobal);
		return new NonAxisAlignedBB(newLocal);
	}

	/**
	 * Transforms the given vector (in place) and returns it (for convenience)
	 */
	private Vector4d toLocal(Vector4d vec) {
		globalToLocal.transform(vec);
		return vec;
	}

	private Vec3d asLocalVec(Vec3d globalPos) {
		return positionToVec(toLocal(vecToPosition(globalPos)));
	}

	@Override
	public boolean isVecInside(Vec3d vec) {
		return UNIT_BOX.isVecInside(asLocalVec(vec));
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

	private EnumFacing correctFacing(EnumFacing localFacing) {
		Vector4d globalFacingVec = vecToDirection(localFacing.getDirectionVec());
		localToGlobal.transform(globalFacingVec);

		return EnumFacing
				.getFacingFromVector((float) globalFacingVec.x, (float) globalFacingVec.y, (float) globalFacingVec.z);
	}

	private RayTraceResult correctRayTrace(RayTraceResult localRayTrace) {
		Vector4d globalHit = vecToPosition(localRayTrace.hitVec);
		localToGlobal.transform(globalHit);

		return new RayTraceResult(positionToVec(globalHit), correctFacing(localRayTrace.sideHit));
	}

	@Override
	public RayTraceResult calculateIntercept(Vec3d start, Vec3d end) {
		RayTraceResult result = UNIT_BOX.calculateIntercept(asLocalVec(start), asLocalVec(end));
		return correctRayTrace(result);
	}

	@Override
	public AxisAlignedBB setMaxY(double y2) {
		// Whatever... i dont care, this method make no sense any way
		throw new UnsupportedOperationException();
	}

	@Override
	public AxisAlignedBB union(AxisAlignedBB other) {
		return super.union(other);
	}
}
