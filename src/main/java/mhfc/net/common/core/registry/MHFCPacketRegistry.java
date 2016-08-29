package mhfc.net.common.core.registry;

import cpw.mods.fml.relauncher.Side;
import mhfc.net.client.quests.QuestClientInitHandler;
import mhfc.net.client.quests.QuestScreenVisualHandler;
import mhfc.net.client.quests.QuestStatusHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.BeginCraftingHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.BenchRefreshHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.CancelRecipeHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.CraftingUpdateHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.SetRecipeHandler;
import mhfc.net.common.core.registry.MHFCQuestRegistry.RegistryRequestVisualHandler;
import mhfc.net.common.core.registry.MHFCQuestRegistry.RunningSubscriptionHandler;
import mhfc.net.common.core.registry.MHFCTileRegistry.TileUpdateHandler;
import mhfc.net.common.eventhandler.MHFCInteractionHandler;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.MessageAIAction;
import mhfc.net.common.network.message.MessageAttackHandler;
import mhfc.net.common.network.message.MessageExploreTileUpdate;
import mhfc.net.common.network.message.bench.MessageBeginCrafting;
import mhfc.net.common.network.message.bench.MessageBenchRefreshRequest;
import mhfc.net.common.network.message.bench.MessageCancelRecipe;
import mhfc.net.common.network.message.bench.MessageCraftingUpdate;
import mhfc.net.common.network.message.bench.MessageSetRecipe;
import mhfc.net.common.network.message.quest.MessageMHFCInteraction;
import mhfc.net.common.network.message.quest.MessageMissionStatus;
import mhfc.net.common.network.message.quest.MessageMissionUpdate;
import mhfc.net.common.network.message.quest.MessageQuestInit;
import mhfc.net.common.network.message.quest.MessageQuestRunningSubscription;
import mhfc.net.common.network.message.quest.MessageRequestMissionUpdate;
import mhfc.net.common.network.packet.MessageTileUpdate;
import mhfc.net.common.tile.TileExploreArea.UpdateRequestHandler;

public class MHFCPacketRegistry {

	public static void init() {
		PacketPipeline.registerPacket(MessageAttackHandler.class, MessageAIAction.class, Side.CLIENT);

		PacketPipeline.registerPacket(TileUpdateHandler.class, MessageTileUpdate.class, Side.CLIENT);
		PacketPipeline.registerPacket(BeginCraftingHandler.class, MessageBeginCrafting.class, Side.SERVER);
		PacketPipeline.registerPacket(SetRecipeHandler.class, MessageSetRecipe.class, Side.SERVER);
		PacketPipeline.registerPacket(CancelRecipeHandler.class, MessageCancelRecipe.class, Side.SERVER);
		PacketPipeline.registerPacket(BenchRefreshHandler.class, MessageBenchRefreshRequest.class, Side.SERVER);
		PacketPipeline.registerPacket(CraftingUpdateHandler.class, MessageCraftingUpdate.class, Side.CLIENT);

		PacketPipeline
				.registerPacket(RegistryRequestVisualHandler.class, MessageRequestMissionUpdate.class, Side.SERVER);
		PacketPipeline.registerPacket(MHFCInteractionHandler.class, MessageMHFCInteraction.class, Side.SERVER);
		PacketPipeline
				.registerPacket(RunningSubscriptionHandler.class, MessageQuestRunningSubscription.class, Side.SERVER);

		PacketPipeline.registerPacket(QuestClientInitHandler.class, MessageQuestInit.class, Side.CLIENT);
		PacketPipeline.registerPacket(QuestScreenVisualHandler.class, MessageMissionUpdate.class, Side.CLIENT);
		PacketPipeline.registerPacket(QuestStatusHandler.class, MessageMissionStatus.class, Side.CLIENT);

		PacketPipeline.registerPacket(UpdateRequestHandler.class, MessageExploreTileUpdate.class, Side.SERVER);
	}
}
