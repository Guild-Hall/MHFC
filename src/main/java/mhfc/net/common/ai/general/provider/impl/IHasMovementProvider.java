package mhfc.net.common.ai.general.provider.impl;

import mhfc.net.common.ai.general.provider.composite.IMovementProvider;
import mhfc.net.common.ai.general.provider.requirements.INeedsMovementParameters;
import mhfc.net.common.ai.general.provider.requirements.INeedsPath;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IPathProvider;

public interface IHasMovementProvider extends INeedsMovementParameters, INeedsPath {
	IMovementProvider getMovementProvider();

	@Override
	default IMoveParameterProvider provideMoveParameters() {
		return getMovementProvider();
	}

	@Override
	default IPathProvider providePath() {
		return getMovementProvider();
	}
}
