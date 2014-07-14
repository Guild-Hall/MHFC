package mhfc.heltrato.common.core.registry;

import mhfc.heltrato.client.render.item.RenderItemArmorStand;
import mhfc.heltrato.client.render.item.RenderItemArmorStandBase;
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
		get.registerItemRenderer(Item.getItemFromBlock(MHFCRegBlock.mhfcblockarmorstandbase), new RenderItemArmorStandBase());
		get.registerItemRenderer(Item.getItemFromBlock(MHFCRegBlock.mhfcblockarmorstand), new RenderItemArmorStand());
		get.registerItemRenderer(Item.getItemFromBlock(MHFCRegBlock.mhfcblockbbqspit), new RenderItemBBQSpit());
	}

}
