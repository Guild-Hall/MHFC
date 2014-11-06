package mhfc.net.common.network.message;

import io.netty.buffer.ByteBuf;
import mhfc.net.common.ai.IMangedAttacks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MessageAIAttack<T extends EntityLivingBase & IMangedAttacks<T>>
		implements
			IMessage {
	private int entityId;
	private int attackIndex;

	public MessageAIAttack() {}

	public MessageAIAttack(T entity, int attackIndex) {
		this.entityId = entity.getEntityId();
		this.attackIndex = attackIndex;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityId = buf.readInt();
		attackIndex = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityId);
		buf.writeInt(attackIndex);
	}

	/**
	 * Used to retrieve the meant actual entity on client side. May return
	 * <code>null</code> if the entity isn't loaded on client side.
	 *
	 * @return the entity
	 */
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public <U extends EntityLivingBase & IMangedAttacks<U>> IMangedAttacks<U> getEntity() {
		return (IMangedAttacks<U>) Minecraft.getMinecraft().theWorld
				.getEntityByID(entityId);
	}

	public int getAttackIndex() {
		return this.attackIndex;
	}
}
