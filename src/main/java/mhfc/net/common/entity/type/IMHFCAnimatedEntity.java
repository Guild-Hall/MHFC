package mhfc.net.common.entity.type;

import mhfc.net.client.model.mhfcmodel.AnimationInformation;

public interface IMHFCAnimatedEntity {
	/**
	 * Should determine the currently "equipped" animInfo and return it.
	 * This is used to render the model
	 *
	 * @return The current {@link AnimationInformation}
	 */
	public AnimationInformation getAnimInformation();
}
