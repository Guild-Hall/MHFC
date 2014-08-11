package mhfc.net.client.model.mhfcmodel.data;

import mhfc.net.client.model.mhfcmodel.Animation;
import mhfc.net.client.model.mhfcmodel.glcontext.GLHelper;

import com.google.common.base.Predicate;

/**
 * Marker interface for ModelData used by {@link GLHelper} classes.
 *
 * @author WorldSEnder
 *
 */
public interface IModelData {
	public void renderAll(Animation currAttack, float subFrame);
	public void renderFiltered(Predicate<String> filter, Animation currAttack,
			float subFrame);
	public void free();
}
