package mhfc.net.common.util.io;

import com.google.gson.*;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.Objects;

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
		JsonElement keyElement = value.getAsJsonObject().get(keyName);
		if (keyElement == null) {
			return missingKeyElement();
		}
		return new ResourceLocation(keyElement.getAsString());
	}

	protected ResourceLocation missingKeyElement() {
		throw new IllegalArgumentException("Key element required");
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
