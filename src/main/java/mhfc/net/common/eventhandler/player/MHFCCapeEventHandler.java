package mhfc.net.common.eventhandler.player;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import mhfc.net.MHFCMain;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraftforge.client.event.RenderPlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MHFCCapeEventHandler {
	private static final Graphics TEST_GRAPHICS = new BufferedImage(128, 128,
			BufferedImage.TYPE_INT_RGB).getGraphics();
	private static final int timeout = 5000;
	private static final String serverLocation = "https://gist.githubusercontent.com/Heltrato/"
			+ "e3f86194c43424abb8c4/raw/donorcapes";

	public static final MHFCCapeEventHandler instance = new MHFCCapeEventHandler();

	private Map<String, String> cloaks = new HashMap<String, String>();
	private List<AbstractClientPlayer> capePlayers = new ArrayList<AbstractClientPlayer>();

	private MHFCCapeEventHandler() {
		buildCloakURLDatabase();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPreRenderSpecials(RenderPlayerEvent.Specials.Pre event) {
		if (!MHFCMain.isPreInitiliazed()
				|| !(event.entityPlayer instanceof AbstractClientPlayer))
			return;
		AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer) event.entityPlayer;

		if (!capePlayers.contains(abstractClientPlayer)) {
			String cloakURL = cloaks.get(event.entityPlayer.getDisplayName());

			if (cloakURL == null)
				return;

			capePlayers.add(abstractClientPlayer);

			new Thread(new CloakThread(abstractClientPlayer, cloakURL)).start();
			event.renderCape = true;
		}
	}

	public void buildCloakURLDatabase() {
		URL url;
		try {
			url = new URL(serverLocation);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(timeout);
			con.setReadTimeout(timeout);
			InputStream io = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(io));

			String str;
			int linetracker = 1;
			while ((str = br.readLine()) != null) {
				if (!str.startsWith("--") && !str.isEmpty()) {
					if (str.contains(":")) {
						String nick = str.substring(0, str.indexOf(":"));
						String link = str.substring(str.indexOf(":") + 1);
						new Thread(new CloakPreload(link)).start();
						cloaks.put(nick, link);
					} else {
						System.err
								.println("[MHFC] [capes.txt] Syntax error on line "
										+ linetracker + ": " + str);
					}
				}
				linetracker++;
			}

			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class CloakThread implements Runnable {
		AbstractClientPlayer abstractClientPlayer;
		String cloakURL;

		public CloakThread(AbstractClientPlayer player, String cloak) {
			abstractClientPlayer = player;
			cloakURL = cloak;

		}

		@Override
		public void run() {
			try {
				Image cape = new ImageIcon(new URL(cloakURL)).getImage();
				BufferedImage bo = new BufferedImage(cape.getWidth(null),
						cape.getHeight(null), BufferedImage.TYPE_INT_ARGB);
				bo.getGraphics().drawImage(cape, 0, 0, null);

				ReflectionHelper.setPrivateValue(ThreadDownloadImageData.class,
						abstractClientPlayer.getTextureCape(), bo,
						new String[]{"bufferedImage", "field_110560_d"});

				ReflectionHelper.setPrivateValue(ThreadDownloadImageData.class,
						abstractClientPlayer.getTextureCape(), false,
						new String[]{"textureUploaded", "field_110559_g"});
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	private class CloakPreload implements Runnable {
		String cloakURL;

		public CloakPreload(String link) {
			cloakURL = link;
		}

		@Override
		public void run() {
			try {
				TEST_GRAPHICS
						.drawImage(new ImageIcon(new URL(cloakURL)).getImage(),
								0, 0, null);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	public void refreshCapes() {
		cloaks.clear();
		capePlayers.clear();
		buildCloakURLDatabase();
	}
}