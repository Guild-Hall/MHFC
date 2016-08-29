package mhfc.net.common.core.registry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MHFCSmeltingRegistry {

	public static void init() {
		GameRegistry.addSmelting(Items.FLINT, new ItemStack(MHFCItemRegistry.getRegistry().wyverniaDust, 2), 1.0F);
	}

}
