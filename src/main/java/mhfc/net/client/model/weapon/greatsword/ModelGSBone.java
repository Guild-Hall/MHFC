package mhfc.net.client.model.weapon.greatsword;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGSBone extends ModelBase {
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

	public ModelGSBone() {
		textureWidth = 32;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 0, 23);
		Shape1.addBox(0F, 0F, 0F, 1, 8, 1);
		Shape1.setRotationPoint(0F, 10F, 0F);
		Shape1.setTextureSize(32, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 1.570796F, 0F);
		Shape2 = new ModelRenderer(this, 4, 26);
		Shape2.addBox(0F, -1F, -2F, 1, 3, 3);
		Shape2.setRotationPoint(0.5F, 8F, 0F);
		Shape2.setTextureSize(32, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0.7853982F, 1.570796F, 0F);
		Shape3 = new ModelRenderer(this, 12, 27);
		Shape3.addBox(0F, 0F, -1F, 2, 4, 1);
		Shape3.setRotationPoint(0.5F, 5.2F, 0.5F);
		Shape3.setTextureSize(32, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0.7853982F, 1.570796F, 0F);
		Shape4 = new ModelRenderer(this, 0, 9);
		Shape4.addBox(0F, 0F, 0F, 2, 3, 1);
		Shape4.setRotationPoint(-1.5F, -2F, 0.5F);
		Shape4.setTextureSize(32, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 1.570796F, 0F);
		Shape5 = new ModelRenderer(this, 4, 22);
		Shape5.addBox(0F, 0F, 0F, 1, 2, 2);
		Shape5.setRotationPoint(-1F, 6F, 0F);
		Shape5.setTextureSize(32, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 1.570796F, 0F);
		Shape6 = new ModelRenderer(this, 0, 4);
		Shape6.addBox(0F, 0F, 0F, 2, 4, 1);
		Shape6.setRotationPoint(-0.6F, -5.8F, 0.5F);
		Shape6.setTextureSize(32, 32);
		Shape6.mirror = true;
		setRotation(Shape6, -0.2443461F, 1.570796F, 0F);
		Shape7 = new ModelRenderer(this, 0, 0);
		Shape7.addBox(0F, 0F, 0F, 1, 3, 1);
		Shape7.setRotationPoint(0.51F, -8F, 0F);
		Shape7.setTextureSize(32, 32);
		Shape7.mirror = true;
		setRotation(Shape7, -0.4014257F, 1.570796F, 0F);
		Shape8 = new ModelRenderer(this, 22, 0);
		Shape8.addBox(0F, 0F, 0F, 2, 10, 1);
		Shape8.setRotationPoint(-0.5F, -4F, 0.5F);
		Shape8.setTextureSize(32, 32);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 1.570796F, 0F);
		Shape9 = new ModelRenderer(this, 28, 0);
		Shape9.addBox(0F, 0F, 0F, 1, 10, 1);
		Shape9.setRotationPoint(0F, -3F, 0F);
		Shape9.setTextureSize(32, 32);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 1.570796F, 0F);
		Shape10 = new ModelRenderer(this, 0, 13);
		Shape10.addBox(0F, 0F, 0F, 2, 5, 1);
		Shape10.setRotationPoint(-1.5F, 1F, 0.5F);
		Shape10.setTextureSize(32, 32);
		Shape10.mirror = true;
		setRotation(Shape10, 0.0698132F, 1.570796F, 0F);
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
