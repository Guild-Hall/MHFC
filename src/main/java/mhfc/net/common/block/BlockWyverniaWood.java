package mhfc.net.common.block;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWyverniaWood extends Block {
	public static enum WyverniaLogSubType implements SubTypedItem.SubTypeEnum<Block> {
		CALFER(MHFCReference.block_log_calfer_name, MHFCReference.block_log_calfer_tex), //
		DIREWOOD(MHFCReference.block_log_direwood_name, MHFCReference.block_log_direwood_tex), //
		GRAND_IFOLIA(MHFCReference.block_log_grandifolia_name, MHFCReference.block_log_grandifolia_tex), //
		MAVEN(MHFCReference.block_log_maven_name, MHFCReference.block_log_maven_tex), //
		NEGUNDO(MHFCReference.block_log_negundo_name, MHFCReference.block_log_negundo_tex), //
		PALMER(MHFCReference.block_log_palmer_name, MHFCReference.block_log_palmer_tex), //
		RADEL(MHFCReference.block_log_radel_name, MHFCReference.block_log_radel_tex), //
		SANDY(MHFCReference.block_log_sandy_name, MHFCReference.block_log_sandy_tex), //
		TILIA(MHFCReference.block_log_tilia_name, MHFCReference.block_log_tilia_tex);

		public final String name;
		public final String texture;

		private WyverniaLogSubType(String name, String tex) {
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
			return MHFCBlockRegistry.getRegistry().mhfcblockwood;
		}
	}

	private final SubTypedItem<Block, WyverniaLogSubType> tree_sideTrait;
	private final SubTypedItem<Block, WyverniaLogSubType> tree_topTrait;

	public BlockWyverniaWood() {
		super(Material.wood);
		tree_sideTrait = new SubTypedItem<>(WyverniaLogSubType.class, texPath -> texPath + "side");
		tree_topTrait = new SubTypedItem<>(WyverniaLogSubType.class, texPath -> texPath + "top");
		setBlockName(MHFCReference.block_wyvernialog_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item block, CreativeTabs creativetabs, List list) {
		tree_sideTrait.getSubItems(block, list);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		tree_sideTrait.registerIcons(iconRegister);
		tree_topTrait.registerIcons(iconRegister);

	}

	@Override
	public IIcon getIcon(int side, int damage) {
		return (side == 1) || (side == 0)
				? this.tree_topTrait.getIcons()[damage]
				: this.tree_sideTrait.getIcons()[damage];
	}

}
