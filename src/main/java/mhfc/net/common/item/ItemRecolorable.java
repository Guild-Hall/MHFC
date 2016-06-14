package mhfc.net.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * A little bit of info about these. Items in Minecraft can hold four bytes of metadata, from -32,768 to 32,767 I've
 * structured the metadata of these items such that we really only need one for each possible item icon. It works as
 * follows:
 *
 * Metadata: 0xIINC
 *
 * II: 0x00-0x7F. Icon identifier. Currently goes as high as 58. N: 0x0 - 0xF. Nonce used to differentiate items of
 * otherwise identical attributes. e.g., Rathian Scale vs. Rathian Plate. C: 0x0 - 0xF. Item color. This is used by the
 * method below, getColorFromItemStack. Funny enough, it's easier to do that here than it is in 1.9. The color is based
 * off of minecraft's default color dictionary, but it uses custom-defined hex values to match Monster Hunter's palette.
 * The color names are retrieved from ItemDye.field_150921_b, an array of all default internal color names.
 *
 * So, overall, the item with the metadata = 562 would be, in hex, 0x0262. This means that it comes from Monster #2, is
 * the 6th item of that type and color, and is the 3rd color, which is green.
 *
 * Note that the item icon is not defined; this is defined by the instantiated object. This way, there only need to be,
 * at most, ~50 instances of this to represent -all- item icons!
 *
 *
 * WARNING: BEFORE INSTANTIATING THESE, YOU MUST ENSURE YOU HAVE REGISTERED ALL OF YOUR ITEMS WITH
 * {@link ItemRecolorableFactory}! OTHERWISE, ITEM SUBTYPES WILL FAIL TO REGISTER. THIS WILL LIKELY CAUSE A CRASH AND
 * NPE'S.
 *
 * @author Landon
 *
 * @param <T>
 */
public abstract class ItemRecolorable extends Item {

	public ItemRecolorable() {
		super();
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
		this.setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public abstract int getColorFromItemStack(ItemStack stack, int renderLayer);

	@Override
	public abstract void getSubItems(Item item, CreativeTabs tab, List list);
}
