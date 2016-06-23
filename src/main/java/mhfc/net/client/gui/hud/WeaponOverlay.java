package mhfc.net.client.gui.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.common.weapon.melee.huntinghorn.HuntingHornWeaponStats;
import mhfc.net.common.weapon.melee.huntinghorn.ItemHuntingHorn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.ItemStack;

@SideOnly(Side.CLIENT)
public class WeaponOverlay {
	public static final WeaponOverlay instance = new WeaponOverlay();

	public void render() {
		EntityClientPlayerMP thePlayer = Minecraft.getMinecraft().thePlayer;
		ItemStack stack = thePlayer.getHeldItem();
		if (stack == null) {
			return;
		}
		if (stack.getItem() instanceof ItemHuntingHorn) {
			renderHuntingHornOverlay(thePlayer, stack);
		}
	}

	private void renderHuntingHornOverlay(EntityClientPlayerMP thePlayer, ItemStack stack) {
		ItemHuntingHorn huntingHorn = ItemHuntingHorn.class.cast(stack.getItem());
		HuntingHornWeaponStats stats = huntingHorn.getWeaponStats();

		String quickInfo = stats.getNoteHistory(stack).toString() + stats.getCurrentNote(stack).toString();
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(quickInfo, 0, 0, 0xFFFFFF);
	}

}
