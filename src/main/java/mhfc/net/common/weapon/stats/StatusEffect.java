package mhfc.net.common.weapon.stats;

import net.minecraft.entity.Entity;

public enum StatusEffect implements ICombatEffect {
	Poison {
		@Override
		public void applyTo(Entity target, float damageAmount) {
			// TODO: apply poison
		}
	},
	Sleep {
		@Override
		public void applyTo(Entity target, float damageAmount) {
			// TODO: apply sleep
		}
	},
	Paralysis {
		@Override
		public void applyTo(Entity target, float damageAmount) {
			// TODO: apply paralysis
		}
	},
	KO {
		@Override
		public void applyTo(Entity target, float damageAmount) {
			// TODO: apply KO
		}
	};

}
