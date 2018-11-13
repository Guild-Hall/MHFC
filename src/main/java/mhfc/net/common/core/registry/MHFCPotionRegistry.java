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
import net.minecraftforge.registries.GameData;

public class MHFCPotionRegistry {
	public static void staticInit() {}

	private static final IServiceKey<MHFCPotionRegistry> serviceAccess = RegistryWrapper
			.registerService("potion registry", MHFCPotionRegistry::new, MHFCMain.initPhase);

	public final Potion stun;
	public final Potion flashed;
	public final Potion kirin_blessing;
	public final Potion painted;

	public static final String potion_paralyze_uuid = "07c8c0a3-5bd7-4ca3-a984-bb9bdacf4496";

	public static final String potion_kirinbless_uuid = "97bd1ec2-4a75-43cc-b81b-e281503c2ffe";

	public static final String potion_attackup_uuid = "6a80c830-745d-4edd-8a17-e580f813bf20";

	private MHFCPotionRegistry() {
		kirin_blessing = registerPotion("kirin_blessing", new PotionKirinBless());
		painted = registerPotion("painted", new PotionPainted(ItemColor.PINK.getRGB(), true));
		stun = registerPotion("stunned", new PotionParalyze());
		flashed = registerPotion("flashed", new PotionFlashed());

		MHFCMain.logger().info("Potions registered");
	}

	private Potion registerPotion(String name, Potion potion) {
		return GameData.register_impl(potion.setRegistryName(name));
	}

	public static MHFCPotionRegistry getRegistry() {
		return serviceAccess.getService();
	}
}
