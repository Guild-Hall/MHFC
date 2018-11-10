package mhfc.net.common.ai.general.provider.requirements;

import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.entity.CreatureAttributes;

public interface INeedsJumpParameters<T extends CreatureAttributes<? super T>> {

	IJumpParameterProvider<T> provideJumpParameters();

	IJumpTimingProvider<T> provideTimingParameters();

}
