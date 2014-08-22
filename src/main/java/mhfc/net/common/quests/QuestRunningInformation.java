package mhfc.net.common.quests;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.eventhandler.MHFCDelayedJob;
import mhfc.net.common.eventhandler.MHFCJobHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

public class QuestRunningInformation extends QuestVisualInformation {

	public enum InformationType {
		Name, Description, Client, Aims, Fails, AreaNameID, TimeLimit, Reward, Fee, MaxPartySize, ShortStatus, LongStatus;
	}

	private interface StringElement {
		public String stringValue();

		public void remove();
	}

	private class StaticString implements StringElement {
		public StaticString(String str) {
			this.str = str;
		}

		private String str;

		@Override
		public String stringValue() {
			return this.str;
		}

		@Override
		public void remove() {
		}
	}

	private class DynamicString implements StringElement, MHFCDelayedJob {

		private String str;
		private volatile String stringValue;
		private volatile int delay;

		public DynamicString(String str) {
			delay = MHFCJobHandler.ticksPerSecond;
			this.str = str;
			switch (str.split(":")[0]) {
				case "time" :
					delay = MHFCJobHandler.ticksPerSecond;
				default :
					stringValue = findReplacement(str);
					MHFCJobHandler.getJobHandler().insert(this,
							getInitialDelay());
					break;
				case "unlocalized" :
					stringValue = findReplacement(str);
					delay = -1;
					break;
			}
		}

		@Override
		public void executeJob() {
			stringValue = findReplacement(str);
			MHFCJobHandler.getJobHandler().insert(this, getInitialDelay());
		}

		@Override
		public int getInitialDelay() {
			return delay;
		}

		@Override
		public String stringValue() {
			return stringValue;
		}

		@Override
		public void remove() {
			MHFCJobHandler.getJobHandler().remove(this);
		}

	}

	protected String shortStatus;
	protected String longStatus;

	protected volatile List<StringElement> nameElements;
	protected volatile List<StringElement> descriptionElements;
	protected volatile List<StringElement> clientElements;
	protected volatile List<StringElement> aimsElements;
	protected volatile List<StringElement> failsElements;

	protected volatile List<StringElement> areaNameIdElements;
	protected volatile List<StringElement> timeLimitInSElements;

	protected volatile List<StringElement> rewardElements;
	protected volatile List<StringElement> feeElements;
	protected volatile List<StringElement> maxPartySizeElements;

	protected volatile List<StringElement> shortStatusElements;
	protected volatile List<StringElement> longStatusElements;

	public QuestRunningInformation(GeneralQuest quest) {
		super(quest.getOriginalDescription().getVisualInformation());
		String localStatusShort = "", localStatusLong = "";
		this.shortStatus = localStatusShort;
		this.longStatus = localStatusLong;
		updateFromQuest(quest);
	}

	public QuestRunningInformation(QuestVisualInformation information,
			String shortStatus, String longStatus) {
		super(information);
		this.shortStatus = shortStatus;
		this.longStatus = longStatus;
		breakAll();
	}

	private List<StringElement> breakApart(String str) {
		List<StringElement> list = new ArrayList<StringElement>(20);
		boolean dynamic = false;
		String firstSplit[] = str.split("\\{");
		List<String> secondSplit = new ArrayList<String>();
		for (String part : firstSplit) {
			for (String small : part.split("\\}")) {
				secondSplit.add(small);
			}
		}
		for (String part : secondSplit) {
			if (part == null)
				part = "";
			if (dynamic) {
				list.add(new DynamicString(part));
			} else {
				list.add(new StaticString(part));
			}
			dynamic = !dynamic;
		}
		return list;
	}

	private String decode(List<StringElement> elements) {
		if (elements == null) {
			System.out.println("Nothing to decode");
			return "NULL";
		}
		String output = "";
		for (int i = 0; i < elements.size(); i++) {
			StringElement e = elements.get(i);
			if (e != null) {
				output += e.stringValue();
			} else
				System.out.println("Something totally wrong");
		}
		return output;
	}

	private String findReplacement(String descriptor) {
		String split[] = descriptor.split(":", 2);
		String identifier = split[0];
		String replacement = "unknown Descriptor " + descriptor;
		switch (identifier) {
			case "time" :
				long subTime = Long.parseLong(split[1]);
				long delta = subTime - System.currentTimeMillis();
				delta /= 1000;
				replacement = "" + (delta >= 3600 ? delta / 3600 + "h " : "")
						+ (delta >= 60 ? (delta % 3600) / 60 + "min " : "")
						+ (delta >= 0 ? delta % 60 : delta) + "s";
				break;
			case "unlocalized" :
				replacement = StatCollector.translateToLocal(split[1]);
				break;
		}
		return replacement;
	}

