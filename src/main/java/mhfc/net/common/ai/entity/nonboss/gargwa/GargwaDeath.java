package mhfc.net.common.ai.entity.nonboss.gargwa;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityGargwa;

public class GargwaDeath extends AIGeneralDeath<EntityGargwa> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Gagua/GaguaDeath.mcanm";

	public GargwaDeath() {
		super(ANIMATION_LOCATION, "mhfc:gagua.death");
	}

}
