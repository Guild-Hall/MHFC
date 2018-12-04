	package mhfc.net.common.ai.general.provider.adapters;

import com.github.worldsender.mcanm.common.animation.IAnimation;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.ai.general.provider.simple.IDamageProvider;

import java.util.Objects;

public class AttackAdapter implements IAttackProvider {
	protected IAnimationProvider animationProvider;
	protected IDamageProvider damageProvider;

	public AttackAdapter(IAnimationProvider animProvider, IDamageProvider damageProvider) {
		this.animationProvider = Objects.requireNonNull(animProvider);
		this.damageProvider = Objects.requireNonNull(damageProvider);
	}

	@Override
	public int getFollowingFrame(int currentFrame) {
		return animationProvider.getFollowingFrame(currentFrame);
	}

	@Override
	public IAnimation getAnimation() {
		return animationProvider.getAnimation();
	}

	@Override
	public boolean shouldContinueAction() {
		return animationProvider.shouldContinueAction();
	}

	@Override
	public IDamageCalculator getDamageCalculator() {
		return damageProvider.getDamageCalculator();
	}

}
