package mhfc.net.client.model.mhfcmodel.animation.stored;

import java.io.DataInputStream;

import org.lwjgl.util.vector.Vector2f;
/**
 * Describes an interpolation between two values at two frames. Let those pairs
 * be (f_1, v_1) and (f_2, v_2). The value returned will be a constant
 * interpolation between the two frames.<br>
 * Set <code>t = (frame - f_1)/(f_2 - f_1)</code>. The returned value will be
 * <code>(1 - t) * v_1 + t * v_2</code>.
 *
 * @author WorldSEnder
 *
 */
public class LinearInterpolation extends Spline {
	public static final ISplineFactory factory = new ISplineFactory() {
		@Override
		public Spline newSpline(Vector2f left, Vector2f right,
				DataInputStream additionalData) {
			return new LinearInterpolation(left, right);
		}
	};

	private Vector2f left;
	private Vector2f right;

	private LinearInterpolation(Vector2f left, Vector2f right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean isInRange(float frame) {
		return frame >= this.left.x && frame <= this.right.x;
	}

	@Override
	public float getValueAt(float frame) {
		float split = this.right.x - this.left.x;
		return (this.right.x - frame) / (split) * this.left.y + //
				(frame - this.left.x) / (split) * this.right.y;
	}
}
