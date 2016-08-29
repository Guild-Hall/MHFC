package mhfc.net.common.eventhandler;

import mhfc.net.common.core.registry.MHFCQuestRegistry;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction.Interaction;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MHFCInteractionHandler implements IMessageHandler<MessageMHFCInteraction, IMessage> {

	@Cancelable
	public static class MHFCInteractionEvent extends Event {
		public EntityPlayerMP player;
		public MessageMHFCInteraction message;
		public Interaction interaction;

		public MHFCInteractionEvent(EntityPlayerMP player, MessageMHFCInteraction message, Interaction interaction) {
			this.player = player;
			this.message = message;
			this.interaction = interaction;
		}
	}

	public static class MHFCInteractionNewQuestEvent extends MHFCInteractionEvent {
		public MHFCInteractionNewQuestEvent(EntityPlayerMP player, MessageMHFCInteraction message) {
			super(player, message, Interaction.NEW_QUEST);
		}
	}

	public static class MHFCInteractionAcceptQuestEvent extends MHFCInteractionEvent {
		public MHFCInteractionAcceptQuestEvent(EntityPlayerMP player, MessageMHFCInteraction message) {
			super(player, message, Interaction.ACCEPT_QUEST);
		}
	}

	public static class MHFCInteractionStartQuestEvent extends MHFCInteractionEvent {
		public MHFCInteractionStartQuestEvent(EntityPlayerMP player, MessageMHFCInteraction message) {
			super(player, message, Interaction.START_QUEST);
		}
	}

	public static class MHFCInteractionEndQuestEvent extends MHFCInteractionEvent {
		public MHFCInteractionEndQuestEvent(EntityPlayerMP player, MessageMHFCInteraction message) {
			super(player, message, Interaction.END_QUEST);
		}
	}

	public static class MHFCInteractionForfeitQuestEvent extends MHFCInteractionEvent {
		public MHFCInteractionForfeitQuestEvent(EntityPlayerMP player, MessageMHFCInteraction message) {
			super(player, message, Interaction.FORFEIT_QUEST);
		}
	}

	public static class MHFCInteractionModReloadEvent extends MHFCInteractionEvent {
		public MHFCInteractionModReloadEvent(EntityPlayerMP player, MessageMHFCInteraction message) {
			super(player, message, Interaction.MOD_RELOAD);
		}
	}

	@Override
	public IMessage onMessage(MessageMHFCInteraction message, MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		onInteraction(player, message);
		return null;
	}

	public static void onInteraction(EntityPlayerMP player, MessageMHFCInteraction message) {
		switch (message.getInteraction()) {
		case NEW_QUEST:
			MinecraftForge.EVENT_BUS.post(new MHFCInteractionNewQuestEvent(player, message));
			break;
		case ACCEPT_QUEST:
			MinecraftForge.EVENT_BUS.post(new MHFCInteractionAcceptQuestEvent(player, message));
			break;
		case START_QUEST:
			MinecraftForge.EVENT_BUS.post(new MHFCInteractionStartQuestEvent(player, message));
			break;
		case END_QUEST:
			MinecraftForge.EVENT_BUS.post(new MHFCInteractionEndQuestEvent(player, message));
			break;
		case FORFEIT_QUEST:
			MinecraftForge.EVENT_BUS.post(new MHFCInteractionForfeitQuestEvent(player, message));
			break;
		case MOD_RELOAD:
			MinecraftForge.EVENT_BUS.post(new MHFCInteractionModReloadEvent(player, message));
			break;
		default:
			MinecraftForge.EVENT_BUS.post(new MHFCInteractionEvent(player, message, Interaction.INVALID));
			break;
		}
		MHFCQuestRegistry.getRegistry().onPlayerInteraction(player, message);
	}
}
