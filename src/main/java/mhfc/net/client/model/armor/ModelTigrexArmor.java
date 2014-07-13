package mhfc.net.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTigrexArmor extends ModelBiped {
	// fields
	ModelRenderer rightheada;
	ModelRenderer leftheada;
	ModelRenderer rightheadb;
	ModelRenderer leftheadb;
	ModelRenderer leftshould;
	ModelRenderer leftshouldpin;
	ModelRenderer body;
	ModelRenderer rightarm;
	ModelRenderer leftarm;
	ModelRenderer leftfoot;
	ModelRenderer rightfoot;
	ModelRenderer helmtip;
	ModelRenderer helmpoint;
	ModelRenderer upperhead;
	ModelRenderer rightshould;
	ModelRenderer rightshouldpin;

	public ModelTigrexArmor(float f) {
		super(f, 0, 128, 128);
		textureWidth = 128;
		textureHeight = 128;

		rightheada = new ModelRenderer(this, 0, 110);
		rightheada.addBox(-5F, -8F, -4F, 1, 2, 6);
		rightheada.setRotationPoint(0F, 0F, 0F);
		rightheada.setTextureSize(128, 128);
		rightheada.mirror = true;
		setRotation(rightheada, 0F, 0F, 0F);
		leftheada = new ModelRenderer(this, 0, 110);
		leftheada.addBox(4F, -8F, -4F, 1, 2, 6);
		leftheada.setRotationPoint(0F, 0F, 0F);
		leftheada.setTextureSize(128, 128);
		leftheada.mirror = true;
		setRotation(leftheada, 0F, 0F, 0F);
		rightheadb = new ModelRenderer(this, 0, 41);
		rightheadb.addBox(-5F, -10F, -4F, 1, 9, 2);
		rightheadb.setRotationPoint(0F, 0F, 0F);
		rightheadb.setTextureSize(128, 128);
		rightheadb.mirror = true;
		setRotation(rightheadb, -0.3717861F, 0F, 0F);
		leftheadb = new ModelRenderer(this, 0, 41);
		leftheadb.addBox(4F, -10F, -4F, 1, 9, 2);
		leftheadb.setRotationPoint(0F, 0F, 0F);
		leftheadb.setTextureSize(128, 128);
		leftheadb.mirror = true;
		setRotation(leftheadb, -0.37179F, 0F, 0F);
		leftshould = new ModelRenderer(this, 29, 73);
		leftshould.addBox(0.5F, -4F, -1F, 3, 2, 2);
		leftshould.setRotationPoint(0F, 0F, 0F);
		leftshould.setTextureSize(128, 128);
		leftshould.mirror = true;
		setRotation(leftshould, 0F, 0F, 0.2974216F);
		leftshouldpin = new ModelRenderer(this, 29, 78);
		leftshouldpin.addBox(1F, -6F, -0.5F, 2, 3, 1);
		leftshouldpin.setRotationPoint(0F, 0F, 0F);
		leftshouldpin.setTextureSize(128, 128);
		leftshouldpin.mirror = true;
		setRotation(leftshouldpin, 0F, 0F, 0.2974216F);
		body = new ModelRenderer(this, 0, 54);
		body.addBox(-4F, 0F, -3F, 8, 12, 6);
		body.setRotationPoint(0F, 0F, 0F);
		body.setTextureSize(128, 128);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		rightarm = new ModelRenderer(this, 40, 41);
		rightarm.addBox(-4F, -2.4F, -3F, 5, 5, 6);
		rightarm.setRotationPoint(0F, 0F, 0F);
		rightarm.setTextureSize(128, 128);
		rightarm.mirror = true;
		setRotation(rightarm, 0F, 0F, 0F);
		leftarm = new ModelRenderer(this, 40, 54);
		leftarm.addBox(-1F, -2.4F, -3F, 5, 5, 6);
		leftarm.setRotationPoint(0F, 0F, 0F);
		leftarm.setTextureSize(128, 128);
		leftarm.mirror = true;
		setRotation(leftarm, 0F, 0F, 0F);
		leftfoot = new ModelRenderer(this, 0, 73);
		leftfoot.addBox(-2F, 8F, -2.5F, 5, 4, 5);
		leftfoot.setRotationPoint(0F, 0F, 0F);
		leftfoot.setTextureSize(128, 128);
		leftfoot.mirror = true;
		setRotation(leftfoot, 0F, 0F, 0F);
		rightfoot = new ModelRenderer(this, 0, 84);
		rightfoot.addBox(-3F, 8F, -2.5F, 5, 4, 5);
		rightfoot.setRotationPoint(0F, 0F, 0F);
		rightfoot.setTextureSize(128, 128);
		rightfoot.mirror = true;
		setRotation(rightfoot, 0F, 0F, 0F);
		helmtip = new ModelRenderer(this, 0, 99);
		helmtip.addBox(-2F, -6F, -5F, 4, 1, 1);
		helmtip.setRotationPoint(0F, 0F, 0F);
		helmtip.setTextureSize(128, 128);
		helmtip.mirror = true;
		setRotation(helmtip, 0F, 0F, 0F);
		helmpoint = new ModelRenderer(this, 0, 103);
		helmpoint.addBox(-4F, -8F, -5F, 8, 2, 1);
		helmpoint.setRotationPoint(0F, 0F, 0F);
		helmpoint.setTextureSize(128, 128);
		helmpoint.mirror = true;
		setRotation(helmpoint, 0F, 0F, 0F);
		upperhead = new ModelRenderer(this, 0, 119);
		upperhead.addBox(-4F, -9F, -4F, 8, 1, 8);
		upperhead.setRotationPoint(0F, 0F, 0F);
		upperhead.setTextureSize(128, 128);
		upperhead.mirror = true;
		setRotation(upperhead, 0F, 0F, 0F);
		rightshould = new ModelRenderer(this, 41, 70);
		rightshould.addBox(-3.5F, -4F, -1F, 3, 2, 2);
		rightshould.setRotationPoint(0F, 0F, 0F);
		rightshould.setTextureSize(128, 128);
		rightshould.mirror = true;
		setRotation(rightshould, 0F, 0F, -0.2974289F);
		rightshouldpin = new ModelRenderer(this, 42, 80);
		rightshouldpin.addBox(-3F, -6F, -0.5F, 2, 3, 1);
		rightshouldpin.setRotationPoint(0F, 0F, 0F);
		rightshouldpin.setTextureSize(128, 128);
		rightshouldpin.mirror = true;
		setRotation(rightshouldpin, 0F, 0F, -0.2974216F);

		this.bipedHead.addChild(helmpoint);
		this.bipedHead.addChild(helmtip);
		this.bipedHead.addChild(rightheadb);
		this.bipedHead.addChild(rightheada);
		this.bipedHead.addChild(leftheadb);
		this.bipedHead.addChild(leftheada);
		this.bipedHead.addChild(upperhead);
		this.bipedBody.addChild(body);
		this.bipedRightLeg.addChild(rightfoot);
		this.bipedLeftLeg.addChild(leftfoot);
		this.bipedLeftArm.addChild(leftshould);
		this.bipedLeftArm.addChild(leftshouldpin);
		this.bipedLeftArm.addChild(leftarm);
		this.bipedRightArm.addChild(rightarm);
		this.bipedRightArm.addChild(rightshould);
		this.bipedRightArm.addChild(rightshouldpin);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		// EntityPlayer player = (EntityPlayer)entity;
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

	}
	private static void setRotation(ModelRenderer model, float x, float y,
			float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
