package mhfc.net.common.eventhandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mhfc.net.common.core.registry.MHFCQuestRegistry;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction.Interaction;
import net.minecraft.entity.player.EntityPlayerMP;

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
			FMLCommonHandler.instance().bus().post(new MHFCInteractionNewQuestEvent(player, message));
			break;
		case ACCEPT_QUEST:
			FMLCommonHandler.instance().bus().post(new MHFCInteractionAcceptQuestEvent(player, message));
			break;
		case START_QUEST:
			FMLCommonHandler.instance().bus().post(new MHFCInteractionStartQuestEvent(player, message));
			break;
		case END_QUEST:
			FMLCommonHandler.instance().bus().post(new MHFCInteractionEndQuestEvent(player, message));
			break;
		case FORFEIT_QUEST:
			FMLCommonHandler.instance().bus().post(new MHFCInteractionForfeitQuestEvent(player, message));
			break;
		case MOD_RELOAD:
			FMLCommonHandler.instance().bus().post(new MHFCInteractionModReloadEvent(player, message));
			break;
		default:
			FMLCommonHandler.instance().bus().post(new MHFCInteractionEvent(player, message, Interaction.INVALID));
			break;
		}
		MHFCQuestRegistry.getRegistry().onPlayerInteraction(player, message);
	}
}
