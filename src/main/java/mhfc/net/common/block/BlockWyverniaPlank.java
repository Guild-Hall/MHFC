package mhfc.net.common.block;

import java.util.List;

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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWyverniaPlank extends Block {
	public static enum WyverniaPlankSubType
			implements
				SubTypedItem.SubTypeEnum<Block> {
		CALFER(MHFCReference.block_calfer_name, MHFCReference.block_calfer_tex), //
		DIREWOOD(MHFCReference.block_direwood_name,
				MHFCReference.block_direwood_tex), //
		GRAND_IFOLIA(MHFCReference.block_grandifolia_name,
				MHFCReference.block_grandifolia_tex), //
		MAVEN(MHFCReference.block_maven_name, MHFCReference.block_maven_tex), //
		NEGUNDO(MHFCReference.block_negundo_name,
				MHFCReference.block_negundo_tex), //
		PALMER(MHFCReference.block_palmer_name, MHFCReference.block_palmer_tex), //
		RADEL(MHFCReference.block_radel_name, MHFCReference.block_radel_tex), //
		SANDY(MHFCReference.block_sandy_name, MHFCReference.block_sandy_tex), //
		TILIA(MHFCReference.block_tilia_name, MHFCReference.block_tilia_tex);

		public final String name;
		public final String texture;
		private WyverniaPlankSubType(String name, String tex) {
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
			return MHFCBlockRegistry.mhfcblockplanks;
		}
	}

	private final SubTypedItem<Block, WyverniaPlankSubType> blockTrait;

	public BlockWyverniaPlank() {
		super(Material.wood);
		blockTrait = new SubTypedItem<>(WyverniaPlankSubType.class);
		setBlockName(MHFCReference.block_wyverniaplank_basename);
		setHardness(0.6f);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void registerBlockIcons(IIconRegister registry) {
		blockTrait.registerIcons(registry);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab,
			@SuppressWarnings("rawtypes") List list) {
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
