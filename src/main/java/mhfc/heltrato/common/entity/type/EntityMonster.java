package mhfc.heltrato.common.entity.type;

import mhfc.heltrato.common.interfaces.iElement;
import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public abstract class EntityMonster extends EntityCreature implements iElement{

	public EntityMonster(World par1World) {
		super(par1World);
	}

}