package mhfc.net.common.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.potion.PotionFlashed;
import mhfc.net.common.potion.PotionKirinBless;
import mhfc.net.common.potion.PotionPainted;
import mhfc.net.common.potion.PotionParalyze;
import mhfc.net.common.util.services.IServiceKey;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MHFCPotionRegistry {
	public static void staticInit() {}

	private static final IServiceKey<MHFCPotionRegistry> serviceAccess = RegistryWrapper
			.registerService("potion registry", MHFCPotionRegistry::new, MHFCMain.initPhase);

	public final Potion stun;
	public final Potion flashed;
	public final Potion kirin_blessing;
	public final Potion painted;

	private MHFCPotionRegistry() {
		kirin_blessing = registerPotion("kirin_blessing", new PotionKirinBless());
		painted = registerPotion("painted", new PotionPainted(ItemColor.PINK.getRGB(), true));
		stun = registerPotion("stunned", new PotionParalyze());
		flashed = registerPotion("flashed", new PotionFlashed());

		MHFCMain.logger().info("Potions registered");
	}

	private Potion registerPotion(String name, Potion potion) {
		return GameRegistry.register(potion.setRegistryName(name));
	}

	public static MHFCPotionRegistry getRegistry() {
		return serviceAccess.getService();
	}
}
