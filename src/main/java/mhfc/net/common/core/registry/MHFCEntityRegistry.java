package mhfc.net.common.core.registry;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.EntityRegistry;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.MHFCMobList;
import mhfc.net.common.entity.monster.EntityBarroth;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.monster.EntityGreatJaggi;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.entity.monster.EntityRathalos;
import mhfc.net.common.entity.monster.EntityTigrex;
import mhfc.net.common.entity.particle.EntityPaintParticleEmitter;
import mhfc.net.common.entity.projectile.EntityBreathe;
import mhfc.net.common.entity.projectile.EntityBullet;
import mhfc.net.common.entity.projectile.EntityFlashBomb;
import mhfc.net.common.entity.projectile.EntityPaintball;
import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import mhfc.net.common.entity.quests.EntityQuestGiver;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.util.services.IServiceKey;
import net.minecraft.entity.Entity;

public class MHFCEntityRegistry {
	public static void staticInit() {}

	private static final IServiceKey<MHFCEntityRegistry> serviceAccess = RegistryWrapper
			.registerService("entity registry", MHFCEntityRegistry::new, MHFCMain.initPhase);

	private int entityID = 0;
	private final List<Class<? extends Entity>> registeredMobs = new ArrayList<>();
	private final List<Class<? extends Entity>> registeredProjectiles = new ArrayList<>();

	public final int tigrexID;
	//public final int kirinID;
	public final int rathalosID;
	public final int greatjaggiID;
	public final int deviljhoID;
	public final int nargacugaID;
	public final int barrothID;
	//public final int delexID;
	//public final int giapreyID;
	//public final int ukanlosID;
	public final int lagiacrusID;
	//public final int gargwaID;

	public final int questGiverID;

	public final int projectileBlockID;
	public final int rathalosFireballID;
	public final int breatheID;
	public final int bulletID;
	public final int flashbombID;
	public final int paintballID;
	public final int paintemitterID;
	public final int arrowID;

	protected MHFCEntityRegistry() {
		// popoID = getMobID(EntityPopo.class, MHFCReference.mob_popo_name,
		// 0xf8248234, 0x193192);
		tigrexID = getMobID(EntityTigrex.class, MHFCReference.mob_tigrex_name, ItemColor.YELLOW, ItemColor.LIBLUE);
		//kirinID = getMobID(EntityKirin.class, MHFCReference.mob_kirin_name, 0xfff85814, 0xff851f15);
		rathalosID = getMobID(EntityRathalos.class,	MHFCReference.mob_rathalos_name, 0xff749819, 0xf838818);
		greatjaggiID = getMobID(EntityGreatJaggi.class,	MHFCReference.mob_greatjaggi_name,ItemColor.PURPLE,ItemColor.PINK);
		deviljhoID = getMobID(EntityDeviljho.class, MHFCReference.mob_deviljho_name, ItemColor.GREEN, ItemColor.SILVER);
		nargacugaID = getMobID(EntityNargacuga.class, MHFCReference.mob_nargacuga_name, 0xf351631, 0x516f13f);
		barrothID = getMobID(EntityBarroth.class, MHFCReference.mob_barroth_name, ItemColor.ORANGE, ItemColor.GRAY);
		//delexID = getMobID(EntityDelex.class, MHFCReference.mob_delex_name, 0x6f33333, 0x654321);
		//giapreyID = getMobID(EntityGiaprey.class, MHFCReference.mob_giaprey_name, 0x6f41512, 0x654321);
		//ukanlosID = getMobID(EntityUkanlos.class, MHFCReference.mob_ukanlos_name, 0x33333333, 0x654321);
		lagiacrusID = getMobID(EntityLagiacrus.class, MHFCReference.mob_lagiacrus_name, 0x6fff512, 0x6ff14f1);
		//gargwaID = getMobID(EntityGargwa.class, MHFCReference.mob_gagua_name, 0x319292, 0x2187ff20);

		questGiverID = getMobID(EntityQuestGiver.class, MHFCReference.mob_questGiver_name);

		projectileBlockID = getProjectileID(EntityProjectileBlock.class, MHFCReference.entity_tigrexBlock_name);
		bulletID = getProjectileID(EntityBullet.class, MHFCReference.entity_bullet_name);
		rathalosFireballID = getProjectileID(EntityRathalosFireball.class, MHFCReference.entity_rathalosFireball_name);
		flashbombID = getProjectileID(
				EntityFlashBomb.class,
				MHFCReference.entity_flashbomb_name,
				(int) EntityFlashBomb.REACH);

		paintballID = getProjectileID(EntityPaintball.class, MHFCReference.entity_paintball_name);

		paintemitterID = getMobID(EntityPaintParticleEmitter.class, MHFCReference.mob_paint_emitter_name);
		arrowID = getProjectileID(EntityWyverniaArrow.class, MHFCReference.projectile_wyverniaarrow_name);
		breatheID = getProjectileID(EntityBreathe.class, MHFCReference.projectile_wyverniaarrow_name);

		MHFCMain.logger().info("Monsters registered");
	}

	/**
	 * returns a new (unique) mob id for the clazz provided. If the entity clazz is already registered this simply
	 * returns <code>-1</code>.
	 *
	 * @param clazz
	 * @param name
	 * @return
	 */
	private int getMobID(Class<? extends Entity> clazz, String name) {
		if (isRegistered(clazz)) {
			return -1;
		}
		int monsterID = getMobID();
		EntityRegistry.registerModEntity(clazz, name, monsterID, MHFCMain.instance(), 64, 1, true);
		registeredMobs.add(clazz);
		MHFCMobList.addMapping(clazz, name, monsterID);
		return monsterID;
	}

	private int getMobID(Class<? extends Entity> clazz, String name, int foreground, int background) {
		if (isRegistered(clazz)) {
			return -1;
		}
		int monsterID = getMobID();
		EntityRegistry.registerModEntity(clazz, name, monsterID, MHFCMain.instance(), 64, 1, true);
		registeredMobs.add(clazz);
		MHFCMobList.addMapping(clazz, name, monsterID, foreground, background);
		return monsterID;
	}

	private int getMobID(Class<? extends Entity> clazz, String name, ItemColor foreground, ItemColor background) {
		return getMobID(clazz, name, foreground.getRGB(), background.getRGB());
	}

	private int getProjectileID(Class<? extends Entity> clazz, String name) {
		if (isRegistered(clazz)) {
			return -1;
		}
		int projectileID = getProjID();
		EntityRegistry.registerModEntity(clazz, name, projectileID, MHFCMain.instance(), 64, 10, true);
		registeredProjectiles.add(clazz);
		return projectileID;
	}

	private int getProjectileID(Class<? extends Entity> clazz, String name, int customTrackingRange) {
		if (isRegistered(clazz)) {
			return -1;
		}
		int projectileID = getProjID();
		EntityRegistry.registerModEntity(clazz, name, projectileID, MHFCMain.instance(), customTrackingRange, 10, true);
		registeredProjectiles.add(clazz);
		return projectileID;
	}

	private int getMobID() {
		return entityID++;
	}

	private int getProjID() {
		return entityID++;
	}

	private boolean isRegistered(Class<?> clazz) {
		return registeredMobs.contains(clazz) || registeredProjectiles.contains(clazz);
	}

	public static MHFCEntityRegistry getRegistry() {
		return serviceAccess.getService();
	}
}
