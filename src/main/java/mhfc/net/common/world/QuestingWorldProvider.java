package mhfc.net.common.world;

import mhfc.net.common.world.gen.QuestingChunkProvider;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class QuestingWorldProvider extends WorldProvider {

	@Override
	public String getDimensionName() {
		return "MHFC Questing";
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return new QuestingChunkProvider(worldObj);
	}

}
