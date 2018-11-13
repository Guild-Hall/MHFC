package mhfc.net.common.world.gen;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldTypeQuesting extends WorldType {

	public WorldTypeQuesting() {
		super("questing");
	}

	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		return new ChunkProviderVoid(world);
	}

	@Override
	public int getVersion() {
		return 1;
	}

	@Override
	public int getSpawnFuzz(WorldServer world, MinecraftServer server) {
		return 1;
	}

	@Override
	public boolean canBeCreated() {
		return false;
	}

}
