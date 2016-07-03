package mhfc.net.common.world.controller;

import java.util.concurrent.CompletionStage;

import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaType;

public interface IAreaManager {
	/**
	 * Gets an empty {@link IArea} of the {@link IAreaType} given. This tries to reuse existing instances if possible.
	 * If no existing IArea of the type can be found, a new one is created.<br>
	 * The area is embedded in the {@link IActiveArea}. When the caller is finished using the area he should call
	 * {@link IActiveArea#dismiss()} and dispose his instance.
	 *
	 * @param type
	 *            the type of the requested area
	 * @return an {@link IActiveArea} with an empty area that can be used for e.g. questing
	 * @throws Exception
	 */
	CompletionStage<IActiveArea> getUnusedInstance(IAreaType type);
}
