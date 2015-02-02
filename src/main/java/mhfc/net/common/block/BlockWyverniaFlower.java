package mhfc.net.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import mhfc.net.MHFCMain;
import mhfc.net.common.block.environment.BlockWyverniaDecor;
import mhfc.net.common.core.registry.MHFCRegBlock;
import mhfc.net.common.util.lib.MHFCReference;

public class BlockWyverniaFlower extends BlockWyverniaDecor{

	@SideOnly(Side.CLIENT)
	private IIcon[] texture;
	
	public BlockWyverniaFlower() {
		super(Material.plants);
		setBlockName("wyverniaflower");
		setCreativeTab(MHFCMain.mhfctabs);
		// TODO Might have some tweaks in ticking . might have some bugs
		setHardness(0.0f);
		setStepSound(Block.soundTypeGrass);
		setTickRandomly(true);
		float f = 0.2F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
	}
	
	
	//TODO might have bugs.
	 public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		 return null;
	 }
	 @Override
	 public int getRenderType() {
		 return 1; // Magic number.
	 }
	 @Override
	 public boolean isOpaqueCube() {
	        return false;
	 }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister regIcon){
		texture = new IIcon[MHFCReference.flowerBlocks.length];
		
		for(int i = 0; i < MHFCReference.flowerBlocks.length; i++){
			texture[i] = regIcon.registerIcon("mhfc:" + MHFCReference.flowerBlocks[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item block, CreativeTabs creativetabs, List list){
		for(int i = 0; i < MHFCReference.flowerBlocks.length; i++){
			list.add(new ItemStack(block, 1, i));
		}
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        if (p_149691_2_ >= this.texture.length)
        {
            p_149691_2_ = 0;
        }

        return this.texture[p_149691_2_];
    }

	
	public int damageDropped(int meta){
		return meta;
	}
	
	
	@Override
	public boolean isValidPosition(World world, int x, int y, int z, int metadata)
	{
		//TODO:					  getBlock()
		Block block = world.getBlock(x, y - 1, z);
		return block == MHFCRegBlock.mhfcblockdirt || block == MHFCRegBlock.mhfcblockgrass || block == Blocks.glass;
	}

}
