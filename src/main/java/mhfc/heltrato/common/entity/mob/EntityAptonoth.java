package mhfc.heltrato.common.entity.mob;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.world.World;
import mhfc.heltrato.common.entity.type.EntityWyvernPeaceful;
import mhfc.heltrato.common.interfaces.iMHFC;

public class EntityAptonoth extends EntityWyvernPeaceful implements iMHFC {

	public int currentAnimID;
	public int animTick;
	
	public EntityAptonoth(World par1World) {
		super(par1World);
		getNavigator().setAvoidsWater(true);
		width = 5f;
		height = 3f;
		
	}
	
	public void applyEntityAttributes(){
		super.applyEntityAttributes();
		applyMonsterAttributes(100D, 170D, 250D, 0.5D);
	}

	public void setAnimID(int id) {
		currentAnimID = id;
	}

	public void setAnimTick(int tick) {
		animTick = tick;
	}

	public int getAnimID() {
		return currentAnimID;
	}

	public int getAnimTick() {
		return animTick;
	}


}
