package mhfc.net.common.quests.api;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;

public class VisualDefinition implements IVisualInformation {

	public static final VisualDefinition LOADING_REPLACEMENT = new VisualDefinition(
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
			QuestDefinition.QuestType.EpicHunting.getAsString());
	public static final IVisualInformation IDENTIFIER_ERROR = new VisualDefinition(
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
			QuestDefinition.QuestType.Gathering.getAsString());
	public static final IVisualInformation UNKNOWN = new VisualDefinition(
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

	protected VisualDefinition(IVisualInformation copy) {
		if (copy == null) {
			return;
		}
		this.name = copy.getName();
		this.description = copy.getDescription();
		this.client = copy.getClient();
		this.aims = copy.getAims();
		this.fails = copy.getFails();
		this.areaNameId = copy.getAreaName();
		this.timeLimitInS = copy.getTimeLimitAsString();
		this.typeString = copy.getQuestType();
		this.reward = copy.getRewardString();
		this.fee = copy.getFeeString();
		this.maxPartySize = copy.getMaxPartySize();
	}

	public VisualDefinition(
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

	/*
	 * (non-Javadoc)
	 *
	 * @see mhfc.net.common.quests.IVisualInformation#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mhfc.net.common.quests.IVisualInformation#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mhfc.net.common.quests.IVisualInformation#getClient()
	 */
	@Override
	public String getClient() {
		return client;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mhfc.net.common.quests.IVisualInformation#getAims()
	 */
	@Override
	public String getAims() {
		return aims;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mhfc.net.common.quests.IVisualInformation#getFails()
	 */
	@Override
	public String getFails() {
		return fails;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mhfc.net.common.quests.IVisualInformation#getTimeLimitAsString()
	 */
	@Override
	public String getTimeLimitAsString() {
		return timeLimitInS;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mhfc.net.common.quests.IVisualInformation#getType()
	 */
	@Override
	public String getQuestType() {
		return typeString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mhfc.net.common.quests.IVisualInformation#getRewardString()
	 */
	@Override
	public String getRewardString() {
		return reward;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mhfc.net.common.quests.IVisualInformation#getFeeString()
	 */
	@Override
	public String getFeeString() {
		return fee;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mhfc.net.common.quests.IVisualInformation#getAreaID()
	 */
	@Override
	public String getAreaName() {
		return areaNameId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mhfc.net.common.quests.IVisualInformation#getMaxPartySize()
	 */
	@Override
	public String getMaxPartySize() {
		return maxPartySize;
	}

	@Override
	public String getSerializerType() {
		return MHFCQuestBuildRegistry.VISUAL_DEFAULT;
	}
}
