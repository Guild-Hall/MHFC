package mhfc.net.common.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.common.entity.mob.EntityKirin;
import mhfc.net.common.entity.mob.EntityPopo;
import mhfc.net.common.entity.mob.EntityRathalos;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityTigrexBlock;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.registry.EntityRegistry;

public class MHFCEntityRegistry {
	private static int entityID = 0;
	private static final MHFCMain mod;

	public static final int popoID;
	public static final int tigrexID;
	public static final int kirinID;
	public static final int rathalosID;

	public static final int tigrexBlockID;
	public static final int rathalosFireballID;

	static {
		MHFCMain.checkPreInitialized();
		mod = MHFCMain.instance;

		popoID = getMobID(EntityPopo.class, MHFCReference.mob_popo_name);
		tigrexID = getMobID(EntityTigrex.class, MHFCReference.mob_tigrex_name);
		kirinID = getMobID(EntityKirin.class, MHFCReference.mob_kirin_name);
		rathalosID = getMobID(EntityRathalos.class,
				MHFCReference.mob_rathalos_name);

		tigrexBlockID = getProjectileID(EntityTigrexBlock.class,
				MHFCReference.entity_tigrexBlock_name);
		rathalosFireballID = getProjectileID(EntityRathalosFireball.class,
				MHFCReference.entity_rathalosFireball_name);
	}

	public static void init() {}

	private static int getMobID(Class<? extends Entity> clazz, String name) {
		int monsterID = getMobID();
		EntityRegistry.registerModEntity(clazz, name, monsterID, mod, 64, 1,
				true);
		return monsterID;
	}

	private static int getProjectileID(Class<? extends Entity> clazz,
			String name) {
		int projectileID = getProjID();
		EntityRegistry.registerModEntity(clazz, name, projectileID, mod, 64,
				10, true);
		return projectileID;
	}

	private static int getMobID() {
		return entityID++;
	}

	private static int getProjID() {
		return entityID++;
	}

}
