package mhfc.net.common.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class MHFCJsonUtils {
	public static String getJsonObjectStringFieldValueOrDefault(
		JsonObject p_151219_0_, String p_151219_1_, String p_151219_2_) {
		return p_151219_0_.has(p_151219_1_)
			? net.minecraft.util.JsonUtils.getJsonElementStringValue(p_151219_0_
				.get(p_151219_1_), p_151219_1_)
			: p_151219_2_;
	}

	public static int getJsonObjectIntegerFieldValueOrDefault(
		JsonObject p_151208_0_, String p_151208_1_, int p_151208_2_) {
		return p_151208_0_.has(p_151208_1_)
			? net.minecraft.util.JsonUtils.getJsonElementIntegerValue(
				p_151208_0_.get(p_151208_1_), p_151208_1_)
			: p_151208_2_;
	}

	public static boolean objectFieldTypeIsObject(JsonObject jsonAsObject,
		String string) {
		return jsonAsObject.has(string) && jsonAsObject.get(string)
			.isJsonObject();
	}

	public static boolean objectFieldTypeIsArray(JsonObject jsonAsObject,
		String string) {
		return jsonAsObject.has(string) && jsonAsObject.get(string)
			.isJsonArray();
	}

	public static boolean objectFieldTypeIsNull(JsonObject jsonAsObject,
		String string) {
		return !jsonAsObject.has(string) || jsonAsObject.get(string)
			.isJsonNull();
	}

	public static boolean objectFieldTypeIsInteger(JsonObject jsonAsObject,
		String string) {
		if (!jsonAsObject.has(string))
			return false;
		JsonElement field = jsonAsObject.get(string);
		if (!field.isJsonPrimitive())
			return false;
		return field.getAsJsonPrimitive().isNumber();
	}

	public static boolean objectFieldTypeIsBoolean(JsonObject jsonAsObject,
		String string) {
		if (!jsonAsObject.has(string))
			return false;
		JsonElement field = jsonAsObject.get(string);
		if (!field.isJsonPrimitive())
			return false;
		return field.getAsJsonPrimitive().isBoolean();
	}

	public static boolean objectFieldTypeIsString(JsonObject jsonAsObject,
		String string) {
		if (!jsonAsObject.has(string))
			return false;
		JsonElement field = jsonAsObject.get(string);
		if (!field.isJsonPrimitive())
			return false;
		return field.getAsJsonPrimitive().isString();
	}

	public static void requireFields(JsonObject object, String... fields)
		throws JsonParseException {
		for (String fieldIdentifier : fields) {
			if (!object.has(fieldIdentifier))
				throw new JsonParseException("Expecte json object to have a "
					+ fieldIdentifier);
		}
	}

}
