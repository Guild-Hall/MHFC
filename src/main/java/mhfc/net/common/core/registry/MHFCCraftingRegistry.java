package mhfc.net.common.core.registry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MHFCCraftingRegistry {

	public static void init() {
		GameRegistry.addRecipe(
				new ItemStack(MHFCBlockRegistry.getRegistry().mhfcblocklosgable, 1),
				new Object[] { "XXX", "X X", "XXX", Character.valueOf('X'),
						MHFCItemRegistry.getRegistry().wyverniaDust });
		GameRegistry.addRecipe(
				new ItemStack(MHFCBlockRegistry.getRegistry().mhfcblockhunterbench, 1),
				new Object[] { "XAX", "SXS", "ASA", Character.valueOf('X'), Blocks.CRAFTING_TABLE,
						Character.valueOf('A'), Blocks.IRON_BLOCK, Character.valueOf('S'), Items.BRICK });
		GameRegistry.addRecipe(
				new ItemStack(MHFCItemRegistry.getRegistry().bombMaterial, 1),
				new Object[] { "XXX", "XAX", "XXX", Character.valueOf('X'), Items.LEATHER, Character.valueOf('A'),
						Items.GUNPOWDER });
	}
}
