package mhfc.net.client.model.mhfcmodel.data;

import mhfc.net.client.model.mhfcmodel.glcontext.GLHelper;

/**
 * Represents the model data right after being loaded. No optimizations or
 * translation has happened yet. This is the raw data return from the
 * appropriate loader. <br>
 * This data will then be translated into an {@link IModelData} by the
 * appropriate {@link GLHelper} to be used to render the model. This is a
 * marker interface.
 *
 * @author WorldSEnder
 *
 */
public interface IRawData {

}
