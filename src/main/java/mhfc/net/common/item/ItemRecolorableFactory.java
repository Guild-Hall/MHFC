package mhfc.net.common.item;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import mhfc.net.common.entity.monster.EnumEntityBigMonster;
import mhfc.net.common.util.lib.MHFCReference;

/**
 * This class is used to ensure that ALL items have a respective ID,
 * without having to register all 32,000 variants of an item.
 * @author Landon
 *
 */
public class ItemRecolorableFactory {


	private final EnumMap<ItemRecolorableType, Set<Integer>> registry;

	public static final ItemRecolorableFactory INSTANCE = new ItemRecolorableFactory();

	protected ItemRecolorableFactory() {
		registry = new EnumMap<ItemRecolorableType, Set<Integer>>(ItemRecolorableType.class);
		for(ItemRecolorableType type : ItemRecolorableType.values())
			registry.put(type, new HashSet<>());
	}

	/**
	 * Creates an item with the given parameters.
	 * It is not advised to use this method, as it auto-increments the nonce where necessary,
	 * but it is available if necessary.
	 * @param type
	 * @param boss
	 * @param nonce
	 * @param color
	 * @return
	 */
	public ItemRecolorableFactory create(ItemRecolorableType type, EnumEntityBigMonster boss, int nonce, ItemColor color) {
		FMLLog.log(Level.INFO, "[%s] Creating %s.%d.%s", MHFCReference.main_modid, boss, nonce, color);
		while(!registry.get(type).add(ItemRecolorable.getMetadataForValues(boss, nonce, color))) {
			FMLLog.log(Level.WARN, "[%s] WARNING: Incrementing nonce because item already exists!", MHFCReference.main_modid);
			nonce++;
			if(nonce > 0xF) {
				throw new IllegalArgumentException(String.format("[%s] FATAL: Ran out of nonces! Check your item ID for: %s.%s",boss,color));
			}
		}
		return this;
	}

	/**
	 * Preferred method for creating a new item. Adds an item to the metadata registry
	 * using the given parameters. This process is required for monster drops to register!
	 * @param type
	 * @param boss
	 * @param color
	 * @return
	 */
	public ItemRecolorableFactory create(ItemRecolorableType type, EnumEntityBigMonster boss, ItemColor color) {
		return create(type, boss, 0, color);
	}

	/**
	 * Returns the metadata registry for given type.
	 * The resulting set is unmodifiable.
	 * @param type
	 * @return
	 */
	public Set<Integer> getAllMetadataForType(ItemRecolorableType type) {
		return Collections.unmodifiableSet(registry.get(type));
	}

	/**
	 * For debugging and translating purposes. Generates a blank lang-file with info about each item created
	 * so that you don't have to dig through the metadata to find out who drops what.
	 * Item will be in the format:
	 * =BOSS.NONCE.COLOR
	 * @param filePath
	 */
	public void generateReport(String fileName) {
		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			int boss = 0;
			int nonce = 0;
			ItemColor color = ItemColor.BLACK;
			for(ItemRecolorableType type : ItemRecolorableType.values()) {
				writer.printf("//TYPE %s%n",type.getUnlocalizedName());
				for(Integer i : INSTANCE.getAllMetadataForType(type)) {
					boss = i >> 16 & 0xFF;
					nonce = i >> 8 & 0xF;
					color = ItemColor.byMetadata(i);
					writer.printf("item.%s_%d.name=(%s.%d.%s)%n", type.getUnlocalizedName(), i, boss, nonce, color);
				}
				writer.println();
			}
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
