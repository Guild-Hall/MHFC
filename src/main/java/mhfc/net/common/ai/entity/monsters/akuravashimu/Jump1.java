package mhfc.net.common.ai.entity.monsters.akuravashimu;

import java.util.List;

import mhfc.net.common.ai.entity.EntityAIMethods;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.JumpAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackTargetAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpTimingAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.impl.IHasJumpProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.ai.general.provider.simple.IJumpParameterProvider;
import mhfc.net.common.ai.general.provider.simple.IJumpTimingProvider;
import mhfc.net.common.entity.CreatureAttributes;
import mhfc.net.common.entity.creature.incomplete.AkuraVashimu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class Jump1 extends JumpAction<AkuraVashimu> implements IHasJumpProvider<AkuraVashimu>{

	private static final String ANIMATION = "mhfc:models/akuravashimu/jump1.mcanm";
	private static final int LAST_FRAME = 90;
	private static final int JUMP_FRAME = 34;
	private static final float TURN_RATE = 0;
	private static final float JUMP_TIME = 35;

	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(90F, 62f, 999999F);
	private static final float SELECTION_WEIGHT = 6f;

	private final IJumpProvider<AkuraVashimu> JUMP;
	{
		IJumpParameterProvider<AkuraVashimu> params = new AttackTargetAdapter<>(JUMP_TIME);
		IJumpTimingProvider<AkuraVashimu> timing = new JumpTimingAdapter<>(JUMP_FRAME, TURN_RATE, 0);
		IAnimationProvider animation = new AnimationAdapter(this, ANIMATION, LAST_FRAME);
		JUMP = new JumpAdapter<>(animation, new DamageAdapter(damageCalc), params, timing);
	}

	public Jump1() {}


	@Override
	protected float computeSelectionWeight() {
		AkuraVashimu entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		if (!SelectionUtils.isInDistance(0F, 7F, entity, target)) {
			return DONT_SELECT;
		}
		return SELECTION_WEIGHT;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		AkuraVashimu entity = getEntity();
		if(this.getCurrentFrame() % 20 == 0) {
		damageCollidingEntities();
		}
		if(this.getCurrentFrame() >= 60 && this.getCurrentFrame() < 62) {
			if(entity.onGround) {
			entity.world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 8.0F, false);
			CreatureAttributes.screenIntensity(entity, 10F, 40F);
			List<EntityLivingBase> entities = EntityAIMethods.getEntitiesNearby(entity, EntityLivingBase.class, 10);
			for(Entity affected: entities) {
				affected.motionY += 0.06D;
			}
			}
		}
		
	}
		

	@Override
	public IJumpProvider<AkuraVashimu> getJumpProvider() {
		return JUMP;
	}

}
