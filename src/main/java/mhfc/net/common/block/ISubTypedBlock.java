package mhfc.net.common.block;

import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.SubTypedItem.SubTypeEnum;
import net.minecraft.block.Block;

public interface ISubTypedBlock<T extends Enum<T> & SubTypeEnum<Block>> {

	/**
	 *
	 * @return the (immutable) block trait of this sub-typed block
	 */
	SubTypedItem<Block, T> getBlockTrait();

}
