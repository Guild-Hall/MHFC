package mhfc.net.client.model.mhfcmodel.data;

import mhfc.net.client.model.mhfcmodel.glcontext.IGLHelper;
import net.minecraft.util.ResourceLocation;

/**
 * Represents the model data right after being loaded. No optimizations or
 * translation has happened yet. This is the raw data return from the
 * appropriate loader. <br>
 * This data will then be translated into an {@link IModelData} by the
 * appropriate {@link IGLHelper} to be used to render the model.
 *
 * @author WorldSEnder
 *
 */
public abstract class RawModelData {
	public final Vertex[] vertexArray = null;
	public final ResourceLocation textureLocation = null;
	public static class Vertex {

	}

}
