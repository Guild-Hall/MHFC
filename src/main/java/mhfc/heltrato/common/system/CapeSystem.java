package mhfc.heltrato.common.system;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;

public class CapeSystem {
	
	/**
	 * This Features a system that add donation capes to player.
	 * 
	 */
	
	public static void capeList() throws Exception
	{
		int timeout = 30;
		String capeURL = "http://i.imgur.com/p0JW7NF.png";
		URL capeListUrl = new URL("https://gist.githubusercontent.com/Heltrato/e3f86194c43424abb8c4/raw/donorcapes");
		URLConnection connection = capeListUrl.openConnection();
		connection.setConnectTimeout(timeout);
		connection.setReadTimeout(timeout);
		InputStream stream = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;
		while ((line = reader.readLine()) != null)
		{
			if(!line.isEmpty()){
				String username = line.toLowerCase();
				ThreadDownloadImageData image = new ThreadDownloadImageData(capeURL, null, null);
				Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("cloaks/" + username), (ITextureObject) image);
				
			}else{
				continue;
			}
			
		}
	}

}
