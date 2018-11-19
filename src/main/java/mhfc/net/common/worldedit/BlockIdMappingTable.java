package mhfc.net.common.worldedit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.sk89q.jnbt.CompoundTag;
import com.sk89q.jnbt.CompoundTagBuilder;
import com.sk89q.jnbt.ListTag;
import com.sk89q.jnbt.StringTag;
import com.sk89q.jnbt.Tag;
import com.sk89q.worldedit.blocks.BaseBlock;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public abstract class BlockIdMappingTable<ID, T extends Tag> implements IBlockMappingTable {
	private Map<ID, Integer> registryNameToCompressedId;
	private List<ID> compressedIdToRegistryName;
	private final Class<T> tagType;
	private final String formatName;

	protected BlockIdMappingTable(Class<T> tagType, String formatName) {
		this.registryNameToCompressedId = new HashMap<>();
		this.compressedIdToRegistryName = new ArrayList<>();
		this.tagType = Objects.requireNonNull(tagType);
		this.formatName = Objects.requireNonNull(formatName);
	}

	private int register(ID registryName) {
		int nextId = compressedIdToRegistryName.size();
		compressedIdToRegistryName.add(nextId, registryName);
		registryNameToCompressedId.put(registryName, nextId);
		return nextId;
	}

	protected abstract ID getIdFromBlock(int baseBlockId);

	protected abstract int getBaseBlockId(ID registryName);

	protected abstract T saveRegistryName(ID registryName);

	protected abstract ID loadRegistryName(T tag);

	@Override
	public int getCompressedIdFor(BaseBlock block) {
		ID registryName = getIdFromBlock(block.getId());
		int id;
		if (registryNameToCompressedId.containsKey(registryName)) {
			id = registryNameToCompressedId.get(registryName).intValue();
		} else {
			id = register(registryName);
		}
		return id;
	}

	@Override
	public int getBaseBlockIdFor(int compressedId) {
		ID registryName = compressedIdToRegistryName.get(compressedId);
		return getBaseBlockId(registryName);
	}

	@Override
	public Tag saveToNbt() {
		List<T> savedTags = compressedIdToRegistryName.stream().map(this::saveRegistryName)
				.collect(Collectors.toList());
		ListTag list = new ListTag(tagType, savedTags);
		return CompoundTagBuilder.create().putString("Format", formatName).put("Entries", list).build();
	}

	@Override
	public void loadFromNbt(Tag nbtTag) {
		CompoundTag data = (CompoundTag) nbtTag;
		String dataFormat = data.getString("Format");
		if (!dataFormat.equals(formatName)) {
			return;
		}

		ListTag list = data.getListTag("Entries");
		List<Tag> tags = list.getValue();
		int size = tags.size();
		Map<ID, Integer> loadedNameToId = new HashMap<>(size);
		List<ID> loadedIdToName = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			T tag = tagType.cast(tags.get(i));
			ID id = loadRegistryName(tag);
			loadedIdToName.add(i, id);
			loadedNameToId.put(id, Integer.valueOf(i));
		}

		this.compressedIdToRegistryName = loadedIdToName;
		this.registryNameToCompressedId = loadedNameToId;
	}

	public static IBlockMappingTable createForgeMappingTable() {
		return new BlockIdMappingTable<ResourceLocation, StringTag>(StringTag.class, "Forge") {
			@Override
			protected int getBaseBlockId(ResourceLocation registryName) {
				Block block = Block.REGISTRY.getObject(registryName);
				return Block.REGISTRY.getIDForObject(block);
			}

			@Override
			protected ResourceLocation getIdFromBlock(int baseBlockId) {
				Block block = Block.REGISTRY.getObjectById(baseBlockId);
				return Block.REGISTRY.getNameForObject(block);
			}

			@Override
			public StringTag saveRegistryName(ResourceLocation id) {
				return new StringTag(id.toString());
			}

			@Override
			public ResourceLocation loadRegistryName(StringTag tag) {
				return new ResourceLocation(tag.getValue());
			}
		};
	}
}