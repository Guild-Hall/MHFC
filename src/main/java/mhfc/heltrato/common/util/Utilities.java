package mhfc.heltrato.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.core.registry.MHFCRegItem;
import mhfc.heltrato.common.core.registry.MHFCRegPotion;
import mhfc.heltrato.common.entity.projectile.EntityLightning;
import mhfc.heltrato.common.entity.type.EntityWyvernHostile;
import mhfc.heltrato.common.entity.type.EntityWyvernPeaceful;
import mhfc.heltrato.common.interfaces.iMHFC;
import mhfc.heltrato.common.network.message.MessageAIAnim;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.client.FMLClientHandler;

public class Utilities {
	
	private static MHFCRegItem item;
	private static Random rand;
	private static double	kbMotionX			= 0D;
	private static double	kbMotionY			= 0D;
	private static double	kbMotionZ			= 0D;
	private static int		knockBackModifier	= 0;
	
	
	public Utilities() {
		rand = new Random();
	}
	
	
	//.. Returns an instant vary of loc
		public ResourceLocation resourcereLoc(String name, ResourceLocation loc) {
			return new ResourceLocation(name);
		}
		
		
	public static void sendAnimPacket(iMHFC entity, int animID) {
		if(MHFCMain.isEffectiveClient()) return;
		entity.setAnimID(animID);
		Entity e = (Entity)entity;
		MHFCMain.network.sendToAll(new MessageAIAnim((byte)animID, e.getEntityId()));
	}
	
	public static ArrayList<Entity> getCollidingEntities(Entity entity, World world, AxisAlignedBB box)
	{
		ArrayList list = new ArrayList();
	    List entities = world.getEntitiesWithinAABBExcludingEntity(entity, box.expand(4.0D, 4.0D, 4.0D));
	    for (int i = 0; i < entities.size(); i++) {
	    Entity entity1 = (Entity)entities.get(i);
	    AxisAlignedBB box1 = entity1.boundingBox;
	    if ((box1 != null) && (box.intersectsWith(box1))) list.add(entity1);
	    }
	    return list;
	}
	
	 public static void removeAttackers(EntityLiving living) {
		 List list = living.worldObj.getEntitiesWithinAABB(EntityLiving.class, living.boundingBox.expand(16.0D, 10.0D, 16.0D));
		 for (int i = 0; i < list.size(); i++) {
			 EntityLiving attacker = (EntityLiving)list.get(i);
			 if ((attacker != living) && 
					 (attacker.getAttackTarget() == living)) {
				 		attacker.setAttackTarget(null);
				 		attacker.setRevengeTarget(null);
	       }
	   }
	
	 }
	 
	 public static void changeAttackInto(EntityLiving living, float movespeed, EntityLiving e) {
		 List list = living.worldObj.getEntitiesWithinAABB(EntityLiving.class, living.boundingBox.expand(16.0D, 10.0D, 16.0D));
		 for ( int i = 0; i < list.size(); i++) {
			 EntityLiving attacker = (EntityLiving)list.get(i);
			 if(attacker != living){
				 attacker.setAttackTarget(e);
				 attacker.setRevengeTarget(e);
			 }
		 }
 		
	 }
	 
	 public static void getFeedMob(EntityWyvernHostile wyvern, float amountHp ){
		 List list = wyvern.worldObj.getEntitiesWithinAABB(EntityWyvernHostile.class, wyvern.boundingBox.expand(12.0D, 3.0D, 12.0D));
		 for ( int i = 0; i < list.size(); i++) {
			 EntityLiving food = (EntityLiving)list.get(i);
			 if(food instanceof EntityWyvernPeaceful || food instanceof EntityAnimal){
				 wyvern.setAttackTarget(food);
				 wyvern.heal(amountHp);
			 }
		 }
	 }
	 
	 
	 
	 public static void chargeMobToEntity(EntityWyvernHostile chargingEntity, Entity target, float distance, float moveSpeed, boolean dependsonWater){
		 PathEntity pathentity = chargingEntity.worldObj.getPathEntityToEntity(chargingEntity, target, 16, false, false, dependsonWater, true);
		 if((pathentity != null) && (distance < 12.0F)){
			 chargingEntity.setPathToEntity(pathentity);
			 chargingEntity.speed = moveSpeed;
		 }
	 }
	 
	 public static int countPlayers(WorldServer worldObj, int dimension) {
		 int players = 0;
		 for( int i = 0; i < worldObj.playerEntities.size() ; i++ ) {
			 EntityPlayerMP entityplayermp = (EntityPlayerMP)worldObj.playerEntities.get(i);
			 
			 if(entityplayermp.dimension == dimension) {
				 players++;
			 }
		 }
		 System.out.println(players);
		 return players;
	 }
	 
