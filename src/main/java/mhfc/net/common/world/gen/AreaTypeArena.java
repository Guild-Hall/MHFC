package mhfc.net.common.world.gen;

import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.controller.CornerPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class AreaTypeArena implements IAreaType {
	private static class AreaArena implements IArea {

		@Override
		public IQuestAreaSpawnController getSpawnController() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void teleportToSpawn(EntityPlayer player) {
			// TODO Auto-generated method stub

		}

		@Override
		public String getUnlocalizedDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void loadFromConfig(AreaConfiguration config) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public IArea populate(World world, CornerPosition lowerLeftCorner, AreaConfiguration configuration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IArea provideForLoading(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AreaConfiguration configForNewArea() {
		return new AreaConfiguration(1, 1);
	}

	@Override
	public AreaConfiguration configForLoading() {
		return new AreaConfiguration();
	}
}
