package mhfc.net.client.render.weapon.bow;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.bow.IModelBow;
import mhfc.net.client.render.weapon.RenderWeapon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

public abstract class RenderBow<T extends ModelBase & IModelBow> extends RenderWeapon<T> {

	public RenderBow(T model, String texture, float scale) {
		super(model, texture, scale);
	}

	protected void setup(EntityLivingBase living) {
		if (!(living instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) living;
		if (player.getCurrentEquippedItem() != null) {
			int usingItem = player.getItemInUseDuration();
			if (usingItem >= 1) {
				model.setupStart();
			}
			if (usingItem >= 5) {
				model.setupHalf();
			}
			if (usingItem >= 25) {
				model.setupFull();
			}
		}
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		setup(entityLiving);
		GL11.glRotatef(00F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-90F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-180F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-0.1F, -0.2F, -0.4F);
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		setup(entityLiving);
		float scale = 1.5f;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-5F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-150F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-1.1F, -0.6F, 0.5F);
	}

	@Override
	public void preEntityItem(RenderBlocks render, EntityItem entityItem) {
		model.setupStart();
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(45F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(0F, -0.6F, -0.1F);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		model.setupStart();
		GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(0.1F, -0.0F, -0.1F);
	}

}
