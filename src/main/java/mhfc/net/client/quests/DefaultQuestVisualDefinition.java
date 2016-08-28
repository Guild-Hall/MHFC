package mhfc.net.client.quests;

import com.google.common.base.Preconditions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import mhfc.net.client.quests.api.IMissionInformation;
import mhfc.net.client.quests.api.IVisualDefinition;
import mhfc.net.client.util.gui.MHFCGuiUtil;
import mhfc.net.common.quests.api.IGoalFactory;
import mhfc.net.common.quests.descriptions.DefaultQuestDescription;
import mhfc.net.common.quests.descriptions.DefaultQuestDescription.QuestType;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.util.MHFCJsonUtils;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.util.stringview.Viewable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

public class DefaultQuestVisualDefinition implements IVisualDefinition {
	public static final int BORDER = 5;
	public static final int LINE_SEPERATION = 2;
	public static final int COLOUR_HEADER = MHFCGuiUtil.COLOUR_FOREGROUND;
	public static final int COLOUR_TEXT = MHFCGuiUtil.COLOUR_TEXT;
	public static final int COLOUR_TITLE = MHFCGuiUtil.COLOUR_TITLE;

	public static class QuestVisualInformationFactory {

		public static final String KEY_NAME = "name";
		public static final String KEY_DESCRIPTION = "longDescription";
		public static final String KEY_CLIENT = "client";
		public static final String KEY_AIMS = "aims";
		public static final String KEY_FAILS = "fails";
		public static final String KEY_AREA_ID = "areaID";
		public static final String KEY_REWARD = "reward";
		public static final String KEY_FEE = "fee";
		public static final String KEY_MAX_PARTY_SIZE = "maxPartySize";
		public static final String KEY_TIME_LIMIT = "timeLimit";
		public static final String KEY_TYPE = "questType";

		private DefaultQuestDescription quest;
		private JsonObject jsonObject;

		public QuestVisualInformationFactory() {}

		public QuestVisualInformationFactory(DefaultQuestDescription quest) {
			this.quest = quest;
		}

		public QuestVisualInformationFactory decodingFrom(JsonElement json, JsonDeserializationContext context) {
			this.jsonObject = json.getAsJsonObject();
			return this;
		}

