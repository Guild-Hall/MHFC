package mhfc.net.common.core.registry;

import mhfc.net.client.quests.MHFCRegQuestVisual.QuestScreenVisualHandler;
import mhfc.net.client.quests.MHFCRegQuestVisual.QuestVisualInitHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.BeginCraftingHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.BenchRefreshHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.CancelRecipeHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.CraftingUpdateHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.SetRecipeHandler;
import mhfc.net.common.core.registry.MHFCQuestRegistry.RegistryRequestVisualHandler;
import mhfc.net.common.core.registry.MHFCQuestRegistry.RunningSubscriptionHandler;
import mhfc.net.common.eventhandler.MHFCInteractionHandler;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.MessageAIAttack;
import mhfc.net.common.network.message.MessageAttackHandler;
import mhfc.net.common.network.message.bench.*;
import mhfc.net.common.network.packet.*;
import cpw.mods.fml.relauncher.Side;

public class MHFCPacketRegistry {

	public static void init() {
		PacketPipeline.registerPacket(MessageAttackHandler.class,
			MessageAIAttack.class, Side.CLIENT);

		PacketPipeline.registerPacket(BeginCraftingHandler.class,
			MessageBeginCrafting.class, Side.SERVER);
		PacketPipeline.registerPacket(SetRecipeHandler.class,
			MessageSetRecipe.class, Side.SERVER);
		PacketPipeline.registerPacket(CancelRecipeHandler.class,
			MessageCancelRecipe.class, Side.SERVER);
		PacketPipeline.registerPacket(BenchRefreshHandler.class,
			MessageBenchRefreshRequest.class, Side.SERVER);
		PacketPipeline.registerPacket(CraftingUpdateHandler.class,
			MessageCraftingUpdate.class, Side.CLIENT);

		PacketPipeline.registerPacket(RegistryRequestVisualHandler.class,
			MessageRequestQuestVisual.class, Side.SERVER);
		PacketPipeline.registerPacket(MHFCInteractionHandler.class,
			MessageMHFCInteraction.class, Side.SERVER);
		PacketPipeline.registerPacket(RunningSubscriptionHandler.class,
			MessageQuestRunningSubscription.class, Side.SERVER);

		PacketPipeline.registerPacket(QuestVisualInitHandler.class,
			MessageQuestScreenInit.class, Side.CLIENT);
		PacketPipeline.registerPacket(QuestScreenVisualHandler.class,
			MessageQuestVisual.class, Side.CLIENT);
	}
}
