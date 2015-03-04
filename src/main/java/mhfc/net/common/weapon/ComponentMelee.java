package mhfc.net.common.weapon;

import mhfc.net.common.util.Attributes;
import mhfc.net.common.util.Utilities;
import mhfc.net.common.weapon.melee.IExtendedReachItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ComponentMelee extends AbstractWeaponClass {
	public final WeaponSpecs weaponSpecs;
	public final Item.ToolMaterial weaponMaterial;

	public ComponentMelee(WeaponSpecs meleespecs, Item.ToolMaterial toolmaterial) {
		weaponSpecs = meleespecs;
		weaponMaterial = toolmaterial;
	}

	@Override
	protected void onSetItem() {}

	@Override
	public void setThisItemProperties() {
		if (weaponMaterial == null) {
			item.setMaxDamage(weaponSpecs.durabilityBase);
		} else {
			item.setMaxDamage((int) (weaponSpecs.durabilityBase + weaponMaterial
					.getMaxUses() * weaponSpecs.damageMult));
		}
		item.setMaxStackSize(weaponSpecs.stackSize);
	}

	@Override
	public float getEntityDamageMaterialPart() {
		if (weaponMaterial == null) {
			return 0F;
		}
		return weaponMaterial.getDamageVsEntity() * weaponSpecs.damageMult;
	}

	@Override
	public float getEntityDamage() {
		return weaponSpecs.damageBase + getEntityDamageMaterialPart();
	}

	@Override
	public float getBlockDamage(ItemStack itemstack, Block block) {
		if (canHarvestBlock(block)) {
			return weaponSpecs.blockDamage * 10F;
		}
		Material material = block.getMaterial();
		return material != Material.plants && material != Material.vine
				&& material != Material.coral && material != Material.leaves
				&& material != Material.gourd ? 1.0F : weaponSpecs.blockDamage;
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return block == Blocks.web;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world,
			Block block, int j, int k, int l, EntityLivingBase entityliving) {
		if ((weaponSpecs.blockDamage > 1F || canHarvestBlock(block))
				&& block.getBlockHardness(world, j, k, l) != 0F) {
			itemstack.damageItem(weaponSpecs.dmgFromBlock, entityliving);
		}
		return true;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack,
			EntityLivingBase entityliving, EntityLivingBase attacker) {
		if (entityliving.hurtResistantTime == entityliving.maxHurtResistantTime) {
			float kb = getKnockBack(itemstack, entityliving, attacker);
			// if (entityliving.onGround)
			{
				Utilities.knockBack(entityliving, attacker, kb);
			}
			entityliving.hurtResistantTime += getAttackDelay(itemstack,
					entityliving, attacker);
		}
		itemstack.damageItem(weaponSpecs.dmgFromEntity, attacker);
		return true;
	}

	@Override
	public int getAttackDelay(ItemStack itemstack,
			EntityLivingBase entityliving, EntityLivingBase attacker) {
		return weaponSpecs.attackDelay;
	}

	@Override
	public float getKnockBack(ItemStack itemstack,
			EntityLivingBase entityliving, EntityLivingBase attacker) {
		return weaponSpecs.getKnockBack(weaponMaterial);
	}

	@Override
	public int getItemEnchantability() {
		return weaponMaterial == null ? 1 : weaponMaterial.getEnchantability();
	}

	@Override
	public void addItemAttributeModifiers(
			Multimap<String, AttributeModifier> multimap) {
		float dmg = getEntityDamage();
		if (dmg > 0F || weaponSpecs.damageMult > 0F) {
			multimap.put(SharedMonsterAttributes.attackDamage
					.getAttributeUnlocalizedName(), new AttributeModifier(
					weapon.getUUID(), "Weapon modifier", dmg - 4, 0));
		}
		multimap.put(
				Attributes.WEAPON_KNOCKBACK.getAttributeUnlocalizedName(),
				new AttributeModifier(weapon.getUUID(),
						"Weapon knockback modifier", weaponSpecs
								.getKnockBack(weaponMaterial) - 0.4F, 0));
		multimap.put(Attributes.ATTACK_SPEED.getAttributeUnlocalizedName(),
				new AttributeModifier(weapon.getUUID(),
						"Weapon attack speed modifier",
						weaponSpecs.attackDelay, 0));
		if (this instanceof IExtendedReachItem) {
			try {
				multimap.put(
						Attributes.WEAPON_REACH.getAttributeUnlocalizedName(),
						new AttributeModifier(weapon.getUUID(),
								"Weapon reach modifier",
								((IExtendedReachItem) this).getExtendedReach(
										null, null, null) - 3F, 0));
			} catch (NullPointerException e) {}
		}
	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player,
			Entity entity) {
		return false;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.block;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}

	@Override
	public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer,
			int count) {}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world,
			EntityPlayer entityplayer, int i) {}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity,
			int i, boolean flag) {}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldRotateAroundWhenRendering() {
		return false;
	}

	public static enum WeaponSpecs {
		// NAME db, dm, edb, edm, bd, kb, dfe, dfb, mss, ad
		GREATSWORD(0, 1F, 3, 1F, 1.5F, 0.5F, 1, 2, 1, 3), //
		HAMMER(0, 1F, 4, 1F, 1F, 0.9F, 1, 2, 1, 9), //
		HUNTINGHORN(0, 0.5F, 3, 1F, 1.5F, 0.6F, 1, 2, 1, 6), //
		LONGSWORD(0, 1F, 1, 1F, 1F, 0F, 1, 2, 1, -4), //
		NONE(0, 0F, 1, 0F, 1F, 0.4F, 0, 0, 1, 0);

		private WeaponSpecs(int durbase, float durmult, float dmgbase,
				float dmgmult, float blockdmg, float knockback,
				int dmgfromentity, int dmgfromblock, int stacksize,
				int attackdelay) {
			durabilityBase = durbase;
			durabilityMult = durmult;

			damageBase = dmgbase;
			damageMult = dmgmult;
			blockDamage = blockdmg;
			this.knockback = knockback;

			dmgFromEntity = dmgfromentity;
			dmgFromBlock = dmgfromblock;

			stackSize = stacksize;
			attackDelay = attackdelay;
		}

		public float getKnockBack(ToolMaterial material) {
			return material == ToolMaterial.GOLD ? knockback * 1.5F : knockback;
		}

		public final int durabilityBase;
		public final float durabilityMult;

		public final float damageBase, damageMult, blockDamage, knockback;

		public final int dmgFromEntity, dmgFromBlock;

		public final int stackSize, attackDelay;
	}
}