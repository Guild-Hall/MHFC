package mhfc.net.client.model.mhfcmodel.animation.stored;

import java.io.DataInputStream;
import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;

public class ConstantEaseOut extends Spline {
	public static final ISplineFactory factory = new ISplineFactory() {
		@Override
		public Spline newSpline(Vector2f left, Vector2f right,
				DataInputStream additionalData) throws IOException {
			return new ConstantEaseOut(left);
		}
	};

	private Vector2f left;

	public ConstantEaseOut(Vector2f left) {
		this.left = left;
	}

	@Override
	public boolean isInRange(float frame) {
		return frame >= left.x;
	}

	@Override
	public float getValueAt(float frame) {
		return left.y;
	}
}
