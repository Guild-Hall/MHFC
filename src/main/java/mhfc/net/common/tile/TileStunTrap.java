package mhfc.net.common.tile;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;

public class TileStunTrap extends TileEntity {

	public EntityLiving entityliving;
	public Random rand;

	public TileStunTrap() {
		rand = new Random();

	}

	@Override
	public void updateEntity() {

		if (entityliving != null) {
			if (entityliving.getDistance(xCoord + 0.5D,
					yCoord + 0.20000000000000001D, zCoord + 0.5D) > 2D) {
				entityliving = null;
				return;
			}

		}
	}

}
