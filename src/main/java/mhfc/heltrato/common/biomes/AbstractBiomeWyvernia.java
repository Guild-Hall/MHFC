package mhfc.heltrato.common.biomes;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class AbstractBiomeWyvernia {
	
	
	public BiomeGenBase basing;
	
	public AbstractBiomeWyvernia() {
	}
	
	public abstract void getBiomeSize(float height, float width);
	public abstract void getBiomeIDperQuest(int ID);
	public abstract void getCoordinateSpawn(ChunkCoordinates coor, int x , int y, int z);
	public abstract void getParticlesToSpawn(Block block, EntityFX fx, int temp);
	public abstract void getBiomeGenName(String savename, int retakeID);

}
