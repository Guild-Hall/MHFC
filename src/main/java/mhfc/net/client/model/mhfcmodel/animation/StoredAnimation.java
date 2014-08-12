package mhfc.net.client.model.mhfcmodel.animation;

import net.minecraft.util.ResourceLocation;

public class StoredAnimation implements IAnimation {
	/**
	 * Reads an Animation from the given file.
	 *
	 * @param file
	 *            the file to load from
	 */
	public StoredAnimation(ResourceLocation file) {
		// TODO: load IAnimation
	}

	@Override
	public BoneTransformation getCurrentTransformation(String bone, int frame,
			float subFrame) {
		// TODO return interpolated transformation
		return null;
	}
}
