package mhfc.net.common.ai.entity.boss.lagiacrus;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityLagiacrus;

public class LagiacrusDying extends AIGeneralDeath<EntityLagiacrus> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Lagiacrus/LagiacrusHurt.mcanm";

	public LagiacrusDying() {
		super(ANIMATION_LOCATION);
	}

}
