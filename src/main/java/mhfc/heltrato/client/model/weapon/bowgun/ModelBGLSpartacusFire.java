package mhfc.heltrato.client.model.weapon.bowgun;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBGLSpartacusFire extends ModelBase
{
  //fields
    ModelRenderer Body;
    ModelRenderer HandleMiddle;
    ModelRenderer HandleTop;
    ModelRenderer HandleBottom;
    ModelRenderer HandleEnd;
    ModelRenderer TopSpike;
    ModelRenderer Barrel;
    ModelRenderer Magazine;
    ModelRenderer Gunsight;
    ModelRenderer FlapLeft;
    ModelRenderer FlapRight;
    ModelRenderer BowLeftTop;
    ModelRenderer BowLeftBottom;
    ModelRenderer BowRightTop;
    ModelRenderer BowRightBottom;
  
  public ModelBGLSpartacusFire()
  {
    textureWidth = 32;
    textureHeight = 32;
    
      Body = new ModelRenderer(this, 0, 27);
      Body.addBox(0F, 0F, 0F, 4, 3, 2);
      Body.setRotationPoint(0F, 0F, -0.5F);
      Body.setTextureSize(32, 32);
      Body.mirror = true;
      setRotation(Body, 0F, 0F, 0F);
      HandleMiddle = new ModelRenderer(this, 0, 24);
      HandleMiddle.addBox(0F, 0F, 0F, 2, 2, 1);
      HandleMiddle.setRotationPoint(4F, 0F, 0F);
      HandleMiddle.setTextureSize(32, 32);
      HandleMiddle.mirror = true;
      setRotation(HandleMiddle, 0F, 0F, 0F);
      HandleTop = new ModelRenderer(this, 0, 22);
      HandleTop.addBox(0F, 0F, 0F, 2, 1, 1);
      HandleTop.setRotationPoint(6F, 0F, 0F);
      HandleTop.setTextureSize(32, 32);
      HandleTop.mirror = true;
      setRotation(HandleTop, 0F, 0F, 0F);
      HandleBottom = new ModelRenderer(this, 0, 16);
      HandleBottom.addBox(0F, 0F, 0F, 2, 1, 1);
      HandleBottom.setRotationPoint(6F, 1F, 0F);
      HandleBottom.setTextureSize(32, 32);
      HandleBottom.mirror = true;
      setRotation(HandleBottom, 0F, 0F, 0.4363323F);
      HandleEnd = new ModelRenderer(this, 0, 18);
      HandleEnd.addBox(0F, 0F, 0F, 1, 3, 1);
      HandleEnd.setRotationPoint(7F, 0F, 0F);
      HandleEnd.setTextureSize(32, 32);
      HandleEnd.mirror = true;
      setRotation(HandleEnd, 0F, 0F, -0.1745329F);
      TopSpike = new ModelRenderer(this, 6, 25);
      TopSpike.addBox(0F, 0F, 0F, 2, 1, 1);
      TopSpike.setRotationPoint(2F, 0F, 0F);
      TopSpike.setTextureSize(32, 32);
      TopSpike.mirror = true;
      setRotation(TopSpike, 0F, 0F, -0.1745329F);
      Barrel = new ModelRenderer(this, 18, 25);
      Barrel.addBox(0F, 0F, 0F, 6, 2, 1);
      Barrel.setRotationPoint(-6F, 1F, 0F);
      Barrel.setTextureSize(32, 32);
      Barrel.mirror = true;
      setRotation(Barrel, 0F, 0F, 0F);
      Magazine = new ModelRenderer(this, 26, 28);
      Magazine.addBox(0F, 0F, 0F, 1, 2, 2);
      Magazine.setRotationPoint(-0.5F, 1.5F, 0F);
      Magazine.setTextureSize(32, 32);
      Magazine.mirror = true;
      setRotation(Magazine, 0F, 0F, 0F);
      Gunsight = new ModelRenderer(this, 22, 0);
      Gunsight.addBox(0F, 0F, 0F, 2, 1, 1);
      Gunsight.setRotationPoint(-5F, 0F, -1F);
      Gunsight.setTextureSize(32, 32);
      Gunsight.mirror = true;
      setRotation(Gunsight, 0F, 0F, 0F);
      FlapLeft = new ModelRenderer(this, 28, 0);
      FlapLeft.addBox(0F, 0F, 0F, 2, 2, 0);
      FlapLeft.setRotationPoint(-3F, 1F, 0F);
      FlapLeft.setTextureSize(32, 32);
      FlapLeft.mirror = true;
      setRotation(FlapLeft, 0F, 0.4363323F, 0F);
      FlapRight = new ModelRenderer(this, 28, 0);
      FlapRight.addBox(0F, 0F, 0F, 2, 2, 0);
      FlapRight.setRotationPoint(-3F, 1F, 1F);
      FlapRight.setTextureSize(32, 32);
      FlapRight.mirror = true;
      setRotation(FlapRight, 0F, -0.4363323F, 0F);
      BowLeftTop = new ModelRenderer(this, 0, 6);
      BowLeftTop.addBox(0F, 0F, -1F, 1, 2, 1);
      BowLeftTop.setRotationPoint(-6F, 3F, 1F);
      BowLeftTop.setTextureSize(32, 32);
      BowLeftTop.mirror = true;
      setRotation(BowLeftTop, -0.4363323F, 0F, -0.1745329F);
      BowLeftBottom = new ModelRenderer(this, 0, 9);
      BowLeftBottom.addBox(-0.3F, 1.9F, -1.03F, 1, 2, 1);
      BowLeftBottom.setRotationPoint(-6F, 3F, 1F);
      BowLeftBottom.setTextureSize(32, 32);
      BowLeftBottom.mirror = true;
      setRotation(BowLeftBottom, -0.4363323F, -0.0698132F, -0.3490659F);
      BowRightTop = new ModelRenderer(this, 0, 0);
      BowRightTop.addBox(0F, 0F, 0F, 1, 2, 1);
      BowRightTop.setRotationPoint(-6F, 3F, 0F);
      BowRightTop.setTextureSize(32, 32);
      BowRightTop.mirror = true;
      setRotation(BowRightTop, 0.4363323F, 0F, -0.1745329F);
      BowRightBottom = new ModelRenderer(this, 0, 3);
      BowRightBottom.addBox(-0.3F, 1.9F, 0.03F, 1, 2, 1);
      BowRightBottom.setRotationPoint(-6F, 3F, -2.997602E-15F);
      BowRightBottom.setTextureSize(32, 32);
      BowRightBottom.mirror = true;
      setRotation(BowRightBottom, 0.4363323F, 0.0698132F, -0.3490659F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    render(f5);
    Body.renderWithRotation(f5);
    HandleMiddle.renderWithRotation(f5);
    HandleTop.renderWithRotation(f5);
    HandleBottom.renderWithRotation(f5);
    HandleEnd.renderWithRotation(f5);
    TopSpike.renderWithRotation(f5);
    Barrel.renderWithRotation(f5);
    Magazine.renderWithRotation(f5);
    Gunsight.renderWithRotation(f5);
    FlapLeft.renderWithRotation(f5);
    FlapRight.renderWithRotation(f5);
    BowLeftTop.renderWithRotation(f5);
    BowLeftBottom.renderWithRotation(f5);
    BowRightTop.renderWithRotation(f5);
    BowRightBottom.renderWithRotation(f5);
  }
  
  public void render(float f5) {
	  Body.renderWithRotation(f5);
	  HandleMiddle.renderWithRotation(f5);
	  HandleTop.renderWithRotation(f5);
	  HandleBottom.renderWithRotation(f5);
	  HandleEnd.renderWithRotation(f5);
	  TopSpike.renderWithRotation(f5);
	  Barrel.renderWithRotation(f5);
	  Magazine.renderWithRotation(f5);
	  Gunsight.renderWithRotation(f5);
	  FlapLeft.renderWithRotation(f5);
	  FlapRight.renderWithRotation(f5);
	  BowLeftTop.renderWithRotation(f5);
	  BowLeftBottom.renderWithRotation(f5);
	  BowRightTop.renderWithRotation(f5);
	  BowRightBottom.renderWithRotation(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5,e);
  }

}
