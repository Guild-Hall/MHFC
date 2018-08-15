package mhfc.net.common.item;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A default implementation of IItemCustomModel if only one model (but a custom one) is needed after all.
 *
 * @author WorldSEnder
 *
 */
public interface IItemSimpleModel extends IItemCustomModel {
	@SideOnly(Side.CLIENT)
	ModelResourceLocation getModel();

	@Override
	@SideOnly(Side.CLIENT)
	default ModelResourceLocation getModelLocation(ItemStack itemStack) {
		return getModel();
	}

	@Override
	default Set<ModelResourceLocation> getPossibleValues() {
		return ImmutableSet.of(getModel());
	}
}
