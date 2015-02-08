package mhfc.net.client.model.weapon.huntinghorn;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHHMetalBagpipe extends ModelBase {
	// fields
	ModelRenderer Head_Joint;
	ModelRenderer Body_Joint_Lower;
	ModelRenderer Body_Joint_Medium;
	ModelRenderer Body_Joint_Higher;
	ModelRenderer Bag;
	ModelRenderer Pipe_1;
	ModelRenderer Pipe_2;
	ModelRenderer Pipe_3;
	ModelRenderer Pipe_4;
	ModelRenderer Rope_1;
	ModelRenderer Rope_2;
	ModelRenderer Rope_3;

	public ModelHHMetalBagpipe() {
		textureWidth = 32;
		textureHeight = 32;
		Head_Joint = new ModelRenderer(this, 0, 27);
		Head_Joint.addBox(0F, 0F, 0F, 2, 3, 2);
		Head_Joint.setRotationPoint(-0.5F, 12F, -0.5F);
		Head_Joint.setTextureSize(32, 32);
		Head_Joint.mirror = true;
		setRotation(Head_Joint, 0F, 1.570796F, 0F);
		Body_Joint_Lower = new ModelRenderer(this, 8, 25);
		Body_Joint_Lower.addBox(0F, 0F, 0F, 1, 6, 1);
		Body_Joint_Lower.setRotationPoint(0F, 6F, -1F);
		Body_Joint_Lower.setTextureSize(32, 32);
		Body_Joint_Lower.mirror = true;
		setRotation(Body_Joint_Lower, 0F, 1.570796F, 0F);
		Body_Joint_Medium = new ModelRenderer(this, 12, 24);
		Body_Joint_Medium.addBox(-2F, -6F, 0F, 2, 6, 2);
		Body_Joint_Medium.setRotationPoint(-0.5F, 6F, -2.5F);
		Body_Joint_Medium.setTextureSize(32, 32);
		Body_Joint_Medium.mirror = true;
		setRotation(Body_Joint_Medium, 0F, 1.570796F, -0.0872665F);
		Body_Joint_Higher = new ModelRenderer(this, 20, 24);
		Body_Joint_Higher.addBox(-2.5F, -5F, 0F, 3, 5, 3);
		Body_Joint_Higher.setRotationPoint(-1F, 0.1F, -2F);
		Body_Joint_Higher.setTextureSize(32, 32);
		Body_Joint_Higher.mirror = true;
		setRotation(Body_Joint_Higher, 0F, 1.570796F, -0.1745329F);
		Bag = new ModelRenderer(this, 20, 15);
		Bag.addBox(-0.5F, -6F, -0.5F, 2, 5, 4);
		Bag.setRotationPoint(-1F, 0.1F, -3F);
		Bag.setTextureSize(32, 32);
		Bag.mirror = true;
		setRotation(Bag, 0F, 1.570796F, -0.1745329F);
		Pipe_1 = new ModelRenderer(this, 0, 0);
		Pipe_1.addBox(0F, -4F, 0F, 1, 4, 1);
		Pipe_1.setRotationPoint(-0.5F, -4F, -2F);
		Pipe_1.setTextureSize(32, 32);
		Pipe_1.mirror = true;
		setRotation(Pipe_1, 1.22173F, 1.570796F, 0F);
		Pipe_2 = new ModelRenderer(this, 0, 0);
		Pipe_2.addBox(0F, -4F, 0F, 1, 4, 1);
		Pipe_2.setRotationPoint(-1F, -5F, -2F);
		Pipe_2.setTextureSize(32, 32);
		Pipe_2.mirror = true;
		setRotation(Pipe_2, 0.6108652F, 1.570796F, 0F);
		Pipe_3 = new ModelRenderer(this, 0, 0);
		Pipe_3.addBox(0F, -4F, -0.5F, 1, 4, 1);
		Pipe_3.setRotationPoint(0F, -5.5F, -2F);
		Pipe_3.setTextureSize(32, 32);
		Pipe_3.mirror = true;
		setRotation(Pipe_3, 0.0872665F, 1.570796F, 0F);
		Pipe_4 = new ModelRenderer(this, 0, 0);
		Pipe_4.addBox(0F, -4F, 0F, 1, 4, 1);
		Pipe_4.setRotationPoint(0.5F, -5.5F, -2F);
		Pipe_4.setTextureSize(32, 32);
		Pipe_4.mirror = true;
		setRotation(Pipe_4, -0.296706F, 1.570796F, 0F);
		Rope_1 = new ModelRenderer(this, 0, 5);
		Rope_1.addBox(0F, -3.5F, 0F, 0, 1, 2);
		Rope_1.setRotationPoint(-1F, -4F, -2.5F);
		Rope_1.setTextureSize(32, 32);
		Rope_1.mirror = true;
		setRotation(Rope_1, 0.9599311F, 1.570796F, 0F);
		Rope_2 = new ModelRenderer(this, 0, 5);
		Rope_2.addBox(0F, -3.5F, 0F, 0, 1, 2);
		Rope_2.setRotationPoint(-1F, -5F, -2.5F);
		Rope_2.setTextureSize(32, 32);
		Rope_2.mirror = true;
		setRotation(Rope_2, 0.3316126F, 1.570796F, 0F);
		Rope_3 = new ModelRenderer(this, 0, 5);
		Rope_3.addBox(0F, -3.5F, -1F, 0, 1, 2);
		Rope_3.setRotationPoint(0F, -5.5F, -2.5F);
		Rope_3.setTextureSize(32, 32);
		Rope_3.mirror = true;
		setRotation(Rope_3, -0.3141593F, 1.570796F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		render(f5);
	}

	public void render(float f5) {
		Head_Joint.render(f5);
		Body_Joint_Lower.render(f5);
		Body_Joint_Medium.render(f5);
		Body_Joint_Higher.render(f5);
		Bag.render(f5);
		Pipe_1.render(f5);
		Pipe_2.render(f5);
		Pipe_3.render(f5);
		Pipe_4.render(f5);
		Rope_1.render(f5);
		Rope_2.render(f5);
		Rope_3.render(f5);
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
