package mhfc.net.common.quests.rewards;

import mhfc.net.common.quests.api.IQuestReward;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class LootTableReward implements IQuestReward {

	private final ResourceLocation loottableLoc;

	public LootTableReward(ResourceLocation loottable) {
		this.loottableLoc = loottable;
	}

	@Override
	public void grantReward(EntityPlayerMP player) {
		int overworldDimension = 0;
		WorldServer world = FMLCommonHandler.instance().getMinecraftServerInstance()
				.worldServerForDimension(overworldDimension);
		LootTable lootTable = world.getLootTableManager().getLootTableFromLocation(loottableLoc);
		LootContext lootContext = new LootContext.Builder(world).withPlayer(player).withLuck(player.getLuck()).build();
		lootTable.generateLootForPools(player.getRNG(), lootContext);
	}

	public ResourceLocation getLootTableLocation() {
		return loottableLoc;
	}

}
