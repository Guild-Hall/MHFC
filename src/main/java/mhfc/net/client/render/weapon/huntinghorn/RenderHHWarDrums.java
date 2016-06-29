package mhfc.net.client.render.weapon.huntinghorn;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.huntinghorn.ModelHHWarDrums;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;

public class RenderHHWarDrums extends RenderHuntingHorn<ModelHHWarDrums> {

	public RenderHHWarDrums() {
		super(new ModelHHWarDrums(), MHFCReference.weapon_hh_wardrums_tex, 1.7f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preEquipped(render, entityLiving);
		GL11.glTranslatef(0.4f, -0.45f, -0.05f);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preFirstPerson(render, entityLiving);
		GL11.glTranslatef(0.35F, -0.3F, -0.1F);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		super.preInventory(render);
		GL11.glTranslatef(0F, -0.3F, -0.0F);
	}

}
