package mhfc.heltrato.client.render.item;

import mhfc.heltrato.client.model.block.ModelArmorStandBase;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderItemArmorStandBase implements IItemRenderer {

	private ModelArmorStandBase model;

	public RenderItemArmorStandBase() {
		model = new ModelArmorStandBase();
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
				renderThisItem(0.9F, 1.4F, 1.0F, 180F, 0F, 0F, 1F);
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
				.bindTexture(new ResourceLocation(MHFCReference.tile_armorstandbase_tex));
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(rotateX, rotateY, rotateZ, angle);
		model.renderModel(scale);
		GL11.glPopMatrix();

	}

}
