/**
 *
 */
package mhfc.net.common.ai.entity;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAnimationProvider;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.util.SoundEvent;

/**
 * @author WorldSEnder ** dang so complex men ~Heltrato
 */
public class AIBite extends DamagingAction<EntityMHFCBase<?>> implements IHasAttackProvider {

	protected float damage;
	protected float weight;
	protected String animationLocation;
	protected int animationLength;
	protected int damageFrame;
	protected SoundEvent sound;
	protected EntityMHFCBase<?> entity = this.getEntity();
	protected float biteRange;

	public AIBite(
			EntityMHFCBase<?> entity,
			String animationLocation,
			int animationLength,
			int damageFrame,
			float damage,
			float weight,
			SoundEvent sound,
			float biteRange) {
		this.entity = entity;
		this.animationLocation = animationLocation;
		this.animationLength = animationLength;
		this.damageFrame = damageFrame;
		this.damage = damage;
		this.weight = weight;
		this.sound = sound;
		this.biteRange = biteRange;

	}

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, this.animationLocation, this.animationLength);
	}

	@Override
	public void onUpdate() {

		target = this.entity.getAttackTarget();
		if (target != null) {
			super.onUpdate();
			if (getCurrentFrame() == this.damageFrame) {
				if (entity.targetDistance <= biteRange) {
					AIUtils.damageEntitiesFromAI(entity, target, provideDamageCalculator());
				}
				entity.playSound(sound, 2.0F, 1.0F);
			}
		}

	}

	@Override
	public IDamageCalculator provideDamageCalculator() {
		return AIUtils.defaultDamageCalc(this.damage, this.damage + 50F, 9999999f);
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(getAnimProvider(), new DamageAdapter(provideDamageCalculator()));
	}

	@Override
	protected float computeSelectionWeight() {
		target = this.entity.getAttackTarget();

			if (SelectionUtils.isIdle(this.entity)) {
				return DONT_SELECT;
			}
			if (!SelectionUtils.isInDistance(0, 10F, this.entity, target)) {
				return DONT_SELECT;
			}
		return this.weight;
	}

}