package mhfc.net.common.quests.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.IVisualInformation;

public interface IVisualInformationFactory {

	public IVisualInformation buildInformation(JsonElement json,
		JsonDeserializationContext context);

	public JsonElement serialize(IVisualInformation information,
		JsonSerializationContext context);

}
