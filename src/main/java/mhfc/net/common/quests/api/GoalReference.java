package mhfc.net.common.quests.api;

import java.lang.reflect.Type;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import net.minecraft.util.JsonUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GoalReference {

	public static class GoalRefSerializer
			implements
				JsonDeserializer<GoalReference> {

		@Override
		public GoalReference deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			return constructFromJson(json, context);
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
		if (element == null)
			return new GoalReference((GoalDescription) null);
		if (JsonUtils.jsonElementTypeIsString(element)) {
			return new GoalReference(JsonUtils.getJsonElementStringValue(
					element, "Goal Reference"));
		} else if (element.isJsonObject()) {
			GoalDescription desc = context.<GoalDescription> deserialize(
					element, GoalDescription.class);
			return new GoalReference(desc);
		}
		throw new JsonParseException(
				"Required a reference on a goal but found something else");
	}

	boolean referByString;
	String id;
	GoalDescription description;

	/**
	 * This class represents a reference to a quest goal, either through the id
	 * corresponding to it or a direct object reference.
	 */

	public GoalReference(String id) {
		this.id = id;
		referByString = true;
		description = null;
	}

	public GoalReference(GoalDescription description) {
		this.description = description;
		referByString = false;
		id = null;
	}

	/**
	 * Gets the actual description that is referred to by this object.
	 */
	public GoalDescription getReferredDescription() {
		if (referByString)
			return MHFCQuestBuildRegistry.getGoalDescription(id);
		else
			return description;
	}

}
