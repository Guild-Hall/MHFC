package mhfc.net.client.quests;

import java.util.List;

import mhfc.net.client.util.gui.MHFCGuiUtil;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.api.VisualDefinition;
import mhfc.net.common.util.MHFCStringDecode.CompositeString;
import mhfc.net.common.util.MHFCStringDecode.StringElement;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

public class QuestRunningInformation extends VisualDefinition implements IRunningInformation {
	/**
	 * Dynamic informations of a quest.
	 *
	 * @author WorldSEnder
	 *
	 */
	public enum InformationType {
		PartySize,
		ShortStatus,
		LongStatus;
	}

	protected CompositeString players;
	protected CompositeString shortStatusElements;
	protected CompositeString longStatusElements;

	public QuestRunningInformation(GeneralQuest quest) {
		super(quest.getOriginalDescription().getVisualInformation());
		updateFromQuest(quest);
	}

	protected void remove(List<StringElement> elements) {
		if (elements == null) {
			return;
		}
		for (StringElement e : elements) {
			e.remove();
		}
	}

	public void updateFromQuest(GeneralQuest q) {
		cleanUp();
		shortStatus = q.updateVisual(InformationType.ShortStatus, shortStatus);
		longStatus = q.updateVisual(InformationType.LongStatus, longStatus);
	}

	@Override
	public String getShortStatus() {
		return shortStatusElements.stringValue();
	}

	public String getTrueShortStatus() {
		return shortStatus;
	}

	@Override
	public String getLongStatus() {
		return longStatusElements.stringValue();
	}

	public String getTrueLongStatus() {
		return longStatus;
	}

	@Override
	public String getName() {
		return nameElements.stringValue();
	}

	public String getTrueName() {
		return name;
	}

	@Override
	public String getDescription() {
		return descriptionElements.stringValue();
	}

	public String getTrueDescription() {
		return description;
	}

	@Override
	public String getClient() {
		return clientElements.stringValue();
	}

	public String getTrueClient() {
		return client;
	}

	@Override
	public String getAims() {
		return aimsElements.stringValue();
	}

	public String getTrueAims() {
		return aims;
	}

	@Override
	public String getFails() {
		return failsElements.stringValue();
	}

	public String getTrueFails() {
		return fails;
	}

	@Override
	public String getTimeLimitAsString() {
		return timeLimitInSElements.stringValue();
	}

	public String getTrueTimeLimitAsString() {
		return timeLimitInS;
	}

	@Override
	public String getRewardString() {
		return rewardElements.stringValue();
	}

	public String getTrueRewardString() {
		return reward;
	}

	@Override
	public String getFeeString() {
		return feeElements.stringValue();
	}

	public String getTrueFeeString() {
		return fee;
	}

	@Override
	public String getAreaName() {
		return areaNameIdElements.stringValue();
	}

	public String getTrueAreaID() {
		return areaNameId;
	}

	@Override
	public String getMaxPartySize() {
		return maxPartySizeElements.stringValue();
	}

	public String getTrueMaxPartySize() {
		return maxPartySize;
	}

	@Override
	public void drawInformation(
			int positionX,
			int positionY,
			int width,
			int height,
			int page,
			FontRenderer fontRenderer) {
		page = page % 3;
		width = Math.max(width, 20);
		int currentY = drawHead(positionX, positionY, width, fontRenderer);
		switch (page) {
		case 0:
			currentY = drawBaseInformation(positionX, currentY, width, fontRenderer);
			String TAG_STATUS = StatCollector.translateToLocal(MHFCReference.unlocalized_tag_status_long);
			currentY += MHFCGuiUtil
					.drawTextAndReturnHeight(fontRenderer, TAG_STATUS, positionX + 5, currentY, 0, COLOUR_HEADER);
			currentY += LINE_SEPERATION;
			String draw = getLongStatus();
			for (String line : draw.split("\n")) {
				currentY += MHFCGuiUtil.drawTextAndReturnHeight(
						fontRenderer,
						line,
						positionX + width / 8,
						currentY,
						7 * width / 8 - BORDER,
						COLOUR_TEXT);
				currentY += LINE_SEPERATION;
			}
			break;
		case 1:
			drawAimsFails(positionX, positionY, width, height, currentY, fontRenderer);
			break;
		case 2:
			drawClientDescription(positionX, currentY, width, fontRenderer);
			break;
		}
		String draw = (page + 1) + "/3";
		fontRenderer.drawString(
				draw,
				positionX + width - fontRenderer.getStringWidth(draw) - BORDER,
				positionY + height - fontRenderer.FONT_HEIGHT - BORDER,
				COLOUR_TEXT);
	}

	@Override
	public void cleanUp() {
		if (nameElements != null) {
			nameElements.remove();
		}
		if (descriptionElements != null) {
			descriptionElements.remove();
		}
		if (clientElements != null) {
			clientElements.remove();
		}
		if (aimsElements != null) {
			aimsElements.remove();
		}
		if (failsElements != null) {
			failsElements.remove();
		}
		if (areaNameIdElements != null) {
			areaNameIdElements.remove();
		}
		if (timeLimitInSElements != null) {
			timeLimitInSElements.remove();
		}
		if (rewardElements != null) {
			rewardElements.remove();
		}
		if (feeElements != null) {
			feeElements.remove();
		}
		if (maxPartySizeElements != null) {
			maxPartySizeElements.remove();
		}
		if (shortStatusElements != null) {
			shortStatusElements.remove();
		}
		if (longStatusElements != null) {
			longStatusElements.remove();
		}
	}

	@Override
	public String getSerializerType() {
		return MHFCQuestBuildRegistry.VISUAL_RUNNING;
	}
}
