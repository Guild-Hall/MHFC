package mhfc.net.common.network.message;

import io.netty.buffer.ByteBuf;
import mhfc.net.common.ai.IManagedActions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MessageAIAction<T extends EntityLiving & IManagedActions<T>>
	implements
		IMessage {
	private int entityId;
	private int targetId;
	private int attackIndex;

	public MessageAIAction() {
	}

	public MessageAIAction(T entity, int attackIndex) {
		this.entityId = entity.getEntityId();
		if (entity.getAttackTarget() != null) {
			this.targetId = entity.getAttackTarget().getEntityId();
		} else {
			this.targetId = -1;
		}
		this.attackIndex = attackIndex;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityId = buf.readInt();
		attackIndex = buf.readInt();
		targetId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityId);
		buf.writeInt(attackIndex);
		buf.writeInt(targetId);
	}

	/**
	 * Used to retrieve the meant actual entity on client side. May return
	 * <code>null</code> if the entity isn't loaded on client side.
	 *
	 * @return the entity
	 */
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public <U extends EntityLiving & IManagedActions<U>> IManagedActions<U> getEntity() {
		return (IManagedActions<U>) Minecraft.getMinecraft().theWorld
			.getEntityByID(entityId);
	}

	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public <U extends EntityLiving & IManagedActions<U>> U getEntityLiving() {
		return (U) Minecraft.getMinecraft().theWorld.getEntityByID(entityId);
	}

	public EntityLivingBase getTarget() {
		return (EntityLivingBase) Minecraft.getMinecraft().theWorld
			.getEntityByID(targetId);
	}

	public int getAttackIndex() {
		return this.attackIndex;
	}
}
