package mhfc.net.common.util;

import java.util.HashSet;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerSet extends HashSet<UUID> {
	/**
	 *
	 */
	private static final long serialVersionUID = 2061439866575218352L;

	public PlayerSet() {
		super();
	}

	public String getPlayerName(EntityPlayer player) {
		return player.getDisplayNameString();
	}
}
