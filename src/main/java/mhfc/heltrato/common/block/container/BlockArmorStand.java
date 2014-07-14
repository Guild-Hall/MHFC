package mhfc.heltrato.common.block.container;

import java.util.List;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.tile.TileArmorStand;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockArmorStand extends BlockContainer {

	public BlockArmorStand() {
		super(Material.iron);
		setBlockBounds(1F/16F*1f, 0, 1F/16F*1f, 1F-1F/16F*1f, 1F-1F/16F*-10F, 1F-1F/16F*1f);
		setBlockName("armorstand");
		setHardness(1.4F);
		setCreativeTab(MHFCMain.mhfctabs);
		
	}

	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileArmorStand();
	}
	
	
	public int getRenderType(){
		return -1;
	}
	
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
