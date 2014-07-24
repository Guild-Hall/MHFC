package mhfc.net.common.entity.type;

import mhfc.net.client.model.mhfcmodel.AnimationInformation;

public interface IMHFCAnimatedEntity {
	/**
	 * Should determine the currently "equipped" animInfo and return it. This is
	 * used to render the model
	 *
	 * @return The current {@link AnimationInformation}
	 */
	public AnimationInformation getAnimInformation();
	/**
	 * Returns for a specific part of the model and a specific subFrame in the
	 * current animation if the part should be rendered.
	 *
	 * You might want to include parts from
	 * {@link AnimationInformation#shouldDisplayPart(String, float)} .
	 *
	 * @param part
	 *            the part in question
	 * @param subFrame
	 *            makes the method subFrame sensible
	 *
	 * @return a predicate to match parts against that may be rendered
	 */
	public boolean shouldDisplayPart(String part, float subFrame);
}
