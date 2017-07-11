/**
 *
 */
package mhfc.net.common.ai.entity.boss.deviljho;

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
import mhfc.net.common.entity.monster.EntityDeviljho;
import net.minecraft.util.SoundEvent;

/**
 * @author WorldSEnder ** dang so complex men ~Heltrato
 */
public class Bite extends DamagingAction<EntityDeviljho> implements IHasAttackProvider {
	
	
	protected float damage;
	protected float weight;
	protected String animationLocation;
	protected int animationLength;
	protected int damageFrame;
	protected SoundEvent sound;

	public Bite(
			String animationLocation,
			int animationLength,
			int damageFrame,
			float damage,
			float weight,
			SoundEvent sound) {
		this.animationLocation = animationLocation;
		this.animationLength = animationLength;
		this.damageFrame = damageFrame;
		this.damage = damage;
		this.weight = weight;
		this.sound = sound;
		
		
		
	}

	//BITE_2("mhfc:models/Deviljho/bite.mcanm", 40) {
	//	BITE_1("mhfc:models/Deviljho/bite2.mcanm", 35) {

	@Override
	public IAnimationProvider getAnimProvider() {
		return new AnimationAdapter(this, this.animationLocation, this.animationLength);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		EntityDeviljho entity = getEntity();
		target = getEntity().getAttackTarget();
		if (target != null) {
			if (getCurrentFrame() == this.damageFrame) {
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
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();

		if (this.getCurrentAnimation() != null) {
			if (SelectionUtils.isIdle(entity)) {
				return DONT_SELECT;
			}
			if (!SelectionUtils.isInDistance(0, 10F, entity, target)) {
				return DONT_SELECT;
			}
		}
		return this.weight;
	}

}