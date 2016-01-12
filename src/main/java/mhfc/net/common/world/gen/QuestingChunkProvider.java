package mhfc.net.common.world.gen;

import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

// Pretty much a void generator
public class QuestingChunkProvider implements IChunkProvider {
	private World world;

	public QuestingChunkProvider(World world) {
		this.world = world;
	}

	@Override
	public boolean chunkExists(int p_73149_1_, int p_73149_2_) {
		return true;
	}

	@Override
	public Chunk provideChunk(int x, int z) {
		return new Chunk(world, x, z);
	}

	@Override
	public Chunk loadChunk(int x, int z) {
		return this.provideChunk(x, z);
	}

	@Override
	public void populate(IChunkProvider provider, int x, int z) {
		;
	}

	@Override
	public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_) {
		return true;
	}

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public String makeString() {
		return "Questing World";
	}

	@Override
	public List getPossibleCreatures(EnumCreatureType type, int x, int y, int z) {
		return Collections.emptyList();
	}

	@Override
	public ChunkPosition func_147416_a(
			World p_147416_1_,
			String p_147416_2_,
			int p_147416_3_,
			int p_147416_4_,
			int p_147416_5_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void recreateStructures(int p_82695_1_, int p_82695_2_) {
		;
	}

	@Override
	public void saveExtraData() {
		;
	}

}
