package mhfc.net.common.system;

import net.minecraft.entity.player.EntityPlayer;

public class DonatorSystem {
	private static final Privilege dragoon = new Privilege() {
		@Override
		public void load() {}

		@Override
		public boolean hasPrivilege(EntityPlayer player) {
			return playerInList(player.getDisplayName(), dragoondonor);
		}
	};
	private static final Privilege kirinS = new Privilege() {
		@Override
		public void load() {}

		@Override
		public boolean hasPrivilege(EntityPlayer player) {
			return playerInList(player.getDisplayName(), kirinSdonor);
		}
	};
	private static final String[] dragoondonor = {"Danmar"};
	private static String[] kirinSdonor = {"requillias", "PCAwesomeness",
			"Schmidmix", "Heltrato"};

	private static boolean playerInList(String name, String[] array) {
		for (int i = 0; i < array.length; i++)
			if (array[i].equals(name))
				return true;
		return false;
	}

	public static boolean checkDragoon(EntityPlayer player) {
		if (player == null)
			return false;
		return dragoon.hasPrivilege(player);
	}

	public static boolean checkKirinS(EntityPlayer player) {
		if (player == null)
			return false;
		return kirinS.hasPrivilege(player);
	}
}
