package mhfc.net.common.block.container;

import mhfc.net.MHFCMain;
import mhfc.net.common.tile.TileHunterBench;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHunterBench extends BlockContainer{

	public BlockHunterBench() {
		super(Material.rock);
		setBlockName("benchtable");
		setHardness(1.2F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	public TileEntity createNewTileEntity(World world, int var1) {
		return new TileHunterBench();
	}
	
	@Override
	public int getRenderType(){
		return -1;
	}
	
	 @Override
	   public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l){
	      return false;
	   }
	
	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	
	public void registerBlockIcons(IIconRegister par1IconRegister){
		blockIcon = par1IconRegister.registerIcon("mhfc:benchtable");
	}
	
	public boolean renderAsNormalBlock() {
        return false;
	}
	
	public boolean onBlockActivated(World var1, int var2, int var3, int var4, EntityPlayer player, int var6, float var7, float var8, float var9)
	{
	if (!player.isSneaking())
	{
	player.openGui(MHFCMain.instance, 1, var1, var2, var3, var4);
	return true;
	}
	else
	{
	return false;
	}
	}


}
