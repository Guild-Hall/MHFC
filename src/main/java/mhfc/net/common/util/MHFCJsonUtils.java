package mhfc.net.common.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class MHFCJsonUtils {
	public static String getJsonObjectStringFieldValueOrDefault(
			JsonObject object,
			String fieldName,
			String defaultValue) {
		return object.has(fieldName) && !object.get(fieldName).isJsonNull()
				? net.minecraft.util.JsonUtils.getJsonElementStringValue(object.get(fieldName), fieldName)
				: defaultValue;
	}

	public static int getJsonObjectIntegerFieldValueOrDefault(JsonObject object, String fieldName, int defaultValue) {
		return object.has(fieldName) && !object.get(fieldName).isJsonNull() && object.get(fieldName).isJsonPrimitive()
				? net.minecraft.util.JsonUtils.getJsonElementIntegerValue(object.get(fieldName), fieldName)
				: defaultValue;
	}

	public static boolean objectFieldTypeIsObject(JsonObject jsonAsObject, String string) {
		return jsonAsObject.has(string) && jsonAsObject.get(string).isJsonObject();
	}

	public static boolean objectFieldTypeIsArray(JsonObject jsonAsObject, String string) {
		return jsonAsObject.has(string) && jsonAsObject.get(string).isJsonArray();
	}

	public static boolean objectFieldTypeIsNull(JsonObject jsonAsObject, String string) {
		return !jsonAsObject.has(string) || jsonAsObject.get(string).isJsonNull();
	}

	public static boolean objectFieldTypeIsInteger(JsonObject jsonAsObject, String string) {
		if (!jsonAsObject.has(string))
			return false;
		JsonElement field = jsonAsObject.get(string);
		if (!field.isJsonPrimitive())
			return false;
		return field.getAsJsonPrimitive().isNumber();
	}

	public static boolean objectFieldTypeIsBoolean(JsonObject jsonAsObject, String string) {
		if (!jsonAsObject.has(string))
			return false;
		JsonElement field = jsonAsObject.get(string);
		if (!field.isJsonPrimitive())
			return false;
		return field.getAsJsonPrimitive().isBoolean();
	}

	public static boolean objectFieldTypeIsString(JsonObject jsonAsObject, String string) {
		if (!jsonAsObject.has(string))
			return false;
		JsonElement field = jsonAsObject.get(string);
		if (!field.isJsonPrimitive())
			return false;
		return field.getAsJsonPrimitive().isString();
	}

	public static void requireFields(JsonObject object, String... fields) throws JsonParseException {
		for (String fieldIdentifier : fields) {
			if (!object.has(fieldIdentifier))
				throw new JsonParseException("Expecte json object to have a " + fieldIdentifier);
		}
	}

}
