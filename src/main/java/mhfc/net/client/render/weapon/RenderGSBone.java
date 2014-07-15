package mhfc.net.client.render.weapon;

import mhfc.net.client.model.weapon.ModelGSBone;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderGSBone implements IItemRenderer {

	private ModelGSBone weapon;

	public RenderGSBone() {
		weapon = new ModelGSBone();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {

			case EQUIPPED : // render in third person
				GL11.glPushMatrix(); // start gl rendering for this section
				float scale = 1.5f;
				GL11.glScalef(scale, scale, scale);
				Minecraft.getMinecraft().renderEngine
						.bindTexture(new ResourceLocation(MHFCReference.tex_weapons_gs_bone));
				GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f); // rotate 0 � on X axis
				GL11.glRotatef(-5F, 0.0f, 1.0f, 0.0f); // rotate -5 � on Y axis
				GL11.glRotatef(-120F, 0.0f, 0.0f, 1.0f); // rotate -150 � on Z
															// axis
				GL11.glTranslatef(-0.4F, -0.5F, 0F); // translate model to fit
														// in the hand of the
														// player
				// the entity argument can/could be passed to as null.
				weapon.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
						0.0625F);
				GL11.glPopMatrix();
				break;

			case EQUIPPED_FIRST_PERSON :

				// rince and repeat the rendering. adjust axis' and translation
				// as needed
				GL11.glPushMatrix();
				scale = 1.5f;
				GL11.glScalef(scale, scale, scale);
				Minecraft.getMinecraft().renderEngine
						.bindTexture(new ResourceLocation(MHFCReference.tex_weapons_gs_bone));
				GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(-5F, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(-170F, 0.0f, 0.0f, 1.0f);
				GL11.glTranslatef(-0.8F, -0.8F, -0.1F);
				weapon.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
						0.0625F);
				GL11.glPopMatrix();
				break;

			case ENTITY :
				GL11.glPushMatrix();
				scale = 1.5F;
				GL11.glScalef(scale, scale, scale);
				Minecraft.getMinecraft().renderEngine
						.bindTexture(new ResourceLocation(MHFCReference.tex_weapons_gs_bone));
				GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(45F, 0.0f, 0.0f, 1.0f);
				GL11.glTranslatef(-0.2F, -0.5F, 0F);
				weapon.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
						0.0625F);
				GL11.glPopMatrix();
				break;

			case INVENTORY :
				GL11.glPushMatrix();
				scale = 1.4F;
				GL11.glScalef(scale, scale, scale);
				Minecraft.getMinecraft().renderEngine
						.bindTexture(new ResourceLocation(MHFCReference.tex_weapons_gs_bone));

				GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
				GL11.glTranslatef(0.1F, -0.4F, 0F);
				// this is a method made by me in my model class to render only
				// the modelparts, without an entity argument, because in your
				// inventory, //the entity is always null.
				weapon.render(0.0625F);
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
