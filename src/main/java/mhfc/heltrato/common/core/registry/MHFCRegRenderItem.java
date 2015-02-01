package mhfc.heltrato.common.core.registry;

import mhfc.heltrato.client.render.item.RenderItemBBQSpit;
import mhfc.heltrato.client.render.item.RenderItemHunterBench;
import mhfc.heltrato.client.render.item.RenderItemStunTrap;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class MHFCRegRenderItem {
	
	private static MinecraftForgeClient get;
	
	public static void render(){
		get.registerItemRenderer(Item.getItemFromBlock(MHFCRegBlock.mhfcblockhunterbench), new RenderItemHunterBench());
		get.registerItemRenderer(Item.getItemFromBlock(MHFCRegBlock.mhfcblockstuntrap), new RenderItemStunTrap());
		get.registerItemRenderer(Item.getItemFromBlock(MHFCRegBlock.mhfcblockbbqspit), new RenderItemBBQSpit());
	}

}
