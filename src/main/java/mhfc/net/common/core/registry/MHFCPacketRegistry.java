package mhfc.net.common.core.registry;

import mhfc.net.client.quests.MHFCRegQuestVisual.QuestScreenVisualHandler;
import mhfc.net.client.quests.MHFCRegQuestVisual.QuestVisualInitHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.BeginCraftingHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.CancelRecipeHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.CraftingUpdateHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.BenchRefreshHandler;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry.SetRecipeHandler;
import mhfc.net.common.core.registry.MHFCQuestsRegistry.PlayerQuestInteractionHandler;
import mhfc.net.common.core.registry.MHFCQuestsRegistry.RegistryRequestVisualHandler;
import mhfc.net.common.core.registry.MHFCQuestsRegistry.RunningSubscriptionHandler;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.MessageAIAttack;
import mhfc.net.common.network.message.MessageAttackHandler;
import mhfc.net.common.network.packet.MessageQuestInteraction;
import mhfc.net.common.network.packet.MessageQuestRunningSubscription;
import mhfc.net.common.network.packet.MessageQuestScreenInit;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.network.packet.MessageRequestQuestVisual;
import mhfc.net.common.network.packet.bench.MessageBeginCrafting;
import mhfc.net.common.network.packet.bench.MessageBenchRefreshRequest;
import mhfc.net.common.network.packet.bench.MessageCancelRecipe;
import mhfc.net.common.network.packet.bench.MessageCraftingUpdate;
import mhfc.net.common.network.packet.bench.MessageSetRecipe;
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
		PacketPipeline.registerPacket(PlayerQuestInteractionHandler.class,
				MessageQuestInteraction.class, Side.SERVER);
		PacketPipeline.registerPacket(RunningSubscriptionHandler.class,
				MessageQuestRunningSubscription.class, Side.SERVER);

		PacketPipeline.registerPacket(QuestVisualInitHandler.class,
				MessageQuestScreenInit.class, Side.CLIENT);
		PacketPipeline.registerPacket(QuestScreenVisualHandler.class,
				MessageQuestVisual.class, Side.CLIENT);
	}
}
