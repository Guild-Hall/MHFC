package mhfc.net.client.core.registry;

import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.IItemColored;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;

public class MHFCItemRenderRegistry {
	public static void init() {
		registerItemColors();
	}

	private static void registerItemColors() {
		MHFCItemRegistry registry = MHFCItemRegistry.getRegistry();

		registerColoredItem(registry.deviljhodrops);
		registerColoredItem(registry.kirindrops);
		registerColoredItem(registry.rathalosdrops);
		registerColoredItem(registry.remobradrops);
		registerColoredItem(registry.tigrexdrops);

		registerColoredItem(registry.firestone);
		registerColoredItem(registry.flashBomb);
		registerColoredItem(registry.kirinbuff);
		registerColoredItem(registry.meat);
		registerColoredItem(registry.nutrients);
		registerColoredItem(registry.MHFCItemFrontierSpawner);
		registerColoredItem(registry.wyvernCoin);
	}

	private static final IItemColor ITEM_COLORED_COLOR = new IItemColor() {
		@SuppressWarnings("unused")
		private static final int NO_LAMBDA = 0; // Can't use lambdas with obfuscated classes...

		@Override
		public int getColorFromItemstack(ItemStack stack, int texIndex) {
			return IItemColored.class.cast(stack.getItem()).getColorFromItemStack(stack, texIndex);
		}
	};

	private static <T extends Item & IItemColored> void registerColoredItem(T item) {
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(ITEM_COLORED_COLOR, item);
	}

}
