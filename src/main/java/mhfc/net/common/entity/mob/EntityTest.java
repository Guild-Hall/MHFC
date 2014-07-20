package mhfc.net.common.entity.mob;

import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.world.World;

public class EntityTest extends EntityMHFCBase {

	public EntityTest(World world) {
		super(world);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

}
