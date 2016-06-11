package mhfc.net.common.item;

import java.util.List;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.common.entity.monster.EnumEntityBigMonster;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * A little bit of info about these.
 * Items in Minecraft can hold four bytes of metadata, from -32,768 to 32,767
 * I've structured the metadata of these items such that we really only need one
 * for each possible item icon. It works as follows:
 *
 * Metadata: 0xMMNC
 *
 * MM: 0x00-0x7F. Monster identifier.
 *  N: 0x0 - 0xF. Nonce used to differentiate items of otherwise identical attributes.
 *  			  e.g., Rathian Scale vs. Rathian Plate.
 *  C: 0x0 - 0xF. Item color. This is used by the method below, getColorFromItemStack.
 *  			  Funny enough, it's easier to do that here than it is in 1.9.
 *  			  The color is based off of minecraft's default color dictionary,
 *  			  but it uses custom-defined hex values to match Monster Hunter's
 *  			  palette.
 *  			  The color names are retrieved from ItemDye.field_150921_b,
 *  			  an array of all default internal color names.
 *
 *  So, overall, the item with the metadata = 562 would be, in hex, 0x0262.
 *  This means that it comes from Monster #2, is the 6th item of that type and color,
 *  and is the 3rd color, which is green.
 *
 *  Note that the item icon is not defined; this is defined by the instantiated object.
 *  This way, there only need to be, at most, ~50 instances of this to represent -all- item icons!
 *
 *
 *  WARNING: BEFORE INSTANTIATING THESE, YOU MUST ENSURE YOU HAVE REGISTERED
 *  ALL OF YOUR ITEMS WITH {@link ItemRecolorableFactory}! OTHERWISE,
 *  ITEM SUBTYPES WILL FAIL TO REGISTER. THIS WILL LIKELY CAUSE A CRASH AND NPE'S.
 * @author Landon
 *
 * @param <T>
 */
public class ItemRecolorable extends Item{

	private final ItemRecolorableFactory registry = ItemRecolorableFactory.INSTANCE;

	protected ItemRecolorableType type;

	protected IIcon icon;

	public ItemRecolorable(ItemRecolorableType type) {
		super();
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
		this.setUnlocalizedName(type.getUnlocalizedName());
		this.type = type;
		this.setCreativeTab(MHFCMain.mhfctabs);
		this.setTextureName(type.getQualifiedName());
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return String.format("%s_%d", this.getUnlocalizedName(), stack.getItemDamage());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return ItemColor.byMetadata(stack.getItemDamage()).getColor();
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	/**
	 * Returns the metadata for the given values.
	 * @param boss   Boss ID. Eventually, this parameter will be changed to an MHFCBase.
	 * @param nonce  A nonce. If you are registering two otherwise identical items, change this value.
	 * @param color  The desired color of the item.
	 * @return metadata The calculated metadata, as described above. Range of [0, 32768]
	 */
	public static int getMetadataForValues(EnumEntityBigMonster boss, int nonce, ItemColor color) {
		int metadata  = boss == null
					  ? 127      << 8
					  : boss.ID  << 8
				 	  | nonce    << 4
				 	  | color.getMetadata();

		if(metadata < 0) {
			metadata = 0;
			FMLLog.log(Level.WARN, "[%s] Warning: Metadata generated was negative. Parameters were: Boss: %s Nonce: %d Color: %s", MHFCReference.main_modid, boss, nonce, color);
		}

		if(metadata > 0x7FFF) {
			metadata = 0x7FFF;
			FMLLog.log(Level.WARN, "[%s] Warning: Metadata generated was too high. Parameters were: Boss: %s Nonce: %d Color: %s", MHFCReference.main_modid, boss, nonce, color);
		}

		return metadata;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i : registry.getAllMetadataForType(type)){
			list.add(new ItemStack(item, 1, i));
		}
	}
}
