package mhfc.net.client.model.weapon.hammer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHWar extends ModelBase {
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Shape8;

	public ModelHWar() {
		textureWidth = 32;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 4, 0);
		Shape1.addBox(0F, 0F, 0F, 1, 4, 1);
		Shape1.setRotationPoint(0F, 10F, -1F);
		Shape1.setTextureSize(32, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 1.570796F, 0F);
		Shape2 = new ModelRenderer(this, 8, 0);
		Shape2.addBox(0F, 0F, 0F, 2, 1, 2);
		Shape2.setRotationPoint(-0.5F, 14F, -0.5F);
		Shape2.setTextureSize(32, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 1.570796F, 0F);
		Shape3 = new ModelRenderer(this, 10, 5);
		Shape3.addBox(0F, 0F, 0F, 2, 4, 2);
		Shape3.setRotationPoint(-0.5F, 6F, -0.5F);
		Shape3.setTextureSize(32, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 1.570796F, 0F);
		Shape4 = new ModelRenderer(this, 0, 0);
		Shape4.addBox(0F, 0F, 0F, 1, 4, 1);
		Shape4.setRotationPoint(0F, 2F, -1F);
		Shape4.setTextureSize(32, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 1.570796F, 0F);
		Shape5 = new ModelRenderer(this, 0, 19);
		Shape5.addBox(0F, 0F, 0F, 5, 5, 8);
		Shape5.setRotationPoint(-3F, -3F, 1F);
		Shape5.setTextureSize(32, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 1.570796F, 0F);
		Shape6 = new ModelRenderer(this, 0, 11);
		Shape6.addBox(0F, 0F, -6F, 3, 2, 6);
		Shape6.setRotationPoint(4F, -4F, 0F);
		Shape6.setTextureSize(32, 32);
		Shape6.mirror = true;
		setRotation(Shape6, 0.1745329F, 1.570796F, 0F);
		Shape7 = new ModelRenderer(this, 18, 12);
		Shape7.addBox(0F, 0F, -1F, 2, 6, 1);
		Shape7.setRotationPoint(4F, 2F, -0.5F);
		Shape7.setTextureSize(32, 32);
		Shape7.mirror = true;
		setRotation(Shape7, -0.4363323F, 1.570796F, 0F);
		Shape8 = new ModelRenderer(this, 0, 5);
		Shape8.addBox(0F, 0F, 0F, 3, 4, 2);
		Shape8.setRotationPoint(4.5F, -3F, 0F);
		Shape8.setTextureSize(32, 32);
		Shape8.mirror = true;
		setRotation(Shape8, -0.4712389F, 1.570796F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		render(f5);

	}

	public void render(float f5) {
		Shape1.renderWithRotation(f5);
		Shape2.renderWithRotation(f5);
		Shape3.renderWithRotation(f5);
		Shape4.renderWithRotation(f5);
		Shape5.renderWithRotation(f5);
		Shape6.renderWithRotation(f5);
		Shape7.renderWithRotation(f5);
		Shape8.renderWithRotation(f5);
	}

	private static void setRotation(ModelRenderer model, float x, float y,
			float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity ent) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
	}

}
