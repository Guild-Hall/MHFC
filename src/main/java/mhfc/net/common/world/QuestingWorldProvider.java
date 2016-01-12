package mhfc.net.common.world;

import mhfc.net.common.world.gen.QuestingChunkProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class QuestingWorldProvider extends WorldProvider {

	@Override
	public String getDimensionName() {
		return "MHFC Questing";
	}

	@Override
	protected void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.desertHills, 0.8F);
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return new QuestingChunkProvider(worldObj);
	}

	@Override
	public boolean canMineBlock(EntityPlayer player, int x, int y, int z) {
		return false;
	}
}
