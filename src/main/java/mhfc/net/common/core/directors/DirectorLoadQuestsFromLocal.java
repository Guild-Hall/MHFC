package mhfc.net.common.core.directors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mhfc.net.common.core.builders.BuilderJsonToQuests;
import mhfc.net.common.core.data.QuestDescriptionRegistryData;
import mhfc.net.common.core.data.QuestDescriptionRegistryData.IQuestDescriptionDirector;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.util.Utilities;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.util.ResourceLocation;

/**
 * Loads quest descriptions from the file system
 */
public class DirectorLoadQuestsFromLocal implements IQuestDescriptionDirector {

	@Override
	public void construct(QuestDescriptionRegistryData data) {
		BuilderJsonToQuests builderFromJson = new BuilderJsonToQuests(data);
		generateQuests(builderFromJson);
		generateGoals(builderFromJson);
		generateGroupMapping(builderFromJson);

	}

	private void generateGroupMapping(BuilderJsonToQuests builderFromJson) {
		ResourceLocation location = new ResourceLocation(
			MHFCReference.groupLocation);
		try (InputStream input = Utilities.inputStream(location);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				input))) {
			builderFromJson.generateGroupMapping(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void generateGoals(BuilderJsonToQuests builderFromJson) {
		ResourceLocation location = new ResourceLocation(
			MHFCReference.goalLocation);
		try (InputStream input = Utilities.inputStream(location);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				input))) {
			builderFromJson.generateGoals(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generateQuests(BuilderJsonToQuests builderFromJson) {
		ResourceLocation location = new ResourceLocation(
			MHFCReference.questLocation);
		String pathToRes = "/assets/" + location.getResourceDomain() + "/"
			+ location.getResourcePath();
		try (InputStream input = MHFCQuestBuildRegistry.class
			.getResourceAsStream(pathToRes);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				input))) {
			builderFromJson.generateQuests(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
