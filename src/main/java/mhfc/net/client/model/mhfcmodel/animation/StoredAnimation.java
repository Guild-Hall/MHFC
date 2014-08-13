package mhfc.net.client.model.mhfcmodel.animation;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelFormatException;

public class StoredAnimation implements IAnimation {
	/**
	 * Reads an Animation from the given file.
	 *
	 * @param file
	 *            the file to load from
	 * @throws ModelFormatException
	 *             when the file is not a correct .mhanm-file
	 */
	public StoredAnimation(ResourceLocation file) throws ModelFormatException {
		// TODO: load IAnimation
	}
	@Override
	public BoneTransformation getCurrentTransformation(String bone, int frame,
			float subFrame) {
		// TODO return interpolated transformation
		return null;
	}
}
