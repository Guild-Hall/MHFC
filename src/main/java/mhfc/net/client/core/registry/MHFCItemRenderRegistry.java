package mhfc.net.client.core.registry;

import mhfc.net.client.render.item.RenderItemArmorStand;
import mhfc.net.client.render.item.RenderItemArmorStandBase;
import mhfc.net.client.render.item.RenderItemBBQSpit;
import mhfc.net.client.render.item.RenderItemHunterBench;
import mhfc.net.client.render.item.RenderItemStunTrap;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class MHFCItemRenderRegistry {

	public static void init() {
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(MHFCBlockRegistry.mhfcblockhunterbench),
				new RenderItemHunterBench());
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(MHFCBlockRegistry.mhfcblockstuntrap),
				new RenderItemStunTrap());
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(MHFCBlockRegistry.mhfcblockarmorstandbase),
				new RenderItemArmorStandBase());
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(MHFCBlockRegistry.mhfcblockarmorstand),
				new RenderItemArmorStand());
		MinecraftForgeClient.registerItemRenderer(
				Item.getItemFromBlock(MHFCBlockRegistry.mhfcblockbbqspit),
				new RenderItemBBQSpit());
	}

}
