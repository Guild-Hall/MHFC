package mhfc.net.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelKirinSArmor extends ModelBiped {
	ModelRenderer headleftear;
	ModelRenderer headrightear;
	ModelRenderer headhair;
	ModelRenderer headcore;
	ModelRenderer mohawk;
	ModelRenderer mohawkend;
	ModelRenderer rightcore;
	ModelRenderer leftcore;
	ModelRenderer breap;
	ModelRenderer kirinbody;
	ModelRenderer rightarm;
	ModelRenderer leftarm;
	ModelRenderer rightleg;
	ModelRenderer leftleg;
	ModelRenderer skull;
	ModelRenderer skulltip;
	ModelRenderer horn;
	ModelRenderer leftbcore;
	ModelRenderer rightbcore;

	public ModelKirinSArmor(float f) {
		super(f, 0, 128, 128);
		textureWidth = 128;
		textureHeight = 128;
		headleftear = new ModelRenderer(this, 6, 62);
		headleftear.addBox(-2F, -9.5F, -8F, 1, 2, 1);
		headleftear.setRotationPoint(0F, 0F, 0F);
		headleftear.setTextureSize(128, 128);
		headleftear.mirror = true;
		setRotation(headleftear, -0.2617994F, -0.2617994F, 0.2617994F);
		headrightear = new ModelRenderer(this, 6, 62);
		headrightear.addBox(1F, -9.5F, -8F, 1, 2, 1);
		headrightear.setRotationPoint(0F, 0F, 0F);
		headrightear.setTextureSize(128, 128);
		headrightear.mirror = true;
		setRotation(headrightear, -0.2617994F, 0.2617994F, -0.2617994F);
		headhair = new ModelRenderer(this, 0, 71);
		headhair.addBox(-4F, -10F, -2F, 8, 2, 6);
		headhair.setRotationPoint(0F, 0F, 0F);
		headhair.setTextureSize(128, 128);
		headhair.mirror = true;
		setRotation(headhair, 0F, 0F, 0F);
		headcore = new ModelRenderer(this, 0, 80);
		headcore.addBox(-1.5F, -10F, -4F, 3, 2, 2);
		headcore.setRotationPoint(0F, 0F, 0F);
		headcore.setTextureSize(128, 128);
		headcore.mirror = true;
		setRotation(headcore, 0F, 0F, 0F);
		mohawk = new ModelRenderer(this, 0, 86);
		mohawk.addBox(-1F, -15F, -3F, 2, 6, 10);
		mohawk.setRotationPoint(0F, 0F, 0F);
		mohawk.setTextureSize(128, 128);
		mohawk.mirror = true;
		setRotation(mohawk, 0F, 0F, 0F);
		mohawkend = new ModelRenderer(this, 0, 104);
		mohawkend.addBox(-1F, -9F, 3F, 2, 6, 4);
		mohawkend.setRotationPoint(0F, 0F, 0F);
		mohawkend.setTextureSize(128, 128);
		mohawkend.mirror = true;
		setRotation(mohawkend, 0F, 0F, 0F);
		rightcore = new ModelRenderer(this, 18, 104);
		rightcore.addBox(-2F, -5F, -2F, 1, 7, 7);
		rightcore.setRotationPoint(0F, 0F, 0F);
		rightcore.setTextureSize(128, 128);
		rightcore.mirror = true;
		setRotation(rightcore, 0.7853982F, 0F, 0F);
		leftcore = new ModelRenderer(this, 18, 104);
		leftcore.addBox(1F, -5F, -2F, 1, 7, 7);
		leftcore.setRotationPoint(0F, 0F, 0F);
		leftcore.setTextureSize(128, 128);
		leftcore.mirror = true;
		setRotation(leftcore, 0.7853982F, 0F, 0F);
		breap = new ModelRenderer(this, 18, 57);
		breap.addBox(-1.5F, 8F, -3.6F, 3, 6, 1);
		breap.setRotationPoint(0F, 0F, 0F);
		breap.setTextureSize(128, 128);
		breap.mirror = true;
		setRotation(breap, 0F, 0F, 0F);
		kirinbody = new ModelRenderer(this, 32, 68);
		kirinbody.addBox(-4F, 0F, -3F, 8, 12, 6);
		kirinbody.setRotationPoint(0F, 0F, 0F);
		kirinbody.setTextureSize(128, 128);
		kirinbody.mirror = true;
		setRotation(kirinbody, 0F, 0F, 0F);
		rightarm = new ModelRenderer(this, 40, 54);
		rightarm.addBox(-4F, -2.4F, -3F, 5, 6, 6);
		rightarm.setRotationPoint(0F, 0F, 0F);
		rightarm.setTextureSize(128, 128);
		rightarm.mirror = true;
		setRotation(rightarm, 0F, 0F, 0F);
		leftarm = new ModelRenderer(this, 40, 54);
		leftarm.addBox(-1F, -2.4F, -3F, 5, 6, 6);
		leftarm.setRotationPoint(0F, 0F, 0F);
		leftarm.setTextureSize(128, 128);
		leftarm.mirror = true;
		setRotation(leftarm, 0F, 0F, 0F);
		rightleg = new ModelRenderer(this, 39, 90);
		rightleg.addBox(-3F, 6F, -2.5F, 5, 6, 5);
		rightleg.setRotationPoint(0F, 0F, 0F);
		rightleg.setTextureSize(128, 128);
		rightleg.mirror = true;
		setRotation(rightleg, 0F, 0F, 0F);
		leftleg = new ModelRenderer(this, 39, 90);
		leftleg.addBox(-2F, 6F, -2.5F, 5, 6, 5);
		leftleg.setRotationPoint(0F, 0F, 0F);
		leftleg.setTextureSize(128, 128);
		leftleg.mirror = true;
		setRotation(leftleg, 0F, 0F, 0F);
		skull = new ModelRenderer(this, 0, 43);
		skull.addBox(-2F, -10.5F, -1F, 4, 2, 4);
		skull.setRotationPoint(0F, 0F, 0F);
		skull.setTextureSize(128, 128);
		skull.mirror = true;
		setRotation(skull, 0.7853982F, 0F, 0F);
		skulltip = new ModelRenderer(this, 0, 51);
		skulltip.addBox(-1F, -10.5F, -3F, 2, 2, 2);
		skulltip.setRotationPoint(0F, 0F, 0F);
		skulltip.setTextureSize(128, 128);
		skulltip.mirror = true;
		setRotation(skulltip, 0.7853982F, 0F, 0F);
		horn = new ModelRenderer(this, 0, 61);
		horn.addBox(-0.5F, -15.5F, 0.5F, 1, 5, 1);
		horn.setRotationPoint(0F, 0F, 0F);
		horn.setTextureSize(128, 128);
		horn.mirror = true;
		setRotation(horn, 0.7853982F, 0F, 0F);
		leftbcore = new ModelRenderer(this, 62, 71);
		leftbcore.addBox(-1F, 1F, -4F, 2, 2, 8);
		leftbcore.setRotationPoint(0F, 0F, 0F);
		leftbcore.setTextureSize(128, 128);
		leftbcore.mirror = true;
		setRotation(leftbcore, 0F, 0F, -0.837758F);
		rightbcore = new ModelRenderer(this, 67, 85);
		rightbcore.addBox(-1F, -1F, -5F, 3, 3, 5);
		rightbcore.setRotationPoint(0F, 0F, 0F);
		rightbcore.setTextureSize(128, 128);
		rightbcore.mirror = true;
		setRotation(rightbcore, 0F, 1.561502F, 0.7458443F);

		bipedHead.addChild(headcore);
		bipedHead.addChild(skull);
		bipedHead.addChild(skulltip);
		bipedHead.addChild(headhair);
		bipedHead.addChild(horn);
		bipedHead.addChild(headleftear);
		bipedHead.addChild(headrightear);
		bipedHead.addChild(mohawk);
		bipedHead.addChild(mohawkend);
		bipedBody.addChild(kirinbody);
		bipedBody.addChild(breap);
		bipedLeftArm.addChild(leftarm);
		bipedLeftArm.addChild(leftcore);
		bipedRightArm.addChild(rightarm);
		bipedRightArm.addChild(rightcore);
		bipedLeftLeg.addChild(leftleg);
		bipedRightLeg.addChild(rightleg);
		bipedLeftArm.addChild(leftbcore);
		bipedRightArm.addChild(rightbcore);
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
			float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}

}
