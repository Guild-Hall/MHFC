package mhfc.net.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ProjectileUnit extends Entity {
	
	/**
	 * 
	 *  This projectile class is for monster users only.
	 *  
	 * 
	 * 
	 */
	
	private int shotDamage;
	private EntityLivingBase shooter;
	private EntityLivingBase target;
	private ArrayList<Entity> pointedEntities;
	private double targetPosX;
	private double targetPosY;
	private double targetPosZ;

	public ProjectileUnit(World worldIn) {
		super(worldIn);
		shotDamage = (10 + this.rand.nextInt(3));
		ignoreFrustumCheck = true;
		noClip = true;
		shooter = null;
		pointedEntities = new ArrayList<Entity>();
	}
	
	public ProjectileUnit(World worldIn, EntityLivingBase shooter, EntityLivingBase target) {
		super(worldIn);
		this.shooter = shooter;
		this.target = target;
		if(target != null) {
		targetPosX = target.posX;
		targetPosY = target.posY;
		targetPosZ = target.posZ;
		}
		double yPos = shooter.posY + shooter.getEyeHeight();
		setPosition(shooter.posX, yPos, shooter.posZ);
		double x = getTargetX() - this.posX;double y = getTargetY() - this.posY;double z = getTargetZ() - this.posZ;
		 double d = Math.sqrt(x * x + z * z);
		 this.rotationYaw = (180.0F + (float)Math.toDegrees(Math.atan2(x, z)));
		 this.rotationPitch = ((float)Math.toDegrees(Math.atan2(y, d)));
	}


	@Override
	protected void entityInit() {
	}
	
	

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		compound.setInteger("targetPosX", (int) this.targetPosX);
		compound.setInteger("targetPosX", (int) this.targetPosY);
		compound.setInteger("targetPosX", (int) this.targetPosX);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		this.targetPosX = compound.getInteger("targetPosX");
		this.targetPosY = compound.getInteger("targetPosY");
		this.targetPosZ = compound.getInteger("targetPosZ");
	}
	
	public double getTargetY() {
		return targetPosY;
	}
	
	public double getTargetX() {
		return targetPosX;
	}
	
	public double getTargetZ() {
		return targetPosZ;
	}
	
	
}