package mhfc.net.common.item.weapon;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import mhfc.net.common.entity.mob.EntityKirin;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.item.weapon.type.SemiLethalClass;

public class WeaponGSKirinThunderSword extends SemiLethalClass {
	
	private float weaponDamage;
	
	public WeaponGSKirinThunderSword(ToolMaterial getType) {
		super(getType);
		setUnlocalizedName("greatsword_3");
		weaponDamage = getType.getDamageVsEntity() - 4;
	}
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4){
		par3List.add("\u00a79Thunder Element");
		par3List.add("\u00a72Semi Lethal Damage");
		
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
		itemIcon = par1IconRegister.registerIcon("mhfc:greatsword");
    }
	
	
    public float getDamageVsEntity(Entity entity)
    {
    	
    	return weaponDamage;
    }

	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase ent, EntityLivingBase player){
		
		float damage = 0.0f;
		if(ent instanceof EntityTigrex) {
			damage = 57f;
		}
		
		DamageSource dmgSource = DamageSource.causePlayerDamage((EntityPlayer) player);
        ent.attackEntityFrom(dmgSource, damage);
        
		return true;
	}
	
	
	

}
