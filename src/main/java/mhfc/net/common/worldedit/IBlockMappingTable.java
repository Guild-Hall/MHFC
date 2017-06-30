package mhfc.net.common.worldedit;

import com.sk89q.jnbt.Tag;
import com.sk89q.worldedit.blocks.BaseBlock;

public interface IBlockMappingTable {

	int getCompressedIdFor(BaseBlock block);

	int getBaseBlockIdFor(int compressedId);

	Tag saveToNbt();

	void loadFromNbt(Tag nbtTag);

}
