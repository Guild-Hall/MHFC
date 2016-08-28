package mhfc.net.client.quests;

import java.util.Objects;

import mhfc.net.client.quests.api.IMissionInformation;
import mhfc.net.client.quests.api.IVisualDefinition;
import mhfc.net.client.util.gui.MHFCGuiUtil;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.util.stringview.Viewable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.NBTBase;

public class DefaultMissionInformation implements IMissionInformation {

	private GroupProperty goalProperties;
	private DefaultQuestVisualDefinition originalDef;
	private Viewable goalSummary;
	private StringBuilder viewBuffer;

	public DefaultMissionInformation(
			GroupProperty propertyRoot,
			Viewable rootGoalStatus,
			DefaultQuestVisualDefinition defaultQuestVisualDefinition) {
		this.goalProperties = Objects.requireNonNull(propertyRoot);
		goalSummary = rootGoalStatus;
		viewBuffer = new StringBuilder();
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
		int margin = 5;
		MHFCGuiUtil.drawViewable(
				goalSummary,
				viewBuffer,
				page,
				0,
				width - margin * 2,
				height - margin * 2,
				positionX + margin,
				positionY + margin,
				12,
				0x222222,
				fontRenderer);
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
}
