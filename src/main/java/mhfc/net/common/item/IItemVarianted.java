package mhfc.net.common.item;

import mhfc.net.common.core.registry.MHFCItemRegistry;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface IItemVarianted {
	/**
	 * Implement this in an item that is also registered in {@link MHFCItemRegistry} to automatically get registered in
	 * {@link ModelBakery#registerItemVariants(net.minecraft.item.Item, net.minecraft.util.ResourceLocation...)}.<br>
	 * The item variants are expected to be represented by the meta values [0,names.size())<br>
	 * Returning <code>null</code> or an empty list will completely skip registration for this item.
	 *
	 * @return a list of variant names.
	 */
	@SideOnly(Side.CLIENT)
	List<String> getVariantNames();
}
