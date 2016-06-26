package mhfc.net.client.model.weapon.bow;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBHuntersStout extends ModelBase implements IModelBow {
	//fields
	ModelRenderer heada;
	ModelRenderer headb;
	ModelRenderer headc;
	ModelRenderer stringrest_;
	ModelRenderer stringahalf;
	ModelRenderer stringbhalf;
	ModelRenderer stringafull;
	ModelRenderer stringbfull;
	ModelRenderer pinb;
	ModelRenderer pina;
	ModelRenderer pinc;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape16;
	ModelRenderer Shape17;
	ModelRenderer Shape18;
	ModelRenderer Shape19;
	ModelRenderer Shape20;
	ModelRenderer Shape21;
	ModelRenderer Shape22;

	public ModelBHuntersStout() {
		textureWidth = 128;
		textureHeight = 128;

		heada = new ModelRenderer(this, 13, 31);
		heada.addBox(-1F, -1F, -1F, 2, 2, 2);
		heada.setRotationPoint(-17F, 0.5F, 0F);
		heada.setTextureSize(128, 128);
		heada.mirror = true;
		setRotation(heada, 0F, 0F, -0.7853982F);
		headb = new ModelRenderer(this, 13, 31);
		headb.addBox(-1F, -1F, -1F, 2, 2, 2);
		headb.setRotationPoint(-14F, 0.5F, 0F);
		headb.setTextureSize(128, 128);
		headb.mirror = true;
		setRotation(headb, 0F, 0F, -0.7853982F);
		headc = new ModelRenderer(this, 13, 31);
		headc.addBox(-1F, -1F, -1F, 2, 2, 2);
		headc.setRotationPoint(-10F, 0.5F, 0F);
		headc.setTextureSize(128, 128);
		headc.mirror = true;
		setRotation(headc, 0F, 0F, -0.7853982F);
		stringrest_ = new ModelRenderer(this, 0, 29);
		stringrest_.addBox(0F, 0F, 0F, 1, 40, 0);
		stringrest_.setRotationPoint(3F, -18F, 0F);
		stringrest_.setTextureSize(128, 128);
		stringrest_.mirror = true;
		setRotation(stringrest_, 0F, 0F, 0F);
		stringahalf = new ModelRenderer(this, 0, 70);
		stringahalf.addBox(0F, 0F, 0F, 1, 20, 0);
		stringahalf.setRotationPoint(3F, -19F, 0F);
		stringahalf.setTextureSize(128, 128);
		stringahalf.mirror = true;
		setRotation(stringahalf, 0F, 0F, -0.1047198F);
		stringbhalf = new ModelRenderer(this, 0, 70);
		stringbhalf.addBox(0F, 0F, 0F, 1, 20, 0);
		stringbhalf.setRotationPoint(5F, 0F, 0F);
		stringbhalf.setTextureSize(128, 128);
		stringbhalf.mirror = true;
		setRotation(stringbhalf, 0F, 0F, 0.1047198F);
		stringafull = new ModelRenderer(this, 0, 70);
		stringafull.addBox(0F, 0F, 0F, 1, 20, 0);
		stringafull.setRotationPoint(3F, -18F, 0F);
		stringafull.setTextureSize(128, 128);
		stringafull.mirror = true;
		setRotation(stringafull, 0F, 0F, -0.3141593F);
		stringbfull = new ModelRenderer(this, 0, 70);
		stringbfull.addBox(0F, 0F, 0F, 1, 20, 0);
		stringbfull.setRotationPoint(9F, 0F, 0F);
		stringbfull.setTextureSize(128, 128);
		stringbfull.mirror = true;
		setRotation(stringbfull, 0F, 0F, 0.3141593F);
		pinb = new ModelRenderer(this, 5, 65);
		pinb.addBox(0F, 0F, -0.5F, 20, 1, 1);
		pinb.setRotationPoint(-14.5F, 0F, 0F);
		pinb.setTextureSize(128, 128);
		pinb.mirror = true;
		setRotation(pinb, 0F, 0F, 0F);
		pina = new ModelRenderer(this, 5, 65);
		pina.addBox(0F, 0F, -0.5F, 20, 1, 1);
		pina.setRotationPoint(-17F, 0F, 0F);
		pina.setTextureSize(128, 128);
		pina.mirror = true;
		setRotation(pina, 0F, 0F, 0F);
		pinc = new ModelRenderer(this, 5, 65);
		pinc.addBox(0F, 0F, -0.5F, 20, 1, 1);
		pinc.setRotationPoint(-10.5F, 0F, 0F);
		pinc.setTextureSize(128, 128);
		pinc.mirror = true;
		setRotation(pinc, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 23, 76);
		Shape3.addBox(-2F, -15F, -2F, 3, 18, 4);
		Shape3.setRotationPoint(-1.6F, -12F, 0F);
		Shape3.setTextureSize(128, 128);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0.6981317F);
		Shape4 = new ModelRenderer(this, 5, 30);
		Shape4.addBox(0F, 0F, -0.5F, 2, 14, 1);
		Shape4.setRotationPoint(-3F, 12F, 0F);
		Shape4.setTextureSize(128, 128);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, -0.5235988F);
		Shape1 = new ModelRenderer(this, 30, 28);
		Shape1.addBox(-2.5F, -4F, -1.5F, 4, 7, 3);
		Shape1.setRotationPoint(0F, -9F, 0F);
		Shape1.setTextureSize(128, 128);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, -0.5235988F);
		Shape2 = new ModelRenderer(this, 46, 28);
		Shape2.addBox(-1F, -3F, -1F, 3, 7, 2);
		Shape2.setRotationPoint(-0.4F, -4.3F, 0F);
		Shape2.setTextureSize(128, 128);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape16 = new ModelRenderer(this, 51, 43);
		Shape16.addBox(-1F, -10F, -1F, 2, 14, 2);
		Shape16.setRotationPoint(0F, -15F, 0F);
		Shape16.setTextureSize(128, 128);
		Shape16.mirror = true;
		setRotation(Shape16, 0F, 0F, -0.5235988F);
		Shape17 = new ModelRenderer(this, 12, 54);
		Shape17.addBox(-3F, -1F, -1.5F, 5, 1, 3);
		Shape17.setRotationPoint(0F, 0F, 0F);
		Shape17.setTextureSize(128, 128);
		Shape17.mirror = true;
		setRotation(Shape17, 0F, 0F, 0F);
		Shape18 = new ModelRenderer(this, 4, 50);
		Shape18.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape18.setRotationPoint(-0.5F, -2F, -2F);
		Shape18.setTextureSize(128, 128);
		Shape18.mirror = true;
		setRotation(Shape18, 0F, 0F, -0.7853982F);
		Shape19 = new ModelRenderer(this, 4, 50);
		Shape19.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape19.setRotationPoint(0F, -2.7F, 1F);
		Shape19.setTextureSize(128, 128);
		Shape19.mirror = true;
		setRotation(Shape19, 0F, 0F, 0.7853982F);
		Shape20 = new ModelRenderer(this, 29, 43);
		Shape20.addBox(-1.5F, 1F, -2F, 5, 3, 4);
		Shape20.setRotationPoint(-2F, 0F, 0F);
		Shape20.setTextureSize(128, 128);
		Shape20.mirror = true;
		setRotation(Shape20, 0F, 0F, 0F);
		Shape21 = new ModelRenderer(this, 8, 78);
		Shape21.addBox(-1.5F, -1F, -1.5F, 3, 10, 3);
		Shape21.setRotationPoint(0F, 4F, 0F);
		Shape21.setTextureSize(128, 128);
		Shape21.mirror = true;
		setRotation(Shape21, 0F, 0F, 0.2094395F);
		Shape22 = new ModelRenderer(this, 0, 0);
		Shape22.addBox(0F, -4F, -1F, 2, 9, 2);
		Shape22.setRotationPoint(-2F, 8F, 0F);
		Shape22.setTextureSize(128, 128);
		Shape22.mirror = true;
		setRotation(Shape22, 0F, 0F, 1.361357F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		render(f5);
	}

	public void render(float f5) {
		heada.renderWithRotation(f5);
		headb.renderWithRotation(f5);
		headc.renderWithRotation(f5);
		stringrest_.renderWithRotation(f5);
		stringahalf.renderWithRotation(f5);
		stringbhalf.renderWithRotation(f5);
		stringafull.renderWithRotation(f5);
		stringbfull.renderWithRotation(f5);
		pinb.renderWithRotation(f5);
		pina.renderWithRotation(f5);
		pinc.renderWithRotation(f5);
		Shape3.renderWithRotation(f5);
		Shape4.renderWithRotation(f5);
		Shape1.renderWithRotation(f5);
		Shape2.renderWithRotation(f5);
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
		stringrest_.isHidden = pina.isHidden = heada.isHidden = false;
		stringahalf.isHidden = stringbhalf.isHidden = pinb.isHidden = headb.isHidden = true;
		stringafull.isHidden = stringbfull.isHidden = pinc.isHidden = headc.isHidden = true;
	}

	@Override
	public void setupHalf() {
		stringrest_.isHidden = pina.isHidden = heada.isHidden = true;
		stringahalf.isHidden = stringbhalf.isHidden = pinb.isHidden = headb.isHidden = false;
		stringafull.isHidden = stringbfull.isHidden = pinc.isHidden = headc.isHidden = true;
	}

	@Override
	public void setupFull() {
		stringrest_.isHidden = pina.isHidden = heada.isHidden = true;
		stringahalf.isHidden = stringbhalf.isHidden = pinb.isHidden = headb.isHidden = true;
		stringafull.isHidden = stringbfull.isHidden = pinc.isHidden = headc.isHidden = false;
	}

	@Override
	public void setupRest() {
		stringrest_.isHidden = false;
		pina.isHidden = heada.isHidden = true;
		stringahalf.isHidden = stringbhalf.isHidden = pinb.isHidden = headb.isHidden = true;
		stringafull.isHidden = stringbfull.isHidden = pinc.isHidden = headc.isHidden = true;
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
