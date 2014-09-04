package mhfc.net.client.model.mhfcmodel.animation.stored;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.client.model.ModelFormatException;

import org.lwjgl.util.vector.Vector2f;

public class AnimatedValue {
	/** The splines making up the full curve. */
	private List<Spline> splines;
	/**
	 * Reads the animated value from the {@link DataInputStream} given using
	 * keyframes. That are time, value pairs. Each pair forms a keyframe.
	 * Between successive two keyframes is a spline that can be the graph of any
	 * real-value-function.<br>
	 *
	 * @param defaultValue
	 *            the value when no keyframe is found
	 * @param is
	 */
	private float defaultValue;
	public AnimatedValue(float defaultValue, DataInputStream dis)
			throws ModelFormatException, IOException {
		this.defaultValue = defaultValue;
		splines = new ArrayList<>();
		int nbrFrames = dis.readUnsignedShort();
		if (nbrFrames == 0) {
			Vector2f point = new Vector2f(0, defaultValue);
			splines.add(Spline.fromDescriminator(Spline.EASE_IN_CONST, null,
					point, dis));
			splines.add(Spline.fromDescriminator(Spline.EASE_OUT_CONST, point,
					null, dis));
		} else {
			Vector2f left = null;
			Vector2f right = Spline.readPoint(dis);
			byte easeIn = dis.readByte();
			if ((easeIn & 0xF7) != 0)
				throw new ModelFormatException(
						String.format(
								"Ease-In interpolation bytes must be in [0x00...0x07], found 0x%x",
								easeIn));
			splines.add(Spline.fromDescriminator(easeIn, left, right, dis));
			for (int i = 1; i < nbrFrames; ++i) {
				left = right;
				right = Spline.readPoint(dis);
				byte interpolation = dis.readByte();
				if ((interpolation ^ 0x8) >= 8)
					throw new ModelFormatException(
							String.format(
									"Interpolation bytes must be in [0x08...0x0F], found 0x%x",
									interpolation));
				splines.add(Spline.fromDescriminator(interpolation, left,
						right, dis));
			}
			byte easeOut = dis.readByte();
			if ((easeOut ^ 0x10) >= 0x10)
				throw new ModelFormatException(
						String.format(
								"Ease-Out interpolation bytes must be in [0x10...0x1F], found 0x%x",
								easeOut));
			splines.add(Spline.fromDescriminator(easeOut, right, null, dis));
		}
	}
	/**
	 * Gets the y-value that corresponds to the time (x-value) given.
	 *
	 * @param time
	 *            the time/x-value on the curve
	 * @return the corresponding y-value
	 */
	public float getValueAt(float time) {
		for (Spline spline : this.splines) {
			if (spline.isInRange(time))
				return spline.getValueAt(time);
		}
		// Should not happen....
		return this.defaultValue;
	}
}
