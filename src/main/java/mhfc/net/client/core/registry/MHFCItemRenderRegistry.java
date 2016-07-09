package mhfc.net.client.core.registry;

import mhfc.net.client.render.item.RenderItemBBQSpit;
import mhfc.net.client.render.item.RenderItemHunterBench;
import mhfc.net.client.render.item.RenderItemStunTrap;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class MHFCItemRenderRegistry {
	public static void init() {
		registerBlockRenderer(MHFCBlockRegistry.getRegistry().mhfcblockhunterbench, new RenderItemHunterBench());
		registerBlockRenderer(MHFCBlockRegistry.getRegistry().mhfcblockstuntrap, new RenderItemStunTrap());
		registerBlockRenderer(MHFCBlockRegistry.getRegistry().mhfcblockbbqspit, new RenderItemBBQSpit());
	}

	private static void registerBlockRenderer(Block block, IItemRenderer renderer) {
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(block), renderer);
	}

}
