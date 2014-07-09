package mhfc.net.common.block;

import java.util.List;
import java.util.Random;

import javax.swing.Icon;

import mhfc.net.MHFCMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
//TODO   
public class BlockIngots extends Block 
{
	private static final String[] blockofTypes = new String[] {"blockof0", "blockof1", "blockof2"};
	private IIcon[] textures;
	
	public BlockIngots() {
		super(Material.rock);
		setCreativeTab(MHFCMain.mhfctabs);
		setHardness(0f + getHardness());
		setHarvestLevel("pickaxe", 3);
		
	}
	
	public int quantityDropped(Random random){
		return 1;
	}
	
	public void registerBlockIcons(IIconRegister par1IconRegister){
		textures = new IIcon[blockofTypes.length];
		for(int i = 0;i < blockofTypes.length; i++){
			textures[i] = par1IconRegister.registerIcon("mhfc:"+ blockofTypes[i]);
		}
	}
	
	public IIcon getIcon(int side, int meta)
	{
		if (meta < 0 || meta >= textures.length) {
			meta = 0;
		}

		return textures[meta];
	}
	
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) 
	{
		for (int i = 0; i < blockofTypes.length; ++i) 
		{
			list.add(new ItemStack(block, 1, i));
		}
	}

	@Override
	
	public int damageDropped(int meta)
	{
		return meta;
	}
	//TODO    
	public float getHardness(){
		int meta = blockofTypes.length;
		if(meta == 0){
			return 1.1f;
		}
		if(meta == 1){
			return 1.6f;
		}
		if(meta == 2){
			return 1.9f;
		}
		return 1f;
	}
	
	

}
