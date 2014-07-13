package mhfc.net.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRathalosArmor extends ModelBiped {
	// fields
	ModelRenderer heada;
	ModelRenderer headb;
	ModelRenderer headc;
	ModelRenderer headd;
	ModelRenderer heade;
	ModelRenderer headf;
	ModelRenderer headg;
	ModelRenderer headh;
	ModelRenderer headi;
	ModelRenderer headj;
	ModelRenderer headk;
	ModelRenderer headl;
	ModelRenderer bodya;
	ModelRenderer bodyb;
	ModelRenderer leftarma;
	ModelRenderer leftarmb;
	ModelRenderer leftarmc;
	ModelRenderer leftarmd;
	ModelRenderer rightarma;
	ModelRenderer rightarmb;
	ModelRenderer rightarmc;
	ModelRenderer rightarmd;
	ModelRenderer rightlega;
	ModelRenderer leftlega;
	ModelRenderer rightlegb;
	ModelRenderer leftlegb;

	public ModelRathalosArmor(float f) {
		super(f, 0, 128, 128);
		textureWidth = 128;
		textureHeight = 128;
		heada = new ModelRenderer(this, 0, 37);
		heada.addBox(-4F, -8F, 4F, 8, 8, 1);
		heada.setRotationPoint(0F, 0F, 0F);
		heada.setTextureSize(128, 128);
		heada.mirror = true;
		setRotation(heada, 0F, 0F, 0F);
		headb = new ModelRenderer(this, 0, 48);
		headb.addBox(4F, -8F, -4F, 1, 8, 8);
		headb.setRotationPoint(0F, 0F, 0F);
		headb.setTextureSize(128, 128);
		headb.mirror = true;
		setRotation(headb, 0F, 0F, 0F);
		headc = new ModelRenderer(this, 0, 48);
		headc.addBox(-5F, -8F, -4F, 1, 8, 8);
		headc.setRotationPoint(0F, 0F, 0F);
		headc.setTextureSize(128, 128);
		headc.mirror = true;
		setRotation(headc, 0F, 0F, 0F);
		headd = new ModelRenderer(this, 21, 38);
		headd.addBox(-4F, -8F, -5F, 8, 8, 1);
		headd.setRotationPoint(0F, 0F, 0F);
		headd.setTextureSize(128, 128);
		headd.mirror = true;
		setRotation(headd, 0F, 0F, 0F);
		heade = new ModelRenderer(this, 20, 48);
		heade.addBox(-4F, -8F, -6F, 8, 4, 1);
		heade.setRotationPoint(0F, 0F, 0F);
		heade.setTextureSize(128, 128);
		heade.mirror = true;
		setRotation(heade, -0.0872665F, 0F, 0F);
		headf = new ModelRenderer(this, 21, 55);
		headf.addBox(-1.5F, -4.5F, -6F, 3, 1, 1);
		headf.setRotationPoint(0F, 0F, 0F);
		headf.setTextureSize(128, 128);
		headf.mirror = true;
		setRotation(headf, 0F, 0F, 0F);
		headg = new ModelRenderer(this, 20, 58);
		headg.addBox(-1F, -8.5F, 3F, 2, 1, 4);
		headg.setRotationPoint(0F, 0F, 0F);
		headg.setTextureSize(128, 128);
		headg.mirror = true;
		setRotation(headg, 0.9599311F, 0F, 0F);
		headh = new ModelRenderer(this, 0, 65);
		headh.addBox(-3F, -9F, -1F, 6, 1, 5);
		headh.setRotationPoint(0F, 0F, 0F);
		headh.setTextureSize(128, 128);
		headh.mirror = true;
		setRotation(headh, 0.3839724F, 0F, 0F);
		headi = new ModelRenderer(this, 0, 73);
		headi.addBox(5F, -3F, -3F, 1, 4, 6);
		headi.setRotationPoint(0F, 0F, 0F);
		headi.setTextureSize(128, 128);
		headi.mirror = true;
		setRotation(headi, 0F, 0F, -0.2617994F);
		headj = new ModelRenderer(this, 0, 73);
		headj.addBox(-6F, -3F, -3F, 1, 4, 6);
		headj.setRotationPoint(0F, 0F, 0F);
		headj.setTextureSize(128, 128);
		headj.mirror = true;
		setRotation(headj, 0F, 0F, 0.2617994F);
		headk = new ModelRenderer(this, 16, 73);
		headk.addBox(2.5F, -6F, -5F, 3, 2, 1);
		headk.setRotationPoint(0F, 0F, 0F);
		headk.setTextureSize(128, 128);
		headk.mirror = true;
		setRotation(headk, -0.0349066F, -0.4363323F, 0F);
		headl = new ModelRenderer(this, 16, 73);
		headl.addBox(-5.5F, -6.5F, -4F, 3, 2, 1);
		headl.setRotationPoint(0F, 0F, 0F);
		headl.setTextureSize(128, 128);
		headl.mirror = true;
		setRotation(headl, 0.0349066F, 0.4363323F, 0F);
		bodya = new ModelRenderer(this, 0, 86);
		bodya.addBox(-4F, 0F, -3F, 8, 12, 6);
		bodya.setRotationPoint(0F, 0F, 0F);
		bodya.setTextureSize(128, 128);
		bodya.mirror = true;
		setRotation(bodya, 0F, 0F, 0F);
		bodyb = new ModelRenderer(this, 16, 78);
		bodyb.addBox(-2F, 0F, -4F, 4, 3, 1);
		bodyb.setRotationPoint(0F, 0F, 0F);
		bodyb.setTextureSize(128, 128);
		bodyb.mirror = true;
		setRotation(bodyb, 0.296706F, 0F, 0F);
		leftarma = new ModelRenderer(this, 40, 39);
		leftarma.addBox(-1F, -2.4F, -3F, 5, 5, 6);
		leftarma.setRotationPoint(0F, 0F, 0F);
		leftarma.setTextureSize(128, 128);
		leftarma.mirror = true;
		setRotation(leftarma, 0F, 0F, 0F);
		leftarmb = new ModelRenderer(this, 36, 54);
		leftarmb.addBox(2F, -5F, -0.5F, 2, 4, 1);
		leftarmb.setRotationPoint(0F, 0F, 0F);
		leftarmb.setTextureSize(128, 128);
		leftarmb.mirror = true;
		setRotation(leftarmb, 0F, 0F, 0.2094395F);
		leftarmc = new ModelRenderer(this, 36, 54);
		leftarmc.addBox(1F, -4F, -3F, 2, 4, 1);
		leftarmc.setRotationPoint(0F, 0F, 0F);
		leftarmc.setTextureSize(128, 128);
		leftarmc.mirror = true;
		setRotation(leftarmc, 0.3907885F, 0F, 0F);
		leftarmd = new ModelRenderer(this, 36, 54);
		leftarmd.addBox(1F, -4.5F, 1.5F, 2, 4, 1);
		leftarmd.setRotationPoint(0F, 0F, 0F);
		leftarmd.setTextureSize(128, 128);
		leftarmd.mirror = true;
		setRotation(leftarmd, -0.4461433F, 0F, 0F);
		rightarma = new ModelRenderer(this, 40, 39);
		rightarma.addBox(-4F, -2.4F, -3F, 5, 5, 6);
		rightarma.setRotationPoint(0F, 0F, 0F);
		rightarma.setTextureSize(128, 128);
		rightarma.mirror = true;
		setRotation(rightarma, 0F, 0F, 0F);
		rightarmb = new ModelRenderer(this, 36, 54);
		rightarmb.addBox(-4F, -5F, 0F, 2, 4, 1);
		rightarmb.setRotationPoint(0F, 0F, 0F);
		rightarmb.setTextureSize(128, 128);
		rightarmb.mirror = true;
		setRotation(rightarmb, 0F, 0F, -0.2094395F);
		rightarmc = new ModelRenderer(this, 36, 54);
		rightarmc.addBox(-3F, -4F, -3F, 2, 4, 1);
		rightarmc.setRotationPoint(0F, 0F, 0F);
		rightarmc.setTextureSize(128, 128);
		rightarmc.mirror = true;
		setRotation(rightarmc, 0.3907885F, 0F, 0F);
		rightarmd = new ModelRenderer(this, 36, 54);
		rightarmd.addBox(-3F, -5F, 1.5F, 2, 4, 1);
		rightarmd.setRotationPoint(0F, 0F, 0F);
		rightarmd.setTextureSize(128, 128);
		rightarmd.mirror = true;
		setRotation(rightarmd, -0.4461433F, 0F, 0F);
		rightlega = new ModelRenderer(this, 0, 106);
		rightlega.addBox(-3F, 4F, -3F, 5, 8, 6);
		rightlega.setRotationPoint(0F, 0F, 0F);
		rightlega.setTextureSize(128, 128);
		rightlega.mirror = true;
		setRotation(rightlega, 0F, 0F, 0F);
		leftlega = new ModelRenderer(this, 24, 106);
		leftlega.addBox(-2F, 4F, -3F, 5, 8, 6);
		leftlega.setRotationPoint(0F, 0F, 0F);
		leftlega.setTextureSize(128, 128);
		leftlega.mirror = true;
		setRotation(leftlega, 0F, 0F, 0F);
		rightlegb = new ModelRenderer(this, 34, 70);
		rightlegb.addBox(-2.5F, 1F, -4.5F, 4, 7, 1);
		rightlegb.setRotationPoint(0F, 0F, 0F);
		rightlegb.setTextureSize(128, 128);
		rightlegb.mirror = true;
		setRotation(rightlegb, 0.1396263F, 0F, 0F);
		leftlegb = new ModelRenderer(this, 34, 70);
		leftlegb.addBox(-1.5F, 1F, -4.5F, 4, 7, 1);
		leftlegb.setRotationPoint(0F, 0F, 0F);
		leftlegb.setTextureSize(128, 128);
		leftlegb.mirror = true;
		setRotation(leftlegb, 0.1396263F, 0F, 0F);

		this.bipedHead.addChild(heada);
		this.bipedHead.addChild(headb);
		this.bipedHead.addChild(headc);
		this.bipedHead.addChild(headd);
		this.bipedHead.addChild(heade);
		this.bipedHead.addChild(headf);
		this.bipedHead.addChild(headg);
		this.bipedHead.addChild(headh);
		this.bipedHead.addChild(headi);
		this.bipedHead.addChild(headj);
		this.bipedHead.addChild(headk);
		this.bipedHead.addChild(headl);
		this.bipedBody.addChild(bodya);
		this.bipedBody.addChild(bodyb);
		this.bipedLeftArm.addChild(leftarma);
		this.bipedLeftArm.addChild(leftarmb);
		this.bipedLeftArm.addChild(leftarmc);
		this.bipedLeftArm.addChild(leftarmd);
		this.bipedRightArm.addChild(rightarma);
		this.bipedRightArm.addChild(rightarmb);
		this.bipedRightArm.addChild(rightarmc);
		this.bipedRightArm.addChild(rightarmd);
		this.bipedLeftLeg.addChild(leftlega);
		this.bipedLeftLeg.addChild(leftlegb);
		this.bipedRightLeg.addChild(rightlega);
		this.bipedRightLeg.addChild(rightlegb);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);

	}

	private static void setRotation(ModelRenderer model, float x, float y,
			float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity param) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, param);
	}

}
