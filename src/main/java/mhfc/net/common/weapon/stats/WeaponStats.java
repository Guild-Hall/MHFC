package mhfc.net.common.weapon.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import mhfc.net.common.item.ItemRarity;

/**
 * An immutable class describing the stats of a weapon. Melee and certain range classes extend this. The attributes here
 * could be boosted with abilities thus the getter methods most often take a player argument.
 * <p>
 * These stats are those that are more or less unique to every weapon. For stats per weapon class, use the base classes
 * for each weapon.
 *
 * @author WorldSEnder
 *
 */
public class WeaponStats {
	public abstract static class WeaponStatsBuilder<T extends WeaponStatsBuilder<T>> {
		private float attackBase;
		private List<CombatEffect> effects;
		private int slots;
		private int cooldownTicks;
		private ItemRarity rarity; // FIXME: when merged with Items, change this to the correct enum
		private String unlocalizedName;

		public WeaponStatsBuilder() {
			this.attackBase = 0;
			this.effects = new ArrayList<>();
			this.slots = 0;
			this.cooldownTicks = 0;
			this.rarity = ItemRarity.R01;
		}

		protected abstract T getThis();

		public T setSlotCount(int slots) {
			Preconditions.checkArgument(slots >= 0, "slots must not be less than zero");
			this.slots = slots;
			return getThis();
		}

		public T setAttack(float attack) {
			Preconditions.checkArgument(attack >= 0, "attack must not be less than zero");
			this.attackBase = attack;
			return getThis();
		}

		public T addCombatEffect(ICombatEffectType type, float amount) {
			effects.add(new CombatEffect(type, amount));
			return getThis();
		}

		public T setCooldownTicks(int cooldown) {
			Preconditions.checkArgument(cooldown >= 0, "cooldown must be positive");
			this.cooldownTicks = cooldown;
			return getThis();
		}

		public T setRarity(int rarity) {
			this.rarity = ItemRarity.fromInt(rarity);
			return getThis();
		}

		public T setRarity(ItemRarity rarity) {
			this.rarity = rarity;
			return getThis();
		}

		public T setName(String unlocalizedName) {
			this.unlocalizedName = Objects.requireNonNull(unlocalizedName);
			return getThis();
		}

		public WeaponStats build() {
			Preconditions.checkState(unlocalizedName != null, "must set a unlocalized name for this item");
			return new WeaponStats(this);
		}
	}

	protected WeaponStats(WeaponStatsBuilder<?> builder) {
		this.attackBase = builder.attackBase;
		this.combatEffects = ImmutableList.copyOf(builder.effects);
		this.slotCount = builder.slots;
		this.cooldownTicks = builder.cooldownTicks;
		this.rarity = builder.rarity;
		this.name = builder.unlocalizedName;
	}

	private final float attackBase;
	private final List<CombatEffect> combatEffects;
	private final int slotCount;
	private final int cooldownTicks;
	private final ItemRarity rarity;
	private final String name;

	public float getAttack() {
		return attackBase;
	}

	public float getAttack(float scale) {
		return scale * getAttack();
	}

	public int getCooldownTicks() {
		return this.cooldownTicks;
	}

	public ItemRarity getRarity() {
		return this.rarity;
	}

	public List<CombatEffect> getCombatEffects() {
		return combatEffects;
	}

	public String getUnlocalizedName() {
		return name;
	}
}
