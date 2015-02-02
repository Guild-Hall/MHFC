package mhfc.net.client.model.weapon.hammer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHTigrex extends ModelBase {
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Shape8;
	ModelRenderer Shape9;
	ModelRenderer Shape10;
	ModelRenderer Shape11;
	ModelRenderer Shape12;

	public ModelHTigrex() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 0, 28);
		Shape1.addBox(0F, 0F, 0F, 2, 2, 2);
		Shape1.setRotationPoint(-0.5F, 10F, -0.5F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 28, 19);
		Shape2.addBox(0F, 0F, 0F, 1, 12, 1);
		Shape2.setRotationPoint(0F, -2F, 0F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 22);
		Shape3.addBox(0F, 0F, 0F, 2, 4, 2);
		Shape3.setRotationPoint(-0.5F, 2F, -0.5F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 8, 22);
		Shape4.addBox(0F, 0F, 0F, 2, 8, 2);
		Shape4.setRotationPoint(-3.5F, -2F, 1.5F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0.3316126F, 1.570796F, 0F);
		Shape5 = new ModelRenderer(this, 0, 0);
		Shape5.addBox(0F, 0F, 0F, 3, 3, 10);
		Shape5.setRotationPoint(-5F, -8F, 0F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 1.658063F, -0.6981317F);
		Shape6 = new ModelRenderer(this, 40, 0);
		Shape6.addBox(-3F, 0F, 0F, 3, 3, 9);
		Shape6.setRotationPoint(-5.5F, -5F, 2F);
		Shape6.setTextureSize(64, 32);
		Shape6.mirror = true;
		setRotation(Shape6, 0.0872665F, 1.745329F, 0F);
		Shape7 = new ModelRenderer(this, 40, 0);
		Shape7.addBox(0F, 0F, 0F, 3, 3, 9);
		Shape7.setRotationPoint(-5F, -5F, -0.5F);
		Shape7.setTextureSize(64, 32);
		Shape7.mirror = true;
		setRotation(Shape7, 0.0872665F, 1.658063F, 0F);
		Shape8 = new ModelRenderer(this, 0, 0);
		Shape8.addBox(-3F, 0F, 0F, 3, 3, 10);
		Shape8.setRotationPoint(-5.5F, -8F, 1F);
		Shape8.setTextureSize(64, 32);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 1.658063F, 0.6981317F);
		Shape9 = new ModelRenderer(this, 16, 26);
		Shape9.addBox(-0.5F, 0F, 0F, 1, 3, 3);
		Shape9.setRotationPoint(-7.5F, -5F, 0.5F);
		Shape9.setTextureSize(64, 32);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 1.570796F, -1.047198F);
		Shape10 = new ModelRenderer(this, 16, 26);
		Shape10.addBox(-0.5F, -3F, 0F, 1, 3, 3);
		Shape10.setRotationPoint(-7.5F, -5F, 0.5F);
		Shape10.setTextureSize(64, 32);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 1.570796F, 0F);
		Shape11 = new ModelRenderer(this, 16, 26);
		Shape11.addBox(-0.5F, 0F, 0F, 1, 3, 3);
		Shape11.setRotationPoint(-7.5F, -5F, 0.5F);
		Shape11.setTextureSize(64, 32);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 1.570796F, 1.047198F);
		Shape12 = new ModelRenderer(this, 36, 18);
		Shape12.addBox(0F, 0F, 0F, 6, 6, 8);
		Shape12.setRotationPoint(-5.5F, -8F, 3.5F);
		Shape12.setTextureSize(64, 32);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 1.570796F, 0F);
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
		Shape9.renderWithRotation(f5);
		Shape10.renderWithRotation(f5);
		Shape11.renderWithRotation(f5);
		Shape12.renderWithRotation(f5);
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
