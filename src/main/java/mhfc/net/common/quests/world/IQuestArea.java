package mhfc.net.common.quests.world;

import net.minecraft.entity.player.EntityPlayer;

public interface IQuestArea {

	public IQuestAreaSpawnController getSpawnController();

	public void teleportToSpawn(EntityPlayer player);

}
