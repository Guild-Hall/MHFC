package mhfc.net.client.model.mhfcmodel;

import mhfc.net.client.model.mhfcmodel.animation.IAnimatedObject;
import mhfc.net.client.model.mhfcmodel.data.IRawData;
import mhfc.net.client.model.mhfcmodel.glcontext.GLHelper;
import mhfc.net.client.model.mhfcmodel.loader.VersionizedModelLoader;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.ModelFormatException;
/**
 *
 * @author WorldSEnder
 *
 */
public class ModelMHMD implements IModelCustom {
	private GLHelper renderHelper;
	/**
	 * Creates a new ModelMHMD from a given ResourceLocation. This constructor
	 * is expected to throw or propagate errors. All occuring
	 * {@link ModelFormatException} will be thrown to the user for him/her to
	 * deal with. If you wish to use an exception-free constructor load the
	 * model via {@link MHMDModelLoader#loadInstance(ResourceLocation)} using
	 * {@link MHMDModelLoader#instance}.
	 *
	 * @param resource
	 *            - The resource to load from. Refer to the wiki on
	 *            https://github.com/Heltrato/MHFC/wiki/API-overview to get
	 *            detail about the modelformat
	 * @throws ModelFormatException
	 *             - if the resource given cannot be read from or the file is
	 *             malformed
	 */
	public ModelMHMD(ResourceLocation resource) throws ModelFormatException {
		// This line could throw
		this(VersionizedModelLoader.loadVersionized(resource));
	}
	/**
	 * This constructor is expected to never throw.
	 */
	protected ModelMHMD(IRawData data) {
		this.renderHelper = GLHelper.getAppropriateHelper();
		if (data != null)
			this.renderHelper.loadInto(data);
		// Else there was an error during loading, we are supposed not to react
		// to that
	}
	/**
	 * Renders the given object in place (current OpenGL-transformation)
	 *
	 * @param object
	 *            the object to render (get animations from there)
	 */
	public void render(IAnimatedObject object, float parTick) {
		renderHelper.render(object, parTick);
	}

	@Override
	public String getType() {
		return "MHFC";
	}

	// ---------------------- Never used ---------------------------
	private static void throwUsage() {
		throw new IllegalAccessError(
				"Can't use this method with this model-type. Use one of render(...)");
	}
	@Override
	public void renderAll() {
		throwUsage();
	}
	@Override
	public void renderOnly(String... groupNames) {
		throwUsage();
	}
	@Override
	public void renderPart(String partName) {
		throwUsage();
	}
	@Override
	public void renderAllExcept(String... excludedGroupNames) {
		throwUsage();
	}
}
