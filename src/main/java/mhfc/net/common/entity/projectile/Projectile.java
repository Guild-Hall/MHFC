package mhfc.net.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public abstract class Projectile extends Entity{
	
	public EntityLivingBase projectileUser;
    private int ticksAlive;
    private int ticksInAir;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;

	public Projectile(World worldobj) {
		super(worldobj);
	}

	protected void entityInit()
    {
    }

}