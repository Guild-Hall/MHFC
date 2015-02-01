package mhfc.net.common.core.registry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCCraftingRegistry {

	public static void init() {
		GameRegistry.addRecipe(new ItemStack(
				MHFCBlockRegistry.mhfcblocklosgable, 1), new Object[]{"XXX",
				"X X", "XXX", Character.valueOf('X'),
				MHFCItemRegistry.MHFCItemWyverniaDust});
		GameRegistry
				.addRecipe(
						new ItemStack(MHFCBlockRegistry.mhfcblockhunterbench, 1),
						new Object[]{"XAX", "SXS", "ASA",
								Character.valueOf('X'), Blocks.crafting_table,
								Character.valueOf('A'), Blocks.iron_block,
								Character.valueOf('S'), Items.brick});
		GameRegistry.addRecipe(new ItemStack(MHFCItemRegistry.MHFCItemBombMaterial,
				1), new Object[]{"XXX", "XAX", "XXX", Character.valueOf('X'),
				Items.leather, Character.valueOf('A'), Items.gunpowder});
	}
}
