package mhfc.net.client.gui.hud;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.client.util.gui.MHFCGuiUtil;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.melee.huntinghorn.HuntingHornWeaponStats;
import mhfc.net.common.weapon.melee.huntinghorn.ItemHuntingHorn;
import mhfc.net.common.weapon.melee.huntinghorn.Note;
import mhfc.net.common.weapon.melee.longsword.ItemLongsword;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class WeaponOverlay {

	private static final ResourceLocation staveLoc = new ResourceLocation(MHFCReference.gui_huntinghorn_stave);
	private static final ResourceLocation noteLoc = new ResourceLocation(MHFCReference.gui_huntinghorn_note);

	private static final ResourceLocation spiritGaugeLoc = new ResourceLocation(MHFCReference.gui_longsword_gauge);

	public static void render() {
		EntityClientPlayerMP thePlayer = Minecraft.getMinecraft().thePlayer;
		ItemStack stack = thePlayer.getHeldItem();
		if (stack == null) {
			return;
		}
		Item item = stack.getItem();
		if (item instanceof ItemHuntingHorn) {
			renderHuntingHornOverlay(thePlayer, stack);
		}
		if (item instanceof ItemLongsword) {
			renderLongswordOverlay(thePlayer, stack);
		}
	}

	private static void renderHuntingHornOverlay(EntityClientPlayerMP thePlayer, ItemStack stack) {
		ItemHuntingHorn huntingHorn = ItemHuntingHorn.class.cast(stack.getItem());
		HuntingHornWeaponStats stats = huntingHorn.getWeaponStats();

		List<Note> noteHistory = stats.getNoteHistory(stack);

		float resizeX = 1f, resizeY = 1f, offsetX = 4, offsetY = 4;

		glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(staveLoc);

		MHFCGuiUtil
				.drawTexturedRectangle(offsetX * resizeX, offsetY * resizeY, 128 * resizeX, 19 * resizeY, 0, 0, 1, 1);

		Minecraft.getMinecraft().renderEngine.bindTexture(noteLoc);
		for (int i = 0; i < noteHistory.size(); i++) {
			float posX = offsetX + 32 + 19 * i + (i / 2) * 9;
			float posY = offsetY + (i % 2 == 0 ? -4.5f : 5.5f);
			Note note = noteHistory.get(i);
			MHFCGuiUtil.setColor(note.colorRGB);
			MHFCGuiUtil.drawTexturedRectangle(posX * resizeX, posY * resizeY, 14 * resizeX, 14 * resizeY, 0, 0, .5f, 1);
		}

		glPopMatrix();
	}

	private static void renderLongswordOverlay(EntityClientPlayerMP thePlayer, ItemStack stack) {
		ItemLongsword item = ItemLongsword.class.cast(stack.getItem());
		float spirit = item.getSpiritPercentage(stack);

		float resizeX = 1.5f, resizeY = 1.5f, offsetX = 4, offsetY = 4, fullLength = 57;
		float gaugeOffsetX = offsetX + 2, gaugeOffsetY = offsetY + 2, gaugeLength = (fullLength - 3) * spirit;

		offsetX *= resizeX;
		offsetY *= resizeY;
		gaugeOffsetX *= resizeX;
		gaugeOffsetY *= resizeY;

		Minecraft.getMinecraft().renderEngine.bindTexture(spiritGaugeLoc);
		MHFCGuiUtil.drawTexturedRectangle(offsetX, offsetY, fullLength * resizeX, 6 * resizeY, 0, 0, 1, 6 / 20f);
		glDisable(GL_TEXTURE_2D);
		glColor3f(spirit - 1.f < -1e-9 ? 0.7f : 1f, 0.1f, 0.1f);
		MHFCGuiUtil.drawTexturedRectangle(gaugeOffsetX, gaugeOffsetY, gaugeLength * resizeX, 2 * resizeY, 0, 0, 1, 1);
		glColor3f(1.f, 1.f, 1.f);
		glEnable(GL_TEXTURE_2D);
	}
}
