package mhfc.net.common.weapon.stats;

import net.minecraft.entity.Entity;

public enum StatusEffect implements ICombatEffectType {
	Poison {
		@Override
		public void applyTo(Entity target, float damageAmount) {
			// TODO: apply poison
		}

		@Override
		public String getUnlocalizedName() {
			return "type.effect.poison";
		}
	},
	Sleep {
		@Override
		public void applyTo(Entity target, float damageAmount) {
			// TODO: apply sleep
		}

		@Override
		public String getUnlocalizedName() {
			return "type.effect.sleep";
		}
	},
	Paralysis {
		@Override
		public void applyTo(Entity target, float damageAmount) {
			// TODO: apply paralysis
		}

		@Override
		public String getUnlocalizedName() {
			return "type.effect.paralysis";
		}
	},
	Knockout {
		@Override
		public void applyTo(Entity target, float damageAmount) {
			// TODO: apply Knockout
		}

		@Override
		public String getUnlocalizedName() {
			return "type.effect.knockout";
		}
	},
	Blastblight {
		@Override
		public void applyTo(Entity target, float damageAmount) {
			// TODO: apply blastblight
		}

		@Override
		public String getUnlocalizedName() {
			return "type.effect.blastblight";
		}
	};

}
