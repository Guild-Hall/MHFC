package mhfc.net.common.world;

import mhfc.net.common.core.registry.MHFCDimensionRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.gen.ChunkProviderVoid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class WorldProviderQuesting extends WorldProvider {
	private QuestFlair flair;

	public WorldProviderQuesting() {
		super();
	}

	public QuestFlair getQuestFlair() {
		if (flair == null) {
			// Maybe fallback to this.getDimension();
			throw new IllegalStateException("Called too early, flair not set yet");
		}
		return flair;
	}

	@Override
	public void setDimension(int dim) {
		super.setDimension(dim);
		flair = MHFCDimensionRegistry.getQuestingFlair(dim);
	}

	@Override
	public DimensionType getDimensionType() {
		return MHFCDimensionRegistry.getDimensionType();
	}

	@Override
	public BlockPos getRandomizedSpawnPoint() {
		BlockPos.MutableBlockPos spawn = new BlockPos.MutableBlockPos(world.getSpawnPoint());
		spawn.setY(world.getTopSolidOrLiquidBlock(spawn).getY());
		return spawn;
	}

	@Override
	public void setWorldTime(long time) {
		if (!world.isRemote) {
			// Don't allow updates of world time by other means than accessing the world info
			// Note that this does let through ticks, as long as the game rule "doDaylightCycle"
			// is set to false, but will block commands like /time
			return;
		}
		super.setWorldTime(time);
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkProviderVoid(world);
	}

	@Override
	public void updateWeather() {
		// Ignore any weather updates (commands pass through :|)
		return;
	}

	@Override
	public void resetRainAndThunder() {
		// Ignore any weather updates
		return;
	}

	@Override
	public void calculateInitialWeather() {
		// As much as I'd like to move this to registerWorldChunkManager, I can't
		// ^ called too early in the constructor, where the perWorldStorage hasn't been loaded yet
		// FIXME: read the MHFCSaveData
		this.world.getWorldInfo().setWorldTime(flair.worldTime);
		this.world.getWorldInfo().setRaining(false);

		this.world.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
		this.world.getGameRules().setOrCreateGameRule("doMobSpawning", "false");
		this.world.getGameRules().setOrCreateGameRule("keepInventory", "true");
		this.world.getGameRules().setOrCreateGameRule("doFireTick", "false");

		super.calculateInitialWeather();
	}
}
