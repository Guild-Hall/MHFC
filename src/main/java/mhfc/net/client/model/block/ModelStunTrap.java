package mhfc.net.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelStunTrap extends ModelBase {
	ModelRenderer Pads;
	ModelRenderer Setter;

	public ModelStunTrap() {
		textureWidth = 64;
		textureHeight = 32;

		Pads = new ModelRenderer(this, 0, 0);
		Pads.addBox(-2.5F, -2F, -2.5F, 5, 2, 5);
		Pads.setRotationPoint(0F, 24F, 0F);
		Pads.setTextureSize(64, 32);
		Pads.mirror = true;
		setRotation(Pads, 0F, 0F, 0F);
		Setter = new ModelRenderer(this, 0, 9);
		Setter.addBox(-1F, -3F, -1F, 2, 1, 2);
		Setter.setRotationPoint(0F, 24F, 0F);
		Setter.setTextureSize(64, 32);
		Setter.mirror = true;
		setRotation(Setter, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Pads.render(f5);
		Setter.render(f5);
	}

	private static void setRotation(ModelRenderer model, float x, float y,
			float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void renderModel(float f) {
		Pads.render(f);
		Setter.render(f);

	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity e) {

		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);

	}

}
