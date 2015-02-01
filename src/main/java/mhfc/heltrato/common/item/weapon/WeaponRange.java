package mhfc.heltrato.common.item.weapon;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

public class WeaponRange extends AbstractWeaponClass {
	
	public static float damage;

	@Override
	protected void onSetItem() {
		
	}

	@Override
	public void setThisItemProperties() {
		
	}

	@Override
	public float getEntityDamageMaterialPart() {
		return 0;
	}

	@Override
	public float getEntityDamage() {
		return 0;
	}

	@Override
	public float getBlockDamage(ItemStack itemstack, Block block) {
		return 0;
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return false;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world,
			Block block, int j, int k, int l, EntityLivingBase entityliving) {
		return false;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack,
			EntityLivingBase entityliving, EntityLivingBase attacker) {
		return false;
	}

	@Override
	public int getAttackDelay(ItemStack itemstack,
			EntityLivingBase entityliving, EntityLivingBase attacker) {
		return 0;
	}

	@Override
	public float getKnockBack(ItemStack itemstack,
			EntityLivingBase entityliving, EntityLivingBase attacker) {
		return 0;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addItemAttributeModifiers(
			Multimap<String, AttributeModifier> multimap) {
		
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return null;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 0;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player,
			Entity entity) {
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		return null;
	}

	@Override
	public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer,
			int count) {
		
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world,
			EntityPlayer entityplayer, int i) {
		
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity,
			int i, boolean flag) {
		
	}

	@Override
	public boolean shouldRotateAroundWhenRendering() {
		return false;
	}
	
	
	

}
