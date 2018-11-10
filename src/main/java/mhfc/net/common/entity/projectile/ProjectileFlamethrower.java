package mhfc.net.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ProjectileFlamethrower extends Entity {

	public ProjectileFlamethrower(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
		this.setNoGravity(true);
		this.height = 0.5F;
		this.setSize(0.25F, 0.25F);
		
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		
	}

}
