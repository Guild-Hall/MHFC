package mhfc.net.common.core.data;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import mhfc.net.common.weapon.melee.MeleeWeaponStats.MeleeWeaponStatsBuilder;
import mhfc.net.common.weapon.melee.greatsword.GreatswordWeaponStats.GreatswordWeaponStatsBuilder;
import mhfc.net.common.weapon.melee.greatsword.ItemGreatsword;
import mhfc.net.common.weapon.stats.ElementalType;
import mhfc.net.common.weapon.stats.ICombatEffectType;
import mhfc.net.common.weapon.stats.Sharpness.SharpnessBuilder;
import mhfc.net.common.weapon.stats.SharpnessLevel;
import mhfc.net.common.weapon.stats.StatusEffect;
import mhfc.net.common.weapon.stats.WeaponStats.WeaponStatsBuilder;
import net.minecraft.item.Item;

public class WeaponData {
	public static final Gson GSON = new GsonBuilder()
			.registerTypeHierarchyAdapter(WeaponData.class, new WeaponDataDeserializer()).create();

	private static class WeaponDataDeserializer implements JsonDeserializer<WeaponData> {
		@Override
		public WeaponData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			WeaponData data = new WeaponData();
			for (Entry<String, JsonElement> member : json.getAsJsonObject().entrySet()) {
				Supplier<Item> weaponRegistrar = parseWeapon(member.getValue().getAsJsonObject());
				data.nameToRegistrator.put(member.getKey(), weaponRegistrar);
			}
			return data;
		}
	}

	private static Supplier<Item> parseWeapon(JsonObject json) {
		String type = json.get("type").getAsString();
		switch (type.toLowerCase()) {
		case "gs":
			return parseGS(json);
		// TODO: other weapon types
		default:
			throw new IllegalArgumentException("Unknown type " + type);
		}
	}

	private static Supplier<Item> parseGS(JsonObject json) {
		final Consumer<GreatswordWeaponStatsBuilder> configuration = ((Consumer<GreatswordWeaponStatsBuilder>) s -> {})
				.andThen(configureBasicBuilder(json)).andThen(configureMeleeBuilder(json))
				.andThen(b -> b.setExtendedReach(1));
		return () -> ItemGreatsword.build(configuration);
	}

	private static Consumer<MeleeWeaponStatsBuilder<?>> configureMeleeBuilder(JsonObject json) {
		JsonElement affinityJson = json.get("affinity");
		JsonElement sharpness = json.get("sharpness");
		Consumer<MeleeWeaponStatsBuilder<?>> configure = b -> {};
		if (affinityJson != null) {
			float affinity = affinityJson.getAsFloat();
			configure = configure.andThen(b -> b.setAffinity(affinity));
		}
		return configure.andThen(configureSharpness(sharpness));
	}

	private static Consumer<MeleeWeaponStatsBuilder<?>> configureSharpness(JsonElement json) {
		if (json == null) {
			return b -> {};
		}
		Consumer<SharpnessBuilder> configuration = b -> {};
		JsonArray sharpnessArray = json.getAsJsonArray();
		for (JsonElement element : sharpnessArray) {
			SharpnessLevel level = SharpnessLevel.valueOf(element.getAsJsonObject().get("level").getAsString());
			int length = element.getAsJsonObject().get("length").getAsInt();
			configuration = configuration.andThen(b -> b.setLength(level, length));
		}
		final Consumer<SharpnessBuilder> finalBuilder = configuration;
		return b -> b.configureSharpness(finalBuilder);
	}

	private static Consumer<WeaponStatsBuilder<?>> configureBasicBuilder(JsonObject json) {
		float attack = json.get("attack").getAsFloat();
		String unlocalizedName = json.get("name").getAsString();
		int rarity = json.get("rarity").getAsInt();
		JsonElement slotsJson = json.get("slots");
		int slots = slotsJson == null ? 0 : slotsJson.getAsInt();
		JsonElement attackModifiers = json.get("modifiers");
		Consumer<WeaponStatsBuilder<?>> configuration = b -> b.setAttack(attack).setName(unlocalizedName)
				.setRarity(rarity).setSlotCount(slots);
		return configuration.andThen(configureAttackModifiers(attackModifiers));
	}

	private static Consumer<WeaponStatsBuilder<?>> configureAttackModifiers(JsonElement attackModifiers) {
		if (attackModifiers == null) {
			return s -> {};
		}
		JsonArray modifiers = attackModifiers.getAsJsonArray();
		Consumer<WeaponStatsBuilder<?>> configuration = s -> {};
		for (int i = 0; i < modifiers.size(); ++i) {
			JsonObject eo = modifiers.get(i).getAsJsonObject();
			ICombatEffectType type = getCombatEffect(eo.get("type").getAsString());
			float amount = eo.get("attack").getAsFloat();
			configuration = configuration.andThen(b -> b.addCombatEffect(type, amount));
		}
		return configuration;
	}

	private static class Wrapper<T extends Enum<T> & ICombatEffectType> {
		private Class<T> clazz;

		public Wrapper(Class<T> clazz) {
			this.clazz = clazz;
		}

		public ICombatEffectType resolveName(String name) {
			return Enum.valueOf(clazz, name);
		}
	}

	private static ICombatEffectType getFirstResolved(String name, Wrapper<?>... clazzes) {
		for (Wrapper<?> clazz : clazzes) {
			try {
				return clazz.resolveName(name);
			} catch (IllegalArgumentException e) {}
		}
		throw new IllegalArgumentException("No enum has a member " + name);
	}

	private static ICombatEffectType getCombatEffect(String type) {
		// FIXME: build a map or smth... srsly
		return getFirstResolved(type, new Wrapper<>(ElementalType.class), new Wrapper<>(StatusEffect.class));
	}

	private Map<String, Supplier<Item>> nameToRegistrator = new HashMap<>();

	private WeaponData() {}

	@SuppressWarnings("unchecked") // Must be unchecked: no type information in weapons.json
	public <T extends Item> T configureItem(String name) {
		return (T) nameToRegistrator.get(name).get();
	}
}
