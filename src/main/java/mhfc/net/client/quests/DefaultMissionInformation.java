package mhfc.net.client.quests;

import java.util.Objects;

import mhfc.net.client.quests.api.IMissionInformation;
import mhfc.net.client.quests.api.IVisualDefinition;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.properties.GroupProperty;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.NBTBase;

public class DefaultMissionInformation implements IMissionInformation {

	private GroupProperty goalProperties;
	private QuestGoal rootGoal;
	private DefaultQuestVisualDefinition originalDef;

	public DefaultMissionInformation(
			GroupProperty propertyRoot,
			QuestGoal rootGoal,
			DefaultQuestVisualDefinition defaultQuestVisualDefinition) {
		this.goalProperties = Objects.requireNonNull(propertyRoot);
		this.rootGoal = Objects.requireNonNull(rootGoal);
		this.originalDef = Objects.requireNonNull(defaultQuestVisualDefinition);
	}

	@Override
	public IVisualDefinition getOriginalDefinition() {
		return originalDef;
	}

	@Override
	public void updateProperties(NBTBase nbtTag) {
		this.goalProperties.updateFrom(nbtTag);
	}

	@Override
	public void drawInformation(
			int positionX,
			int positionY,
			int width,
			int height,
			int page,
			FontRenderer fontRenderer) {
		// TODO Auto-generated method stub
		fontRenderer.drawString("WIP", 10, 20, 0xFFFFFF);
	}

	@Override
	public int getPageCount() {
		return 1;
	}

	@Override
	public String shortStatus() {
		// TODO Auto-generated method stub
		return "WIP - short status";
	}

	@Override
	public void cleanUp() {
		rootGoal.questGoalFinalize();
	}
}
