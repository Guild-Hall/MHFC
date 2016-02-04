package mhfc.net.common.world.area;

import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.quests.world.SpawnControllerAdapter;
import mhfc.net.common.quests.world.SpawnControllerAdapter.SpawnInformation;
import mhfc.net.common.world.controller.CornerPosition;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class EmptyArea extends AreaAdapter {

	protected IQuestAreaSpawnController spawnController;

	public class SpawnController extends SpawnControllerAdapter {
		public SpawnController() {
			super(EmptyArea.this);
		}

		@Override
		protected void enqueDefaultSpawns() {}

		@Override
		protected SpawnInformation constructDefaultSpawnInformation(Entity entity) {
			return EmptyArea.this.constructDefaultSpawnInformation(entity);
		}
	}

	public EmptyArea(World world) {
		super(world);
	}

	public EmptyArea(World world, CornerPosition pos, AreaConfiguration config) {
		super(world, pos, config);
		spawnController = new SpawnController();
	}

	@Override
	public void loadFromConfig(CornerPosition pos, AreaConfiguration config) {
		super.loadFromConfig(pos, config);
		spawnController = new SpawnController();
	}

	@Override
	public IQuestAreaSpawnController getSpawnController() {
		return spawnController;
	}

	public abstract SpawnInformation constructDefaultSpawnInformation(Entity entity);
}
