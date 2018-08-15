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

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MHFCCapeEventHandler {
	private static final Graphics TEST_GRAPHICS = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB).getGraphics();
	private static final int timeout = 5000;
	private static final String serverPath = "https://gist.githubusercontent.com/Heltrato/"
			+ "e3f86194c43424abb8c4/raw/donorcapes";
	private static final URL capesLocation;
	static {
		try {
			capesLocation = new URL(serverPath);
		} catch (MalformedURLException e) {
			throw new AssertionError("Unreachable: Malformed path to capes location", e);
		}
	}

	public static final MHFCCapeEventHandler instance = new MHFCCapeEventHandler();

	private Map<String, String> cloaks = new HashMap<>();
	private List<AbstractClientPlayer> capePlayers = new ArrayList<>();

	private MHFCCapeEventHandler() {
		new Thread(this::buildCloakURLDatabase, "mhfcCloakDatabase").start();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPreRenderSpecials(RenderPlayerEvent.Pre event) {
		if (!(event.getEntityPlayer() instanceof AbstractClientPlayer)) {
			return;
		}
		AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer) event.getEntityPlayer();

		if (!capePlayers.contains(abstractClientPlayer)) {
			String cloakURL = cloaks.get(event.getEntityPlayer().getName());

			if (cloakURL == null) {
				return;
			}

			capePlayers.add(abstractClientPlayer);

			new Thread(new CloakThread(abstractClientPlayer, cloakURL)).start();
			//FIXME: event.getRenderer().addLayer(/* Layer */);
		}
	}

	public void buildCloakURLDatabase() {
		try {
			URLConnection con = capesLocation.openConnection();
			con.setConnectTimeout(timeout);
			con.setReadTimeout(timeout);
			try (
					InputStream io = con.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(io))) {

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
							System.err.println("[MHFC] [capes.txt] Syntax error on line " + linetracker + ": " + str);
						}
					}
					linetracker++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class CloakThread implements Runnable {
		@SuppressWarnings("unused")
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
				BufferedImage bo = new BufferedImage(
						cape.getWidth(null),
						cape.getHeight(null),
						BufferedImage.TYPE_INT_ARGB);
				bo.getGraphics().drawImage(cape, 0, 0, null);
				// FIXME: load the capes, check if that is "legal":
				// @see
				// https://twitter.com/MojangSupport/status/497365165893902336
				// https://twitter.com/TheZerotiger/status/497356712668852224/photo/1

				// ReflectionHelper.setPrivateValue(ThreadDownloadImageData.class,
				// abstractClientPlayer.getTextureCape(), bo,
				// new String[]{"bufferedImage", "field_110560_d"});
				//
				// ReflectionHelper.setPrivateValue(ThreadDownloadImageData.class,
				// abstractClientPlayer.getTextureCape(), false,
				// new String[]{"textureUploaded", "field_110559_g"});
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
				TEST_GRAPHICS.drawImage(new ImageIcon(new URL(cloakURL)).getImage(), 0, 0, null);
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
