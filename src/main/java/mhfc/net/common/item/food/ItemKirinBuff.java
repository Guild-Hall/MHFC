package mhfc.net.common.item.food;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCRegPotion;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemKirinBuff extends ItemFood{

	public ItemKirinBuff(int healAmount, float modifier, boolean p_i45339_3_) {
		super(healAmount, modifier, p_i45339_3_);
		setUnlocalizedName("kirinbuff");
		setCreativeTab(MHFCMain.mhfctabs);
		setPotionEffect(Potion.resistance.id, 15, 4, 100);
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
        this.itemIcon = par1IconRegister.registerIcon("mhfc:kirinbuff");
	}
	

}
