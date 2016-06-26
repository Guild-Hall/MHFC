package mhfc.net.common.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import mhfc.net.common.ai.general.AIUtils;
import net.minecraft.util.Vec3;

public class AIUtilsTest {

	public static final double delta = 0.1d;

	@Before
	public void setUp() throws Exception {}

	@Test
	public void testYawConversion() {
		Vec3 yaw0 = Vec3.createVectorHelper(0, 0, 1);
		Vec3 yaw0_1 = Vec3.createVectorHelper(-0.2158149778842926, -0.14341038465499878, 0.9658312797546387);
		assertEquals(0, AIUtils.lookVecToYaw(yaw0), delta);
		assertEquals(12.600037, AIUtils.lookVecToYaw(yaw0_1), delta);

		Vec3 yaw90 = Vec3.createVectorHelper(-1, 0, 0);
		Vec3 yaw90_1 = Vec3.createVectorHelper(-0.9924389123916626, -0.10707709938287735, -0.05982581153512001);
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
