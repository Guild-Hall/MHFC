package mhfc.net.common.tile;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileStunTrap extends TileEntity {

	public EntityLiving entityliving;
	public Random rand;

	public TileStunTrap() {
		rand = new Random();

	}

	public void updateEntity() {
		if (entityliving != null) {
			BlockPos position = getPos();
			if (entityliving.getDistance(
					position.getX() + 0.5D,
					position.getY() + 0.20000000000000001D,
					position.getZ() + 0.5D) > 2D) {
				entityliving = null;
				return;
			}
		}
	}

}
