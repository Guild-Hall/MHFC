package mhfc.net.common.system;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;

public class DonatorSystem {
	public static final Privilege dragoon = new Privilege() {
		@Override
		public void load() {}

		@Override
		public boolean hasPrivilege(EntityPlayer player) {
			return player != null && playerInList(player.getDisplayName(), dragoondonor);
		}
	};
	public static final Privilege kirinS = new Privilege() {
		@Override
		public void load() {}

		@Override
		public boolean hasPrivilege(EntityPlayer player) {
			return player != null && playerInList(player.getDisplayName(), kirinSdonor);
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
	private static String[] kirinSdonor = { "requillias", "PCAwesomeness", "Schmidmix", "Heltrato" };
	private static String[] Stbionicdonor = { "TheDemoPikachu", "PCAwesomeness", "Heltrato" };

	private static boolean playerInList(ITextComponent iTextComponent, String[] array) {
		for (String element : array) {
			if (element.equals(iTextComponent)) {
				return true;
			}
		}
		return false;
	}

	public static boolean checkKirinS(EntityPlayer player) {
		if (player == null) {
			return false;
		}
		return kirinS.hasPrivilege(player);
	}
}
