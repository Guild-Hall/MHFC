package mhfc.net.client.model.weapon.greatsword;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGSKirinThunderSword extends ModelBase {
	// fields
	ModelRenderer spike_left_1;
	ModelRenderer spike_left_2;
	ModelRenderer spike_left_3;
	ModelRenderer spike_left_4;
	ModelRenderer spike_right_1;
	ModelRenderer spike_right_2;
	ModelRenderer spike_right_3;
	ModelRenderer spike_support_left_1;
	ModelRenderer spike_support_left_2;
	ModelRenderer spike_support_left_3;
	ModelRenderer spike_support_left_4;
	ModelRenderer spike_support_right_1;
	ModelRenderer spike_support_right_2;
	ModelRenderer spike_support_right_3;
	ModelRenderer body_higher;
	ModelRenderer body_middle;
	ModelRenderer body_lower;
	ModelRenderer handle_higher;
	ModelRenderer handle_middle;
	ModelRenderer handle_lower;

	public ModelGSKirinThunderSword() {
		textureWidth = 32;
		textureHeight = 32;

		spike_left_1 = new ModelRenderer(this, 8, 0);
		spike_left_1.addBox(0F, 0F, 0F, 1, 3, 1);
		spike_left_1.setRotationPoint(-1.5F, -17.4F, -0.5F);
		spike_left_1.setTextureSize(32, 32);
		spike_left_1.mirror = true;
		setRotation(spike_left_1, 0F, 1.570796F, 0F);
		spike_left_2 = new ModelRenderer(this, 4, 0);
		spike_left_2.addBox(0F, 0F, 0F, 1, 4, 1);
		spike_left_2.setRotationPoint(-1.5F, -13.4F, -0.5F);
		spike_left_2.setTextureSize(32, 32);
		spike_left_2.mirror = true;
		setRotation(spike_left_2, 0F, 1.570796F, 0F);
		spike_left_3 = new ModelRenderer(this, 4, 0);
		spike_left_3.addBox(0F, 0F, 0F, 1, 4, 1);
		spike_left_3.setRotationPoint(-2.5F, -8.4F, -0.5F);
		spike_left_3.setTextureSize(32, 32);
		spike_left_3.mirror = true;
		setRotation(spike_left_3, 0F, 1.570796F, 0F);
		spike_left_4 = new ModelRenderer(this, 4, 0);
		spike_left_4.addBox(0F, 0F, 0F, 1, 4, 1);
		spike_left_4.setRotationPoint(-2.5F, -3.4F, -0.5F);
		spike_left_4.setTextureSize(32, 32);
		spike_left_4.mirror = true;
		setRotation(spike_left_4, 0F, 1.570796F, 0F);
		spike_right_1 = new ModelRenderer(this, 4, 0);
		spike_right_1.addBox(0F, 0F, 0F, 1, 4, 1);
		spike_right_1.setRotationPoint(2.5F, -16.4F, -0.5F);
		spike_right_1.setTextureSize(32, 32);
		spike_right_1.mirror = true;
		setRotation(spike_right_1, 0F, 1.570796F, 0F);
		spike_right_2 = new ModelRenderer(this, 4, 0);
		spike_right_2.addBox(0F, 0F, 0F, 1, 4, 1);
		spike_right_2.setRotationPoint(3.5F, -11.4F, -0.5F);
		spike_right_2.setTextureSize(32, 32);
		spike_right_2.mirror = true;
		setRotation(spike_right_2, 0F, 1.570796F, 0F);
		spike_right_3 = new ModelRenderer(this, 4, 0);
		spike_right_3.addBox(0F, 0F, 0F, 1, 4, 1);
		spike_right_3.setRotationPoint(3.5F, -5.4F, -1.5F);
		spike_right_3.setTextureSize(32, 32);
		spike_right_3.mirror = true;
		setRotation(spike_right_3, 0F, 0F, 0F);
		spike_support_left_1 = new ModelRenderer(this, 20, 0);
		spike_support_left_1.addBox(0F, 0F, -2F, 1, 1, 2);
		spike_support_left_1.setRotationPoint(0.7F, -15F, -0.5F);
		spike_support_left_1.setTextureSize(32, 32);
		spike_support_left_1.mirror = true;
		setRotation(spike_support_left_1, -0.1745329F, 1.570796F, 0F);
		spike_support_left_2 = new ModelRenderer(this, 12, 0);
		spike_support_left_2.addBox(0F, 0F, -2F, 1, 1, 3);
		spike_support_left_2.setRotationPoint(0.5F, -10F, -0.5F);
		spike_support_left_2.setTextureSize(32, 32);
		spike_support_left_2.mirror = true;
		setRotation(spike_support_left_2, -0.1745329F, 1.570796F, 0F);
		spike_support_left_3 = new ModelRenderer(this, 8, 4);
		spike_support_left_3.addBox(0F, 0F, -2F, 2, 2, 2);
		spike_support_left_3.setRotationPoint(0F, -6F, 0F);
		spike_support_left_3.setTextureSize(32, 32);
		spike_support_left_3.mirror = true;
		setRotation(spike_support_left_3, -0.1745329F, 1.570796F, 0F);
		spike_support_left_4 = new ModelRenderer(this, 8, 4);
		spike_support_left_4.addBox(0F, 0F, -2F, 2, 2, 2);
		spike_support_left_4.setRotationPoint(0F, -1F, 0F);
		spike_support_left_4.setTextureSize(32, 32);
		spike_support_left_4.mirror = true;
		setRotation(spike_support_left_4, -0.1745329F, 1.570796F, 0F);
		spike_support_right_1 = new ModelRenderer(this, 20, 0);
		spike_support_right_1.addBox(0F, 0F, 0F, 1, 1, 2);
		spike_support_right_1.setRotationPoint(1.2F, -13F, -0.5F);
		spike_support_right_1.setTextureSize(32, 32);
		spike_support_right_1.mirror = true;
		setRotation(spike_support_right_1, 0.1745329F, 1.570796F, 0F);
		spike_support_right_2 = new ModelRenderer(this, 12, 0);
		spike_support_right_2.addBox(0F, 0F, -1F, 1, 1, 3);
		spike_support_right_2.setRotationPoint(1.5F, -8F, -0.5F);
		spike_support_right_2.setTextureSize(32, 32);
		spike_support_right_2.mirror = true;
		setRotation(spike_support_right_2, 0.1745329F, 1.570796F, 0F);
		spike_support_right_3 = new ModelRenderer(this, 8, 4);
		spike_support_right_3.addBox(0F, 0F, 0F, 2, 2, 2);
		spike_support_right_3.setRotationPoint(2F, -3F, 0F);
		spike_support_right_3.setTextureSize(32, 32);
		spike_support_right_3.mirror = true;
		setRotation(spike_support_right_3, 0.1745329F, 1.570796F, 0F);
		body_higher = new ModelRenderer(this, 8, 8);
		body_higher.addBox(0F, 0F, 0F, 1, 6, 1);
		body_higher.setRotationPoint(0.5F, -18F, -0.5F);
		body_higher.setTextureSize(32, 32);
		body_higher.mirror = true;
		setRotation(body_higher, 0F, 1.570796F, 0F);
		body_middle = new ModelRenderer(this, 0, 7);
		body_middle.addBox(0F, 0F, 0F, 2, 6, 2);
		body_middle.setRotationPoint(0F, -12F, 0F);
		body_middle.setTextureSize(32, 32);
		body_middle.mirror = true;
		setRotation(body_middle, 0F, 1.570796F, 0F);
		body_lower = new ModelRenderer(this, 0, 15);
		body_lower.addBox(0F, 0F, 0F, 3, 8, 3);
		body_lower.setRotationPoint(-0.5F, -6F, 0.5F);
		body_lower.setTextureSize(32, 32);
		body_lower.mirror = true;
		setRotation(body_lower, 0F, 1.570796F, 0F);
		handle_higher = new ModelRenderer(this, 8, 26);
		handle_higher.addBox(0F, 0F, 0F, 2, 4, 2);
		handle_higher.setRotationPoint(0F, 2F, 0F);
		handle_higher.setTextureSize(32, 32);
		handle_higher.mirror = true;
		setRotation(handle_higher, 0F, 1.570796F, 0F);
		handle_middle = new ModelRenderer(this, 16, 27);
		handle_middle.addBox(0F, 0F, 0F, 1, 4, 1);
		handle_middle.setRotationPoint(0.5F, 6F, -0.5F);
		handle_middle.setTextureSize(32, 32);
		handle_middle.mirror = true;
		setRotation(handle_middle, 0F, 1.570796F, 0F);
		handle_lower = new ModelRenderer(this, 0, 26);
		handle_lower.addBox(0F, 0F, 0F, 2, 4, 2);
		handle_lower.setRotationPoint(0F, 10F, 0F);
		handle_lower.setTextureSize(32, 32);
		handle_lower.mirror = true;
		setRotation(handle_lower, 0F, 1.570796F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		render(f5);

	}

	public void render(float f5) {
		spike_left_1.renderWithRotation(f5);
		spike_left_2.renderWithRotation(f5);
		spike_left_3.renderWithRotation(f5);
		spike_left_4.renderWithRotation(f5);
		spike_right_1.renderWithRotation(f5);
		spike_right_2.renderWithRotation(f5);
		spike_right_3.renderWithRotation(f5);
		spike_support_left_1.renderWithRotation(f5);
		spike_support_left_2.renderWithRotation(f5);
		spike_support_left_3.renderWithRotation(f5);
		spike_support_left_4.renderWithRotation(f5);
		spike_support_right_1.renderWithRotation(f5);
		spike_support_right_2.renderWithRotation(f5);
		spike_support_right_3.renderWithRotation(f5);
		body_higher.renderWithRotation(f5);
		body_middle.renderWithRotation(f5);
		body_lower.renderWithRotation(f5);
		handle_higher.renderWithRotation(f5);
		handle_middle.renderWithRotation(f5);
		handle_lower.renderWithRotation(f5);
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
