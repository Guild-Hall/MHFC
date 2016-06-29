package mhfc.net.client.render.weapon.greatsword;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.greatsword.ModelGSBone;
import mhfc.net.client.render.weapon.RenderMelee;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;

public class RenderGSBone extends RenderMelee<ModelGSBone> {

	public RenderGSBone() {
		super(new ModelGSBone(), MHFCReference.weapon_gs_bone_tex, 1.5f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preEquipped(render, entityLiving);
		GL11.glTranslatef(0.45F, -0.65F, -0.05F);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preFirstPerson(render, entityLiving);
		GL11.glTranslatef(0.35F, -0.45F, 0F);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		super.preInventory(render);
		GL11.glTranslatef(0.1F, -0.4F, 0F);
	}

}
