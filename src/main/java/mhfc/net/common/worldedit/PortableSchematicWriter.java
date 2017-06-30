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
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.registry.WorldData;

public class PortableSchematicWriter implements ClipboardWriter {

	private static final int MAX_SIZE = Short.MAX_VALUE - Short.MIN_VALUE;
	private final NBTOutputStream outputStream;
	private final IBlockMappingTable blockMappingTable;

	public PortableSchematicWriter(NBTOutputStream output, IBlockMappingTable mappingTable) {
		this.outputStream = output;
		this.blockMappingTable = mappingTable;
	}

	@Override
	public void write(Clipboard clipboard, WorldData data) throws IOException {
		// Same implementation as in SchematicWriter, except
		// - block-id are determined via the blockMappingTable
		// - the name is "Portable-Schematic" to disambiguate
		// - the mappingTable is saved in a tag "Mappings"
		Region region = clipboard.getRegion();
		Vector origin = clipboard.getOrigin();
		Vector min = region.getMinimumPoint();
		Vector offset = min.subtract(origin);
		int width = region.getWidth();
		int height = region.getHeight();
		int length = region.getLength();

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

		CompoundTagBuilder schematic = CompoundTagBuilder.create();
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

		byte[] blocks = new byte[width * height * length];
		byte[] addBlocks = null;
		byte[] blockData = new byte[width * height * length];
		List<Tag> tileEntities = new ArrayList<>();

		for (Vector point : region) {
			Vector relative = point.subtract(min);
			int x = relative.getBlockX();
			int y = relative.getBlockY();
			int z = relative.getBlockZ();

			int index = y * width * length + z * width + x;
			BaseBlock block = clipboard.getBlock(point);

			int blockType = blockMappingTable.getCompressedIdFor(block);
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
			blockData[index] = (byte) block.getData();

			// Store TileEntity data
			CompoundTag rawTag = block.getNbtData();
			if (rawTag != null) {
				Map<String, Tag> values = new HashMap<>();
				for (Entry<String, Tag> entry : rawTag.getValue().entrySet()) {
					values.put(entry.getKey(), entry.getValue());
				}

				values.put("id", new StringTag(block.getNbtId()));
				values.put("x", new IntTag(x));
				values.put("y", new IntTag(y));
				values.put("z", new IntTag(z));

				CompoundTag tileEntityTag = new CompoundTag(values);
				tileEntities.add(tileEntityTag);
			}
		}

		schematic.put("Blocks", new ByteArrayTag(blocks));
		schematic.put("Data", new ByteArrayTag(blockData));
		schematic.put("TileEntities", new ListTag(CompoundTag.class, tileEntities));

		if (addBlocks != null) {
			schematic.put("AddBlocks", new ByteArrayTag(addBlocks));
		}

		// ====================================================================
		// Entities
		// ====================================================================

		List<Tag> entities = new ArrayList<>();
		for (Entity entity : clipboard.getEntities()) {
			BaseEntity state = entity.getState();

			if (state != null) {
				Map<String, Tag> values = new HashMap<>();

				// Put NBT provided data
				CompoundTag rawTag = state.getNbtData();
				if (rawTag != null) {
					values.putAll(rawTag.getValue());
				}

				// Store our location data, overwriting any
				values.put("id", new StringTag(state.getTypeId()));
				values.put("Pos", writeVector(entity.getLocation().toVector()));
				values.put("Rotation", writeRotation(entity.getLocation()));

				CompoundTag entityTag = new CompoundTag(values);
				entities.add(entityTag);
			}
		}

		schematic.put("Entities", new ListTag(CompoundTag.class, entities));
		schematic.put("Mappings", blockMappingTable.saveToNbt());

		// ====================================================================
		// Output
		// ====================================================================

		outputStream.writeNamedTag("Portable-Schematic", schematic.build());
	}

	private static Tag writeVector(Vector vector) {
		ListTagBuilder list = ListTagBuilder.create(DoubleTag.class);
		list.add(new DoubleTag(vector.getX()));
		list.add(new DoubleTag(vector.getY()));
		list.add(new DoubleTag(vector.getZ()));
		return list.build();
	}

	private static Tag writeRotation(Location location) {
		ListTagBuilder list = ListTagBuilder.create(FloatTag.class);
		list.add(new FloatTag(location.getYaw()));
		list.add(new FloatTag(location.getPitch()));
		return list.build();
	}

	@Override
	public void close() throws IOException {
		outputStream.close();
	}
}
