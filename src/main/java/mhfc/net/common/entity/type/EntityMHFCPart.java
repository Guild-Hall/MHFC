package mhfc.net.common.entity.type;

import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.world.World;

public class EntityMHFCPart extends EntityDragonPart {

	public EntityMHFCPart(World world) {
		super(null, null, 0, 0);
		throw new IllegalStateException("Can't create a Part as lone entity");
	}
	public EntityMHFCPart(IEntityMultiPart parent, double offX, double offY,
			double offZ) {
		super(parent, "", 0F, 0F);
		this.posX = offX;
		this.posY = offY;
		this.posZ = offZ;
	}
	/**
	 * Convenience function, no checks, just offset
	 *
	 * @return
	 */
	protected final void offsetEntity(double offX, double offY, double offZ) {
		this.boundingBox.offset(offX, offY, offZ);
		this.posX += offX;
		this.posY += offY;
		this.posZ += offZ;
	}
}
