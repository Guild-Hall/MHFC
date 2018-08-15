package mhfc.net.common.util.math;

import javax.vecmath.Matrix4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public class MatrixUtils {

	public static Matrix4d newIdentity() {
		Matrix4d mat = new Matrix4d();
		mat.setIdentity();
		return mat;
	}

	public static Matrix4d newScale(double sX, double sY, double sZ) {
		return newScale(new Vector3d(sX, sY, sZ));
	}

	public static Matrix4d newScale(Vector3d scale) {
		Matrix4d mat = newIdentity();
		mat.m00 = scale.x;
		mat.m11 = scale.y;
		mat.m22 = scale.z;
		return mat;
	}

	public static Matrix4d newTranslation(double tX, double tY, double tZ) {
		return newTranslation(new Vector3d(tX, tY, tZ));
	}

	public static Matrix4d newTranslation(Vector3d translation) {
		Matrix4d mat = newIdentity();
		mat.setTranslation(translation);
		return mat;
	}

	public static Matrix4d newRotation(Quat4d rotate) {
		Matrix4d mat = newIdentity();
		mat.set(rotate);
		return mat;
	}

}
