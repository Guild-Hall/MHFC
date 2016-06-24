package mhfc.net.common.ai.entity.nonboss.gagua;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityGagua;

public class GaguaDeath extends AIGeneralDeath<EntityGagua> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Gagua/GaguaDeath.mcanm";

	public GaguaDeath() {
		super(ANIMATION_LOCATION);
	}

}
