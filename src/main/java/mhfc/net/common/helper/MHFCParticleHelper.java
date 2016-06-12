package mhfc.net.common.helper;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.common.entity.particle.EntitySKirinFX;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;

public class MHFCParticleHelper {

	public static Minecraft minecraft = Minecraft.getMinecraft();
	private EntityFX entityfx = null;

	//TODO will be moved / removed soon.
	public static void spawnParticle(String string, double x, double y, double z , double fallx , double fally, double fallz) {

		EntityFX entityfx = null;

		Random rand = new Random();

		if (string.equals("kirinS"))
        {
            entityfx = new EntitySKirinFX(minecraft.theWorld, x, y, z, fallx, fally, fallz);
        }
	}

	@SideOnly(Side.CLIENT)
	public static void spawnParticleFromEntity(EntityFX entity) {
		Minecraft.getMinecraft().effectRenderer.addEffect(entity);

	}



}
