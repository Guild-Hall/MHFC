package mhfc.net.common.core.registry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCRegSmelting 
{
	private static GameRegistry smelt;
	
	public static void init(){
		smelt.addSmelting(Items.flint, new ItemStack(MHFCRegItem.MHFCItemWyverniaDust, 2), 1.0F);
	}

}
