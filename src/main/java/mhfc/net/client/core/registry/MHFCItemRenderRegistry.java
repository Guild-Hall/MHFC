package mhfc.net.client.core.registry;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.IBlockVarianted;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.IItemColored;
import mhfc.net.common.item.IItemCustomModel;
import mhfc.net.common.item.IItemVarianted;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;

public class MHFCItemRenderRegistry {
	public static void staticInit() {}

	static {
		MHFCMain.preInitPhase.registerEntryCallback(e -> preInit());
		MHFCMain.initPhase.registerEntryCallback(e -> init());
	}

	private static void preInit() {
		registerItemRenders();
	}

	private static void registerItemRenders() {
		MHFCItemRegistry items = MHFCItemRegistry.getRegistry();
		MHFCBlockRegistry blocks = MHFCBlockRegistry.getRegistry();

		handleItemsPreInit(items.allItems);

		handleItemsPreInit(blocks.allItems);
		handleBlockPreInit(blocks.allBlocks);
	}

	private static void init() {
		registerItemColors();
	}

	private static void registerItemColors() {
		MHFCItemRegistry items = MHFCItemRegistry.getRegistry();
		MHFCBlockRegistry blocks = MHFCBlockRegistry.getRegistry();

		handleItemsInit(items.allItems);
		handleItemsInit(blocks.allItems);
	}

	private static void handleBlockPreInit(Collection<Block> allBlocks) {
		for (Block block : allBlocks) {
			if (block instanceof IBlockVarianted) {
				registerVariantBlock(block);
			}
		}
	}

	private static void registerVariantBlock(Block block) {
		IBlockVarianted varianted = (IBlockVarianted) block;
		ResourceLocation registration = block.getRegistryName();
		String domain = registration.getResourceDomain();
		String path = registration.getResourcePath();
		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				String variantName = varianted.getVariantName(state);
				if (variantName == null) {
					return new ModelResourceLocation(registration, this.getPropertyString(state.getProperties()));
				}
				return new ModelResourceLocation(new ResourceLocation(domain, path + "_" + variantName), "normal");
			}
		});
	}

	private static void handleItemsPreInit(Collection<Item> items) {
		for (Item item : items) {
			if (item instanceof IItemVarianted) {
				registerVariantItem(item);
			}
			if (item instanceof IItemCustomModel) {
				registerWithCustomModel(item);
			}
		}
	}

	private static void handleItemsInit(Collection<Item> items) {
		for (Item item : items) {
			if (item instanceof IItemColored) {
				registerColoredItem(item);
			}
		}
	}

	private static final ItemMeshDefinition ITEM_MESHER = new ItemMeshDefinition() {
		@SuppressWarnings("unused")
		private static final int NO_LAMBDA = 0; // Can't use lambdas with obfuscated classes...

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return IItemCustomModel.class.cast(stack.getItem()).getModelLocation(stack);
		}
	};

	private static void registerWithCustomModel(Item item) {
		IItemCustomModel modelledItem = (IItemCustomModel) item;
		Set<ModelResourceLocation> allModels = modelledItem.getPossibleValues();
		if (allModels == null || allModels.isEmpty()) {
			return;
		}
		ModelBakery.registerItemVariants(item, allModels.toArray(new ModelResourceLocation[0]));
		ModelLoader.setCustomMeshDefinition(item, ITEM_MESHER);
	}

	private static void registerVariantItem(Item item) {
		IItemVarianted subtyped = (IItemVarianted) item;
		List<String> variantNames = subtyped.getVariantNames();
		if (variantNames == null || variantNames.isEmpty()) {
			return;
		}
		MHFCMain.logger().debug("Registering " + item + " for variants " + variantNames);
		ResourceLocation itemRegistryName = item.getRegistryName();
		String domain = itemRegistryName.getResourceDomain();
		String path = itemRegistryName.getResourcePath();
		int i = 0;
		for (String variantName : variantNames) {
			int variantMeta = i++;
			ModelResourceLocation variantModelLocation = new ModelResourceLocation(
					new ResourceLocation(domain, path + "_" + variantName),
					"inventory");
			ModelLoader.setCustomModelResourceLocation(item, variantMeta, variantModelLocation);
		}
	}

	private static final IItemColor ITEM_COLORED_COLOR = new IItemColor() {
		@SuppressWarnings("unused")
		private static final int NO_LAMBDA = 0; // Can't use lambdas with obfuscated classes...

		@Override
		public int getColorFromItemstack(ItemStack stack, int texIndex) {
			return IItemColored.class.cast(stack.getItem()).getColorFromItemStack(stack, texIndex);
		}
	};

	private static <T extends Item> void registerColoredItem(T item) {
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(ITEM_COLORED_COLOR, item);
	}

}
