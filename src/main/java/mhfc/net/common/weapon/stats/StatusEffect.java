package mhfc.net.common.weapon.stats;

import net.minecraft.entity.EntityLivingBase;

public enum StatusEffect implements ICombatEffectType {
	Poison {
		@Override
		public void applyTo(EntityLivingBase target, float damageAmount, EntityLivingBase attacker) {
			// TODO: apply poison
		}

		@Override
		public String getUnlocalizedName() {
			return "type.effect.poison";
		}
	},
	Sleep {
		@Override
		public void applyTo(EntityLivingBase target, float damageAmount, EntityLivingBase attacker) {
			// TODO: apply sleep
		}

		@Override
		public String getUnlocalizedName() {
			return "type.effect.sleep";
		}
	},
	Paralysis {
		@Override
		public void applyTo(EntityLivingBase target, float damageAmount, EntityLivingBase attacker) {
			// TODO: apply paralysis
		}

		@Override
		public String getUnlocalizedName() {
			return "type.effect.paralysis";
		}
	},
	Knockout {
		@Override
		public void applyTo(EntityLivingBase target, float damageAmount, EntityLivingBase attacker) {
			// TODO: apply Knockout
		}

		@Override
		public String getUnlocalizedName() {
			return "type.effect.knockout";
		}
	},
	Blastblight {
		@Override
		public void applyTo(EntityLivingBase target, float damageAmount, EntityLivingBase attacker) {
			// TODO: apply blastblight
		}

		@Override
		public String getUnlocalizedName() {
			return "type.effect.blastblight";
		}
	};

}
