package mhfc.net.client.quests;

import java.util.Objects;

import mhfc.net.client.quests.api.IMissionInformation;
import mhfc.net.client.quests.api.IVisualDefinition;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.util.stringview.Viewable;
import net.minecraft.nbt.NBTBase;

public class DefaultMissionInformation implements IMissionInformation {

	private GroupProperty goalProperties;
	private DefaultQuestVisualDefinition originalDef;
	private Viewable goalSummary;
	private Viewable shortStatus;

	public DefaultMissionInformation(
			GroupProperty propertyRoot,
			Viewable rootGoalStatus,
			Viewable goalShortSummary,
			DefaultQuestVisualDefinition defaultQuestVisualDefinition) {
		this.goalProperties = Objects.requireNonNull(propertyRoot);
		this.goalSummary = rootGoalStatus;
		this.originalDef = Objects.requireNonNull(defaultQuestVisualDefinition);
		this.shortStatus = Objects.requireNonNull(goalShortSummary);
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
	public Viewable getView() {
		return goalSummary;
	}

	@Override
	public int getPageCount() {
		return 1;
	}

	@Override
	public Viewable getShortStatus() {
		return shortStatus;
	}
}
