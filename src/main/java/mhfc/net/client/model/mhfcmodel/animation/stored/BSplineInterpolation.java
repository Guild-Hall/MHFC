package mhfc.net.client.model.mhfcmodel.animation.stored;

import java.io.DataInputStream;
import java.io.IOException;

import mhfc.net.MHFCMain;

import org.lwjgl.util.vector.Vector2f;

public class BSplineInterpolation extends Spline {
	public static final ISplineFactory factory = new ISplineFactory() {
		@Override
		public Spline newSpline(Vector2f left, Vector2f right,
				DataInputStream additionalData) throws IOException {
			Vector2f leftHandle = Spline.readPoint(additionalData);
			Vector2f rightHandle = Spline.readPoint(additionalData);
			return new BSplineInterpolation(left, leftHandle, rightHandle,
					right);
		}
	};
	private Vector2f left;
	private Vector2f leftHandle;
	private Vector2f rightHandle;
	private Vector2f right;
	public BSplineInterpolation(Vector2f left, Vector2f leftHandle,
			Vector2f rightHandle, Vector2f right) {
		MHFCMain.logger
				.debug("Not supporting bspline interpolation yet, using linear");
		this.left = left;
		this.leftHandle = leftHandle;
		this.rightHandle = rightHandle;
		this.right = right;
	}

	@Override
	public boolean isInRange(float frame) {
		return frame >= this.left.x && frame <= this.right.x;
	}

	@Override
	public float getValueAt(float frame) {
		// TODO: BSpline interpolation
		float split = this.right.x - this.left.x;
		return (this.right.x - frame) / (split) * this.left.y + //
				(frame - this.left.x) / (split) * this.right.y;
	}
}
