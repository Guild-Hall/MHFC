package mhfc.net.common.quests;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.common.eventhandler.MHFCDelayedJob;
import mhfc.net.common.eventhandler.MHFCJobHandler;

public class QuestRunningInformation extends QuestVisualInformation {

	private interface StringElement {
		public String stringValue();
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
	}

	private class DynamicString implements StringElement, MHFCDelayedJob {

		private String str;
		private String stringValue;
		private int delay;

		public DynamicString(String str) {
			this.str = str;
			switch (str.split(":")[0]) {
				case "time" :
					delay = 30;
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

	}

	protected String shortStatus;
	protected String longStatus;

	protected StringElement[] nameElements;
	protected StringElement[] descriptionElements;
	protected StringElement[] clientElements;
	protected StringElement[] aimsElements;
	protected StringElement[] failsElements;

	protected StringElement[] areaNameIdElements;
	protected StringElement[] timeLimitInSElements;

	protected StringElement[] rewardElements;
	protected StringElement[] feeElements;
	protected StringElement[] maxPartySizeElements;

	protected StringElement[] shortStatusElements;
	protected StringElement[] longStatusElements;

	public QuestRunningInformation(GeneralQuest quest) {
		super(quest.getOriginalDescription().getVisualInformation());
		// FIXME retrieve correct strings from the quest
		String localStatusShort = "", localStatusLong = "";
		this.shortStatus = localStatusShort;
		this.longStatus = localStatusLong;
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

	public QuestRunningInformation(QuestVisualInformation information,
			String shortStatus, String longStatus) {
		super(information);
		this.shortStatus = shortStatus;
		this.longStatus = longStatus;
	}

	private StringElement[] breakApart(String str) {
		List<StringElement> list = new ArrayList<StringElement>(20);
		boolean dynamic = false;
		int startIndex = 0;
		while (dynamic ? str.contains("}") : str.contains("{")) {
			if (dynamic) {
				int index = str.indexOf("}", startIndex);
				if (index < 0) {
					list.add(new DynamicString(str));
					break;
				}
				if (str.length() > index && str.charAt(index + 1) == '}') {
					str = str.replaceFirst("}}", "}");
					startIndex = index + 1;
					continue;
				}
				list.add(new DynamicString(str.substring(0, index).replaceAll(
						"{{", "{")));
				str = str.substring(index + 1);
				dynamic = !dynamic;
			} else {
				int index = str.indexOf("{", startIndex);
				if (index < 0) {
					list.add(new StaticString(str));
					break;
				}
				if (str.length() > index && str.charAt(index + 1) == '{') {
					str = str.replaceFirst("{{", "{");
					startIndex = index + 1;
					continue;
				}
				list.add(new StaticString(str.substring(0, index).replaceAll(
						"}}", "}")));
				str = str.substring(index + 1);
				dynamic = !dynamic;
			}
		}
		return list.toArray(new StringElement[0]);
	}

	private String decode(StringElement elements[]) {
		String output = "";
		for (StringElement e : elements) {
			output += e.stringValue();
		}
		return output;
	}

	private String findReplacement(String descriptor) {
		String split[] = descriptor.split(":", 2);
		String identifier = split[0];
		String replacement = "unknown Descriptor " + descriptor;
		switch (identifier) {
			case "time" :
				int subTime = Integer.parseInt(split[1]);
				long delta = subTime - System.currentTimeMillis();
				delta /= 1000;
				replacement = "" + (delta >= 3600 ? delta / 3600 + "h " : "")
						+ (delta >= 60 ? (delta % 3600) / 60 + "min " : "")
						+ (delta >= 0 ? delta % 60 : delta) + "s";
				break;
			case "unlocalized" :
				// FIXME Return some unlocalized string, maybe include server
				// support
				replacement = "!!" + descriptor + "!!";
				break;
		}
		return replacement;
	}

	public String getShortStatus() {
		return decode(shortStatusElements);
	}

	public String getLongStatus() {
		return decode(longStatusElements);
	}

	@Override
	public String getName() {
		return decode(nameElements);
	}

	@Override
	public String getDescription() {
		return decode(descriptionElements);
	}

	@Override
	public String getClient() {
		return decode(clientElements);
	}

	@Override
	public String getAims() {
		return decode(aimsElements);
	}

	@Override
	public String getFails() {
		return decode(failsElements);
	}

	@Override
	public String getTimeLimitAsString() {
		return decode(timeLimitInSElements);
	}

	@Override
	public String getRewardString() {
		return decode(rewardElements);
	}

	@Override
	public String getFeeString() {
		return decode(feeElements);
	}

	@Override
	public String getAreaID() {
		return decode(areaNameIdElements);
	}

	@Override
	public String getMaxPartySize() {
		return decode(maxPartySizeElements);
	}

}
