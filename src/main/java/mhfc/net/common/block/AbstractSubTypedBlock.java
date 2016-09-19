package mhfc.net.common.block;

import java.util.List;
import java.util.Objects;

import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.SubTypedItem.SubTypeEnum;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public abstract class AbstractSubTypedBlock<T extends Enum<T> & SubTypeEnum<Block>> extends Block
		implements
		ISubTypedBlock<T> {

	protected static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(Class<T> enumClazz) {
		return PropertyEnum.create("variant", enumClazz);
	}

	protected final SubTypedItem<Block, T> blockTrait;
	protected final PropertyEnum<T> subtypeProperty;

	public AbstractSubTypedBlock(PropertyEnum<T> subtypeProperty, Material blockMaterial) {
		super(blockMaterial);
		this.blockTrait = new SubTypedItem<>(subtypeProperty);
		this.subtypeProperty = Objects.requireNonNull(subtypeProperty);
	}

	/**
	 *
	 * @return the (immutable) block trait of this sub-typed block
	 */
	@Override
	public SubTypedItem<Block, T> getBlockTrait() {
		return blockTrait;
	}

	/**
	 * This *needs* to be new BlockStateContainer(this, subtypeProperty) with the same property as given in the
	 * constructor to work correctly.
	 */
	@Override
	protected abstract BlockStateContainer createBlockState();

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
		getBlockTrait().getSubItems(item, list);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(subtypeProperty, getBlockTrait().getSubType(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return getBlockTrait().getMeta(state.getValue(subtypeProperty));
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
}
