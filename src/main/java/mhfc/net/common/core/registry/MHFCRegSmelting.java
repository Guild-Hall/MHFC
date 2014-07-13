package mhfc.net.common.core.registry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCRegSmelting {

	public static void init() {
		GameRegistry.addSmelting(Items.flint, new ItemStack(
				MHFCRegItem.MHFCItemWyverniaDust, 2), 1.0F);
	}

}
