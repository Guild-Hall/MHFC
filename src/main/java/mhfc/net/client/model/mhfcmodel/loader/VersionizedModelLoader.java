package mhfc.net.client.model.mhfcmodel.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import mhfc.net.client.model.mhfcmodel.data.RawModelData;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelFormatException;

public abstract class VersionizedModelLoader {
	private static Map<Integer, VersionizedModelLoader> registeredLoaders;

	public static RawModelData loadVersionized(ResourceLocation resLocation) {
		try (InputStream is = Minecraft.getMinecraft().getResourceManager()
				.getResource(resLocation).getInputStream()) {

		} catch (NullPointerException npe) {
			throw new ModelFormatException("File can't be null.", npe);
		} catch (IOException ioe) {
			throw new ModelFormatException(String.format(
					"Can't read from resource given (%s).", resLocation), ioe);
		} catch (ModelFormatException mfe) {
			throw new ModelFormatException(
					String.format(
							"A model format excpetion occured when reading from file %s",
							resLocation), mfe);
		}
		return null;
	}
}
