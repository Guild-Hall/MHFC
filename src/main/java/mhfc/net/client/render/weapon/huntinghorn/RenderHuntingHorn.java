package mhfc.net.client.render.weapon.huntinghorn;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.render.weapon.RenderMelee;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;

public class RenderHuntingHorn<T extends ModelBase> extends RenderMelee<T> {

	protected RenderHuntingHorn(T model, String texture, float scale) {
		super(model, texture, scale);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		super.preFirstPerson(render, entityLiving);
		GL11.glRotatef(180f, 0, 1f, 0);
		GL11.glTranslatef(-0.6f, 0.2f, 0);
	}

	@Override
	public void preEntityItem(RenderBlocks render, EntityItem entityItem) {
		GL11.glRotatef(90f, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(45F, 0.0f, 0.0f, 1.0f);
	}

}
