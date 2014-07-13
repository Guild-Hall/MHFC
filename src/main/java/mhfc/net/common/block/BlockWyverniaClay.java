package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCRegItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class BlockWyverniaClay extends Block {

	public BlockWyverniaClay() {
		super(Material.clay);
		setBlockName("wyverniaclayblock");
		setBlockTextureName("mhfc:wyverniaclayblock");
		setHardness(0.9f);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_,
			int p_149650_3_) {
		return MHFCRegItem.mhfcitemwyverniaclay;
	}

	@Override
	public int quantityDropped(Random random) {
		return 2;
	}

	public void registerIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon("mhfc:wyverniaclayblock");
	}

}
