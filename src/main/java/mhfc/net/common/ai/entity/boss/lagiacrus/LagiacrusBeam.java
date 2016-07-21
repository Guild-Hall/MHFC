package mhfc.net.common.ai.entity.boss.lagiacrus;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import mhfc.net.common.entity.projectile.EntityBeam;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;

public class LagiacrusBeam extends ActionAdapter<EntityLagiacrus> {

	private static int ANIM_FRAME = 100;
	private static double TARGET_DISTANCE = 30F;
	
	private static float WEIGHT = 15;
	
	public LagiacrusBeam() {
		setAnimation("mhfc:models/Lagiacrus/LagiacrusBeam.mcanm");
		setLastFrame(ANIM_FRAME);
	}
	
	@Override
	public float getWeight() {
		EntityLagiacrus entity = getEntity();
		if(target == null){
			return DONT_SELECT;
		}
		Vec3 LOOK_TARGET = WorldHelper.getVectorToTarget(entity, target);
		double distance = LOOK_TARGET.lengthVector();
		if (distance > TARGET_DISTANCE) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}
	
	
	@Override
	public void beginExecution(){
		getEntity().playSound("mhfc:lagiacrus.beam", 2.0F, 1.0F);
	}

	@Override
	protected void update() {
		EntityLagiacrus entity = getEntity();
		 float radius1 = 1.7f;
		 if(this.getCurrentFrame() == 10 && !entity.worldObj.isRemote){
			EntityBeam beam = new EntityBeam(entity.worldObj, entity, entity.posX + radius1 * Math.sin(-entity.rotationYaw * Math.PI / 180), entity.posY + 1.4, entity.posZ + radius1 * Math.cos(-entity.rotationYaw * Math.PI / 180), (float) ((entity.rotationYawHead + 90) * Math.PI / 180), (float) (-entity.rotationPitch * Math.PI / 180), 55);
			entity.worldObj.spawnEntityInWorld(beam);
		 	}
		
	}
	
	

}
