 package mhfc.net.common.util.world;

 import java.util.ArrayList;
import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S27PacketExplosion;
/*     */ import net.minecraft.util.AABBPool;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.util.Vec3Pool;
/*     */ import net.minecraft.world.ChunkPosition;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class MHFCExplosion extends Explosion
/*     */ {
/*  27 */   public static boolean lessenDamage = true;
/*  28 */   public static boolean useEntity = true;
/*  29 */   public static boolean destroyBlocks = true;
/*  30 */   public static boolean lessenDrop = true;
/*     */ 
/*  32 */   public boolean isFlaming = false;
/*  33 */   private int field_77289_h = 16;
/*  34 */   private Random explosionRNG = new Random();
/*     */   private World worldObj;
/*     */   public double explosionX;
/*     */   public double explosionY;
/*     */   public double explosionZ;
/*     */   public Entity exploder;
/*     */   public float explosionSize;
/*  41 */   public List affectedBlockPositions = new ArrayList();
/*  42 */   private Map field_77288_k = new HashMap();
/*     */ 
/*     */   public MHFCExplosion(World world, double x, double y, double z, float r) {
/*  45 */     this(world, null, x, y, z, r);
/*     */   }
/*     */ 
/*     */   public MHFCExplosion(World world, Entity entity, double x, double y, double z, float r)
/*     */   {
/*  50 */     super(world, entity, x, y, z, r);
/*  51 */     this.worldObj = world;
/*  52 */     this.exploder = entity;
/*  53 */     this.explosionSize = r;
/*  54 */     this.explosionX = x;
/*  55 */     this.explosionY = y;
/*  56 */     this.explosionZ = z;
/*     */   }
/*     */ 
/*     */   public void func_77278_a()
/*     */   {
/*  64 */     float var1 = this.explosionSize;
/*  65 */     HashSet var2 = new HashSet();
/*     */ 
/*  73 */     for (int var3 = 0; var3 < this.field_77289_h; var3++)
/*     */     {
/*  75 */       for (int var4 = 0; var4 < this.field_77289_h; var4++)
/*     */       {
/*  77 */         for (int var5 = 0; var5 < this.field_77289_h; var5++)
/*     */         {
/*  79 */           if ((var3 == 0) || (var3 == this.field_77289_h - 1) || (var4 == 0) || (var4 == this.field_77289_h - 1) || (var5 == 0) || (var5 == this.field_77289_h - 1))
/*     */           {
/*  81 */             double var6 = var3 / (this.field_77289_h - 1.0F) * 2.0F - 1.0F;
/*  82 */             double var8 = var4 / (this.field_77289_h - 1.0F) * 2.0F - 1.0F;
/*  83 */             double var10 = var5 / (this.field_77289_h - 1.0F) * 2.0F - 1.0F;
/*  84 */             double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
/*  85 */             var6 /= var12;
/*  86 */             var8 /= var12;
/*  87 */             var10 /= var12;
/*  88 */             float var14 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
/*  89 */             double var15 = this.explosionX;
/*  90 */             double var17 = this.explosionY;
/*  91 */             double var19 = this.explosionZ;
/*     */ 
/*  93 */             for (float var21 = 0.3F; var14 > 0.0F; var14 -= var21 * 0.75F)
/*     */             {
/*  95 */               int var22 = MathHelper.floor_double(var15);
/*  96 */               int var23 = MathHelper.floor_double(var17);
/*  97 */               int var24 = MathHelper.floor_double(var19);
/*  98 */               Block block = this.worldObj.getBlock(var22, var23, var24);
/*     */ 
/* 100 */               if (block.getMaterial() != Material.air)
/*     */               {
/* 102 */                 float f3 = this.exploder != null ? this.exploder.func_145772_a(this, this.worldObj, var22, var23, var24, block) : block.getExplosionResistance(this.exploder, this.worldObj, var22, var23, var24, this.explosionX, this.explosionY, this.explosionZ);
/*     */               }
/*     */ 
/* 105 */               if (var14 > 0.0F)
/*     */               {
/* 107 */                 var2.add(new ChunkPosition(var22, var23, var24));
/*     */               }
/*     */ 
/* 110 */               var15 += var6 * var21;
/* 111 */               var17 += var8 * var21;
/* 112 */               var19 += var10 * var21;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 119 */     this.affectedBlockPositions.addAll(var2);
/* 120 */     this.explosionSize *= 2.0F;
/* 121 */     int var3 = MathHelper.floor_double(this.explosionX - this.explosionSize - 1.0D);
/* 122 */     int var4 = MathHelper.floor_double(this.explosionX + this.explosionSize + 1.0D);
/* 123 */     int var5 = MathHelper.floor_double(this.explosionY - this.explosionSize - 1.0D);
/* 124 */     int var27 = MathHelper.floor_double(this.explosionY + this.explosionSize + 1.0D);
/* 125 */     int var7 = MathHelper.floor_double(this.explosionZ - this.explosionSize - 1.0D);
/* 126 */     int var28 = MathHelper.floor_double(this.explosionZ + this.explosionSize + 1.0D);
/* 127 */     List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getAABBPool().getAABB(var3, var5, var7, var4, var27, var28));
/* 128 */     Vec3 var29 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.explosionX, this.explosionY, this.explosionZ);
/*     */ 
/* 130 */     for (int var11 = 0; var11 < var9.size(); var11++)
/*     */     {
/* 132 */       Entity var30 = (Entity)var9.get(var11);
/* 133 */       double var13 = var30.getDistance(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize;
/*     */ 
/* 135 */       if (var13 <= 1.0D)
/*     */       {
/* 137 */         double var15 = var30.posX - this.explosionX;
/* 138 */         double var17 = var30.posY + var30.getEyeHeight() - this.explosionY;
/* 139 */         double var19 = var30.posZ - this.explosionZ;
/* 140 */         double var32 = MathHelper.sqrt_double(var15 * var15 + var17 * var17 + var19 * var19);
/*     */ 
/* 142 */         if (var32 != 0.0D)
/*     */         {
/* 144 */           var15 /= var32;
/* 145 */           var17 /= var32;
/* 146 */           var19 /= var32;
/* 147 */           double var31 = this.worldObj.getBlockDensity(var29, var30.boundingBox);
/* 148 */           double var33 = (1.0D - var13) * var31;
/* 149 */           float power = 8.0F;
/* 150 */           if (this.lessenDamage) power = 6.0F;
/* 151 */           DamageSource source = DamageSource.setExplosionSource(this);
/* 152 */           if ((this.useEntity) && (this.exploder != null) && ((this.exploder instanceof EntityLiving))) source = DamageSource.causeMobDamage((EntityLiving)this.exploder);
/* 153 */           var30.attackEntityFrom(source, (int)((var33 * var33 + var33) / 2.0D * power * this.explosionSize + 1.0D));
/* 154 */           var30.motionX += var15 * var33;
/* 155 */           var30.motionY += var17 * var33;
/* 156 */           var30.motionZ += var19 * var33;
/*     */ 
/* 158 */           if ((var30 instanceof EntityPlayer))
/*     */           {
/* 160 */             this.field_77288_k.put((EntityPlayer)var30, this.worldObj.getWorldVec3Pool().getVecFromPool(var15 * var33, var17 * var33, var19 * var33));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 166 */     this.explosionSize = var1;
/*     */   }
/*     */ 
/*     */   public void func_77279_a(boolean par1)
/*     */   {
/* 174 */     this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
/* 175 */     this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 0.0D, 0.0D, 0.0D);
/* 176 */     Iterator var2 = this.affectedBlockPositions.iterator();
/*     */ 
/* 183 */     while (var2.hasNext())
/*     */     {
/* 185 */       ChunkPosition var3 = (ChunkPosition)var2.next();
/* 186 */       int var4 = var3.chunkPosX;
/* 187 */       int var5 = var3.chunkPosY;
/* 188 */       int var6 = var3.chunkPosZ;
/* 189 */       Block block = this.worldObj.getBlock(var4, var5, var6);
/*     */ 
/* 191 */       if (par1)
/*     */       {
/* 193 */         double var8 = var4 + this.worldObj.rand.nextFloat();
/* 194 */         double var10 = var5 + this.worldObj.rand.nextFloat();
/* 195 */         double var12 = var6 + this.worldObj.rand.nextFloat();
/* 196 */         double var14 = var8 - this.explosionX;
/* 197 */         double var16 = var10 - this.explosionY;
/* 198 */         double var18 = var12 - this.explosionZ;
/* 199 */         double var20 = MathHelper.sqrt_double(var14 * var14 + var16 * var16 + var18 * var18);
/* 200 */         var14 /= var20;
/* 201 */         var16 /= var20;
/* 202 */         var18 /= var20;
/* 203 */         double var22 = 0.5D / (var20 / this.explosionSize + 0.1D);
/* 204 */         var22 *= (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
/* 205 */         var14 *= var22;
/* 206 */         var16 *= var22;
/* 207 */         var18 *= var22;
/* 208 */         this.worldObj.spawnParticle("explode", (var8 + this.explosionX * 1.0D) / 2.0D, (var10 + this.explosionY * 1.0D) / 2.0D, (var12 + this.explosionZ * 1.0D) / 2.0D, var14, var16, var18);
/* 209 */         this.worldObj.spawnParticle("smoke", var8, var10, var12, var14, var16, var18);
/*     */       }
/*     */ 
/* 212 */       if (block.getMaterial() != Material.air)
/*     */       {
/* 214 */         if ((block.canDropFromExplosion(this)) && ((!this.lessenDrop) || (this.explosionRNG.nextFloat() < 0.2F)))
/*     */           {
/* 243 */             block.dropBlockAsItemWithChance(this.worldObj, var4, var5, var6, this.worldObj.getBlockMetadata(var4, var5, var6), 1.0F / this.explosionSize, 0);
/*     */           }
/*     */ 
/* 246 */           block.onBlockExploded(this.worldObj, var4, var5, var6, this);
/*     */         }
/*     */       }
/*     */ 
/* 226 */     if (this.isFlaming)
/*     */     {
/* 228 */       var2 = this.affectedBlockPositions.iterator();
/*     */ 
/* 230 */       while (var2.hasNext())
/*     */       {
/* 232 */         ChunkPosition var3 = (ChunkPosition)var2.next();
/* 233 */         int var4 = var3.chunkPosX;
/* 234 */         int var5 = var3.chunkPosY;
/* 235 */         int var6 = var3.chunkPosZ;
/* 236 */         Block block = this.worldObj.getBlock(var4, var5, var6);
/* 262 */         Block block1 = this.worldObj.getBlock(var4, var5 - 1, var6);
/*     */ 
/* 239 */          if ((block.getMaterial() == Material.air) && (block1.func_149730_j()) && (this.explosionRNG.nextInt(3) == 0))
/*     */         {
/* 266 */           this.worldObj.setBlock(var4, var5, var6, Blocks.fire);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void explode(boolean particles) {
/* 248 */     func_77278_a();
/* 249 */     func_77279_a(particles);
/* 250 */     if ((this.destroyBlocks) && ((this.worldObj instanceof WorldServer))) {
/* 251 */       Iterator var11 = this.worldObj.playerEntities.iterator();
/* 252 */       while (var11.hasNext()) {
/* 253 */         EntityPlayer var12 = (EntityPlayer)var11.next();
/*     */ 
/* 255 */         if (var12.getDistanceSq(this.explosionX, this.explosionY, this.explosionZ) < 4096.0D)
/* 256 */           ((EntityPlayerMP)var12).playerNetServerHandler.sendPacket(new S27PacketExplosion(this.explosionX, this.explosionY, this.explosionZ, this.explosionSize, this.affectedBlockPositions, (Vec3)func_77277_b().get(var12)));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map func_77277_b()
/*     */   {
/* 264 */     return this.field_77288_k;
/*     */   }
/*     */ }

/* Location:           C:\Users\TOSHIBA\Desktop\Utilities\1.6.4 Mods\Mutant_Creatures_v1.4.4_mc1.6.4.zip
 * Qualified Name:     thehippomaster.MutantCreatures.MCExplosion
 * JD-Core Version:    0.6.2
 */