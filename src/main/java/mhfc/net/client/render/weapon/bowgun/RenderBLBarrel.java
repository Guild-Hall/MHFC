package mhfc.net.client.render.weapon.bowgun;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.bowgun.ModelBLBarrel;
import mhfc.net.client.render.weapon.RenderRange;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;

public class RenderBLBarrel extends RenderRange<ModelBLBarrel> {

	public RenderBLBarrel() {
		super(new ModelBLBarrel(), MHFCReference.weapon_bgl_barrel_tex, 2f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preEquipped(render, entityLiving);
		GL11.glTranslatef(0.2F, -0.05F, -0.2F);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preFirstPerson(render, entityLiving);
		GL11.glTranslatef(0.35F, -0.45F, -0F);
	}

	@Override
	public void preEntityItem(RenderBlocks render, EntityItem entityItem) {
		super.preEntityItem(render, entityItem);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		super.preInventory(render);
	}

}
