package mhfc.net.client.model.weapon.huntinghorn;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHHIvoryHorn extends ModelBase
{
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
  
  public ModelHHIvoryHorn()
  {
    textureWidth = 32;
    textureHeight = 32;
    
      Shape1 = new ModelRenderer(this, 0, 0);
      Shape1.addBox(0F, 0F, 0F, 1, 10, 1);
      Shape1.setRotationPoint(0F, 5F, 0F);
      Shape1.setTextureSize(32, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 4, 0);
      Shape2.addBox(0F, 0F, 0F, 1, 2, 1);
      Shape2.setRotationPoint(-0.5F, 14F, 0F);
      Shape2.setTextureSize(32, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 10, 13);
      Shape3.addBox(0F, 0F, 0F, 2, 3, 2);
      Shape3.setRotationPoint(-1F, 2F, -0.5F);
      Shape3.setTextureSize(32, 32);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 0, 13);
      Shape4.addBox(0F, 0F, 0F, 2, 2, 3);
      Shape4.setRotationPoint(-1.5F, 0F, -1F);
      Shape4.setTextureSize(32, 32);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 0, 24);
      Shape5.addBox(0F, 0F, 0F, 3, 4, 4);
      Shape5.setRotationPoint(-3F, -4.033333F, -1.5F);
      Shape5.setTextureSize(32, 32);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 0, 18);
      Shape6.addBox(0F, -3F, -1.5F, 1, 3, 3);
      Shape6.setRotationPoint(0F, 0F, 0.5F);
      Shape6.setTextureSize(32, 32);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      Shape7 = new ModelRenderer(this, 28, 28);
      Shape7.addBox(0F, -3F, 0F, 1, 3, 1);
      Shape7.setRotationPoint(-2.5F, -3F, 1F);
      Shape7.setTextureSize(32, 32);
      Shape7.mirror = true;
      setRotation(Shape7, -0.3490659F, 0F, 0F);
      Shape8 = new ModelRenderer(this, 28, 28);
      Shape8.addBox(0F, -3F, -1F, 1, 3, 1);
      Shape8.setRotationPoint(-2.5F, -3F, 0.03333334F);
      Shape8.setTextureSize(32, 32);
      Shape8.mirror = true;
      setRotation(Shape8, 0.3490659F, 0F, 0F);
      Shape9 = new ModelRenderer(this, 22, 29);
      Shape9.addBox(0F, 0F, 0F, 1, 1, 2);
      Shape9.setRotationPoint(-2F, -2F, 2F);
      Shape9.setTextureSize(32, 32);
      Shape9.mirror = true;
      setRotation(Shape9, 0F, 0F, 0F);
      Shape10 = new ModelRenderer(this, 22, 29);
      Shape10.addBox(0F, 0F, -2F, 1, 1, 2);
      Shape10.setRotationPoint(-2F, -2F, -1F);
      Shape10.setTextureSize(32, 32);
      Shape10.mirror = true;
      setRotation(Shape10, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    render(f5);
  }
  
  public void render(float f5){
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
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5,e);
  }

}
