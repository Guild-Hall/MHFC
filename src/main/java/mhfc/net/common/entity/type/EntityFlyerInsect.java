package mhfc.net.common.entity.type;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;

public class EntityFlyerInsect extends EntityCreature implements IMob {

	public double health;
	public double speed;
	public int getExpValue = 1000;
	
	
	
	public EntityFlyerInsect(World par1World) {
		super(par1World);
		 experienceValue = getExpValue;
		 getNavigator().setAvoidsWater(true);
	}

}
