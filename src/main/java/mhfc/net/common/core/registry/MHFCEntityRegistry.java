package mhfc.net.common.core.registry;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.EntityRegistry;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.MHFCMobList;
import mhfc.net.common.entity.monster.EntityBarroth;
import mhfc.net.common.entity.monster.EntityDelex;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.monster.EntityGargwa;
import mhfc.net.common.entity.monster.EntityGreatJaggi;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.entity.monster.EntityRathalos;
import mhfc.net.common.entity.monster.EntityTigrex;
import mhfc.net.common.entity.projectile.EntityBreathe;
import mhfc.net.common.entity.projectile.EntityBullet;
import mhfc.net.common.entity.projectile.EntityFlashBomb;
import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import mhfc.net.common.entity.quests.EntityQuestGiver;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.Entity;

public class MHFCEntityRegistry {
	private static int entityID = 0;
	private static final MHFCMain mod;
	private static final List<Class<? extends Entity>> registeredMobs;
	private static final List<Class<? extends Entity>> registeredProjectiles;

	public static final int tigrexID;
	public static final int rathalosID;
	public static final int greatjaggiID;
	public static final int deviljhoID;
	public static final int nargacugaID;
	public static final int barrothID;
	public static final int delexID;
	
	public static final int lagiacrusID;

	public static final int gargwaID;
	
	
	public static int giapreyID;
	public static int ukanlosID;
	public static int kirinID;
	
	
	public static final int questGiverID;

	public static final int projectileBlockID;
	public static final int rathalosFireballID;
	public static final int breatheID;
	public static final int bulletID;
	public static final int flashbombID;
	public static final int arrowID;

	static {
		MHFCMain.checkPreInitialized();
		mod = MHFCMain.instance;
		registeredMobs = new ArrayList<>();
		registeredProjectiles = new ArrayList<>();

		// popoID = getMobID(EntityPopo.class, MHFCReference.mob_popo_name,
		// 0xf8248234, 0x193192);
		//kirinID = getMobID(EntityKirin.class, MHFCReference.mob_kirin_name, 0xfff85814, 0xff851f15);
		tigrexID = getMobID(EntityTigrex.class, MHFCReference.mob_tigrex_name, 0xfff432e3, 0x1020394f);
		rathalosID = getMobID(EntityRathalos.class, MHFCReference.mob_rathalos_name, 0xff749819, 0xf838818);
		nargacugaID = getMobID(EntityNargacuga.class, MHFCReference.mob_nargacuga_name, 0xf351631, 0x516f13f);
		greatjaggiID = getMobID(EntityGreatJaggi.class, MHFCReference.mob_greatjaggi_name, 0xff119f91, 0xff929ff);
		deviljhoID = getMobID(EntityDeviljho.class, MHFCReference.mob_deviljho_name, 0x6ff81ff, 0xff11d830);
		barrothID = getMobID(EntityBarroth.class, MHFCReference.mob_barroth_name, 0x6ffffff, 0x654321);
		//ukanlosID = getMobID(EntityUkanlos.class, MHFCReference.mob_ukanlos_name, 0x33333333, 0x654321);
		delexID = getMobID(EntityDelex.class, MHFCReference.mob_delex_name, 0x6f33333, 0x654321);
		//giapreyID = getMobID(EntityGiaprey.class, MHFCReference.mob_giaprey_name, 0x6f41512, 0x654321);
		lagiacrusID = getMobID(EntityLagiacrus.class, MHFCReference.mob_lagiacrus_name, 0x6fff512, 0x6ff14f1);
		gargwaID = getMobID(EntityGargwa.class, MHFCReference.mob_gagua_name, 0x319292, 0x2187ff20);
		questGiverID = getMobID(EntityQuestGiver.class, MHFCReference.mob_questGiver_name);

		projectileBlockID = getProjectileID(EntityProjectileBlock.class, MHFCReference.entity_tigrexBlock_name);
		bulletID = getProjectileID(EntityBullet.class, MHFCReference.entity_bullet_name);
		rathalosFireballID = getProjectileID(EntityRathalosFireball.class, MHFCReference.entity_rathalosFireball_name);
		flashbombID = getProjectileID(
				EntityFlashBomb.class,
				MHFCReference.entity_flashbomb_name,
				EntityFlashBomb.FALL_OFF_END);
		arrowID = getProjectileID(EntityWyverniaArrow.class, MHFCReference.projectile_wyverniaarrow_name);
		breatheID = getProjectileID(EntityBreathe.class, MHFCReference.projectile_wyverniaarrow_name);
	}

	public static void init() {}

	/**
	 * returns a new (unique) mob id for the clazz provided. If the entity clazz is already registered this simply
	 * returns <code>-1</code>.
	 *
	 * @param clazz
	 * @param name
	 * @return
	 */
	private static int getMobID(Class<? extends Entity> clazz, String name) {
		if (MHFCEntityRegistry.isRegistered(clazz)) {
			return -1;
		}
		int monsterID = MHFCEntityRegistry.getMobID();
		EntityRegistry.registerModEntity(clazz, name, monsterID, mod, 64, 1, true);
		registeredMobs.add(clazz);
		MHFCMobList.addMapping(clazz, name, monsterID);
		return monsterID;
	}

	private static int getMobID(Class<? extends Entity> clazz, String name, int foreground, int background) {
		if (MHFCEntityRegistry.isRegistered(clazz)) {
			return -1;
		}
		int monsterID = MHFCEntityRegistry.getMobID();
		EntityRegistry.registerModEntity(clazz, name, monsterID, mod, 64, 1, true);
		registeredMobs.add(clazz);
		MHFCMobList.addMapping(clazz, name, monsterID, foreground, background);
		return monsterID;
	}

	private static int getProjectileID(Class<? extends Entity> clazz, String name) {
		if (MHFCEntityRegistry.isRegistered(clazz)) {
			return -1;
		}
		int projectileID = MHFCEntityRegistry.getProjID();
		EntityRegistry.registerModEntity(clazz, name, projectileID, mod, 64, 10, true);
		registeredProjectiles.add(clazz);
		return projectileID;
	}

	private static int getProjectileID(Class<? extends Entity> clazz, String name, int customTrackingRange) {
		if (MHFCEntityRegistry.isRegistered(clazz)) {
			return -1;
		}
		int projectileID = MHFCEntityRegistry.getProjID();
		EntityRegistry.registerModEntity(clazz, name, projectileID, mod, customTrackingRange, 10, true);
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
		return registeredMobs.contains(clazz) || registeredProjectiles.contains(clazz);
	}

}
