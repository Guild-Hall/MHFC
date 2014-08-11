package mhfc.net.client.model.mhfcmodel;

import java.util.Random;

import mhfc.net.client.model.PartTickModelBase;
import mhfc.net.client.model.mhfcmodel.data.IRawData;
import mhfc.net.client.model.mhfcmodel.glcontext.GLHelper;
import mhfc.net.client.model.mhfcmodel.loader.VersionizedModelLoader;
import mhfc.net.common.entity.type.IMHFCAnimatedObject;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.TextureOffset;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.ModelFormatException;
/**
 *
 * @author WorldSEnder
 *
 */
public class ModelMHFC extends PartTickModelBase implements IModelCustom {
	private GLHelper renderHelper;
	/**
	 * Creates a new ModelMHFC from a given ResourceLocation. This constructor
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
	public ModelMHFC(ResourceLocation resource) throws ModelFormatException {
		// This line could throw
		IRawData amd = VersionizedModelLoader.loadVersionized(resource);
		this.renderHelper = GLHelper.getAppropriateHelper();
		if (amd != null)
			this.renderHelper.loadInto(amd);
		// Else there was an error, we are not supposed to react to that
	}
	/**
	 * This constructor is expected to never throw.
	 */
	protected ModelMHFC(IRawData data) {
		this.renderHelper = GLHelper.getAppropriateHelper();
		if (data != null)
			this.renderHelper.loadInto(data);
	}

	@Override
	public void render(Entity entity, float uLimbSwing,
			float interpolatedSwing, float uRotfloat, float headYaw,
			float interpolatedPitch, float size) {
		if (!(entity instanceof IMHFCAnimatedObject))
			throw new IllegalArgumentException(
					String.format(
							"Entity rendered must be an IMHFCAnimatedObject. EntityId %d",
							entity.getEntityId()));
		IMHFCAnimatedObject animatedEntity = (IMHFCAnimatedObject) entity;
		renderHelper.render(animatedEntity, this.getPartialTick());
	}

	@Override
	public ModelRenderer getRandomModelBox(Random r) {
		// FIXME For some reason we cannot return null here
		return null;
	}

	// Will not use this method
	@Override
	public TextureOffset getTextureOffset(String boxName) {
		return new TextureOffset(0, 0);
	}

	@Override
	public String getType() {
		return "MHFC";
	}

	// ---------------------- Never used ---------------------------
	@Override
	public void renderAll() {}
	@Override
	public void renderOnly(String... groupNames) {}
	@Override
	public void renderPart(String partName) {}
	@Override
	public void renderAllExcept(String... excludedGroupNames) {}
}
