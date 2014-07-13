package mhfc.net.common.ai;

import mhfc.net.common.implement.iMHFC;
import mhfc.net.common.network.packet.PacketAISetter;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public abstract class AIAnimation<T extends EntityLiving & iMHFC> extends
		EntityAIBase {
	private T animatedEntity;

	public AIAnimation(T mob) {
		animatedEntity = mob;
		setMutexBits(7);
	}

	public abstract int getAnimID();

	public T getEntity() {
		return animatedEntity;
	}

	public abstract boolean isAutomatic();

	public abstract int getDuration();

	public boolean shouldAnimate() {
		return false;
	}

	@Override
	public boolean shouldExecute() {
		if (isAutomatic()) return animatedEntity.getAnimID() == getAnimID();
		return shouldAnimate();
	}

	@Override
	public void startExecuting() {
		if (!isAutomatic())
			PacketAISetter.sendAnimPacket(animatedEntity, getAnimID());
		animatedEntity.setAnimTick(0);
	}

	@Override
	public boolean continueExecuting() {
		return animatedEntity.getAnimTick() < getDuration();
	}

	@Override
	public void resetTask() {
		PacketAISetter.sendAnimPacket(animatedEntity, 0);
	}
}
