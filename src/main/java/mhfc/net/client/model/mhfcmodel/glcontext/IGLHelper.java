package mhfc.net.client.model.mhfcmodel.glcontext;

import mhfc.net.client.model.mhfcmodel.data.IModelData;
import mhfc.net.client.model.mhfcmodel.data.RawModelData;
import mhfc.net.client.model.mhfcmodel.loader.VersionizedModelLoader;
import mhfc.net.common.entity.type.IMHFCAnimatedEntity;

/**
 * Represents an IGLHelper. That is a render-glHelper for the correct OpenGL-
 * version present on this computer.
 *
 * @author WorldSEnder
 *
 * @param <T>
 *            the ModelDataType this IGLHelper can handle
 */
public interface IGLHelper<T extends IModelData> {
	/**
	 * This method is used to translate the {@link RawModelData} that was
	 * previously read by the appropriate {@link VersionizedModelLoader}.
	 *
	 * @param amd
	 *            the data loaded by the {@link VersionizedModelLoader}
	 * @return data that can be understood by this {@link IGLHelper}
	 */
	public T translate(RawModelData amd);
	/**
	 * Actually renders the model given by data. This is normally the same this
	 * handler previously returned by {@link #translate(RawModelData)}.
	 *
	 * @param data
	 *            the data to render
	 * @param entity
	 *            the entity to render
	 * @param subFrame
	 *            the current subFrame, always 0.0 <= subFrame <= 1.0
	 **/
	public void render(T data, IMHFCAnimatedEntity entity, float subFrame);
}
