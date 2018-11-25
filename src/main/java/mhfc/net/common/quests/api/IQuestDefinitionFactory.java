package mhfc.net.common.quests.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import mhfc.net.common.util.io.IConverter;

public interface IQuestDefinitionFactory
		extends
		IConverter<JsonElement, JsonDeserializationContext, IQuestDefinition, JsonSerializationContext> {
}
