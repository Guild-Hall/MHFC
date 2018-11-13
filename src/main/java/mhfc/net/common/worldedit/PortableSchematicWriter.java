package mhfc.net.common.worldedit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sk89q.jnbt.ByteArrayTag;
import com.sk89q.jnbt.CompoundTag;
import com.sk89q.jnbt.CompoundTagBuilder;
import com.sk89q.jnbt.DoubleTag;
import com.sk89q.jnbt.FloatTag;
import com.sk89q.jnbt.IntTag;
import com.sk89q.jnbt.ListTag;
import com.sk89q.jnbt.ListTagBuilder;
import com.sk89q.jnbt.NBTOutputStream;
import com.sk89q.jnbt.ShortTag;
import com.sk89q.jnbt.StringTag;
import com.sk89q.jnbt.Tag;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.registry.LegacyMapper;

public class PortableSchematicWriter implements ClipboardWriter {

	private static final int MAX_SIZE = Short.MAX_VALUE - Short.MIN_VALUE;
	private final NBTOutputStream outputStream;
	private final IBlockMappingTable blockMappingTable;

	public PortableSchematicWriter(NBTOutputStream output, IBlockMappingTable mappingTable) {
		outputStream = output;
		blockMappingTable = mappingTable;
	}

	@Override
	public void write(Clipboard clipboard) throws IOException {
		// Same implementation as in SchematicWriter, except
		// - block-id are determined via the blockMappingTable
		// - the name is "Portable-Schematic" to disambiguate
		// - the mappingTable is saved in a tag "Mappings"
		final Region region = clipboard.getRegion();
		final Vector origin = clipboard.getOrigin();
		final Vector min = region.getMinimumPoint();
		final Vector offset = min.subtract(origin);
		final int width = region.getWidth();
		final int height = region.getHeight();
		final int length = region.getLength();

		if (width > MAX_SIZE) {
			throw new IllegalArgumentException("Width of region too large for a .schematic");
		}
		if (height > MAX_SIZE) {
			throw new IllegalArgumentException("Height of region too large for a .schematic");
		}
		if (length > MAX_SIZE) {
			throw new IllegalArgumentException("Length of region too large for a .schematic");
		}

		// ====================================================================
		// Metadata
		// ====================================================================

		final CompoundTagBuilder schematic = CompoundTagBuilder.create();
		schematic.put("Width", new ShortTag((short) width));
		schematic.put("Length", new ShortTag((short) length));
		schematic.put("Height", new ShortTag((short) height));
		schematic.put("Materials", new StringTag("Alpha"));
		schematic.put("WEOriginX", new IntTag(min.getBlockX()));
		schematic.put("WEOriginY", new IntTag(min.getBlockY()));
		schematic.put("WEOriginZ", new IntTag(min.getBlockZ()));
		schematic.put("WEOffsetX", new IntTag(offset.getBlockX()));
		schematic.put("WEOffsetY", new IntTag(offset.getBlockY()));
		schematic.put("WEOffsetZ", new IntTag(offset.getBlockZ()));

		// ====================================================================
		// Block handling
		// ====================================================================

		final byte[] blocks = new byte[width * height * length];
		byte[] addBlocks = null;
		final byte[] blockData = new byte[width * height * length];
		final List<Tag> tileEntities = new ArrayList<>();

		for (final Vector point : region) {
			final Vector relative = point.subtract(min);
			final int x = relative.getBlockX();
			final int y = relative.getBlockY();
			final int z = relative.getBlockZ();

			final int index = y * width * length + z * width + x;
			final BaseBlock block = clipboard.getFullBlock(point);

			final int[] legacy = LegacyMapper.getInstance().getLegacyFromBlock(block.toImmutableState());
			// assert legacy.length;
			final int blockType = blockMappingTable.getCompressedIdFor(block);
			// Save 4096 IDs in an AddBlocks section
			if (blockType > 255) {
				if (addBlocks == null) { // Lazily create section
					addBlocks = new byte[(blocks.length >> 1) + 1];
				}

				addBlocks[index >> 1] = (byte) (((index & 1) == 0)
						? addBlocks[index >> 1] & 0xF0 | (blockType >> 8) & 0xF
						: addBlocks[index >> 1] & 0xF | ((blockType >> 8) & 0xF) << 4);
			}

			blocks[index] = (byte) blockType;
			blockData[index] = (byte) legacy[1];

			// Store TileEntity data
			final CompoundTag rawTag = block.getNbtData();
			if (rawTag != null) {
				final Map<String, Tag> values = new HashMap<>();
				for (final Entry<String, Tag> entry : rawTag.getValue().entrySet()) {
					values.put(entry.getKey(), entry.getValue());
				}

				values.put("id", new StringTag(block.getNbtId()));
				values.put("x", new IntTag(x));
				values.put("y", new IntTag(y));
				values.put("z", new IntTag(z));

				final CompoundTag tileEntityTag = new CompoundTag(values);
				tileEntities.add(tileEntityTag);
			}
		}

		schematic.put("Blocks", new ByteArrayTag(blocks));
		schematic.put("Data", new ByteArrayTag(blockData));
		if (addBlocks != null) {
			schematic.put("AddBlocks", new ByteArrayTag(addBlocks));
		}
		schematic.put("TileEntities", new ListTag(CompoundTag.class, tileEntities));
		schematic.put("Mappings", blockMappingTable.saveToNbt());

		// ====================================================================
		// Entities
		// ====================================================================

		final List<Tag> entities = new ArrayList<>();
		for (final Entity entity : clipboard.getEntities()) {
			final BaseEntity state = entity.getState();

			if (state != null) {
				final Map<String, Tag> values = new HashMap<>();

				// Put NBT provided data
				final CompoundTag rawTag = state.getNbtData();
				if (rawTag != null) {
					values.putAll(rawTag.getValue());
				}

				// Store our location data, overwriting any
				values.put("id", new StringTag(state.getType().getId()));
				values.put("Pos", writePosition(entity.getLocation()));
				values.put("Rotation", writeRotation(entity.getLocation()));

				final CompoundTag entityTag = new CompoundTag(values);
				entities.add(entityTag);
			}
		}

		schematic.put("Entities", new ListTag(CompoundTag.class, entities));

		// ====================================================================
		// Output
		// ====================================================================

		outputStream.writeNamedTag("Portable-Schematic", schematic.build());
	}

	private static Tag writePosition(Location location) {
		final Vector vector = location.toVector();
		final ListTagBuilder list = ListTagBuilder.create(DoubleTag.class);
		list.add(new DoubleTag(vector.getX()));
		list.add(new DoubleTag(vector.getY()));
		list.add(new DoubleTag(vector.getZ()));
		return list.build();
	}

	private static Tag writeRotation(Location location) {
		final ListTagBuilder list = ListTagBuilder.create(FloatTag.class);
		list.add(new FloatTag(location.getYaw()));
		list.add(new FloatTag(location.getPitch()));
		return list.build();
	}

	@Override
	public void close() throws IOException {
		outputStream.close();
	}
}
