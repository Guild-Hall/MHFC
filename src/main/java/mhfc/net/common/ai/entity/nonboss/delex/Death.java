package mhfc.net.common.ai.entity.nonboss.delex;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.EntityDelex;

public class Death extends AIGeneralDeath<EntityDelex> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Delex/DelexDeath.mcanm";

	public Death() {
		super(ANIMATION_LOCATION, MHFCSoundRegistry.getRegistry().delexDeath);
	}

}
