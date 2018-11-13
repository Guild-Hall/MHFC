package mhfc.net.common.ai.entity.monsters.rathalos;

import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.AnimatedAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAnimationProvider;
import mhfc.net.common.entity.creature.Rathalos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Fireball extends AnimatedAction<Rathalos> implements IHasAnimationProvider {

	private static final int LAST_FRAME = 60;
	private static final String ANIMATION_LOCATION = "mhfc:models/Rathalos/RathalosFireBlast.mcanm";


	private final IAnimationProvider ANIMATION = new AnimationAdapter(this, ANIMATION_LOCATION, LAST_FRAME);

	public Fireball() {}
	
	@Override
	protected float computeSelectionWeight() {
		Rathalos entity = this.getEntity();
		target = entity.getAttackTarget();
			if (SelectionUtils.isIdle(entity)) {
				return DONT_SELECT;
			}
			if (!SelectionUtils.isInDistance(0, 15, entity, target)) {
				return DONT_SELECT;
		}
		return 25;
	}


	@Override
	public IAnimationProvider getAnimProvider() {
		return ANIMATION;
	}
	
	
	@Override
	protected void onUpdate() {
		Rathalos entity = this.getEntity();
		target = entity.getAttackTarget();

		if (target != null) {
			entity.getTurnHelper().updateTurnSpeed(30F);
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getLookHelper().setLookPositionWithEntity(target, 15, 15);

			if (this.getCurrentFrame() == 22) {
                Vec3d vec3d = this.getEntity().getLook(1.0F);
                double d2 = target.posX - (this.getEntity().posX + vec3d.x * 4.0D);
                double d3 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (0.5D + this.getEntity().posY + (double)(this.getEntity().height / 2.0F));
                double d4 = target.posZ - (this.getEntity().posZ + vec3d.z * 4.0D);
                this.getEntity().world.playEvent((EntityPlayer)null, 1016, new BlockPos(this.getEntity()), 0);
                EntityLargeFireball breath = new EntityLargeFireball(this.getEntity().world, this.getEntity(), d2, d3, d4);
                breath.explosionPower = 2;
                breath.posX =this.getEntity().posX + vec3d.x * 4.0D;
                breath.posY = this.getEntity().posY + (double)(this.getEntity().height / 2.0F) + 0.5D;
                breath.posZ = this.getEntity().posZ + vec3d.z * 4.0D;
			
			if (!entity.world.isRemote)
				entity.world.spawnEntity(breath);
			}
		}
	}

}
