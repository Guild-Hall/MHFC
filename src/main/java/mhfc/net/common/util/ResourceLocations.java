package mhfc.net.common.util;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResourceLocations {

	public static BufferedInputStream openEmbeddedResource(ResourceLocation location) throws IOException {
		String pathToRes = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath();
		InputStream instream = MHFCQuestBuildRegistry.class.getResourceAsStream(pathToRes);
		if (instream == null) {
			throw new IOException("File doesn't exist");
		}
		return new BufferedInputStream(instream);
	}

}
