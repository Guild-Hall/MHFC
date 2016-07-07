package mhfc.net.common.world.area;

import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.quests.world.SpawnControllerAdapter;
import mhfc.net.common.quests.world.SpawnControllerAdapter.SpawnInformation;
import mhfc.net.common.quests.world.SpawnControllerAdapter.Spawnable;
import net.minecraft.world.World;

public abstract class EmptyArea extends AreaAdapter {
	public class SpawnController extends SpawnControllerAdapter {
		public SpawnController() {
			super(EmptyArea.this.worldView);
		}

		@Override
		protected void enqueDefaultSpawns() {}

		@Override
		protected SpawnInformation constructDefaultSpawnInformation(Spawnable entity) {
			return EmptyArea.this.constructDefaultSpawnInformation(entity);
		}
	}

	public EmptyArea(World world, AreaConfiguration config) {
		super(world, config);
	}

	@Override
	protected IQuestAreaSpawnController initializeSpawnController() {
		SpawnController spawnController = new SpawnController();
		spawnController.setAreaInstance(this);
		return spawnController;
	}

	public abstract SpawnInformation constructDefaultSpawnInformation(Spawnable entity);
}
