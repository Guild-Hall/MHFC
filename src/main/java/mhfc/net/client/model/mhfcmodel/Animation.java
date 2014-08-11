package mhfc.net.client.model.mhfcmodel;

import java.util.Map;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public class Animation {
	/**
	 * Describes a BoneTransformation. This includes rotation, translation and
	 * scaling.
	 *
	 * @author WorldSEnder
	 *
	 */
	public static class BoneTransformation {
		/**
		 * This interpolation mode will yield to following results:<br>
		 * <code>factor < 1.0F</code>: the left transformation<br>
		 * <code>factor >= 1.0F</code>: the right transformation
		 */
		public static final int CONSTANT = 0;
		/**
		 * For every value the 'left' and 'right' {@link BoneTransformation}s
		 * argument hold, the value in the result will be<br>
		 * <code>(1-factor)*left + factor*right</code>.
		 */
		public static final int LINEAR = 1;
		/**
		 * For every value the 'left' and 'right' {@link BoneTransformation}s
		 * argument hold, the value in the result will be<br>
		 * <code>(1-factor)^2*(2*factor+1)*left + factor^2*(3-2*factor)*right</code>
		 * .<br>
		 * Note: this holds that no two different factors will generate the same
		 * output value (if left and right differ and factor is in [0, 1])
		 */
		public static final int SPLINE = 3;

		public static final BoneTransformation identity = new BoneTransformation();

		private Quaternion rotationQuat;
		private Vector3f translation;
		private float scale;

		public BoneTransformation() {
			this(new Quaternion(), new Vector3f(), 1.0F);
		}

		public BoneTransformation(Quaternion quat, Vector3f translation,
				float scale) {
			this.rotationQuat = quat;
			this.translation = translation;
			this.scale = scale;
		}

		public Matrix4f asMatrix() {
			return Utils.fromRotTrans(this.rotationQuat, this.translation)
					.scale(new Vector3f(scale, scale, scale));
		}
		/**
		 * Interpolates between two {@link BoneTransformation}s and returns
		 * their interpolation. This is can be useful when using the current
		 * subFrame as the factor.
		 *
		 * @param left
		 *            the "left" transformation of two to interpolate between
		 * @param right
		 *            the "right" transformation of two to interpolate between
		 * @param factor
		 *            how much the left/right Transformation should influence
		 *            the outcome. A value of 0 always means that the left
		 *            transform is returned, a value of 1 will always output the
		 *            right transformation
		 * @param mode
		 *            the mode to use. Use the symbolic constants
		 * @return
		 */
		public static BoneTransformation interpolate(BoneTransformation left,
				BoneTransformation right, float factor, int mode) {
			// TODO: interpolation algorithms
			return null;
		}
	}

	/**
	 * Reads a Animation from the given file. An attack contains all the
	 * following information: - when to pick the attack - how long the attack is
	 * (in frames) - what hitboxes to enable/disable - their damage - possibly
	 * entities to spawn during the attack - the position of the bones at all
	 * frames
	 *
	 * @param file
	 *            the file to load from
	 */
	public Animation(ResourceLocation file) {
		// TODO: load Animation
	}

	/**
	 * Returns a map that associates bone's names with their current
	 * {@link BoneTransformation}. The returned Map may be frozen. If a name is
	 * not found in the map it should be assumed that Bone is in his
	 * bindingpose-state. If a name is been found in the map that is not the
	 * name of a bone this is an error and may throw afterwards.<br>
	 *
	 * A return value of <code>null</code> should be handled like an empty map.
	 *
	 * @param s
	 */
	public Map<String, BoneTransformation> getCurrentTransformation(
			float subFrame) {
		return null;
	}
}
