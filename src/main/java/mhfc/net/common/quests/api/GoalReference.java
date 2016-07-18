package mhfc.net.common.quests.api;

import java.lang.reflect.Type;

import com.google.gson.*;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import net.minecraft.util.JsonUtils;

public class GoalReference {

	public static class GoalRefSerializer
		implements
			JsonDeserializer<GoalReference>,
			JsonSerializer<GoalReference> {

		@Override
		public GoalReference deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
			return constructFromJson(json, context);
		}

		@Override
		public JsonElement serialize(GoalReference src, Type typeOfSrc,
			JsonSerializationContext context) {
			if (src.referByString) {
				return new JsonPrimitive(src.id);
			} else {
				if (src.description == null)
					return JsonNull.INSTANCE;
				return context.serialize(src.description,
					GoalDefinition.class);
			}
		}

	}

	/**
	 * Constructs a GoalReference from the json stream and raises an exception
	 * if there is none. Also constructs any nested goals.
	 * 
	 * @return A GoalReference for the json, not null
	 */
	public static GoalReference constructFromJson(JsonElement element,
		JsonDeserializationContext context) {
		if (element == null || element.isJsonNull())
			return new GoalReference((GoalDefinition) null);
		if (element.isJsonPrimitive() && element.getAsJsonPrimitive()
			.isString()) {
			return new GoalReference(JsonUtils.getJsonElementStringValue(
				element, "Goal Reference"));
		} else if (element.isJsonObject()) {
			GoalDefinition desc = context.<GoalDefinition> deserialize(
				element, GoalDefinition.class);
			return new GoalReference(desc);
		}
		throw new JsonParseException(
			"Required a reference on a goal but found something else");
	}

	boolean referByString;
	String id;
	GoalDefinition description;

	/**
	 * This class represents a reference to a quest goal, either through the id
	 * corresponding to it or a direct object reference.
	 */

	public GoalReference(String id) {
		this.id = id;
		referByString = true;
		description = null;
	}

	public GoalReference(GoalDefinition description) {
		this.description = description;
		referByString = false;
		id = null;
	}

	/**
	 * Gets the actual description that is referred to by this object.
	 */
	public GoalDefinition getReferredDescription() {
		if (referByString)
			return MHFCQuestBuildRegistry.getGoalDescription(id);
		else
			return description;
	}

}
