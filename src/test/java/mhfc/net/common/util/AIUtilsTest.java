package mhfc.net.common.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import mhfc.net.common.ai.general.AIUtils;
import net.minecraft.util.math.Vec3d;

public class AIUtilsTest {

	public static final double delta = 0.1d;

	@Before
	public void setUp() throws Exception {}

	@Test
	public void testYawConversion() {
		Vec3d yaw0 = new Vec3d(0, 0, 1);
		Vec3d yaw0_1 = new Vec3d(-0.2158149778842926, -0.14341038465499878, 0.9658312797546387);
		assertEquals(0, AIUtils.lookVecToYaw(yaw0), delta);
		assertEquals(12.600037, AIUtils.lookVecToYaw(yaw0_1), delta);

		Vec3d yaw90 = new Vec3d(-1, 0, 0);
		Vec3d yaw90_1 = new Vec3d(-0.9924389123916626, -0.10707709938287735, -0.05982581153512001);
		assertEquals(90, AIUtils.lookVecToYaw(yaw90), delta);
		assertEquals(93.45007, AIUtils.lookVecToYaw(yaw90_1.normalize()), delta);
	}

	@Test
	public void testNormalize() {
		assertEquals(0, AIUtils.normalizeAngle(360), delta);
		assertEquals(0, AIUtils.normalizeAngle(-360), delta);
		assertEquals(120, AIUtils.normalizeAngle(-240), delta);
		assertEquals(-120, AIUtils.normalizeAngle(240), delta);
	}
}
