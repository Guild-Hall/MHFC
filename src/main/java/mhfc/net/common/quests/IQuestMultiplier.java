package mhfc.net.common.quests;

public interface IQuestMultiplier {

	/**
	 * I'd hope if you may want to a look at this feature @WorldSEnder, since this will be a torso for our future mod
	 * features, the reason i'd create this is to have more complexity in terms of quest difficulty in relation to
	 * monsters difficulty, thus this will be a big help for players having a great reminder that our mod is coping at
	 * with the same path as the Monster Hunter series. ~Heltrato
	 **/

	/** The basing of all difficulty, most likely related to the Hunter's Rank (soon to be featured) **/
	public int starDifficulty();

	/** The healthMultiplier in which can should be edited with JSON format. **/
	public float healthMultiplier();

	/** The damageMultiplier is a amplify damage for most AIs. **/
	public float damageMultiplier();

	/** The unlockKey() is a int feature in which the monster can use the AIs through ints fulffiled. **/
	public int unlockKey();

	/** The dropChanceMultiplier() this increase the drop frequency for most loots. **/
	public float dropChanceMultiplier();

	/**
	 * The rarityAllowance()is in which used if the quest allows up to want integer is to drop the rarity of the item
	 * per quest.
	 */
	public int rarityAllowance();

}
