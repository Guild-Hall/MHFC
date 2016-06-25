package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityNargacuga;

public class NargacugaDeath extends AIGeneralDeath<EntityNargacuga> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Nargacuga/NargaDeath.mcanm";

	public NargacugaDeath() {
		super(ANIMATION_LOCATION);
	}

}
