package mhfc.net.client.render.weapon.bow;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.bow.ModelBHunters;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.range.bow.BHunters;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderBHunters implements IItemRenderer {

	private ModelBHunters weapon;

	public RenderBHunters() {
		weapon = new ModelBHunters();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		int usingItem = 72000 - ((BHunters) item.getItem()).usingItem;
		switch (type) {

			case EQUIPPED : // render in third person
				float scale = 1.2f;
				GL11.glPushMatrix(); 
				if (((Entity) data[1] instanceof EntityPlayer)&& (((EntityPlayer) data[1]).getCurrentEquippedItem() != null)) {
					if (usingItem < 5) {
						Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_hunters_tex_start));
					}else  if ((usingItem >= 5) && (usingItem < 25)) {
						Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_hunters_tex_half));
					}  if (usingItem >= 25) {
						Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_hunters_tex_full));
					}
				} else {
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_hunters_tex_rest));
				}
				GL11.glRotatef(00F, 1.0f, 0.0f, 0.0f); 
				GL11.glRotatef(-90F, 0.0f, 1.0f, 0.0f); 
				GL11.glRotatef(-180F, 0.0f, 0.0f, 1.0f); 
				GL11.glTranslatef(-0.1F, -0.2F, -0.4F);
				GL11.glScalef(scale, scale, scale);
				weapon.renderA(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,0.0625F);
				GL11.glPopMatrix();
				break;

			case EQUIPPED_FIRST_PERSON :

				// rince and repeat the rendering. adjust axis' and translation
				// as needed
				GL11.glPushMatrix();
				if (((Entity) data[1] instanceof EntityPlayer)&& (((EntityPlayer) data[1]).getCurrentEquippedItem() != null)) {

					if (usingItem < 5) {
						Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_hunters_tex_start));
					}  if ((usingItem >= 5) && (usingItem < 25)) {
						Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_hunters_tex_half));
					}  if (usingItem >= 25) {
						Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_hunters_tex_full));
					}
				} else {
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_hunters_tex_rest));
				}
				scale = 1.5f;
				GL11.glScalef(scale, scale, scale);
				GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(-5F, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(-150F, 0.0f, 0.0f, 1.0f);
				
				
							//In to face or out, Up/Down , Left or Right <-- positive/negative
				GL11.glTranslatef(-1.1F, -0.6F, 0.5F);
				weapon.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
						0.0625F);
				GL11.glPopMatrix();
				break;

			case ENTITY :
				GL11.glPushMatrix();
				scale = 2.4F;
				GL11.glScalef(scale, scale, scale);
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_hunters_tex_rest));
				GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(45F, 0.0f, 0.0f, 1.0f);
				GL11.glTranslatef(0F, -0.6F, -0.1F);
				weapon.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
						0.0625F);
				GL11.glPopMatrix();
				break;

			case INVENTORY :
				GL11.glPushMatrix();
				scale = 1.0F;
				GL11.glScalef(scale, scale, scale);
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_hunters_tex_rest));

				GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
				GL11.glTranslatef(0.1F, -0.0F, -0.1F);
				// this is a method made by me in my model class to render only
				// the modelparts, without an entity argument, because in your
				// inventory, //the entity is always null.
				weapon.renderRest(0.0625F);
				GL11.glPopMatrix();
				break;

			default :
				break;
		}
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		switch (type) {
			case INVENTORY :
				return true;
			default :
				break;
		}
		return false;

	}

}
