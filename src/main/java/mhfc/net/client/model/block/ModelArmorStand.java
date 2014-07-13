package mhfc.net.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelArmorStand extends ModelBase {
	ModelRenderer Base;
	ModelRenderer Stand;

	public ModelArmorStand() {
		textureWidth = 128;
		textureHeight = 64;
		Base = new ModelRenderer(this, 0, 0);
		Base.addBox(-8F, -1F, -8F, 16, 3, 16);
		Base.setRotationPoint(0F, 22F, 0F);
		Base.setTextureSize(128, 64);
		Base.mirror = true;
		setRotation(Base, 0F, 0F, 0F);
		Stand = new ModelRenderer(this, 0, 23);
		Stand.addBox(0F, 0F, 0F, 1, 25, 1);
		Stand.setRotationPoint(-0.5F, -3F, -0.5F);
		Stand.setTextureSize(128, 64);
		Stand.mirror = true;
		setRotation(Stand, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Base.render(f5);
		Stand.render(f5);
	}

	public void renderModel(float f) {
		Base.render(f);
		Stand.render(f);
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
