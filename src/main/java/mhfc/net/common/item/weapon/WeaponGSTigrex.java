package mhfc.net.common.item.weapon;

import java.util.List;
import java.util.Random;

import mhfc.net.common.entity.mob.EntityKirin;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.item.weapon.type.LethalClass;
import mhfc.net.common.item.weapon.type.SemiLethalClass;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class WeaponGSTigrex extends SemiLethalClass{
	
	private float weaponDamage;

	public WeaponGSTigrex(ToolMaterial getType) {
		super(getType);
		setUnlocalizedName("greatsword_2");
		setFull3D();
		weaponDamage = getType.getDamageVsEntity() - 4;
	}
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4){
		par3List.add("Greatsword Class");
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
		Random rand = new Random();
		float damage = 0.0f;
		if(ent instanceof EntityKirin){
			damage = weaponDamage + rand.nextInt(9);
		}
		
		DamageSource dmgSource = DamageSource.causePlayerDamage((EntityPlayer) player);
        ent.attackEntityFrom(dmgSource, damage);
        
		return true;
	}
	

}
