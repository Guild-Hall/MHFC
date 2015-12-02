/**
 *
 */
package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;

/**
 * @author WorldSEnder
 *
 */
public class DeviljhoBiteB extends DeviljhoBiteA {
	private static final int LAST_FRAME = 40;
	private static final IDamageCalculator damageCalc = AIUtils
		.defaultDamageCalc(44f, 62f, 1600f);

	private static final double MAX_DIST = 9f;
	private static final double MAX_ANGLE = 0.155; // This is cos(30)
	private static final float WEIGHT = 4;

	public DeviljhoBiteB() {
		setAnimation("mhfc:models/Deviljho/bite.mcanm");
		setLastFrame(LAST_FRAME);
	}
	
	@Override
	public void update() {
		if (this.getCurrentFrame() == 25) {
			getEntity().playSound("mhfc:deviljho-bite", 1.0F, 1.0F);
		}
	}


}
