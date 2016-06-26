package mhfc.net.common.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityLaserBeam extends Entity {
	
	private final double radius = 20;
	public EntityLivingBase caster;
	public double endPosX, endPosY, endPosZ;
	public double collidePosX, collidePosY, collidePosZ;
	
	public boolean on = true;
	public int blockSide = -1;

	public EntityLaserBeam(World worldObj) {
		super(worldObj);
		setSize(0.1F,0.1F);
		ignoreFrustumCheck = true;
		
	}
	
	public EntityLaserBeam(World worldObj, EntityLivingBase caster, double x, double y, double z, float yaw, float pitch, int duration) {
		this(worldObj);
		this.caster = caster;
		
		
		
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(2, 0f);
		dataWatcher.addObject(3, 0f);
		dataWatcher.addObject(4, 0);
		dataWatcher.addObject(5, (byte) 0);
		dataWatcher.addObject(6, 0);
		
	}

    public double getYaw() {
        return dataWatcher.getWatchableObjectFloat(2);
    }

    public void setYaw(float yaw) {
        dataWatcher.updateObject(2, yaw);
    }

    public double getPitch() {
        return dataWatcher.getWatchableObjectFloat(3);
    }

    public void setPitch(float pitch) {
        dataWatcher.updateObject(3, pitch);
    }

    public double getDuration() {
        return dataWatcher.getWatchableObjectInt(4);
    }

    public void setDuration(int duration) {
        dataWatcher.updateObject(4, duration);
    }

    public boolean getHasPlayer() {
        return dataWatcher.getWatchableObjectByte(5) == (byte) 1;
    }

    public void setHasPlayer(boolean player) {
        dataWatcher.updateObject(5, player ? (byte) 1 : (byte) 0);
    }

    public int getCasterID() {
        return dataWatcher.getWatchableObjectInt(6);
    }

    public void setCasterID(int id) {
        dataWatcher.updateObject(6, id);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        setDead();
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {

    }
    
    private void calculateEndPos() {
        endPosX = posX + radius * Math.cos(getYaw()) * Math.cos(getPitch());
        endPosZ = posZ + radius * Math.sin(getYaw()) * Math.cos(getPitch());
        endPosY = posY + radius * Math.sin(getPitch());
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean isInRangeToRenderDist(double distance) {
        return distance < 1024;
    }

    private void updateWithPlayer() {
        this.setYaw((float) ((caster.rotationYawHead + 90) * Math.PI / 180));
        this.setPitch((float) (-caster.rotationPitch * Math.PI / 180));
        this.setPosition(caster.posX, caster.posY + 1.2f, caster.posZ);
    }
    
    public TargetHit raytraceEntities(World world, Vec3 from, Vec3 to, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        TargetHit result = new TargetHit();
        result.setBlockHit(world.func_147447_a(Vec3.createVectorHelper(from.xCoord, from.yCoord, from.zCoord), to, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock));
        if (result.targetBlock != null) {
            collidePosX = result.targetBlock.hitVec.xCoord;
            collidePosY = result.targetBlock.hitVec.yCoord;
            collidePosZ = result.targetBlock.hitVec.zCoord;
            blockSide = result.targetBlock.sideHit;
        } else {
            collidePosX = endPosX;
            collidePosY = endPosY;
            collidePosZ = endPosZ;
            blockSide = -1;
        }
        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(Math.min(posX, collidePosX), Math.min(posY, collidePosY), Math.min(posZ, collidePosZ), Math.max(posX, collidePosX), Math.max(posY, collidePosY), Math.max(posZ, collidePosZ)).expand(1, 1, 1));
        for (EntityLivingBase entity : entities) {
            if (entity == caster) {
                continue;
            }
            float pad = entity.getCollisionBorderSize() + 0.5f;
            AxisAlignedBB aabb = entity.boundingBox.expand(pad, pad, pad);
            MovingObjectPosition hit = aabb.calculateIntercept(from, to);
            if (aabb.isVecInside(from)) {
                result.addEntityHit(entity);
            } else if (hit != null) {
                result.addEntityHit(entity);
            }
        }
        return result;
    }

    public static class TargetHit {
        private MovingObjectPosition targetBlock;

        private List<EntityLivingBase> entities = new ArrayList<>();

        public MovingObjectPosition getBlockHit() {
            return targetBlock;
        }

        public void setBlockHit(MovingObjectPosition blockHit) {
            this.targetBlock = blockHit;
        }

        public void addEntityHit(EntityLivingBase entity) {
            entities.add(entity);
        }
    }
	
	
	

}
