package mhfc.net.common.block;

import java.util.List;
import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
// TODO
public class BlockIngots extends Block {
	private static final String[] ingotIconNames = new String[]{
			MHFCReference.block_blockingot0_tex,
			MHFCReference.block_blockingot1_tex,
			MHFCReference.block_blockingot2_tex};
	private IIcon[] textures;

	public BlockIngots() {
		super(Material.rock);
		setCreativeTab(MHFCMain.mhfctabs);
		setHardness(0f + getHardness());
		setHarvestLevel("pickaxe", 3);

	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		textures = new IIcon[ingotIconNames.length];
		for (int i = 0; i < ingotIconNames.length; i++) {
			textures[i] = par1IconRegister.registerIcon(ingotIconNames[i]);
		}
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta < 0 || meta >= textures.length) {
			meta = 0;
		}

		return textures[meta];
	}

	@Override
	@SuppressWarnings("unchecked")
	public void getSubBlocks(Item block, CreativeTabs creativeTabs,
			@SuppressWarnings("rawtypes") List list) {
		for (int i = 0; i < ingotIconNames.length; ++i) {
			list.add(new ItemStack(block, 1, i));
		}
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	// TODO
	public float getHardness() {
		int meta = ingotIconNames.length;
		if (meta == 0) {
			return 1.1f;
		}
		if (meta == 1) {
			return 1.6f;
		}
		if (meta == 2) {
			return 1.9f;
		}
		return 1f;
	}

}
