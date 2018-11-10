package mhfc.net.common.entity.projectile;

import mhfc.net.common.core.registry.MHFCItemRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ProjectileArrow extends EntityArrow {

	public static final float mhfc_vanilla_size_x = 0.5F;
	public static final float mhfc_vanilla_size_y = 0.5F;

	public int arrowData;

	protected double defaultRenderDistanceWeight = 14.0D;

	public ProjectileArrow(World world) {
		super(world);
		setBaseStats();
	}

	public ProjectileArrow(World world, double posX, double posY, double posZ) {
		this(world);
		setPosition(posX, posY, posZ);
	}

	public ProjectileArrow(World world, EntityLivingBase theShooter, float force) {
		super(world, theShooter);
		setBaseStats();
		if (theShooter instanceof EntityPlayer) {
			// this.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
		}
		setAim(theShooter, theShooter.rotationPitch, theShooter.rotationYaw, 0.0F, force * 3.0F, 1.0F);
	}

	private void setBaseStats() {
		setSize(mhfc_vanilla_size_x, mhfc_vanilla_size_y);
		setDamage(8.0D);
	}

	/**
	 * We dont want to allow to spawn wyvernia arrows inside to avoid quest world leak or issues. So we need to remove
	 * the arrow whether it hits or not. @author Heltrato
	 */

	@Override
	public void onUpdate() {
		if (this.inGround) {
			this.setDead();
		}
		super.onUpdate();
	}

	@Override
	protected void arrowHit(EntityLivingBase living) {
		// do something here?
	}

	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(MHFCItemRegistry.getRegistry().arrow);
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
