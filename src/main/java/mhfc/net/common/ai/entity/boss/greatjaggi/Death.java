package mhfc.net.common.ai.entity.boss.greatjaggi;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityGreatJaggi;

public class Death extends AIGeneralDeath<EntityGreatJaggi> {

	private static final String ANIMATION_LOCATION = "mhfc:models/GreatJaggi/GreatJaggiDeath.mcanm";

	public Death() {
		super(ANIMATION_LOCATION, MHFCSoundRegistry.getRegistry().greatJaggiDeath);
	}

}
