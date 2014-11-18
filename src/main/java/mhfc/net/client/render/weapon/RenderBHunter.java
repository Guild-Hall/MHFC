package mhfc.net.client.render.weapon;

import mhfc.net.client.model.weapon.ModelBHunter;
import mhfc.net.client.render.RenderWeapon;
import mhfc.net.common.item.weapon.WeaponBHunter;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderBHunter extends RenderWeapon {

	private ModelBHunter weapon;
	// private RenderWeapon getRender;
	private float scale;

	public RenderBHunter() {
		weapon = new ModelBHunter();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		int usingItem = ((WeaponBHunter) item.getItem()).usingItem;
		switch (type) {

			case EQUIPPED : // 3rd person
				GL11.glPushMatrix();
				if ((((Entity) data[1] instanceof EntityPlayer))
						&& (((EntityPlayer) data[1]).getCurrentEquippedItem() != null)) {
					if (usingItem < 5) {
						Minecraft.getMinecraft().renderEngine
								.bindTexture(new ResourceLocation(
										MHFCReference.weapon_bow_hunter_tex0));
					}
					if ((usingItem > -5) && (usingItem < 35)) {
						Minecraft.getMinecraft().renderEngine
								.bindTexture(new ResourceLocation(
										MHFCReference.weapon_bow_hunter_tex1));
					}
					if ((usingItem >= 35)) {
						Minecraft.getMinecraft().renderEngine
								.bindTexture(new ResourceLocation(
										MHFCReference.weapon_bow_hunter_tex2));
					} else {
						Minecraft.getMinecraft().renderEngine
								.bindTexture(new ResourceLocation(
										MHFCReference.weapon_bow_hunter_tex0));
					}
					scale = 1.5f;
					GL11.glScalef(scale, scale, scale);
					GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f); // rotate 0 ° on X
															// axis
					GL11.glRotatef(-90F, 0.0f, 1.0f, 0.0f); // rotate -5 ° on Y
															// axis
					GL11.glRotatef(-180, 0.0f, 0.0f, 1.0f); // rotate -150 ° on
															// Z axis
					GL11.glTranslatef(-0.0F, -0.1F, -0.3F); // translate model
															// to fit in the
															// hand of the
															// player
					weapon.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F,
							0.0F, 0.0625F);
					GL11.glPopMatrix();
				}

				break;

			case EQUIPPED_FIRST_PERSON :
				GL11.glPushMatrix();
				if ((((Entity) data[1] instanceof EntityPlayer))
						&& (((EntityPlayer) data[1]).getCurrentEquippedItem() != null)) {
					if (usingItem < 5) {
						Minecraft.getMinecraft().renderEngine
								.bindTexture(new ResourceLocation(
										MHFCReference.weapon_bow_hunter_tex0));
					} else if ((usingItem >= 5) && (usingItem < 35)) {
						Minecraft.getMinecraft().renderEngine
								.bindTexture(new ResourceLocation(
										MHFCReference.weapon_bow_hunter_tex1));
					} else if (usingItem >= 35)
						Minecraft.getMinecraft().renderEngine
								.bindTexture(new ResourceLocation(
										MHFCReference.weapon_bow_hunter_tex2));
				} else {
					Minecraft.getMinecraft().renderEngine
							.bindTexture(new ResourceLocation(
									MHFCReference.weapon_bow_hunter_tex0));
				}
				scale = 1.4f;
				GL11.glScalef(scale, scale, scale);
				GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(-5F, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(-150F, 0.0f, 0.0f, 1.0f);
				GL11.glTranslatef(-0.4F, -0.2F, -0.1F);
				weapon.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
						0.0625F);
				GL11.glPopMatrix();
				break;

			case ENTITY :
				GL11.glPushMatrix();
				scale = 1F;
				GL11.glScalef(scale, scale, scale);
				Minecraft.getMinecraft().renderEngine
						.bindTexture(new ResourceLocation(
								MHFCReference.weapon_bow_hunter_tex0));

				GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(45F, 0.0f, 0.0f, 1.0f);
				GL11.glTranslatef(-0.2F, -0.6F, 0F);
				weapon.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
						0.0625F);
				GL11.glPopMatrix();
				break;

			case INVENTORY :
				GL11.glPushMatrix();
				scale = 1.3F;
				GL11.glScalef(scale, scale, scale);
				Minecraft.getMinecraft().renderEngine
						.bindTexture(new ResourceLocation(
								MHFCReference.weapon_bow_hunter_tex0));

				GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
				GL11.glTranslatef(-0.2F, 0.1F, -0.0F);
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

}
