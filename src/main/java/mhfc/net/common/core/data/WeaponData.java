package mhfc.net.common.core.data;

import com.google.gson.*;
import mhfc.net.common.weapon.melee.MeleeWeaponStats.MeleeWeaponStatsBuilder;
import mhfc.net.common.weapon.melee.greatsword.GreatswordWeaponStats.GreatswordWeaponStatsBuilder;
import mhfc.net.common.weapon.melee.greatsword.ItemGreatsword;
import mhfc.net.common.weapon.melee.hammer.HammerWeaponStats.HammerWeaponStatsBuilder;
import mhfc.net.common.weapon.melee.hammer.ItemHammer;
import mhfc.net.common.weapon.melee.huntinghorn.HuntingHornWeaponStats.HuntingHornWeaponStatsBuilder;
import mhfc.net.common.weapon.melee.huntinghorn.ItemHuntingHorn;
import mhfc.net.common.weapon.melee.huntinghorn.Note;
import mhfc.net.common.weapon.melee.longsword.ItemLongsword;
import mhfc.net.common.weapon.melee.longsword.LongswordWeaponStats.LongswordWeaponStatsBuilder;
import mhfc.net.common.weapon.range.bow.BowWeaponStats.BowWeaponStatsBuilder;
import mhfc.net.common.weapon.range.bow.ItemBow;
import mhfc.net.common.weapon.range.bowgun.BowgunWeaponStats.BowgunWeaponStatsBuilder;
import mhfc.net.common.weapon.range.bowgun.heavy.ItemHeavyBowgun;
import mhfc.net.common.weapon.range.bowgun.light.ItemLightBowgun;
import mhfc.net.common.weapon.stats.ElementalType;
import mhfc.net.common.weapon.stats.ICombatEffectType;
import mhfc.net.common.weapon.stats.Sharpness.SharpnessBuilder;
import mhfc.net.common.weapon.stats.SharpnessLevel;
import mhfc.net.common.weapon.stats.StatusEffect;
import mhfc.net.common.weapon.stats.WeaponStats.WeaponStatsBuilder;
import net.minecraft.item.Item;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
		switch (type.toLowerCase(Locale.ROOT)) {
		case "gs":
		case "greatsword":
			return parseGS(json);
		case "ls":
		case "longsword":
			return parseLS(json);
		case "hm":
		case "hammer":
			return parseHM(json);
		case "hh":
		case "huntinghorn":
		case "horn":
			return parseHH(json);
		case "b":
		case "bow":
			return parseB(json);
		case "bgl":
		case "light_bowgun":
		case "light bowgun":
			return parseBGL(json);
		case "bgh":
		case "heavy_bowgun":
		case "heavy bowgun":
			return parseBGH(json);
		default:
			throw new IllegalArgumentException("Unknown type " + type);
		}
	}

	private static Supplier<Item> parseBGL(JsonObject json) {
		return () -> ItemLightBowgun.build(parseBowgun(json));
	}

	private static Supplier<Item> parseBGH(JsonObject json) {
		return () -> ItemHeavyBowgun.build(parseBowgun(json));
	}

	private static Supplier<Item> parseB(JsonObject json) {
		final Consumer<BowWeaponStatsBuilder> configuration = ((Consumer<BowWeaponStatsBuilder>) s -> {})
				.andThen(configureBasicBuilder(json));
		return () -> ItemBow.build(configuration);
	}

	private static Supplier<Item> parseHH(JsonObject json) {
		final Consumer<HuntingHornWeaponStatsBuilder> configuration = ((Consumer<HuntingHornWeaponStatsBuilder>) s -> {})
				.andThen(configureBasicBuilder(json)).andThen(configureMeleeBuilder(json))
				.andThen(configureHuntingHorn(json));
		return () -> ItemHuntingHorn.build(configuration);
	}

	private static Supplier<Item> parseHM(JsonObject json) {
		final Consumer<HammerWeaponStatsBuilder> configuration = ((Consumer<HammerWeaponStatsBuilder>) s -> {})
				.andThen(configureBasicBuilder(json)).andThen(configureMeleeBuilder(json));
		return () -> ItemHammer.build(configuration);
	}

	private static Supplier<Item> parseLS(JsonObject json) {
		final Consumer<LongswordWeaponStatsBuilder> configuration = ((Consumer<LongswordWeaponStatsBuilder>) s -> {})
				.andThen(configureBasicBuilder(json)).andThen(configureMeleeBuilder(json))
				.andThen(b -> b.setExtendedReach(1));
		return () -> ItemLongsword.build(configuration);
	}

	private static Supplier<Item> parseGS(JsonObject json) {
		final Consumer<GreatswordWeaponStatsBuilder> configuration = ((Consumer<GreatswordWeaponStatsBuilder>) s -> {})
				.andThen(configureBasicBuilder(json)).andThen(configureMeleeBuilder(json))
				.andThen(b -> b.setExtendedReach(1));
		return () -> ItemGreatsword.build(configuration);
	}

	private static Consumer<? super HuntingHornWeaponStatsBuilder> configureHuntingHorn(JsonObject json) {
		JsonArray notesJson = json.get("notes").getAsJsonArray();
		Note[] notes = StreamSupport.stream(notesJson.spliterator(), false).map(e -> getNote(e.getAsString()))
				.toArray(Note[]::new);
		return b -> b.setNotes(notes);
	}

	private static Consumer<BowgunWeaponStatsBuilder> parseBowgun(JsonObject json) {
		return ((Consumer<BowgunWeaponStatsBuilder>) s -> {}).andThen(configureBasicBuilder(json));
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
			JsonElement eo = modifiers.get(i);
			if (eo.isJsonObject()) {
				JsonObject eoo = eo.getAsJsonObject();
				ICombatEffectType type = getCombatEffect(eoo.get("type").getAsString());
				float amount = eoo.get("attack").getAsFloat();
				configuration = configuration.andThen(b -> b.addCombatEffect(type, amount));
			} else {
				JsonArray eoa = eo.getAsJsonArray();
				ICombatEffectType type = getCombatEffect(eoa.get(0).getAsString());
				float amount = eoa.get(1).getAsFloat();
				configuration = configuration.andThen(b -> b.addCombatEffect(type, amount));
			}
		}
		return configuration;
	}

	private static Map<String, ICombatEffectType> EFFECT_TYPES = new HashMap<>();
	private static Map<String, Note> NOTES = new HashMap<>();
	static {
		Stream.concat(Stream.of(ElementalType.values()), Stream.of(StatusEffect.values())).forEach(e -> {
			ICombatEffectType oldValue = EFFECT_TYPES.put(e.name().toLowerCase(Locale.ROOT), e);
			if (oldValue != null) {
				throw new IllegalArgumentException("duplicate name " + e.name());
			}
		});
		Arrays.stream(Note.values()).forEach(e -> {
			NOTES.put(e.name().toLowerCase(Locale.ROOT), e);
		});
	}

	private static <R, T> T requireExistingMapping(Map<R, T> map, R key) {
		return map.computeIfAbsent(key, t -> {
			throw new IllegalArgumentException(key.toString() + " must be an entry of the map " + map.toString());
		});
	}

	private static ICombatEffectType getCombatEffect(String type) {
		return requireExistingMapping(EFFECT_TYPES, type.toLowerCase(Locale.ROOT));
	}

	private static Note getNote(String type) {
		return requireExistingMapping(NOTES, type.toLowerCase(Locale.ROOT));
	}

	private Map<String, Supplier<Item>> nameToRegistrator = new HashMap<>();

	private WeaponData() {}

	@SuppressWarnings("unchecked") // Must be unchecked: no type information in weapons.json
	public <T extends Item> T configureItem(String name) {
		Supplier<Item> supplier = nameToRegistrator.get(name);
		if (supplier == null) {
			throw new IllegalArgumentException("unknown name " + name);
		}
		return (T) supplier.get();
	}
}
