package mhfc.net.common.util.world;

import java.util.ArrayList;
import java.util.HashMap;
/*     */
import java.util.HashSet;
/*     */
import java.util.Iterator;
/*     */
import java.util.List;
/*     */
import java.util.Map;
/*     */
import java.util.Random;
/*     */
import net.minecraft.block.Block;
/*     */
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
/*     */
import net.minecraft.entity.Entity;
/*     */
import net.minecraft.entity.EntityLiving;
/*     */
import net.minecraft.entity.player.EntityPlayer;
/*     */
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S27PacketExplosion;
/*     */
import net.minecraft.util.AxisAlignedBB;
/*     */
import net.minecraft.util.DamageSource;
/*     */
import net.minecraft.util.MathHelper;
/*     */
import net.minecraft.util.Vec3;
/*     */
import net.minecraft.world.ChunkPosition;
/*     */
import net.minecraft.world.Explosion;
/*     */
import net.minecraft.world.World;
/*     */
import net.minecraft.world.WorldServer;

public class MHFCExplosion extends Explosion {
	public static boolean damageReduce = true;
	public static boolean useEntity = true;
	public static boolean destroyBlocks = true;
	public static boolean lessenDrop = true;

	public boolean isBurning = false;
	private int byteinitiator = 16;
	private Random randomexplosion = new Random();
	private World worldObj;
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public Entity exploder;
	public float explosionSize;
	public List affectedBlockPositions = new ArrayList();
	private Map field_77288_k = new HashMap();
	
	//A sort of reworking Explosion System that i will add something really soon.
	
	public boolean isRepeated = false;
	public int repeatTimes;
	public int oilStacking;

	public MHFCExplosion(World world, double x, double y, double z, float r) {
		this(world, null, x, y, z, r);
	}

	public MHFCExplosion(World world, Entity entity, double x, double y,
			double z, float r) {
		super(world, entity, x, y, z, r);
		worldObj = world;
		exploder = entity;
		explosionSize = r;
		explosionX = x;
		explosionY = y;
		explosionZ = z;
	}

	public void func_77278_a() {
		float var1 = explosionSize;
		HashSet var2 = new HashSet();

		for (int var3 = 0; var3 < byteinitiator; var3++) {
			for (int var4 = 0; var4 < byteinitiator; var4++) {
				for (int var5 = 0; var5 < byteinitiator; var5++) {
					if ((var3 == 0) || (var3 == byteinitiator - 1)
							|| (var4 == 0) || (var4 == byteinitiator - 1)
							|| (var5 == 0) || (var5 == byteinitiator - 1)) {
						double var6 = var3 / (byteinitiator - 1.0F) * 2.0F
								- 1.0F;
						double var8 = var4 / (byteinitiator - 1.0F) * 2.0F
								- 1.0F;
						double var10 = var5 / (byteinitiator - 1.0F) * 2.0F
								- 1.0F;
						double var12 = Math.sqrt(var6 * var6 + var8 * var8
								+ var10 * var10);
						var6 /= var12;
						var8 /= var12;
						var10 /= var12;
						float var14 = explosionSize
								* (0.7F + worldObj.rand.nextFloat() * 0.6F);
						double var15 = explosionX;
						double var17 = explosionY;
						double var19 = explosionZ;

						for (float var21 = 0.3F; var14 > 0.0F; var14 -= var21 * 0.75F) {
							int var22 = MathHelper.floor_double(var15);
							int var23 = MathHelper.floor_double(var17);
							int var24 = MathHelper.floor_double(var19);
							Block block = worldObj
									.getBlock(var22, var23, var24);

							if (block.getMaterial() != Material.air) {
								float f3 = exploder != null ? exploder
										.func_145772_a(this, worldObj, var22,
												var23, var24, block) : block
										.getExplosionResistance(exploder,
												worldObj, var22, var23, var24,
												explosionX, explosionY,
												explosionZ);
							}

							if (var14 > 0.0F) {
								var2.add(new ChunkPosition(var22, var23, var24));
							}

							var15 += var6 * var21;
							var17 += var8 * var21;
							var19 += var10 * var21;
						}
					}
				}
			}
		}

		affectedBlockPositions.addAll(var2);
		explosionSize *= 2.0F;
		int var3 = MathHelper.floor_double(explosionX - explosionSize - 1.0D);
		int var4 = MathHelper.floor_double(explosionX + explosionSize + 1.0D);
		int var5 = MathHelper.floor_double(explosionY - explosionSize - 1.0D);
		int var27 = MathHelper.floor_double(explosionY + explosionSize + 1.0D);
		int var7 = MathHelper.floor_double(explosionZ - explosionSize - 1.0D);
		int var28 = MathHelper.floor_double(explosionZ + explosionSize + 1.0D);
		List var9 = worldObj.getEntitiesWithinAABBExcludingEntity(
				exploder,
				AxisAlignedBB.getAABBPool().getAABB(var3, var5, var7, var4,
						var27, var28));
		Vec3 var29 = worldObj.getWorldVec3Pool().getVecFromPool(explosionX,
				explosionY, explosionZ);

		for (int var11 = 0; var11 < var9.size(); var11++) {
			Entity var30 = (Entity) var9.get(var11);
			double var13 = var30
					.getDistance(explosionX, explosionY, explosionZ)
					/ explosionSize;

			if (var13 <= 1.0D) {
				double var15 = var30.posX - explosionX;
				double var17 = var30.posY + var30.getEyeHeight() - explosionY;
				double var19 = var30.posZ - explosionZ;
				double var32 = MathHelper.sqrt_double(var15 * var15 + var17
						* var17 + var19 * var19);

				if (var32 != 0.0D) {
					var15 /= var32;
					var17 /= var32;
					var19 /= var32;
					double var31 = worldObj.getBlockDensity(var29,
							var30.boundingBox);
					double var33 = (1.0D - var13) * var31;
					float power = 8.0F;
					if (damageReduce)
						power = 6.0F;
					DamageSource source = DamageSource.setExplosionSource(this);
					if ((useEntity) && (exploder != null)
							&& ((exploder instanceof EntityLiving)))
						source = DamageSource
								.causeMobDamage((EntityLiving) exploder);
					var30.attackEntityFrom(source,
							(int) ((var33 * var33 + var33) / 2.0D * power
									* explosionSize + 1.0D));
					var30.motionX += var15 * var33;
					var30.motionY += var17 * var33;
					var30.motionZ += var19 * var33;

					if ((var30 instanceof EntityPlayer)) {
						field_77288_k.put(
								(EntityPlayer) var30,
								worldObj.getWorldVec3Pool().getVecFromPool(
										var15 * var33, var17 * var33,
										var19 * var33));
					}
				}
			}
		}

		explosionSize = var1;
	}

