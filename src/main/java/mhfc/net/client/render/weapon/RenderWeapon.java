package mhfc.net.client.render.weapon;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public abstract class RenderWeapon implements IItemRenderer{
	
	public ModelBase modelx;
	private ResourceLocation modelTexture;
	RenderItem rend = new RenderItem();
	
	public RenderWeapon(ModelBase model, String texture){
		modelx = model;
		modelTexture = new ResourceLocation(texture);
		
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		Minecraft.getMinecraft().renderEngine.bindTexture(modelTexture);
		GL11.glPushMatrix();
		
		switch(type){
		case ENTITY:
			renderEntity();
			break;
		case EQUIPPED:
			renderEquipped();
			break;
		case EQUIPPED_FIRST_PERSON:
			renderEquippedFP();
			break;
		/*case INVENTORY:
			renderInventory();
			break;*/
		default : 
			break;
		}
		preSpecials(item, modelx, data);
		renderScale();
		modelx.render(null,0,0,0,0,0,0.0625f);
		
		
		GL11.glPopMatrix();
	}
	public abstract void renderEquippedFP();
	public abstract void renderEntity();
	public abstract void renderEquipped();
	public abstract void renderScale();
//	public abstract void renderInventory();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}
	
	public void preSpecials(ItemStack item, ModelBase model, Object... data){
		
	}
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,ItemRendererHelper helper) {
		  return true;
	}

		
	}
