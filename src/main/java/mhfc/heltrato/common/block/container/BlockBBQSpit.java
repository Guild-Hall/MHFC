package mhfc.heltrato.common.block.container;

import java.util.List;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.tile.TileBBQSpit;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBBQSpit extends BlockContainer{

	public BlockBBQSpit() {
		super(Material.iron);
		setBlockName("bbqspit");
		setCreativeTab(MHFCMain.mhfctabs);
	}
	

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileBBQSpit();
	}
	
	public int getRenderType(){
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	
	public boolean renderAsNormalBlock() {
        return false;
	}
	
	public void registerBlockIcons(IIconRegister par1IconRegister){
		blockIcon = par1IconRegister.registerIcon("mhfc:armorstandblock");
	}

}
