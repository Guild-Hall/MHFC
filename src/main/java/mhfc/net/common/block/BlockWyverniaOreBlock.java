package mhfc.net.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class BlockWyverniaOreBlock extends Block {
	public static enum WyverniaOreBlockSubType implements SubTypedItem.SubTypeEnum<Block> {
		MACHALITE(MHFCReference.block_machalite_name, MHFCReference.block_machalite_tex), //
		CARBALITE(MHFCReference.block_carbalite_name, MHFCReference.block_carbalite_tex), //
		ELTALITE(MHFCReference.block_eltalite_name, MHFCReference.block_eltalite_tex), //
		DRAGONITE(MHFCReference.block_dragonite_name, MHFCReference.block_dragonite_tex);

		public final String name;
		public final String texture;

		private WyverniaOreBlockSubType(String name, String tex) {
			this.name = name;
			this.texture = tex;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getTexPath() {
			return texture;
		}

		@Override
		public Block getBaseItem() {
			return MHFCBlockRegistry.getRegistry().mhfcblockoreblocks;
		}
	}

	private final SubTypedItem<Block, WyverniaOreBlockSubType> blockTrait;

	public BlockWyverniaOreBlock() {
		super(Material.rock);
		blockTrait = new SubTypedItem<>(WyverniaOreBlockSubType.class);
		setBlockName(MHFCReference.block_oreblock_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void registerBlockIcons(IIconRegister registry) {
		blockTrait.registerIcons(registry);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		blockTrait.getSubItems(item, list);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return blockTrait.getIcons()[meta];
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}
}
