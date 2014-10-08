package mhfc.net.common.quests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.core.registry.MHFCRegStringDecode;
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

	private class CompositeString implements StringElement {
		protected CompositeString parent;
		List<StringElement> parts;

		public CompositeString(String toBreak) {
			parts = new LinkedList<StringElement>();
			parts.addAll(breakApart(toBreak));
			for (StringElement s : parts) {
				if (s instanceof CompositeString) {
					((CompositeString) s).setParent(this);
				}
			}
		}

		public void setParent(CompositeString s) {
			this.parent = s;
		}

		@Override
		public String stringValue() {
			return decode(parts);
		}

		@Override
		public void remove() {
			for (StringElement e : parts)
				e.remove();
		}

		public void childUpdated(CompositeString dynamicString) {

		}

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

	private class DynamicString extends CompositeString
			implements
				MHFCDelayedJob {

		private volatile String stringValue;
		private volatile int delay;

		public DynamicString(String str) {
			super(str);

		}

		@Override
		public void executeJob() {
			String superValue = super.stringValue();
			String[] split = superValue.split(":", 2);
			if (split.length == 1) {
				this.stringValue = split[0];
				delay = -1;
			} else {
				stringValue = findReplacement(super.stringValue());
				delay = MHFCRegStringDecode.getDecoder(split[0])
						.getUpdateDelay();
			}
			MHFCJobHandler.getJobHandler().insert(this, getInitialDelay());
			if (parent != null)
				parent.childUpdated(this);
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

		@Override
		public void childUpdated(CompositeString dynamicString) {
			MHFCJobHandler.getJobHandler().remove(this);
			executeJob();
		}

	}

	protected String shortStatus;
	protected String longStatus;

	protected CompositeString nameElements;
	protected CompositeString descriptionElements;
	protected CompositeString clientElements;
	protected CompositeString aimsElements;
	protected CompositeString failsElements;

	protected CompositeString areaNameIdElements;
	protected CompositeString timeLimitInSElements;

	protected CompositeString rewardElements;
	protected CompositeString feeElements;
	protected CompositeString maxPartySizeElements;

	protected CompositeString shortStatusElements;
	protected CompositeString longStatusElements;

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
		// "foo{{st}other}bar{irr}" turns to foo {st}other bar irr
		// Algorithm?
		for (String part : firstSplit) {
			for (String small : part.split("\\}")) {
				secondSplit.add(small);
			}
		}
		for (String part : secondSplit) {
			if (part == null)
				part = "";
			if (dynamic) {
				list.add(new CompositeString(part));
			} else {
				list.add(new StaticString(part));
			}
			dynamic = !dynamic;
		}
		return list;
	}

	private String decode(List<StringElement> elements) {
		if (elements == null) {
			return "NULL";
		}
		String output = "";
		for (int i = 0; i < elements.size(); i++) {
			StringElement e = elements.get(i);
			if (e != null) {
				output += e.stringValue();
			}
		}
		return output;
	}

	private String findReplacement(String descriptor) {
		String split[] = descriptor.split(":", 2);
		String identifier = split[0];
		if (split.length == 1)
			return identifier;
		String replacement = MHFCRegStringDecode.getDecoder(identifier)
				.getDecoded(identifier, split[1]);
		return replacement == null
				? "unknown Descriptor " + descriptor
				: replacement;
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
		nameElements = new CompositeString(name);
		descriptionElements = new CompositeString(description);
		clientElements = new CompositeString(client);
		aimsElements = new CompositeString(aims);
		failsElements = new CompositeString(fails);
		areaNameIdElements = new CompositeString(areaNameId);
		timeLimitInSElements = new CompositeString(timeLimitInS);
		rewardElements = new CompositeString(reward);
		feeElements = new CompositeString(fee);
		maxPartySizeElements = new CompositeString(maxPartySize);
		shortStatusElements = new CompositeString(shortStatus);
		longStatusElements = new CompositeString(longStatus);
	}

	public String getShortStatus() {
		return shortStatusElements.stringValue();
	}

	public String getTrueShortStatus() {
		return shortStatus;
	}

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
	public String getAreaID() {
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
		nameElements.remove();
		descriptionElements.remove();
		clientElements.remove();
		aimsElements.remove();
		failsElements.remove();
		areaNameIdElements.remove();
		timeLimitInSElements.remove();
		rewardElements.remove();
		feeElements.remove();
		maxPartySizeElements.remove();
		shortStatusElements.remove();
		longStatusElements.remove();
	}
}
