package mhfc.net.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Projectile extends Entity implements IProjectile{
	
	public EntityLivingBase projectileUser;
    private int ticksAlive;
    private int ticksInAir;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;

	public Projectile(World worldobj) {
		super(worldobj);
	}
	
	 /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
	@Override
    public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }
	
	@Override
	public void onUpdate()
    {
		  if (this.world.isRemote || (this.projectileUser == null || !this.projectileUser.isDead) && this.world.isBlockLoaded(new BlockPos(this)))
	        {
	            super.onUpdate();


	            ++this.ticksInAir;
	            RayTraceResult raytraceresult = ProjectileHelper.forwardsRaycast(this, true, this.ticksInAir >= 25, this.projectileUser);

	            if (raytraceresult != null)
	            {
	                this.onImpact(raytraceresult);
	            }

	            this.posX += this.motionX;
	            this.posY += this.motionY;
	            this.posZ += this.motionZ;
	            ProjectileHelper.rotateTowardsMovement(this, 0.2F);
	            float f = this.getMotionFactor();

	            if (this.isInWater())
	            {
	                for (int i = 0; i < 4; ++i)
	                {
	                    this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ, new int[0]);
	                }

	                f = 0.8F;
	            }

	            this.motionX += this.accelerationX;
	            this.motionY += this.accelerationY;
	            this.motionZ += this.accelerationZ;
	            this.motionX *= (double)f;
	            this.motionY *= (double)f;
	            this.motionZ *= (double)f;
	            this.world.spawnParticle(this.getParticleType(), this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
	            this.setPosition(this.posX, this.posY, this.posZ);
	        }
	        else
	        {
	            this.setDead();
	        }
    }

	protected EnumParticleTypes getParticleType()
	{
    return EnumParticleTypes.SMOKE_LARGE;
	}	

	
	 /**
     * Checks if the entity is in range to render.
     */
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;

        if (Double.isNaN(d0))
        {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }

	protected void entityInit()
    {
    }
	
    public boolean canBeCollidedWith(){
        return true;
    }

    public float getCollisionBorderSize(){
        return 1.0F;
    }

	
	
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setTag("direction", this.newDoubleNBTList(new double[] {this.motionX, this.motionY, this.motionZ}));
        compound.setTag("power", this.newDoubleNBTList(new double[] {this.accelerationX, this.accelerationY, this.accelerationZ}));
        compound.setInteger("life", this.ticksAlive);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        if (compound.hasKey("power", 9))
        {
            NBTTagList nbttaglist = compound.getTagList("power", 6);

            if (nbttaglist.tagCount() == 3)
            {
                this.accelerationX = nbttaglist.getDoubleAt(0);
                this.accelerationY = nbttaglist.getDoubleAt(1);
                this.accelerationZ = nbttaglist.getDoubleAt(2);
            }
        }

        this.ticksAlive = compound.getInteger("life");

        if (compound.hasKey("direction", 9) && compound.getTagList("direction", 6).tagCount() == 3)
        {
            NBTTagList nbttaglist1 = compound.getTagList("direction", 6);
            this.motionX = nbttaglist1.getDoubleAt(0);
            this.motionY = nbttaglist1.getDoubleAt(1);
            this.motionZ = nbttaglist1.getDoubleAt(2);
        }
        else
        {
            this.setDead();
        }
    }

	
	  /**
     * Return the motion factor for this projectile. The factor is multiplied by the original motion.
     */
    protected float getMotionFactor()
    {
        return 0.95F;
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
    	
    }

    public static void registerFixesFireball(DataFixer fixer, String name)
    {
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            this.setBeenAttacked();

            if (source.getEntity() != null)
            {
                Vec3d vec3d = source.getEntity().getLookVec();

                if (vec3d != null)
                {
                    this.motionX = vec3d.xCoord;
                    this.motionY = vec3d.yCoord;
                    this.motionZ = vec3d.zCoord;
                    this.accelerationX = this.motionX * 0.1D;
                    this.accelerationY = this.motionY * 0.1D;
                    this.accelerationZ = this.motionZ * 0.1D;
                }

                if (source.getEntity() instanceof EntityLivingBase)
                {
                    this.projectileUser = (EntityLivingBase)source.getEntity();
                }

                return true;
            }
            else
            {
                return false;
            }
        }
    }
    
    // Default
    protected float getGravityVelocity() {
		return 0.15f;
	}
    
    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float partialTicks)
    {
        return 1.0F;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float partialTicks)
    {
        return 15728880;
    }

}