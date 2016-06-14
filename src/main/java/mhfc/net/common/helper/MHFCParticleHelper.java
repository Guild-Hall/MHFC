package mhfc.net.common.helper;

import mhfc.net.common.entity.particle.EntitySKirinFX;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;

public class MHFCParticleHelper {
	
	public static Minecraft minecraft = Minecraft.getMinecraft();

	//TODO will be moved / removed soon.
	public static void spawnParticle(String string, double x, double y, double z , double fallx , double fally, double fallz) {
		
		@SuppressWarnings("unused")
		EntityFX entityfx;
		
		
		if (string.equals("kirinS"))
        {
            entityfx = new EntitySKirinFX(minecraft.theWorld, x, y, z, fallx, fally, fallz);
        }
	}
	
	
	
}
