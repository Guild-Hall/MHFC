package mhfc.net.client.gui.hud;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.melee.huntinghorn.HuntingHornWeaponStats;
import mhfc.net.common.weapon.melee.huntinghorn.ItemHuntingHorn;
import mhfc.net.common.weapon.melee.huntinghorn.Note;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class WeaponOverlay {
	public static final WeaponOverlay instance = new WeaponOverlay();

	private static final ResourceLocation staveLoc = new ResourceLocation(MHFCReference.gui_huntinghorn_stave);
	private static final ResourceLocation noteLoc = new ResourceLocation(MHFCReference.gui_huntinghorn_note);

	public void render() {
		EntityClientPlayerMP thePlayer = Minecraft.getMinecraft().thePlayer;
		ItemStack stack = thePlayer.getHeldItem();
		if (stack == null) {
			return;
		}
		Item item = stack.getItem();
		if (item instanceof ItemHuntingHorn) {
			renderHuntingHornOverlay(thePlayer, stack);
		}
	}

	private void renderHuntingHornOverlay(EntityClientPlayerMP thePlayer, ItemStack stack) {
		ItemHuntingHorn huntingHorn = ItemHuntingHorn.class.cast(stack.getItem());
		HuntingHornWeaponStats stats = huntingHorn.getWeaponStats();

		List<Note> noteHistory = stats.getNoteHistory(stack);

		String quickInfo = noteHistory.toString() + stats.getCurrentNote(stack).toString();
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(quickInfo, 0, 0, 0xFFFFFF);
	}

}
