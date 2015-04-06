package mhfc.net.common.entity.type;

import java.util.List;
import java.util.Random;

import mhfc.net.common.ai.AIAttackManager;
import mhfc.net.common.ai.IExecutableAttack;
import mhfc.net.common.ai.IMangedAttacks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.IAnimatedObject;
import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.IAnimation;
import com.google.common.base.Predicate;

/**
 * This class should provide a good base to code off. As almost every entity in
 * Monster Hunter is a multi- box Entity extending this class will have you
 * covered. Instead of setting {@link Entity#width} and {@link Entity#height}
 * you should set your bounding box correctly in the constructor.
 *
 * @author WorldSEnder
 *
 * @param <YC>
 *            your class, the class of the attacks
 *
 */
public abstract class EntityMHFCBase<YC extends EntityMHFCBase<YC>>
	extends
		EntityCreature
	implements
		IEntityMultiPart,
		IAnimatedObject,
		IMangedAttacks<YC> {
	/**
	 * {@link #getDataWatcher()}
	 */
	protected static final int DATA_FRAME = 12;
	protected final AIAttackManager<YC> attackManager;

	public EntityMHFCBase(World world) {
		super(world);
		tasks.addTask(0,
			this.attackManager = new AIAttackManager<YC>((YC) this));
	}

	/**
	 * Specialize the return type to a {@link EntityMHFCPart}
	 */
	@Override
	public abstract EntityMHFCPart[] getParts();

	/**
	 * Drops the given item near this entity in the world. Utility method
	 *
	 * @param item
	 *            the item to drop
	 * @param count
	 *            the stack size
	 */
	public void dropItemRand(Item item, int count) {
		dropItemRand(new ItemStack(item, count, 0));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(DATA_FRAME, Integer.valueOf(-1));
	}

	/**
	 * Drops the given item stack as entity in the world of this entity. Utility
	 * method
	 *
	 * @param stack
	 *            the stack to drop
	 */
	public void dropItemRand(ItemStack stack) {
		Random rand = worldObj.rand;
		EntityItem entityItem = new EntityItem(this.worldObj, posX
			+ rand.nextInt(10) - 5, posY + 1.0D, posZ + rand.nextInt(10) - 5,
			stack);
		worldObj.spawnEntityInWorld(entityItem);
	}

	public double healthbaseHP(double lowhp, double medhp, double highhp) {
		// FIXME: we can do that better
		if (this.rand.nextInt(60) == 0) {
			return medhp;
		} else if (this.rand.nextInt(120) == 0) {
			return highhp;
		} else if (this.rand.nextInt(80) == 0) {
			return lowhp;
		}
		return medhp;
	}

	// FIXME: will update location, rotation set algs in 1.8, bc huge changes
	// inc
	/**
	 * Sets the location AND angles of this entity. Custom implementation
	 * because we need to be notified when the angles of the entity change.<br>
	 * This is only called once per update-tick
	 */
	// @Override
	// public void setLocationAndAngles(double posX, double posY, double posZ,
	// float yaw, float pitch) {
	// super.setLocationAndAngles(posX, posY, posZ, yaw, pitch);
	// this.setRotation(yaw, pitch);
	// super.setPosition(posX, posY, posZ);
	// this.setRotation(yaw, pitch);
	// }
	/**
	 * This is a custom implementation of the movement algorithm. It just moves
	 * the bounding box by the difference between the current and next position.
	 * No height or width, just offset the whole box.
	 */
	// @Override
	// public void setPosition(double newPosX, double newPosY, double newPosZ) {
	// hm, somehow keep the BB the same size
	// this.posX = newPosX;
	// this.posY = newPosY;
	// this.posZ = newPosZ;
	// double diffX = newPosX - this.posX;
	// double diffY = newPosY - this.posY;
	// double diffZ = newPosZ - this.posZ;
	// this.boundingBox.offset(diffX, diffY, diffZ);
	// this.offsetEntity(diffX, diffY, diffZ);
	// }
	// @Override
	// protected void setRotation(float newYaw, float newPitch) {
	// float diffYawDeg = newYaw - this.rotationYaw;
	// double diffYawRad = diffYawDeg / 180D;
	// this.rotationYaw = newYaw % 360.0F;
	// this.rotationPitch = newPitch % 360.0F;
	// EntityMHFCPart[] parts = this.getParts();
	// if (parts == null)
	// return;
	// for (EntityMHFCPart part : parts) {
	// double offXPart = part.posX - this.posX;
	// double offZPart = part.posZ - this.posZ;
	// part.posX = this.posX
	// + (Math.cos(diffYawRad) * offXPart - Math.sin(diffYawRad)
	// * offZPart);
	// part.posY = this.posY
	// + (Math.sin(diffYawRad) * offXPart + Math.cos(diffYawRad)
	// * offZPart);
	// }
	// }

	@Override
	protected boolean canDespawn() {
		return false;
	}

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
					.getCollidingBoundingBoxes(this, part.boundingBox.addCoord(
						currOffX, currOffY, currOffZ));
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
					.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(
						correctedOffX, currOffY, correctedOffZ));

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
					groundOffY = bb.calculateYOffset(this.boundingBox
						.getOffsetBoundingBox(currOffX, currOffY, currOffZ),
						groundOffY);
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
	private void offsetEntity(double offX, double offY, double offZ) {
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
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
	}

	@Override
	protected void updateAITick() {
		super.updateAITick();
		setFrame(this.attackManager.getNextFrame(getCurrentFrame()));
	}

	public void setFrame(int newframe) {
		this.dataWatcher.updateObject(DATA_FRAME, Integer.valueOf(newframe));
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
	public AIAttackManager<YC> getAttackManager() {
		return attackManager;
	}

	@Override
	public IAnimation getCurrentAnimation() {
		return this.attackManager.getCurrentAnimation();
	}

	@Override
	public int getCurrentFrame() {
		return this.dataWatcher.getWatchableObjectInt(DATA_FRAME);
	}

	@Override
	public void onAttackEnd(IExecutableAttack<YC> oldAttack) {
		setFrame(-1);
	}

	@Override
	public void onAttackStart(IExecutableAttack<YC> newAttack) {
		if (newAttack != null)
			setFrame(0);
	}

	@Override
	public Scale getScale() {
		return NO_SCALE;
	}
}
