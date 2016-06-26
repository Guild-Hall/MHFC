package mhfc.net.client.model.weapon.bowgun;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBLBarrel extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
  
  public ModelBLBarrel()
  {
    textureWidth = 32;
    textureHeight = 32;
    
    Shape1 = new ModelRenderer(this, 0, 20);
    Shape1.addBox(-0.5F, -1F, -10F, 1, 2, 10);
    Shape1.setRotationPoint(0.5F, 1F, 3F);
    Shape1.setTextureSize(32, 32);
    Shape1.mirror = true;
    setRotation(Shape1, 0F, 1.570796F, 0F);
    Shape2 = new ModelRenderer(this, 0, 15);
    Shape2.addBox(-0.5F, 0F, 0.5F, 1, 2, 3);
    Shape2.setRotationPoint(0F, 0F, 3F);
    Shape2.setTextureSize(32, 32);
    Shape2.mirror = true;
    setRotation(Shape2, -0.418879F, 1.570796F, 0F);
    Shape3 = new ModelRenderer(this, 0, 12);
    Shape3.addBox(-1F, -0.5F, -1F, 1, 1, 2);
    Shape3.setRotationPoint(-1.5F, -0.5F, 2F);
    Shape3.setTextureSize(32, 32);
    Shape3.mirror = true;
    setRotation(Shape3, 0F, 1.570796F, 0F);
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
