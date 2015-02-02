package mhfc.net.common.weapon.melee.huntinghorn;

import java.util.List;

import mhfc.net.common.util.Cooldown;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class HHTigrex extends HuntingHornClass {

	public HHTigrex(ToolMaterial getType) {
		super(getType, 3600);
		setUnlocalizedName(hhlocal + 3);
		getWeaponDescription("No Element",4);
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
				if (entity instanceof EntityPlayer)
				((EntityLivingBase) entity)	.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 2000, 1));
			}
			}
		}
		// player.motionY++;
		return iStack;
	}

}
