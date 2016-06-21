package mhfc.net.common.weapon.melee.huntinghorn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Preconditions;

import mhfc.net.common.util.Trie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class HHSongRegistry {
	public static final int SONG_LENGTH_CAP = 4;

	private static final ISong attackUpSmall = new ISong() {
		@Override
		public void onPlayed(EntityPlayer player, ItemStack stack, HuntingHornWeaponStats itemStats) {
			player.addPotionEffect(new PotionEffect(1, 10));
		}

		@Override
		public String getUnlocalizedName() {
			return "songs.attackUpSmall";
		}
	};

	private static List<Note> compose(Note... notes) {
		return Arrays.asList(notes);
	}

	private static Trie<Note, ISong> songMap = new Trie<>(Note.class);

	static {
		registerSong(compose(Note.Purple, Note.Purple), attackUpSmall);
	}

	public static void registerSong(List<Note> notes, ISong song) {
		Preconditions.checkArgument(notes.size() <= SONG_LENGTH_CAP, "song too long");
		songMap.insert(notes, song);
	}

	public static Optional<ISong> getSong(List<Note> noteHistory) {
		return songMap.findFirstPrefix(noteHistory);
	}
}
