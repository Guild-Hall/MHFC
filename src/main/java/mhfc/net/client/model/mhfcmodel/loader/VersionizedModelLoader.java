package mhfc.net.client.model.mhfcmodel.loader;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Map;

import mhfc.net.client.model.mhfcmodel.data.RawModelData;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelFormatException;

public abstract class VersionizedModelLoader {
	public static final int magic = 0x4D484D44; // = MHMD
	private static Map<Integer, VersionizedModelLoader> registeredLoaders;

	static {
		registerLoader(1, LoaderVersion1.instance);
	}
	/**
	 * I don't think we ever gonna reach this number but version will be
	 * interpreted as unsigned here.
	 *
	 * @param version
	 *            the version to register the loader for
	 * @param vml
	 *            the loader to register
	 */
	public static boolean registerLoader(int version, VersionizedModelLoader vml) {
		return registeredLoaders.put(version, vml) != null;
	}

	public static RawModelData loadVersionized(ResourceLocation resLocation) {
		try (DataInputStream dis = new DataInputStream(new BufferedInputStream(
				Minecraft.getMinecraft().getResourceManager()
						.getResource(resLocation).getInputStream()))) {

			int foundMagic = dis.readInt();
			if (foundMagic != magic) {
				throw new ModelFormatException(String.format(
						"Wrong magic number. Found %x, expected %x.",
						foundMagic, magic));
			}
			int version = dis.readInt();
			VersionizedModelLoader loader = registeredLoaders.get(version);
			if (loader == null)
				throw new ModelFormatException("Unrecognized model version.");
			return loader.loadFromInputStream(version, dis);

		} catch (NullPointerException npe) {
			throw new ModelFormatException("File can't be null.", npe);
		} catch (EOFException eofe) {
			throw new ModelFormatException(String.format(
					"Unexpected end of file.", resLocation), eofe);
		} catch (IOException ioe) {
			throw new ModelFormatException(String.format(
					"Can't read from resource given (%s).", resLocation), ioe);
		} catch (ModelFormatException mfe) {
			throw new ModelFormatException(
					String.format(
							"A model format exception occured when reading from file %s",
							resLocation), mfe);
		}
	}
	/**
	 * Actually loads the inputstream into the {@link RawModelData} format.
	 *
	 * @param version
	 *            the version of the file if one handler is registered for
	 *            multiple versions
	 * @param is
	 *            the input stream to load from
	 * @return a fully loaded {@link RawModelData}
	 */
	public abstract RawModelData loadFromInputStream(int version,
			DataInputStream is);
}
