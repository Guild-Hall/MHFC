package mhfc.net.common.entity.projectile;

import mhfc.net.common.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWyverniaArrow extends Entity implements IProjectile{
	
	protected int  arrowHeadingX  = -1;
	protected int  arrowHeadingY  = -1;
	protected int  arrowHeadingZ  = -1;
	protected Block block;
	protected Utilities util;
	
	public int arrowData;
	
	public boolean isInGround;
	
	protected double defaultRenderDistanceWeight = 14.0D;
	
	public int canBePickedUp;  // 1 is player can pick up
	
	public int arrowShake; // recoil bow
	 
	public Entity shootingEntity; // The arrow holder;
	 
	protected int ticksInGround;
	protected int ticksInAir;
	protected double damage = 8.0D;  //Raise to third damage of the vanilla arrow.
	
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
			this.posY = theShooter.posY + (double)theShooter.getEyeHeight() - 0.10000000149011612D;
	        double d0 = target.posX - theShooter.posX;
	        double d1 = target.boundingBox.minY + (double)(target.height / 3.0F) - this.posY;
	        double d2 = target.posZ - theShooter.posZ;
	        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);

	        if (d3 >= 1.0E-7D)
	        {
	            float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
	            float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
	            double d4 = d0 / d3;
	            double d5 = d2 / d3;
	            setLocationAndAngles(theShooter.posX + d4, this.posY, theShooter.posZ + d5, f2, f3);
	            yOffset = 0.0F;
	            float f4 = (float)d3 * 0.2F;
	            setThrowableHeading(d0, d1 + (double)f4, d2, unknownA, unknownB);
	        } 
	}
	

	@Override
	public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_,
			float p_70186_8_) {
		
	}

    @Override
    protected void entityInit() {
        dataWatcher.addObject(16, Byte.valueOf((byte) 0));
        dataWatcher.addObject(17, "");
    }


	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		
	}

}
