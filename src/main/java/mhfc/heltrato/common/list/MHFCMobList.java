package mhfc.heltrato.common.list;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import mhfc.heltrato.common.core.registry.MHFCRegEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLLog;

public class MHFCMobList
{
    private static final Logger logger = LogManager.getLogger();
    public static Map stringToClassMapping = new HashMap();
    public static Map classToStringMapping = new HashMap();
    public static Map IDtoClassMapping = new HashMap();
    private static Map classToIDMapping = new HashMap();
    private static Map stringToIDMapping = new HashMap();
    public static HashMap entityEggs = new LinkedHashMap();
    private static final String __OBFID = "CL_00001538";

    public static void addMapping(Class par0Class, String par1Str, int par2)
    {
        stringToClassMapping.put(par1Str, par0Class);
        classToStringMapping.put(par0Class, par1Str);
        IDtoClassMapping.put(Integer.valueOf(par2), par0Class);
        classToIDMapping.put(par0Class, Integer.valueOf(par2));
        stringToIDMapping.put(par1Str, Integer.valueOf(par2));
    }

    public static void addMapping(Class par0Class, String par1Str, int par2, int par3, int par4)
    {
        addMapping(par0Class, par1Str, par2);
        entityEggs.put(Integer.valueOf(par2), new MHFCEggInfo(par2, par3, par4));
    }

    public static Entity createEntityByName(String par0Str, World par1World)
    {
        Entity entity = null;

        try
        {
            Class oclass = (Class)stringToClassMapping.get(par0Str);

            if (oclass != null)
            {
                entity = (Entity)oclass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par1World});
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return entity;
    }

    public static Entity createEntityFromNBT(NBTTagCompound par0NBTTagCompound, World par1World)
    {
        Entity entity = null;

        if ("Minecart".equals(par0NBTTagCompound.getString("id")))
        {
            switch (par0NBTTagCompound.getInteger("Type"))
            {
                case 0:
                    par0NBTTagCompound.setString("id", "MinecartRideable");
                    break;
                case 1:
                    par0NBTTagCompound.setString("id", "MinecartChest");
                    break;
                case 2:
                    par0NBTTagCompound.setString("id", "MinecartFurnace");
            }

            par0NBTTagCompound.removeTag("Type");
        }

        Class oclass = null;
        try
        {
            oclass = (Class)stringToClassMapping.get(par0NBTTagCompound.getString("id"));

            if (oclass != null)
            {
                entity = (Entity)oclass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par1World});
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        if (entity != null)
        {
            try
            {
                entity.readFromNBT(par0NBTTagCompound);
            }
            catch (Exception e)
            {
                FMLLog.log(Level.ERROR, e,
                        "An Entity %s(%s) has thrown an exception during loading, its state cannot be restored. Report this to the mod author",
                        par0NBTTagCompound.getString("id"), oclass.getName());
                entity = null;
            }
        }
        else
        {
            logger.warn("Skipping Entity with id " + par0NBTTagCompound.getString("id"));
        }

        return entity;
    }
    
    public static Entity createEntityByID(int par0, World par1World)
    {
        Entity entity = null;

        try
        {
            Class oclass = getClassFromID(par0);

            if (oclass != null)
            {
                entity = (Entity)oclass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par1World});
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        if (entity == null)
        {
            logger.warn("Skipping Entity with id " + par0);
        }

        return entity;
    }

    public static int getEntityID(Entity par0Entity)
    {
        Class oclass = par0Entity.getClass();
        return classToIDMapping.containsKey(oclass) ? ((Integer)classToIDMapping.get(oclass)).intValue() : 0;
    }

    /**
     * Return the class assigned to this entity ID.
     */
    public static Class getClassFromID(int par0)
    {
        return (Class)IDtoClassMapping.get(Integer.valueOf(par0));
    }

    /**
     * Gets the string representation of a specific entity.
     */
    public static String getEntityString(Entity par0Entity)
    {
        return (String)classToStringMapping.get(par0Entity.getClass());
    }

    /**
     * Finds the class using IDtoClassMapping and classToStringMapping
     */
    public static String getStringFromID(int par0)
    {
        Class oclass = getClassFromID(par0);
        return oclass != null ? (String)classToStringMapping.get(oclass) : null;
    }


    public static void func_151514_a() {}

    public static Set func_151515_b()
    {
        return Collections.unmodifiableSet(stringToIDMapping.keySet());
    }

    static
    {
    	int monsterID = MHFCRegEntity.getMobID();
    	
		
		
    }
    

    public static class MHFCEggInfo
        {
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
            private static final String __OBFID = "CL_00001539";

            public MHFCEggInfo(int par1, int par2, int par3)
            {
                this.spawnedID = par1;
                this.primaryColor = par2;
                this.secondaryColor = par3;
                this.field_151512_d = MHFCMobList.getx(this);
                this.field_151513_e = MHFCMobList.gety(this);
            }
        }
    
    public static StatBase getx(MHFCMobList.MHFCEggInfo p_151182_0_)
    {
        String s = MHFCMobList.getStringFromID(p_151182_0_.spawnedID);
        return s == null ? null : (new StatBase("stat.killEntity." + s, new ChatComponentTranslation("stat.entityKill", new Object[] {new ChatComponentTranslation("entity." + s + ".name", new Object[0])}))).registerStat();
    }

    public static StatBase gety(MHFCMobList.MHFCEggInfo p_151176_0_)
    {
        String s = MHFCMobList.getStringFromID(p_151176_0_.spawnedID);
        return s == null ? null : (new StatBase("stat.entityKilledBy." + s, new ChatComponentTranslation("stat.entityKilledBy", new Object[] {new ChatComponentTranslation("entity." + s + ".name", new Object[0])}))).registerStat();
    }
}