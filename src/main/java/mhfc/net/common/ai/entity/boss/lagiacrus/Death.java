package mhfc.net.common.ai.entity.boss.lagiacrus;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityLagiacrus;

public class Death extends AIGeneralDeath<EntityLagiacrus> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Lagiacrus/LagiacrusHurt.mcanm";

	public Death() {
		super(ANIMATION_LOCATION, "mhfc:lagiacrus.death");
	}

}