	public void func_77279_a(boolean par1) {
		worldObj.playSoundEffect(
				explosionX,
				explosionY,
				explosionZ,
				"random.explode",
				4.0F,
				(1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		worldObj.spawnParticle("hugeexplosion", explosionX, explosionY,
				explosionZ, 0.0D, 0.0D, 0.0D);
		Iterator var2 = affectedBlockPositions.iterator();

		while (var2.hasNext()) {
			ChunkPosition var3 = (ChunkPosition) var2.next();
			int var4 = var3.chunkPosX;
			int var5 = var3.chunkPosY;
			int var6 = var3.chunkPosZ;
			Block block = worldObj.getBlock(var4, var5, var6);

			if (par1) {
				double var8 = var4 + worldObj.rand.nextFloat();
				double var10 = var5 + worldObj.rand.nextFloat();
				double var12 = var6 + worldObj.rand.nextFloat();
				double var14 = var8 - explosionX;
				double var16 = var10 - explosionY;
				double var18 = var12 - explosionZ;
				double var20 = MathHelper.sqrt_double(var14 * var14 + var16
						* var16 + var18 * var18);
				var14 /= var20;
				var16 /= var20;
				var18 /= var20;
				double var22 = 0.5D / (var20 / explosionSize + 0.1D);
				var22 *= (worldObj.rand.nextFloat() * worldObj.rand.nextFloat() + 0.3F);
				var14 *= var22;
				var16 *= var22;
				var18 *= var22;
				worldObj.spawnParticle("explode",
						(var8 + explosionX * 1.0D) / 2.0D,
						(var10 + explosionY * 1.0D) / 2.0D,
						(var12 + explosionZ * 1.0D) / 2.0D, var14, var16, var18);
				worldObj.spawnParticle("smoke", var8, var10, var12, var14,
						var16, var18);
			}
			if (block.getMaterial() != Material.air) {
				if ((block.canDropFromExplosion(this))
						&& ((!lessenDrop) || (randomexplosion.nextFloat() < 0.2F))) {
					block.dropBlockAsItemWithChance(worldObj, var4, var5, var6,
							worldObj.getBlockMetadata(var4, var5, var6),
							1.0F / explosionSize, 0);
				}

				block.onBlockExploded(worldObj, var4, var5, var6, this);
			}
		}
		if (isBurning) {
			var2 = affectedBlockPositions.iterator();

			while (var2.hasNext()) {
				ChunkPosition var3 = (ChunkPosition) var2.next();
				int var4 = var3.chunkPosX;
				int var5 = var3.chunkPosY;
				int var6 = var3.chunkPosZ;
				Block block = worldObj.getBlock(var4, var5, var6);
				Block block1 = worldObj.getBlock(var4, var5 - 1, var6);
				if ((block.getMaterial() == Material.air)
						&& (block1.func_149730_j())
						&& (randomexplosion.nextInt(3) == 0)) {
					worldObj.setBlock(var4, var5, var6, Blocks.fire);
				}
			}
		}
	}

	public void explode(boolean particles) {
		func_77278_a();
		func_77279_a(particles);
		if ((destroyBlocks) && ((worldObj instanceof WorldServer))) {
			Iterator var11 = worldObj.playerEntities.iterator();
			while (var11.hasNext()) {
				EntityPlayer var12 = (EntityPlayer) var11.next();

				if (var12.getDistanceSq(explosionX, explosionY, explosionZ) < 4096.0D)
					((EntityPlayerMP) var12).playerNetServerHandler
							.sendPacket(new S27PacketExplosion(explosionX,
									explosionY, explosionZ, explosionSize,
									affectedBlockPositions,
									(Vec3) func_77277_b().get(var12)));
			}
		}
	}

	public Map func_77277_b() {
		return field_77288_k;
	}
}
