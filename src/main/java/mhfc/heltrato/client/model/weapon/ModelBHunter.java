package mhfc.heltrato.client.model.weapon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBHunter extends ModelBase
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
    ModelRenderer Shape10;
    ModelRenderer Shape11;
    ModelRenderer Shape12;
    ModelRenderer Shape13;
    ModelRenderer Shape14;
    ModelRenderer Shape15;
  
  public ModelBHunter()
  {
	  textureWidth = 64;
	  textureHeight = 64;
    
      Shape1 = new ModelRenderer(this, 0, 19);
      Shape1.addBox(-1F, -3F, -1F, 2, 3, 2);
      Shape1.setRotationPoint(0F, 0F, 0F);
      Shape1.setTextureSize(64, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 10, 19);
      Shape2.addBox(-1F, -1F, -1.5F, 2, 3, 3);
      Shape2.setRotationPoint(0F, 2F, 0F);
      Shape2.setTextureSize(64, 64);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 42, 0);
      Shape3.addBox(-9F, -1F, -0.5F, 10, 1, 1);
      Shape3.setRotationPoint(0F, -1F, 0F);
      Shape3.setTextureSize(64, 64);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 43, 4);
      Shape4.addBox(-7F, -1F, -0.5F, 7, 1, 1);
      Shape4.setRotationPoint(0F, 3F, 0F);
      Shape4.setTextureSize(64, 64);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 35, 0);
      Shape5.addBox(0F, -7F, -0.5F, 1, 7, 1);
      Shape5.setRotationPoint(0F, -2F, 0F);
      Shape5.setTextureSize(64, 64);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0.3346075F);
      Shape6 = new ModelRenderer(this, 30, 0);
      Shape6.addBox(-0.7F, 1F, -0.5F, 1, 8, 1);
      Shape6.setRotationPoint(0F, 2.5F, 0F);
      Shape6.setTextureSize(64, 64);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, -0.4089647F);
      Shape7 = new ModelRenderer(this, 25, 0);
      Shape7.addBox(-0.7F, -3.5F, -0.5F, 1, 4, 1);
      Shape7.setRotationPoint(3F, -8F, 0F);
      Shape7.setTextureSize(64, 64);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 1.226894F);
      Shape8 = new ModelRenderer(this, 25, 0);
      Shape8.addBox(0F, -5F, -0.5F, 1, 4, 1);
      Shape8.setRotationPoint(2F, 10.5F, 0F);
      Shape8.setTextureSize(64, 64);
      Shape8.mirror = true;
      setRotation(Shape8, 0F, 0F, 1.417331F);
      Shape9 = new ModelRenderer(this, 45, 31);
      Shape9.addBox(0F, -1F, 0F, 1, 20, 1);
      Shape9.setRotationPoint(4F, -8F, -0.5F);
      Shape9.setTextureSize(64, 64);
      Shape9.mirror = true;
      setRotation(Shape9, 0F, 0F, 0F);
      Shape10 = new ModelRenderer(this, 18, 0);
      Shape10.addBox(0F, 0F, 0F, 1, 10, 1);
      Shape10.setRotationPoint(4F, -9F, -0.5F);
      Shape10.setTextureSize(64, 64);
      Shape10.mirror = true;
      setRotation(Shape10, 0F, 0F, -0.2602503F);
      Shape11 = new ModelRenderer(this, 18, 0);
      Shape11.addBox(0F, 0F, 0F, 1, 10, 1);
      Shape11.setRotationPoint(6.5F, 0F, -0.5F);
      Shape11.setTextureSize(64, 64);
      Shape11.mirror = true;
      setRotation(Shape11, 0F, 0F, 0.2010743F);
      Shape12 = new ModelRenderer(this, 0, 0);
      Shape12.addBox(0F, 0F, -0.5F, 1, 11, 1);
      Shape12.setRotationPoint(4F, -8.5F, 0F);
      Shape12.setTextureSize(64, 64);
      Shape12.mirror = true;
      setRotation(Shape12, 0F, 0F, -0.5585054F);
      Shape13 = new ModelRenderer(this, 0, 0);
      Shape13.addBox(1F, -11F, -0.5F, 1, 12, 1);
      Shape13.setRotationPoint(3.5F, 9F, 0F);
      Shape13.setTextureSize(64, 64);
      Shape13.mirror = true;
      setRotation(Shape13, 0F, 0F, 0.5136845F);
      Shape14 = new ModelRenderer(this, 0, 54);
      Shape14.addBox(-13F, 0F, 0F, 20, 1, 1);
      Shape14.setRotationPoint(0F, 0F, -0.5F);
      Shape14.setTextureSize(64, 64);
      Shape14.mirror = true;
      setRotation(Shape14, 0F, 0F, 0F);
      Shape15 = new ModelRenderer(this, 0, 50);
      Shape15.addBox(-10F, 0F, 0F, 20, 1, 1);
      Shape15.setRotationPoint(0F, 0F, -0.5F);
      Shape15.setTextureSize(64, 64);
      Shape15.mirror = true;
      setRotation(Shape15, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    render(f5);
   
  }
  
  public void render(float f5) {
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
	    Shape12.renderWithRotation(f5);
	    Shape13.renderWithRotation(f5);
	    Shape14.renderWithRotation(f5);
	    Shape15.renderWithRotation(f5);
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
