package mhfc.net.common.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.client.particle.paint.ParticlePaintEmitter;
import mhfc.net.common.core.MHFCMobList;
import mhfc.net.common.entity.creature.*;
import mhfc.net.common.entity.creature.incomplete.AkuraVashimu;
import mhfc.net.common.entity.creature.incomplete.GreatJaggi;
import mhfc.net.common.entity.creature.npc.NPCQuestGiver;
import mhfc.net.common.entity.fx.FXDeviljhoLaser;
import mhfc.net.common.entity.fx.FXFlashbomb;
import mhfc.net.common.entity.projectile.ProjectileArrow;
import mhfc.net.common.entity.projectile.ProjectileBlock;
import mhfc.net.common.entity.projectile.ProjectileBullet;
import mhfc.net.common.entity.projectile.ProjectilePaintball;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.util.services.IServiceKey;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.ArrayList;
import java.util.List;

public class MHFCEntityRegistry {
	public static void staticInit() {}

	private static final IServiceKey<MHFCEntityRegistry> serviceAccess = RegistryWrapper
			.registerService("entity registry", MHFCEntityRegistry::new, MHFCMain.initPhase);

	private int entityID = 0;
	private final List<Class<? extends Entity>> registeredMobs = new ArrayList<>();
	private final List<Class<? extends Entity>> registeredProjectiles = new ArrayList<>();

	
	public final int gargwaID;
	public final int delexID;
	public final int tigrexID;
	public final int kirinID;
	public final int rathalosID;
	public final int greatjaggiID;
	public final int deviljhoID;
	public final int nargacugaID;
	public final int barrothID;
	
	public final int lagiacrusID;
	
	
	// NEW MONSTER 1.12
	public final int akuraVashimuID;

	public final int questGiverID;

	public final int projectileBlockID;
	public final int deviljholaserbreathID;
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
		 * 
		 * 1.12.2 - READDED
		 * In order to compensate in the rework for quest worlds.
		 * 
		 */
		
		gargwaID = getMobID(Gargwa.class, ResourceInterface.mob_gagua_name, ItemColor.YELLOW,ItemColor.MAGNTA);
		delexID = getMobID(Delex.class, ResourceInterface.mob_delex_name, ItemColor.GREEN, ItemColor.YELLOW);
		tigrexID = getMobID(Tigrex.class, ResourceInterface.mob_tigrex_name, ItemColor.BLUE, ItemColor.YELLOW);
		greatjaggiID = getMobID(GreatJaggi.class,ResourceInterface.mob_greatjaggi_name,ItemColor.BLUE, ItemColor.CYAN);
		barrothID = getMobID(Barroth.class, ResourceInterface.mob_barroth_name, ItemColor.BROWN,ItemColor.GREEN);
		rathalosID = getMobID(Rathalos.class, ResourceInterface.mob_rathalos_name, ItemColor.RED, ItemColor.YELLOW);
		akuraVashimuID = getMobID(AkuraVashimu.class, ResourceInterface.akuravashimu_name, ItemColor.BLACK, ItemColor.CYAN);
		nargacugaID = getMobID(Nargacuga.class, ResourceInterface.mob_nargacuga_name, ItemColor.BLACK, ItemColor.RED);
		lagiacrusID = getMobID(Lagiacrus.class, ResourceInterface.mob_lagiacrus_name, ItemColor.CYAN, ItemColor.BLUE);
		deviljhoID = getMobID(Deviljho.class, ResourceInterface.mob_deviljho_name, ItemColor.GREEN, ItemColor.BROWN);
		kirinID = getMobID(Kirin.class, ResourceInterface.mob_kirin_name, ItemColor.WHITE, ItemColor.CYAN);
		
		questGiverID = getMobID(NPCQuestGiver.class, ResourceInterface.mob_questGiver_name,ItemColor.BLUE, ItemColor.BROWN);

		
		deviljholaserbreathID = getProjectileID(FXDeviljhoLaser.class, ResourceInterface.entity_deviljhobeam2_name);
		projectileBlockID = getProjectileID(ProjectileBlock.class, ResourceInterface.entity_tigrexBlock_name);
		bulletID = getProjectileID(ProjectileBullet.class, ResourceInterface.entity_bullet_name);
		flashbombID = getProjectileID(
				FXFlashbomb.class,
				ResourceInterface.entity_flashbomb_name,
				(int) FXFlashbomb.REACH);

		paintballID = getProjectileID(ProjectilePaintball.class, ResourceInterface.entity_paintball_name);

		paintemitterID = getMobID(ParticlePaintEmitter.class, ResourceInterface.mob_paint_emitter_name);
		arrowID = getProjectileID(ProjectileArrow.class, ResourceInterface.projectile_wyverniaarrow_name);
		breatheID = getProjectileID(FXDeviljhoLaser.class, ResourceInterface.projectile_wyverniaarrow_name);

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
