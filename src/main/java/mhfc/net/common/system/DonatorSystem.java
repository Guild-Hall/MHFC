package mhfc.net.common.system;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;

public class DonatorSystem {
	
	/**
	 * Will rework on this, there must be a raw site .txt to utilize
	 * */
	public static final Privilege dragoon = new Privilege() {
		@Override
		public void load() {}

		@Override
		public boolean hasPrivilege(EntityPlayer player) {
			return player != null && playerInList(player.getDisplayName(), dragoondonor);
		}
	};

	public static final Privilege bionic = new Privilege() {
		@Override
		public void load() {}

		@Override
		public boolean hasPrivilege(EntityPlayer player) {
			return player != null && playerInList(player.getDisplayName(), Stbionicdonor);
		}
	};
	// FIXME: replace with Player UUIDs
	private static final String[] dragoondonor = { "Danmar", "Heltrato" };
	private static String[] Stbionicdonor = { "TheDemoPikachu", "PCAwesomeness", "Heltrato" };

	private static boolean playerInList(ITextComponent iTextComponent, String[] array) {
		for (String element : array) {
			if (element.equals(iTextComponent)) {
				return true;
			}
		}
		return false;
	}

	
	public static boolean checkDragoon(EntityPlayer player) {
		if (player == null) {
			return false;
		}
		return dragoon.hasPrivilege(player);
	}
}
