package mhfc.net.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDragoonArmor extends ModelBiped
{
  //fields
    ModelRenderer heada;
    ModelRenderer headb;
    ModelRenderer headc;
    ModelRenderer headd;
    ModelRenderer heade;
    ModelRenderer headf;
    ModelRenderer headg;
    ModelRenderer headh;
    ModelRenderer headi;
    ModelRenderer rightarmc;
    ModelRenderer leftarmc;
    ModelRenderer bodyb;
    ModelRenderer rightarmb;
    ModelRenderer leftarmb;
    ModelRenderer rightleg;
    ModelRenderer leftleg;
    ModelRenderer bodya;
    ModelRenderer rightarma;
    ModelRenderer leftarma;
  
  public ModelDragoonArmor(float f)
  {
	  super(f, 0, 128, 128);
	  textureWidth = 128;
	  textureHeight = 128;
      heada = new ModelRenderer(this, 0, 36);
      heada.addBox(-4F, -4.5F, -4.5F, 8, 4, 1);
      heada.setRotationPoint(0F, 0F, 0F);
      heada.setTextureSize(128, 128);
      heada.mirror = true;
      setRotation(heada, 0.1745329F, 0F, 0F);
      headb = new ModelRenderer(this, 0, 44);
      headb.addBox(-0.5F, -10F, -6.2F, 1, 7, 1);
      headb.setRotationPoint(0F, 0F, 0F);
      headb.setTextureSize(128, 128);
      headb.mirror = true;
      setRotation(headb, -0.125175F, 0F, 0F);
      headc = new ModelRenderer(this, 0, 54);
      headc.addBox(-4F, -9F, -5.7F, 8, 6, 1);
      headc.setRotationPoint(0F, 0F, 0F);
      headc.setTextureSize(128, 128);
      headc.mirror = true;
      setRotation(headc, -0.125168F, 0F, 0F);
      headd = new ModelRenderer(this, 5, 42);
      headd.addBox(-4F, -10F, -4F, 8, 3, 8);
      headd.setRotationPoint(0F, 0F, 0F);
      headd.setTextureSize(128, 128);
      headd.mirror = true;
      setRotation(headd, 0F, 0F, 0F);
      heade = new ModelRenderer(this, 23, 37);
      heade.addBox(-1F, -11F, -1F, 2, 1, 2);
      heade.setRotationPoint(0F, 0F, 0F);
      heade.setTextureSize(128, 128);
      heade.mirror = true;
      setRotation(heade, 0F, 0F, 0F);
      headf = new ModelRenderer(this, 0, 62);
      headf.addBox(-0.5F, -10F, -9F, 1, 4, 1);
      headf.setRotationPoint(0F, 0F, 0F);
      headf.setTextureSize(128, 128);
      headf.mirror = true;
      setRotation(headf, -0.9666439F, 0F, 0F);
      headg = new ModelRenderer(this, 0, 70);
      headg.addBox(-1F, -10F, 9F, 2, 9, 2);
      headg.setRotationPoint(0F, 0F, 0F);
      headg.setTextureSize(128, 128);
      headg.mirror = true;
      setRotation(headg, 0.5356818F, 0F, 0F);
      headh = new ModelRenderer(this, 0, 83);
      headh.addBox(4F, -6F, -1F, 1, 3, 4);
      headh.setRotationPoint(0F, 0F, 0F);
      headh.setTextureSize(128, 128);
      headh.mirror = true;
      setRotation(headh, 0.2602503F, 0F, 0F);
      headi = new ModelRenderer(this, 0, 83);
      headi.addBox(-5F, -6F, -1F, 1, 3, 4);
      headi.setRotationPoint(0F, 0F, 0F);
      headi.setTextureSize(128, 128);
      headi.mirror = true;
      setRotation(headi, 0.260246F, 0F, 0F);
      rightarmc = new ModelRenderer(this, 20, 55);
      rightarmc.addBox(-4F, 4F, -1F, 1, 4, 1);
      rightarmc.setRotationPoint(0F, 0F, 0F);
      rightarmc.setTextureSize(128, 128);
      rightarmc.mirror = true;
      setRotation(rightarmc, 0F, 0F, 0F);
      leftarmc = new ModelRenderer(this, 12, 82);
      leftarmc.addBox(1F, -4F, -3F, 1, 6, 4);
      leftarmc.setRotationPoint(0F, 0F, 0F);
      leftarmc.setTextureSize(128, 128);
      leftarmc.mirror = true;
      setRotation(leftarmc, 0.7853982F, 0F, 0F);
      bodyb = new ModelRenderer(this, 10, 63);
      bodyb.addBox(-4F, 0F, -3F, 8, 12, 6);
      bodyb.setRotationPoint(0F, 0F, 0F);
      bodyb.setTextureSize(128, 128);
      bodyb.mirror = true;
      setRotation(bodyb, 0F, 0F, 0F);
      rightarmb = new ModelRenderer(this, 40, 42);
      rightarmb.addBox(-2.7F, 3F, -2F, 5, 5, 4);
      rightarmb.setRotationPoint(0F, 0F, 0F);
      rightarmb.setTextureSize(128, 128);
      rightarmb.mirror = true;
      setRotation(rightarmb, 0F, 0F, 0F);
      leftarmb = new ModelRenderer(this, 40, 42);
      leftarmb.addBox(-1.7F, 5F, -2F, 5, 5, 4);
      leftarmb.setRotationPoint(0F, 0F, 0F);
      leftarmb.setTextureSize(128, 128);
      leftarmb.mirror = true;
      setRotation(leftarmb, 0F, 0F, 0F);
      rightleg = new ModelRenderer(this, 25, 83);
      rightleg.addBox(-3F, 6F, -2.5F, 5, 6, 5);
      rightleg.setRotationPoint(0F, 0F, 0F);
      rightleg.setTextureSize(128, 128);
      rightleg.mirror = true;
      setRotation(rightleg, 0F, 0F, 0F);
      leftleg = new ModelRenderer(this, 25, 83);
      leftleg.addBox(-2F, 6F, -2.5F, 5, 6, 5);
      leftleg.setRotationPoint(0F, 0F, 0F);
      leftleg.setTextureSize(128, 128);
      leftleg.mirror = true;
      setRotation(leftleg, 0F, 0F, 0F);
      bodya = new ModelRenderer(this, 0, 107);
      bodya.addBox(0F, 0.5F, -4F, 4, 11, 4);
      bodya.setRotationPoint(0F, 0F, 0F);
      bodya.setTextureSize(128, 128);
      bodya.mirror = true;
      setRotation(bodya, 0F, 0.7853982F, 0F);
      rightarma = new ModelRenderer(this, 0, 93);
      rightarma.addBox(-4F, -2.4F, -3F, 5, 6, 6);
      rightarma.setRotationPoint(0F, 0F, 0F);
      rightarma.setTextureSize(128, 128);
      rightarma.mirror = true;
      setRotation(rightarma, 0F, 0F, 0F);
      leftarma = new ModelRenderer(this, 0, 93);
      leftarma.addBox(-1F, -2.4F, -3F, 5, 6, 6);
      leftarma.setRotationPoint(0F, 0F, 0F);
      leftarma.setTextureSize(128, 128);
      leftarma.mirror = true;
      setRotation(leftarma, 0F, 0F, 0F);
      
      bipedHead.addChild(heada);
      bipedHead.addChild(headb);
      bipedHead.addChild(headc);
      bipedHead.addChild(headd);
      bipedHead.addChild(heade);
      bipedHead.addChild(headf);
      bipedHead.addChild(headg);
      bipedHead.addChild(headh);
      bipedHead.addChild(headi);
      bipedBody.addChild(bodya);
      bipedBody.addChild(bodyb);
      bipedLeftArm.addChild(leftarma);
      bipedLeftArm.addChild(leftarmc);
      bipedRightArm.addChild(rightarma);
      bipedRightArm.addChild(rightarmc);
      bipedLeftLeg.addChild(leftleg);
      bipedRightLeg.addChild(rightleg);
      
      
      
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
   
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
