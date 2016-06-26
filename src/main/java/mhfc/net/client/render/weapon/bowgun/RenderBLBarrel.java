package mhfc.net.client.render.weapon.bowgun;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.bowgun.ModelBLBarrel;
import mhfc.net.client.render.weapon.RenderWeapon;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;

public class RenderBLBarrel extends RenderWeapon<ModelBLBarrel> {

	public RenderBLBarrel() {
		super(new ModelBLBarrel(), MHFCReference.weapon_bgl_barrel_tex, 2f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(180F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(180F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(90F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-0.1F, 0.2F, -0.2F);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		float scale = 1.4f;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-5F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-150F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(0.05F, -0.6F, -0.8F);
	}

	@Override
	public void preEntityItem(RenderBlocks render, EntityItem entityItem) {
		float scale = 2.4F;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(45F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(0F, -0.6F, -0.1F);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		float scale = 2.5F;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(0.1F, -0.4F, -0.5F);
	}

}
