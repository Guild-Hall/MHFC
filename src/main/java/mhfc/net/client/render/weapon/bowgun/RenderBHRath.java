package mhfc.net.client.render.weapon.bowgun;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.bowgun.ModelRathBowgun;
import mhfc.net.client.render.weapon.RenderRange;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;

public class RenderBHRath extends RenderRange<ModelRathBowgun> {

	public RenderBHRath() {
		super(new ModelRathBowgun(), MHFCReference.weapon_bgh_rath_tex, 1.3f);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preEquipped(render, entityLiving);
		GL11.glTranslatef(0.6F, -0.5f, 0.1f);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preFirstPerson(render, entityLiving);
		GL11.glTranslatef(1.25F, -1.15F, -0F);
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
