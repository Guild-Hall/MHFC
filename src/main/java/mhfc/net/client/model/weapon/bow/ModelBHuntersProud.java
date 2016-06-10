package mhfc.net.client.model.weapon.bow;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBHuntersProud extends ModelBase implements IModelBow {
	//fields
	ModelRenderer heada;
	ModelRenderer headb;
	ModelRenderer headc;
	ModelRenderer pina;
	ModelRenderer pinb;
	ModelRenderer pinc;
	ModelRenderer stringrest;
	ModelRenderer stringahalf;
	ModelRenderer stringbhalf;
	ModelRenderer stringafull;
	ModelRenderer stringbfull;
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Shape8;
	ModelRenderer Shape9;
	ModelRenderer Shape10;
	ModelRenderer Shape11;

	public ModelBHuntersProud() {
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
		pina = new ModelRenderer(this, 5, 65);
		pina.addBox(0F, 0F, -0.5F, 20, 1, 1);
		pina.setRotationPoint(-17F, 0F, 0F);
		pina.setTextureSize(128, 128);
		pina.mirror = true;
		setRotation(pina, 0F, 0F, 0F);
		pinb = new ModelRenderer(this, 5, 65);
		pinb.addBox(0F, 0F, -0.5F, 20, 1, 1);
		pinb.setRotationPoint(-14.5F, 0F, 0F);
		pinb.setTextureSize(128, 128);
		pinb.mirror = true;
		setRotation(pinb, 0F, 0F, 0F);
		pinc = new ModelRenderer(this, 5, 65);
		pinc.addBox(0F, 0F, -0.5F, 20, 1, 1);
		pinc.setRotationPoint(-10.5F, 0F, 0F);
		pinc.setTextureSize(128, 128);
		pinc.mirror = true;
		setRotation(pinc, 0F, 0F, 0F);
		stringrest = new ModelRenderer(this, 0, 29);
		stringrest.addBox(0F, 0F, 0F, 1, 40, 0);
		stringrest.setRotationPoint(3F, -18F, 0F);
		stringrest.setTextureSize(128, 128);
		stringrest.mirror = true;
		setRotation(stringrest, 0F, 0F, 0F);
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
		Shape5 = new ModelRenderer(this, 0, 0);
		Shape5.addBox(-24F, -1F, -1F, 25, 2, 2);
		Shape5.setRotationPoint(-4F, -3F, 0F);
		Shape5.setTextureSize(128, 128);
		Shape5.mirror = true;
		setRotation(Shape5, -0.7853982F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 11, 15);
		Shape6.addBox(-1F, -1F, -1.5F, 2, 4, 3);
		Shape6.setRotationPoint(-4F, -4F, 0F);
		Shape6.setTextureSize(128, 128);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 59, 0);
		Shape7.addBox(-7F, -1F, -0.5F, 8, 4, 1);
		Shape7.setRotationPoint(-4F, 3F, 0F);
		Shape7.setTextureSize(128, 128);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		Shape8 = new ModelRenderer(this, 22, 12);
		Shape8.addBox(-2F, -2F, -2F, 4, 6, 4);
		Shape8.setRotationPoint(-2F, 9F, 0F);
		Shape8.setTextureSize(128, 128);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 0F, 0F);
		Shape9 = new ModelRenderer(this, 40, 14);
		Shape9.addBox(-2F, -2F, -2F, 4, 4, 4);
		Shape9.setRotationPoint(-2F, -9F, 0F);
		Shape9.setTextureSize(128, 128);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 0F, 0F);
		Shape10 = new ModelRenderer(this, 22, 41);
		Shape10.addBox(-2F, -3F, -2F, 4, 6, 4);
		Shape10.setRotationPoint(4F, -19.5F, 0F);
		Shape10.setTextureSize(128, 128);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 0F, 0.7760897F);
		Shape11 = new ModelRenderer(this, 80, 0);
		Shape11.addBox(-2F, -2F, -2F, 4, 4, 4);
		Shape11.setRotationPoint(4F, 22F, 0F);
		Shape11.setTextureSize(128, 128);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, -0.715585F);
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
		pina.renderWithRotation(f5);
		pinb.renderWithRotation(f5);
		pinc.renderWithRotation(f5);
		stringrest.renderWithRotation(f5);
		stringahalf.renderWithRotation(f5);
		stringbhalf.renderWithRotation(f5);
		stringafull.renderWithRotation(f5);
		stringbfull.renderWithRotation(f5);
		Shape1.renderWithRotation(f5);
		Shape2.renderWithRotation(f5);
		Shape3.renderWithRotation(f5);
		Shape4.renderWithRotation(f5);
		Shape5.renderWithRotation(f5);
		Shape6.renderWithRotation(f5);
		Shape7.renderWithRotation(f5);
		Shape8.renderWithRotation(f5);
		Shape9.renderWithRotation(f5);
		Shape10.renderWithRotation(f5);
		Shape11.renderWithRotation(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setupStart() {
		stringrest.isHidden = pina.isHidden = heada.isHidden = false;
		stringahalf.isHidden = stringbhalf.isHidden = pinb.isHidden = headb.isHidden = true;
		stringafull.isHidden = stringbfull.isHidden = pinc.isHidden = headc.isHidden = true;
	}

	@Override
	public void setupHalf() {
		stringrest.isHidden = pina.isHidden = heada.isHidden = true;
		stringahalf.isHidden = stringbhalf.isHidden = pinb.isHidden = headb.isHidden = false;
		stringafull.isHidden = stringbfull.isHidden = pinc.isHidden = headc.isHidden = true;
	}

	@Override
	public void setupFull() {
		stringrest.isHidden = pina.isHidden = heada.isHidden = true;
		stringahalf.isHidden = stringbhalf.isHidden = pinb.isHidden = headb.isHidden = true;
		stringafull.isHidden = stringbfull.isHidden = pinc.isHidden = headc.isHidden = false;
	}

	@Override
	public void setupRest() {
		stringrest.isHidden = false;
		pina.isHidden = heada.isHidden = true;
		stringahalf.isHidden = stringbhalf.isHidden = pinb.isHidden = headb.isHidden = true;
		stringafull.isHidden = stringbfull.isHidden = pinc.isHidden = headc.isHidden = true;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}

}
