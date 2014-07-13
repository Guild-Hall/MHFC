package mhfc.net.common.core.registry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCRegCrafting {

	public static void init() {
		GameRegistry.addRecipe(
				new ItemStack(MHFCRegBlock.mhfcblocklosgable, 1), new Object[]{
						"XXX", "X X", "XXX", Character.valueOf('X'),
						MHFCRegItem.MHFCItemWyverniaDust});
		GameRegistry.addRecipe(new ItemStack(MHFCRegBlock.mhfcblockhunterbench,
				1), new Object[]{"XAX", "SXS", "ASA", Character.valueOf('X'),
				Blocks.crafting_table, Character.valueOf('A'),
				Blocks.iron_block, Character.valueOf('S'), Items.brick});
		GameRegistry.addRecipe(new ItemStack(MHFCRegItem.MHFCItemBombMaterial,
				1), new Object[]{"XXX", "XAX", "XXX", Character.valueOf('X'),
				Items.leather, Character.valueOf('A'), Items.gunpowder});
	}

}
