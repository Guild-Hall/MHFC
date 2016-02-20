package mhfc.net.client.model.weapon.huntinghorn;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHHBlackCasket extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape14;
    ModelRenderer Shape4;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;
  
  public ModelHHBlackCasket()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Shape1 = new ModelRenderer(this, 35, 0);
      Shape1.addBox(-0.5F, 0F, -0.5F, 1, 10, 1);
      Shape1.setRotationPoint(0F, 2F, 0F);
      Shape1.setTextureSize(64, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 30, 20);
      Shape2.addBox(-1F, -11F, -1F, 2, 5, 2);
      Shape2.setRotationPoint(0F, 11F, 0F);
      Shape2.setTextureSize(64, 64);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 15, 0);
      Shape3.addBox(-1.5F, -11F, -0.5F, 3, 3, 3);
      Shape3.setRotationPoint(0F, 1.5F, -3F);
      Shape3.setTextureSize(64, 64);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 0, 0);
      Shape5.addBox(-1.5F, -6F, -1.5F, 3, 4, 3);
      Shape5.setRotationPoint(0F, 4F, 0F);
      Shape5.setTextureSize(64, 64);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 50, 0);
      Shape6.addBox(-1F, 0F, -1F, 2, 3, 2);
      Shape6.setRotationPoint(0F, 11F, 0F);
      Shape6.setTextureSize(64, 64);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      Shape14 = new ModelRenderer(this, 0, 41);
      Shape14.addBox(-4F, -22F, -4F, 8, 18, 4);
      Shape14.setRotationPoint(0F, 5F, 2F);
      Shape14.setTextureSize(64, 64);
      Shape14.mirror = true;
      setRotation(Shape14, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 0, 20);
      Shape4.addBox(-5F, -20F, -1.5F, 10, 15, 3);
      Shape4.setRotationPoint(0F, 5F, 0F);
      Shape4.setTextureSize(64, 64);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape7 = new ModelRenderer(this, 40, 0);
      Shape7.addBox(-0.5F, -4F, -0.5F, 1, 6, 1);
      Shape7.setRotationPoint(0F, 5F, 1F);
      Shape7.setTextureSize(64, 64);
      Shape7.mirror = true;
      setRotation(Shape7, -0.4537856F, 0F, 0F);
      Shape8 = new ModelRenderer(this, 50, 38);
      Shape8.addBox(0F, 0F, -2.5F, 2, 21, 5);
      Shape8.setRotationPoint(-5F, -17.2F, 0F);
      Shape8.setTextureSize(64, 64);
      Shape8.mirror = true;
      setRotation(Shape8, 0F, 0F, -0.4014257F);
      Shape9 = new ModelRenderer(this, 50, 38);
      Shape9.addBox(0F, 0F, -2.3F, 2, 21, 5);
      Shape9.setRotationPoint(4F, -17.5F, 0F);
      Shape9.setTextureSize(64, 64);
      Shape9.mirror = true;
      setRotation(Shape9, 0F, 0F, 0.4363323F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    render(f5);
  
  }
  
  public void render(float f5) {
	  Shape1.renderWithRotation(f5);
	    Shape2.renderWithRotation(f5);
	    Shape3.renderWithRotation(f5);
	    Shape5.renderWithRotation(f5);
	    Shape6.renderWithRotation(f5);
	    Shape14.renderWithRotation(f5);
	    Shape4.renderWithRotation(f5);
	    Shape7.renderWithRotation(f5);
	    Shape8.renderWithRotation(f5);
	    Shape9.renderWithRotation(f5);
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
