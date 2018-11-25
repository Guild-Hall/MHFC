package mhfc.net.common.util;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

public class Deprecate {
	
	public Deprecate(){
	}
	
	// This being renamed deprecate because most methods here will be moved.

	public void addWeaponElementFX(EntityLivingBase entityLiving, ItemStack stack) {

	}

	/**
	 * For all nearby entities that are attacking the EntityLiving, resets the attack and revenge target
	 *
	 * @param living
	 */
	public static void removeAttackers(EntityLiving living) {
		List<EntityLiving> list = living.world
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


	public static <T, R> T[] mapAll(Function<? super R, T> func, R[] holders, IntFunction<T[]> arrNew) {
		return Arrays.stream(holders).sequential().map(func).toArray(arrNew);
	}

	public static BufferedInputStream openEmbeddedResource(ResourceLocation location) throws IOException {
		String pathToRes = "/assets/" + location.getNamespace() + "/" + location.getPath();
		InputStream instream = MHFCQuestBuildRegistry.class.getResourceAsStream(pathToRes);
		if (instream == null) {
			throw new IOException("File doesn't exist");
		}
		return new BufferedInputStream(instream);
	}

}
