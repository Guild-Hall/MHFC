package mhfc.net.common.util.io;

import java.lang.reflect.Type;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.ResourceLocation;

public abstract class JsonDelegatingConverter<T, D>
extends
DelegatingConverter<JsonElement, JsonElement, JsonDeserializationContext, D, T, JsonSerializationContext, ResourceLocation>
implements
JsonDeserializer<D>,
JsonSerializer<D> {
	private static Logger LOGGER = LogManager.getLogger();

	private final String keyName;
	private final String dataName;

	public JsonDelegatingConverter(String keyNameInObject, String dataNameInObject) {
		this.keyName = Objects.requireNonNull(keyNameInObject);
		this.dataName = dataNameInObject;
	}

	@Override
	protected ResourceLocation extractKeyFromA(JsonElement value) {
		return new ResourceLocation(value.getAsJsonObject().get(keyName).getAsString());
	}

	@Override
	protected JsonElement extractConvertibleFromA(JsonElement value) {
		if (dataName == null) {
			return value;
		}
		return value.getAsJsonObject().get(dataName);
	}

	@Override
	protected JsonObject createA(ResourceLocation key, JsonElement value) {
		if (dataName == null) {
			JsonObject object = value.getAsJsonObject();
			if (object.has(keyName)) {
				LOGGER.warn("Overwriting key during conversion of value");
			}
			object.addProperty(keyName, key.toString());
			return object;
		}
		JsonObject object = new JsonObject();
		object.addProperty(keyName, key.toString());
		object.add(dataName, value);
		return object;
	}

	@Override
	public JsonElement serialize(D src, Type typeOfSrc, JsonSerializationContext context) {
		return convertFrom(src, context);
	}

	@Override
	public D deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		return convertTo(json, context);
	}

}
