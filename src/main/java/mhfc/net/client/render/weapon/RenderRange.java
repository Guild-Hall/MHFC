package mhfc.net.client.render.weapon;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

public class RenderRange<T extends ModelBase> extends RenderWeapon<T> {

	public RenderRange(T model, String texture, float scale) {
		super(model, texture, scale);
	}

	@Override
	public void preEquipped(RenderBlocks render, EntityLivingBase entityLiving) {
		GL11.glRotatef(180F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(0, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(330, 0.0f, 0.0f, 1.0f);
	}

	@Override
	public void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving) {
		GL11.glRotatef(180F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-10F, 0.0f, 1.0f, 0.0f);
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			if (!player.isUsingItem()) {
				GL11.glRotatef(-25f, 0.0f, 0.0f, 1.0f);
			}
		}
	}

	@Override
	public void preEntityItem(RenderBlocks render, EntityItem entityItem) {
		GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(45F, 0.0f, 0.0f, 1.0f);
	}

	@Override
	public void preInventory(RenderBlocks render) {
		GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
	}

}
