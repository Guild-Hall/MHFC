package mhfc.net.common.weapon.stats;

import java.util.Objects;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public enum ElementalType implements ICombatEffectType {
	Fire(new DamageSource("mhfc.fireelement").setDamageBypassesArmor()) {
		@Override
		public void onEntitySwing(EntityLivingBase entity, ItemStack stack, Random rand) {
			double velX = rand.nextGaussian(), velY = rand.nextGaussian(), velZ = rand.nextGaussian();
			double posX = entity.posX, posY = entity.posY, posZ = entity.posZ;
			entity.worldObj.spawnParticle("lava", posX, posY, posZ, velX, velY, velZ);
		}

		@Override
		public String getUnlocalizedName() {
			return "type.effect.fire";
		}
	},
	Water(new DamageSource("mhfc.waterelement").setDamageBypassesArmor()) {
		@Override
		public String getUnlocalizedName() {
			return "type.effect.water";
		}
	},
	Thunder(new DamageSource("mhfc.thunderelement").setDamageBypassesArmor()) {
		@Override
		public String getUnlocalizedName() {
			return "type.effect.thunder";
		}
	},
	Dragon(new DamageSource("mhfc.dragonelement").setDamageBypassesArmor()) {
		@Override
		public String getUnlocalizedName() {
			return "type.effect.dragon";
		}
	},
	Ice(new DamageSource("mhfc.iceelement").setDamageBypassesArmor()) {
		@Override
		public String getUnlocalizedName() {
			return "type.effect.ice";
		}
	};

	public final DamageSource damageSource;

	private ElementalType(DamageSource damageSrc) {
		this.damageSource = Objects.requireNonNull(damageSrc);
	}

	@Override
	public void applyTo(Entity target, float damageAmount) {
		// TODO: add the attacker as damageSourceEntity
		target.attackEntityFrom(damageSource, damageAmount);
	}
}
