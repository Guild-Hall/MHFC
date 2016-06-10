package mhfc.net.client.model.weapon.huntinghorn;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHHEliteBagpipe extends ModelBase
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
  
  public ModelHHEliteBagpipe()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Shape1 = new ModelRenderer(this, 14, 9);
      Shape1.addBox(-0.5F, 0F, -0.5F, 1, 10, 1);
      Shape1.setRotationPoint(0F, 2F, 0F);
      Shape1.setTextureSize(64, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 37, 27);
      Shape2.addBox(-1F, -6F, -1F, 2, 9, 2);
      Shape2.setRotationPoint(0F, 0F, 0F);
      Shape2.setTextureSize(64, 64);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 29, 17);
      Shape3.addBox(-2.5F, -11F, -0.5F, 5, 5, 3);
      Shape3.setRotationPoint(0F, 0F, 0F);
      Shape3.setTextureSize(64, 64);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 27, 6);
      Shape4.addBox(-3.5F, -12F, -2F, 7, 7, 2);
      Shape4.setRotationPoint(0F, 0F, 0F);
      Shape4.setTextureSize(64, 64);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 14, 0);
      Shape5.addBox(-1.5F, -3F, -1.5F, 3, 3, 3);
      Shape5.setRotationPoint(0F, 0F, 0F);
      Shape5.setTextureSize(64, 64);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 0, 0);
      Shape6.addBox(-1F, 0F, -1F, 2, 3, 2);
      Shape6.setRotationPoint(0F, 11F, 0F);
      Shape6.setTextureSize(64, 64);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      Shape7 = new ModelRenderer(this, 50, 0);
      Shape7.addBox(0F, -10F, -1.5F, 1, 6, 1);
      Shape7.setRotationPoint(-2F, -6F, 0F);
      Shape7.setTextureSize(64, 64);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 0F);
      Shape8 = new ModelRenderer(this, 50, 0);
      Shape8.addBox(1F, -18F, -1.5F, 1, 6, 1);
      Shape8.setRotationPoint(0F, 2F, 0F);
      Shape8.setTextureSize(64, 64);
      Shape8.mirror = true;
      setRotation(Shape8, 0F, 0F, 0F);
      Shape9 = new ModelRenderer(this, 0, 39);
      Shape9.addBox(-10F, -9F, -1.5F, 20, 1, 1);
      Shape9.setRotationPoint(0F, 0F, 0F);
      Shape9.setTextureSize(64, 64);
      Shape9.mirror = true;
      setRotation(Shape9, 0F, 0F, 0F);
      Shape10 = new ModelRenderer(this, 0, 20);
      Shape10.addBox(-1F, -0.5F, -2F, 3, 2, 2);
      Shape10.setRotationPoint(-8F, -9F, 0F);
      Shape10.setTextureSize(64, 64);
      Shape10.mirror = true;
      setRotation(Shape10, 0F, 0F, 0F);
      Shape11 = new ModelRenderer(this, 0, 20);
      Shape11.addBox(-1.5F, -0.5F, -2F, 3, 2, 2);
      Shape11.setRotationPoint(7F, -9F, 0F);
      Shape11.setTextureSize(64, 64);
      Shape11.mirror = true;
      setRotation(Shape11, 0F, 0F, 0F);
      Shape12 = new ModelRenderer(this, 0, 30);
      Shape12.addBox(0.5F, -2F, -2F, 2, 4, 2);
      Shape12.setRotationPoint(0F, -16F, 0F);
      Shape12.setTextureSize(64, 64);
      Shape12.mirror = true;
      setRotation(Shape12, 0F, 0F, 0F);
      Shape13 = new ModelRenderer(this, 0, 30);
      Shape13.addBox(-2.5F, -16F, -2F, 2, 4, 2);
      Shape13.setRotationPoint(0F, -2F, 0F);
      Shape13.setTextureSize(64, 64);
      Shape13.mirror = true;
      setRotation(Shape13, 0F, 0F, 0F);
      Shape14 = new ModelRenderer(this, 0, 44);
      Shape14.addBox(-4F, -19F, -6F, 8, 17, 3);
      Shape14.setRotationPoint(0F, 0F, 0F);
      Shape14.setTextureSize(64, 64);
      Shape14.mirror = true;
      setRotation(Shape14, 0F, 0F, 0F);
      Shape15 = new ModelRenderer(this, 31, 0);
      Shape15.addBox(-3F, -9F, -3F, 6, 3, 1);
      Shape15.setRotationPoint(0F, 0F, 0F);
      Shape15.setTextureSize(64, 64);
      Shape15.mirror = true;
      setRotation(Shape15, 0F, 0F, 0F);
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
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity x)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5 , x);
  }

}
