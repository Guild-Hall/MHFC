package mhfc.net.common.block;

import mhfc.net.common.core.registry.MHFCBlockRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBlockVarianted {

	/**
	 * Implement this in a block that is also registered in {@link MHFCBlockRegistry} to automatically get registered in
	 * {@link ModelLoader#setCustomStateMapper(net.minecraft.block.Block, net.minecraft.client.renderer.block.statemap.IStateMapper)}.<br>
	 * Returning <code>null</code> will completely skip registration for this block state and default to the normal
	 * location.
	 *
	 * @param state
	 *            the state being polled
	 * @return the variant name
	 *
	 */
	@SideOnly(Side.CLIENT)
	String getVariantName(IBlockState state);

}
