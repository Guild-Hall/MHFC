package mhfc.net.common.core.registry;

import mhfc.net.common.crafting.MHFCCraftingManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MHFCHunterCraftingRegistry {

	public static void init() {
		/**
		 * Samples for Shaped Recipe & Shapeless addShapedRecipe(new ItemStack(Item.bow, 1), new Object[] {" II", "I S",
		 * "I S", "I S", " II", 'I', Item.stick, 'S', Item.silk}); addShapedRecipe(new ItemStack(Block.anvil, 1), new
		 * Object[] {"III", " i ", "iii", 'I', Block.blockIron, 'i', Item.ingotIron});
		 */

		// x.addShapedRecipe(new ItemStack(z.mhfcitemkirinhelm, 1), new Object[]
		// {} );
		MHFCCraftingManager man = MHFCCraftingManager.getInstance();
		MHFCItemRegistry itemRegistry = MHFCItemRegistry.getRegistry();

		man.addShapedRecipe(
				new ItemStack(itemRegistry.trapTool, 1),
				new Object[] { "   ", "   ", "XXX", "XXA", "AXA", 'X', itemRegistry.bombMaterial, 'A',
						Items.GUNPOWDER });
		// Blocks
		man.addShapedRecipe(
				new ItemStack(MHFCBlockRegistry.getRegistry().mhfcblockstuntrap, 1),
				new Object[] { "ADX", "DXF", "XXA", "FXD", "XXX", 'X', itemRegistry.trapTool, 'A', Items.REDSTONE, 'D',
						Items.IRON_INGOT, 'F', itemRegistry.bombMaterial });

	}

}
