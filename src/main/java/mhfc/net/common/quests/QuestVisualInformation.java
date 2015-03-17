package mhfc.net.common.quests;

import mhfc.net.common.core.registry.MHFCQuestsRegistry;
import mhfc.net.common.util.gui.MHFCGuiUtil;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

public class QuestVisualInformation {

	public static final int BORDER = 5;
	public static final int LINE_SEPERATION = 2;
	public static final int COLOUR_HEADER = 0xb04040;
	public static final int COLOUR_TEXT = 0x404040;
	public static final int COLOUR_TITLE = 0x000000;

	public static final QuestVisualInformation LOADING_REPLACEMENT = new QuestVisualInformation(
		"Loading...", "Waiting for server response", "Guild hunter", "---",
		"----", "Town", "No time limit", "A quest", "None", "---",
		QuestType.EpicHunting);
	public static final QuestVisualInformation IDENTIFIER_ERROR = new QuestVisualInformation(
		"Identifier invalid",
		"Please contact the server operator so he can give information to the mod team",
		"MHFC mod team", "Work out this issue", "Not contacting anyone",
		"Network or server", "Just do it asap", "A better experience",
		"A few seconds of your time", "Hopefully one", QuestType.Gathering);

	public enum QuestType {
		Hunting(MHFCQuestsRegistry.QUEST_TYPE_HUNTING),
		EpicHunting(MHFCQuestsRegistry.QUEST_TYPE_EPIC_HUNTING),
		Killing(MHFCQuestsRegistry.QUEST_TYPE_KILLING),
		Gathering(MHFCQuestsRegistry.QUEST_TYPE_GATHERING);
		QuestType(String s) {
			this.s = s;
		}

		public String getAsString() {
			return s;
		}

		String s;
	}

	protected String name;
	protected String description;
	protected String client;
	protected String aims;
	protected String fails;

	protected String areaNameId;
	protected String timeLimitInS;
	protected QuestType type;

	protected String reward;
	protected String fee;
	protected String maxPartySize;

	protected QuestVisualInformation(QuestVisualInformation copy) {
		if (copy == null)
			return;
		this.name = copy.getName();
		this.description = copy.getDescription();
		this.client = copy.getClient();
		this.aims = copy.getAims();
		this.fails = copy.getFails();
		this.areaNameId = copy.getAreaID();
		this.timeLimitInS = copy.getTimeLimitAsString();
		this.type = copy.getType();
		this.reward = copy.getRewardString();
		this.fee = copy.getFeeString();
		this.maxPartySize = copy.getMaxPartySize();
	}

