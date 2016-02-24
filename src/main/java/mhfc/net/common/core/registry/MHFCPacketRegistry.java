package mhfc.net.common.core.registry;

import cpw.mods.fml.relauncher.Side;
import mhfc.net.client.quests.MHFCRegQuestVisual.QuestClientInitHandler;
import mhfc.net.client.quests.MHFCRegQuestVisual.QuestScreenVisualHandler;
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
import mhfc.net.common.network.message.MessageAIAttack;
import mhfc.net.common.network.message.MessageAttackHandler;
import mhfc.net.common.network.message.MessageExploreTileUpdate;
import mhfc.net.common.network.message.bench.MessageBeginCrafting;
import mhfc.net.common.network.message.bench.MessageBenchRefreshRequest;
import mhfc.net.common.network.message.bench.MessageCancelRecipe;
import mhfc.net.common.network.message.bench.MessageCraftingUpdate;
import mhfc.net.common.network.message.bench.MessageSetRecipe;
import mhfc.net.common.network.packet.MessageMHFCInteraction;
import mhfc.net.common.network.packet.MessageQuestInit;
import mhfc.net.common.network.packet.MessageQuestRunningSubscription;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.network.packet.MessageRequestQuestVisual;
import mhfc.net.common.network.packet.MessageTileUpdate;
import mhfc.net.common.tile.TileExploreArea.UpdateRequestHandler;

public class MHFCPacketRegistry {

	public static void init() {
		PacketPipeline.registerPacket(MessageAttackHandler.class, MessageAIAttack.class, Side.CLIENT);

		PacketPipeline.registerPacket(TileUpdateHandler.class, MessageTileUpdate.class, Side.CLIENT);
		PacketPipeline.registerPacket(BeginCraftingHandler.class, MessageBeginCrafting.class, Side.SERVER);
		PacketPipeline.registerPacket(SetRecipeHandler.class, MessageSetRecipe.class, Side.SERVER);
		PacketPipeline.registerPacket(CancelRecipeHandler.class, MessageCancelRecipe.class, Side.SERVER);
		PacketPipeline.registerPacket(BenchRefreshHandler.class, MessageBenchRefreshRequest.class, Side.SERVER);
		PacketPipeline.registerPacket(CraftingUpdateHandler.class, MessageCraftingUpdate.class, Side.CLIENT);

		PacketPipeline.registerPacket(RegistryRequestVisualHandler.class, MessageRequestQuestVisual.class, Side.SERVER);
		PacketPipeline.registerPacket(MHFCInteractionHandler.class, MessageMHFCInteraction.class, Side.SERVER);
		PacketPipeline
				.registerPacket(RunningSubscriptionHandler.class, MessageQuestRunningSubscription.class, Side.SERVER);

		PacketPipeline.registerPacket(QuestClientInitHandler.class, MessageQuestInit.class, Side.CLIENT);
		PacketPipeline.registerPacket(QuestScreenVisualHandler.class, MessageQuestVisual.class, Side.CLIENT);

		PacketPipeline.registerPacket(UpdateRequestHandler.class, MessageExploreTileUpdate.class, Side.SERVER);
	}
}
