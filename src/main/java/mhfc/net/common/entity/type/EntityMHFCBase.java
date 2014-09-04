package mhfc.net.common.entity.type;

import java.util.List;

import mhfc.net.client.model.mhfcmodel.animation.IAnimatedObject;
import mhfc.net.client.model.mhfcmodel.animation.IAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

/**
 * This class should provide a good base to code off. As almost every entity in
 * Monster Hunter is a multi- box Entity extending this class will have you
 * covered. Instead of setting {@link Entity#width} and {@link Entity#height}
 * you should set your bounding box correctly in the constructor.
 *
 * @author WorldSEnder
 *
 */
public abstract class EntityMHFCBase extends EntityLiving
		implements
			IEntityMultiPart,
			IAnimatedObject {

	public EntityMHFCBase(World world) {
		super(world);
		this.boundingBox.setBounds(-0.5f, 0, -0.5f, 0.5f, 1f, 0.5f);
	}

	@Override
	public void setLocationAndAngles(double posX, double posY, double posZ,
			float yaw, float pitch) {
		this.setPosition(posX, posY, posZ);
		this.setRotation(yaw, pitch);
	}

	/**
	 * This is a custom implementation of the movement algorithm. It just moves
	 * the bounding box by the difference between the current and next position.
	 * No height or width, just offset the whole box.
	 */
	@Override
	public void setPosition(double newPosX, double newPosY, double newPosZ) {
		// If there are bugs with messy bounding boxes, look here
		double diffX = newPosX - this.posX;
		double diffY = newPosY - this.posY;
		double diffZ = newPosZ - this.posZ;
		this.offsetEntity(diffX, diffY, diffZ);
	}

	@Override
	protected void setRotation(float newYaw, float newPitch) {
		float diffYawDeg = newYaw - this.rotationYaw;
		double diffYawRad = diffYawDeg / 180D;
		this.rotationYaw = newYaw % 360.0F;
		this.rotationPitch = newPitch % 360.0F;
		EntityMHFCPart[] parts = this.getParts();
		if (parts == null)
			return;
		for (EntityMHFCPart part : parts) {
			double offXPart = part.posX - this.posX;
			double offZPart = part.posZ - this.posZ;
			part.posX = this.posX
					+ (Math.cos(diffYawRad) * offXPart - Math.sin(diffYawRad)
							* offZPart);
			part.posY = this.posY
					+ (Math.sin(diffYawRad) * offXPart + Math.cos(diffYawRad)
							* offZPart);
		}
	}

	/**
	 * Specialize the return type to a {@link EntityMHFCPart}
	 */
	@Override
	public abstract EntityMHFCPart[] getParts();

	/**
	 * Tries to moves the entity by the passed in displacement. Args: x, y, z
	 *
	 * Have to reimplement this function because it fiddles with the bounding
	 * box in an unwanted way (also doesn't respect entityParts)
	 */
	@Override
	public void moveEntity(double currOffX, double currOffY, double currOffZ) {
		if (this.noClip) {
			this.offsetEntity(currOffX, currOffY, currOffZ);
		} else {
			double originalOffX = currOffX;
			double originalOffY = currOffY;
			double originalOffZ = currOffZ;

			if (this.isInWeb) {
				this.isInWeb = false;
				currOffX *= 0.25D;
				currOffY *= 0.05000000074505806D;
				currOffZ *= 0.25D;
				this.motionX = 0.0D;
				this.motionY = 0.0D;
				this.motionZ = 0.0D;
			}

			double correctedOffX = currOffX;
			double correctedOffY = currOffY;
			double correctedOffZ = currOffZ;
			EntityMHFCPart[] parts = this.getParts();
			if (parts == null)
				parts = new EntityMHFCPart[0];

			@SuppressWarnings("unchecked")
			List<AxisAlignedBB> bbsInWay = this.worldObj
					.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(
							currOffX, currOffY, currOffZ));
			// Calculates the smallest possible offset in Y direction
			for (AxisAlignedBB bb : bbsInWay) {
				currOffY = bb.calculateYOffset(this.boundingBox, currOffY);
			}
			for (AxisAlignedBB bb : bbsInWay) {
				currOffX = bb.calculateXOffset(this.boundingBox, currOffX);
			}
			for (AxisAlignedBB bb : bbsInWay) {
				currOffZ = bb.calculateZOffset(this.boundingBox, currOffZ);
			}
			for (EntityMHFCPart part : parts) {
				@SuppressWarnings("unchecked")
				List<AxisAlignedBB> bbsInWayPart = this.worldObj
						.getCollidingBoundingBoxes(this, part.boundingBox
								.addCoord(currOffX, currOffY, currOffZ));
				// Calculates the smallest possible offset in Y direction
				for (AxisAlignedBB bb : bbsInWayPart) {
					currOffY = bb.calculateYOffset(part.boundingBox, currOffY);
				}
				for (AxisAlignedBB bb : bbsInWayPart) {
					currOffX = bb.calculateXOffset(part.boundingBox, currOffX);
				}
				for (AxisAlignedBB bb : bbsInWayPart) {
					currOffZ = bb.calculateZOffset(part.boundingBox, currOffZ);
				}
			}
			/** If we will are or will land on something */
			boolean landed = this.onGround
					|| (correctedOffY != currOffY && correctedOffY < 0.0D);

			if (this.stepHeight > 0.0F && landed && (this.ySize < 0.125F)
					&& (correctedOffX != currOffX || correctedOffZ != currOffZ)) {
				double nostepOffX = currOffX;
				double nostepOffY = currOffY;
				double nostepOffZ = currOffZ;

				currOffX = correctedOffX;
				currOffY = this.stepHeight;
				currOffZ = correctedOffZ;

				@SuppressWarnings("unchecked")
				List<AxisAlignedBB> bbsInStepup = this.worldObj
						.getCollidingBoundingBoxes(this, this.boundingBox
								.addCoord(correctedOffX, currOffY,
										correctedOffZ));

				for (AxisAlignedBB bb : bbsInStepup) {
					currOffY = bb.calculateYOffset(this.boundingBox, currOffY);
				}
				for (AxisAlignedBB bb : bbsInStepup) {
					currOffX = bb.calculateXOffset(this.boundingBox, currOffX);
				}
				for (AxisAlignedBB bb : bbsInStepup) {
					currOffZ = bb.calculateZOffset(this.boundingBox, currOffZ);
				}
				for (EntityMHFCPart part : parts) {
					@SuppressWarnings("unchecked")
					List<AxisAlignedBB> bbsInStepupPart = this.worldObj
							.getCollidingBoundingBoxes(this, part.boundingBox
									.addCoord(currOffX, currOffY, currOffZ));
					for (AxisAlignedBB bb : bbsInStepupPart) {
						currOffY = bb.calculateYOffset(part.boundingBox,
								currOffY);
					}
					for (AxisAlignedBB bb : bbsInStepupPart) {
						currOffX = bb.calculateXOffset(part.boundingBox,
								currOffX);
					}
					for (AxisAlignedBB bb : bbsInStepupPart) {
						currOffZ = bb.calculateZOffset(part.boundingBox,
								currOffZ);
					}
				}

				double groundOffY = (-this.stepHeight);
				for (AxisAlignedBB bb : bbsInStepup) {
					groundOffY = bb.calculateYOffset(
							this.boundingBox.getOffsetBoundingBox(currOffX,
									currOffY, currOffZ), groundOffY);
				}
				for (EntityMHFCPart part : parts) {
					@SuppressWarnings("unchecked")
					List<AxisAlignedBB> bbsInStepDown = this.worldObj
							.getCollidingBoundingBoxes(this, part.boundingBox
									.addCoord(currOffX, currOffY, currOffZ));
					// Calculates the smallest possible offset in Y direction
					for (AxisAlignedBB bb : bbsInStepDown) {
						currOffY = bb.calculateYOffset(part.boundingBox,
								currOffY);
					}
				}
				currOffY += groundOffY;

				if (nostepOffX * nostepOffX + nostepOffY * nostepOffY >= currOffX
						* currOffX + currOffZ * currOffZ) {
					currOffX = nostepOffX;
					currOffY = nostepOffY;
					currOffZ = nostepOffZ;
				}
			}
			// Until this point, unlike the original implementation, nothing
			// happened to the
			// entity itself, push the state
			double originalX = this.posX;
			double originalY = this.posY;
			double originalZ = this.posZ;
			AxisAlignedBB originalBB = this.boundingBox.copy();
			// Handle things like fire, movesounds, etc
			super.moveEntity(originalOffX, originalOffY, originalOffZ);
			// Pop the state
			this.posX = originalX;
			this.posY = originalY;
			this.posZ = originalZ;
			this.boundingBox.setBB(originalBB);
			// Apply our offset
			this.offsetEntity(currOffX, currOffY, currOffZ);
		}
	}

	/**
	 * Convenience function, no checks, just offset
	 */
	private final void offsetEntity(double offX, double offY, double offZ) {
		this.boundingBox.offset(offX, offY, offZ);

		this.posX += offX;
		this.posY += offY;
		this.posZ += offZ;
		EntityMHFCPart[] parts = this.getParts();
		if (parts == null)
			return;
		for (EntityMHFCPart part : parts) {
			part.offsetEntity(offX, offY, offZ);
		}
	}

	/**
	 * Unknown purpose, worldObj is public
	 */
	@Override
	public World func_82194_d() {
		return this.worldObj;
	}

	@Override
	public boolean attackEntityFromPart(EntityDragonPart part,
			DamageSource damageSource, float damageValue) {
		// TODO handle attacked from
		return false;
	}
	/**
	 * This implementation just forwards the call to the predicate of the
	 * currently executed animation.
	 */
	@Override
	public Predicate<String> getPartPredicate(float subFrame) {
		return IAnimatedObject.RENDER_ALL;
	}

	@Override
	public IAnimation getCurrentAnimation() {
		// TODO: get this from the AI
		return null;
	}

	@Override
	public int getCurrentFrame() {
		// TODO: get this from the AI
		return 0;
	}
}
