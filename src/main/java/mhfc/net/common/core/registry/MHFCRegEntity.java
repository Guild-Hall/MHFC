package mhfc.net.common.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.common.entity.mob.EntityKirin;
import mhfc.net.common.entity.mob.EntityPopo;
import mhfc.net.common.entity.mob.EntityRathalos;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityTigrexBlock;
import mhfc.net.common.entity.quests.EntityQuestGiver;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.registry.EntityRegistry;

public class MHFCRegEntity {

	public static int mobID = 300;
	public static int projID = 200;
	private static MHFCMain mod = MHFCMain.instance;

	public static void render() {

		registerMonster();
		registerOthers();

	}

	public static void registerMonster() {

		getMobID(EntityPopo.class, MHFCReference.mob_popo_name);
		getMobID(EntityTigrex.class, MHFCReference.mob_tigrex_name);
		getMobID(EntityKirin.class, MHFCReference.mob_kirin_name);
		getMobID(EntityRathalos.class, MHFCReference.mob_rathalos_name);
		getMobID(EntityQuestGiver.class, MHFCReference.mob_questGiver_name);
	}

	public static void registerOthers() {

		getEntityID(EntityTigrexBlock.class,
				MHFCReference.entity_tigrexBlock_name);
		getEntityID(EntityRathalosFireball.class,
				MHFCReference.entity_rathalosFireball_name);
	}

	private static void getMobID(Class<? extends Entity> entityClass,
			String name) {
		int monsterID = getMobID();
		EntityRegistry.registerModEntity(entityClass, name, monsterID, mod, 64,
				1, true);

	}

	private static void getEntityID(Class<? extends Entity> clazz, String name) {
		int projectileID = getProjID();
		EntityRegistry.registerModEntity(clazz, name, projectileID, mod, 64,
				10, true);
	}

	public static int getMobID() {
		return mobID++;
	}

	public static int getProjID() {
		return projID++;
	}

}
