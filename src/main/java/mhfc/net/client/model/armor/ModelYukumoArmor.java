package mhfc.net.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelYukumoArmor extends ModelBiped {
	ModelRenderer body;
	ModelRenderer rightleg;
	ModelRenderer leftleg;
	ModelRenderer head1;
	ModelRenderer head2;
	ModelRenderer head3;
	ModelRenderer head4;
	ModelRenderer head5;
	ModelRenderer head6;
	ModelRenderer head7;
	ModelRenderer head8;
	ModelRenderer head9;
	ModelRenderer head10;
	ModelRenderer head11;

	public ModelYukumoArmor(float f) {
		super(f, 0, 128, 128);
		textureWidth = 128;
		textureHeight = 128;
		body = new ModelRenderer(this, 0, 100);
		body.addBox(-4F, 0F, -3F, 8, 12, 6);
		body.setRotationPoint(0F, 0F, 0F);
		body.setTextureSize(128, 128);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		rightleg = new ModelRenderer(this, 40, 57);
		rightleg.addBox(-3F, 8F, -2.5F, 5, 4, 5);
		rightleg.setRotationPoint(0F, 0F, 0F);
		rightleg.setTextureSize(128, 128);
		rightleg.mirror = true;
		setRotation(rightleg, 0F, 0F, 0F);
		leftleg = new ModelRenderer(this, 40, 68);
		leftleg.addBox(-2F, 8F, -2.5F, 5, 4, 5);
		leftleg.setRotationPoint(0F, 0F, 0F);
		leftleg.setTextureSize(128, 128);
		leftleg.mirror = true;
		setRotation(leftleg, 0F, 0F, 0F);
		head1 = new ModelRenderer(this, 0, 39);
		head1.addBox(-6F, -9F, -6F, 12, 2, 12);
		head1.setRotationPoint(0F, 0F, 0F);
		head1.setTextureSize(128, 128);
		head1.mirror = true;
		setRotation(head1, 0F, 0F, 0F);
		head2 = new ModelRenderer(this, 0, 78);
		head2.addBox(-1F, -10F, -1F, 2, 1, 2);
		head2.setRotationPoint(0F, 0F, 0F);
		head2.setTextureSize(128, 128);
		head2.mirror = true;
		setRotation(head2, 0F, 0F, 0F);
		head3 = new ModelRenderer(this, 0, 83);
		head3.addBox(0F, -13F, 0F, 0, 4, 8);
		head3.setRotationPoint(0F, 0F, 0F);
		head3.setTextureSize(128, 128);
		head3.mirror = true;
		setRotation(head3, 0F, 0F, 0F);
		head4 = new ModelRenderer(this, 0, 59);
		head4.addBox(-4F, -9F, -7F, 8, 2, 1);
		head4.setRotationPoint(0F, 0F, 0F);
		head4.setTextureSize(128, 128);
		head4.mirror = true;
		setRotation(head4, 0F, 0F, 0F);
		head5 = new ModelRenderer(this, 0, 68);
		head5.addBox(-2F, -9F, -8F, 4, 2, 1);
		head5.setRotationPoint(0F, 0F, 0F);
		head5.setTextureSize(128, 128);
		head5.mirror = true;
		setRotation(head5, 0F, 0F, 0F);
		head6 = new ModelRenderer(this, 0, 63);
		head6.addBox(-4F, -9F, 6F, 8, 2, 1);
		head6.setRotationPoint(0F, 0F, 0F);
		head6.setTextureSize(128, 128);
		head6.mirror = true;
		setRotation(head6, 0F, 0F, 0F);
		head7 = new ModelRenderer(this, 0, 72);
		head7.addBox(-2F, -9F, 7F, 4, 2, 1);
		head7.setRotationPoint(0F, 0F, 0F);
		head7.setTextureSize(128, 128);
		head7.mirror = true;
		setRotation(head7, 0F, 0F, 0F);
		head8 = new ModelRenderer(this, 19, 70);
		head8.addBox(-7F, -9F, -4F, 1, 2, 8);
		head8.setRotationPoint(0F, 0F, 0F);
		head8.setTextureSize(128, 128);
		head8.mirror = true;
		setRotation(head8, 0F, 0F, 0F);
		head9 = new ModelRenderer(this, 19, 58);
		head9.addBox(6F, -9F, -4F, 1, 2, 8);
		head9.setRotationPoint(0F, 0F, 0F);
		head9.setTextureSize(128, 128);
		head9.mirror = true;
		setRotation(head9, 0F, 0F, 0F);
		head10 = new ModelRenderer(this, 20, 91);
		head10.addBox(-8F, -9F, -2F, 1, 2, 4);
		head10.setRotationPoint(0F, 0F, 0F);
		head10.setTextureSize(128, 128);
		head10.mirror = true;
		setRotation(head10, 0F, 0F, 0F);
		head11 = new ModelRenderer(this, 20, 83);
		head11.addBox(7F, -9F, -2F, 1, 2, 4);
		head11.setRotationPoint(0F, 0F, 0F);
		head11.setTextureSize(128, 128);
		head11.mirror = true;
		setRotation(head11, 0F, 0F, 0F);

		bipedHead.addChild(head1);
		bipedHead.addChild(head2);
		bipedHead.addChild(head3);
		bipedHead.addChild(head4);
		bipedHead.addChild(head5);
		bipedHead.addChild(head6);
		bipedHead.addChild(head7);
		bipedHead.addChild(head8);
		bipedHead.addChild(head9);
		bipedHead.addChild(head10);
		bipedHead.addChild(head11);
		bipedBody.addChild(body);
		bipedLeftLeg.addChild(leftleg);
		bipedRightLeg.addChild(rightleg);

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
