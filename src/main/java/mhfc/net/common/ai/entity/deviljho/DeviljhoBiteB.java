/**
 *
 */
package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;

/**
 * @author WorldSEnder
 *
 */
public class DeviljhoBiteB extends DeviljhoBiteA {
	private static final int LAST_FRAME = 40;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(44f, 50F, 9999999f);

	private static final double MAX_DIST = 9f;
	private static final float WEIGHT = 4;

	public DeviljhoBiteB() {
		setAnimation("mhfc:models/Deviljho/bite.mcanm");
		setLastFrame(LAST_FRAME);
	}

	@Override
	public void update() {
		if (this.getCurrentFrame() == 25) {
			getEntity().playSound("mhfc:deviljho.bite", 1.0F, 1.0F);
		}
	}

}
