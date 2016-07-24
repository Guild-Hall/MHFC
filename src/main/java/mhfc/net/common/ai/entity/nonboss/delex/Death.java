package mhfc.net.common.ai.entity.nonboss.delex;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityDelex;

public class Death extends AIGeneralDeath<EntityDelex> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Delex/DelexDeath.mcanm";
//git
	public Death() {
		super(ANIMATION_LOCATION, "mhfc:delex.hurt");
	}

}
