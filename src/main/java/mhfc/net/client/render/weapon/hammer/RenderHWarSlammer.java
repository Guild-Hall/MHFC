package mhfc.net.client.render.weapon.hammer;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.hammer.ModelHWarSlammer;
import mhfc.net.client.render.weapon.RenderMelee;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;

public class RenderHWarSlammer extends RenderMelee<ModelHWarSlammer> {

	public RenderHWarSlammer() {
		super(new ModelHWarSlammer(), MHFCReference.weapon_hm_warslammer_tex, 1.8f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preEquipped(render, entityLiving);
		GL11.glTranslatef(0.35F, -0.7F, -0.15F);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preFirstPerson(render, entityLiving);
		GL11.glTranslatef(0.35F, -0.45F, -0.1F);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		super.preInventory(render);
		GL11.glTranslatef(-0.0F, -0.3F, -0.0F);
	}

}
