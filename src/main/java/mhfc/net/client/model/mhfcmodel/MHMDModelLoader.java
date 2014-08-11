package mhfc.net.client.model.mhfcmodel;

import java.io.IOError;

import mhfc.net.client.model.mhfcmodel.data.IRawData;
import mhfc.net.client.model.mhfcmodel.loader.VersionizedModelLoader;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustomLoader;
import net.minecraftforge.client.model.ModelFormatException;

public class MHMDModelLoader implements IModelCustomLoader {
	private static String[] suffixList = {".mhmd"};

	/**
	 * Used to load the models in an exception-free manner (for n0bs or just
	 * lazy guys who don't want to deal with exceptions). This instance is also
	 * registered in the {@link AdvancedModelLoader}-class provided by forge.
	 */
	public static final MHMDModelLoader instance = new MHMDModelLoader();

	@Override
	public String getType() {
		return "MHFC";
	}

	@Override
	public String[] getSuffixes() {
		return suffixList;
	}
	/**
	 * Static version of {@link #loadInstance(ResourceLocation)}
	 *
	 * @param resLoc
	 *            the resource location to load from
	 * @return the loaded model, never null
	 * @see #loadInstance(ResourceLocation)
	 */
	public static ModelMHFC loadModel(ResourceLocation resLoc) {
		return instance.loadInstance(resLoc);
	}
	/**
	 * Actually loads the model. Note that this method will (normally) not
	 * throw. It catches {@link IOError}s as well as
	 * {@link ModelFormatException}, etc.
	 *
	 * @return the newly loaded {@link ModelMHFC}, never <code>null</code>
	 */
	@Override
	public ModelMHFC loadInstance(ResourceLocation resource) {
		IRawData loadedData = null;
		try {
			loadedData = VersionizedModelLoader.loadVersionized(resource);
		} catch (ModelFormatException mfe) {
			// TODO: log this as an error
		}
		// The following constructor will not throw but eat our loaded data even
		// when that is null.
		return new ModelMHFC(loadedData);
	}
}