	public QuestVisualInformation(String name, String description,
		String client, String aims, String fails, String areaNameID,
		String timeLimitInS, String reward, String fee, String maxPartySize,
		QuestType type) {
		this.name = name;
		this.type = type;
		this.timeLimitInS = timeLimitInS;
		this.description = description;
		this.client = client;
		this.aims = aims;
		this.fails = fails;
		this.reward = reward;
		this.fee = fee;
		this.maxPartySize = maxPartySize;
		this.areaNameId = areaNameID;

	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getClient() {
		return client;
	}

	public String getAims() {
		return aims;
	}

	public String getFails() {
		return fails;
	}

	public String getTimeLimitAsString() {
		return timeLimitInS;
	}

	public QuestType getType() {
		return type;
	}

	public String getRewardString() {
		return reward;
	}

	public String getFeeString() {
		return fee;
	}

	public String getAreaID() {
		return areaNameId;
	}

	public String getMaxPartySize() {
		return maxPartySize;
	}

	public void drawInformation(int positionX, int positionY, int width,
		int height, FontRenderer fontRenderer, int renderFrame) {
		int FPS = 120;
		drawInformation(positionX, positionY, width, height, Math.max(
			((renderFrame / FPS) % 4) - 1, 0), fontRenderer);
	}

	public void drawInformation(int positionX, int positionY, int width,
		int height, int page, FontRenderer fontRenderer) {
		String TAG_MONSTERS = StatCollector
			.translateToLocal(MHFCReference.unlocalized_tag_monsters), TAG_REQUISITES = StatCollector
			.translateToLocal(MHFCReference.unlocalized_tag_requisites);
		int lineHeight = fontRenderer.FONT_HEIGHT + 2;
		String draw;
		int currentY = drawHead(positionX, positionY, width, fontRenderer);
		// TODO Implement NYI
		switch (page) {
			case 0 :
				currentY = drawBaseInformation(positionX, currentY, width,
					fontRenderer);
				currentY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer,
					TAG_MONSTERS, positionX + BORDER, currentY, 0,
					COLOUR_HEADER);
				currentY += LINE_SEPERATION;
				currentY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer,
					"NYI", positionX + width / 8, currentY, 7 * width / 8
						- BORDER, COLOUR_TEXT);
				currentY += LINE_SEPERATION;
				currentY = Math.max(currentY, positionY + height / 2);
				currentY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer,
					TAG_REQUISITES, positionX + BORDER, currentY, 0,
					COLOUR_HEADER);
				currentY += LINE_SEPERATION;
				fontRenderer.drawSplitString("NYI", positionX + width / 8,
					currentY, 7 * width / 8 - BORDER, COLOUR_TEXT);
				break;
			case 1 :
				drawAimsFails(positionX, positionY, width, height, currentY,
					fontRenderer);
				break;
			case 2 :
				drawClientDescription(positionX, currentY, width, fontRenderer);
				break;
		}
		draw = (page + 1) + "/3";
		fontRenderer.drawString(draw, positionX + width
			- fontRenderer.getStringWidth(draw) - 4, positionY + height
			- lineHeight, 0x404040);

	}

	protected void drawClientDescription(int positionX, int currentY,
		int width, FontRenderer fontRenderer) {
		width = Math.max(width, 20);
		String TAG_CLIENT = StatCollector
			.translateToLocal(MHFCReference.unlocalized_tag_client), //
		TAG_DESCRIPTION = StatCollector
			.translateToLocal(MHFCReference.unlocalized_tag_description);
		fontRenderer.drawString(TAG_CLIENT, positionX + BORDER, currentY,
			COLOUR_HEADER);
		int offsetX = (BORDER + fontRenderer.getStringWidth(TAG_CLIENT) + BORDER);
		int drawWidth = width - offsetX - BORDER;
		String client = getClient();
		if (MHFCGuiUtil.isDrawWidthTooSmall(fontRenderer, drawWidth, client)) {
			currentY += fontRenderer.FONT_HEIGHT;
			offsetX = width / 8;
			drawWidth = width - offsetX - BORDER;
		}
		currentY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer,
			getClient(), positionX + offsetX, currentY, width + offsetX
				- BORDER, COLOUR_TEXT);
		currentY += LINE_SEPERATION;
		currentY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer,
			TAG_DESCRIPTION, positionX + BORDER, currentY, 0, COLOUR_HEADER);
		currentY += LINE_SEPERATION;
		fontRenderer.drawSplitString(getDescription(), positionX + width / 8,
			currentY, 7 * width / 8 - BORDER, COLOUR_TEXT);
	}

	protected void drawAimsFails(int positionX, int positionY, int width,
		int height, int currentY, FontRenderer fontRenderer) {
		String TAG_AIMS = StatCollector
			.translateToLocal(MHFCReference.unlocalized_tag_aims), //
		TAG_FAILS = StatCollector
			.translateToLocal(MHFCReference.unlocalized_tag_fails);
		currentY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer, TAG_AIMS,
			positionX + BORDER, currentY, 0, COLOUR_HEADER);
		currentY += LINE_SEPERATION;
		currentY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer,
			getAims(), positionX + width / 8, currentY, 7 * width / 8 - BORDER,
			COLOUR_TEXT);
		currentY += LINE_SEPERATION;
		currentY = Math.max(currentY, positionY + height / 2);
		currentY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer,
			TAG_FAILS, positionX + BORDER, currentY, 0, COLOUR_HEADER);
		currentY += LINE_SEPERATION;
		currentY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer,
			getFails(), positionX + width / 8, currentY,
			7 * width / 8 - BORDER, COLOUR_TEXT);
		currentY += LINE_SEPERATION;
	}

	protected int drawHead(int positionX, int positionY, int width,
		FontRenderer fontRenderer) {
		String TAG_TYPE = StatCollector.translateToLocal(this.getType()
			.getAsString());
		positionY += BORDER;
		positionY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer,
			TAG_TYPE, positionX
				+ (width - fontRenderer.getStringWidth(TAG_TYPE)) / 2,
			positionY, 0, COLOUR_TITLE);
		positionY += LINE_SEPERATION;
		String draw = getName();
		positionY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer,
			getName(), positionX + (width - fontRenderer.getStringWidth(draw))
				/ 2, positionY, 0, COLOUR_TITLE);
		positionY += LINE_SEPERATION;
		return positionY;
	}

	protected int drawBaseInformation(int positionX, int positionY, int width,
		FontRenderer fontRenderer) {
		String TAG_FEE = StatCollector
			.translateToLocal(MHFCReference.unlocalized_tag_fee), //
		TAG_REWARD = StatCollector
			.translateToLocal(MHFCReference.unlocalized_tag_reward), //
		TAG_TIME = StatCollector
			.translateToLocal(MHFCReference.unlocalized_tag_time), //
		TAG_AREA = StatCollector
			.translateToLocal(MHFCReference.unlocalized_tag_area), //
		AREA_ID = StatCollector.translateToLocal(getAreaID());
		fontRenderer.drawString(TAG_REWARD, positionX + BORDER, positionY,
			COLOUR_HEADER);
		positionY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer,
			getRewardString(), positionX + width / 2, positionY, width / 2
				- BORDER, COLOUR_TEXT);
		positionY += LINE_SEPERATION;
		fontRenderer.drawString(TAG_FEE, positionX + BORDER, positionY,
			COLOUR_HEADER);
		positionY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer,
			getFeeString(), positionX + width / 2, positionY, width / 2
				- BORDER, COLOUR_TEXT);
		positionY += LINE_SEPERATION;
		fontRenderer.drawString(TAG_TIME, positionX + BORDER, positionY,
			COLOUR_HEADER);
		positionY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer,
			getTimeLimitAsString(), positionX + width / 2, positionY, width / 2
				- BORDER, COLOUR_TEXT);
		positionY += LINE_SEPERATION;
		fontRenderer.drawString(TAG_AREA, positionX + BORDER, positionY,
			COLOUR_HEADER);
		positionY += MHFCGuiUtil.drawTextAndReturnHeight(fontRenderer, AREA_ID,
			positionX + width / 2, positionY, width / 2 - BORDER, COLOUR_TEXT);
		positionY += LINE_SEPERATION;
		return positionY;
	}
}
