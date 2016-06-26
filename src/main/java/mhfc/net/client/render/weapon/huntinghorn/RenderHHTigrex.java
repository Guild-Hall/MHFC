package mhfc.net.client.render.weapon.huntinghorn;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.huntinghorn.ModelHHTigrex;
import mhfc.net.client.render.weapon.RenderWeapon;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;

public class RenderHHTigrex extends RenderWeapon<ModelHHTigrex> {

	public RenderHHTigrex() {
		super(new ModelHHTigrex(), MHFCReference.weapon_hh_tigrex_tex, 1.7f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-5F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-120F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-0.35F, -0.5F, -0.05F);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		float scale = 1.4f;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-5F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-150F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-0.4F, -0.4F, -0.1F);
	}

	@Override
	public void preEntityItem(RenderBlocks render, EntityItem entityItem) {
		float scale = 3F;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(90F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-0.2F, -0.3F, 0F);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(0F, -0.1F, -0.0F);
	}

}
