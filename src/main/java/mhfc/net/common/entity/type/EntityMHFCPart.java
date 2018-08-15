package mhfc.net.common.entity.type;

import mhfc.net.common.util.math.NonAxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class EntityMHFCPart extends EntityDragonPart {

	private Vec3d offset;
	private Entity entityParent;
	private NonAxisAlignedBB baseBoundingBox;
	private NonAxisAlignedBB currentBoundingBox;

	public <T extends Entity & IEntityMultiPart> EntityMHFCPart(T parent, String partName, Vec3d offset) {
		super(parent, partName, 0, 0);
		this.entityParent = parent;
		this.offset = offset;
	}

	/**
	 * Convenience function, no checks, just offset
	 *
	 * @return
	 */
	protected final void offsetEntity(double offX, double offY, double offZ) {
		this.offset = offset.addVector(offX, offY, offZ);
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	/**
	 * Returns true if Entity argument is equal to this Entity
	 */
	@Override
	public boolean isEntityEqual(Entity entityIn) {
		return super.isEntityEqual(entityIn) || this.entityDragonObj == entityIn;
	}

	public IEntityMultiPart getOwningEntity() {
		return entityDragonObj;
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		updateBoundingBox();
	}

	@Override
	public AxisAlignedBB getEntityBoundingBox() {
		return this.currentBoundingBox;
	}

	@Override
	public void setEntityBoundingBox(AxisAlignedBB bb) {
		this.currentBoundingBox = NonAxisAlignedBB.wrapping(bb);
	}

	private void updateBoundingBox() {
		this.currentBoundingBox = transformNAABB(this.baseBoundingBox);
	}

	private void updateBaseBoundingBox() {
		this.baseBoundingBox = untransformNAABB(this.currentBoundingBox);
	}

	@Override
	protected void setSize(float width, float height) {
		super.setSize(width, height);
		double halfWidth = width / 2;
		setEntityBoundingBox(new NonAxisAlignedBB(-halfWidth, 0, -halfWidth, halfWidth, height, halfWidth));
		updateBaseBoundingBox();
	}

	private NonAxisAlignedBB untransformNAABB(NonAxisAlignedBB wrapped) {
		double yawParent = Math.toRadians(entityParent.rotationYaw);
		double pitchParent = Math.toRadians(entityParent.rotationPitch);
		Vec3d offsetParent = entityParent.getPositionVector();
		double yaw = Math.toRadians(this.rotationYaw);
		double pitch = Math.toRadians(this.rotationPitch);
		Vec3d offset = this.getPositionVector();
		return wrapped.offset(offset.scale(-1)).rotate(1, 0, 0, -pitch).rotate(0, 1, 0, -yaw)
				.offset(offsetParent.scale(-1)).rotate(1, 0, 0, -pitchParent).rotate(0, 1, 0, -yawParent);
	}

	private NonAxisAlignedBB transformNAABB(NonAxisAlignedBB baseBox) {
		double yawParent = Math.toRadians(entityParent.rotationYaw);
		double pitchParent = Math.toRadians(entityParent.rotationPitch);
		Vec3d offsetParent = entityParent.getPositionVector();
		double yaw = Math.toRadians(this.rotationYaw);
		double pitch = Math.toRadians(this.rotationPitch);
		Vec3d offset = this.getPositionVector();
		return baseBox.rotate(0, 1, 0, yawParent).rotate(1, 0, 0, pitchParent).offset(offsetParent)//
				.rotate(0, 1, 0, yaw).rotate(1, 0, 0, pitch).offset(offset);
	}
}
