package mhfc.net.client.model.weapon.bow;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBHunters extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;
    ModelRenderer start;
    ModelRenderer half;
    ModelRenderer full;
    ModelRenderer pinStart;
    ModelRenderer pinHalf;
    ModelRenderer pinful;
    ModelRenderer Shape16;
    ModelRenderer Shape17;
    ModelRenderer Shape18;
    ModelRenderer Shape19;
    ModelRenderer Shape20;
    ModelRenderer Shape21;
    ModelRenderer Shape22;
  
  public ModelBHunters()
  {
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
      Shape5 = new ModelRenderer(this, 0, 29);
      Shape5.addBox(0F, 0F, 0F, 1, 40, 0);
      Shape5.setRotationPoint(3F, -18F, 0F);
      Shape5.setTextureSize(128, 128);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 0, 70);
      Shape6.addBox(0F, 0F, 0F, 1, 20, 0);
      Shape6.setRotationPoint(3F, -19F, 0F);
      Shape6.setTextureSize(128, 128);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, -0.1047198F);
      Shape7 = new ModelRenderer(this, 0, 70);
      Shape7.addBox(0F, 0F, 0F, 1, 20, 0);
      Shape7.setRotationPoint(5F, 0F, 0F);
      Shape7.setTextureSize(128, 128);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 0.1047198F);
      Shape8 = new ModelRenderer(this, 0, 95);
      Shape8.addBox(0F, 0F, 0F, 1, 20, 0);
      Shape8.setRotationPoint(9F, 0F, 0F);
      Shape8.setTextureSize(128, 128);
      Shape8.mirror = true;
      setRotation(Shape8, 0F, 0F, 0.3141593F);
      Shape9 = new ModelRenderer(this, 0, 95);
      Shape9.addBox(0F, 0F, 0F, 1, 20, 0);
      Shape9.setRotationPoint(3F, -18F, 0F);
      Shape9.setTextureSize(128, 128);
      Shape9.mirror = true;
      setRotation(Shape9, 0F, 0F, -0.3141593F);
      start = new ModelRenderer(this, 5, 65);
      start.addBox(0F, 0F, -0.5F, 20, 1, 1);
      start.setRotationPoint(-17F, 0F, 0F);
      start.setTextureSize(128, 128);
      start.mirror = true;
      setRotation(start, 0F, 0F, 0F);
      half = new ModelRenderer(this, 5, 68);
      half.addBox(0F, 0F, -0.5F, 20, 1, 1);
      half.setRotationPoint(-14.5F, 0F, 0F);
      half.setTextureSize(128, 128);
      half.mirror = true;
      setRotation(half, 0F, 0F, 0F);
      full = new ModelRenderer(this, 5, 71);
      full.addBox(0F, 0F, -0.5F, 20, 1, 1);
      full.setRotationPoint(-10.5F, 0F, 0F);
      full.setTextureSize(128, 128);
      full.mirror = true;
      setRotation(full, 0F, 0F, 0F);
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
      pinful = new ModelRenderer(this, 13, 45);
      pinful.addBox(-1F, -1F, -1F, 2, 2, 2);
      pinful.setRotationPoint(-10F, 0.5F, 0F);
      pinful.setTextureSize(128, 128);
      pinful.mirror = true;
      setRotation(pinful, 0F, 0F, -0.7853982F);
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
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    renderA(entity, f, f1, f2, f3, f4, f5);
    renderRest(f5);
  }
  
  public void renderRest(float f5) {
	  Shape1.render(f5);
	  Shape2.render(f5);
	  Shape3.render(f5);
	  Shape4.render(f5);
	  Shape5.render(f5);
	  Shape16.render(f5);
	  Shape17.render(f5);
	  Shape18.render(f5);
	  Shape19.render(f5);
	  Shape20.render(f5);
	  Shape21.render(f5);
  }

public void renderA(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
	  Shape1.renderWithRotation(f5);
	  Shape2.renderWithRotation(f5);
	  Shape3.renderWithRotation(f5);
	  Shape4.renderWithRotation(f5);
	  Shape5.renderWithRotation(f5);
	  Shape6.renderWithRotation(f5);
	  Shape7.renderWithRotation(f5);
	  Shape8.renderWithRotation(f5);
	  Shape9.renderWithRotation(f5);
	  start.renderWithRotation(f5);
	  half.renderWithRotation(f5);
	  full.renderWithRotation(f5);
	  pinStart.renderWithRotation(f5);
	  pinHalf.renderWithRotation(f5);
	  pinful.renderWithRotation(f5);
	  Shape16.renderWithRotation(f5);
	  Shape17.renderWithRotation(f5);
	  Shape18.renderWithRotation(f5);
	  Shape19.renderWithRotation(f5);
	  Shape20.renderWithRotation(f5);
	  Shape21.renderWithRotation(f5);
	  Shape22.renderWithRotation(f5);
	
}

private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
  }

}