	 public static void updateArmorTick(World world,EntityPlayer player) {
		 ItemStack[] armorstack = new ItemStack[4];
		 	armorstack[0] = player.inventory.armorItemInSlot(0);
		 	armorstack[1] = player.inventory.armorItemInSlot(1);
		 	armorstack[2] = player.inventory.armorItemInSlot(2);
		 	armorstack[3] = player.inventory.armorItemInSlot(3);
		 	
		if((armorstack[0] != null) && (armorstack[0].getItem() == item.mhfcitemkirinhelm) && (armorstack[1] != null) && (armorstack[1].getItem() == item.mhfcitemkirinchest) && (armorstack[2] != null) && (armorstack[2].getItem() == item.mhfcitemkirinlegs) && (armorstack[3] != null) && (armorstack[3].getItem() == item.mhfcitemkirinboots)) {
			return;
		}
		if((armorstack[0] != null) && (armorstack[0].getItem() == item.mhfcitemkirinShelm) && (armorstack[1] != null) && (armorstack[1].getItem() == item.mhfcitemkirinSchest) && (armorstack[2] != null) && (armorstack[2].getItem() == item.mhfcitemkirinSlegs) && (armorstack[3] != null) && (armorstack[3].getItem() == item.mhfcitemkirinSboots)) {
			int duration = 15;
			player.addPotionEffect(new PotionEffect(MHFCRegPotion.mhfcpotionkirinbless.id, duration++, 1));
			world.spawnParticle("cloud", player.posX + rand.nextFloat() * 2.0F - 1.0D, player.posY + rand.nextFloat() * 3.0F + 1.0D, player.posZ + rand.nextFloat() * 2.0F - 1.0D, 0.0D, 0.0D, 0.0D);
			return;
		}
		if((armorstack[0] != null) && (armorstack[0].getItem() == item.mhfcitemtigrexhelm) && (armorstack[1] != null) && (armorstack[1].getItem() == item.mhfcitemtigrexchest) && (armorstack[2] != null) && (armorstack[2].getItem() == item.mhfcitemtigrexlegs) && (armorstack[3] != null) && (armorstack[3].getItem() == item.mhfcitemtigrexboots)) {
			return;
		}
		
		if((armorstack[0] != null) && (armorstack[0].getItem() == item.mhfcitemrathaloshelm) && (armorstack[1] != null) && (armorstack[1].getItem() == item.mhfcitemrathaloschest) && (armorstack[2] != null) && (armorstack[2].getItem() == item.mhfcitemrathaloslegs) && (armorstack[3] != null) && (armorstack[3].getItem() == item.mhfcitemrathalosboots)) {
			return;
		}
		 	
	 }
	 
	 public static void spawnlightnings(int init, double Lx, double Ly, double Lz, int many, EntityLivingBase e) {
		 for(init = 0; init < many; init++) {
			 EntityLightning l = new EntityLightning(e.worldObj);
			 l.setPosition(Lx, Ly, Lz);
			 e.worldObj.spawnEntityInWorld(l);
		 }
	 }
	 
	 
	 //TODO nullDamage is chance that all incoming singe target (projectiles , not aoe) has a 20% chance to 
	 //  be block and will be feature on update on lance. - `Heltrato  
	 public static void nullDamage(DamageSource source, EntityLivingBase attacker, float damage, EntityPlayer player){
		 if(rand.nextInt(2) == 0){
			 if(source.causeMobDamage(attacker) != null || source.isProjectile()){
				 damage = 0;
			 }
		 }
	 }
	 
	 // Rage Form Test Method.
	 public static void startIncrement(boolean a, int xcount, int duration, EntityWyvernHostile entity){
		 xcount = 0;
		 int durationVolt = 100 + duration; // default duration 
		 if(a){
			 if(!entity.isDead){
				 xcount++;
				 durationVolt--;
				 if(durationVolt == 0){
					 a = false;
				 }
			 }
		 }
		 
	 }
	 
	 public static void knockBack(EntityLivingBase entityliving, EntityLivingBase attacker, float knockback)
		{
			entityliving.motionX = kbMotionX;
			entityliving.motionY = kbMotionY;
			entityliving.motionZ = kbMotionZ;
			//float f2 = 1F / 0.4F;
			
			//attackEntityFrom part
			double dx = attacker.posX - entityliving.posX;
			double dz;
			
			for (dz = attacker.posZ - entityliving.posZ; dx * dx + dz * dz < 1E-4D; dz = (Math.random() - Math.random()) * 0.01D)
			{
				dx = (Math.random() - Math.random()) * 0.01D;
			}
			
			entityliving.attackedAtYaw = (float) ((Math.atan2(dz, dx) * 180D) / Math.PI) - entityliving.rotationYaw;
			
			//knockBack part
			float f = MathHelper.sqrt_double(dx * dx + dz * dz);
			entityliving.motionX -= (dx / f) * knockback;
			entityliving.motionY += knockback;
			entityliving.motionZ -= (dz / f) * knockback;
			if (entityliving.motionY > 0.4D)
			{
				entityliving.motionY = 0.4D;
			}
			
			if (knockBackModifier > 0)
			{
				dx = -Math.sin(Math.toRadians(attacker.rotationYaw)) * knockBackModifier * 0.5F;
				dz = Math.cos(Math.toRadians(attacker.rotationYaw)) * knockBackModifier * 0.5F;
				entityliving.addVelocity(dx, 0.1D, dz);
			}
			
			knockBackModifier = 0;
			kbMotionX = kbMotionY = kbMotionZ = 0D;
		}
		
		public static void prepareKnockbackOnEntity(EntityLivingBase attacker, EntityLivingBase entity)
		{
			knockBackModifier = EnchantmentHelper.getKnockbackModifier(attacker, entity);
			if (attacker.isSprinting())
			{
				knockBackModifier++;
			}
			kbMotionX = entity.motionX;
			kbMotionY = entity.motionY;
			kbMotionZ = entity.motionZ;
		}
		
		
}	 
		 
	 
	 

