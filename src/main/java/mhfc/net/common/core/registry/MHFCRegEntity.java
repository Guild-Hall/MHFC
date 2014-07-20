package mhfc.net.common.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.common.entity.mob.EntityKirin;
import mhfc.net.common.entity.mob.EntityPopo;
import mhfc.net.common.entity.mob.EntityRathalos;
import mhfc.net.common.entity.mob.EntityTest;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityTigrexBlock;
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

		getMobID(EntityPopo.class, "popo");
		getMobID(EntityTigrex.class, "tigrex");
		getMobID(EntityKirin.class, "kirin");
		getMobID(EntityRathalos.class, "rathalos");
		getMobID(EntityTest.class, "test");
	}

	public static void registerOthers() {

		getEntityID(EntityTigrexBlock.class, "TigrexBlock");
		getEntityID(EntityRathalosFireball.class, "Fireball");
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
