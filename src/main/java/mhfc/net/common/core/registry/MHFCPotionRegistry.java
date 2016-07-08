package mhfc.net.common.core.registry;

import org.apache.logging.log4j.Level;

import mhfc.net.MHFCMain;
import mhfc.net.common.helper.MHFCReflectionHelper;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.potion.PotionFlashed;
import mhfc.net.common.potion.PotionKirinBless;
import mhfc.net.common.potion.PotionPainted;
import mhfc.net.common.potion.PotionParalyze;
import net.minecraft.potion.Potion;

public class MHFCPotionRegistry {
	private static final int MAXPOTIONS = 8; // REMEMBER TO INCREASE THIS
	private static int originalSize;
	private static int offset = 0;

	public static Potion stun;
	public static Potion flashed;
	public static Potion kirin_blessing;
	public static Potion painted;

	public static void init() {
		originalSize = extendPotionsArray(MAXPOTIONS);

		kirin_blessing = new PotionKirinBless(getNextID(), false, 591932);
		painted = new PotionPainted(getNextID(), true, ItemColor.PINK.getRGB(), true);
		stun = new PotionParalyze(getNextID(), true, 999999);
		flashed = new PotionFlashed(getNextID());
	}

	private static int extendPotionsArray(int size) {
		int oldSize = Potion.potionTypes.length;
		Potion[] newPotions = new Potion[oldSize + size];

		System.arraycopy(Potion.potionTypes, 0, newPotions, 0, oldSize);

		MHFCReflectionHelper.setPrivateFinalValue(Potion.class, null, newPotions, "potionTypes", "field_76425_a");
		return oldSize;
	}

	private static int getNextID() {
		if (offset < MAXPOTIONS) {
			return originalSize + offset++;
		}
		throw MHFCMain.logger().throwing(Level.DEBUG, new IllegalStateException("Trying to register too many potions"));
	}
}
