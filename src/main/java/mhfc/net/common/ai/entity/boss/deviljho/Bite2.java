/**
 *
 */
package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.core.registry.MHFCSoundRegistry;

/**
 * @author WorldSEnder
 *
 */
public class Bite2 extends Bite1 {
	private static final String ANIMATION = "mhfc:models/Deviljho/bite.mcanm";
	private static final int LAST_FRAME = 40;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(120f, 50F, 9999999f);

	private static final double MAX_DIST = 5f;
	private static final float WEIGHT = 9;

	public Bite2() {
		setAnimation(ANIMATION);
		setLastFrame(LAST_FRAME);
	}

	@Override
	public void update() {
		if (this.getCurrentFrame() == 25) {
			getEntity().playSound(MHFCSoundRegistry.getRegistry().deviljhoBiteB, 2.0F, 1.0F);
		}
	}

}
