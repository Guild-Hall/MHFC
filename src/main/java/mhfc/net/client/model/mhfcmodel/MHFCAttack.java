package mhfc.net.client.model.mhfcmodel;

import net.minecraft.util.ResourceLocation;

public class MHFCAttack {
	protected int attackFrame = 0;

	/**
	 * Reads a MHFCAttack from the given file. An attack contains all the
	 * following information: - when to pick the attack - how long the attack is
	 * (in frames) - what hitboxes to enable/disable - their damage - possibly
	 * entities to spawn during the attack - the position of the bones at all
	 * frames
	 *
	 * @param file
	 *            the file to load from
	 */
	public MHFCAttack(ResourceLocation file) {
		// TODO: load MHFCAttack
	}

	/**
	 * Binds the current bone matrices to the OpenGL pipeline. Should only be
	 * called by a model for rendering purposes.
	 *
	 * @param subFrame
	 */
	public void glBindBoneMatrices(int glUniformLocation, float subFrame) {
		// TODO: compile and bind the location
		// glUniformMatrix4fv(glUniformLocation, boneCount, false, floatBuffer);
	}
	/**
	 * Gets the frame the animation is currently in. If the frame is not in
	 * range of the animation and the animation is not cyclic it will be shown
	 * as clamped to (0, lastFrame).
	 *
	 * @return the current frame/tick of the animation to play
	 */
	public int getAnimationFrame() {
		return attackFrame;
	}
}
