package mhfc.net.common.entity.projectile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.common.helper.DamageHelper;
import mhfc.net.common.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityWyverniaArrow extends EntityArrow implements IProjectile{
	
	protected int  arrowHeadingX  = -1;
	protected int  arrowHeadingY  = -1;
	protected int  arrowHeadingZ  = -1;
	protected Block block;
	protected Utilities util;
	
	public int arrowData;
	
	public boolean isitInGround;
	
	protected double defaultRenderDistanceWeight = 14.0D;
	
	
	 
	 
	protected int ticksisInGround;
	protected int ticksInAir;
	protected double damage = 12.0D;  //Raise to third damage of the vanilla arrow.
	
    /** The amount of knockback an arrow applies when it hits a mob. */
    private int knockbackStrength;
    
	public EntityWyverniaArrow(World world) {
		super(world);
		renderDistanceWeight = defaultRenderDistanceWeight;
		setSize(util.mhfc_vanilla_size_x, util.mhfc_vanilla_size_y);
	}
	
	public EntityWyverniaArrow(World world, double posX, double posY, double posZ) {
		super(world);
		renderDistanceWeight = defaultRenderDistanceWeight;
		setSize(util.mhfc_vanilla_size_x, util.mhfc_vanilla_size_y);
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
	}
	
	// i dont know exactly whats par1 and par2 atm. @Heltrato
	public EntityWyverniaArrow(World world, EntityLivingBase theShooter, EntityLivingBase target, float unknownA, float unknownB){
		super(world);
		renderDistanceWeight = defaultRenderDistanceWeight;
		shootingEntity = theShooter;
		if(theShooter instanceof EntityPlayer){
			canBePickedUp = 1;
		}
			posY = theShooter.posY + (double)theShooter.getEyeHeight() - 0.10000000149011612D;
	        double d0 = target.posX - theShooter.posX;
	        double d1 = target.boundingBox.minY + (double)(target.height / 3.0F) - posY;
	        double d2 = target.posZ - theShooter.posZ;
	        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);

	        if (d3 >= 1.0E-7D)
	        {
	            float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
	            float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
	            double d4 = d0 / d3;
	            double d5 = d2 / d3;
	            setLocationAndAngles(theShooter.posX + d4, posY, theShooter.posZ + d5, f2, f3);
	            yOffset = 0.0F;
	            float f4 = (float)d3 * 0.2F;
	            setThrowableHeading(d0, d1 + (double)f4, d2, unknownA, unknownB);
	        } 
	}
	
	public EntityWyverniaArrow(World world, EntityLivingBase theShooter, float unknownA)
	    {
		super(world);
		renderDistanceWeight = defaultRenderDistanceWeight;
		shootingEntity = theShooter;
		if (theShooter instanceof EntityPlayer)
		{
	            canBePickedUp = 1;
		}

	        setSize(0.5F, 0.5F);
	        setLocationAndAngles(theShooter.posX, theShooter.posY + (double)theShooter.getEyeHeight(), theShooter.posZ, theShooter.rotationYaw, theShooter.rotationPitch);
	        posX -= (double)(MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
	        posY -= 0.10000000149011612D;
	        posZ -= (double)(MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
	        setPosition(posX, posY, posZ);
	        yOffset = 0.0F;
	        motionX = (double)(-MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI));
	        motionZ = (double)(MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI));
	        motionY = (double)(-MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI));
	        setThrowableHeading(motionX, motionY, motionZ, unknownA * 1.5F, 1.0F);
	    }
	
	 public void setKnockbackStrength(int p_70240_1_)
	    {
	        knockbackStrength = p_70240_1_;
	    }

	
    public void setThrowableHeading(double par1, double par2, double par3, float par4, float par5)
    {
        float x2 = MathHelper.sqrt_double(par1 * par1 + par2 * par2 + par3 * par3);
        par1 /= (double)x2;
        par2 /= (double)x2;
        par3 /= (double)x2;
        par1 += rand.nextGaussian() * (double)(rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par5;
        par2 += rand.nextGaussian() * (double)(rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par5;
        par3 += rand.nextGaussian() * (double)(rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par5;
        par1 *= (double)par4;
        par2 *= (double)par4;
        par3 *= (double)par4;
        motionX = par1;
        motionY = par2;
        motionZ = par3;
        float x3 = MathHelper.sqrt_double(par1 * par1 + par3 * par3);
        prevRotationYaw = rotationYaw = (float)(Math.atan2(par1, par3) * 180.0D / Math.PI);
        prevRotationPitch = rotationPitch = (float)(Math.atan2(par2, (double)x3) * 180.0D / Math.PI);
        ticksisInGround = 0;
    }
    
    public double getDamage()
    {
        return damage;
    }
    
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par2, double par3, float float1, float float2, int par4)
    {
        setPosition(par1, par2, par3);
        setRotation(float1, float2);
    }
    
    public void setDamage(double damaX)
    {
        this.damage = damaX;
    }

    
    // diz is displacment.
    @SideOnly(Side.CLIENT)
    public void setVelocity(double dizX, double disY, double disZ)
    {
        motionX = dizX;
        motionY = disY;
        motionZ = disZ;

        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float y1 = MathHelper.sqrt_double(dizX * dizX + disZ * disZ);
            prevRotationYaw = rotationYaw = (float)(Math.atan2(dizX, disZ) * 180.0D / Math.PI);
            prevRotationPitch = rotationPitch = (float)(Math.atan2(disY, (double)y1) * 180.0D / Math.PI);
            prevRotationPitch = rotationPitch;
            prevRotationYaw = rotationYaw;
            setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
            ticksisInGround = 0;
        }
    }
    
    public void onUpdate()
    {
        super.onEntityUpdate();

        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            prevRotationYaw = rotationYaw = (float)(Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
            prevRotationPitch = rotationPitch = (float)(Math.atan2(motionY, (double)f) * 180.0D / Math.PI);
        }

        Block blockz = worldObj.getBlock(arrowHeadingX, arrowHeadingY, arrowHeadingZ);

        if (blockz.getMaterial() != Material.air)
        {
        	blockz.setBlockBoundsBasedOnState(worldObj, arrowHeadingX, arrowHeadingY, arrowHeadingZ);
            AxisAlignedBB axisalignedbb = blockz.getCollisionBoundingBoxFromPool(worldObj, arrowHeadingX, arrowHeadingY, arrowHeadingZ);

            if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(posX, posY, posZ)))
            {
                isitInGround = true;
            }
        }

        if (arrowShake > 0)
        {
            --arrowShake;
        }

        if (isitInGround)
        {
            int j = worldObj.getBlockMetadata(arrowHeadingX, arrowHeadingY, arrowHeadingZ);

            if (block == blockz && j == arrowData)
            {
                ++ticksisInGround;

                if (ticksisInGround == 1200)
                {
                    setDead();
                }
            }
            else
            {
                isitInGround = false;
                motionX *= (double)(rand.nextFloat() * 0.2F);
                motionY *= (double)(rand.nextFloat() * 0.2F);
                motionZ *= (double)(rand.nextFloat() * 0.2F);
                ticksisInGround = 0;
                ticksInAir = 0;
            }
        }
        else
        {
            ++ticksInAir;
            Vec3 vec31 = Vec3.createVectorHelper(posX, posY, posZ);
            Vec3 vec3 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
            MovingObjectPosition movingobjectposition = worldObj.func_147447_a(vec31, vec3, false, true, false);
            vec31 = Vec3.createVectorHelper(posX, posY, posZ);
            vec3 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);

            if (movingobjectposition != null)
            {
                vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }

            Entity entity = null;
            @SuppressWarnings("rawtypes")
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            int i;
            float f1;

            for (i = 0; i < list.size(); ++i)
            {
                Entity entity1 = (Entity)list.get(i);

                if (entity1.canBeCollidedWith() && (entity1 != shootingEntity || ticksInAir >= 5))
                {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double)f1, (double)f1, (double)f1);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

                    if (movingobjectposition1 != null)
                    {
                        double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null)
            {
                movingobjectposition = new MovingObjectPosition(entity);
            }

            if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;

                if (entityplayer.capabilities.disableDamage || shootingEntity instanceof EntityPlayer && !((EntityPlayer)shootingEntity).canAttackPlayer(entityplayer))
                {
                    movingobjectposition = null;
                }
            }

            float f2;
            float f4;

            if (movingobjectposition != null)
            {
                if (movingobjectposition.entityHit != null)
                {
                    f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                    int k = MathHelper.ceiling_double_int((double)f2 * damage);

                    if (getIsCritical())
                    {
                        k += rand.nextInt(k / 2 + 2);
                    }

                    DamageSource damagesource = null;

                    if (shootingEntity == null)
                    {
                        damagesource = DamageHelper.causeWyverniaArrow(this, this);
                    }
                    else
                    {
                        damagesource = DamageHelper.causeWyverniaArrow(this, shootingEntity);
                    }

                    if (isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
                    {
                        movingobjectposition.entityHit.setFire(5);
                    }

                    if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float)k))
                    {
                        if (movingobjectposition.entityHit instanceof EntityLivingBase)
                        {
                            EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;

                            if (!worldObj.isRemote)
                            {
                                entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
                            }

                            if (knockbackStrength > 0)
                            {
                                f4 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);

                                if (f4 > 0.0F)
                                {
                                    movingobjectposition.entityHit.addVelocity(motionX * (double)knockbackStrength * 0.6000000238418579D / (double)f4, 0.1D, motionZ * (double)knockbackStrength * 0.6000000238418579D / (double)f4);
                                }
                            }

                            if (shootingEntity != null && shootingEntity instanceof EntityLivingBase)
                            {
                                EnchantmentHelper.func_151384_a(entitylivingbase, shootingEntity);
                                EnchantmentHelper.func_151385_b((EntityLivingBase)shootingEntity, entitylivingbase);
                            }

                            if (shootingEntity != null && movingobjectposition.entityHit != shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && shootingEntity instanceof EntityPlayerMP)
                            {
                                ((EntityPlayerMP)shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                            }
                        }

                        playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));

                        if (!(movingobjectposition.entityHit instanceof EntityEnderman))
                        {
                            setDead();
                        }
                    }
                    else
                    {
                        motionX *= -0.10000000149011612D;
                        motionY *= -0.10000000149011612D;
                        motionZ *= -0.10000000149011612D;
                        rotationYaw += 180.0F;
                        prevRotationYaw += 180.0F;
                        ticksInAir = 0;
                    }
                }
                else
                {
                    arrowHeadingX = movingobjectposition.blockX;
                    arrowHeadingY = movingobjectposition.blockY;
                    arrowHeadingZ = movingobjectposition.blockZ;
                    block = worldObj.getBlock(arrowHeadingX, arrowHeadingY, arrowHeadingZ);
                    arrowData = worldObj.getBlockMetadata(arrowHeadingX, arrowHeadingY, arrowHeadingZ);
                    motionX = (double)((float)(movingobjectposition.hitVec.xCoord - posX));
                    motionY = (double)((float)(movingobjectposition.hitVec.yCoord - posY));
                    motionZ = (double)((float)(movingobjectposition.hitVec.zCoord - posZ));
                    f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                    posX -= motionX / (double)f2 * 0.05000000074505806D;
                    posY -= motionY / (double)f2 * 0.05000000074505806D;
                    posZ -= motionZ / (double)f2 * 0.05000000074505806D;
                    playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
                    isitInGround = true;
                    arrowShake = 7;
                    setIsCritical(false);

                    if (block.getMaterial() != Material.air)
                    {
                        block.onEntityCollidedWithBlock(worldObj, arrowHeadingX, arrowHeadingY, arrowHeadingZ, this);
                    }
                }
            }

            if (getIsCritical())
            {
                for (i = 0; i < 4; ++i)
                {
                    worldObj.spawnParticle("crit", posX + motionX * (double)i / 4.0D, posY + motionY * (double)i / 4.0D, posZ + motionZ * (double)i / 4.0D, -motionX, -motionY + 0.2D, -motionZ);
                }
            }

            posX += motionX;
            posY += motionY;
            posZ += motionZ;
            f2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            rotationYaw = (float)(Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

            for (rotationPitch = (float)(Math.atan2(motionY, (double)f2) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
            {
                ;
            }

            while (rotationPitch - prevRotationPitch >= 180.0F)
            {
                prevRotationPitch += 360.0F;
            }

            while (rotationYaw - prevRotationYaw < -180.0F)
            {
                prevRotationYaw -= 360.0F;
            }

            while (rotationYaw - prevRotationYaw >= 180.0F)
            {
                prevRotationYaw += 360.0F;
            }

            rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
            rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
            float f3 = 0.99F;
            f1 = 0.05F;

            if (isInWater())
            {
                for (int l = 0; l < 4; ++l)
                {
                    f4 = 0.25F;
                    worldObj.spawnParticle("bubble", posX - motionX * (double)f4, posY - motionY * (double)f4, posZ - motionZ * (double)f4, motionX, motionY, motionZ);
                }

                f3 = 0.8F;
            }

            if (isWet())
            {
                extinguish();
            }

            motionX *= (double)f3;
            motionY *= (double)f3;
            motionZ *= (double)f3;
            motionY -= (double)f1;
            setPosition(posX, posY, posZ);
            func_145775_I();
        }
    }
    
    @Override
    protected void entityInit() {
        dataWatcher.addObject(16, Byte.valueOf((byte) 0));
        dataWatcher.addObject(17, "");
    }


	
	@Override
	public void writeEntityToNBT(NBTTagCompound writeNBT) {
		writeNBT.setShort("XTile", (short)arrowHeadingX);
		writeNBT.setShort("YTile", (short)arrowHeadingY);
		writeNBT.setShort("ZTile", (short)arrowHeadingZ);
		writeNBT.setShort("Life", (short)ticksisInGround);
		writeNBT.setByte("InTile", (byte)Block.getIdFromBlock(block));
		writeNBT.setByte("InData", (byte)arrowData);
		writeNBT.setByte("Shake", (byte)arrowShake);
		writeNBT.setByte("InGround", (byte)(isitInGround ? 1 : 0));
		writeNBT.setByte("Pickup", (byte)canBePickedUp);
		writeNBT.setDouble("Damage", damage);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        arrowHeadingX = p_70037_1_.getShort("XTile");
        arrowHeadingY = p_70037_1_.getShort("YTile");
        arrowHeadingZ = p_70037_1_.getShort("ZTile");
        ticksisInGround = p_70037_1_.getShort("Life");
        block = Block.getBlockById(p_70037_1_.getByte("InTile") & 255);
        arrowData = p_70037_1_.getByte("InData") & 255;
        arrowShake = p_70037_1_.getByte("Shake") & 255;
        isitInGround = p_70037_1_.getByte("InGround") == 1;

        if (p_70037_1_.hasKey("Damage", 99))
        {
            damage = p_70037_1_.getDouble("Damage");
        }

        if (p_70037_1_.hasKey("Pickup", 99))
        {
            canBePickedUp = p_70037_1_.getByte("Pickup");
        }
        else if (p_70037_1_.hasKey("Player", 99))
        {
            canBePickedUp = p_70037_1_.getBoolean("Player") ? 1 : 0;
        }
    }

	
	public boolean getIsCritical()
	{
	        byte b0 = dataWatcher.getWatchableObjectByte(16);
	        return (b0 & 1) != 0;
	}

	
    public void setIsCritical(boolean istrue)
    {
        byte b0 = dataWatcher.getWatchableObjectByte(16);

        if (istrue)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -2)));
        }
    }
}
