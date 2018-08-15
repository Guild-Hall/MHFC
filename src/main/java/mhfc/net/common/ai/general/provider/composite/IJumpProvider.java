package mhfc.net.common.ai.general.provider.composite;

import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;

public interface IJumpProvider<T extends EntityMHFCBase<? super T>>
		extends
		IAttackProvider,
		IJumpTimingProvider<T>,
		IJumpParameterProvider<T> {

}
