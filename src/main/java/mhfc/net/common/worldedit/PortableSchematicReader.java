package mhfc.net.common.worldedit;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import com.sk89q.jnbt.ByteArrayTag;
import com.sk89q.jnbt.CompoundTag;
import com.sk89q.jnbt.IntTag;
import com.sk89q.jnbt.ListTag;
import com.sk89q.jnbt.NBTInputStream;
import com.sk89q.jnbt.NamedTag;
import com.sk89q.jnbt.ShortTag;
import com.sk89q.jnbt.StringTag;
import com.sk89q.jnbt.Tag;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.entity.EntityType;
import com.sk89q.worldedit.world.entity.EntityTypes;
import com.sk89q.worldedit.world.registry.LegacyMapper;
import com.sk89q.worldedit.world.storage.NBTConversions;

public class PortableSchematicReader implements ClipboardReader {
	private static final Logger log = Logger.getLogger(PortableSchematicReader.class.getCanonicalName());

	private final NBTInputStream inputStream;
	private final IBlockMappingTable blockMappingTable;

	public PortableSchematicReader(NBTInputStream input, IBlockMappingTable mappingTable) {
		inputStream = input;
		blockMappingTable = mappingTable;
	}

	@Override
	public Clipboard read() throws IOException {
		// Same implementation as in SchematicWriter, except
		// - block-id are determined via the blockMappingTable
		// - the name is "Portable-Schematic" to disambiguate
		// - the mappingTable is saved in a tag "Mappings"
		// Schematic tag
		final NamedTag rootTag = inputStream.readNamedTag();
		if (!rootTag.getName().equals("Portable-Schematic")) {
			throw new IOException("Tag 'Portable-Schematic' does not exist or is not first");
		}
		final CompoundTag schematicTag = (CompoundTag) rootTag.getTag();

		// Check
		final Map<String, Tag> schematic = schematicTag.getValue();
		// ====================================================================
		// Metadata
		// ====================================================================
		final short width = requireTag(schematic, "Width", ShortTag.class).getValue();
		final short length = requireTag(schematic, "Length", ShortTag.class).getValue();
		final short height = requireTag(schematic, "Height", ShortTag.class).getValue();
		final String materials = requireTag(schematic, "Materials", StringTag.class).getValue();
		if (!materials.equals("Alpha")) {
			throw new IOException("Schematic file is not an Alpha schematic");
		}

		Vector origin;
		Region region;
		try {
			final int originX = requireTag(schematic, "WEOriginX", IntTag.class).getValue();
			final int originY = requireTag(schematic, "WEOriginY", IntTag.class).getValue();
			final int originZ = requireTag(schematic, "WEOriginZ", IntTag.class).getValue();
			final Vector min = new Vector(originX, originY, originZ);

			final int offsetX = requireTag(schematic, "WEOffsetX", IntTag.class).getValue();
			final int offsetY = requireTag(schematic, "WEOffsetY", IntTag.class).getValue();
			final int offsetZ = requireTag(schematic, "WEOffsetZ", IntTag.class).getValue();
			final Vector offset = new Vector(offsetX, offsetY, offsetZ);

			origin = min.subtract(offset);
			region = new CuboidRegion(min, min.add(width, height, length).subtract(Vector.ONE));
		} catch (final IOException ignored) {
			origin = new Vector(0, 0, 0);
			region = new CuboidRegion(origin, origin.add(width, height, length).subtract(Vector.ONE));
		}

		// ====================================================================
		// Block handling
		// ====================================================================
		final byte[] blockId = requireTag(schematic, "Blocks", ByteArrayTag.class).getValue();
		final byte[] blockData = requireTag(schematic, "Data", ByteArrayTag.class).getValue();
		byte[] addId = new byte[0];
		// We support 4096 block IDs using the same method as vanilla Minecraft, where
		// the highest 4 bits are stored in a separate byte array.
		if (schematic.containsKey("AddBlocks")) {
			addId = requireTag(schematic, "AddBlocks", ByteArrayTag.class).getValue();
		}
		final List<Tag> tileEntities = requireTag(schematic, "TileEntities", ListTag.class).getValue();
		blockMappingTable.loadFromNbt(requireTag(schematic, "Mappings", CompoundTag.class));

		// Get blocks
		final short[] blocks = new short[blockId.length]; // Have to later combine IDs

		// Combine the AddBlocks data with the first 8-bit block ID
		for (int index = 0; index < blockId.length; index++) {
			if ((index >> 1) >= addId.length) { // No corresponding AddBlocks index
				blocks[index] = (short) (blockId[index] & 0xFF);
			} else {
				if ((index & 1) == 0) {
					blocks[index] = (short) (((addId[index >> 1] & 0x0F) << 8) + (blockId[index] & 0xFF));
				} else {
					blocks[index] = (short) (((addId[index >> 1] & 0xF0) << 4) + (blockId[index] & 0xFF));
				}
			}
		}

		// Need to pull out tile entities
		final Map<BlockVector, Map<String, Tag>> tileEntitiesMap = new HashMap<>();

		for (final Tag tag : tileEntities) {
			if (!(tag instanceof CompoundTag)) {
				continue;
			}
			final CompoundTag t = (CompoundTag) tag;

			int x = 0;
			int y = 0;
			int z = 0;

			final Map<String, Tag> values = new HashMap<>();

			for (final Map.Entry<String, Tag> entry : t.getValue().entrySet()) {
				if (entry.getKey().equals("x")) {
					if (entry.getValue() instanceof IntTag) {
						x = ((IntTag) entry.getValue()).getValue();
					}
				} else if (entry.getKey().equals("y")) {
					if (entry.getValue() instanceof IntTag) {
						y = ((IntTag) entry.getValue()).getValue();
					}
				} else if (entry.getKey().equals("z")) {
					if (entry.getValue() instanceof IntTag) {
						z = ((IntTag) entry.getValue()).getValue();
					}
				}

				values.put(entry.getKey(), entry.getValue());
			}

			final BlockVector vec = new BlockVector(x, y, z);
			tileEntitiesMap.put(vec, values);
		}

		final BlockArrayClipboard clipboard = new BlockArrayClipboard(region);
		clipboard.setOrigin(origin);

		// Don't log a torrent of errors
		int failedBlockSets = 0;

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				for (int z = 0; z < length; ++z) {
					final int index = y * width * length + z * width + x;
					final BlockVector pt = new BlockVector(x, y, z);
					final int id = blocks[index];
					LegacyMapper LEGACY_MAPPER = LegacyMapper.getInstance();
					final BlockState blockState = LEGACY_MAPPER
							.getBlockFromLegacy(blockMappingTable.getBaseBlockIdFor(id), blockData[index]);

					final BaseBlock block;
					if (tileEntitiesMap.containsKey(pt)) {
						block = blockState.toBaseBlock(new CompoundTag(tileEntitiesMap.get(pt)));
					} else {
						block = blockState.toBaseBlock();
					}

					try {
						clipboard.setBlock(region.getMinimumPoint().add(pt), block);
					} catch (final WorldEditException e) {
						switch (failedBlockSets) {
						case 0:
							log.log(Level.WARNING, "Failed to set block on a Clipboard", e);
							break;
						case 1:
							log.log(
									Level.WARNING,
									"Failed to set block on a Clipboard (again) -- no more messages will be logged",
									e);
							break;
						default:
						}

						failedBlockSets++;
					}
				}
			}
		}

		// ====================================================================
		// Entities
		// ====================================================================

		try {
			final List<Tag> entityTags = requireTag(schematic, "Entities", ListTag.class).getValue();

			for (final Tag tag : entityTags) {
				if (tag instanceof CompoundTag) {
					final CompoundTag compound = (CompoundTag) tag;
					final EntityType id = EntityTypes.get(compound.getString("id"));
					final Location location = NBTConversions
							.toLocation(clipboard, compound.getListTag("Pos"), compound.getListTag("Rotation"));
					final BaseEntity state = new BaseEntity(id, compound);
					clipboard.createEntity(location, state);
				}
			}
		} catch (final IOException ignored) { // No entities? No problem
		}

		return clipboard;
	}

	@Override
	public void close() throws IOException {
		inputStream.close();
	}

	private static <T extends Tag> T requireTag(Map<String, Tag> items, String key, Class<T> expected)
			throws IOException {
		if (!items.containsKey(key)) {
			throw new IOException("Schematic file is missing a \"" + key + "\" tag");
		}

		final Tag tag = items.get(key);
		if (!expected.isInstance(tag)) {
			throw new IOException(key + " tag is not of tag type " + expected.getName());
		}

		return expected.cast(tag);
	}

	@Nullable
	private static <T extends Tag> T getTag(CompoundTag tag, Class<T> expected, String key) {
		final Map<String, Tag> items = tag.getValue();

		if (!items.containsKey(key)) {
			return null;
		}

		final Tag test = items.get(key);
		if (!expected.isInstance(test)) {
			return null;
		}

		return expected.cast(test);
	}

}
