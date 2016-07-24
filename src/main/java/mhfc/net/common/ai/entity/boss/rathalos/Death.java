package mhfc.net.common.ai.entity.boss.rathalos;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityRathalos;

public class Death extends AIGeneralDeath<EntityRathalos> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Rathalos/RathalosDeath.mcanm";

	public Death() {
		super(ANIMATION_LOCATION, "mhfc:rathalos.death");
	}

}
