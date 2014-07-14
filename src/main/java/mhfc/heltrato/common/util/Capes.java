package mhfc.heltrato.common.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;

public class Capes {
	

public static void addDevCapes() {

String capeURL = "http://i.imgur.com/p0JW7NF.png";
String[] devs = {"XxHeltratoxX", "hewman3000", "NightCroweler", "karseius", "dracowhite", "danmanr98"};
String[] youtubersStrings = {"FuriousDestroyer"};

ThreadDownloadImageData image = new ThreadDownloadImageData(capeURL, null, null);

for (String username : devs) {

Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("cloaks/" + username), (ITextureObject) image);
}
}

}
