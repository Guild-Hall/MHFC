package mhfc.net.common.weapon.melee.huntinghorn;

import com.google.common.base.Preconditions;
import mhfc.net.common.util.Trie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HHSongRegistry {
	public static final int SONG_LENGTH_CAP = 4;


	private static final ISong attackupSMALL = new ISong() {
		@Override
		public void onPlayed(EntityPlayer player, ItemStack stack, HuntingHornWeaponStats itemStats) {
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 40));
		}

		@Override
		public String getUnlocalizedName() {
			return "songs.attackupSMALL";
		}
	};

	private static final ISong attackupMED = new ISong() {
		@Override
		public void onPlayed(EntityPlayer player, ItemStack stack, HuntingHornWeaponStats itemStats) {
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 60, 2));
		}

		@Override
		public String getUnlocalizedName() {
			return "songs.attackupMED";
		}
	};

	private static final ISong attackupBIG = new ISong() {
		@Override
		public void onPlayed(EntityPlayer player, ItemStack stack, HuntingHornWeaponStats itemStats) {
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 120, 3));
		}

		@Override
		public String getUnlocalizedName() {
			return "songs.attackupBIG";
		}
	};

	private static final ISong healSMALL = new ISong() {
		@Override
		public void onPlayed(EntityPlayer player, ItemStack stack, HuntingHornWeaponStats itemStats) {
			player.heal(6);

		}

		@Override
		public String getUnlocalizedName() {
			return "songs.healSMALL";
		}
	};
	private static final ISong healMED = new ISong() {
		@Override
		public void onPlayed(EntityPlayer player, ItemStack stack, HuntingHornWeaponStats itemStats) {
			player.heal(12);

		}

		@Override
		public String getUnlocalizedName() {
			return "songs.healMED";
		}
	};
	private static final ISong healBIG = new ISong() {
		@Override
		public void onPlayed(EntityPlayer player, ItemStack stack, HuntingHornWeaponStats itemStats) {
			player.heal(18);

		}

		@Override
		public String getUnlocalizedName() {
			return "songs.healBIG";
		}
	};
	private static final ISong fullhealREGEN = new ISong() {
		@Override
		public void onPlayed(EntityPlayer player, ItemStack stack, HuntingHornWeaponStats itemStats) {
			player.heal(20);
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 120, 1));

		}

		@Override
		public String getUnlocalizedName() {
			return "songs.fullhealREGEN";
		}
	};

	private static final ISong movespeedSMALL = new ISong() {
		@Override
		public void onPlayed(EntityPlayer player, ItemStack stack, HuntingHornWeaponStats itemStats) {
			player.heal(20);
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60, 2));

		}

		@Override
		public String getUnlocalizedName() {
			return "songs.movespeedSMALL";
		}
	};

	private static final ISong movespeedBIG = new ISong() {
		@Override
		public void onPlayed(EntityPlayer player, ItemStack stack, HuntingHornWeaponStats itemStats) {
			player.heal(20);
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 150, 2));

		}

		@Override
		public String getUnlocalizedName() {
			return "songs.movespeedBIG";
		}
	};

	private static final ISong targetDAMAGE = new ISong() {
		@Override
		public void onPlayed(EntityPlayer player, ItemStack stack, HuntingHornWeaponStats itemStats) {

		}

		@Override
		public String getUnlocalizedName() {
			return "songs.movespeedBIG";
		}
	};



	private static List<Note> compose(Note... notes) {
		return Arrays.asList(notes);
	}

	private static Trie<Note, ISong> songMap = new Trie<>(Note.class);

	static {
		registerSong(compose(Note.Black, Note.Orange), attackupBIG);
		registerSong(compose(Note.Red, Note.Red, Note.Blue, Note.Purple), attackupMED);
		registerSong(compose(Note.Green, Note.Red, Note.White), attackupSMALL);
		registerSong(compose(Note.Black, Note.Black, Note.Orange, Note.Cyan), fullhealREGEN);
		registerSong(compose(Note.Blue, Note.Blue, Note.Red), healBIG);
		registerSong(compose(Note.Red, Note.Blue, Note.White), healMED);
		registerSong(compose(Note.White, Note.Purple, Note.Red), healSMALL);
		registerSong(compose(Note.Blue, Note.Blue, Note.Yellow), movespeedBIG);
		registerSong(compose(Note.Purple, Note.Red, Note.White), movespeedSMALL);
		registerSong(compose(Note.Cyan, Note.Cyan), targetDAMAGE);

	}

	public static void registerSong(List<Note> notes, ISong song) {
		Preconditions.checkArgument(notes.size() <= SONG_LENGTH_CAP, "song too long");
		songMap.insert(notes, song);
	}

	public static Optional<ISong> getSong(List<Note> noteHistory) {
		return songMap.findFirstPrefix(noteHistory);
	}
}
