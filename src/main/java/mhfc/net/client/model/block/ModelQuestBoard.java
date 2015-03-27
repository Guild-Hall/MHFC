package mhfc.net.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelQuestBoard extends ModelBase {
	// fields
	ModelRenderer Shape1;
	ModelRenderer mainBoard;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;

	public ModelQuestBoard() {
		textureWidth = 64;
		textureHeight = 64;

		mainBoard = new ModelRenderer(this, 0, 0);
		mainBoard.addBox(-8F, 0F, -1.5F, 16, 12, 3);
		mainBoard.setRotationPoint(0F, 10F, 0F);
		mainBoard.setTextureSize(64, 64);
		mainBoard.mirror = true;
		setRotation(mainBoard, 0F, 0F, 0F);

		Shape1 = new ModelRenderer(this, 14, 16);
		Shape1.addBox(-1.5F, 0F, 0F, 3, 4, 0);
		Shape1.setRotationPoint(6F, 16F, -1.5F);
		Shape1.setTextureSize(64, 64);
		Shape1.mirror = true;
		setRotation(Shape1, -0.0872665F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 7, 16);
		Shape3.addBox(-1.5F, 0F, 0F, 3, 4, 0);
		Shape3.setRotationPoint(1F, 11F, -1.5F);
		Shape3.setTextureSize(64, 64);
		Shape3.mirror = true;
		setRotation(Shape3, -0.0523599F, 0F, -0.0523599F);
		Shape4 = new ModelRenderer(this, 0, 16);
		Shape4.addBox(-1.5F, 0F, 0F, 3, 4, 0);
		Shape4.setRotationPoint(-6F, 12F, -1.5F);
		Shape4.setTextureSize(64, 64);
		Shape4.mirror = true;
		setRotation(Shape4, -0.0872665F, 0F, 0.0872665F);
		Shape5 = new ModelRenderer(this, 7, 16);
		Shape5.addBox(-1.5F, 0F, 0F, 3, 4, 0);
		Shape5.setRotationPoint(-4F, 15F, -1.5F);
		Shape5.setTextureSize(64, 64);
		Shape5.mirror = true;
		setRotation(Shape5, -0.122173F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 16);
		Shape2.addBox(-1.5F, 0F, 0F, 3, 4, 0);
		Shape2.setRotationPoint(3F, 13F, -1.5F);
		Shape2.setTextureSize(64, 64);
		Shape2.mirror = true;
		setRotation(Shape2, -0.1047198F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		mainBoard.render(f5);
		Shape1.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape2.render(f5);
	}

	public void renderModel(int count) {
		float f5 = 0.0325F;
		mainBoard.render(f5);

		if (count > 0)
			Shape1.render(f5);
		if (count > 1)
			Shape2.render(f5);
		if (count > 2)
			Shape5.render(f5);
		if (count > 3)
			Shape4.render(f5);
		if (count > 4)
			Shape3.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
