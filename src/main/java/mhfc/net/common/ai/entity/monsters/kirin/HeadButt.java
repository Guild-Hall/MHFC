package mhfc.net.common.ai.entity.monsters.kirin;

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
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.monster.wip.EntityKirin;

public class HeadButt extends JumpAction<EntityKirin> implements IHasJumpProvider<EntityKirin> {

	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(90F, 62f, 999999F);

	
	private final IJumpProvider<EntityKirin> HEADBUTT;
	{
		IJumpParameterProvider<EntityKirin> params = new AttackTargetAdapter<>(15F);
		IJumpTimingProvider<EntityKirin> timing = new JumpTimingAdapter<>(16, 12f, 0);
		IAnimationProvider animation = new AnimationAdapter(this, "mhfc:models/Kirin/kirinheadbutt.mcanm", 35);
		HEADBUTT = new JumpAdapter<>(animation, new DamageAdapter(damageCalc), params, timing);
	}
	
	public HeadButt() {}
	
	@Override
	protected float computeSelectionWeight() {
		EntityKirin entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		return 5F;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		EntityKirin entity = getEntity();
		damageCollidingEntities();
		if (this.getCurrentFrame() == 18) {
			damageCollidingEntities();
			entity.playSound(MHFCSoundRegistry.getRegistry().kirinIdle, 2.0F, 1.0F);
		}
	}


	@Override
	public IJumpProvider<EntityKirin> getJumpProvider() {
		return HEADBUTT;
	}


}
