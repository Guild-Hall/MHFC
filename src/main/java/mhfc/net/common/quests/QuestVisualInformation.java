package mhfc.net.common.quests;

import mhfc.net.common.core.registry.MHFCQuestsRegistry;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

public class QuestVisualInformation {

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
		Hunting(MHFCQuestsRegistry.QUEST_TYPE_HUNTING), EpicHunting(
				MHFCQuestsRegistry.QUEST_TYPE_EPIC_HUNTING), Killing(
				MHFCQuestsRegistry.QUEST_TYPE_KILLING), Gathering(
				MHFCQuestsRegistry.QUEST_TYPE_GATHERING);
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
			String timeLimitInS, String reward, String fee,
			String maxPartySize, QuestType type) {
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
		drawInformation(positionX, positionY, width, height,
				Math.max(((renderFrame / FPS) % 4) - 1, 0), fontRenderer);
	}

	public void drawInformation(int positionX, int positionY, int width,
			int height, int page, FontRenderer fontRenderer) {
		// FIXME rewrite for correctly position all lines, even with line breaks
		String TAG_MONSTERS = StatCollector
				.translateToLocal(MHFCReference.unlocalized_tag_monsters), //
		TAG_REQUISITES = StatCollector
				.translateToLocal(MHFCReference.unlocalized_tag_requisites);
//@formatter:off
		int lineHeight = fontRenderer.FONT_HEIGHT+2;
		String draw;
		int currentY = drawHead(positionX, positionY, lineHeight, width,
				fontRenderer);
		//TODO Implement NYI
		switch (page) {
			case 0 :
				currentY = drawBaseInformation(positionX, currentY, lineHeight,
						width, fontRenderer);
				draw = TAG_MONSTERS;
				fontRenderer.drawString(draw, positionX + 5, currentY, 0xB04040);
				draw = "NYI";
				currentY += lineHeight;
				fontRenderer.drawSplitString(draw, positionX + width / 8,
						currentY, width / 2 - 5, 0x404040);
				currentY = positionY+(height-(positionY-currentY+lineHeight)-5)/2;
				draw = TAG_REQUISITES;
				fontRenderer.drawString(draw, positionX + 5, currentY, 0xB04040);
				draw = "NYI";
				currentY += lineHeight;
				fontRenderer.drawSplitString(draw, positionX + width / 8,
						currentY, width / 2 - 5, 0x404040);
				break;
			case 1 :
				drawAimsFails(positionX, positionY, width, height, currentY,
						lineHeight, fontRenderer);
				break;
			case 2 :
				drawClientDescription(positionX, currentY, width, lineHeight,
						fontRenderer);
				break;
		}
		draw = (page + 1) + "/3";
		fontRenderer.drawString(draw,
				positionX + width - fontRenderer.getStringWidth(draw) - 4,
				positionY + height - lineHeight, 0x404040);

	}

	protected void drawClientDescription(int positionX, int currentY,
			int width, int lineHeight, FontRenderer fontRenderer) {
		width = Math.max(width, 20);
		String TAG_CLIENT = StatCollector
				.translateToLocal(MHFCReference.unlocalized_tag_client), //
		TAG_DESCRIPTION = StatCollector
				.translateToLocal(MHFCReference.unlocalized_tag_description);
		String draw;
		draw = TAG_CLIENT;
		fontRenderer.drawString(draw, positionX+5, currentY, 0xB04040);
		draw = getClient();
		fontRenderer.drawSplitString(draw, positionX+
				(fontRenderer.getStringWidth(TAG_CLIENT)+width
				-fontRenderer.getStringWidth(draw))/2,
				currentY, 2*width/3, 0x404040);
		currentY += fontRenderer.listFormattedStringToWidth(draw,Math.max(width/2,10))
				.size()*lineHeight;
		draw = TAG_DESCRIPTION;
		fontRenderer.drawString(draw, positionX+5, currentY, 0xB04040);
		currentY += lineHeight;
		draw = getDescription();
		fontRenderer.drawSplitString(draw, positionX+width/8, currentY,
				7*width/8-5, 0x404040);
	}

	protected void drawAimsFails(int positionX, int positionY, int width,
			int height, int currentY, int lineHeight,
			FontRenderer fontRenderer) {
		String TAG_AIMS = StatCollector
				.translateToLocal(MHFCReference.unlocalized_tag_aims), //
		TAG_FAILS = StatCollector
				.translateToLocal(MHFCReference.unlocalized_tag_fails);
		String draw;
		draw = TAG_AIMS;
		fontRenderer.drawString(draw, positionX+5, currentY, 0xB04040);
		currentY += lineHeight;
		draw = getAims();
		fontRenderer.drawSplitString(draw, positionX+width/8, currentY, 7*width/8-5, 0x404040);
		draw = TAG_FAILS;
		currentY = positionY+(height-(positionY-currentY+lineHeight)-5)/2;
		fontRenderer.drawString(draw, positionX+5, currentY, 0xB04040);
		currentY += lineHeight;
		draw = getFails();
		fontRenderer.drawSplitString(draw, positionX+width/8, currentY, 7*width/8-5, 0x404040);
	}

	protected int drawHead(int positionX, int positionY, int lineHeight,
			int width, FontRenderer fontRenderer) {
		String TAG_TYPE = StatCollector.translateToLocal(this.getType()
				.getAsString());
		fontRenderer
				.drawString(
						TAG_TYPE,
						positionX
								+ (width - fontRenderer
										.getStringWidth(TAG_TYPE)) / 2,
						positionY + 5, 0x000000);
		String draw = getName();
		fontRenderer.drawString(draw,
				positionX + (width - fontRenderer.getStringWidth(draw)) / 2,
				positionY + 5 + lineHeight, 0x000000);
		int currentY = positionY + 2 * lineHeight + 8;
		return currentY;
	}

	protected int drawBaseInformation(int positionX, int positionY,
			int lineHeight, int width, FontRenderer fontRenderer) {
		String TAG_FEE = StatCollector
				.translateToLocal(MHFCReference.unlocalized_tag_fee), //
		TAG_REWARD = StatCollector
				.translateToLocal(MHFCReference.unlocalized_tag_reward), //
		TAG_TIME = StatCollector
				.translateToLocal(MHFCReference.unlocalized_tag_time), //
		TAG_AREA = StatCollector
				.translateToLocal(MHFCReference.unlocalized_tag_area);
		String draw;
		draw = TAG_REWARD;
		fontRenderer.drawString(draw, positionX + 5, positionY, 0xB04040);
		draw = getRewardString();
		fontRenderer.drawSplitString(draw, positionX + width / 2, positionY,
				width / 2 - 5, 0x404040);
		positionY += lineHeight;
		draw = TAG_FEE;
		fontRenderer.drawString(draw, positionX + 5, positionY, 0xB04040);
		draw = getFeeString();
		fontRenderer.drawSplitString(draw, positionX + width / 2, positionY,
				width / 2 - 5, 0x404040);
		positionY += lineHeight;
		draw = TAG_TIME;
		fontRenderer.drawString(draw, positionX + 5, positionY, 0xB04040);
		draw = getTimeLimitAsString();
		fontRenderer.drawSplitString(draw, positionX + width / 2, positionY,
				width / 2 - 5, 0x404040);
		positionY += lineHeight;
		draw = TAG_AREA;
		fontRenderer.drawString(draw, positionX + 5, positionY, 0xB04040);
		draw = StatCollector.translateToLocal(getAreaID());
		fontRenderer.drawSplitString(draw, positionX + width / 2, positionY,
				width / 2 - 5, 0x404040);
		positionY += lineHeight;
		return positionY;
	}

	// @formatter:on
}
