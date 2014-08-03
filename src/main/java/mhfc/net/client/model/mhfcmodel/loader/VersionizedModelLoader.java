package mhfc.net.client.model.mhfcmodel.loader;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mhfc.net.MHFCMain;
import mhfc.net.client.model.mhfcmodel.Utils;
import mhfc.net.client.model.mhfcmodel.data.IRawData;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelFormatException;

public abstract class VersionizedModelLoader {
	public static final long magic = 0x4d484643204d444cL; // = MHMD
	private static Map<Integer, VersionizedModelLoader> registeredLoaders = new HashMap<>();

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
	/**
	 * Interprets the resource location as a file with a version. The correct
	 * loader is selected and the data is loaded. After this operation the data
	 * will follow the specified format.
	 *
	 * @param resLocation
	 * @return
	 */
	public static IRawData loadVersionized(ResourceLocation resLocation) {
		try (DataInputStream dis = new DataInputStream(new BufferedInputStream(
				Minecraft.getMinecraft().getResourceManager()
						.getResource(resLocation).getInputStream()))) {
			long foundMagic = dis.readLong();
			if (foundMagic != magic) {
				throw new ModelFormatException(String.format(
						"Wrong magic number. Found %x, expected %x.",
						foundMagic, magic));
			}

			long modelUUIDmost = dis.readLong();
			long modelUUIDleast = dis.readLong();
			String artist = Utils.readString(dis);

			int version = dis.readInt();
			VersionizedModelLoader loader = registeredLoaders.get(version);
			if (loader == null)
				throw new ModelFormatException("Unrecognized model version.");

			IRawData data = loader.loadFromInputStream(version, dis);
			MHFCMain.logger
					.debug("Successfully loaded model %s, version %d from artist %s. (Modelhash: %x-%x)",
							resLocation, version, artist, modelUUIDmost,
							modelUUIDleast);
			return data;

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
	 * Actually loads the inputstream into the {@link RawDataV1} format. This
	 * method should deal with all restrictions (names, etc.) and throw a
	 * ModelFormatException if the input violates any rules.
	 *
	 * @param version
	 *            the version of the file if one handler is registered for
	 *            multiple versions
	 * @param is
	 *            the input stream to load from
	 * @return a fully loaded {@link RawDataV1}
	 * @throws IOException
	 *             when either the file is too short or another IOExceptio
	 *             occurs
	 * @throws ModelFormatException
	 *             when the input stream is not conform to the specified format
	 */
	public abstract IRawData loadFromInputStream(int version, DataInputStream is)
			throws IOException, ModelFormatException;
}
