package mhfc.net.client.core.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;

public class MHFCCapeRegistry {
	private static final String capeURL = "http://i.imgur.com/p0JW7NF.png";
	private static final String[] devs = {"XxHeltratoxX", "hewman3000",
			"NightCroweler", "karseius", "dracowhite", "danmanr98",
			"WorldSEnder", "HeroicKatora"};
	@SuppressWarnings("unused")
	// Will be added in the future
	private static final String[] youtubersStrings = {"FuriousDestroyer"};

	public static void init() {
		ResourceLocation defaultCapeRes = null;

		ThreadDownloadImageData image = new ThreadDownloadImageData(capeURL,
				defaultCapeRes, null);

		for (String username : devs) {
			Minecraft.getMinecraft().renderEngine.loadTexture(
					new ResourceLocation("cloaks/" + username), image);
		}
	}

}
