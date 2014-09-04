package mhfc.net.client.model.mhfcmodel.animation.stored;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.client.model.ModelFormatException;

import org.lwjgl.util.vector.Vector2f;

/**
 * A spline is the line between two key-frames k_1 and k_2.
 *
 * @author WorldSEnder
 *
 */
public abstract class Spline {
	public static Map<Byte, ISplineFactory> factories;
	public static final byte EASE_IN_CONST = 0;

	public static final byte INTERPOLATION_CONST = 8;
	public static final byte INTERPOLATION_LINEAR = 9;
	public static final byte INTERPOLATION_BSPLINE = 10;

	public static final byte EASE_OUT_CONST = 16;
	static {
		factories = new HashMap<>();
		// Default registry
		registerFactory(EASE_IN_CONST, ConstantEaseIn.factory);
		registerFactory(INTERPOLATION_CONST, ConstantInterpolation.factory);
		registerFactory(INTERPOLATION_LINEAR, LinearInterpolation.factory);
		registerFactory(INTERPOLATION_BSPLINE, BSplineInterpolation.factory);
		registerFactory(EASE_OUT_CONST, ConstantEaseOut.factory);
		// TODO: EASE_OUT_LINEAR
	}
	public static interface ISplineFactory {
		/**
		 * This function should construct a new between the two points given
		 * reading additional data from the {@link DataInputStream} given.
		 *
		 * @param left
		 *            the left control-point of this spline
		 * @param right
		 *            the right control-point of this spline
		 * @param additionalData
		 *            read additional data from this
		 * @return the constructed spline. Not <code>null</code>
		 * @throws IOException
		 *             if an {@link IOException} results from reading from the
		 *             {@link DataInputStream} given
		 */
		public Spline newSpline(Vector2f left, Vector2f right,
				DataInputStream additionalData) throws IOException;
	}
	/**
	 * Registers an {@link ISplineFactory} for the descriminator byte given.
	 *
	 * @param descriminator
	 *            the descriminator byte
	 * @param factory
	 *            the factory, can't be <code>null</code>
	 * @return a previously registered factory, if any
	 */
	public static ISplineFactory registerFactory(byte descriminator,
			ISplineFactory factory) {
		if (factory == null)
			throw new IllegalArgumentException("Factory can't be null");
		return factories.put(descriminator, factory);
	}
	/**
	 * Returns if the frame given is actually on this spline.
	 *
	 * @param frame
	 * @return
	 */
	public abstract boolean isInRange(float frame);
	/**
	 * Gets the value of this spline at the given frame. This function should
	 * but may not hold for any frame given but can throw an
	 * {@link IllegalArgumentException} in case the frame is illegal. It <b>can
	 * not</b> throw if {@link #isInRange(float)} returns <code>true</code> for
	 * the requested frame.
	 *
	 * @param frame
	 *            the frame to get the value at.
	 * @return the value of of this spline at the frame
	 */
	public abstract float getValueAt(float frame);
	/**
	 * Constructs a new spline from the read descriminator byte using previously
	 * registered factories. Generally left.x should be less than right.x.
	 *
	 * @param descr
	 *            the read descriminator byte
	 * @param left
	 *            left point of this spline
	 * @param right
	 *            right point of this spline
	 * @param dis
	 *            a {@link DataInputStream} to read additional data from
	 * @return
	 * @throws ModelFormatException
	 * @throws IOException
	 */
	public static Spline fromDescriminator(byte descr, Vector2f left,
			Vector2f right, DataInputStream dis) throws ModelFormatException,
			IOException {
		ISplineFactory factory = factories.get(descr);
		if (factory == null)
			throw new ModelFormatException("Unknown interpolation mode.");
		// MHFCMain.logger
		// .debug("Not supporting bspline interpolation yet, using linear");
		return factory.newSpline(left, right, dis);
	}
	/**
	 * Utility function for reading a sole point from the
	 * {@link DataInputStream}.
	 *
	 * @throws IOException
	 *             if an {@link IOException} occurs.
	 */
	public static Vector2f readPoint(DataInputStream dis) throws IOException {
		float x = dis.readFloat();
		float y = dis.readFloat();
		return new Vector2f(x, y);
	}
}
