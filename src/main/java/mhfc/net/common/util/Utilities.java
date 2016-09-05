package mhfc.net.common.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.IntFunction;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.entity.projectile.EntityLightning;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Utilities {
	private static Random rand = new Random();

	public static final float mhfc_vanilla_size_x = 0.5F;
	public static final float mhfc_vanilla_size_y = 0.5F;

	public void addWeaponElementFX(EntityLivingBase entityLiving, ItemStack stack) {

	}

	/**
	 * For all nearby entities that are attacking the EntityLiving, resets the attack and revenge target
	 *
	 * @param living
	 */
	public static void removeAttackers(EntityLiving living) {
		List<EntityLiving> list = living.worldObj
				.getEntitiesWithinAABB(EntityLiving.class, living.getEntityBoundingBox().expand(16.0D, 10.0D, 16.0D));
		for (EntityLiving attacker : list) {
			if ((attacker != living) && (attacker.getAttackTarget() == living)) {
				attacker.setAttackTarget(null);
				attacker.setRevengeTarget(null);
			}
		}
	}

	// YES THIS IS useful for 1.9
	public static int countPlayers(WorldServer worldObj) {
		return worldObj.playerEntities.size();
	}

	public static void spawnLightnings(double Lx, double Ly, double Lz, int many, World world) {
		for (int i = 0; i < many; i++) {
			EntityLightning l = new EntityLightning(world);
			l.setPosition(Lx, Ly, Lz);
			world.spawnEntityInWorld(l);
		}
	}

	public static <T, R> T[] mapAll(Function<? super R, T> func, R[] holders, IntFunction<T[]> arrNew) {
		return Arrays.stream(holders).sequential().map(func).toArray(arrNew);
	}

	public static BufferedInputStream openEmbeddedResource(ResourceLocation location) throws IOException {
		String pathToRes = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath();
		InputStream instream = MHFCQuestBuildRegistry.class.getResourceAsStream(pathToRes);
		if (instream == null) {
			throw new IOException("File doesn't exist");
		}
		return new BufferedInputStream(instream);
	}

}
