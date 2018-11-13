package mhfc.net.common.util.math;

import javax.vecmath.Matrix4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * Describes an arbitrary subspace of the 3-dimensional euclidian space and a geometric object lying in it, described in
 * local coordinates as the a box from (0,..) to (1,..)
 *
 * @author WorldSEnder
 *
 */
public class Subspace3 {
	protected static final double EPSILON = 1e-9;

	protected static boolean isSmallerEq(double left, double right) {
		return left < right || isEpsilonEqual(left, right);
	}

	protected static boolean isGreaterEq(double left, double right) {
		return left > right || isEpsilonEqual(left, right);
	}

	protected static boolean isInInterval(double val, double min, double max) {
		return isGreaterEq(val, min) && isSmallerEq(val, max);
	}

	protected static boolean isEpsilonEqual(double left, double right) {
		return Math.abs(left - right) < EPSILON;
	}

	// Homogeneous matrix with scale 1 and invertible, w always 1
	private final Matrix4d localMatrix;
	private final Matrix4d invLocalMatrix;
	// All entries >= 0
	private final Vector3d sizes;
	private final AxisAlignedBB asAABB;
	private ThreadLocal<Vector4d> vectorBuffer = new ThreadLocal<>();
	private ThreadLocal<Vector4d> vectorBuffer2 = new ThreadLocal<>();

	private Subspace3(Matrix4d localmat, Vector3d sizes) {
		this.localMatrix = localmat;
		this.invLocalMatrix = new Matrix4d();
		this.invLocalMatrix.invert(localmat);
		this.sizes = sizes;
		this.asAABB = computeAABB();
	}

