package mhfc.net.client.model.projectile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRathalosFireball extends ModelBase {
	// fields
	ModelRenderer Fireball_detail;
	ModelRenderer Fireball_Body;

	public ModelRathalosFireball() {
		textureWidth = 64;
		textureHeight = 64;
		Fireball_detail = new ModelRenderer(this, 0, 9);
		Fireball_detail.addBox(-2F, -2F, -2F, 4, 4, 4);
		Fireball_detail.setRotationPoint(0F, 0F, 0F);
		Fireball_detail.setTextureSize(64, 64);
		Fireball_detail.mirror = true;
		setRotation(Fireball_detail, 0.7853982F, 0.7853982F, 0.7853982F);
		Fireball_Body = new ModelRenderer(this, 0, 0);
		Fireball_Body.addBox(-2F, -2F, -2F, 4, 4, 4);
		Fireball_Body.setRotationPoint(0F, 0F, 0F);
		Fireball_Body.setTextureSize(64, 64);
		Fireball_Body.mirror = true;
		setRotation(Fireball_Body, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Fireball_detail.render(f5);
		Fireball_Body.render(f5);
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
