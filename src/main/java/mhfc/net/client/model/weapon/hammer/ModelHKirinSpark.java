package mhfc.net.client.model.weapon.hammer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHKirinSpark extends ModelBase {
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape8;
	ModelRenderer Shape9;
	ModelRenderer Shape10;
	ModelRenderer Shape11;
	ModelRenderer Shape12;
	ModelRenderer Shape13;

	public ModelHKirinSpark() {
		textureWidth = 64;
		textureHeight = 64;

		Shape1 = new ModelRenderer(this, 12, 0);
		Shape1.addBox(0F, 0F, 0F, 1, 15, 1);
		Shape1.setRotationPoint(-0.5F, 0F, 0F);
		Shape1.setTextureSize(64, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 0);
		Shape2.addBox(0F, 0F, 0F, 3, 3, 3);
		Shape2.setRotationPoint(-1.5F, 15F, -1F);
		Shape2.setTextureSize(64, 64);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 26);
		Shape3.addBox(0F, 0F, 0F, 3, 1, 1);
		Shape3.setRotationPoint(0.5F, 16F, 0F);
		Shape3.setTextureSize(64, 64);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, -0.2974289F);
		Shape4 = new ModelRenderer(this, 0, 26);
		Shape4.addBox(0F, 0F, 0F, 3, 1, 1);
		Shape4.setRotationPoint(-1F, 17F, 0F);
		Shape4.setTextureSize(64, 64);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, -2.767844F);
		Shape5 = new ModelRenderer(this, 0, 36);
		Shape5.addBox(0F, 0F, 0F, 3, 3, 3);
		Shape5.setRotationPoint(-1.5F, 0F, -1F);
		Shape5.setTextureSize(64, 64);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 31, 34);
		Shape6.addBox(0F, 0F, 0F, 8, 10, 8);
		Shape6.setRotationPoint(-4F, -10F, -3.5F);
		Shape6.setTextureSize(64, 64);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape8 = new ModelRenderer(this, 37, 0);
		Shape8.addBox(0F, 0F, 0F, 2, 9, 7);
		Shape8.setRotationPoint(-6F, -9.5F, -3F);
		Shape8.setTextureSize(64, 64);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 0F, 0F);
		Shape9 = new ModelRenderer(this, 37, 0);
		Shape9.addBox(0F, 0F, 0F, 2, 9, 7);
		Shape9.setRotationPoint(4F, -9.5F, -3F);
		Shape9.setTextureSize(64, 64);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 0F, 0F);
		Shape10 = new ModelRenderer(this, 0, 52);
		Shape10.addBox(0F, 0F, 0F, 1, 2, 9);
		Shape10.setRotationPoint(2F, -11F, -4F);
		Shape10.setTextureSize(64, 64);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 0F, 0F);
		Shape11 = new ModelRenderer(this, 0, 52);
		Shape11.addBox(0F, 0F, 0F, 1, 2, 9);
		Shape11.setRotationPoint(-2.4F, -11F, -4F);
		Shape11.setTextureSize(64, 64);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, 0F);
		Shape12 = new ModelRenderer(this, 0, 46);
		Shape12.addBox(0F, 0F, 0F, 2, 2, 2);
		Shape12.setRotationPoint(-0.6F, -11.5F, -0.5F);
		Shape12.setTextureSize(64, 64);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0F);
		Shape13 = new ModelRenderer(this, 0, 29);
		Shape13.addBox(0F, 0F, 0F, 1, 5, 1);
		Shape13.setRotationPoint(0F, -16F, 0F);
		Shape13.setTextureSize(64, 64);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0F, 0F);
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
		Shape8.renderWithRotation(f5);
		Shape9.renderWithRotation(f5);
		Shape10.renderWithRotation(f5);
		Shape11.renderWithRotation(f5);
		Shape12.renderWithRotation(f5);
		Shape13.renderWithRotation(f5);
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
