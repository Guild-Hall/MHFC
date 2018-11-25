package mhfc.net.client.gui.hud;

import mhfc.net.client.util.gui.MHFCGuiUtil;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.weapon.melee.huntinghorn.HuntingHornWeaponStats;
import mhfc.net.common.weapon.melee.huntinghorn.ItemHuntingHorn;
import mhfc.net.common.weapon.melee.huntinghorn.Note;
import mhfc.net.common.weapon.melee.longsword.ItemLongsword;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class WeaponOverlay {

	private static final ResourceLocation staveLoc = new ResourceLocation(ResourceInterface.gui_huntinghorn_stave);
	private static final ResourceLocation noteLoc = new ResourceLocation(ResourceInterface.gui_huntinghorn_note);

	private static final ResourceLocation spiritGaugeLoc = new ResourceLocation(ResourceInterface.gui_longsword_gauge);

	public static void render() {
		EntityPlayerSP thePlayer = Minecraft.getMinecraft().player;
		ItemStack stack = thePlayer.getHeldItemMainhand();
		if (stack == null) {
			return;
		}
		Item item = stack.getItem();
		if (item instanceof ItemHuntingHorn) {
			renderHuntingHornOverlay(stack);
		}
		if (item instanceof ItemLongsword) {
			renderLongswordOverlay(stack);
		}
	}

	private static void renderHuntingHornOverlay(ItemStack stack) {
		ItemHuntingHorn huntingHorn = ItemHuntingHorn.class.cast(stack.getItem());
		HuntingHornWeaponStats stats = huntingHorn.getWeaponStats();

		List<Note> noteHistory = stats.getNoteHistory(stack);

		float resizeX = 1f, resizeY = 1f, offsetX = 4, offsetY = 4;

		GlStateManager.pushMatrix();
		MHFCGuiUtil.setColor(0xFFFFFF, 1F);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
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

		GlStateManager.color(1.f, 1.f, 1.f);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	private static void renderLongswordOverlay(ItemStack stack) {
		ItemLongsword item = ItemLongsword.class.cast(stack.getItem());
		float spirit = item.getSpiritPercentage(stack);

		float resizeX = 1.5f, resizeY = 1.5f, offsetX = 4, offsetY = 4, fullLength = 57;
		float gaugeOffsetX = offsetX + 2, gaugeOffsetY = offsetY + 2, gaugeLength = (fullLength - 3) * spirit;

		offsetX *= resizeX;
		offsetY *= resizeY;
		gaugeOffsetX *= resizeX;
		gaugeOffsetY *= resizeY;

		Minecraft.getMinecraft().renderEngine.bindTexture(spiritGaugeLoc);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		MHFCGuiUtil.drawTexturedRectangle(offsetX, offsetY, fullLength * resizeX, 6 * resizeY, 0, 0, 1, 6 / 20f);
		GlStateManager.disableBlend();

		GlStateManager.disableTexture2D();
		GlStateManager.color(spirit - 1.f < -1e-9 ? 0.7f : 1f, 0.1f, 0.1f);
		MHFCGuiUtil.drawTexturedRectangle(gaugeOffsetX, gaugeOffsetY, gaugeLength * resizeX, 2 * resizeY, 0, 0, 1, 1);
		GlStateManager.color(1.f, 1.f, 1.f);
		GlStateManager.enableTexture2D();
	}
}
