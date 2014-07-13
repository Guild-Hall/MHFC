package mhfc.net.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHunterBench extends ModelBase {
	// fields
	ModelRenderer CraftingBody;
	ModelRenderer SquareA;
	ModelRenderer SquareB;
	ModelRenderer SquareC;
	ModelRenderer SquareD;
	ModelRenderer Plate;
	ModelRenderer TipA;
	ModelRenderer TipB;
	ModelRenderer TipC;
	ModelRenderer TipD;
	ModelRenderer CoreA;
	ModelRenderer CoreB;

	public ModelHunterBench() {
		textureWidth = 128;
		textureHeight = 128;

		CraftingBody = new ModelRenderer(this, 0, 68);
		CraftingBody.addBox(0F, 0F, 0F, 30, 30, 30);
		CraftingBody.setRotationPoint(-15F, -6F, -15F);
		CraftingBody.setTextureSize(128, 128);
		CraftingBody.mirror = true;
		setRotation(CraftingBody, 0F, 0F, 0F);
		SquareA = new ModelRenderer(this, 0, 21);
		SquareA.addBox(-3.5F, 0F, -3.5F, 7, 3, 7);
		SquareA.setRotationPoint(-11F, -9F, -11F);
		SquareA.setTextureSize(128, 128);
		SquareA.mirror = true;
		setRotation(SquareA, 0F, 0F, 0F);
		SquareB = new ModelRenderer(this, 0, 21);
		SquareB.addBox(-3.5F, -9F, -3.5F, 7, 3, 7);
		SquareB.setRotationPoint(11F, 0F, -11F);
		SquareB.setTextureSize(128, 128);
		SquareB.mirror = true;
		setRotation(SquareB, 0F, 0F, 0F);
		SquareC = new ModelRenderer(this, 0, 21);
		SquareC.addBox(-3.5F, 0F, -3.5F, 7, 3, 7);
		SquareC.setRotationPoint(11F, -9F, 11F);
		SquareC.setTextureSize(128, 128);
		SquareC.mirror = true;
		setRotation(SquareC, 0F, 0F, 0F);
		SquareD = new ModelRenderer(this, 0, 21);
		SquareD.addBox(-3.5F, 0F, -3.5F, 7, 3, 7);
		SquareD.setRotationPoint(-11F, -9F, 11F);
		SquareD.setTextureSize(128, 128);
		SquareD.mirror = true;
		setRotation(SquareD, 0F, 0F, 0F);
		Plate = new ModelRenderer(this, 50, 14);
		Plate.addBox(0F, 0F, 0F, 16, 1, 16);
		Plate.setRotationPoint(-8F, -7F, -8F);
		Plate.setTextureSize(128, 128);
		Plate.mirror = true;
		setRotation(Plate, 0F, 0F, 0F);
		TipA = new ModelRenderer(this, 0, 0);
		TipA.addBox(-1.5F, -9F, -1.5F, 3, 9, 3);
		TipA.setRotationPoint(11F, -8F, -11F);
		TipA.setTextureSize(128, 128);
		TipA.mirror = true;
		setRotation(TipA, -0.3490659F, 0F, -0.3490659F);
		TipB = new ModelRenderer(this, 0, 0);
		TipB.addBox(-1.5F, -9F, -1.5F, 3, 9, 3);
		TipB.setRotationPoint(11F, -8F, 11F);
		TipB.setTextureSize(128, 128);
		TipB.mirror = true;
		setRotation(TipB, 0.3490659F, 0F, -0.3490659F);
		TipC = new ModelRenderer(this, 0, 0);
		TipC.addBox(-1.5F, -9F, -1.5F, 3, 9, 3);
		TipC.setRotationPoint(-11F, -8F, 11F);
		TipC.setTextureSize(128, 128);
		TipC.mirror = true;
		setRotation(TipC, 0.3490659F, 0F, 0.3490659F);
		TipD = new ModelRenderer(this, 0, 0);
		TipD.addBox(-1.5F, -9F, -1.5F, 3, 9, 3);
		TipD.setRotationPoint(-11F, -8F, -11F);
		TipD.setTextureSize(128, 128);
		TipD.mirror = true;
		setRotation(TipD, -0.3490659F, 0F, 0.3490659F);
		CoreA = new ModelRenderer(this, 0, 33);
		CoreA.addBox(0F, 0F, 0F, 31, 2, 31);
		CoreA.setRotationPoint(-15.5F, -3F, -15.5F);
		CoreA.setTextureSize(128, 128);
		CoreA.mirror = true;
		setRotation(CoreA, 0F, 0F, 0F);
		CoreB = new ModelRenderer(this, 0, 33);
		CoreB.addBox(0F, 0F, 0F, 31, 2, 31);
		CoreB.setRotationPoint(-15.5F, 19F, -15.5F);
		CoreB.setTextureSize(128, 128);
		CoreB.mirror = true;
		setRotation(CoreB, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		CraftingBody.render(f5);
		SquareA.render(f5);
		SquareB.render(f5);
		SquareC.render(f5);
		SquareD.render(f5);
		Plate.render(f5);
		TipA.render(f5);
		TipB.render(f5);
		TipC.render(f5);
		TipD.render(f5);
		CoreA.render(f5);
		CoreB.render(f5);
	}

	private static void setRotation(ModelRenderer model, float x, float y,
			float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	public void renderModel(float f) {
		CraftingBody.render(f);
		SquareA.render(f);
		SquareB.render(f);
		SquareC.render(f);
		SquareD.render(f);
		Plate.render(f);
		TipA.render(f);
		TipB.render(f);
		TipC.render(f);
		TipD.render(f);
		CoreA.render(f);
		CoreB.render(f);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}

}
