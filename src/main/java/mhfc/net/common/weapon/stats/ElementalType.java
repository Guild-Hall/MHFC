package mhfc.net.common.weapon.stats;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;

public enum ElementalType implements ICombatEffectType {
	Fire(EnumParticleTypes.LAVA) {
		@Override
		public String getUnlocalizedName() {
			return "type.effect.fire";
		}

		@Override
		protected DamageSource getDamageSource(EntityLivingBase attacker) {
			return createSourceBase("mhfc.fireelement", attacker).setDamageBypassesArmor();
		}
	},
	Water(EnumParticleTypes.WATER_BUBBLE) {
		@Override
		public String getUnlocalizedName() {
			return "type.effect.water";
		}

		@Override
		protected DamageSource getDamageSource(EntityLivingBase attacker) {
			return createSourceBase("mhfc.waterelement", attacker).setDamageBypassesArmor();
		}
	},
	Thunder(null) {
		@Override
		public String getUnlocalizedName() {
			return "type.effect.thunder";
		}

		@Override
		protected DamageSource getDamageSource(EntityLivingBase attacker) {
			return createSourceBase("mhfc.thunderelement", attacker).setDamageBypassesArmor();
		}
	},
	Dragon(EnumParticleTypes.DRAGON_BREATH) {
		@Override
		public String getUnlocalizedName() {
			return "type.effect.dragon";
		}

		@Override
		protected DamageSource getDamageSource(EntityLivingBase attacker) {
			return createSourceBase("mhfc.dragonelement", attacker).setDamageBypassesArmor();
		}
	},
	Ice(null) {
		@Override
		public String getUnlocalizedName() {
			return "type.effect.ice";
		}

		@Override
		protected DamageSource getDamageSource(EntityLivingBase attacker) {
			return createSourceBase("mhfc.iceelement", attacker).setDamageBypassesArmor();
		}
	};

	private final EnumParticleTypes particleType;

	private ElementalType(EnumParticleTypes particle) {
		this.particleType = particle;
	}

	@Override
	public void onEntitySwing(EntityLivingBase entity, ItemStack stack, Random rand) {
		if (particleType != null) {
			double velX = rand.nextGaussian(), velY = rand.nextGaussian(), velZ = rand.nextGaussian();
			double posX = entity.posX, posY = entity.posY, posZ = entity.posZ;
			entity.world.spawnParticle(particleType, posX, posY, posZ, velX, velY, velZ);
		}
	}

	@Override
	public void applyTo(EntityLivingBase target, float damageAmount, EntityLivingBase attacker) {
		target.attackEntityFrom(getDamageSource(attacker), damageAmount);
	}

	protected abstract DamageSource getDamageSource(EntityLivingBase attacker);

	protected static DamageSource createSourceBase(String type, EntityLivingBase attacker) {
		return attacker == null ? new DamageSource(type) : new EntityDamageSource(type, attacker);
	}
}
