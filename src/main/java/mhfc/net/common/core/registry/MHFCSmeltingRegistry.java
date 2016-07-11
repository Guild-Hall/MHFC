package mhfc.net.common.core.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MHFCSmeltingRegistry {

	public static void init() {
		GameRegistry
				.addSmelting(Items.flint, new ItemStack(MHFCItemRegistry.getRegistry().wyverniaDust, 2), 1.0F);
	}

}
