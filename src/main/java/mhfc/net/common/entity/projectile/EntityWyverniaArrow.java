package mhfc.net.common.entity.projectile;

import mhfc.net.common.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityWyverniaArrow extends EntityArrow implements IProjectile {

	public static final float mhfc_vanilla_size_x = 0.5F;
	public static final float mhfc_vanilla_size_y = 0.5F;

	protected int arrowHeadingX = -1;
	protected int arrowHeadingY = -1;
	protected int arrowHeadingZ = -1;
	protected Block block;
	protected Utilities util;

	public int arrowData;

	protected double defaultRenderDistanceWeight = 14.0D;

	public EntityWyverniaArrow(World world) {
		super(world);
		setBaseStats();
	}

	public EntityWyverniaArrow(World world, double posX, double posY, double posZ) {
		this(world);
		setPosition(posX, posY, posZ);
	}

	public EntityWyverniaArrow(World world, EntityLivingBase theShooter, float force) {
		super(world, theShooter);
		setBaseStats();
		setAim(theShooter, theShooter.rotationPitch, theShooter.rotationYaw, 0.0F, force * 3.0F, 1.0F);
	}

	private void setBaseStats() {
		setSize(mhfc_vanilla_size_x, mhfc_vanilla_size_y);
		setDamage(12.0D);
	}

	@Override
	protected void arrowHit(EntityLivingBase living) {
		// FIXME: do something here?
	}

	@Override
	protected ItemStack getArrowStack() {
		// FIXME: Auto-generated method stub
		return null;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbtTag) {
		super.readEntityFromNBT(nbtTag);
	}

}
