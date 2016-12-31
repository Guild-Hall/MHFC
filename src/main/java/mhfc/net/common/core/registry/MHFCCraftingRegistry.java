package mhfc.net.common.core.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MHFCCraftingRegistry {

	public static void init() {
	
		GameRegistry.addRecipe(
				new ItemStack(MHFCBlockRegistry.getRegistry().mhfcblockhunterbench, 1),
				new Object[] { "XAX", "SXS", "ASA", Character.valueOf('X'), Blocks.crafting_table,
						Character.valueOf('A'), Blocks.iron_block, Character.valueOf('S'), Items.brick });
		GameRegistry.addRecipe(
				new ItemStack(MHFCItemRegistry.getRegistry().bombMaterial, 1),
				new Object[] { "XXX", "XAX", "XXX", Character.valueOf('X'), Items.leather, Character.valueOf('A'),
						Items.gunpowder });
	}
}
