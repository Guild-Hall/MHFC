package mhfc.net.common.core.directors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.JsonIOException;

import mhfc.net.common.core.builders.BuilderJsonToQuests;
import mhfc.net.common.core.data.QuestDescriptionRegistry;
import mhfc.net.common.core.data.QuestDescriptionRegistry.IQuestDescriptionDirector;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.util.Utilities;
import net.minecraft.util.ResourceLocation;

/**
 * Loads quest descriptions from the file system
 */
public class DirectorLoadQuestsFromLocal implements IQuestDescriptionDirector {

	@Override
	public void construct(QuestDescriptionRegistry data) {
		BuilderJsonToQuests builderFromJson = new BuilderJsonToQuests(data);
		generateQuests(builderFromJson);
		generateGoals(builderFromJson);
		generateGroupMapping(builderFromJson);

	}

	private BufferedReader openReader(ResourceLocation location) throws IOException {
		return new BufferedReader(new InputStreamReader(Utilities.openEmbeddedResource(location)));
	}

	private void generateGroupMapping(BuilderJsonToQuests builderFromJson) {
		ResourceLocation location = new ResourceLocation(Libraries.groupLocation);
		try (BufferedReader reader = openReader(location)) {
			builderFromJson.acceptGroupMappingFrom(reader);
		} catch (IOException ioe) {
			throw new JsonIOException("Couldn't read from groupLocation " + Libraries.questLocation, ioe);
		}

	}

	private void generateGoals(BuilderJsonToQuests builderFromJson) {
		ResourceLocation location = new ResourceLocation(Libraries.goalLocation);
		try (BufferedReader reader = openReader(location)) {
			builderFromJson.acceptGoalsFrom(reader);
		} catch (IOException ioe) {
			throw new JsonIOException("Couldn't read from goalLocation " + Libraries.questLocation, ioe);
		}
	}

	private void generateQuests(BuilderJsonToQuests builderFromJson) {
		ResourceLocation location = new ResourceLocation(Libraries.questLocation);
		try (BufferedReader reader = openReader(location)) {
			builderFromJson.acceptQuestsFrom(reader);
		} catch (IOException ioe) {
			throw new JsonIOException("Couldn't read from questLocation " + Libraries.questLocation, ioe);
		}
	}

}
