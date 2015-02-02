package mhfc.net.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBBQSpit extends ModelBase {
	ModelRenderer Shape1;
	ModelRenderer Shape2a;
	ModelRenderer Shape2b;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape2;
	ModelRenderer Shape5;
	ModelRenderer Shape6;

	public ModelBBQSpit() {
		textureWidth = 128;
		textureHeight = 128;
		Shape1 = new ModelRenderer(this, 0, 106);
		Shape1.addBox(-5F, 0F, -10F, 10, 1, 20);
		Shape1.setRotationPoint(0F, 23F, 0F);
		Shape1.setTextureSize(128, 128);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2a = new ModelRenderer(this, 0, 38);
		Shape2a.addBox(-0.5F, -10F, -0.5F, 1, 10, 1);
		Shape2a.setRotationPoint(0F, 24F, 8F);
		Shape2a.setTextureSize(128, 128);
		Shape2a.mirror = true;
		setRotation(Shape2a, 0F, 0F, 0F);
		Shape2b = new ModelRenderer(this, 0, 38);
		Shape2b.addBox(-0.5F, -10F, 0F, 1, 10, 1);
		Shape2b.setRotationPoint(0F, 24F, -8F);
		Shape2b.setTextureSize(128, 128);
		Shape2b.mirror = true;
		setRotation(Shape2b, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 54);
		Shape3.addBox(-2.5F, -2.5F, 0F, 5, 5, 1);
		Shape3.setRotationPoint(0F, 14.5F, -9F);
		Shape3.setTextureSize(128, 128);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 0, 0);
		Shape4.addBox(-0.5F, 0F, -0.5F, 1, 1, 24);
		Shape4.setRotationPoint(0F, 13F, -13F);
		Shape4.setTextureSize(128, 128);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 66);
		Shape2.addBox(-1.5F, -1.5F, -1F, 3, 3, 1);
		Shape2.setRotationPoint(0F, 13.5F, 10F);
		Shape2.setTextureSize(128, 128);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 9, 38);
		Shape5.addBox(-3.5F, -1.5F, -3.5F, 7, 3, 7);
		Shape5.setRotationPoint(0F, 22F, 0F);
		Shape5.setTextureSize(128, 128);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 0, 78);
		Shape6.addBox(-3F, -3F, 0F, 6, 6, 11);
		Shape6.setRotationPoint(-1F, 14F, -6F);
		Shape6.setTextureSize(128, 128);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0.0174533F, -0.7041216F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shape1.render(f5);
		Shape2a.render(f5);
		Shape2b.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape2.render(f5);
		Shape5.render(f5);
	}

	public void renderModel(float f) {
		Shape1.render(f);
		Shape2a.render(f);
		Shape2b.render(f);
		Shape3.render(f);
		Shape4.render(f);
		Shape2.render(f);
		Shape5.render(f);
	}

	public void renderChicken(boolean show) {
		Shape6.showModel = show;
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
