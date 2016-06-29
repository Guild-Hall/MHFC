package mhfc.net.client.render.weapon.huntinghorn;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.huntinghorn.ModelHHIvoryHorn;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;

public class RenderHHIvoryHorn extends RenderHuntingHorn<ModelHHIvoryHorn> {

	public RenderHHIvoryHorn() {
		super(new ModelHHIvoryHorn(), MHFCReference.weapon_hh_ivoryhorn_tex, 1.5f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preEquipped(render, entityLiving);
		GL11.glRotatef(90f, 0, 1, 0);
		GL11.glTranslatef(0.1f, -0.65F, 0.5f);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preFirstPerson(render, entityLiving);
		GL11.glRotatef(90f, 0, 1f, 0);
		GL11.glTranslatef(-.2f, -0.7f, -0F);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		super.preInventory(render);
		GL11.glTranslatef(0F, -0.3F, -0.0F);
		GL11.glRotatef(90, 0, 1, 0);
	}

}
