package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityBarroth;

public class BarrothDeath extends AIGeneralDeath<EntityBarroth> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Barroth/BarrothDeath.mcanm";

	public BarrothDeath() {
		super(ANIMATION_LOCATION);
	}

}
