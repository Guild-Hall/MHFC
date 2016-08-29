package mhfc.net.common.quests.api;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import net.minecraft.util.JsonUtils;

public abstract class GoalReference {

	public static class GoalRefSerializer implements JsonDeserializer<GoalReference>, JsonSerializer<GoalReference> {
		@Override
		public GoalReference deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return constructFromJson(json, context);
		}

		@Override
		public JsonElement serialize(GoalReference src, Type typeOfSrc, JsonSerializationContext context) {
			return src.serialize(typeOfSrc, context);
		}
	}

	private static class ReferenceByID extends GoalReference {
		private String id;

		public ReferenceByID(String id) {
			this.id = id;
		}

		@Override
		public GoalDefinition getReferredDescription() {
			return MHFCQuestBuildRegistry.getGoalDescription(id);
		}

		@Override
		protected JsonElement serialize(Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(id);
		}
	}

	private static class DirectReference extends GoalReference {
		private GoalDefinition description;

		public DirectReference(GoalDefinition description) {
			this.description = description;
		}

		@Override
		public GoalDefinition getReferredDescription() {
			return description;
		}

		@Override
		protected JsonElement serialize(Type typeOfSrc, JsonSerializationContext context) {
			if (description == null) {
				return JsonNull.INSTANCE;
			}
			return context.serialize(description, GoalDefinition.class);
		}
	}

	/**
	 * Constructs a GoalReference from the json stream and raises an exception if there is none. Also constructs any
	 * nested goals.
	 *
	 * @return A GoalReference for the json, not null
	 */
	public static GoalReference constructFromJson(JsonElement element, JsonDeserializationContext context) {
		if (element == null || element.isJsonNull()) {
			return referTo((GoalDefinition) null);
		}
		if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
			return referTo(JsonUtils.getJsonElementStringValue(element, "Goal Reference"));
		} else if (element.isJsonObject()) {
			GoalDefinition desc = context.deserialize(element, GoalDefinition.class);
			return referTo(desc);
		}
		throw new JsonParseException("Required a reference on a goal but found something else");
	}

	/**
	 * This class represents a reference to a quest goal, either through the id corresponding to it or a direct object
	 * reference.
	 */

	/**
	 * Gets the actual description that is referred to by this object.
	 */
	public abstract GoalDefinition getReferredDescription();

	protected abstract JsonElement serialize(Type typeOfSrc, JsonSerializationContext context);

	public static GoalReference referTo(String id) {
		return new ReferenceByID(id);
	}

	public static GoalReference referTo(GoalDefinition definition) {
		return new DirectReference(definition);
	}

}
