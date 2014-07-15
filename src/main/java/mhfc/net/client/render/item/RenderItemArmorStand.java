package mhfc.net.client.render.item;

import mhfc.net.client.model.block.ModelArmorStand;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderItemArmorStand implements IItemRenderer {

	private ModelArmorStand model;

	public RenderItemArmorStand() {
		model = new ModelArmorStand();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case ENTITY :
				renderThisItem(0.3F, 0.5F, 0.0F, 180F, 0F, 0F, 1F);
				break;
			case EQUIPPED :
				renderThisItem(0.6F, 1.4F, 0.8F, 180F, 0F, 0F, 1F);
				break;
			case EQUIPPED_FIRST_PERSON :
				renderThisItem(0.6F, 1.6F, 0.8F, 180F, 0F, 0F, 1F);
				break;
			case INVENTORY :
				renderThisItem(0.9F, 1.2F, 1.0F, 180F, 0F, 0F, 1F);
				break;
			default :
				break;
		}

	}

	private void renderThisItem(float x, float y, float z, float rotateX,
			float rotateY, float rotateZ, float angle) {
		float scale = 0.04125F;
		// Tessellator tess = Tessellator.instance;
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine
				.bindTexture(new ResourceLocation(MHFCReference.tex_tile_armorstand));
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(rotateX, rotateY, rotateZ, angle);
		model.renderModel(scale);
		GL11.glPopMatrix();

	}

}
