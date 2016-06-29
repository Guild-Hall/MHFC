package mhfc.net.client.render.weapon.greatsword;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.greatsword.ModelGSTigrex;
import mhfc.net.client.render.weapon.RenderMelee;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;

public class RenderGSTigrex extends RenderMelee<ModelGSTigrex> {

	public RenderGSTigrex() {
		super(new ModelGSTigrex(), MHFCReference.weapon_gs_tigrexagito_tex, 1f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preEquipped(render, entityLiving);
		GL11.glTranslatef(0.72F, 0.4F, -0.05F);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preFirstPerson(render, entityLiving);
		GL11.glTranslatef(0.4F, 0.7F, -0.1F);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		super.preInventory(render);
		GL11.glTranslatef(0f, 0.7F, 0F);
	}

}
