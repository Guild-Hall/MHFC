package mhfc.net.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGreatJaggiArmor extends ModelBiped
{
  //fields
    ModelRenderer ha;
    ModelRenderer hb;
    ModelRenderer hc;
    ModelRenderer hd;
    ModelRenderer he;
    ModelRenderer hf;
    ModelRenderer hg;
    ModelRenderer ba;
    ModelRenderer bb;
    ModelRenderer bc;
    ModelRenderer ra;
    ModelRenderer la;
    ModelRenderer lb;
    
    ModelRenderer lfa;
    ModelRenderer rfa;
  
  public ModelGreatJaggiArmor(float f)
  {
	super(f,0, 128, 128);  
    textureWidth = 128;
    textureHeight = 128;
      ha = new ModelRenderer(this, 0, 34);
      ha.addBox(-4F, -3F, -4.5F, 3, 3, 1);
      ha.setRotationPoint(0F, 0F, 0F);
      ha.setTextureSize(128, 128);
      ha.mirror = true;
      setRotation(ha, 0F, 0F, 0F);
      hb = new ModelRenderer(this, 0, 34);
      hb.addBox(1F, -3F, -4.5F, 3, 3, 1);
      hb.setRotationPoint(0F, 0F, 0F);
      hb.setTextureSize(128, 128);
      hb.mirror = true;
      setRotation(hb, 0F, 0F, 0F);
      hc = new ModelRenderer(this, 0, 39);
      hc.addBox(-3.5F, -8F, -4.4F, 7, 2, 1);
      hc.setRotationPoint(0F, 0F, 0F);
      hc.setTextureSize(128, 128);
      hc.mirror = true;
      setRotation(hc, 0F, 0F, 0F);
      hd = new ModelRenderer(this, 0, 44);
      hd.addBox(-5F, -9F, -4.5F, 2, 3, 9);
      hd.setRotationPoint(0F, 0F, 0F);
      hd.setTextureSize(128, 128);
      hd.mirror = true;
      setRotation(hd, 0F, 0F, 0F);
      he = new ModelRenderer(this, 0, 44);
      he.addBox(3F, -9F, -4.5F, 2, 3, 9);
      he.setRotationPoint(0F, 0F, 0F);
      he.setTextureSize(128, 128);
      he.mirror = true;
      setRotation(he, 0F, 0F, 0F);
      hf = new ModelRenderer(this, 0, 58);
      hf.addBox(-4.5F, -6F, -3F, 1, 6, 6);
      hf.setRotationPoint(0F, 0F, 0F);
      hf.setTextureSize(128, 128);
      hf.mirror = true;
      setRotation(hf, 0F, 0F, 0F);
      hg = new ModelRenderer(this, 0, 58);
      hg.addBox(3.5F, -6F, -3F, 1, 6, 6);
      hg.setRotationPoint(0F, 0F, 0F);
      hg.setTextureSize(128, 128);
      hg.mirror = true;
      setRotation(hg, 0F, 0F, 0F);
      ba = new ModelRenderer(this, 0, 85);
      ba.addBox(-4F, 0F, -2.4F, 8, 12, 1);
      ba.setRotationPoint(0F, 0F, 0F);
      ba.setTextureSize(128, 128);
      ba.mirror = true;
      setRotation(ba, 0F, 0F, 0F);
      bb = new ModelRenderer(this, 0, 99);
      bb.addBox(-3.5F, 6F, -3F, 7, 1, 1);
      bb.setRotationPoint(0F, 0F, 0F);
      bb.setTextureSize(128, 128);
      bb.mirror = true;
      setRotation(bb, 0F, 0F, 0F);
      bc = new ModelRenderer(this, 0, 102);
      bc.addBox(-4F, 0F, 1.6F, 8, 12, 1);
      bc.setRotationPoint(0F, 0F, 0F);
      bc.setTextureSize(128, 128);
      bc.mirror = true;
      setRotation(bc, 0F, 0F, 0F);
      ra = new ModelRenderer(this, 0, 71);
      ra.addBox(-4F, -4F, -2.5F, 5, 8, 5);
      ra.setRotationPoint(0F, 0F, 0F);
      ra.setTextureSize(128, 128);
      ra.mirror = true;
      setRotation(ra, 0F, 0F, 0F);
      la = new ModelRenderer(this, 24, 34);
      la.addBox(-0.4F, -6F, -3F, 1, 7, 6);
      la.setRotationPoint(0F, 0F, 0F);
      la.setTextureSize(128, 128);
      la.mirror = true;
      setRotation(la, 0F, 0F, 0F);
      lb = new ModelRenderer(this, 23, 48);
      lb.addBox(0F, -4F, -2.5F, 4, 4, 5);
      lb.setRotationPoint(0F, 0F, 0F);
      lb.setTextureSize(128, 128);
      lb.mirror = true;
      setRotation(lb, 0F, 0F, 0F);
      lfa = new ModelRenderer(this, 15, 59);
      lfa.addBox(-2F, 9F, -2.5F, 5, 3, 5);
      lfa.setRotationPoint(0F, 0F, 0F);
      lfa.setTextureSize(128, 128);
      lfa.mirror = true;
      setRotation(lfa, 0F, 0F, 0F);
      rfa = new ModelRenderer(this, 15, 59);
      rfa.addBox(-3F, 9F, -2.5F, 5, 3, 5);
      rfa.setRotationPoint(0F, 0F, 0F);
      rfa.setTextureSize(128, 128);
      rfa.mirror = true;
      setRotation(rfa, 0F, 0F, 0F);
      
      bipedHead.addChild(ha);
      bipedHead.addChild(hb);
      bipedHead.addChild(hc);
      bipedHead.addChild(hd);
      bipedHead.addChild(he);
      bipedHead.addChild(hf);
      bipedHead.addChild(hg);
      bipedBody.addChild(ba);
      bipedBody.addChild(bb);
      bipedBody.addChild(bc);
      bipedRightArm.addChild(ra);
      bipedLeftArm.addChild(la);
      bipedLeftArm.addChild(lb);
      bipedLeftLeg.addChild(lfa);
      bipedRightLeg.addChild(rfa);
      
      
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
