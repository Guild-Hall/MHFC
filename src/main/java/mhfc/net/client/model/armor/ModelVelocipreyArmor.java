package mhfc.net.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelVelocipreyArmor extends ModelBiped
{
  //fields
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;
    ModelRenderer Shape10;
    ModelRenderer Shape11;
    ModelRenderer Shape12;
    ModelRenderer Shape13;
    ModelRenderer Shape14;
    ModelRenderer Shape15;
    ModelRenderer body;
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape5;
    ModelRenderer Shape16;
    ModelRenderer Shape17;
  
  public ModelVelocipreyArmor(float f)
  {
	  super(f, 0, 128, 128);
	  textureWidth = 128;
	  textureHeight = 128;
      Shape3 = new ModelRenderer(this, 18, 35);
      Shape3.addBox(-5F, -8F, -4F, 1, 8, 8);
      Shape3.setRotationPoint(0F, 0F, 0F);
      Shape3.setTextureSize(128, 128);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 36, 35);
      Shape4.addBox(-4F, -8F, 4F, 8, 8, 1);
      Shape4.setRotationPoint(0F, 0F, 0F);
      Shape4.setTextureSize(128, 128);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 16, 70);
      Shape6.addBox(-0.5F, -11F, -3.3F, 1, 2, 1);
      Shape6.setRotationPoint(0F, 0F, 0F);
      Shape6.setTextureSize(128, 128);
      Shape6.mirror = true;
      setRotation(Shape6, -0.7853982F, 0F, 0F);
      Shape7 = new ModelRenderer(this, 0, 67);
      Shape7.addBox(-1F, -9F, 4F, 2, 1, 1);
      Shape7.setRotationPoint(0F, 0F, 0F);
      Shape7.setTextureSize(128, 128);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 0F);
      Shape8 = new ModelRenderer(this, 0, 69);
      Shape8.addBox(-1F, -8F, 8.4F, 2, 4, 2);
      Shape8.setRotationPoint(0F, 0F, 0F);
      Shape8.setTextureSize(128, 128);
      Shape8.mirror = true;
      setRotation(Shape8, 0.3490659F, 0F, 0F);
      Shape9 = new ModelRenderer(this, 0, 46);
      Shape9.addBox(-2F, -3F, -4.5F, 4, 3, 1);
      Shape9.setRotationPoint(0F, 0F, 0F);
      Shape9.setTextureSize(128, 128);
      Shape9.mirror = true;
      setRotation(Shape9, 0.5934119F, 0F, 0F);
      Shape10 = new ModelRenderer(this, 0, 75);
      Shape10.addBox(-3F, 1F, -4F, 6, 1, 1);
      Shape10.setRotationPoint(0F, 0F, 0F);
      Shape10.setTextureSize(128, 128);
      Shape10.mirror = true;
      setRotation(Shape10, 0F, 0F, 0F);
      Shape11 = new ModelRenderer(this, 24, 51);
      Shape11.addBox(-4F, 1F, -3F, 8, 10, 1);
      Shape11.setRotationPoint(0F, 0F, 0F);
      Shape11.setTextureSize(128, 128);
      Shape11.mirror = true;
      setRotation(Shape11, 0F, 0F, 0F);
      Shape12 = new ModelRenderer(this, 42, 51);
      Shape12.addBox(-4F, 2F, -4F, 8, 8, 1);
      Shape12.setRotationPoint(0F, 0F, 0F);
      Shape12.setTextureSize(128, 128);
      Shape12.mirror = true;
      setRotation(Shape12, 0F, 0F, 0F);
      Shape13 = new ModelRenderer(this, 60, 51);
      Shape13.addBox(-4F, 1F, 2F, 8, 10, 1);
      Shape13.setRotationPoint(0F, 0F, 0F);
      Shape13.setTextureSize(128, 128);
      Shape13.mirror = true;
      setRotation(Shape13, 0F, 0F, 0F);
      Shape14 = new ModelRenderer(this, 78, 51);
      Shape14.addBox(-1.5F, -3F, -2.5F, 5, 6, 5);
      Shape14.setRotationPoint(0F, 0F, 0F);
      Shape14.setTextureSize(128, 128);
      Shape14.mirror = true;
      setRotation(Shape14, 0F, 0F, 0F);
      Shape15 = new ModelRenderer(this, 98, 51);
      Shape15.addBox(-3.5F, -3F, -2.5F, 5, 6, 5);
      Shape15.setRotationPoint(0F, 0F, 0F);
      Shape15.setTextureSize(128, 128);
      Shape15.mirror = true;
      setRotation(Shape15, 0F, 0F, 0F);
      body = new ModelRenderer(this, 0, 51);
      body.addBox(-4F, 0F, -2F, 8, 11, 4);
      body.setRotationPoint(0F, 0F, 0F);
      body.setTextureSize(128, 128);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      Shape1 = new ModelRenderer(this, 0, 35);
      Shape1.addBox(-4F, -8F, -5F, 8, 8, 1);
      Shape1.setRotationPoint(0F, 0F, 0F);
      Shape1.setTextureSize(128, 128);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 18, 35);
      Shape2.addBox(4F, -8F, -4F, 1, 8, 8);
      Shape2.setRotationPoint(0F, 0F, 0F);
      Shape2.setTextureSize(128, 128);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 27, 65);
      Shape5.addBox(-4F, -9F, -4F, 8, 1, 8);
      Shape5.setRotationPoint(0F, 0F, 0F);
      Shape5.setTextureSize(128, 128);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape16 = new ModelRenderer(this, 21, 78);
      Shape16.addBox(-2.5F, 10F, -2.5F, 5, 2, 5);
      Shape16.setRotationPoint(0F, 0F, 0F);
      Shape16.setTextureSize(128, 128);
      Shape16.mirror = true;
      setRotation(Shape16, 0F, 0F, 0F);
      Shape17 = new ModelRenderer(this, 21, 78);
      Shape17.addBox(-2.5F, 10F, -2.5F, 5, 2, 5);
      Shape17.setRotationPoint(0F, 0F, 0F);
      Shape17.setTextureSize(128, 128);
      Shape17.mirror = true;
      setRotation(Shape17, 0F, 0F, 0F);
      
      
      bipedHead.addChild(Shape1);
      bipedHead.addChild(Shape2);
      bipedHead.addChild(Shape3);
      bipedHead.addChild(Shape4);
      bipedHead.addChild(Shape5);
      bipedHead.addChild(Shape6);
      bipedHead.addChild(Shape7);
      bipedHead.addChild(Shape8);
      bipedBody.addChild(Shape9);
      bipedBody.addChild(Shape10);
      bipedBody.addChild(Shape11);
      bipedBody.addChild(Shape12);
      bipedBody.addChild(Shape13);
      bipedBody.addChild(body);
      bipedLeftArm.addChild(Shape14);
      bipedRightArm.addChild(Shape15);
      bipedRightLeg.addChild(Shape16);
      bipedLeftLeg.addChild(Shape17);
      
      
      
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
   
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
  }

}
