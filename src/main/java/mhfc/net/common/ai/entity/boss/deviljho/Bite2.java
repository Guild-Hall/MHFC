/**
 *
 */
package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;

/**
 * @author WorldSEnder
 *
 */
public class Bite2 extends Bite1 {
	@SuppressWarnings("unused")
	private static final String ANIMATION = "mhfc:models/Deviljho/bite.mcanm";
	private static final int LAST_FRAME = 40;
	@SuppressWarnings("unused")
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(120f, 50F, 9999999f);

	@SuppressWarnings("unused")
	private static final double MAX_DIST = 5f;
	@SuppressWarnings("unused")
	private static final float WEIGHT = 9;

	public Bite2() {
		setAnimation("mhfc:models/Deviljho/bite.mcanm");
		setLastFrame(LAST_FRAME);
	}

	@Override
	public void update() {
		if (this.getCurrentFrame() == 25) {
			getEntity().playSound("mhfc:deviljho.biteb", 2.0F, 1.0F);
		}
	}

}
