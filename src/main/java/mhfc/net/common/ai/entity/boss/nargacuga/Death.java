package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityNargacuga;

public class Death extends AIGeneralDeath<EntityNargacuga> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/NargaDeath.mcanm";

	public Death() {
		super(ANIMATION_LOCATION, "mhfc:narga.death");
	}

}
