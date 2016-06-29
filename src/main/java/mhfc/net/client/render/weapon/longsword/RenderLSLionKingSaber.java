package mhfc.net.client.render.weapon.longsword;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.longsword.ModelLSSaber;
import mhfc.net.client.render.weapon.RenderWeapon;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;

public class RenderLSLionKingSaber extends RenderWeapon<ModelLSSaber> {

	public RenderLSLionKingSaber() {
		super(new ModelLSSaber(), MHFCReference.weapon_ls_lionkingsaber_tex, 1.3f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(180F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-10F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(330, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(0.45F,-0.65F,-0.1F);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		float scale = 1.4f;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(180F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-10F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(330, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(0.35F,-0.45F, -0.1F);
	}

	@Override
	public void preEntityItem(RenderBlocks render, EntityItem entityItem) {
		float scale = 2F;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(45F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-0.2F, -0.5F, 0F);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-0.4F, -0.4F, -0.1F);
	}

}
