package mhfc.net.common.ai.entity.boss.rathalos;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.monster.EntityRathalos;

public class Fireball extends ActionAdapter<EntityRathalos> {
	
	
	private static final String SET_Animation = "mhfc:models/Rathalos/RathalosFireBlast.mcanm";
	private static final int Set_Frame = 60;
	private static final double Set_MaxDistance = 15f;
	private static final float Set_Weight = 6F;
	
	
	public Fireball(){
		this.setAnimation(SET_Animation);
		this.setLastFrame(Set_Frame);
	}
	
	
	@Override
	public float getWeight() {
		return 0;
	}

	@Override
	protected void update() {
	}
	
	
	@Override
	public void beginExecution() {
		
	}

}
