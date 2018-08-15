package mhfc.net.common.quests.rewards;

import java.util.List;

import mhfc.net.common.quests.api.IQuestReward;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
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
		List<ItemStack> rewards = lootTable.generateLootForPools(player.getRNG(), lootContext);

		boolean anyItemInInventory = false;
		for (ItemStack reward : rewards) {
			boolean wasAdded = player.inventory.addItemStackToInventory(reward);
			anyItemInInventory |= wasAdded;
			if (!wasAdded || !reward.isEmpty()) {
				EntityItem droppedItem = player.dropItem(reward, false);
				if (droppedItem != null) {
					droppedItem.setNoPickupDelay();
					droppedItem.setOwner(player.getName());
				}
			}
		}
		if (anyItemInInventory) {
			// TODO: maybe also play a little sound...
			player.inventoryContainer.detectAndSendChanges();
		}
	}

	public ResourceLocation getLootTableLocation() {
		return loottableLoc;
	}

}
