package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.general.actions.AIGeneralDeath;
import mhfc.net.common.entity.monster.EntityDeviljho;

public class DeviljhoDying extends AIGeneralDeath<EntityDeviljho> {

	private static final String ANIMATION_LOCATION = "mhfc:models/Deviljho/DeviljhoDeath.mcanm";

	public DeviljhoDying() {
		super(ANIMATION_LOCATION);
	}

}
