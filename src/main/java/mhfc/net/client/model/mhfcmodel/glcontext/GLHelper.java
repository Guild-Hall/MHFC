package mhfc.net.client.model.mhfcmodel.glcontext;

import mhfc.net.client.model.mhfcmodel.data.IRawData;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1;
import mhfc.net.client.model.mhfcmodel.loader.VersionizedModelLoader;
import mhfc.net.common.entity.type.IMHFCAnimatedEntity;

/**
 * Represents an GLHelper. That is a render-glHelper for the correct OpenGL-
 * version present on this computer.
 *
 * @author WorldSEnder
 *
 * @param <T>
 *            the ModelDataType this GLHelper can handle
 */
public abstract class GLHelper {
	/**
	 * This method is used to translate the {@link RawDataV1} that was
	 * previously read by the appropriate {@link VersionizedModelLoader}.
	 *
	 * @param amd
	 *            the data loaded by the {@link VersionizedModelLoader}
	 * @return data that can be understood by this {@link GLHelper}
	 */
	public final void loadInto(IRawData amd) {
		if (amd instanceof RawDataV1)
			this.loadInto((RawDataV1) amd);
	}
	/**
	 * Loads data of version 1.
	 *
	 * @param datav1
	 *            the data to be loaded into this handler
	 */
	public abstract void loadInto(RawDataV1 datav1);
	/**
	 * Actually renders the model given by data. This is normally the same this
	 * handler previously returned by {@link #translate(RawDataV1)}.
	 *
	 * @param entity
	 *            the entity to render
	 * @param subFrame
	 *            the current subFrame, always 0.0 <= subFrame <= 1.0
	 **/
	public abstract void render(IMHFCAnimatedEntity entity, float subFrame);
}
