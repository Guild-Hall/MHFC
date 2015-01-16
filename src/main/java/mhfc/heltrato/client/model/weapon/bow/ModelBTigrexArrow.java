package mhfc.heltrato.client.model.weapon.bow;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBTigrexArrow extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2A;
    ModelRenderer Shape3a;
    ModelRenderer Shape4a;
    ModelRenderer Shape3b;
    ModelRenderer Shape4b;
    ModelRenderer Shape5;
    ModelRenderer Shape5a;
    ModelRenderer Shape5b;
    ModelRenderer Shape5c;
    ModelRenderer Shape5d;
    ModelRenderer Shape5e;
    ModelRenderer Shape6a;
    ModelRenderer Shape6b;
    ModelRenderer Shape7a;
    ModelRenderer Shape7b;
    ModelRenderer Shape8a;
    ModelRenderer Shape8b;
    ModelRenderer Shape9a;
    ModelRenderer Shape10b;
    ModelRenderer Shape10c;
    ModelRenderer Shape10e;
    ModelRenderer Shape10f;
    ModelRenderer Shape11h;
    ModelRenderer Shape12j;
    ModelRenderer Shape11k;
    ModelRenderer Shape12l;
  
  public ModelBTigrexArrow()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      Shape1 = new ModelRenderer(this, 25, 0);
      Shape1.addBox(0F, 0F, 0F, 3, 3, 2);
      Shape1.setRotationPoint(-1F, -13.5F, -1F);
      Shape1.setTextureSize(128, 128);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2A = new ModelRenderer(this, 0, 12);
      Shape2A.addBox(0F, -1F, 0F, 5, 3, 3);
      Shape2A.setRotationPoint(-2.5F, -8.5F, -1.5F);
      Shape2A.setTextureSize(128, 128);
      Shape2A.mirror = true;
      setRotation(Shape2A, 0F, 0F, 0F);
      Shape3a = new ModelRenderer(this, 0, 100);
      Shape3a.addBox(0F, 0F, 0F, 6, 3, 6);
      Shape3a.setRotationPoint(-3.5F, -16F, -3F);
      Shape3a.setTextureSize(128, 128);
      Shape3a.mirror = true;
      setRotation(Shape3a, 0F, 0F, 0F);
      Shape4a = new ModelRenderer(this, 0, 115);
      Shape4a.addBox(0F, 0F, 0F, 5, 1, 4);
      Shape4a.setRotationPoint(-2.8F, -17F, -2F);
      Shape4a.setTextureSize(128, 128);
      Shape4a.mirror = true;
      setRotation(Shape4a, 0F, 0F, 0F);
      Shape3b = new ModelRenderer(this, 0, 100);
      Shape3b.addBox(0F, 0F, 0F, 6, 3, 6);
      Shape3b.setRotationPoint(-3.5F, -8F, -3F);
      Shape3b.setTextureSize(128, 128);
      Shape3b.mirror = true;
      setRotation(Shape3b, 0F, 0F, 0F);
      Shape4b = new ModelRenderer(this, 0, 115);
      Shape4b.addBox(0F, 0F, 0F, 5, 1, 4);
      Shape4b.setRotationPoint(-2.8F, -5F, -2F);
      Shape4b.setTextureSize(128, 128);
      Shape4b.mirror = true;
      setRotation(Shape4b, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 0, 81);
      Shape5.addBox(1F, 0F, 0.5F, 4, 3, 2);
      Shape5.setRotationPoint(-6.5F, -16F, -5F);
      Shape5.setTextureSize(128, 128);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, -0.3490659F, 0F);
      Shape5a = new ModelRenderer(this, 0, 81);
      Shape5a.addBox(-7F, -2F, -2F, 4, 3, 2);
      Shape5a.setRotationPoint(0F, -6F, 0F);
      Shape5a.setTextureSize(128, 128);
      Shape5a.mirror = true;
      setRotation(Shape5a, 0F, -0.3490659F, 0F);
      Shape5b = new ModelRenderer(this, 0, 81);
      Shape5b.addBox(0F, 0F, 0F, 4, 3, 2);
      Shape5b.setRotationPoint(-7F, -16F, -1F);
      Shape5b.setTextureSize(128, 128);
      Shape5b.mirror = true;
      setRotation(Shape5b, 0F, 0F, 0F);
      Shape5c = new ModelRenderer(this, 0, 81);
      Shape5c.addBox(-8F, 0F, 1F, 4, 3, 2);
      Shape5c.setRotationPoint(0F, -16F, -1F);
      Shape5c.setTextureSize(128, 128);
      Shape5c.mirror = true;
      setRotation(Shape5c, 0F, 0.3490659F, 0F);
      Shape5d = new ModelRenderer(this, 0, 81);
      Shape5d.addBox(-7F, -2F, 0F, 4, 3, 2);
      Shape5d.setRotationPoint(0F, -6F, 0F);
      Shape5d.setTextureSize(128, 128);
      Shape5d.mirror = true;
      setRotation(Shape5d, 0F, 0.3490659F, 0F);
      Shape5e = new ModelRenderer(this, 0, 81);
      Shape5e.addBox(0F, -2F, 0F, 4, 3, 2);
      Shape5e.setRotationPoint(-7F, -6F, -1F);
      Shape5e.setTextureSize(128, 128);
      Shape5e.mirror = true;
      setRotation(Shape5e, 0F, 0F, 0F);
      Shape6a = new ModelRenderer(this, 0, 40);
      Shape6a.addBox(-1.5F, 0F, -1.5F, 3, 12, 3);
      Shape6a.setRotationPoint(0F, -29F, 0F);
      Shape6a.setTextureSize(128, 128);
      Shape6a.mirror = true;
      setRotation(Shape6a, 0F, 0F, 0F);
      Shape6b = new ModelRenderer(this, 0, 60);
      Shape6b.addBox(-1.5F, 0F, -1.5F, 3, 12, 3);
      Shape6b.setRotationPoint(0F, -4F, 0F);
      Shape6b.setTextureSize(128, 128);
      Shape6b.mirror = true;
      setRotation(Shape6b, 0F, 0F, 0F);
      Shape7a = new ModelRenderer(this, 15, 40);
      Shape7a.addBox(-1.5F, -16F, -1.5F, 3, 15, 3);
      Shape7a.setRotationPoint(-1.2F, -27F, 0F);
      Shape7a.setTextureSize(128, 128);
      Shape7a.mirror = true;
      setRotation(Shape7a, 0F, 0F, 0.8028515F);
      Shape7b = new ModelRenderer(this, 15, 65);
      Shape7b.addBox(-1.5F, 0F, -1.5F, 3, 15, 3);
      Shape7b.setRotationPoint(-0.4F, 7F, 0F);
      Shape7b.setTextureSize(128, 128);
      Shape7b.mirror = true;
      setRotation(Shape7b, 0F, 0F, -0.8028515F);
      Shape8a = new ModelRenderer(this, 0, 25);
      Shape8a.addBox(-1F, 0F, -1F, 2, 4, 2);
      Shape8a.setRotationPoint(13F, -41F, 0F);
      Shape8a.setTextureSize(128, 128);
      Shape8a.mirror = true;
      setRotation(Shape8a, 0F, 0F, 0.8028515F);
      Shape8b = new ModelRenderer(this, 0, 25);
      Shape8b.addBox(-1F, 0F, -1F, 2, 4, 2);
      Shape8b.setRotationPoint(10F, 17F, 0F);
      Shape8b.setTextureSize(128, 128);
      Shape8b.mirror = true;
      setRotation(Shape8b, 0F, 0F, -0.8028515F);
      Shape9a = new ModelRenderer(this, 100, 18);
      Shape9a.addBox(-0.5F, -11F, -0.5F, 1, 53, 1);
      Shape9a.setRotationPoint(10F, -26F, 0F);
      Shape9a.setTextureSize(128, 128);
      Shape9a.mirror = true;
      setRotation(Shape9a, 0F, 0F, 0F);
      Shape10b = new ModelRenderer(this, 110, 20);
      Shape10b.addBox(-0.5F, 0F, -0.5F, 1, 27, 1);
      Shape10b.setRotationPoint(10F, -36F, 0F);
      Shape10b.setTextureSize(128, 128);
      Shape10b.mirror = true;
      setRotation(Shape10b, 0F, 0F, -0.296706F);
      Shape10c = new ModelRenderer(this, 110, 20);
      Shape10c.addBox(-0.5F, -27F, -0.5F, 1, 27, 1);
      Shape10c.setRotationPoint(10F, 16F, 0F);
      Shape10c.setTextureSize(128, 128);
      Shape10c.mirror = true;
      setRotation(Shape10c, 0F, 0F, 0.296706F);
      Shape10e = new ModelRenderer(this, 120, 20);
      Shape10e.addBox(-0.5F, 0F, -0.5F, 1, 29, 1);
      Shape10e.setRotationPoint(10F, -36F, 0F);
      Shape10e.setTextureSize(128, 128);
      Shape10e.mirror = true;
      setRotation(Shape10e, 0F, 0F, -0.4363323F);
      Shape10f = new ModelRenderer(this, 120, 20);
      Shape10f.addBox(-0.5F, -29F, -0.5F, 1, 29, 1);
      Shape10f.setRotationPoint(10F, 16F, 0F);
      Shape10f.setTextureSize(128, 128);
      Shape10f.mirror = true;
      setRotation(Shape10f, 0F, 0F, 0.4363323F);
      Shape11h = new ModelRenderer(this, 50, 80);
      Shape11h.addBox(-23F, 0F, 0F, 35, 1, 1);
      Shape11h.setRotationPoint(6F, -10.5F, 0F);
      Shape11h.setTextureSize(128, 128);
      Shape11h.mirror = true;
      setRotation(Shape11h, 0F, 0F, 0F);
      Shape12j = new ModelRenderer(this, 50, 50);
      Shape12j.addBox(-1F, 0F, -1F, 3, 3, 3);
      Shape12j.setRotationPoint(-18F, -11F, 0F);
      Shape12j.setTextureSize(128, 128);
      Shape12j.mirror = true;
      setRotation(Shape12j, 0F, 0F, -0.7853982F);
      Shape11k = new ModelRenderer(this, 50, 90);
      Shape11k.addBox(0F, 0F, 0F, 29, 1, 1);
      Shape11k.setRotationPoint(-7F, -10.5F, 0F);
      Shape11k.setTextureSize(128, 128);
      Shape11k.mirror = true;
      setRotation(Shape11k, 0F, 0F, 0F);
      Shape12l = new ModelRenderer(this, 70, 50);
      Shape12l.addBox(-1F, 0F, -1F, 3, 3, 3);
      Shape12l.setRotationPoint(-8F, -11F, 0F);
      Shape12l.setTextureSize(128, 128);
      Shape12l.mirror = true;
      setRotation(Shape12l, 0F, 0F, -0.7853982F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    render(f5);
   
  }
  
  public void render(float f5) {
	  	Shape1.render(f5);
	  	Shape2A.render(f5);
	    Shape3a.render(f5);
	    Shape4a.render(f5);
	    Shape3b.render(f5);
	    Shape4b.render(f5);
	    Shape5.render(f5);
	    Shape5a.render(f5);
	    Shape5b.render(f5);
	    Shape5c.render(f5);
	    Shape5d.render(f5);
	    Shape5e.render(f5);
	    Shape6a.render(f5);
	    Shape6b.render(f5);
	    Shape7a.render(f5);
	    Shape7b.render(f5);
	    Shape8a.render(f5);
	    Shape8b.render(f5);
	    Shape9a.render(f5);
	    Shape10b.render(f5);
	    Shape10c.render(f5);
	    Shape10e.render(f5);
	    Shape10f.render(f5);
	    Shape11h.render(f5);
	    Shape12j.render(f5);
	    Shape11k.render(f5);
	    Shape12l.render(f5);
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
