package mhfc.net.client.model.mhfcmodel;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustomLoader;
import net.minecraftforge.client.model.ModelFormatException;

public class MHFCModelLoader implements IModelCustomLoader {
	private static String[] suffixList = {".mhfcmd"};

	public static final MHFCModelLoader instance = new MHFCModelLoader();

	@Override
	public String getType() {
		return "MHFC";
	}

	@Override
	public String[] getSuffixes() {
		return suffixList;
	}
	@Override
	public ModelMHFC loadInstance(ResourceLocation resource)
			throws ModelFormatException {
		return new ModelMHFC(resource);
	}
}
