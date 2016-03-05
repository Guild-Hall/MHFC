package mhfc.net.common.entity.projectile;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * ~~ This will be base of all Entity Breathe of the monsters. Nothing special thou just a base projectile .. Make a
 * constructor and include your specified monster. add special attributes base on the methods and functions below..
 * Simple @author Heltrato
 * 
 */
public class EntityBreathe extends EntityThrowable {

	public EntityBreathe(World the_World) {
		super(the_World);
	}

	@Override
	public void onImpact(MovingObjectPosition positioing) {

	}

}
