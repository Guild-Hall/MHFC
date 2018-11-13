package mhfc.net.common.item.block;

import java.util.List;
import java.util.Objects;

import mhfc.net.common.block.ISubTypedBlock;
import mhfc.net.common.item.IItemVarianted;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.SubTypedItem.SubTypeEnum;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemSubtypedBlock<T extends Enum<T> & SubTypeEnum<Block>> extends ItemBlock implements IItemVarianted {

	protected final SubTypedItem<Block, T> blockPerk;

	protected ItemSubtypedBlock(Class<T> enumClass, Block baseBlock) {
		this(new SubTypedItem<>(enumClass), baseBlock);
	}

	protected ItemSubtypedBlock(SubTypedItem<Block, T> blockTrait, Block baseBlock) {
		super(baseBlock);
		blockPerk = Objects.requireNonNull(blockTrait);
	}

	@Override
	public List<String> getVariantNames() {
		return blockPerk.getVariants();
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return super.getTranslationKey() + "." + blockPerk.getSubType(stack).getUnlocalizedName();
	}

	@Override
	public int getMetadata(int damage) {
		// Effectively clamps the range to an accepted range
		return blockPerk.getMeta(blockPerk.getSubType(damage));
	}

	public static <T extends Enum<T> & SubTypeEnum<Block>, B extends Block & ISubTypedBlock<T>> ItemSubtypedBlock<T> createFor(
			B block) {
		return new ItemSubtypedBlock<>(block.getBlockTrait(), block);
	}

}
