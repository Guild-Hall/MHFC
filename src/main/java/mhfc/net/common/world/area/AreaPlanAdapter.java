package mhfc.net.common.world.area;

import com.sk89q.worldedit.function.operation.Operation;

public class AreaPlanAdapter implements IAreaPlan {
	private IArea area;
	private Operation op;

	public AreaPlanAdapter(IArea finished, Operation op) {
		this.area = finished;
		this.op = op;
	}

	@Override
	public IArea getFinishedArea() {
		return area;
	}

	@Override
	public Operation getFirstOperation() {
		return op;
	}
}