		public DefaultQuestVisualDefinition forQuest(DefaultQuestDescription quest) {
			Preconditions.checkState(jsonObject != null, "must set the json to decode first");
			this.quest = quest;
			String name = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(jsonObject, KEY_NAME, getDefaultName());
			String description = MHFCJsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonObject, KEY_DESCRIPTION, getDefaultDescription());
			String client = MHFCJsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonObject, KEY_CLIENT, getDefaultClient());
			String aims = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(jsonObject, KEY_AIMS, getDefaultAims());
			String fails = MHFCJsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonObject, KEY_FAILS, getDefaultFails());
			String areaName = quest.getAreaType().getUnlocalizedName();
			String timeLimit = MHFCJsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonObject, KEY_TIME_LIMIT, getDefaultTimeLimit());
			String reward = MHFCJsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonObject, KEY_REWARD, getDefaultReward());
			String fee = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(jsonObject, KEY_FEE, getDefaultFee());
			String maxPartySize = MHFCJsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonObject, KEY_MAX_PARTY_SIZE, getDefaultPartySize());
			String type = MHFCJsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonObject, KEY_TYPE, getDefaultQuestType());
			return new DefaultQuestVisualDefinition(
					quest,
					name,
					description,
					client,
					aims,
					fails,
					areaName,
					timeLimit,
					reward,
					fee,
					maxPartySize,
					type);
		}

		public JsonElement serialize(DefaultQuestVisualDefinition information, JsonSerializationContext context) {
			JsonObject holder = new JsonObject();
			holder.addProperty(KEY_NAME, information.name);
			holder.addProperty(KEY_DESCRIPTION, information.description);
			holder.addProperty(KEY_CLIENT, information.client);
			holder.addProperty(KEY_AIMS, information.aims);
			holder.addProperty(KEY_FAILS, information.fails);
			holder.addProperty(KEY_AREA_ID, information.areaNameId);
			holder.addProperty(KEY_TIME_LIMIT, information.timeLimitInS);
			holder.addProperty(KEY_REWARD, information.reward);
			holder.addProperty(KEY_FEE, information.fee);
			holder.addProperty(KEY_MAX_PARTY_SIZE, information.maxPartySize);
			holder.addProperty(KEY_TYPE, information.typeString);
			return holder;
		}

		private String getDefaultName() {
			return "Quest";
		}

		private String getDefaultDescription() {
			return "A new monster threatens the town so go out and kill it soon.";
		}

		private String getDefaultClient() {
			return "Hunter Guild";
		}

		private String getDefaultAims() {
			return "Kill all big monsters!";
		}

		private String getDefaultFails() {
			return "Died three times or time has run out!";
		}

		private String getDefaultTimeLimit() {
			return "As fast as possible";
		}

		private String getDefaultReward() {
			return quest == null ? "Gratitude from everybody" : quest.getReward() + "z";
		}

		private String getDefaultFee() {
			return quest == null ? "For free!" : quest.getFee() + "z";
		}

		private String getDefaultPartySize() {
			return quest == null ? "A few friends" : quest.getMaxPartySize() + " hunters";
		}

		private String getDefaultQuestType() {
			return quest == null ? "Unknown" : quest.getQuestType().getAsString();
		}
	}

	public static final DefaultQuestVisualDefinition LOADING_REPLACEMENT = new DefaultQuestVisualDefinition(
			DefaultQuestDescription.UNKNOWN_DESCRIPTION,
			"Loading...",
			"Waiting for server response",
			"Guild hunter",
			"---",
			"----",
			"Town",
			"No time limit",
			"A quest",
			"None",
			"---",
			QuestType.EpicHunting.getAsString());
	public static final DefaultQuestVisualDefinition IDENTIFIER_ERROR = new DefaultQuestVisualDefinition(
			DefaultQuestDescription.UNKNOWN_DESCRIPTION,
			"Identifier invalid",
			"Please contact the server operator so he can give information to the mod team",
			"MHFC mod team",
			"Work out this issue",
			"Not contacting anyone",
			"Network or server",
			"Just do it asap",
			"A better experience",
			"A few seconds of your time",
			"Hopefully one",
			QuestType.Gathering.getAsString());
	public static final DefaultQuestVisualDefinition UNKNOWN = new DefaultQuestVisualDefinition(
			DefaultQuestDescription.UNKNOWN_DESCRIPTION,
			"Unknown quest",
			"Creating visual failed. The quest exists though",
			"Hunter's guild",
			"Gather and slay what you can and report this",
			"Dont't die",
			"Somewhere in a galaxy far away",
			"A long time ago",
			"Server owners gratitude for reporting",
			"What did you pay?",
			"A few friends",
			"Unknown quest");

	protected DefaultQuestDescription quest;

	protected String name;
	protected String description;
	protected String client;
	protected String aims;
	protected String fails;

	protected String areaNameId;
	protected String timeLimitInS;
	protected String typeString;

	protected String reward;
	protected String fee;
	protected String maxPartySize;

	public DefaultQuestVisualDefinition(
			DefaultQuestDescription quest,
			String name,
			String description,
			String client,
			String aims,
			String fails,
			String areaNameID,
			String timeLimitInS,
			String reward,
			String fee,
			String maxPartySize,
			String type) {
		this.quest = quest;
		this.name = name;
		this.typeString = type;
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

	@Override
	public String getDisplayName() {
		return getNameLocalized();
	}

	@Override
	public IMissionInformation build() {
		GroupProperty propertyRoot = GroupProperty.makeRootProperty();
		IGoalFactory factory = quest.bindGoalVisuals(propertyRoot);
		Viewable rootGoalStatus = factory.buildVisual();
		Viewable rootShortStatus = factory.buildShortStatus();
		return new DefaultMissionInformation(propertyRoot, rootGoalStatus, rootShortStatus, this);
	}

	@Override
	public int getPageCount() {
		return 3;
	}

	@Override
	public void drawInformation(
			int positionX,
			int positionY,
			int width,
			int height,
			int page,
			FontRenderer fontRenderer) {
		String TAG_MONSTERS = StatCollector.translateToLocal(MHFCReference.unlocalized_tag_monsters),
				TAG_REQUISITES = StatCollector.translateToLocal(MHFCReference.unlocalized_tag_requisites);
		int lineHeight = fontRenderer.FONT_HEIGHT + 2;
		String draw;
		page = page % 3;
		int currentY = drawHead(positionX, positionY, width, fontRenderer);
		// TODO Implement NYI
		switch (page) {
		case 0:
			currentY = drawBaseInformation(positionX, currentY, width, fontRenderer);
			currentY += MHFCGuiUtil.drawTextAndReturnHeight(
					fontRenderer,
					TAG_MONSTERS,
					positionX + BORDER,
					currentY,
					0,
					COLOUR_HEADER);
			currentY += LINE_SEPERATION;
			currentY += MHFCGuiUtil.drawTextAndReturnHeight(
					fontRenderer,
					"NYI",
					positionX + width / 8,
					currentY,
					7 * width / 8 - BORDER,
					COLOUR_TEXT);
			currentY += LINE_SEPERATION;
			currentY = Math.max(currentY, positionY + height / 2);
			currentY += MHFCGuiUtil.drawTextAndReturnHeight(
					fontRenderer,
					TAG_REQUISITES,
					positionX + BORDER,
					currentY,
					0,
					COLOUR_HEADER);
			currentY += LINE_SEPERATION;
			fontRenderer.drawSplitString("NYI", positionX + width / 8, currentY, 7 * width / 8 - BORDER, COLOUR_TEXT);
			break;
		case 1:
			drawAimsFails(positionX, positionY, width, height, currentY, fontRenderer);
			break;
		case 2:
			drawClientDescription(positionX, currentY, width, fontRenderer);
			break;
		}
		draw = (page + 1) + "/3";
		fontRenderer.drawString(
				draw,
				positionX + width - fontRenderer.getStringWidth(draw) - 4,
				positionY + height - lineHeight,
				COLOUR_TEXT);

	}

	protected void drawClientDescription(int positionX, int currentY, int width, FontRenderer fontRenderer) {
		width = Math.max(width, 20);
		String TAG_CLIENT = StatCollector.translateToLocal(MHFCReference.unlocalized_tag_client);
		fontRenderer.drawString(TAG_CLIENT, positionX + BORDER, currentY, COLOUR_HEADER);
		int offsetX = (BORDER + fontRenderer.getStringWidth(TAG_CLIENT) + BORDER);
		int drawWidth = width - offsetX - BORDER;
		String client = getClientLocalized();
		if (MHFCGuiUtil.isDrawWidthTooSmall(fontRenderer, drawWidth, client)) {
			currentY += fontRenderer.FONT_HEIGHT;
			offsetX = width / 8;
			drawWidth = width - offsetX - BORDER;
		}
		currentY += MHFCGuiUtil.drawTextAndReturnHeight(
				fontRenderer,
				client,
				positionX + offsetX,
				currentY,
				width + offsetX - BORDER,
				COLOUR_TEXT);
		currentY += LINE_SEPERATION;
		currentY += MHFCGuiUtil.drawTextLocalizedAndReturnHeight(
				fontRenderer,
				MHFCReference.unlocalized_tag_description,
				positionX + BORDER,
				currentY,
				0,
				COLOUR_HEADER);
		currentY += LINE_SEPERATION;
		MHFCGuiUtil.drawTextLocalizedAndReturnHeight(
				fontRenderer,
				description,
				positionX + width / 8,
				currentY,
				7 * width / 8 - BORDER,
				COLOUR_TEXT);
	}

	private String getClientLocalized() {
		return StatCollector.translateToLocal(client);
	}

	protected void drawAimsFails(
			int positionX,
			int positionY,
			int width,
			int height,
			int currentY,
			FontRenderer fontRenderer) {
		currentY += MHFCGuiUtil.drawTextLocalizedAndReturnHeight(
				fontRenderer,
				MHFCReference.unlocalized_tag_aims,
				positionX + BORDER,
				currentY,
				0,
				COLOUR_HEADER);
		currentY += LINE_SEPERATION;
		currentY += MHFCGuiUtil.drawTextLocalizedAndReturnHeight(
				fontRenderer,
				aims,
				positionX + width / 8,
				currentY,
				7 * width / 8 - BORDER,
				COLOUR_TEXT);
		currentY += LINE_SEPERATION;
		currentY = Math.max(currentY, positionY + height / 2);
		currentY += MHFCGuiUtil.drawTextLocalizedAndReturnHeight(
				fontRenderer,
				MHFCReference.unlocalized_tag_fails,
				positionX + BORDER,
				currentY,
				0,
				COLOUR_HEADER);
		currentY += LINE_SEPERATION;
		currentY += MHFCGuiUtil.drawTextLocalizedAndReturnHeight(
				fontRenderer,
				fails,
				positionX + width / 8,
				currentY,
				7 * width / 8 - BORDER,
				COLOUR_TEXT);
		currentY += LINE_SEPERATION;
	}

	protected int drawHead(int positionX, int positionY, int width, FontRenderer fontRenderer) {
		String questtype = getTypeLocalized();
		positionY += BORDER;
		positionY += MHFCGuiUtil.drawTextAndReturnHeight(
				fontRenderer,
				questtype,
				positionX + (width - fontRenderer.getStringWidth(questtype)) / 2,
				positionY,
				0,
				COLOUR_TITLE);
		positionY += LINE_SEPERATION;
		String draw = getNameLocalized();
		positionY += MHFCGuiUtil.drawTextAndReturnHeight(
				fontRenderer,
				draw,
				positionX + (width - fontRenderer.getStringWidth(draw)) / 2,
				positionY,
				0,
				COLOUR_TITLE);
		positionY += LINE_SEPERATION;
		return positionY;
	}

	private String getTypeLocalized() {
		return StatCollector.translateToLocal(this.typeString);
	}

	private String getNameLocalized() {
		return StatCollector.translateToLocal(name);
	}

	protected int drawBaseInformation(int positionX, int positionY, int width, FontRenderer fontRenderer) {
		String TAG_FEE = StatCollector.translateToLocal(MHFCReference.unlocalized_tag_fee), //
				TAG_REWARD = StatCollector.translateToLocal(MHFCReference.unlocalized_tag_reward), //
				TAG_TIME = StatCollector.translateToLocal(MHFCReference.unlocalized_tag_time), //
				TAG_AREA = StatCollector.translateToLocal(MHFCReference.unlocalized_tag_area);
		fontRenderer.drawString(TAG_REWARD, positionX + BORDER, positionY, COLOUR_HEADER);
		positionY += MHFCGuiUtil.drawTextLocalizedAndReturnHeight(
				fontRenderer,
				reward,
				positionX + width / 2,
				positionY,
				width / 2 - BORDER,
				COLOUR_TEXT);
		positionY += LINE_SEPERATION;
		fontRenderer.drawString(TAG_FEE, positionX + BORDER, positionY, COLOUR_HEADER);
		positionY += MHFCGuiUtil.drawTextLocalizedAndReturnHeight(
				fontRenderer,
				fee,
				positionX + width / 2,
				positionY,
				width / 2 - BORDER,
				COLOUR_TEXT);
		positionY += LINE_SEPERATION;
		fontRenderer.drawString(TAG_TIME, positionX + BORDER, positionY, COLOUR_HEADER);
		positionY += MHFCGuiUtil.drawTextLocalizedAndReturnHeight(
				fontRenderer,
				timeLimitInS,
				positionX + width / 2,
				positionY,
				width / 2 - BORDER,
				COLOUR_TEXT);
		positionY += LINE_SEPERATION;
		fontRenderer.drawString(TAG_AREA, positionX + BORDER, positionY, COLOUR_HEADER);
		positionY += MHFCGuiUtil.drawTextLocalizedAndReturnHeight(
				fontRenderer,
				areaNameId,
				positionX + width / 2,
				positionY,
				width / 2 - BORDER,
				COLOUR_TEXT);
		positionY += LINE_SEPERATION;
		return positionY;
	}

}
