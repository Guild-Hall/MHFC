package mhfc.net.common.ai.general.provider.adapters;

import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;

public class MoveParameterAdapter implements IMoveParameterProvider {

	private float turnRate;
	private float moveSpeed;

	public MoveParameterAdapter(float turnRate, float moveSpeed) {
		this.turnRate = turnRate;
		this.moveSpeed = moveSpeed;
	}

	@Override
	public float getTurnRate() {
		return turnRate;
	}

	@Override
	public float getMoveSpeed() {
		return moveSpeed;
	}

}
