package mhfc.net.client.model.mhfcmodel.animation.stored;

import java.io.DataInputStream;

import org.lwjgl.util.vector.Vector2f;

/**
 * Describes an interpolation between two values at two frames. Let those pairs
 * be (f_1, v_1) and (f_2, v_2). The value returned will be v_1 if
 * <code>frame < f_2 </code> and v_2 otherwise.
 *
 * @author WorldSEnder
 *
 */
public class ConstantInterpolation extends Spline {
	public static final ISplineFactory factory = new ISplineFactory() {
		@Override
		public Spline newSpline(Vector2f left, Vector2f right,
				DataInputStream additionalData) {
			return new ConstantInterpolation(left, right);
		}
	};

	private Vector2f left;
	private Vector2f right;
	private ConstantInterpolation(Vector2f left, Vector2f right) {
		this.left = left;
		this.right = right;
	}
	@Override
	public boolean isInRange(float frame) {
		return frame >= this.left.x && frame <= this.right.x;
	}
	@Override
	public float getValueAt(float frame) {
		return frame < this.right.x ? this.left.y : this.right.y;
	}
}
