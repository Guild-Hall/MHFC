package mhfc.heltrato.common.entity.type;

import mhfc.heltrato.common.interfaces.iElement;
import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;

public class EntityMonster extends EntityCreature implements iElement{

	public EntityMonster(World par1World) {
		super(par1World);
	}

	public boolean thunderElement() {
		return false;
	}

	public boolean fireElement() {
		return false;
	}

	public boolean waterElement() {
		return false;
	}

	public boolean isStunVulnerable() {
		return false;
	}

}
