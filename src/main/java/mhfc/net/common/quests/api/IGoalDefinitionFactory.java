package mhfc.net.common.quests.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.util.io.IConverter;

public interface IGoalDefinitionFactory
		extends
		IConverter<JsonElement, JsonDeserializationContext, IGoalDefinition, JsonSerializationContext> {
}
