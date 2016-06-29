package mhfc.net.client.render.weapon.bow;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.model.weapon.bow.IModelBow;
import mhfc.net.client.render.weapon.RenderRange;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

public abstract class RenderBow<T extends ModelBase & IModelBow> extends RenderRange<T> {

	public RenderBow(T model, String texture, float scale) {
		super(model, texture, scale);
	}

	protected void setup(EntityLivingBase living) {
		if (!(living instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) living;
		if (player.getHeldItem() != null) {
			int usingItem = player.getItemInUseDuration();
			if (usingItem >= 1) {
				model.setupStart();
			}
			if (usingItem >= 5) {
				model.setupHalf();
			}
			if (usingItem >= 25) {
				model.setupFull();
			}
			if (usingItem == 0) {
				model.setupRest();
			}
		}
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preEquipped(render, entityLiving);
		GL11.glTranslatef(0.6F, -0.2F, -0F);
		setup(entityLiving);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preFirstPerson(render, entityLiving);
		GL11.glTranslatef(0.35F, -0.45F, -0F);
		setup(entityLiving);
	}

	@Override
	public void preEntityItem(RenderBlocks render, EntityItem entityItem) {
		super.preEntityItem(render, entityItem);
		model.setupRest();
	}

	@Override
	public void preInventory(RenderBlocks render) {
		super.preInventory(render);
		model.setupRest();
	}

}
