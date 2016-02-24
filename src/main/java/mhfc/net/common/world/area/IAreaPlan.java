package mhfc.net.common.world.area;

import com.sk89q.worldedit.function.operation.Operation;

public interface IAreaPlan {
	/**
	 * Returns the {@link IArea}. Calling this before the {@link Operation} returned by {@link #getFirstOperation()} and
	 * all followup operations are complete may cause an {@link IllegalStateException}.
	 *
	 * @return the {@link IArea} of this area.
	 */
	IArea getFinishedArea();

	/**
	 * Returns the {@link Operation} that needs to be down to construct the Area. Operations chain themselves. Calling
	 * this method after the first {@link Operation} has been resumed may cause an {@link IllegalStateException}.
	 *
	 * @return the first operation to do
	 */
	Operation getFirstOperation();
}
