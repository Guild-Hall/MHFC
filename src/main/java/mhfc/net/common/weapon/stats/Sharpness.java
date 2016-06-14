package mhfc.net.common.weapon.stats;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;

import com.google.common.base.Preconditions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class Sharpness {
	public static class SharpnessBuilder {
		private Map<SharpnessLevel, Integer> sharpnessLength;

		public SharpnessBuilder() {
			sharpnessLength = new EnumMap<>(SharpnessLevel.class);
		}

		/**
		 * Set the sharpness length for a specific sharpness level of the weapon.
		 *
		 * @param level
		 *            the level to set, non null
		 * @param length
		 *            the length the sharpness should have, must be <= 0
		 * @return this builder
		 */
		public SharpnessBuilder setLength(SharpnessLevel level, int length) {
			Preconditions.checkState(length >= 0, "Length must be greater or equal 0");
			Objects.requireNonNull(level);
			this.sharpnessLength.put(level, length);
			return this;
		}

		/**
		 * Builds the sharpness
		 *
		 * @return
		 */
		public Sharpness build() {
			return new Sharpness(this);
		}
	}

	public static final Sharpness DEFAULT_SHARPNESS = new SharpnessBuilder().setLength(SharpnessLevel.Orange, 20)
			.build();

	private NavigableMap<Integer, SharpnessLevel> sharpnessEntries;

	protected Sharpness(SharpnessBuilder builder) {
		this.sharpnessEntries = new TreeMap<>();
		int levelStart = 0;
		for (SharpnessLevel level : SharpnessLevel.values()) {
			Integer levelLength = builder.sharpnessLength.get(level);
			if (levelLength == null || levelLength.intValue() == 0) {
				continue;
			}
			sharpnessEntries.put(levelStart, level);
			levelStart += levelLength.intValue();
		}
	}

	private int getBaseSharpness(ItemStack itemstack) {
		// TODO: decide based on attached nbt
		return 0;
	}

	/**
	 * Gets the sharpness for a specific itemstack and player. The player is needed to decide on effects.
	 *
	 * @param itemstack
	 * @param player
	 * @return
	 */
	public SharpnessLevel getSharpness(ItemStack itemstack, EntityPlayer player) {
		int sharpness = getBaseSharpness(itemstack);
		// TODO: manipulate with effects from player (Sharpness +1)
		Entry<Integer, SharpnessLevel> floorEntry = sharpnessEntries.floorEntry(sharpness);
		if (floorEntry == null) {
			return SharpnessLevel.Red;
		}
		SharpnessLevel level = floorEntry.getValue();
		return level;
	}
}
