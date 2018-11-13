package mhfc.net.common.worldedit;

import com.sk89q.jnbt.Tag;

public interface IBlockMappingTable {

	int getCompressedIdFor(com.sk89q.worldedit.world.block.BaseBlock block);

	int getBaseBlockIdFor(int compressedId);

	Tag saveToNbt();

	void loadFromNbt(Tag nbtTag);

}
