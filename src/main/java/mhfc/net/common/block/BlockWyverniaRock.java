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

public class BlockWyverniaRock extends Block {
	public static enum WyverniaRockSubType implements SubTypedItem.SubTypeEnum<Block> {
		AUVEL(MHFCReference.block_auvel_name, MHFCReference.block_auvel_tex), //
		CRADLE(MHFCReference.block_cradle_name, MHFCReference.block_cradle_tex), //
		TACREN(MHFCReference.block_tacren_name, MHFCReference.block_tacren_tex);

		public final String name;
		public final String texture;

		private WyverniaRockSubType(String name, String tex) {
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
			return MHFCBlockRegistry.getRegistry().mhfcblockrocks;
		}
	}

	private final SubTypedItem<Block, WyverniaRockSubType> blockTrait;

	public BlockWyverniaRock() {
		super(Material.rock);
		blockTrait = new SubTypedItem<>(WyverniaRockSubType.class);
		setBlockName(MHFCReference.block_wyverniarock_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setHardness(1.1f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister regIcon) {
		blockTrait.registerIcons(regIcon);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item block, CreativeTabs creativetabs, List list) {
		blockTrait.getSubItems(block, list);
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
