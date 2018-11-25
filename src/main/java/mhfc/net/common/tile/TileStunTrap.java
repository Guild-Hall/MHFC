package mhfc.net.common.tile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class TileStunTrap extends TileEntity implements ITickable {

	public EntityLiving entityliving;
	public Random rand;

	public TileStunTrap() {
		rand = new Random();

	}

	@Override
	public void update() {
		if (entityliving == null) {
			return;
		}
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
