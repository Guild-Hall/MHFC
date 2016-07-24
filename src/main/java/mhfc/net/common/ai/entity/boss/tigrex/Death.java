package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityTigrex;

public class Death extends AIGeneralDeath<EntityTigrex> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Tigrex/dying.mcanm";

	public Death() {
		super(ANIMATION_LOCATION, "mhfc:tigrex.death");
	}

}
