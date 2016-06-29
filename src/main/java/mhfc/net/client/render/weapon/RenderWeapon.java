package mhfc.net.client.render.weapon;

import java.util.Objects;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public abstract class RenderWeapon<T extends ModelBase> implements IItemRenderer {

	protected final T model;
	protected final ResourceLocation modelTexture;
	protected final float scale;

	public RenderWeapon(T model, String texture, float scale) {
		this.model = Objects.requireNonNull(model);
		this.modelTexture = new ResourceLocation(texture);
		this.scale = scale;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		Minecraft.getMinecraft().renderEngine.bindTexture(modelTexture);
		GL11.glPushMatrix();

		preScale();
		// @see ItemRenderType
		Entity entity = null;
		switch (type) {
		case ENTITY: {
			RenderBlocks render = (RenderBlocks) data[0];
			EntityItem entityItem = (EntityItem) data[1];
			preEntityItem(render, entityItem);
			break;
		}
		case EQUIPPED: {
			RenderBlocks render = (RenderBlocks) data[0];
			EntityLivingBase entityLiving = (EntityLivingBase) data[1];
			entity = entityLiving;
			preEquipped(render, entityLiving);
			break;
		}
		case EQUIPPED_FIRST_PERSON: {
			RenderBlocks render = (RenderBlocks) data[0];
			EntityLivingBase entityLiving = (EntityLivingBase) data[1];
			entity = entityLiving;
			preFirstPerson(render, entityLiving);
			break;
		}
		case INVENTORY: {
			RenderBlocks render = (RenderBlocks) data[0];
			preInventory(render);
			break;
		}
		default:
			break;
		}
		model.render(entity, 0, 0, 0, 0, 0, 0.0625f);

		GL11.glPopMatrix();
	}

	public abstract void preEquipped(RenderBlocks render, EntityLivingBase entityLiving);

	public abstract void preFirstPerson(RenderBlocks render, EntityLivingBase entityLiving);

	public abstract void preEntityItem(RenderBlocks render, EntityItem entityItem);

	public abstract void preInventory(RenderBlocks render);

	protected void preScale() {
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch (type) {
		case INVENTORY:
			return true;
		default:
			break;
		}
		return false;
	}

}
