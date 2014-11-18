package mhfc.net.common.core.registry;

import mhfc.net.common.crafting.MHFCCraftingManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MHFCRegCraftingHunter {

	public static MHFCCraftingManager x;
	public static MHFCRegItem z;
	public static MHFCRegBlock y;

	public static void craftAll() {
		/**
		 * Samples for Shaped Recipe & Shapeless addShapedRecipe(new
		 * ItemStack(Item.bow, 1), new Object[] {" II", "I S", "I S", "I S",
		 * " II", 'I', Item.stick, 'S', Item.silk}); addShapedRecipe(new
		 * ItemStack(Block.anvil, 1), new Object[] {"III", " i ", "iii", 'I',
		 * Block.blockIron, 'i', Item.ingotIron});
		 */

		// x.addShapedRecipe(new ItemStack(z.mhfcitemkirinhelm, 1), new Object[]
		// {} );
		MHFCCraftingManager man = MHFCCraftingManager.getInstance();

		man.addShapedRecipe(new ItemStack(MHFCRegItem.MHFCItemHTigrex, 1),
				new Object[]{"XXX", "XTX", "TXT", " S ", " S ", 'X',
						MHFCRegItem.MHFCItemTigrexShell, 'T',
						MHFCRegItem.MHFCItemTigrexScale, 'S', Items.stick});
		man.addShapedRecipe(new ItemStack(MHFCRegItem.mhfcitemgsbone, 1),
				new Object[]{" X ", "TXT", "TXT", " S ", " S ", 'X',
						Items.iron_ingot, 'T', Items.bone, 'S', Items.stick});
		man.addShapedRecipe(new ItemStack(MHFCRegItem.MHFCItemTrapTool, 1),
				new Object[]{"   ", "   ", "XXX", "XXA", "AXA", 'X',
						MHFCRegItem.MHFCItemBombMaterial, 'A', Items.gunpowder});
		// Blocks
		man.addShapedRecipe(
				new ItemStack(MHFCRegBlock.mhfcblockstuntrap, 1),
				new Object[]{"ADX", "DXF", "XXA", "FXD", "XXX", 'X',
						MHFCRegItem.MHFCItemTrapTool, 'A', Items.redstone, 'D',
						Items.iron_ingot, 'F', MHFCRegItem.MHFCItemBombMaterial});

	}

}
