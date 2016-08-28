package mhfc.net.common.quests.properties;

import java.util.Objects;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants.NBT;

/**
 * Enum-like type that supports checked casting of nbt tags.
 *
 * @author WorldSEnder
 *
 * @param <C>
 *            the class type of the tag type, e.g. NBTTagCompound
 * @see NBT
 */
public final class NBTType<C> {
	public static final NBTType<NBTTagEnd> TAG_END = new NBTType<>(NBT.TAG_END, NBTTagEnd.class);
	public static final NBTType<NBTTagByte> TAG_BYTE = new NBTType<>(NBT.TAG_BYTE, NBTTagByte.class);
	public static final NBTType<NBTTagShort> TAG_SHORT = new NBTType<>(NBT.TAG_SHORT, NBTTagShort.class);
	public static final NBTType<NBTTagInt> TAG_INT = new NBTType<>(NBT.TAG_INT, NBTTagInt.class);
	public static final NBTType<NBTTagLong> TAG_LONG = new NBTType<>(NBT.TAG_LONG, NBTTagLong.class);
	public static final NBTType<NBTTagFloat> TAG_FLOAT = new NBTType<>(NBT.TAG_FLOAT, NBTTagFloat.class);
	public static final NBTType<NBTTagDouble> TAG_DOUBLE = new NBTType<>(NBT.TAG_DOUBLE, NBTTagDouble.class);

	//    public static final int TAG_BYTE_ARRAY  = 7;

	public static final NBTType<NBTTagString> TAG_STRING = new NBTType<>(NBT.TAG_STRING, NBTTagString.class);
	public static final NBTType<NBTTagList> TAG_LIST = new NBTType<>(NBT.TAG_LIST, NBTTagList.class);
	public static final NBTType<NBTTagCompound> TAG_COMPOUND = new NBTType<>(NBT.TAG_COMPOUND, NBTTagCompound.class);

	//    public static final int TAG_INT_ARRAY   = 11;

	private int nbtType;
	private Class<C> clazz;

	private NBTType(int nbtType, Class<C> clazz) {
		this.nbtType = nbtType;
		this.clazz = Objects.requireNonNull(clazz);
	}

	public boolean checkTag(NBTBase candidate) {
		return candidate.getId() == nbtType;
	}

	public C assureTagType(NBTBase nbtTag) {
		if (!checkTag(nbtTag)) {
			throw new IllegalArgumentException("Unexpected tag type, got " + nbtTag + " expected " + nbtType);
		}
		return clazz.cast(nbtTag);
	}
}
