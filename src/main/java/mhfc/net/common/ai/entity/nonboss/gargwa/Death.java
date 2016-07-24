package mhfc.net.common.ai.entity.nonboss.gargwa;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityGargwa;

public class Death extends AIGeneralDeath<EntityGargwa> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Gagua/GaguaDeath.mcanm";

	public Death() {
		super(ANIMATION_LOCATION, "mhfc:gagua.death");
	}

}
