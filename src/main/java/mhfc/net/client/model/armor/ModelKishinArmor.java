package mhfc.net.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelKishinArmor extends ModelBiped
{
    ModelRenderer heada;
    ModelRenderer headb;
    ModelRenderer headc;
    ModelRenderer headd;
    ModelRenderer rightarma;
    ModelRenderer rightarmb;
    ModelRenderer rightarmc;
    ModelRenderer rightarmd;
    ModelRenderer rightarme;
    ModelRenderer rightarmf;
    ModelRenderer rightarmg;
    ModelRenderer leftarma;
    ModelRenderer leftarmb;
    ModelRenderer leftarmc;
    ModelRenderer leftarmd;
    ModelRenderer leftarme;
    ModelRenderer leftarmf;
    ModelRenderer rightarmh;
    ModelRenderer leftlegc;
    ModelRenderer leftlegd;
    ModelRenderer rightlegb;
    ModelRenderer leftlega;
    ModelRenderer leftlegb;
    ModelRenderer rightlega;
  
  public ModelKishinArmor(float f)
  {
	  super(f,0,128,128);
	  textureWidth = 128;
	  textureHeight = 128;
      heada = new ModelRenderer(this, 0, 35);
      heada.addBox(-2.5F, -8F, -4.5F, 5, 2, 1);
      heada.setRotationPoint(0F, 0F, 0F);
      heada.setTextureSize(128, 128);
      heada.mirror = true;
      setRotation(heada, 0F, 0F, 0F);
      headb = new ModelRenderer(this, 0, 40);
      headb.addBox(2.5F, -10F, -4.5F, 1, 4, 1);
      headb.setRotationPoint(0F, 0F, 0F);
      headb.setTextureSize(128, 128);
      headb.mirror = true;
      setRotation(headb, 0F, 0F, 0F);
      headc = new ModelRenderer(this, 0, 47);
      headc.addBox(-3.5F, -10F, -4.5F, 1, 4, 1);
      headc.setRotationPoint(0F, 0F, 0F);
      headc.setTextureSize(128, 128);
      headc.mirror = true;
      setRotation(headc, 0F, 0F, 0F);
      headd = new ModelRenderer(this, 0, 54);
      headd.addBox(-1.5F, -8F, -5F, 3, 2, 1);
      headd.setRotationPoint(0F, 0F, 0F);
      headd.setTextureSize(128, 128);
      headd.mirror = true;
      setRotation(headd, 0.0087266F, 0F, 0F);
      rightarma = new ModelRenderer(this, 0, 59);
      rightarma.addBox(-4F, -2.5F, -3F, 5, 5, 6);
      rightarma.setRotationPoint(0F, 0F, 0F);
      rightarma.setTextureSize(128, 128);
      rightarma.mirror = true;
      setRotation(rightarma, 0F, 0F, 0F);
      rightarmb = new ModelRenderer(this, 0, 72);
      rightarmb.addBox(0F, -3.5F, -3.5F, 1, 6, 7);
      rightarmb.setRotationPoint(0F, 0F, 0F);
      rightarmb.setTextureSize(128, 128);
      rightarmb.mirror = true;
      setRotation(rightarmb, 0F, 0F, 0F);
      rightarmc = new ModelRenderer(this, 0, 87);
      rightarmc.addBox(-2.5F, -8F, 0.5F, 2, 8, 2);
      rightarmc.setRotationPoint(0F, 0F, 0F);
      rightarmc.setTextureSize(128, 128);
      rightarmc.mirror = true;
      setRotation(rightarmc, 0F, -0.7853982F, 0F);
      rightarmd = new ModelRenderer(this, 6, 40);
      rightarmd.addBox(-2F, -11F, 1F, 1, 5, 1);
      rightarmd.setRotationPoint(0F, 0F, 0F);
      rightarmd.setTextureSize(128, 128);
      rightarmd.mirror = true;
      setRotation(rightarmd, 0F, -0.7853982F, 0F);
      rightarme = new ModelRenderer(this, 5, 48);
      rightarme.addBox(-7F, 0F, -1F, 4, 1, 2);
      rightarme.setRotationPoint(0F, 0F, 0F);
      rightarme.setTextureSize(128, 128);
      rightarme.mirror = true;
      setRotation(rightarme, 0F, 0F, 0.4537856F);
      rightarmf = new ModelRenderer(this, 15, 36);
      rightarmf.addBox(-3.5F, 3.5F, -3F, 4, 2, 6);
      rightarmf.setRotationPoint(0F, 0F, 0F);
      rightarmf.setTextureSize(128, 128);
      rightarmf.mirror = true;
      setRotation(rightarmf, 0F, 0F, 0F);
      rightarmg = new ModelRenderer(this, 20, 46);
      rightarmg.addBox(-4F, 4.9F, -1F, 4, 1, 2);
      rightarmg.setRotationPoint(0F, 0F, 0F);
      rightarmg.setTextureSize(128, 128);
      rightarmg.mirror = true;
      setRotation(rightarmg, 0F, 0F, 0.4537856F);
      leftarma = new ModelRenderer(this, 25, 52);
      leftarma.addBox(-1F, -3F, -3.5F, 1, 6, 7);
      leftarma.setRotationPoint(0F, 0F, 0F);
      leftarma.setTextureSize(128, 128);
      leftarma.mirror = true;
      setRotation(leftarma, 0F, 0F, 0F);
      leftarmb = new ModelRenderer(this, 12, 55);
      leftarmb.addBox(-1F, -1F, -4.5F, 1, 1, 1);
      leftarmb.setRotationPoint(0F, 0F, 0F);
      leftarmb.setTextureSize(128, 128);
      leftarmb.mirror = true;
      setRotation(leftarmb, 0F, 0F, 0F);
      leftarmc = new ModelRenderer(this, 12, 55);
      leftarmc.addBox(-1F, 1F, -4.5F, 1, 1, 1);
      leftarmc.setRotationPoint(0F, 0F, 0F);
      leftarmc.setTextureSize(128, 128);
      leftarmc.mirror = true;
      setRotation(leftarmc, 0F, 0F, 0F);
      leftarmd = new ModelRenderer(this, 0, 100);
      leftarmd.addBox(-0.5F, -2.5F, -3F, 5, 5, 6);
      leftarmd.setRotationPoint(0F, 0F, 0F);
      leftarmd.setTextureSize(128, 128);
      leftarmd.mirror = true;
      setRotation(leftarmd, 0F, 0F, 0F);
      leftarme = new ModelRenderer(this, 60, 36);
      leftarme.addBox(-0.5F, 3.5F, -3F, 4, 4, 6);
      leftarme.setRotationPoint(0F, 0F, 0F);
      leftarme.setTextureSize(128, 128);
      leftarme.mirror = true;
      setRotation(leftarme, 0F, 0F, 0F);
      leftarmf = new ModelRenderer(this, 19, 74);
      leftarmf.addBox(5.3F, -3F, -1F, 2, 6, 2);
      leftarmf.setRotationPoint(0F, 0F, 0F);
      leftarmf.setTextureSize(128, 128);
      leftarmf.mirror = true;
      setRotation(leftarmf, 0F, 0F, 0.7853982F);
      rightarmh = new ModelRenderer(this, 12, 88);
      rightarmh.addBox(-3F, -7F, -3F, 2, 6, 2);
      rightarmh.setRotationPoint(0F, 0F, 0F);
      rightarmh.setTextureSize(128, 128);
      rightarmh.mirror = true;
      setRotation(rightarmh, 0.8726646F, 0F, 0F);
      leftlegc = new ModelRenderer(this, 48, 47);
      leftlegc.addBox(-2F, 6F, -2.5F, 5, 3, 5);
      leftlegc.setRotationPoint(0F, 0F, 0F);
      leftlegc.setTextureSize(128, 128);
      leftlegc.mirror = true;
      setRotation(leftlegc, 0F, 0F, 0F);
      leftlegd = new ModelRenderer(this, 48, 58);
      leftlegd.addBox(-0.5F, 2F, -6.5F, 2, 4, 1);
      leftlegd.setRotationPoint(0F, 0F, 0F);
      leftlegd.setTextureSize(128, 128);
      leftlegd.mirror = true;
      setRotation(leftlegd, 0.5235988F, 0F, 0F);
      rightlegb = new ModelRenderer(this, 31, 75);
      rightlegb.addBox(-1.5F, 10F, -4F, 2, 2, 2);
      rightlegb.setRotationPoint(0F, 0F, 0F);
      rightlegb.setTextureSize(128, 128);
      rightlegb.mirror = true;
      setRotation(rightlegb, 0.0174533F, 0F, 0F);
      leftlega = new ModelRenderer(this, 38, 36);
      leftlega.addBox(-2F, 10F, -2.5F, 5, 2, 5);
      leftlega.setRotationPoint(0F, 0F, 0F);
      leftlega.setTextureSize(128, 128);
      leftlega.mirror = true;
      setRotation(leftlega, 0F, 0F, 0F);
      leftlegb = new ModelRenderer(this, 37, 45);
      leftlegb.addBox(-0.5F, 10F, -4F, 2, 2, 2);
      leftlegb.setRotationPoint(0F, 0F, 0F);
      leftlegb.setTextureSize(128, 128);
      leftlegb.mirror = true;
      setRotation(leftlegb, 0.0174533F, 0F, 0F);
      rightlega = new ModelRenderer(this, 25, 66);
      rightlega.addBox(-3F, 10F, -2.5F, 5, 2, 5);
      rightlega.setRotationPoint(0F, 0F, 0F);
      rightlega.setTextureSize(128, 128);
      rightlega.mirror = true;
      setRotation(rightlega, 0F, 0F, 0F);
      
      
      bipedHead.addChild(heada);
      bipedHead.addChild(headb);
      bipedHead.addChild(headc);
      bipedHead.addChild(headd);
      bipedRightArm.addChild(rightarma);
      bipedRightArm.addChild(rightarmb);
      bipedRightArm.addChild(rightarmc);
      bipedRightArm.addChild(rightarmd);
      bipedRightArm.addChild(rightarme);
      bipedRightArm.addChild(rightarmf);
      bipedRightArm.addChild(rightarmg);
      bipedRightArm.addChild(rightarmh);
      bipedLeftArm.addChild(leftarma);
      bipedLeftArm.addChild(leftarmb);
      bipedLeftArm.addChild(leftarmc);
      bipedLeftArm.addChild(leftarmd);
      bipedLeftArm.addChild(leftarme);
      bipedLeftArm.addChild(leftarmf);
      bipedLeftLeg.addChild(leftlega);
      bipedLeftLeg.addChild(leftlegb);
      bipedLeftLeg.addChild(leftlegc);
      bipedLeftLeg.addChild(leftlegd);
      bipedRightLeg.addChild(rightlega);
      bipedRightLeg.addChild(rightlegb);
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
