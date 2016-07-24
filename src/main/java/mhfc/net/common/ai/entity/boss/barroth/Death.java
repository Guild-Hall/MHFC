package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityBarroth;

public class Death extends AIGeneralDeath<EntityBarroth> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Barroth/BarrothDeath.mcanm";

	public Death() {
		super(ANIMATION_LOCATION, "mhfc:barroth.death");
	}

}
