package mhfc.net.common.core.registry;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.MHFCMobList;
import mhfc.net.common.entity.monster.EntityBarroth;
import mhfc.net.common.entity.monster.EntityDelex;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.monster.EntityGargwa;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.entity.monster.EntityTigrex;
import mhfc.net.common.entity.monster.wip.EntityGreatJaggi;
import mhfc.net.common.entity.monster.wip.EntityKirin;
import mhfc.net.common.entity.monster.wip.EntityLagiacrus;
import mhfc.net.common.entity.particle.EntityPaintParticleEmitter;
import mhfc.net.common.entity.projectile.EntityDeviljhoLaserBeam;
import mhfc.net.common.entity.projectile.EntityBullet;
import mhfc.net.common.entity.projectile.EntityFlashBomb;
import mhfc.net.common.entity.projectile.EntityPaintball;
import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import mhfc.net.common.entity.quests.EntityQuestGiver;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.util.services.IServiceKey;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class MHFCEntityRegistry {
	public static void staticInit() {}

	private static final IServiceKey<MHFCEntityRegistry> serviceAccess = RegistryWrapper
			.registerService("entity registry", MHFCEntityRegistry::new, MHFCMain.initPhase);

	private int entityID = 0;
	private final List<Class<? extends Entity>> registeredMobs = new ArrayList<>();
	private final List<Class<? extends Entity>> registeredProjectiles = new ArrayList<>();

	public final int tigrexID;
	public final int kirinID;
	//public final int rathalosID;
	public final int greatjaggiID;
	public final int deviljhoID;
	public final int nargacugaID;
	public final int barrothID;
	public final int delexID;
	//public final int giapreyID;
	//public final int ukanlosID;
	public final int lagiacrusID;
	public final int gargwaID;

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
		
		/***
		 * 
		 * 30/04/2017
		 * 
		 * Monsters ( boss and non-boss) will no longer have egg info for registry. Its 
		 * because they are now serve as Quest Monsters and will be restricted to spawn
		 * in Overworld. However for spotlights you can still summon them via comman
		 * " /summon mhfc:monstername "
		 * ex: /summon mhfc:tigrex
		 * 
		 * The egg registry or the additional argument for getMobID method will serve for the
		 * NPCs i will add in the future so it is not deprecated. ~Heltrato
		 * 
		 */
		
		// popoID = getMobID(EntityPopo.class, MHFCReference.mob_popo_name);
		tigrexID = getMobID(EntityTigrex.class, ResourceInterface.mob_tigrex_name);
		kirinID = getMobID(EntityKirin.class, ResourceInterface.mob_kirin_name);
		//rathalosID = getMobID(EntityRathalos.class, ResourceInterface.mob_rathalos_name);
		greatjaggiID = getMobID(EntityGreatJaggi.class,ResourceInterface.mob_greatjaggi_name);
		deviljhoID = getMobID(EntityDeviljho.class, ResourceInterface.mob_deviljho_name);
		nargacugaID = getMobID(EntityNargacuga.class, ResourceInterface.mob_nargacuga_name);
		questGiverID = getMobID(EntityQuestGiver.class, ResourceInterface.mob_questGiver_name);
		barrothID = getMobID(EntityBarroth.class, ResourceInterface.mob_barroth_name);
		delexID = getMobID(EntityDelex.class, ResourceInterface.mob_delex_name);
		//giapreyID = getMobID(EntityGiaprey.class, MHFCReference.mob_giaprey_name, 0x6f41512, 0x654321);
		//ukanlosID = getMobID(EntityUkanlos.class, MHFCReference.mob_ukanlos_name, 0x33333333, 0x654321);
		lagiacrusID = getMobID(EntityLagiacrus.class, ResourceInterface.mob_lagiacrus_name);
		gargwaID = getMobID(EntityGargwa.class, ResourceInterface.mob_gagua_name);

		

		projectileBlockID = getProjectileID(EntityProjectileBlock.class, ResourceInterface.entity_tigrexBlock_name);
		bulletID = getProjectileID(EntityBullet.class, ResourceInterface.entity_bullet_name);
		rathalosFireballID = getProjectileID(
				EntityRathalosFireball.class,
				ResourceInterface.entity_rathalosFireball_name);
		flashbombID = getProjectileID(
				EntityFlashBomb.class,
				ResourceInterface.entity_flashbomb_name,
				(int) EntityFlashBomb.REACH);

		paintballID = getProjectileID(EntityPaintball.class, ResourceInterface.entity_paintball_name);

		paintemitterID = getMobID(EntityPaintParticleEmitter.class, ResourceInterface.mob_paint_emitter_name);
		arrowID = getProjectileID(EntityWyverniaArrow.class, ResourceInterface.projectile_wyverniaarrow_name);
		breatheID = getProjectileID(EntityDeviljhoLaserBeam.class, ResourceInterface.projectile_wyverniaarrow_name);

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
		ResourceLocation entityId = new ResourceLocation(ResourceInterface.main_modid, name);
		EntityRegistry.registerModEntity(entityId, clazz, name, monsterID, MHFCMain.instance(), 64, 1, true);
		registeredMobs.add(clazz);
		MHFCMobList.addMapping(clazz, entityId, monsterID);
		return monsterID;
	}

	private int getMobID(Class<? extends Entity> clazz, String name, int foreground, int background) {
		if (isRegistered(clazz)) {
			return -1;
		}
		int monsterID = getMobID();
		ResourceLocation entityId = new ResourceLocation(ResourceInterface.main_modid, name);
		EntityRegistry.registerModEntity(entityId, clazz, name, monsterID, MHFCMain.instance(), 64, 1, true);
		registeredMobs.add(clazz);
		MHFCMobList.addMapping(clazz, entityId, monsterID, foreground, background);
		return monsterID;
	}

	@SuppressWarnings("unused")
	private int getMobID(Class<? extends Entity> clazz, String name, ItemColor foreground, ItemColor background) {
		return getMobID(clazz, name, foreground.getRGB(), background.getRGB());
	}

	private int getProjectileID(Class<? extends Entity> clazz, String name) {
		if (isRegistered(clazz)) {
			return -1;
		}
		int projectileID = getProjID();
		ResourceLocation entityId = new ResourceLocation(ResourceInterface.main_modid, name);
		EntityRegistry.registerModEntity(entityId, clazz, name, projectileID, MHFCMain.instance(), 64, 10, true);
		registeredProjectiles.add(clazz);
		return projectileID;
	}

	private int getProjectileID(Class<? extends Entity> clazz, String name, int customTrackingRange) {
		if (isRegistered(clazz)) {
			return -1;
		}
		int projectileID = getProjID();
		ResourceLocation entityId = new ResourceLocation(ResourceInterface.main_modid, name);
		EntityRegistry.registerModEntity(
				entityId,
				clazz,
				name,
				projectileID,
				MHFCMain.instance(),
				customTrackingRange,
				10,
				true);
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
