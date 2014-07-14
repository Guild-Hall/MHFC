package mhfc.heltrato.common.item.weapon;

import java.util.List;

import mhfc.heltrato.common.entity.mob.EntityKirin;
import mhfc.heltrato.common.entity.mob.EntityTigrex;
import mhfc.heltrato.common.entity.type.EntityWyvernHostile;
import mhfc.heltrato.common.helper.MHFCWeaponMaterialHelper;
import mhfc.heltrato.common.item.weapon.type.SemiLethalClass;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class WeaponGSBone extends SemiLethalClass{

	private float weaponDamage;
	
	public WeaponGSBone(ToolMaterial getType) {
		super(getType);
		setUnlocalizedName("greatsword_1");
		weaponDamage = getType.getDamageVsEntity() - 4;
		setFull3D();
	}
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4){
		par3List.add("\u00a79No-Element");
		par3List.add("\u00a72Semi Lethal Damage");
	}
	
	public boolean isFull3D(){
        return true;
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
		if(ent instanceof EntityKirin){
			damage = 16;
		}
		
		DamageSource dmgSource = DamageSource.causePlayerDamage((EntityPlayer) player);
        ent.attackEntityFrom(dmgSource, damage);
        
		return true;
	}
	

}
