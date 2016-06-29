package mhfc.net.client.render.weapon;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;

public class RenderMelee<T extends ModelBase> extends RenderWeapon<T> {

	public RenderMelee(T model, String texture, float scale) {
		super(model, texture, scale);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		GL11.glRotatef(180F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-10F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(330, 0.0f, 0.0f, 1.0f);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		GL11.glRotatef(180f, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-10F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-30f, 0.0f, 0.0f, 1.0f);
	}

	@Override
	public void preEntityItem(RenderBlocks render, EntityItem entityItem) {
		GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(45F, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(-0.2F, -0.5F, 0F);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
	}

}
