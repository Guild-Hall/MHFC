package mhfc.net.client.model.mhfcmodel;

import net.minecraft.util.ResourceLocation;

public class MHFCAttack {

	/**
	 * Reads a MHFCAttack from the given file. An attack contains all the
	 * following information:
	 * - when to pick the attack
	 * - how long the attack is (in frames)
	 * - what hitboxes to enable/disable
	 * - their damage
	 * - possibly entities to spawn during the attack
	 * - the position of the bones at all frames
	 *
	 * @param file the file to load from
	 */
	public MHFCAttack(ResourceLocation file) {
		// TODO: load MHFCAttack
	}

	// Later for the AI to update the bounding boxes
	// public Bone[] getBones() {
	//
	// }
	/**
	 * Binds the current bone matrices to the OpenGL pipeline.
	 * Should only be called by a model for rendering purposes.
	 *
	 * @param frame
	 * @param subFrame
	 */
	public void glBindBoneMatrices(int glUniformLocation, int frame,
			float subFrame) {
		// TODO: compile and bind the location
		// glUniformMatrix4fv(glUniformLocation, boneCount, false, floatBuffer);
	}
}
