package mhfc.net.common.core.registry;

import java.util.Arrays;

import org.apache.logging.log4j.Level;

import mhfc.net.MHFCMain;
import mhfc.net.common.helper.MHFCReflectionHelper;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.potion.PotionFlashed;
import mhfc.net.common.potion.PotionKirinBless;
import mhfc.net.common.potion.PotionPainted;
import mhfc.net.common.potion.PotionParalyze;
import mhfc.net.common.util.services.IServiceKey;
import net.minecraft.potion.Potion;

public class MHFCPotionRegistry {
	private static final int MAXPOTIONS = 8; // REMEMBER TO INCREASE THIS

	public static void staticInit() {}

	private static final IServiceKey<MHFCPotionRegistry> serviceAccess = RegistryWrapper
			.registerService("potion registry", MHFCPotionRegistry::new, MHFCMain.initPhase);

	private int originalSize;
	private int offset = 0;

	public static Potion stun;
	public final Potion flashed;
	public final Potion kirin_blessing;
	public final Potion painted;

	private MHFCPotionRegistry() {
		originalSize = extendPotionsArray(MAXPOTIONS);

		kirin_blessing = new PotionKirinBless(getNextID(), false, 591932);
		painted = new PotionPainted(getNextID(), true, ItemColor.PINK.getRGB(), true);
		stun = new PotionParalyze(getNextID(), true, 999999);
		flashed = new PotionFlashed(getNextID());

		MHFCMain.logger().info("Potions registered");
	}

	private static int extendPotionsArray(int size) {
		int oldSize = Potion.potionTypes.length;
		Potion[] newPotions = Arrays.copyOf(Potion.potionTypes, oldSize + size);

		MHFCReflectionHelper.setPrivateFinalValue(Potion.class, null, newPotions, "potionTypes", "field_76425_a");
		return oldSize;
	}

	private int getNextID() {
		if (offset < MAXPOTIONS) {
			return originalSize + offset++;
		}
		throw MHFCMain.logger().throwing(Level.DEBUG, new IllegalStateException("Trying to register too many potions"));
	}

	public static MHFCPotionRegistry getRegistry() {
		return serviceAccess.getService();
	}
}