	private AxisAlignedBB computeAABB() {
		double[] buffer = new double[4];
		localMatrix.getRow(0, buffer);
		double minX = minPosition(buffer);
		double maxX = maxPosition(buffer);
		localMatrix.getRow(1, buffer);
		double minY = minPosition(buffer);
		double maxY = maxPosition(buffer);
		localMatrix.getRow(2, buffer);
		double minZ = minPosition(buffer);
		double maxZ = maxPosition(buffer);
		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	protected double maxPosition(double[] row) {
		double x1 = row[0] * sizes.x, x2 = row[1] * sizes.y, x3 = row[2] * sizes.z, off = row[3];
		return off + Math.max(0, x1) + Math.max(0, x2) + Math.max(0, x3);
	}

	protected double minPosition(double[] row) {
		double x1 = row[0] * sizes.x, x2 = row[1] * sizes.y, x3 = row[2] * sizes.z, off = row[3];
		return off + Math.min(0, x1) + Math.min(0, x2) + Math.min(0, x3);
	}

	/**
	 * Returns an {@link AxisAlignedBB} that contains the whole described object and wraps "as close as possible" around
	 * it - though no actual contract has to be fulfilled regarding the second property, it may contain the object very
	 * loosely in reality.
	 *
	 * @return
	 */
	public AxisAlignedBB getWrappingBoundingBox() {
		return asAABB;
	}

	/**
	 * Calculates the {@link RayTraceResult} (in global coordinates) when a trace starting at start and ending at end
	 * would potentially intercept the subspace.<br>
	 * If the ray does intercept, {@link RayTraceResult#hitVec} should be the first point hit, when coming from start
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	public RayTraceResult calculateIntercept(Vec3d start, Vec3d end) {
		// line = start + p*(end - start)
		// space = local[3] + a*local[0] + b*local[1] + c*local[2]
		// NB: a \el [0, size[0]], b \el [0, size[1]], c \el [0, size[2]], p \el [0, 1]
		// Find smallest such p
		// First transform start and end into the subspace
		Vector4d buffStart = vectorBuffer.get();
		buffStart.set(start.x, start.y, start.z, 1);
		Vector4d buffVec = vectorBuffer2.get();
		buffVec.set(end.x, end.y, end.z, 1);
		invLocalMatrix.transform(buffStart);
		invLocalMatrix.transform(buffVec);
		buffVec.sub(buffStart);
		// In local space: find smallest p:
		// l = buffStart + p * buffVec, l \el [0, size], p \el [0, 1]
		Interval intervalP = Interval.ZERO_TO_ONE;
		Interval intervalPCoord;
		intervalPCoord = findInterval(buffStart.x, buffVec.x, sizes.x);
		intervalP = intervalP.intersection(intervalPCoord);
		if (intervalP == null) {
			return null;
		}
		intervalPCoord = findInterval(buffStart.y, buffVec.y, sizes.y);
		intervalP = intervalP.intersection(intervalPCoord);
		if (intervalP == null) {
			return null;
		}
		intervalPCoord = findInterval(buffStart.z, buffVec.z, sizes.z);
		intervalP = intervalP.intersection(intervalPCoord);
		if (intervalP == null) {
			return null;
		}
		double p = intervalP.min;
		Vec3d hit = start.scale(1 - p).add(end.scale(p));
		return new RayTraceResult(hit, EnumFacing.DOWN);
	}

	private Interval findInterval(double start, double vec, double size) {
		if (isEpsilonEqual(vec, 0)) {
			if (!isInInterval(start, 0, size)) {
				return null;
			}
			return Interval.ZERO_TO_ONE; // mathematically should be (-\inf, inf)
		}
		double minPX = (0 - start) / vec;
		double maxPX = (size - start) / vec;
		return new Interval(minPX, maxPX);
	}

	/**
	 * Determines if the point represented by vec is inside the subspace.
	 *
	 * @param vec
	 * @return
	 */
	public boolean isPointInside(Vec3d vec) {
		// space = local[3] + a*local[0] + b*local[1] + c*local[2]
		// NB: a \el [0, size[0]], b \el [0, size[1]], c \el [0, size[2]]
		// \exists a, b, c: space = vec?
		Vector4d buffer = vectorBuffer.get();
		buffer.set(vec.x, vec.y, vec.z, 1);
		invLocalMatrix.transform(buffer);
		return isInInterval(buffer.x, 0, sizes.x) && isInInterval(buffer.y, 0, sizes.y)
				&& isInInterval(buffer.z, 0, sizes.z);

	}

	/**
	 * Expands (or contracts) the subspace by the specified amount in global dimensions into positive global axis
	 * directions. This may rotate and scale the space a bit, the goal is to achieve a somewhat similar subspace in the
	 * end.
	 *
	 * @param globalX
	 * @param globalY
	 * @param globalZ
	 * @return a new subspace
	 */
	public Subspace3 expand(double globalX, double globalY, double globalZ) {
		// FIXME: What to actually do? There's no way to fulfill this in all oriented subspaces, is there?
		return null;
	}

	/**
	 * Offsets the subspace by the given amount. This may result in a homogeneous subspace
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Subspace3 offset(double x, double y, double z) {
		Matrix4d offsetLocal = new Matrix4d(localMatrix);
		offsetLocal.m03 += x;
		offsetLocal.m13 += y;
		offsetLocal.m23 += z;
		return new Subspace3(offsetLocal, sizes);
	}

	public Subspace3 rotate(double aX, double aY, double aZ, double angleRad) {
		Quat4d quat = new Quat4d(aX, aY, aZ, angleRad / 2);
		quat.normalize();
		Matrix4d newLocal = MatrixUtils.newRotation(quat);
		newLocal.mul(localMatrix);
		return new Subspace3(newLocal, sizes);
	}

	public double getAvgEdgeLength() {
		return (sizes.x + sizes.y + sizes.z) / 3;
	}

	public static Subspace3 createAxisAligned(AxisAlignedBB bb) {
		Matrix4d unitMatrix = MatrixUtils.newIdentity();
		unitMatrix.m03 = bb.minX;
		unitMatrix.m13 = bb.minY;
		unitMatrix.m23 = bb.minZ;
		double scaleX = bb.maxX - bb.minX;
		double scaleY = bb.maxY - bb.minY;
		double scaleZ = bb.maxZ - bb.minZ;
		return new Subspace3(unitMatrix, new Vector3d(scaleX, scaleY, scaleZ));
	}

}
