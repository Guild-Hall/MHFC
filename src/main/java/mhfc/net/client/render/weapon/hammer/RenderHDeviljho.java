package mhfc.net.client.render.weapon.hammer;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.hammer.ModelHDeviljho;
import mhfc.net.client.render.weapon.RenderMelee;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;

public class RenderHDeviljho extends RenderMelee<ModelHDeviljho> {

	public RenderHDeviljho() {
		super(new ModelHDeviljho(), MHFCReference.weapon_hm_deviljho_tex, 1.5f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preEquipped(render, entityLiving);
		GL11.glTranslatef(0.45F, -0.65F, -0.0F);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preFirstPerson(render, entityLiving);
		GL11.glTranslatef(0.35F, -0.45F, -0.0F);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		super.preInventory(render);
		GL11.glTranslatef(-0.2F, -0.7F, -0.2F);
	}

}
