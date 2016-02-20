package mhfc.net.client.model.weapon.huntinghorn;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHHWarDrums extends ModelBase
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
  
  public ModelHHWarDrums()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Shape1 = new ModelRenderer(this, 0, 0);
      Shape1.addBox(-0.5F, 0F, -0.5F, 1, 10, 1);
      Shape1.setRotationPoint(0F, 2F, 0F);
      Shape1.setTextureSize(64, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 19, 0);
      Shape2.addBox(-1F, -6F, -1F, 2, 9, 2);
      Shape2.setRotationPoint(0F, 0F, 0F);
      Shape2.setTextureSize(64, 64);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 0, 39);
      Shape3.addBox(-2.5F, -11F, 1.5F, 5, 5, 1);
      Shape3.setRotationPoint(0F, 0F, 0F);
      Shape3.setTextureSize(64, 64);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 0, 20);
      Shape4.addBox(-3.5F, -12F, -2F, 7, 7, 4);
      Shape4.setRotationPoint(-4F, 0F, 0F);
      Shape4.setTextureSize(64, 64);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 29, 0);
      Shape5.addBox(-1.5F, -3F, -1.5F, 3, 3, 3);
      Shape5.setRotationPoint(0F, 0F, 0F);
      Shape5.setTextureSize(64, 64);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 0, 32);
      Shape6.addBox(-1F, 0F, -1F, 2, 3, 2);
      Shape6.setRotationPoint(0F, 11F, 0F);
      Shape6.setTextureSize(64, 64);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      Shape7 = new ModelRenderer(this, 0, 20);
      Shape7.addBox(0.5F, -12F, -2F, 7, 7, 4);
      Shape7.setRotationPoint(-1F, 0F, 0F);
      Shape7.setTextureSize(64, 64);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 0F);
      Shape8 = new ModelRenderer(this, 0, 20);
      Shape8.addBox(-3.5F, -19F, -2F, 7, 7, 4);
      Shape8.setRotationPoint(0F, 0F, 0F);
      Shape8.setTextureSize(64, 64);
      Shape8.mirror = true;
      setRotation(Shape8, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5 , entity );

  }
  
  public void render(float f5)  {
	    Shape1.renderWithRotation(f5);
	    Shape2.renderWithRotation(f5);
	    Shape3.renderWithRotation(f5);
	    Shape4.renderWithRotation(f5);
	    Shape5.renderWithRotation(f5);
	    Shape6.renderWithRotation(f5);
	    Shape7.renderWithRotation(f5);
	    Shape8.renderWithRotation(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e )
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
  }

}
