package mhfc.net.common.item;

import java.util.Set;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Designates an item with a custom model location. This is if {@link IItemVarianted} is not enough.
 *
 * @author WorldSEnder
 *
 */
public interface IItemCustomModel {
	/**
	 * Returns the model location that should be used to display the itemstack. Note that the return value of this
	 * method always has to be contained in {@link #getPossibleValues()}.
	 *
	 * @param itemStack
	 *            the itemstack that is being displayed.
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	ModelResourceLocation getModelLocation(ItemStack itemStack);

	/**
	 * Returns the set of all values that might get returned by {@link #getModelLocation(ItemStack)}. As in
	 * {@link IItemVarianted}, returning an empty collection (or <code>null</code>) here will skip registration.
	 *
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	Set<ModelResourceLocation> getPossibleValues();
}