	protected void remove(List<StringElement> elements) {
		if (elements == null)
			return;
		for (StringElement e : elements) {
			e.remove();
		}
	}

	public void updateFromQuest(GeneralQuest q) {
		name = q.update(InformationType.Name, name);
		description = q.update(InformationType.Description, description);
		client = q.update(InformationType.Client, client);
		aims = q.update(InformationType.Aims, aims);
		fails = q.update(InformationType.Fails, fails);
		areaNameId = q.update(InformationType.AreaNameID, areaNameId);
		timeLimitInS = q.update(InformationType.TimeLimit, timeLimitInS);
		reward = q.update(InformationType.Reward, reward);
		fee = q.update(InformationType.Fee, fee);
		maxPartySize = q.update(InformationType.MaxPartySize, maxPartySize);
		shortStatus = q.update(InformationType.ShortStatus, shortStatus);
		longStatus = q.update(InformationType.LongStatus, longStatus);
	}

	private void breakAll() {
		nameElements = breakApart(name);
		descriptionElements = breakApart(description);
		clientElements = breakApart(client);
		aimsElements = breakApart(aims);
		failsElements = breakApart(fails);
		areaNameIdElements = breakApart(areaNameId);
		timeLimitInSElements = breakApart(timeLimitInS);
		rewardElements = breakApart(reward);
		feeElements = breakApart(fee);
		maxPartySizeElements = breakApart(maxPartySize);
		shortStatusElements = breakApart(shortStatus);
		longStatusElements = breakApart(longStatus);
	}

	public String getShortStatus() {
		return decode(shortStatusElements);
	}

	public String getTrueShortStatus() {
		return shortStatus;
	}

	public String getLongStatus() {
		return decode(longStatusElements);
	}

	public String getTrueLongStatus() {
		return longStatus;
	}

	@Override
	public String getName() {
		return decode(nameElements);
	}

	public String getTrueName() {
		return name;
	}

	@Override
	public String getDescription() {
		return decode(descriptionElements);
	}

	public String getTrueDescription() {
		return description;
	}

	@Override
	public String getClient() {
		return decode(clientElements);
	}

	public String getTrueClient() {
		return client;
	}

	@Override
	public String getAims() {
		return decode(aimsElements);
	}

	public String getTrueAims() {
		return aims;
	}

	@Override
	public String getFails() {
		return decode(failsElements);
	}

	public String getTrueFails() {
		return fails;
	}

	@Override
	public String getTimeLimitAsString() {
		return decode(timeLimitInSElements);
	}

	public String getTrueTimeLimitAsString() {
		return timeLimitInS;
	}

	@Override
	public String getRewardString() {
		return decode(rewardElements);
	}

	public String getTrueRewardString() {
		return reward;
	}

	@Override
	public String getFeeString() {
		return decode(feeElements);
	}

	public String getTrueFeeString() {
		return fee;
	}

	@Override
	public String getAreaID() {
		return decode(areaNameIdElements);
	}

	public String getTrueAreaID() {
		return areaNameId;
	}

	@Override
	public String getMaxPartySize() {
		return decode(maxPartySizeElements);
	}

	public String getTrueMaxPartySize() {
		return maxPartySize;
	}

	@Override
	public void drawInformation(int positionX, int positionY, int width,
			int height, int page, FontRenderer fontRenderer) {
		String draw;
		int lineHeight = fontRenderer.FONT_HEIGHT + 2;
		int currentY = drawHead(positionX, positionY, lineHeight, width,
				fontRenderer);
		switch (page) {
			case 0 :
				currentY = drawBaseInformation(positionX, currentY, lineHeight,
						width, fontRenderer);
				String TAG_STATUS = StatCollector
						.translateToLocal(MHFCRegQuestVisual.UNLOCALIZED_TAG_STATUS_LONG);
				draw = TAG_STATUS;
				fontRenderer
						.drawString(draw, positionX + 5, currentY, 0xB04040);
				draw = getLongStatus();
				currentY += lineHeight;
				for (String line : draw.split("\n")) {
					fontRenderer.drawSplitString(line, positionX + width / 8,
							currentY, 7 * width / 8 - 5, 0x404040);
					currentY += (fontRenderer.listFormattedStringToWidth(line,
							7 * width / 8 - 5).size()) * lineHeight + 2;
				}
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

	public void cleanUp() {
		remove(nameElements);
		remove(descriptionElements);
		remove(clientElements);
		remove(aimsElements);
		remove(failsElements);
		remove(areaNameIdElements);
		remove(timeLimitInSElements);
		remove(rewardElements);
		remove(feeElements);
		remove(maxPartySizeElements);
		remove(shortStatusElements);
		remove(longStatusElements);
	}
}
