package mhfc.heltrato.common.block.container;

import java.util.List;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.core.registry.MHFCRegRenderID;
import mhfc.heltrato.common.tile.TileArmorStandBase;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockArmorStandBase extends BlockContainer {

	public BlockArmorStandBase() {
		super(Material.iron);
		
		setBlockBounds(1F/16F*-0.05f, 0, 1F/16F*-0.05f, 1F-1F/16F*-0.05f, 1F-1F/16F*14F, 1F-1F/16F*-0.05f);
		setBlockName("armorstandbase");
		setHardness(1.3F);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	

	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileArmorStandBase();
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
