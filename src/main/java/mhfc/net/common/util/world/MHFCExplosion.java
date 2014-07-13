package mhfc.net.common.util.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class MHFCExplosion extends Explosion {
	public static boolean lessenDamage = true;
	public static boolean useEntity = true;
	public static boolean destroyBlocks = true;
	public static boolean lessenDrop = true;

	public boolean isFlaming = false;
	private int field_77289_h = 16;
	private Random explosionRNG = new Random();
	private World worldObj;
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public Entity exploder;
	public float explosionSize;
	public List<ChunkPosition> affectedBlockPositions = new ArrayList<ChunkPosition>();
	private Map<EntityPlayer, Vec3> field_77288_k = new HashMap<EntityPlayer, Vec3>();

	public MHFCExplosion(World world, double x, double y, double z, float r) {
		this(world, null, x, y, z, r);
	}

	public MHFCExplosion(World world, Entity entity, double x, double y,
			double z, float r) {
		super(world, entity, x, y, z, r);
		this.worldObj = world;
		this.exploder = entity;
		this.explosionSize = r;
		this.explosionX = x;
		this.explosionY = y;
		this.explosionZ = z;
	}

	public void func_77278_a() {
		float var1 = this.explosionSize;
		HashSet<ChunkPosition> var2 = new HashSet<ChunkPosition>();

		for (int var3 = 0; var3 < this.field_77289_h; var3++) {
			for (int var4 = 0; var4 < this.field_77289_h; var4++) {
				for (int var5 = 0; var5 < this.field_77289_h; var5++) {
					if ((var3 == 0) || (var3 == this.field_77289_h - 1)
							|| (var4 == 0) || (var4 == this.field_77289_h - 1)
							|| (var5 == 0) || (var5 == this.field_77289_h - 1)) {
						double var6 = var3 / (this.field_77289_h - 1.0F) * 2.0F
								- 1.0F;
						double var8 = var4 / (this.field_77289_h - 1.0F) * 2.0F
								- 1.0F;
						double var10 = var5 / (this.field_77289_h - 1.0F)
								* 2.0F - 1.0F;
						double var12 = Math.sqrt(var6 * var6 + var8 * var8
								+ var10 * var10);
						var6 /= var12;
						var8 /= var12;
						var10 /= var12;
						float var14 = this.explosionSize
								* (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
						double var15 = this.explosionX;
						double var17 = this.explosionY;
						double var19 = this.explosionZ;

						for (float var21 = 0.3F; var14 > 0.0F; var14 -= var21 * 0.75F) {
							int var22 = MathHelper.floor_double(var15);
							int var23 = MathHelper.floor_double(var17);
							int var24 = MathHelper.floor_double(var19);
							Block block = this.worldObj.getBlock(var22, var23,
									var24);

							if (block.getMaterial() != Material.air) {
								float f3 = this.exploder != null
										? this.exploder.func_145772_a(this,
												this.worldObj, var22, var23,
												var24, block) : block
												.getExplosionResistance(
														this.exploder,
														this.worldObj, var22,
														var23, var24,
														this.explosionX,
														this.explosionY,
														this.explosionZ);
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

		this.affectedBlockPositions.addAll(var2);
		this.explosionSize *= 2.0F;
		int var3 = MathHelper.floor_double(this.explosionX - this.explosionSize
				- 1.0D);
		int var4 = MathHelper.floor_double(this.explosionX + this.explosionSize
				+ 1.0D);
		int var5 = MathHelper.floor_double(this.explosionY - this.explosionSize
				- 1.0D);
		int var27 = MathHelper.floor_double(this.explosionY
				+ this.explosionSize + 1.0D);
		int var7 = MathHelper.floor_double(this.explosionZ - this.explosionSize
				- 1.0D);
		int var28 = MathHelper.floor_double(this.explosionZ
				+ this.explosionSize + 1.0D);
		@SuppressWarnings("unchecked")
		List<Entity> var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(
				this.exploder, AxisAlignedBB.getBoundingBox(var3, var5, var7,
						var4, var27, var28));
		Vec3 var29 = Vec3.createVectorHelper(this.explosionX, this.explosionY,
				this.explosionZ);

		for (int var11 = 0; var11 < var9.size(); var11++) {
			Entity var30 = var9.get(var11);
			double var13 = var30.getDistance(this.explosionX, this.explosionY,
					this.explosionZ) / this.explosionSize;

			if (var13 <= 1.0D) {
				double var15 = var30.posX - this.explosionX;
				double var17 = var30.posY + var30.getEyeHeight()
						- this.explosionY;
				double var19 = var30.posZ - this.explosionZ;
				double var32 = MathHelper.sqrt_double(var15 * var15 + var17
						* var17 + var19 * var19);

				if (var32 != 0.0D) {
					var15 /= var32;
					var17 /= var32;
					var19 /= var32;
					double var31 = this.worldObj.getBlockDensity(var29,
							var30.boundingBox);
					double var33 = (1.0D - var13) * var31;
					float power = 8.0F;
					if (MHFCExplosion.lessenDamage) power = 6.0F;
					DamageSource source = DamageSource.setExplosionSource(this);
					if ((MHFCExplosion.useEntity) && (this.exploder != null)
							&& ((this.exploder instanceof EntityLiving)))
						source = DamageSource
								.causeMobDamage((EntityLiving) this.exploder);
					var30.attackEntityFrom(source,
							(int) ((var33 * var33 + var33) / 2.0D * power
									* this.explosionSize + 1.0D));
					var30.motionX += var15 * var33;
					var30.motionY += var17 * var33;
					var30.motionZ += var19 * var33;

					if ((var30 instanceof EntityPlayer)) {
						this.field_77288_k.put(
								(EntityPlayer) var30,
								Vec3.createVectorHelper(var15 * var33, var17
										* var33, var19 * var33));
					}
				}
			}
		}

		this.explosionSize = var1;
	}

	public void func_77279_a(boolean par1) {
		this.worldObj.playSoundEffect(this.explosionX, this.explosionY,
				this.explosionZ, "random.explode", 4.0F,
				(1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand
						.nextFloat()) * 0.2F) * 0.7F);
		this.worldObj.spawnParticle("hugeexplosion", this.explosionX,
				this.explosionY, this.explosionZ, 0.0D, 0.0D, 0.0D);
		Iterator<ChunkPosition> var2 = this.affectedBlockPositions.iterator();

		while (var2.hasNext()) {
			ChunkPosition var3 = var2.next();
			int var4 = var3.chunkPosX;
			int var5 = var3.chunkPosY;
			int var6 = var3.chunkPosZ;
			Block block = this.worldObj.getBlock(var4, var5, var6);

			if (par1) {
				double var8 = var4 + this.worldObj.rand.nextFloat();
				double var10 = var5 + this.worldObj.rand.nextFloat();
				double var12 = var6 + this.worldObj.rand.nextFloat();
				double var14 = var8 - this.explosionX;
				double var16 = var10 - this.explosionY;
				double var18 = var12 - this.explosionZ;
				double var20 = MathHelper.sqrt_double(var14 * var14 + var16
						* var16 + var18 * var18);
				var14 /= var20;
				var16 /= var20;
				var18 /= var20;
				double var22 = 0.5D / (var20 / this.explosionSize + 0.1D);
				var22 *= (this.worldObj.rand.nextFloat()
						* this.worldObj.rand.nextFloat() + 0.3F);
				var14 *= var22;
				var16 *= var22;
				var18 *= var22;
				this.worldObj.spawnParticle("explode",
						(var8 + this.explosionX * 1.0D) / 2.0D,
						(var10 + this.explosionY * 1.0D) / 2.0D,
						(var12 + this.explosionZ * 1.0D) / 2.0D, var14, var16,
						var18);
				this.worldObj.spawnParticle("smoke", var8, var10, var12, var14,
						var16, var18);
			}

			if (block.getMaterial() != Material.air) {
				if ((block.canDropFromExplosion(this))
						&& ((!MHFCExplosion.lessenDrop) || (this.explosionRNG
								.nextFloat() < 0.2F))) {
					block.dropBlockAsItemWithChance(this.worldObj, var4, var5,
							var6,
							this.worldObj.getBlockMetadata(var4, var5, var6),
							1.0F / this.explosionSize, 0);
				}

				block.onBlockExploded(this.worldObj, var4, var5, var6, this);
			}
		}

		if (this.isFlaming) {
			var2 = this.affectedBlockPositions.iterator();

			while (var2.hasNext()) {
				ChunkPosition var3 = var2.next();
				int var4 = var3.chunkPosX;
				int var5 = var3.chunkPosY;
				int var6 = var3.chunkPosZ;
				Block block = this.worldObj.getBlock(var4, var5, var6);
				Block block1 = this.worldObj.getBlock(var4, var5 - 1, var6);

				if ((block.getMaterial() == Material.air)
						&& (block1.func_149730_j())
						&& (this.explosionRNG.nextInt(3) == 0)) {
					this.worldObj.setBlock(var4, var5, var6, Blocks.fire);
				}
			}
		}
	}

	public void explode(boolean particles) {
		func_77278_a();
		func_77279_a(particles);
		if ((MHFCExplosion.destroyBlocks)
				&& ((this.worldObj instanceof WorldServer))) {
			@SuppressWarnings("unchecked")
			Iterator<EntityPlayer> var11 = this.worldObj.playerEntities
					.iterator();
			while (var11.hasNext()) {
				EntityPlayer var12 = var11.next();

				if (var12.getDistanceSq(this.explosionX, this.explosionY,
						this.explosionZ) < 4096.0D)
					((EntityPlayerMP) var12).playerNetServerHandler
							.sendPacket(new S27PacketExplosion(this.explosionX,
									this.explosionY, this.explosionZ,
									this.explosionSize,
									this.affectedBlockPositions, func_77277_b()
											.get(var12)));
			}
		}
	}

	@Override
	public Map<EntityPlayer, Vec3> func_77277_b() {
		return this.field_77288_k;
	}
}

/*
 * Location: C:\Users\TOSHIBA\Desktop\Utilities\1.6.4
 * Mods\Mutant_Creatures_v1.4.4_mc1.6.4.zip
 * Qualified Name: thehippomaster.MutantCreatures.MCExplosion
 * JD-Core Version: 0.6.2
 */
