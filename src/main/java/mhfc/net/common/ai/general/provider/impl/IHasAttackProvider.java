package mhfc.net.common.ai.general.provider.impl;

import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.requirements.INeedsDamageCalculator;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;

public interface IHasAttackProvider extends IHasAnimationProvider, INeedsDamageCalculator {

	IAttackProvider getAttackProvider();

	@Override
	default IAnimationProvider getAnimProvider() {
		return getAttackProvider();
	}

	@Override
	default IDamageCalculator provideDamageCalculator() {
		return getAttackProvider().getDamageCalculator();
	}
}
