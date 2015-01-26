package mhfc.heltrato.common.item.weapon.huntinghorn;

import java.util.List;

import mhfc.heltrato.common.item.weapon.type.HuntingHornClass;
import mhfc.heltrato.common.util.Cooldown;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class WeaponHHIvoryHorn extends HuntingHornClass {

	public WeaponHHIvoryHorn(ToolMaterial getType) {
		super(getType, 2000);
		setUnlocalizedName(hhlocal + 2);
		getWeaponDescription("No Element",1);
		elementalType(false, false);
		enableCooldownDisplay = true;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack iStack, World world,
			EntityPlayer player) {
		weapon.onItemRightClick(iStack, world, player);
		if (Cooldown.canUse(iStack, cooldown)) {
			@SuppressWarnings("unchecked")
			List<Entity> list = player.worldObj	.getEntitiesWithinAABBExcludingEntity(player.getLastAttacker(),	player.boundingBox.expand(12D, 8D, 12D));
			for (Entity entity : list) {
				if(entity != null){
				if (entity instanceof EntityPlayer || entity instanceof EntityVillager)
				((EntityLivingBase) entity)	.heal(8F);
			}
			}
		}
		// player.motionY++;
		return iStack;
	}
}
