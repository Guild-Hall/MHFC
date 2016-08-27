package mhfc.net.client.quests;

import java.util.List;

import mhfc.net.client.quests.api.IMissionInformation;
import mhfc.net.common.quests.Mission;
import mhfc.net.common.util.MHFCStringDecode.CompositeString;
import mhfc.net.common.util.MHFCStringDecode.StringElement;

public class QuestRunningInformation implements IMissionInformation {
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

	public QuestRunningInformation(Mission quest) {
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

	public void updateFromQuest(Mission q) {
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
}
