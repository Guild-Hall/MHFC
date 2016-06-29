package mhfc.net.client.render.weapon.huntinghorn;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.huntinghorn.ModelHHTigrex;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;

public class RenderHHTigrex extends RenderHuntingHorn<ModelHHTigrex> {

	public RenderHHTigrex() {
		super(new ModelHHTigrex(), MHFCReference.weapon_hh_tigrex_tex, 1.7f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preEquipped(render, entityLiving);
		GL11.glTranslatef(0.4f, -0.45f, -0.05f);
		GL11.glRotatef(180f, 0, 1f, 0);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preFirstPerson(render, entityLiving);
		GL11.glTranslatef(0.35F, -0.45F, -0.1F);
		GL11.glRotatef(180, 0, 1, 0);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		super.preInventory(render);
		GL11.glTranslatef(0F, -0.4F, -0.0F);
		GL11.glRotatef(180, 0, 1, 0);
	}

}
