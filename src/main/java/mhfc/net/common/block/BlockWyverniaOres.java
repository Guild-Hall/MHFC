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

public class BlockWyverniaOres extends Block {

	public static enum WyverniaOreSubType implements SubTypedItem.SubTypeEnum<Block> {
		ARMOR_SPHERE_ORE(MHFCReference.block_orearmorsphere_name, MHFCReference.block_orearmorsphere_tex), //
		ARMOR_SPHERE_PLUS_ORE(MHFCReference.block_orearmorsphereplus_name, MHFCReference.block_orearmorsphereplus_tex), //
		CARBALITE_ORE(MHFCReference.block_orecarbalite_name, MHFCReference.block_orecarbalite_tex), //
		DRAGONITE_ORE(MHFCReference.block_oredragonite_name, MHFCReference.block_oredragonite_tex), //
		ELTALITE_ORE(MHFCReference.block_oreeltalite_name, MHFCReference.block_oreeltalite_tex), //
		MACHALITE_ORE(MHFCReference.block_oremachalite_name, MHFCReference.block_oremachalite_tex),
		FURUKURAITO_ORE(MHFCReference.block_orefurukuraito_name, MHFCReference.block_orefurukuraito_tex),;

		public final String name;
		public final String texture;

		private WyverniaOreSubType(String name, String tex) {
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
			return MHFCBlockRegistry.getRegistry().mhfcblockore;
		}

	}

	private final SubTypedItem<Block, WyverniaOreSubType> blockTrait;

	public BlockWyverniaOres() {
		super(Material.rock);
		blockTrait = new SubTypedItem<>(WyverniaOreSubType.class);
		setBlockName(MHFCReference.block_ores_basename);
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
