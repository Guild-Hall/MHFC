package mhfc.heltrato.client.model.weapon.bow;

import mhfc.heltrato.common.weapon.range.iBowAnim;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBPower extends ModelBase implements iBowAnim
{
    ModelRenderer baseA;
    ModelRenderer baseB;
    ModelRenderer spearA;
    ModelRenderer spearPinA;
    ModelRenderer spearPinB;
    ModelRenderer spearB;
    ModelRenderer basepoint;
    ModelRenderer baseC;
    ModelRenderer baseD;
    ModelRenderer stringBase;
    ModelRenderer stringU;
    ModelRenderer stringL;
  
  public ModelBPower()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      baseA = new ModelRenderer(this, 0, 43);
      baseA.addBox(-1F, -10F, -1F, 2, 10, 2);
      baseA.setRotationPoint(-3F, 0F, 0F);
      baseA.setTextureSize(128, 128);
      baseA.mirror = true;
      setRotation(baseA, 0F, 0F, 0.2617994F);
      baseB = new ModelRenderer(this, 0, 43);
      baseB.addBox(-1F, 0F, -1F, 2, 10, 2);
      baseB.setRotationPoint(-3F, 2F, 0F);
      baseB.setTextureSize(128, 128);
      baseB.mirror = true;
      setRotation(baseB, 0F, 0F, -0.2617994F);
      spearA = new ModelRenderer(this, 0, 60);
      spearA.addBox(-15F, 0F, -0.5F, 15, 1, 1);
      spearA.setRotationPoint(0F, 1F, 0F);
      spearA.setTextureSize(128, 128);
      spearA.mirror = true;
      setRotation(spearA, 0F, 0F, 0F);
      spearPinA = new ModelRenderer(this, 0, 75);
      spearPinA.addBox(-1F, -1F, -0.5F, 2, 2, 1);
      spearPinA.setRotationPoint(-15F, 1.5F, 0F);
      spearPinA.setTextureSize(128, 128);
      spearPinA.mirror = true;
      setRotation(spearPinA, 0F, 0F, -0.7853982F);
      spearPinB = new ModelRenderer(this, 0, 75);
      spearPinB.addBox(0F, 0F, 0F, 2, 2, 1);
      spearPinB.setRotationPoint(-8F, 1.5F, 0F);
      spearPinB.setTextureSize(128, 128);
      spearPinB.mirror = true;
      setRotation(spearPinB, 0F, 0F, -0.7853982F);
      spearB = new ModelRenderer(this, 0, 60);
      spearB.addBox(0F, 0F, -0.5F, 12, 1, 1);
      spearB.setRotationPoint(-6F, 1F, 0F);
      spearB.setTextureSize(128, 128);
      spearB.mirror = true;
      setRotation(spearB, 0F, 0F, 0F);
      basepoint = new ModelRenderer(this, 20, 0);
      basepoint.addBox(0F, 0F, -1F, 1, 1, 2);
      basepoint.setRotationPoint(-3F, 0F, 0F);
      basepoint.setTextureSize(128, 128);
      basepoint.mirror = true;
      setRotation(basepoint, 0F, 0F, 0F);
      baseC = new ModelRenderer(this, 0, 43);
      baseC.addBox(-1.2F, -5F, -1F, 2, 6, 2);
      baseC.setRotationPoint(0F, -10F, 0F);
      baseC.setTextureSize(128, 128);
      baseC.mirror = true;
      setRotation(baseC, 0F, 0F, 0.3490659F);
      baseD = new ModelRenderer(this, 0, 43);
      baseD.addBox(-1F, 0F, -1F, 2, 6, 2);
      baseD.setRotationPoint(-0.5F, 11F, 0F);
      baseD.setTextureSize(128, 128);
      baseD.mirror = true;
      setRotation(baseD, 0F, 0F, -0.4886922F);
      stringBase = new ModelRenderer(this, 0, 0);
      stringBase.addBox(0F, -2F, -0.5F, 1, 29, 1);
      stringBase.setRotationPoint(1F, -12F, 0F);
      stringBase.setTextureSize(128, 128);
      stringBase.mirror = true;
      setRotation(stringBase, 0F, 0F, 0F);
      stringU = new ModelRenderer(this, 0, 0);
      stringU.addBox(0F, 0F, -0.5F, 1, 15, 1);
      stringU.setRotationPoint(1F, -13F, 0F);
      stringU.setTextureSize(128, 128);
      stringU.mirror = true;
      setRotation(stringU, 0F, 0F, -0.2792527F);
      stringL = new ModelRenderer(this, 0, 0);
      stringL.addBox(0F, 0F, -0.5F, 1, 15, 1);
      stringL.setRotationPoint(5F, 0F, 0F);
      stringL.setTextureSize(128, 128);
      stringL.mirror = true;
      setRotation(stringL, 0F, 0F, 0.2792527F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    render(f5);

  }
  
  public void render(float f5){
	  baseA.renderWithRotation(f5);
	  baseB.renderWithRotation(f5);
	  spearA.renderWithRotation(f5);
	  spearPinA.renderWithRotation(f5);
	  spearPinB.renderWithRotation(f5);
	  spearB.renderWithRotation(f5);
	  basepoint.renderWithRotation(f5);
	  baseC.renderWithRotation(f5);
	  baseD.renderWithRotation(f5);
	  stringBase.renderWithRotation(f5);
	  stringU.renderWithRotation(f5);
	  stringL.renderWithRotation(f5);
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
  
@Override
public void renderBase(){
baseA.showModel = baseB.showModel = baseC.showModel = baseD.showModel = basepoint.showModel = true;	  
}

@Override
public void restBow(boolean show) {
	stringBase.showModel = show;
}

@Override
public void pullSlow(boolean show) {
	spearA.showModel = spearPinA.showModel = show;
}

@Override
public void pullHard(boolean show) {
	stringU.showModel = stringL.showModel = spearB.showModel = spearPinB.showModel = show;
}
  

}
