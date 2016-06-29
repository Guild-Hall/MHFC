package mhfc.net.client.render.weapon.longsword;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.longsword.ModelLSDarkVipern;
import mhfc.net.client.render.weapon.RenderMelee;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;

public class RenderLSDarkVipern extends RenderMelee<ModelLSDarkVipern> {

	public RenderLSDarkVipern() {
		super(new ModelLSDarkVipern(), MHFCReference.weapon_ls_darkviper_tex, 1.0f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preEquipped(render, entityLiving);
		GL11.glTranslatef(0.65F, 0.6F, -0.1F);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preFirstPerson(render, entityLiving);
		GL11.glTranslatef(0.65F, 1.1F, -0.1F);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		super.preInventory(render);
		GL11.glTranslatef(-0.2F, 0.7F, -0.1F);
	}

}
