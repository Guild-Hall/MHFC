package mhfc.net.common.item;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.item.ItemDye;

/**
 * Enum defining all item colors, as well as their custom color
 * in the Monster Hunter palette. May move the palette
 * definition to a config file later...
 * @author Landon
 *
 */
public enum ItemColor {
	BLACK ( 0, 0x303030),
	RED   ( 1, 0xF85858),
	GREEN ( 2, 0x54843B),
	BROWN ( 3, 0xBD9729),
	BLUE  ( 4, 0x5870A0),
	PURPLE( 5, 0xB890C0),
	CYAN  ( 6, 0x31A0A0),
	SILVER( 7, 0x686868),
	GRAY  ( 8, 0xA0A0A0),
	PINK  ( 9, 0xE890A0),
	LIME  (10, 0x80B050),
	YELLOW(11, 0xF8D058),
	LIBLUE(12, 0x9BE1F5),
	MAGNTA(13, 0xF931F7),
	ORANGE(14, 0xF89858),
	WHITE (15, 0xFFFFFF);
	private static final String[] colorNames = ItemDye.field_150921_b;
	private final int metadata;
	private final int color;
	private ItemColor(int metadata, int color) {
		this.metadata = metadata;
		this.color = color;
	}

	/**
	 * Returns associated metadata.
	 * @return the metadata
	 */
	public int getMetadata() {
		return metadata;
	}

	/**
	 * Returns the color as an integer ranging from
	 * 0x000000 (0) to 0xFFFFFF (16,777,215)
	 * @return the color
	 */
	public int getRGB() {
		return color;
	}

	/**
	 * Returns the color associated with metadata.
	 * Note that the metadata can be of ANY length;
	 * it only looks at the last byte.
	 * @param metadata
	 * @return the color
	 */
	public static ItemColor byMetadata(int metadata) {
		int col = metadata & 0xF;
		for(ItemColor iColor : ItemColor.values()) {
			if(iColor.getMetadata() == col)
				return iColor;
		}

		MHFCMain.logger.log(Level.ERROR, "[%s] ERROR: Unable to find color for metadata %x! Defaulting to WHITE.", MHFCReference.main_modid, metadata);
		return ItemColor.WHITE;
	}
}