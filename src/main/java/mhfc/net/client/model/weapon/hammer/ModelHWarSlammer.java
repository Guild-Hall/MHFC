package mhfc.net.client.model.weapon.hammer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHWarSlammer extends ModelBase {
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;

	public ModelHWarSlammer() {
		textureWidth = 64;
		textureHeight = 64;

		Shape1 = new ModelRenderer(this, 0, 37);
		Shape1.addBox(0F, 0F, 0F, 3, 1, 3);
		Shape1.setRotationPoint(-1F, 14F, 0F);
		Shape1.setTextureSize(64, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 26, 0);
		Shape2.addBox(0F, 0F, 0F, 1, 16, 1);
		Shape2.setRotationPoint(0F, -0.5F, 1F);
		Shape2.setTextureSize(64, 64);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 43);
		Shape3.addBox(0F, -1F, -1F, 7, 6, 5);
		Shape3.setRotationPoint(-3F, 0F, 0F);
		Shape3.setTextureSize(64, 64);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 0, 26);
		Shape4.addBox(0F, 0F, 0F, 2, 3, 5);
		Shape4.setRotationPoint(2.2F, -0.1F, -1F);
		Shape4.setTextureSize(64, 64);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, -0.4886922F);
		Shape5 = new ModelRenderer(this, 0, 13);
		Shape5.addBox(0F, 0F, -1F, 2, 3, 5);
		Shape5.setRotationPoint(3.7F, 1.3F, 0F);
		Shape5.setTextureSize(64, 64);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0.5054226F);
		Shape6 = new ModelRenderer(this, 17, 24);
		Shape6.addBox(0F, 0F, 0F, 1, 2, 5);
		Shape6.setRotationPoint(4.1F, 1F, -1F);
		Shape6.setTextureSize(64, 64);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 0, 0);
		Shape7.addBox(0F, 0F, 0F, 2, 3, 2);
		Shape7.setRotationPoint(-0.5F, 6.8F, 0.5F);
		Shape7.setTextureSize(64, 64);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
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
