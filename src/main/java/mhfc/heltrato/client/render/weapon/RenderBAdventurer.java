package mhfc.heltrato.client.render.weapon;

import mhfc.heltrato.client.model.weapon.ModelBAdventurer;
import mhfc.heltrato.client.render.RenderWeapon;
import mhfc.heltrato.common.core.registry.MHFCRegItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class RenderBAdventurer extends RenderWeapon {

	public RenderBAdventurer(ModelBase model, String texture) {
		super(model, texture);
	}

	@Override
	public void renderEquippedFP() {
		GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f); 
		GL11.glRotatef(-90F, 0.0f, 1.0f, 0.0f); 
		GL11.glRotatef(-180, 0.0f, 0.0f, 1.0f); 
		GL11.glTranslatef(-0.0F, -0.1F, -0.3F);
		GL11.glScalef(2, 2, 2);
	}

	@Override
	public void renderEntity() {
		GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(45F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-0.2F, -0.6F, 0F);
		GL11.glScalef(3, 3, 3);
	}

	@Override
	public void renderEquipped() {
		GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f); 
		GL11.glRotatef(-90F, 0.0f, 1.0f, 0.0f); 
		GL11.glRotatef(-180, 0.0f, 0.0f, 1.0f); 
		GL11.glTranslatef(-0.0F, -0.1F, -0.3F);
		GL11.glScalef(2, 2, 2);
	}

	@Override
	public void renderScale() {
		float f = 1f;
		GL11.glScalef(f, f, f);	
		
	}

	public void renderInventory() {
		GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-0.2F, 0.1F, -0.0F);
		GL11.glScalef(1.3F, 1.3F, 1.3F);
		
	}
	
	@Override
	public void preSpecials(ItemStack item, ModelBase model, Object... data) {
		super.preSpecials(item, model, data);
		if ((Entity) data[1] instanceof EntityPlayer && ((EntityPlayer)data[1]).getCurrentEquippedItem() != null) {

			if(item.getItem() != null && item.getItem().equals(MHFCRegItem.mhfcitembadventurer)){

				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				int count =player.getItemInUseCount();
				int passed = 72000 - count;
				((ModelBAdventurer)model).renderBase();
				((ModelBAdventurer)model).string.isHidden = false;
				((ModelBAdventurer)model).pullSlow(false);
				((ModelBAdventurer)model).pullHard(false);
				if(passed == 72000){
				}
				if(passed >0 && passed <=5){
					((ModelBAdventurer)model).string.isHidden = true;
					//				((ModelBAdventurer)model).pullSlow(true);
					if(!(player == Minecraft.getMinecraft().renderViewEntity &&
							Minecraft.getMinecraft().gameSettings.thirdPersonView == 0)){

					}else{
						GL11.glTranslatef(0.1f,-0.1f,0.1f);
						GL11.glRotatef(5, 0.0f, 0.0f, 1.0f);
						GL11.glRotatef(-10, 0.0f, 1.0f, 0.0f);
						GL11.glRotatef(-20, 1.0f, 0.0f, 0.0f);
					}

				}
				if(passed >5 && passed <=18){
					((ModelBAdventurer)model).pullSlow(true);
					if(!(player == Minecraft.getMinecraft().renderViewEntity &&
							Minecraft.getMinecraft().gameSettings.thirdPersonView == 0)){

					}else{
						GL11.glTranslatef(0.1f,-0.1f,0.1f);
						GL11.glRotatef(5, 0.0f, 0.0f, 1.0f);
						GL11.glRotatef(-10, 0.0f, 1.0f, 0.0f);
						GL11.glRotatef(-20, 1.0f, 0.0f, 0.0f);
					}
				}
				if(passed >18 && passed <72000){
					((ModelBAdventurer)model).pullHard(true);
					if(!(player == Minecraft.getMinecraft().renderViewEntity &&
							Minecraft.getMinecraft().gameSettings.thirdPersonView == 0)){

					}else{
						GL11.glTranslatef(0.1f,-0.1f,0.1f);
						GL11.glRotatef(5, 0.0f, 0.0f, 1.0f);
						GL11.glRotatef(-10, 0.0f, 1.0f, 0.0f);
						GL11.glRotatef(-20, 1.0f, 0.0f, 0.0f);
					}//pabie
				}
			}else{
				((ModelBAdventurer)model).renderBase();
				((ModelBAdventurer)model).pullSlow(false);
				((ModelBAdventurer)model).pullHard(false);
			}
		}
		else{
			((ModelBAdventurer)model).renderBase();
			((ModelBAdventurer)model).string.isHidden = false;
			((ModelBAdventurer)model).pullSlow(false);
			((ModelBAdventurer)model).pullHard(false);
			GL11.glTranslatef(0.1f, -0.1f, 0);
			GL11.glScalef(0.7f, 0.7f, 0.7f);
		}
		}

}
