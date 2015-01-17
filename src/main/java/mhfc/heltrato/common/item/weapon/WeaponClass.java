package mhfc.heltrato.common.item.weapon;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class WeaponClass extends ItemSword {

	public String des1, des2, des3, weaponicon, unlocalName; // <--- Shorten the handles
	public float damage, damageincrease;
	public Random rand;
	
	public WeaponClass(ToolMaterial getType) {
		super(getType);
		setUnlocalizedName(unlocalName);
		setFull3D();
		rand = new Random();
	}
	
	
	/* 
	 *  An order setup for easy methods for future uses of MHFC Weapons.
	 *  Easy to config to setup with this short methods.
	 *  Define the weapon components and much further stable the ticks.
	 * 
	 * 
	 * */
	
	
	public void getWeaponSetupComponent(String first, String second, String third,String textureloc, String unlocalizedName){
		des1 = first;
		des2 = second;
		des3 = third;
		weaponicon = textureloc;
		unlocalName = unlocalizedName;
	}
	
	public void updateDamaging(EntityLivingBase target, EntityLivingBase player, EntityLivingBase prior, float damageincrease2){
		damage = 0.0F;
		damageincrease = damageincrease2;
		if(target == prior){
			damage = damage + damageincrease;
		}
		DamageSource dmgSource = DamageSource.causePlayerDamage((EntityPlayer) player);
		target.attackEntityFrom(dmgSource, damage);
		
	}
	
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase ent, EntityLivingBase player)  {
		updateDamaging(ent,player, ent, damageincrease);
		// registers the default damage here 
				return true;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public void registerIcons(IIconRegister par1IconRegister){
		itemIcon = par1IconRegister.registerIcon(weaponicon);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		par3List.add(des1);
		par3List.add(des2);
		par3List.add(des3);
	}

}
