package mhfc.net.client.model.weapon.bow;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBHunters extends ModelBase implements IModelBow {
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer bowstringStart;
	ModelRenderer bowstringHalf1;
	ModelRenderer bowstringHalf2;
	ModelRenderer bowtringFull1;
	ModelRenderer bowtringFull2;
	ModelRenderer arrowStart;
	ModelRenderer arrowHalf;
	ModelRenderer arrowFull;
	ModelRenderer pinStart;
	ModelRenderer pinHalf;
	ModelRenderer pinFull;
	ModelRenderer Shape16;
	ModelRenderer Shape17;
	ModelRenderer Shape18;
	ModelRenderer Shape19;
	ModelRenderer Shape20;
	ModelRenderer Shape21;
	ModelRenderer Shape22;

	public ModelBHunters() {
		textureWidth = 128;
		textureHeight = 128;

		Shape1 = new ModelRenderer(this, 0, 15);
		Shape1.addBox(-1F, -10F, -1F, 2, 10, 2);
		Shape1.setRotationPoint(-2F, 0F, 0F);
		Shape1.setTextureSize(128, 128);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 15);
		Shape2.addBox(0F, 0F, 0F, 2, 10, 2);
		Shape2.setRotationPoint(-3F, 2F, -1F);
		Shape2.setTextureSize(128, 128);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 5, 46);
		Shape3.addBox(-1F, -13F, -0.5F, 2, 14, 1);
		Shape3.setRotationPoint(-1.6F, -10F, 0F);
		Shape3.setTextureSize(128, 128);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0.5235988F);
		Shape4 = new ModelRenderer(this, 5, 30);
		Shape4.addBox(0F, 0F, -0.5F, 2, 14, 1);
		Shape4.setRotationPoint(-3F, 12F, 0F);
		Shape4.setTextureSize(128, 128);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, -0.5235988F);
		bowstringStart = new ModelRenderer(this, 0, 29);
		bowstringStart.addBox(0F, 0F, 0F, 1, 40, 0);
		bowstringStart.setRotationPoint(3F, -18F, 0F);
		bowstringStart.setTextureSize(128, 128);
		bowstringStart.mirror = true;
		setRotation(bowstringStart, 0F, 0F, 0F);
		bowstringHalf1 = new ModelRenderer(this, 0, 70);
		bowstringHalf1.addBox(0F, 0F, 0F, 1, 20, 0);
		bowstringHalf1.setRotationPoint(3F, -19F, 0F);
		bowstringHalf1.setTextureSize(128, 128);
		bowstringHalf1.mirror = true;
		setRotation(bowstringHalf1, 0F, 0F, -0.1047198F);
		bowstringHalf2 = new ModelRenderer(this, 0, 70);
		bowstringHalf2.addBox(0F, 0F, 0F, 1, 20, 0);
		bowstringHalf2.setRotationPoint(5F, 0F, 0F);
		bowstringHalf2.setTextureSize(128, 128);
		bowstringHalf2.mirror = true;
		setRotation(bowstringHalf2, 0F, 0F, 0.1047198F);
		bowtringFull1 = new ModelRenderer(this, 0, 95);
		bowtringFull1.addBox(0F, 0F, 0F, 1, 20, 0);
		bowtringFull1.setRotationPoint(9F, 0F, 0F);
		bowtringFull1.setTextureSize(128, 128);
		bowtringFull1.mirror = true;
		setRotation(bowtringFull1, 0F, 0F, 0.3141593F);
		bowtringFull2 = new ModelRenderer(this, 0, 95);
		bowtringFull2.addBox(0F, 0F, 0F, 1, 20, 0);
		bowtringFull2.setRotationPoint(3F, -18F, 0F);
		bowtringFull2.setTextureSize(128, 128);
		bowtringFull2.mirror = true;
		setRotation(bowtringFull2, 0F, 0F, -0.3141593F);
		arrowStart = new ModelRenderer(this, 5, 65);
		arrowStart.addBox(0F, 0F, -0.5F, 20, 1, 1);
		arrowStart.setRotationPoint(-17F, 0F, 0F);
		arrowStart.setTextureSize(128, 128);
		arrowStart.mirror = true;
		setRotation(arrowStart, 0F, 0F, 0F);
		arrowHalf = new ModelRenderer(this, 5, 68);
		arrowHalf.addBox(0F, 0F, -0.5F, 20, 1, 1);
		arrowHalf.setRotationPoint(-14.5F, 0F, 0F);
		arrowHalf.setTextureSize(128, 128);
		arrowHalf.mirror = true;
		setRotation(arrowHalf, 0F, 0F, 0F);
		arrowFull = new ModelRenderer(this, 5, 71);
		arrowFull.addBox(0F, 0F, -0.5F, 20, 1, 1);
		arrowFull.setRotationPoint(-10.5F, 0F, 0F);
		arrowFull.setTextureSize(128, 128);
		arrowFull.mirror = true;
		setRotation(arrowFull, 0F, 0F, 0F);
		pinStart = new ModelRenderer(this, 13, 31);
		pinStart.addBox(-1F, -1F, -1F, 2, 2, 2);
		pinStart.setRotationPoint(-17F, 0.5F, 0F);
		pinStart.setTextureSize(128, 128);
		pinStart.mirror = true;
		setRotation(pinStart, 0F, 0F, -0.7853982F);
		pinHalf = new ModelRenderer(this, 13, 38);
		pinHalf.addBox(-1F, -1F, -1F, 2, 2, 2);
		pinHalf.setRotationPoint(-14F, 0.5F, 0F);
		pinHalf.setTextureSize(128, 128);
		pinHalf.mirror = true;
		setRotation(pinHalf, 0F, 0F, -0.7853982F);
		pinFull = new ModelRenderer(this, 13, 45);
		pinFull.addBox(-1F, -1F, -1F, 2, 2, 2);
		pinFull.setRotationPoint(-10F, 0.5F, 0F);
		pinFull.setTextureSize(128, 128);
		pinFull.mirror = true;
		setRotation(pinFull, 0F, 0F, -0.7853982F);
		Shape16 = new ModelRenderer(this, 35, 4);
		Shape16.addBox(-11F, -1F, -0.5F, 12, 1, 1);
		Shape16.setRotationPoint(-3F, -2F, -0.5F);
		Shape16.setTextureSize(128, 128);
		Shape16.mirror = true;
		setRotation(Shape16, -0.7853982F, 0F, 0F);
		Shape17 = new ModelRenderer(this, 24, 4);
		Shape17.addBox(-1F, 0F, -3F, 1, 1, 3);
		Shape17.setRotationPoint(-12F, -2.7F, 0F);
		Shape17.setTextureSize(128, 128);
		Shape17.mirror = true;
		setRotation(Shape17, 0F, 0F, 0F);
		Shape18 = new ModelRenderer(this, 44, 7);
		Shape18.addBox(0F, 0F, -0.5F, 5, 1, 1);
		Shape18.setRotationPoint(-7F, 3F, 0.5F);
		Shape18.setTextureSize(128, 128);
		Shape18.mirror = true;
		setRotation(Shape18, -0.7853982F, 0F, 0F);
		Shape19 = new ModelRenderer(this, 64, 2);
		Shape19.addBox(-2F, 0F, -2F, 4, 5, 4);
		Shape19.setRotationPoint(-2F, -9F, 0F);
		Shape19.setTextureSize(128, 128);
		Shape19.mirror = true;
		setRotation(Shape19, 0F, 0F, 0F);
		Shape20 = new ModelRenderer(this, 64, 13);
		Shape20.addBox(-2F, -5F, -2F, 4, 7, 4);
		Shape20.setRotationPoint(-2F, 9.2F, 0F);
		Shape20.setTextureSize(128, 128);
		Shape20.mirror = true;
		setRotation(Shape20, 0F, 0F, 0F);
		Shape21 = new ModelRenderer(this, 53, 11);
		Shape21.addBox(-1F, -1F, -0.5F, 2, 2, 1);
		Shape21.setRotationPoint(-2F, -1F, -1.5F);
		Shape21.setTextureSize(128, 128);
		Shape21.mirror = true;
		setRotation(Shape21, 0F, 0F, -0.7853982F);
		Shape22 = new ModelRenderer(this, 39, 12);
		Shape22.addBox(-1F, -1F, -0.5F, 2, 2, 1);
		Shape22.setRotationPoint(-2F, -1F, 1.5F);
		Shape22.setTextureSize(128, 128);
		Shape22.mirror = true;
		setRotation(Shape22, 0F, 0F, -0.7853982F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		render(f5);
	}

	public void render(float f5) {
		Shape1.renderWithRotation(f5);
		Shape2.renderWithRotation(f5);
		Shape3.renderWithRotation(f5);
		Shape4.renderWithRotation(f5);
		bowstringStart.renderWithRotation(f5);
		bowstringHalf1.renderWithRotation(f5);
		bowstringHalf2.renderWithRotation(f5);
		bowtringFull1.renderWithRotation(f5);
		bowtringFull2.renderWithRotation(f5);
		arrowStart.renderWithRotation(f5);
		arrowHalf.renderWithRotation(f5);
		arrowFull.renderWithRotation(f5);
		pinStart.renderWithRotation(f5);
		pinHalf.renderWithRotation(f5);
		pinFull.renderWithRotation(f5);
		Shape16.renderWithRotation(f5);
		Shape17.renderWithRotation(f5);
		Shape18.renderWithRotation(f5);
		Shape19.renderWithRotation(f5);
		Shape20.renderWithRotation(f5);
		Shape21.renderWithRotation(f5);
		Shape22.renderWithRotation(f5);
	}

	@Override
	public void setupStart() {
		bowstringStart.isHidden = arrowStart.isHidden = pinStart.isHidden = false;
		bowstringHalf1.isHidden = bowstringHalf2.isHidden = arrowHalf.isHidden = pinHalf.isHidden = true;
		bowtringFull1.isHidden = bowtringFull2.isHidden = arrowFull.isHidden = pinFull.isHidden = true;
	}

	@Override
	public void setupHalf() {
		bowstringStart.isHidden = arrowStart.isHidden = pinStart.isHidden = true;
		bowstringHalf1.isHidden = bowstringHalf2.isHidden = arrowHalf.isHidden = pinHalf.isHidden = false;
		bowtringFull1.isHidden = bowtringFull2.isHidden = arrowFull.isHidden = pinFull.isHidden = true;
	}

	@Override
	public void setupFull() {
		bowstringStart.isHidden = arrowStart.isHidden = pinStart.isHidden = true;
		bowstringHalf1.isHidden = bowstringHalf2.isHidden = arrowHalf.isHidden = pinHalf.isHidden = true;
		bowtringFull1.isHidden = bowtringFull2.isHidden = arrowFull.isHidden = pinFull.isHidden = false;
	}

	@Override
	public void setupRest() {
		bowstringStart.isHidden = false;
		arrowStart.isHidden = pinStart.isHidden = true;
		bowstringHalf1.isHidden = bowstringHalf2.isHidden = arrowHalf.isHidden = pinHalf.isHidden = true;
		bowtringFull1.isHidden = bowtringFull2.isHidden = arrowFull.isHidden = pinFull.isHidden = true;
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}

}
