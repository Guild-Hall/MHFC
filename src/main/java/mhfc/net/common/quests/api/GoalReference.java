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
import net.minecraft.util.ResourceLocation;

/**
 * This class represents a reference to a quest goal, either through the id corresponding to it or a direct object
 * reference.
 */
public abstract class GoalReference {

	public static class GoalRefSerializer implements JsonDeserializer<GoalReference>, JsonSerializer<GoalReference> {
		@Override
		public GoalReference deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			if (element == null || element.isJsonNull()) {
				return referTo((GoalDefinitionDelegate) null);
			}
			if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
				return referTo(JsonUtils.getString(element, "Goal Reference"));
			} else if (element.isJsonObject()) {
				GoalDefinitionDelegate desc = context.deserialize(element, GoalDefinitionDelegate.class);
				return referTo(desc);
			}
			throw new JsonParseException("Required a reference on a goal but found something else");
		}

		@Override
		public JsonElement serialize(GoalReference src, Type typeOfSrc, JsonSerializationContext context) {
			return src.serialize(typeOfSrc, context);
		}
	}

	private static class ReferenceByID extends GoalReference {
		private ResourceLocation id;

		public ReferenceByID(String id) {
			this.id = new ResourceLocation(id);
		}

		@Override
		public IGoalDefinition getReferredDescription() {
			return MHFCQuestBuildRegistry.getGoalDescription(id);
		}

		@Override
		protected JsonElement serialize(Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(id.toString());
		}
	}

	private static class DirectReference extends GoalReference {
		private GoalDefinitionDelegate description;

		public DirectReference(GoalDefinitionDelegate description) {
			this.description = description;
		}

		@Override
		public IGoalDefinition getReferredDescription() {
			return description.getValue();
		}

		@Override
		protected JsonElement serialize(Type typeOfSrc, JsonSerializationContext context) {
			if (description == null) {
				return JsonNull.INSTANCE;
			}
			return context.serialize(description, GoalDefinitionDelegate.class);
		}
	}

	/**
	 * Gets the actual description that is referred to by this object.
	 */
	public abstract IGoalDefinition getReferredDescription();

	protected abstract JsonElement serialize(Type typeOfSrc, JsonSerializationContext context);

	public static GoalReference referTo(String id) {
		return new ReferenceByID(id);
	}

	public static GoalReference referTo(GoalDefinitionDelegate definition) {
		return new DirectReference(definition);
	}

}
