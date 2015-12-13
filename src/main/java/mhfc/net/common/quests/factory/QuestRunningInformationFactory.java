package mhfc.net.common.quests.factory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.IVisualInformation;
import mhfc.net.common.quests.api.IVisualInformationFactory;

public class QuestRunningInformationFactory
	implements
		IVisualInformationFactory {

	@Override
	public IVisualInformation buildInformation(JsonElement json,
		JsonDeserializationContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement serialize(IVisualInformation information,
		JsonSerializationContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
