package mhfc.net.client.render.weapon.bow;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.bow.ModelBPower;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.range.bow.BowClass;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderBPower implements IItemRenderer {
	
	public ModelBPower weapon;

	public RenderBPower() {
		weapon = new ModelBPower();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		int usingItem = 72000 - ((BowClass) item.getItem()).usingItem;
		switch (type) {
		case EQUIPPED:
			GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_power_tex));
			if (((Entity) data[1] instanceof EntityPlayer)&& (((EntityPlayer) data[1]).getCurrentEquippedItem() != null)) {
				if ((usingItem >5)  && (usingItem <=18)){
					weapon.restBow(false);
					weapon.pullSlow(true);
				}else if (usingItem >= 25) 
					weapon.pullHard(true);
				}else {
					weapon.renderBase();
					weapon.restBow(true);
					weapon.pullSlow(false);
					weapon.pullHard(false);
			}
			GL11.glRotatef(90F, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(150F, 1.0f, 0.0f, 0.0f);
			GL11.glTranslatef(0F, 0.65F, -0.7F);
			GL11.glRotatef(180, 0, 0, 01);
			weapon.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,0.0625F);
			GL11.glPopMatrix();
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_power_tex));
			if (((EntityPlayer) data[1]).getCurrentEquippedItem() != null) {
				if ((usingItem >5)  && (usingItem <=18)){
					weapon.restBow(false);
					weapon.pullSlow(true);
				}else if (usingItem >= 25) 
					weapon.pullHard(true);
				}else {
					weapon.renderBase();
					weapon.restBow(true);
					weapon.pullSlow(false);
					weapon.pullHard(false);
			}
			GL11.glRotatef(90F, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(140F, 1.0f, 0.0f, 0.0f);
			GL11.glTranslatef(0F, -0.6F, -0.8F);
			weapon.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,0.0625F);
			GL11.glPopMatrix();

			break;
		case ENTITY:
			GL11.glPushMatrix();
			float scale = 1.5F;
			GL11.glScalef(scale, scale, scale);
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_power_tex));
			GL11.glRotatef(0f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(90F, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(0F, -0.4F, 0F);
			weapon.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,0.0625F);
			GL11.glPopMatrix();
			break;
		case INVENTORY:
			GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.weapon_bow_power_tex));
			scale = 1F;
			GL11.glScalef(scale, scale, scale);
			GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
			GL11.glTranslatef(0.0F, -0.4F, 0F);
			weapon.render(0.0625F);
			GL11.glPopMatrix();
			break;

		default:
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
