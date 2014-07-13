package mhfc.net.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAkuraCrystal extends ModelBase {
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;

	public ModelAkuraCrystal() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 19, 0);
		Shape1.addBox(-1F, -5F, -1F, 2, 5, 2);
		Shape1.setRotationPoint(0F, 24F, 0F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0.5576792F, 1.003822F, -0.7435722F);
		Shape2 = new ModelRenderer(this, 0, 0);
		Shape2.addBox(-1F, -7F, -1F, 2, 7, 2);
		Shape2.setRotationPoint(0F, 24F, 0F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0.1858931F, -0.2602503F);
		Shape3 = new ModelRenderer(this, 37, 0);
		Shape3.addBox(-1F, -5F, -1F, 1, 5, 1);
		Shape3.setRotationPoint(0F, 24F, 0F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, -0.2602503F, 0.8922867F, 0.3717861F);
		Shape4 = new ModelRenderer(this, 42, 0);
		Shape4.addBox(-1F, -5F, -1F, 2, 5, 2);
		Shape4.setRotationPoint(0F, 24F, 0F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 1.933288F, 0.5205006F);
		Shape5 = new ModelRenderer(this, 28, 0);
		Shape5.addBox(-1F, -5F, -1F, 2, 5, 2);
		Shape5.setRotationPoint(0F, 24F, 0F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 1.933288F, -0.7435722F);
		Shape6 = new ModelRenderer(this, 9, 0);
		Shape6.addBox(-1F, -5F, -1F, 2, 5, 2);
		Shape6.setRotationPoint(0F, 24F, 0F);
		Shape6.setTextureSize(64, 32);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0.4833219F, 0.8179294F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
	}

	public void renderModel(float f) {
		Shape1.render(f);
		Shape2.render(f);
		Shape3.render(f);
		Shape4.render(f);
		Shape5.render(f);
		Shape6.render(f);
	}
	private static void setRotation(ModelRenderer model, float x, float y,
			float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}

}
