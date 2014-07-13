package mhfc.net.common.core.registry;

import mhfc.net.client.render.item.RenderItemArmorStand;
import mhfc.net.client.render.item.RenderItemArmorStandBase;
import mhfc.net.client.render.item.RenderItemBBQSpit;
import mhfc.net.client.render.item.RenderItemHunterBench;
import mhfc.net.client.render.item.RenderItemStunTrap;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class MHFCRegRenderItem {

	public static void render() {
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(MHFCRegBlock.mhfcblockhunterbench),
				new RenderItemHunterBench());
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(MHFCRegBlock.mhfcblockstuntrap),
				new RenderItemStunTrap());
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(MHFCRegBlock.mhfcblockarmorstandbase),
				new RenderItemArmorStandBase());
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(MHFCRegBlock.mhfcblockarmorstand),
				new RenderItemArmorStand());
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(MHFCRegBlock.mhfcblockbbqspit),
				new RenderItemBBQSpit());
	}

}
