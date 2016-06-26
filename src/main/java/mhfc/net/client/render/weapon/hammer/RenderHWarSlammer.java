package mhfc.net.client.render.weapon.hammer;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.hammer.ModelHWarSlammer;
import mhfc.net.client.render.weapon.RenderWeapon;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;

public class RenderHWarSlammer extends RenderWeapon<ModelHWarSlammer> {

	public RenderHWarSlammer() {
		super(new ModelHWarSlammer(), MHFCReference.weapon_hm_warslammer_tex, 1.8f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-5F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-120F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-0.35F, -0.6F, -0.15F);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-5F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-150F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-0.4F, -0.6F, -0.1F);
	}

	@Override
	public void preEntityItem(RenderBlocks render, EntityItem entityItem) {
		float scale = 3F;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(45F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-0.2F, -0.5F, 0F);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		float scale = 1.6F;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-0.0F, -0.3F, -0.0F);
	}

}
