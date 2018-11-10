package mhfc.net.common.ai.general.provider.impl;

import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.requirements.INeedsJumpParameters;
import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.entity.CreatureAttributes;

public interface IHasJumpProvider<T extends CreatureAttributes<? super T>>
		extends
		IHasAttackProvider,
		INeedsJumpParameters<T> {

	IJumpProvider<T> getJumpProvider();

	@Override
	default IAttackProvider getAttackProvider() {
		return getJumpProvider();
	}

	@Override
	default IJumpParameterProvider<T> provideJumpParameters() {
		return getJumpProvider();
	}

	@Override
	default IJumpTimingProvider<T> provideTimingParameters() {
		return getJumpProvider();
	}

}
