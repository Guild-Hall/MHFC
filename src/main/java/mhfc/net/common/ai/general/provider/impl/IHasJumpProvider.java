package mhfc.net.common.ai.general.provider.impl;

import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.requirements.INeedsJumpParameters;
import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;

public interface IHasJumpProvider<T extends EntityMHFCBase<? super T>>
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
