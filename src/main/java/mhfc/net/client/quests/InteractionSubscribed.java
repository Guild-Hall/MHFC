package mhfc.net.client.quests;

import mhfc.net.common.core.registry.MHFCRegQuests;
import mhfc.net.common.network.packet.MessageQuestInteraction;
import mhfc.net.common.network.packet.MessageQuestInteraction.Interaction;
import net.minecraftforge.event.CommandEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class InteractionSubscribed {

	private boolean taken = false;

	@SubscribeEvent
	public void onText(CommandEvent event) {
		if (taken) {
			System.out.println("Sent start");
			MHFCRegQuests.networkWrapper
					.sendToServer(new MessageQuestInteraction(
							Interaction.VOTE_START, new String[0]));
			taken = false;
		} else {
			System.out.println("Sent new");
			MHFCRegQuests.networkWrapper
					.sendToServer(new MessageQuestInteraction(
							Interaction.START_NEW, event.parameters[0]));
			taken = true;
		}
	}
}
