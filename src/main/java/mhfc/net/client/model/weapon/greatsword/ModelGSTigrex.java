package mhfc.net.client.model.weapon.greatsword;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGSTigrex extends ModelBase {
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Shape8B;
	ModelRenderer Shape8A;
	ModelRenderer Shape9A;
	ModelRenderer Shape9B;
	ModelRenderer Shape10;
	ModelRenderer Shape11;
	ModelRenderer Shape12A;
	ModelRenderer Shape12B;
	ModelRenderer Shape13A;
	ModelRenderer Shape13B;
	ModelRenderer Shape14;
	ModelRenderer Shape15;
	ModelRenderer Shape16;
	ModelRenderer Shape17;
	ModelRenderer Shape17B;
	ModelRenderer Shape17C;
	ModelRenderer Shape17D;
	ModelRenderer Shape17E;
	ModelRenderer Shape17F;

	public ModelGSTigrex() {
		textureWidth = 256;
		textureHeight = 128;

		Shape1 = new ModelRenderer(this, 61, 24);
		Shape1.addBox(-1F, -1F, -1F, 2, 2, 2);
		Shape1.setRotationPoint(0F, 5F, -0.5F);
		Shape1.setTextureSize(256, 128);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 13, 12);
		Shape2.addBox(-0.5F, -0.5F, -3F, 1, 1, 2);
		Shape2.setRotationPoint(0F, 5F, -0.5F);
		Shape2.setTextureSize(256, 128);
		Shape2.mirror = true;
		setRotation(Shape2, -0.1745329F, 1.570796F, 0F);
		Shape3 = new ModelRenderer(this, 22, 0);
		Shape3.addBox(-0.5F, -12F, -0.5F, 1, 14, 1);
		Shape3.setRotationPoint(0F, 2F, -0.5F);
		Shape3.setTextureSize(256, 128);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 0, 0);
		Shape4.addBox(-1F, -6F, -1F, 2, 6, 2);
		Shape4.setRotationPoint(0F, 1F, -0.5F);
		Shape4.setTextureSize(256, 128);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 0, 13);
		Shape5.addBox(-1.5F, -1F, -1.5F, 3, 2, 3);
		Shape5.setRotationPoint(0F, -11F, -0.5F);
		Shape5.setTextureSize(256, 128);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 0, 20);
		Shape6.addBox(-3.6F, -2F, -0.9F, 7, 5, 5);
		Shape6.setRotationPoint(-2F, -15F, -0.5F);
		Shape6.setTextureSize(256, 128);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 1.570796F, 0F);
		Shape7 = new ModelRenderer(this, 0, 34);
		Shape7.addBox(-0.5F, -4F, -0.5F, 1, 5, 1);
		Shape7.setRotationPoint(0F, -15F, 2F);
		Shape7.setTextureSize(256, 128);
		Shape7.mirror = true;
		setRotation(Shape7, -0.2617994F, 0F, 0F);
		Shape8B = new ModelRenderer(this, 39, 39);
		Shape8B.addBox(-1.5F, -19F, -2F, 3, 20, 4);
		Shape8B.setRotationPoint(0F, -16F, 1.5F);
		Shape8B.setTextureSize(256, 128);
		Shape8B.mirror = true;
		setRotation(Shape8B, 0F, 1.570796F, -0.0523599F);
		Shape8A = new ModelRenderer(this, 22, 39);
		Shape8A.addBox(-1.5F, -16F, -2F, 3, 20, 4);
		Shape8A.setRotationPoint(0F, -19F, -2.5F);
		Shape8A.setTextureSize(256, 128);
		Shape8A.mirror = true;
		setRotation(Shape8A, 0F, 1.570796F, 0.0523599F);
		Shape9A = new ModelRenderer(this, 0, 42);
		Shape9A.addBox(-2.5F, 0F, -2.5F, 5, 6, 5);
		Shape9A.setRotationPoint(0F, -40F, 2.5F);
		Shape9A.setTextureSize(256, 128);
		Shape9A.mirror = true;
		setRotation(Shape9A, 0F, 1.570796F, 0F);
		Shape9B = new ModelRenderer(this, 0, 55);
		Shape9B.addBox(-2.5F, 0F, -2.5F, 5, 6, 5);
		Shape9B.setRotationPoint(0F, -40F, -3.5F);
		Shape9B.setTextureSize(256, 128);
		Shape9B.mirror = true;
		setRotation(Shape9B, 0F, 1.570796F, 0F);
		Shape10 = new ModelRenderer(this, 40, 0);
		Shape10.addBox(-1F, -21F, -1F, 2, 22, 2);
		Shape10.setRotationPoint(2F, -14F, 1.5F);
		Shape10.setTextureSize(256, 128);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 1.570796F, -0.0523599F);
		Shape11 = new ModelRenderer(this, 30, 0);
		Shape11.addBox(1F, -21F, 1F, 2, 22, 2);
		Shape11.setRotationPoint(0F, -14F, -0.5F);
		Shape11.setTextureSize(256, 128);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 1.570796F, 0.0523599F);
		Shape12A = new ModelRenderer(this, 29, 26);
		Shape12A.addBox(-2F, -2.5F, -0.5F, 4, 5, 2);
		Shape12A.setRotationPoint(3F, -37F, 2F);
		Shape12A.setTextureSize(256, 128);
		Shape12A.mirror = true;
		setRotation(Shape12A, 0F, 0F, 0F);
		Shape12B = new ModelRenderer(this, 44, 26);
		Shape12B.addBox(-3F, -2.5F, 1.5F, 4, 5, 2);
		Shape12B.setRotationPoint(3F, -37F, -6F);
		Shape12B.setTextureSize(256, 128);
		Shape12B.mirror = true;
		setRotation(Shape12B, 0F, 0F, 0F);
		Shape13A = new ModelRenderer(this, 52, 0);
		Shape13A.addBox(-1.5F, -1.5F, -7F, 3, 3, 7);
		Shape13A.setRotationPoint(0F, -37F, 2.5F);
		Shape13A.setTextureSize(256, 128);
		Shape13A.mirror = true;
		setRotation(Shape13A, 0.1745329F, 1.570796F, 0F);
		Shape13B = new ModelRenderer(this, 52, 0);
		Shape13B.addBox(-1.5F, -1.5F, -7F, 3, 3, 7);
		Shape13B.setRotationPoint(-0.2F, -37F, -3.5F);
		Shape13B.setTextureSize(256, 128);
		Shape13B.mirror = true;
		setRotation(Shape13B, 0.1745329F, 1.570796F, 0F);
		Shape14 = new ModelRenderer(this, 55, 46);
		Shape14.addBox(-1.5F, -3F, -2.3F, 3, 6, 3);
		Shape14.setRotationPoint(-4F, -13F, 0.5F);
		Shape14.setTextureSize(256, 128);
		Shape14.mirror = true;
		setRotation(Shape14, 0F, 0F, 0F);
		Shape15 = new ModelRenderer(this, 58, 33);
		Shape15.addBox(-1F, -12F, -1F, 2, 8, 2);
		Shape15.setRotationPoint(-4F, 1F, -0.5F);
		Shape15.setTextureSize(256, 128);
		Shape15.mirror = true;
		setRotation(Shape15, 0F, 0F, 0F);
		Shape16 = new ModelRenderer(this, 15, 0);
		Shape16.addBox(-0.5F, -6F, -0.5F, 1, 6, 1);
		Shape16.setRotationPoint(0F, 0F, -0.5F);
		Shape16.setTextureSize(256, 128);
		Shape16.mirror = true;
		setRotation(Shape16, 0.6806784F, 1.570796F, 0F);
		Shape17 = new ModelRenderer(this, 54, 12);
		Shape17.addBox(-1F, -1F, -5F, 2, 2, 6);
		Shape17.setRotationPoint(-2.2F, -30F, -3F);
		Shape17.setTextureSize(256, 128);
		Shape17.mirror = true;
		setRotation(Shape17, 0.1745329F, 1.570796F, 0.0523599F);
		Shape17B = new ModelRenderer(this, 54, 12);
		Shape17B.addBox(-1F, -1F, -5F, 2, 2, 6);
		Shape17B.setRotationPoint(-2.5F, -30F, 2F);
		Shape17B.setTextureSize(256, 128);
		Shape17B.mirror = true;
		setRotation(Shape17B, 0.1745329F, 1.570796F, -0.0523599F);
		Shape17C = new ModelRenderer(this, 54, 12);
		Shape17C.addBox(-1F, -1F, -4F, 2, 2, 6);
		Shape17C.setRotationPoint(-2.2F, -26F, -3F);
		Shape17C.setTextureSize(256, 128);
		Shape17C.mirror = true;
		setRotation(Shape17C, 0.1745329F, 1.570796F, 0.0523599F);
		Shape17D = new ModelRenderer(this, 54, 12);
		Shape17D.addBox(-1F, -1F, -4F, 2, 2, 6);
		Shape17D.setRotationPoint(-2.5F, -26F, 2F);
		Shape17D.setTextureSize(256, 128);
		Shape17D.mirror = true;
		setRotation(Shape17D, 0.1745329F, 1.570796F, -0.0523599F);
		Shape17E = new ModelRenderer(this, 54, 12);
		Shape17E.addBox(-1F, -1F, -3F, 2, 2, 6);
		Shape17E.setRotationPoint(-3.2F, -22F, 2F);
		Shape17E.setTextureSize(256, 128);
		Shape17E.mirror = true;
		setRotation(Shape17E, 0.1745329F, 1.570796F, -0.0523599F);
		Shape17F = new ModelRenderer(this, 54, 12);
		Shape17F.addBox(-1F, -1F, -3F, 2, 2, 6);
		Shape17F.setRotationPoint(-3.2F, -22F, -3F);
		Shape17F.setTextureSize(256, 128);
		Shape17F.mirror = true;
		setRotation(Shape17F, 0.1745329F, 1.570796F, 0.0523599F);
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
		Shape8B.renderWithRotation(f5);
		Shape8A.renderWithRotation(f5);
		Shape9A.renderWithRotation(f5);
		Shape9B.renderWithRotation(f5);
		Shape10.renderWithRotation(f5);
		Shape11.renderWithRotation(f5);
		Shape12A.renderWithRotation(f5);
		Shape12B.renderWithRotation(f5);
		Shape13A.renderWithRotation(f5);
		Shape13B.renderWithRotation(f5);
		Shape14.renderWithRotation(f5);
		Shape15.renderWithRotation(f5);
		Shape16.renderWithRotation(f5);
		Shape17.renderWithRotation(f5);
		Shape17B.renderWithRotation(f5);
		Shape17C.renderWithRotation(f5);
		Shape17D.renderWithRotation(f5);
		Shape17E.renderWithRotation(f5);
		Shape17F.renderWithRotation(f5);
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
