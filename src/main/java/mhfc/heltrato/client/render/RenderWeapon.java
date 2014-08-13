package mhfc.heltrato.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderWeapon implements IItemRenderer{
	
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		if (item.hasEffect(item.getItemDamage())) {
		     GL11.glDepthFunc(514);
		     GL11.glDisable(2896);
		     Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/misc/enchanted_item_glint.png"));
		     GL11.glEnable(3042);
		     GL11.glBlendFunc(768, 1);
		     float f7 = 0.76F;
		     GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
		     GL11.glMatrixMode(5890);
		     GL11.glPushMatrix();
		     float f8 = 0.125F;
		     GL11.glScalef(f8, f8, f8);
		     float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
		     GL11.glTranslatef(f9, 0.0F, 0.0F);
		     GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
		     GL11.glPopMatrix();
		     GL11.glPushMatrix();
		     GL11.glScalef(f8, f8, f8);
		     f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
		     GL11.glTranslatef(-f9, 0.0F, 0.0F);
		     GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
		     GL11.glPopMatrix();
		     GL11.glMatrixMode(5888);
		     GL11.glDisable(3042);
		     GL11.glEnable(2896);
		     GL11.glDepthFunc(515);
		}
		GL11.glPopMatrix();
	}

	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,ItemRendererHelper helper) {
		  switch (type) {
          case INVENTORY:
                  return true;
          default:
                  break;
          }
          return false;
	}

		
	}
