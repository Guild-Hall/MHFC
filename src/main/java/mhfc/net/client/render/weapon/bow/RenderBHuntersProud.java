package mhfc.net.client.render.weapon.bow;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.bow.ModelBHuntersProud;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderBHuntersProud implements IItemRenderer {

	private ModelBHuntersProud weapon;

	public RenderBHuntersProud() {
		weapon = new ModelBHuntersProud();
	}

	private void setup(Object player) {
		if ((player instanceof EntityPlayer) && (((EntityPlayer) player).getCurrentEquippedItem() != null)) {
			int usingItem = ((EntityPlayer) player).getItemInUseDuration();
			if (usingItem >= 1) {
				weapon.setupStart();
			}
			if (usingItem >= 5) {
				weapon.setupHalf();
			}
			if (usingItem >= 25) {
				weapon.setupFull();
			}
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		float scale = 1.2f;
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_huntersproud_tex));
		weapon.setupRest();
		GL11.glPushMatrix();
		switch (type) {
		case EQUIPPED: // render in third person
			setup(data[1]);
			GL11.glRotatef(00F, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(-90F, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-180F, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(-0.1F, -0.2F, -0.4F);
			GL11.glScalef(scale, scale, scale);
			weapon.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;

		case EQUIPPED_FIRST_PERSON:
			// rince and repeat the rendering. adjust axis' and translation
			// as needed
			setup(data[1]);
			scale = 1.5f;
			GL11.glScalef(scale, scale, scale);
			GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(-5F, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-150F, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(-1.1F, -0.6F, 0.5F);
			weapon.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;

		case ENTITY:
			scale = 2.4F;
			GL11.glScalef(scale, scale, scale);
			GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(45F, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(0F, -0.6F, -0.1F);
			weapon.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;

		case INVENTORY:
			scale = 1.0F;
			GL11.glScalef(scale, scale, scale);
			GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
			GL11.glTranslatef(0.1F, -0.0F, -0.1F);
			weapon.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625F);
			break;

		default:
			break;
		}
		GL11.glPopMatrix();

	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch (type) {
		case INVENTORY:
			return true;
		default:
			break;
		}
		return false;
	}

}
