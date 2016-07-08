package mhfc.net.common.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLLog;
import mhfc.net.MHFCMain;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class MHFCMobList {
	private static final Logger logger = MHFCMain.logger();;
	private static Map<String, Class<? extends Entity>> stringToClassMapping = new HashMap<>();
	private static Map<Class<? extends Entity>, String> classToStringMapping = new HashMap<>();
	private static Map<Integer, Class<? extends Entity>> IDtoClassMapping = new HashMap<>();
	private static Map<Class<? extends Entity>, Integer> classToIDMapping = new HashMap<>();
	private static Map<String, Integer> stringToIDMapping = new HashMap<>();

	private static Map<Integer, MHFCEggInfo> entityEggs = new LinkedHashMap<>();

	public static void addMapping(Class<? extends Entity> clazz, String name, int id) {
		stringToClassMapping.put(name, clazz);
		classToStringMapping.put(clazz, name);
		IDtoClassMapping.put(Integer.valueOf(id), clazz);
		classToIDMapping.put(clazz, Integer.valueOf(id));
		stringToIDMapping.put(name, Integer.valueOf(id));
	}

	public static void addMapping(
			Class<? extends Entity> clazz,
			String name,
			int id,
			int foregroundcolor,
			int backgroundcolor) {
		addMapping(clazz, name, id);
		entityEggs.put(Integer.valueOf(id), new MHFCEggInfo(id, foregroundcolor, backgroundcolor));
	}

	private static Entity createEntityByClass(Class<? extends Entity> oclass, World world) {
		if (oclass == null) {
			return null;
		}
		Entity entity = null;
		try {
			entity = oclass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return entity;
	}

	public static Entity createEntityByName(String name, World world) {
		Entity entity = createEntityByClass(getClassFromName(name), world);
		if (entity == null) {
			logger.warn("Skipping Entity with id " + name);
		}
		return entity;
	}

	public static Entity createEntityFromNBT(NBTTagCompound nbtTag, World world) {
		Class<? extends Entity> oclass = stringToClassMapping.get(nbtTag.getString("id"));

		Entity entity = createEntityByClass(oclass, world);
		if (entity == null) {
			logger.warn("Skipping Entity with id " + nbtTag.getString("id"));
			return null;
		}
		try {
			entity.readFromNBT(nbtTag);
		} catch (Exception e) {
			FMLLog.log(
					Level.ERROR,
					e,
					"An Entity %s(%s) has thrown an exception during loading, its state cannot be restored. Report this to the mod author",
					nbtTag.getString("id"),
					oclass.getName());
			entity = null;
		}

		return entity;
	}

	public static Entity createEntityByID(int par0, World par1World) {
		Entity entity = createEntityByClass(getClassFromID(par0), par1World);
		if (entity == null) {
			logger.warn("Skipping Entity with id " + par0);
		}
		return entity;
	}

	public static int getEntityID(Entity entity) {
		Class<? extends Entity> oclass = entity.getClass();
		return classToIDMapping.containsKey(oclass) ? classToIDMapping.get(oclass).intValue() : -1;
	}

	public static String getEntityString(Entity entity) {
		return classToStringMapping.get(entity.getClass());
	}

	public static Class<? extends Entity> getClassFromName(String name) {
		return stringToClassMapping.get(name);
	}

	public static Class<? extends Entity> getClassFromID(int id) {
		return IDtoClassMapping.get(Integer.valueOf(id));
	}

	public static String getStringFromID(int id) {
		Class<? extends Entity> oclass = getClassFromID(id);
		return oclass != null ? classToStringMapping.get(oclass) : null;
	}

	public static Set<String> nameset() {
		return Collections.unmodifiableSet(stringToIDMapping.keySet());
	}

	public static Map<Integer, MHFCEggInfo> registeredEggs() {
		return Collections.unmodifiableMap(entityEggs);
	}

	public static class MHFCEggInfo {
		/**
		 * The entityID of the spawned mob
		 */
		public final int spawnedID;
		/**
		 * Base color of the egg
		 */
		public final int primaryColor;
		/**
		 * Color of the egg spots
		 */
		public final int secondaryColor;
		public final StatBase field_151512_d;
		public final StatBase field_151513_e;

		public MHFCEggInfo(int par1, int par2, int par3) {
			this.spawnedID = par1;
			this.primaryColor = par2;
			this.secondaryColor = par3;
			this.field_151512_d = MHFCMobList.getKills(this);
			this.field_151513_e = MHFCMobList.getKilledBy(this);
		}
	}

	public static StatBase getKills(MHFCMobList.MHFCEggInfo eggInfo) {
		String s = MHFCMobList.getStringFromID(eggInfo.spawnedID);
		return s == null
				? null
				: (new StatBase(
						"stat.killEntity." + s,
						new ChatComponentTranslation(
								"stat.entityKill",
								new Object[] { new ChatComponentTranslation("entity." + s + ".name", new Object[0]) })))
										.registerStat();
	}

	public static StatBase getKilledBy(MHFCMobList.MHFCEggInfo eggInfo) {
		String s = MHFCMobList.getStringFromID(eggInfo.spawnedID);
		return s == null
				? null
				: (new StatBase(
						"stat.entityKilledBy." + s,
						new ChatComponentTranslation(
								"stat.entityKilledBy",
								new Object[] { new ChatComponentTranslation("entity." + s + ".name", new Object[0]) })))
										.registerStat();
	}
}
