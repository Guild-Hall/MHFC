package mhfc.net.client.model.weapon.longsword;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLSIronKatana extends ModelBase {
	// fields
	ModelRenderer CenterPit;
	ModelRenderer UpperPit;
	ModelRenderer LowerPit;
	ModelRenderer Bars;
	ModelRenderer Craig;
	ModelRenderer Zanku;
	ModelRenderer Taiku;
	ModelRenderer Neru;

	public ModelLSIronKatana() {
		textureWidth = 32;
		textureHeight = 32;

		CenterPit = new ModelRenderer(this, 0, 24);
		CenterPit.addBox(-0.5F, 0F, -0.5F, 2, 2, 2);
		CenterPit.setRotationPoint(0.5F, 16F, -0.5F);
		CenterPit.setTextureSize(32, 32);
		CenterPit.mirror = true;
		setRotation(CenterPit, 0F, 1.570796F, 0F);
		UpperPit = new ModelRenderer(this, 0, 20);
		UpperPit.addBox(-0.5F, 0F, -0.5F, 2, 2, 2);
		UpperPit.setRotationPoint(0.5F, 12F, -0.5F);
		UpperPit.setTextureSize(32, 32);
		UpperPit.mirror = true;
		setRotation(UpperPit, 0F, 1.570796F, 0F);
		LowerPit = new ModelRenderer(this, 0, 28);
		LowerPit.addBox(-0.5F, 0F, -0.5F, 2, 2, 2);
		LowerPit.setRotationPoint(0.5F, 20F, -0.5F);
		LowerPit.setTextureSize(32, 32);
		LowerPit.mirror = true;
		setRotation(LowerPit, 0F, 1.570796F, 0F);
		Bars = new ModelRenderer(this, 8, 21);
		Bars.addBox(0F, 0F, 0F, 1, 10, 1);
		Bars.setRotationPoint(0.5F, 10F, -0.5F);
		Bars.setTextureSize(32, 32);
		Bars.mirror = true;
		setRotation(Bars, 0F, 1.570796F, 0F);
		Craig = new ModelRenderer(this, 0, 0);
		Craig.addBox(-0.5F, 0F, -1F, 2, 1, 3);
		Craig.setRotationPoint(0.5F, 9F, -0.5F);
		Craig.setTextureSize(32, 32);
		Craig.mirror = true;
		setRotation(Craig, 0F, 1.570796F, 0F);
		Zanku = new ModelRenderer(this, 14, 20);
		Zanku.addBox(0F, -10F, 0F, 1, 10, 2);
		Zanku.setRotationPoint(0.3F, -16F, -0.5F);
		Zanku.setTextureSize(32, 32);
		Zanku.mirror = true;
		setRotation(Zanku, -0.0698132F, 1.570796F, 0F);
		Taiku = new ModelRenderer(this, 20, 20);
		Taiku.addBox(0F, -10F, 0F, 1, 10, 2);
		Taiku.setRotationPoint(0F, -6F, -0.5F);
		Taiku.setTextureSize(32, 32);
		Taiku.mirror = true;
		setRotation(Taiku, -0.0349066F, 1.570796F, 0F);
		Neru = new ModelRenderer(this, 26, 15);
		Neru.addBox(0F, 0F, -0.5F, 1, 15, 2);
		Neru.setRotationPoint(0.5F, -6F, -0.5F);
		Neru.setTextureSize(32, 32);
		Neru.mirror = true;
		setRotation(Neru, 0F, 1.570796F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		render(f5);
	}

	public void render(float f5) {
		CenterPit.renderWithRotation(f5);
		UpperPit.renderWithRotation(f5);
		LowerPit.renderWithRotation(f5);
		Bars.renderWithRotation(f5);
		Craig.renderWithRotation(f5);
		Zanku.renderWithRotation(f5);
		Taiku.renderWithRotation(f5);
		Neru.renderWithRotation(f5);
	}

	private static void setRotation(ModelRenderer model, float x, float y,
			float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
