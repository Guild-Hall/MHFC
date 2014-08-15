package mhfc.heltrato.client.model.mhfcmodel;

import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

public class ModelMHFC extends ModelBase {
	protected final ResourceLocation resLocation;

	/**
	 * Creates a new ModelMHFC. Note that the model is
	 * not loaded yet but should be loaded with the {@link #load()} method. This
	 * allows us to avoid lagspikes when handled wisely
	 * and keeps a exception-free constructor.
	 *
	 * @param file - The file to load from. A
	 */
	public ModelMHFC(final ResourceLocation file) {
		resLocation = file;
	}

	public void load() {

	}
}
