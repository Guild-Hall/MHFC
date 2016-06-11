package mhfc.net.common.entity.monster;

/**
 * List used for the express purpose of simplifying loot creation.
 * @author Landon
 *
 */
public enum EnumEntityBigMonster {
	GREAT_JAGGI(0),
	BARROTH(1),
	TIGREX(2),
	DEVILJHO(3),
	NARGACUGA(4),
	RATHALOS(5),
	NONE(127);
	public final int ID;
	private EnumEntityBigMonster(int ID) {
		this.ID = ID;
	}

	public static EnumEntityBigMonster byID(int ID) {
		for(EnumEntityBigMonster mon : EnumEntityBigMonster.values()) {
			if(mon.ID == ID)
				return mon;
		}
		return NONE;
	}
}
