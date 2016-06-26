package mhfc.net.common.weapon.melee.huntinghorn;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Preconditions;

import mhfc.net.common.util.NBTUtils;
import mhfc.net.common.weapon.melee.MeleeWeaponStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HuntingHornWeaponStats extends MeleeWeaponStats {
	protected static final String NBT_NOTEHISTORY = "mhfc:lastNotes";
	protected static final String NBT_CURRENTNOTE = "mhfc:currentNote";
	private static Note[] allNotes = Note.values();

	public static class HuntingHornWeaponStatsBuilder extends MeleeWeaponStatsBuilder<HuntingHornWeaponStatsBuilder> {
		private Note[] notes;

		public HuntingHornWeaponStatsBuilder() {
			notes = new Note[0];
		}

		@Override
		protected HuntingHornWeaponStatsBuilder getThis() {
			return this;
		}

		public HuntingHornWeaponStatsBuilder setNotes(Note... notes) {
			this.notes = mhfc.net.common.util.Objects.requireNonNullDeep(notes);
			return this;
		}

		@Override
		public HuntingHornWeaponStats build() {
			return new HuntingHornWeaponStats(this);
		}
	}

	private Note[] notes;

	protected HuntingHornWeaponStats(HuntingHornWeaponStatsBuilder builder) {
		super(builder);
		Preconditions.checkArgument(builder.notes.length == 3, "Hunting horn must have 3 notes playable");
		assert builder.notes.length > 0 : "Must not be empty note array";
		this.notes = builder.notes;
	}

	private int getNoteIndex(ItemStack stack) {
		int index = NBTUtils.getNBTChecked(stack).getInteger(NBT_CURRENTNOTE);
		if (index < 0 || index >= notes.length) {
			index = 0;
		}
		return index;
	}

	// FIXME: Should use a input-based note-system, not a toggle-based one
	public Note getCurrentNote(ItemStack stack) {
		return notes[getNoteIndex(stack)];
	}

	public void toggleNote(ItemStack stack) {
		int current = getNoteIndex(stack);
		current = (current + 1) % notes.length;
		NBTUtils.getNBTChecked(stack).setInteger(NBT_CURRENTNOTE, current);
	}

	private void pushNote(ItemStack stack, Note note) {
		Objects.requireNonNull(note);
		int[] notes = NBTUtils.getNBTChecked(stack).getIntArray(NBT_NOTEHISTORY);
		if (notes.length >= HHSongRegistry.SONG_LENGTH_CAP) {
			System.arraycopy(notes, 0, notes, 1, HHSongRegistry.SONG_LENGTH_CAP - 1);
			notes[0] = note.ordinal();
		} else {
			int[] newNotes = new int[notes.length + 1];
			System.arraycopy(notes, 0, newNotes, 1, notes.length);
			notes = newNotes;
			notes[0] = note.ordinal();
			NBTUtils.getNBTChecked(stack).setIntArray(NBT_NOTEHISTORY, notes);
		}
	}

	private void playSong(ItemStack stack, EntityPlayer player, ISong song) {
		song.onPlayed(player, stack, this);
	}

	public void onNotePlayed(ItemStack stack, EntityPlayer player, Note note) {
		pushNote(stack, note);
		note.playSound(stack, player);
		List<Note> history = getNoteHistory(stack);
		HHSongRegistry.getSong(history).ifPresent(s -> this.playSong(stack, player, s));
	}

	public List<Note> getNoteHistory(ItemStack stack) {
		int[] notes = NBTUtils.getNBTChecked(stack).getIntArray(NBT_NOTEHISTORY);
		Note[] properNotes = new Note[notes.length];
		for (int i = 0; i < notes.length; i++) {
			properNotes[i] = allNotes[notes[i]];
		}
		return Arrays.asList(properNotes);
	}

	public void clearNoteHistory(ItemStack stack) {
		NBTUtils.getNBTChecked(stack).setIntArray(NBT_NOTEHISTORY, ArrayUtils.EMPTY_INT_ARRAY);
	}
}
