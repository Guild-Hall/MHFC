package mhfc.net.common.core.registry;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.MHFCMobList;
import mhfc.net.common.entity.mob.EntityBarroth;
import mhfc.net.common.entity.mob.EntityDeviljho;
import mhfc.net.common.entity.mob.EntityGreatJaggi;
import mhfc.net.common.entity.mob.EntityNargacuga;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.projectile.EntityBullet;
import mhfc.net.common.entity.projectile.EntityFlashBomb;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityTigrexBlock;
import mhfc.net.common.entity.quests.EntityQuestGiver;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.registry.EntityRegistry;

public class MHFCEntityRegistry {
	private static int entityID = 0;
	private static final MHFCMain mod;
	private static final List<Class<? extends Entity>> registeredMobs;
	private static final List<Class<? extends Entity>> registeredProjectiles;

	// public static final int popoID;
	public static final int tigrexID;
	// public static final int kirinID;
//	public static final int rathalosID;
	public static final int greatjaggiID;
	public static int deviljhoID;
	public static int nargacugaID;
	public static final int barrothID;
	
	public static final int questGiverID;

	public static final int tigrexBlockID;
	public static final int rathalosFireballID;
	public static final int bulletID;
	public static final int flashbombID;

	static {
		MHFCMain.checkPreInitialized();
		mod = MHFCMain.instance;
		registeredMobs = new ArrayList<>();
		registeredProjectiles = new ArrayList<>();

		// popoID = getMobID(EntityPopo.class, MHFCReference.mob_popo_name,
		// 0xf8248234, 0x193192);
		tigrexID = getMobID(EntityTigrex.class, MHFCReference.mob_tigrex_name,0xfff432e3, 0x1020394f);
		// kirinID = getMobID(EntityKirin.class, MHFCReference.mob_kirin_name, 0xfff85814, 0xff851f15);
		/// rathalosID = getMobID(EntityRathalos.class,	MHFCReference.mob_rathalos_name, 0xff749819, 0xf838818);
		greatjaggiID = getMobID(EntityGreatJaggi.class, MHFCReference.mob_greatjaggi_name,0xff119f91, 0xff929ff);
		deviljhoID = getMobID(EntityDeviljho.class, MHFCReference.mob_deviljho_name,0x6ff81ff, 0xff11d830);
//		nargacugaID = getMobID(EntityNargacuga.class, MHFCReference.mob_nargacuga_name,	0xf351631, 0x516f13f);
		barrothID = getMobID(EntityBarroth.class, MHFCReference.mob_barroth_name,0x6ffffff, 0x654321);
		questGiverID = getMobID(EntityQuestGiver.class,MHFCReference.mob_questGiver_name);

		tigrexBlockID = getProjectileID(EntityTigrexBlock.class,
			MHFCReference.entity_tigrexBlock_name);
		bulletID = getProjectileID(EntityBullet.class,
			MHFCReference.entity_bullet_name);
		rathalosFireballID = getProjectileID(EntityRathalosFireball.class,
			MHFCReference.entity_rathalosFireball_name);
		flashbombID = getProjectileID(EntityFlashBomb.class,
			MHFCReference.entity_flashbomb_name, EntityFlashBomb.FALL_OFF_END);
	}

	public static void init() {
	}

	/**
	 * returns a new (unique) mob id for the clazz provided. If the entity clazz
	 * is already registered this simply returns <code>-1</code>.
	 *
	 * @param clazz
	 * @param name
	 * @return
	 */
	private static int getMobID(Class<? extends Entity> clazz, String name) {
		if (isRegistered(clazz))
			return -1;
		int monsterID = getMobID();
		EntityRegistry.registerModEntity(clazz, name, monsterID, mod, 64, 1,
			true);
		registeredMobs.add(clazz);
		MHFCMobList.addMapping(clazz, name, monsterID);
		return monsterID;
	}

	private static int getMobID(Class<? extends Entity> clazz, String name,
		int foreground, int background) {
		if (isRegistered(clazz))
			return -1;
		int monsterID = getMobID();
		EntityRegistry.registerModEntity(clazz, name, monsterID, mod, 64, 1,
			true);
		registeredMobs.add(clazz);
		MHFCMobList.addMapping(clazz, name, monsterID, foreground, background);
		return monsterID;
	}

	private static int getProjectileID(Class<? extends Entity> clazz,
		String name) {
		if (isRegistered(clazz))
			return -1;
		int projectileID = getProjID();
		EntityRegistry.registerModEntity(clazz, name, projectileID, mod, 64,
			10, true);
		registeredProjectiles.add(clazz);
		return projectileID;
	}

	private static int getProjectileID(Class<? extends Entity> clazz,
		String name, int customTrackingRange) {
		if (isRegistered(clazz))
			return -1;
		int projectileID = getProjID();
		EntityRegistry.registerModEntity(clazz, name, projectileID, mod,
			customTrackingRange, 10, true);
		registeredProjectiles.add(clazz);
		return projectileID;
	}

	private static int getMobID() {
		return entityID++;
	}

	private static int getProjID() {
		return entityID++;
	}

	private static boolean isRegistered(Class<?> clazz) {
		return registeredMobs.contains(clazz)
			|| registeredProjectiles.contains(clazz);
	}

}
