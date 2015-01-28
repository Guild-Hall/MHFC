package mhfc.net.common.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;

public class MHFCCapes {
	public static void addDevCapes() {
		String capeURL = "http://i.imgur.com/p0JW7NF.png";
		String[] devs = {"XxHeltratoxX", "hewman3000", "NightCroweler",
				"karseius", "dracowhite", "danmanr98", "WorldSEnder"};
		String[] youtubersStrings = {"FuriousDestroyer"};
		ResourceLocation defaultCapeRes = null;

		// TODO This had to be fixed for this Forge Version, why was it wrong in
		// the first place?
		ThreadDownloadImageData image = new ThreadDownloadImageData( //
				capeURL, // Download location
				defaultCapeRes, null); // On download-failure this will be
										// displayed
		// No postprocessing

		for (String username : devs) {
			Minecraft.getMinecraft().renderEngine.loadTexture(
					new ResourceLocation("cloaks/" + username), image);
		}
	}

}
