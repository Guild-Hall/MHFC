package mhfc.net.common.ai.entity.nargacuga;

import mhfc.net.MHFCMain;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIGeneralJumpAttack;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.IDamageProvider;
import mhfc.net.common.ai.general.provider.IJumpParamterProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;

public final class NargacugaPounce extends AIGeneralJumpAttack<EntityNargacuga> {

	private static final float WEIGHT = 3.0f;

	private static final IDamageCalculator dmgCalculator = AIUtils.defaultDamageCalc(28, 300, 5000);

	public static NargacugaPounce createNargaPounce(NargaJumpBehaviour behaviour) {
		IAnimationProvider animation = behaviour.getAnimation();
		ISelectionPredicate<EntityNargacuga> select = behaviour.getSelectionPredicate();
		IWeightProvider<EntityNargacuga> weight = new IWeightProvider.SimpleWeightAdapter<>(WEIGHT);
		IDamageProvider damage = new IDamageProvider.DamageAdapter(dmgCalculator);
		IJumpTimingProvider<EntityNargacuga> timing = behaviour.getJumpTiming();
		IJumpParamterProvider<EntityNargacuga> params = behaviour.getJumpParameters();
		NargacugaPounce pounce = new NargacugaPounce(
				new JumpAdapter<EntityNargacuga>(animation, select, weight, damage, params, timing),
				behaviour);
		return pounce;

	}

	private NargaJumpBehaviour behaviour;

	private NargacugaPounce(IJumpProvider<EntityNargacuga> provider, NargaJumpBehaviour behaviour) {
		super(provider);
		this.behaviour = behaviour;
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		MHFCMain.logger.debug("Narga jump {}", this.behaviour);
		setToNextFrame(18);
	}

}
