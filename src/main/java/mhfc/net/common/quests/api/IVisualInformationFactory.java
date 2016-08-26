package mhfc.net.common.quests.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;

public interface IVisualInformationFactory<T extends IVisualDefinition> {

	public T buildInformation(JsonElement json, JsonDeserializationContext context);

	public JsonElement serialize(T information, JsonSerializationContext context);

}
