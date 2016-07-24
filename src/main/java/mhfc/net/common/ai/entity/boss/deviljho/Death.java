package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityDeviljho;

public class Death extends AIGeneralDeath<EntityDeviljho> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Deviljho/DeviljhoDeath.mcanm";

	public Death() {
		super(ANIMATION_LOCATION, "mhfc:deviljho.death");
	}

}
