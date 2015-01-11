package mhfc.heltrato.common.core.registry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCRegCrafting {
	
	private static GameRegistry craft;
	
	
	public static void init(){
		craft.addRecipe(new ItemStack(MHFCRegBlock.mhfcblocklosgable, 1), new Object[] { "XXX", "X X", "XXX", Character.valueOf('X'), MHFCRegItem.MHFCItemWyverniaDust});
		craft.addRecipe(new ItemStack(MHFCRegBlock.mhfcblockhunterbench, 1), new Object[] { "XAX", "SXS", "ASA", Character.valueOf('X'), Blocks.crafting_table, Character.valueOf('A'), Blocks.iron_block, Character.valueOf('S'), Items.brick});
		craft.addRecipe(new ItemStack(MHFCRegItem.MHFCItemBombMaterial, 1), new Object[] { "XXX", "XAX", "XXX", Character.valueOf('X'), Items.leather , Character.valueOf('A'), Items.gunpowder }); 
	}
	

}
