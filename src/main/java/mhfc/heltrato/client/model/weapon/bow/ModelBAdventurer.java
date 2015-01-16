package mhfc.heltrato.client.model.weapon.bow;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBAdventurer extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    public ModelRenderer string;
    ModelRenderer arrowBaseA;
    ModelRenderer arrowHeadA;
    ModelRenderer stringUpperA;
    ModelRenderer stringLowerB;
    ModelRenderer arrowBaseB;
    ModelRenderer arrowHeadB;
    ModelRenderer sharpA;
    ModelRenderer sharpB;
  
  public ModelBAdventurer()
  {
	  	textureWidth = 64;
	  	textureHeight = 64;
      Shape1 = new ModelRenderer(this, 0, 22);
      Shape1.addBox(-0.5F, -3F, 0F, 1, 3, 2);
      Shape1.setRotationPoint(0F, -4F, 0F);
      Shape1.setTextureSize(64, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 0, 29);
      Shape2.addBox(-0.5F, 0F, 0F, 1, 1, 1);
      Shape2.setRotationPoint(0F, -4F, 0F);
      Shape2.setTextureSize(64, 64);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 0, 22);
      Shape3.addBox(-0.5F, -3F, 0F, 1, 3, 2);
      Shape3.setRotationPoint(0F, 0F, 0F);
      Shape3.setTextureSize(64, 64);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 0, 47);
      Shape4.addBox(-0.5F, -6F, -1F, 1, 6, 2);
      Shape4.setRotationPoint(0F, -6.7F, 1F);
      Shape4.setTextureSize(64, 64);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, -0.3141593F);
      Shape5 = new ModelRenderer(this, 0, 36);
      Shape5.addBox(-0.5F, -8F, -1F, 1, 8, 2);
      Shape5.setRotationPoint(-1.7F, -12F, 1F);
      Shape5.setTextureSize(64, 64);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, -0.5235988F);
      Shape6 = new ModelRenderer(this, 0, 47);
      Shape6.addBox(-0.5F, 0F, -1F, 1, 6, 2);
      Shape6.setRotationPoint(0F, 0F, 1F);
      Shape6.setTextureSize(64, 64);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0.3141593F);
      Shape7 = new ModelRenderer(this, 0, 36);
      Shape7.addBox(-0.5F, 0.5F, -1F, 1, 8, 2);
      Shape7.setRotationPoint(-1.6F, 5F, 1F);
      Shape7.setTextureSize(64, 64);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 0.5235988F);
      string = new ModelRenderer(this, 37, 0);
      string.addBox(-0.5F, 0F, 0.5F, 1, 30, 1);
      string.setRotationPoint(-5.5F, -18.5F, 0.5F);
      string.setTextureSize(64, 64);
      string.mirror = true;
      setRotation(string, 0F, 0F, 0F);
      arrowBaseA = new ModelRenderer(this, 14, 41);
      arrowBaseA.addBox(0F, 0F, 0F, 15, 1, 1);
      arrowBaseA.setRotationPoint(-5F, -4F, 1F);
      arrowBaseA.setTextureSize(64, 64);
      arrowBaseA.mirror = true;
      setRotation(arrowBaseA, 0F, 0F, 0F);
      arrowHeadA = new ModelRenderer(this, 12, 0);
      arrowHeadA.addBox(-2F, -2F, 0F, 3, 3, 1);
      arrowHeadA.setRotationPoint(12F, -3.5F, 1F);
      arrowHeadA.setTextureSize(64, 64);
      arrowHeadA.mirror = true;
      setRotation(arrowHeadA, 0F, 0F, -0.7853982F);
      stringUpperA = new ModelRenderer(this, 37, 0);
      stringUpperA.addBox(-0.5F, 0F, -0.5F, 1, 16, 1);
      stringUpperA.setRotationPoint(-5.5F, -18.5F, 1.5F);
      stringUpperA.setTextureSize(64, 64);
      stringUpperA.mirror = true;
      setRotation(stringUpperA, 0F, 0F, 0.3839724F);
      stringLowerB = new ModelRenderer(this, 37, 0);
      stringLowerB.addBox(0F, -17F, 0F, 1, 17, 1);
      stringLowerB.setRotationPoint(-6F, 12F, 1F);
      stringLowerB.setTextureSize(64, 64);
      stringLowerB.mirror = true;
      setRotation(stringLowerB, 0F, 0F, -0.3665191F);
      arrowBaseB = new ModelRenderer(this, 14, 41);
      arrowBaseB.addBox(-11F, 0F, 1F, 13, 1, 1);
      arrowBaseB.setRotationPoint(0F, -4F, 0F);
      arrowBaseB.setTextureSize(64, 64);
      arrowBaseB.mirror = true;
      setRotation(arrowBaseB, 0F, 0F, 0F);
      arrowHeadB = new ModelRenderer(this, 12, 0);
      arrowHeadB.addBox(0F, 0F, 0F, 3, 3, 1);
      arrowHeadB.setRotationPoint(1F, -3.5F, 1F);
      arrowHeadB.setTextureSize(64, 64);
      arrowHeadB.mirror = true;
      setRotation(arrowHeadB, 0F, 0F, -0.7853982F);
      sharpA = new ModelRenderer(this, 0, 10);
      sharpA.addBox(-0.5F, -6F, -0.5F, 1, 6, 1);
      sharpA.setRotationPoint(-2F, -12F, 1F);
      sharpA.setTextureSize(64, 64);
      sharpA.mirror = true;
      setRotation(sharpA, 0F, 0F, 0.9948377F);
      sharpB = new ModelRenderer(this, 0, 10);
      sharpB.addBox(-0.5F, -6F, -0.5F, 1, 6, 1);
      sharpB.setRotationPoint(3F, 10F, 1F);
      sharpB.setTextureSize(64, 64);
      sharpB.mirror = true;
      setRotation(sharpB, 0F, 0F, -0.9948377F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
   
  }
  
  public void render(float f5){
	  //base
	    Shape1.render(f5);
	    Shape2.render(f5);
	    Shape3.render(f5);
	    Shape4.render(f5);
	    Shape5.render(f5);
	    Shape6.render(f5);
	    Shape7.render(f5);
	    string.render(f5);
	    sharpA.render(f5);
	    sharpB.render(f5);
	    //pull
	    arrowBaseA.render(f5);
	    arrowHeadA.render(f5);
	    
	    //pullx2
	    arrowBaseB.render(f5);
	    arrowHeadB.render(f5);
	    
	    stringUpperA.render(f5);
	    stringLowerB.render(f5);
  }
  
  public void renderBase() {
	  Shape1.showModel = Shape2.showModel = Shape3.showModel = Shape4.showModel =
	  Shape5.showModel = Shape6.showModel = Shape7.showModel = sharpA.showModel = sharpB.showModel = true;
  }
  
  
  public void pullSlow(boolean show){
	  arrowBaseA.showModel =
	  arrowHeadA.showModel = show;
			  
  }
  
  public void pullHard(boolean show){
	  	arrowBaseB.showModel =
	  	arrowHeadB.showModel =
	  	stringUpperA.showModel =
	  	stringLowerB.showModel = show;
	  	
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
