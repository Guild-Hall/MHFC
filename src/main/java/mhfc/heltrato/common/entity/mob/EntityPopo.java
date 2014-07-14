package mhfc.heltrato.common.entity.mob;

import mhfc.heltrato.common.entity.type.EntityWyvernPeaceful;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class EntityPopo extends EntityWyvernPeaceful {

	public EntityPopo(World par1World) {
		super(par1World);
		width = 4f;
		height = 3f;
		getNavigator().setAvoidsWater(true);
	}
	
	public void applyEntityAttributes(){
		super.applyEntityAttributes();
		applyMonsterAttributes(75D, 155D, 210D, 0.5D);
	}


}
