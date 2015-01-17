package mhfc.heltrato.common.item.food;

import java.util.List;

import mhfc.heltrato.MHFCMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemNutrients extends ItemFood {
	
	public int metaData;

	public ItemNutrients(int healamount, float modifier, boolean p_i45339_3_, int meta) {
		super(healamount, modifier, p_i45339_3_);
		metaData = meta;
		setUnlocalizedName("nutrients" + metaData);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(1);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
			if(metaData == 0) {
				par3List.add("Adds 4 health points");
				par3List.add("Duration:10 minutes");
				par3List.add("\u00a79[Only Once]");
			}
			if(metaData == 1) {
				par3List.add("Adds 8 health points for 10 minutes [Only Once]");
				par3List.add("Duration:10 minutes");
				par3List.add("\u00a79[Only Once]");
			}
	}

	
	public void registerIcons(IIconRegister par1IconRegister){
        itemIcon = par1IconRegister.registerIcon("mhfc:nutrients" + metaData);
	}
	
	@Override
	public void onFoodEaten(ItemStack stack, World world, EntityPlayer player){
		float health = player.getHealth();
		if(metaData == 0) {
			player.removePotionEffect(21);
			player.addPotionEffect(new PotionEffect(21, 12000, 1, true));
			player.setHealth(health);
		}if(metaData == 1){
			player.removePotionEffect(21);
			player.addPotionEffect(new PotionEffect(21, 12000, 3, true));
			player.setHealth(health);
		}
	}

}
