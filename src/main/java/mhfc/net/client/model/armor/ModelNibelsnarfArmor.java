package mhfc.net.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelNibelsnarfArmor extends ModelBiped
{
  //fields
    ModelRenderer headA;
    ModelRenderer headB;
    ModelRenderer headC;
    ModelRenderer headD;
    ModelRenderer headG;
    ModelRenderer headH;
    ModelRenderer headI;
    ModelRenderer headJ;
    ModelRenderer bodyA;
    ModelRenderer bodyB;
    ModelRenderer bodyC;
    ModelRenderer bodyD;
    ModelRenderer bodyE;
    ModelRenderer bodyF;
    ModelRenderer bodyG;
    ModelRenderer bodyH;
    ModelRenderer bodyI;
    ModelRenderer bodyJ;
    ModelRenderer bodyK;
    ModelRenderer leftArmA;
    ModelRenderer leftArmB;
    ModelRenderer leftArmC;
    ModelRenderer leftArmD;
    ModelRenderer rightArmA;
    ModelRenderer rightArmB;
    ModelRenderer rightArmC;
    ModelRenderer rightArmD;
    ModelRenderer rightlega;
    ModelRenderer rightlegb;
    ModelRenderer leftlega;
    ModelRenderer leftlegb;
  
  public ModelNibelsnarfArmor(float f)
  {
	  super(f, 0, 128, 128);
	  textureWidth = 128;
	  textureHeight = 128;
      headA = new ModelRenderer(this, 0, 57);
      headA.addBox(-4F, -4.7F, -5F, 8, 5, 1);
      headA.setRotationPoint(0F, 0F, 0F);
      headA.setTextureSize(128, 128);
      headA.mirror = true;
      setRotation(headA, 0F, 0F, 0F);
      headB = new ModelRenderer(this, 20, 58);
      headB.addBox(-4F, -8F, -6F, 3, 3, 2);
      headB.setRotationPoint(0F, 0F, 0F);
      headB.setTextureSize(128, 128);
      headB.mirror = true;
      setRotation(headB, 0F, 0F, 0F);
      headC = new ModelRenderer(this, 0, 65);
      headC.addBox(1F, -8F, -6F, 3, 3, 2);
      headC.setRotationPoint(0F, 0F, 0F);
      headC.setTextureSize(128, 128);
      headC.mirror = true;
      setRotation(headC, 0F, 0F, 0F);
      headD = new ModelRenderer(this, 0, 72);
      headD.addBox(-1.5F, -14F, -6F, 3, 11, 3);
      headD.setRotationPoint(0F, 0F, 0F);
      headD.setTextureSize(128, 128);
      headD.mirror = true;
      setRotation(headD, 0.0698132F, 0F, 0F);
      headG = new ModelRenderer(this, 12, 65);
      headG.addBox(-3F, -13F, -5.5F, 6, 3, 2);
      headG.setRotationPoint(0F, 0F, 0F);
      headG.setTextureSize(128, 128);
      headG.mirror = true;
      setRotation(headG, 0.0698132F, 0F, 0F);
      headH = new ModelRenderer(this, 31, 38);
      headH.addBox(-4F, -9F, -4F, 8, 1, 8);
      headH.setRotationPoint(0F, 0F, 0F);
      headH.setTextureSize(128, 128);
      headH.mirror = true;
      setRotation(headH, 0F, 0F, 0F);
      headI = new ModelRenderer(this, 34, 48);
      headI.addBox(3.4F, -8F, -4F, 1, 4, 8);
      headI.setRotationPoint(0F, 0F, 0F);
      headI.setTextureSize(128, 128);
      headI.mirror = true;
      setRotation(headI, 0F, 0F, 0F);
      headJ = new ModelRenderer(this, 14, 72);
      headJ.addBox(-4.4F, -8F, -4F, 1, 5, 8);
      headJ.setRotationPoint(0F, 0F, 0F);
      headJ.setTextureSize(128, 128);
      headJ.mirror = true;
      setRotation(headJ, 0F, 0F, 0F);
      bodyA = new ModelRenderer(this, 0, 37);
      bodyA.addBox(-4F, 0F, -3F, 8, 12, 6);
      bodyA.setRotationPoint(0F, 0F, 0F);
      bodyA.setTextureSize(128, 128);
      bodyA.mirror = true;
      setRotation(bodyA, 0F, 0F, 0F);
      bodyB = new ModelRenderer(this, 34, 63);
      bodyB.addBox(-4F, 0.5F, -6F, 8, 6, 3);
      bodyB.setRotationPoint(0F, 0F, 0F);
      bodyB.setTextureSize(128, 128);
      bodyB.mirror = true;
      setRotation(bodyB, 0F, 0F, 0F);
      bodyC = new ModelRenderer(this, 0, 88);
      bodyC.addBox(-3.7F, -4F, -8F, 2, 7, 2);
      bodyC.setRotationPoint(0F, 0F, 0F);
      bodyC.setTextureSize(128, 128);
      bodyC.mirror = true;
      setRotation(bodyC, 0.4363323F, 0F, 0F);
      bodyD = new ModelRenderer(this, 0, 88);
      bodyD.addBox(-1F, -4F, -8F, 2, 7, 2);
      bodyD.setRotationPoint(0F, 0F, 0F);
      bodyD.setTextureSize(128, 128);
      bodyD.mirror = true;
      setRotation(bodyD, 0.4363323F, 0F, 0F);
      bodyE = new ModelRenderer(this, 0, 88);
      bodyE.addBox(1.7F, -4F, -8F, 2, 7, 2);
      bodyE.setRotationPoint(0F, 0F, 0F);
      bodyE.setTextureSize(128, 128);
      bodyE.mirror = true;
      setRotation(bodyE, 0.4363323F, 0F, 0F);
      bodyF = new ModelRenderer(this, 34, 76);
      bodyF.addBox(-4F, 6.5F, -5F, 8, 5, 2);
      bodyF.setRotationPoint(0F, 0F, 0F);
      bodyF.setTextureSize(128, 128);
      bodyF.mirror = true;
      setRotation(bodyF, 0F, 0F, 0F);
      bodyG = new ModelRenderer(this, 10, 89);
      bodyG.addBox(-1.5F, 6.5F, -6F, 3, 3, 1);
      bodyG.setRotationPoint(0F, 0F, 0F);
      bodyG.setTextureSize(128, 128);
      bodyG.mirror = true;
      setRotation(bodyG, 0F, 0F, 0F);
      bodyH = new ModelRenderer(this, 34, 63);
      bodyH.addBox(-4F, 0.5F, 3F, 8, 6, 2);
      bodyH.setRotationPoint(0F, 0F, 0F);
      bodyH.setTextureSize(128, 128);
      bodyH.mirror = true;
      setRotation(bodyH, 0F, 0F, 0F);
      bodyI = new ModelRenderer(this, 0, 88);
      bodyI.addBox(1.5F, -3.5F, 5F, 2, 7, 2);
      bodyI.setRotationPoint(0F, 0F, 0F);
      bodyI.setTextureSize(128, 128);
      bodyI.mirror = true;
      setRotation(bodyI, -0.4363323F, 0F, 0F);
      bodyJ = new ModelRenderer(this, 0, 88);
      bodyJ.addBox(-1.2F, -3.5F, 5F, 2, 7, 2);
      bodyJ.setRotationPoint(0F, 0F, 0F);
      bodyJ.setTextureSize(128, 128);
      bodyJ.mirror = true;
      setRotation(bodyJ, -0.4363323F, 0F, 0F);
      bodyK = new ModelRenderer(this, 0, 88);
      bodyK.addBox(-3.8F, -3.5F, 5F, 2, 7, 2);
      bodyK.setRotationPoint(0F, 0F, 0F);
      bodyK.setTextureSize(128, 128);
      bodyK.mirror = true;
      setRotation(bodyK, -0.4363323F, 0F, 0F);
      leftArmA = new ModelRenderer(this, 54, 49);
      leftArmA.addBox(-1F, -2.4F, -3F, 5, 6, 6);
      leftArmA.setRotationPoint(0F, 0F, 0F);
      leftArmA.setTextureSize(128, 128);
      leftArmA.mirror = true;
      setRotation(leftArmA, 0F, 0F, 0F);
      leftArmB = new ModelRenderer(this, 0, 100);
      leftArmB.addBox(0F, -5F, -3.5F, 3, 4, 1);
      leftArmB.setRotationPoint(0F, 0F, 0F);
      leftArmB.setTextureSize(128, 128);
      leftArmB.mirror = true;
      setRotation(leftArmB, 0F, 0F, 0F);
      leftArmC = new ModelRenderer(this, 0, 100);
      leftArmC.addBox(3.5F, -5F, -2F, 1, 4, 3);
      leftArmC.setRotationPoint(0F, 0F, 0F);
      leftArmC.setTextureSize(128, 128);
      leftArmC.mirror = true;
      setRotation(leftArmC, 0F, 0F, 0F);
      leftArmD = new ModelRenderer(this, 0, 100);
      leftArmD.addBox(0F, -5F, 2.5F, 3, 4, 1);
      leftArmD.setRotationPoint(0F, 0F, 0F);
      leftArmD.setTextureSize(128, 128);
      leftArmD.mirror = true;
      setRotation(leftArmD, 0F, 0F, 0F);
      rightArmA = new ModelRenderer(this, 54, 49);
      rightArmA.addBox(-4F, -2.4F, -3F, 5, 6, 6);
      rightArmA.setRotationPoint(0F, 0F, 0F);
      rightArmA.setTextureSize(128, 128);
      rightArmA.mirror = true;
      setRotation(rightArmA, 0F, 0F, 0F);
      rightArmB = new ModelRenderer(this, 0, 100);
      rightArmB.addBox(-4.5F, -5F, -1.5F, 1, 4, 3);
      rightArmB.setRotationPoint(0F, 0F, 0F);
      rightArmB.setTextureSize(128, 128);
      rightArmB.mirror = true;
      setRotation(rightArmB, 0F, 0F, 0F);
      rightArmC = new ModelRenderer(this, 0, 100);
      rightArmC.addBox(-3F, -5F, -3.5F, 3, 4, 1);
      rightArmC.setRotationPoint(0F, 0F, 0F);
      rightArmC.setTextureSize(128, 128);
      rightArmC.mirror = true;
      setRotation(rightArmC, 0F, 0F, 0F);
      rightArmD = new ModelRenderer(this, 0, 100);
      rightArmD.addBox(-3F, -5F, 2.5F, 3, 4, 1);
      rightArmD.setRotationPoint(0F, 0F, 0F);
      rightArmD.setTextureSize(128, 128);
      rightArmD.mirror = true;
      setRotation(rightArmD, 0F, 0F, 0F);
      rightlega = new ModelRenderer(this, 0, 110);
      rightlega.addBox(-3F, 5F, -3F, 5, 7, 6);
      rightlega.setRotationPoint(0F, 0F, 0F);
      rightlega.setTextureSize(128, 128);
      rightlega.mirror = true;
      setRotation(rightlega, 0F, 0F, 0F);
      rightlegb = new ModelRenderer(this, 15, 101);
      rightlegb.addBox(-3F, 9F, -6F, 5, 3, 3);
      rightlegb.setRotationPoint(0F, 0F, 0F);
      rightlegb.setTextureSize(128, 128);
      rightlegb.mirror = true;
      setRotation(rightlegb, 0F, 0F, 0F);
      leftlega = new ModelRenderer(this, 0, 110);
      leftlega.addBox(-2F, 5F, -3F, 5, 7, 6);
      leftlega.setRotationPoint(0F, 0F, 0F);
      leftlega.setTextureSize(128, 128);
      leftlega.mirror = true;
      setRotation(leftlega, 0F, 0F, 0F);
      leftlegb = new ModelRenderer(this, 15, 101);
      leftlegb.addBox(-2F, 9F, -6F, 5, 3, 3);
      leftlegb.setRotationPoint(0F, 0F, 0F);
      leftlegb.setTextureSize(128, 128);
      leftlegb.mirror = true;
      setRotation(leftlegb, 0F, 0F, 0F);
      
      bipedHead.addChild(headA);
      bipedHead.addChild(headB);
      bipedHead.addChild(headC);
      bipedHead.addChild(headD);
      bipedHead.addChild(headG);
      bipedHead.addChild(headI);
      bipedHead.addChild(headJ); 
      bipedBody.addChild(bodyA);
      bipedBody.addChild(bodyB);
      bipedBody.addChild(bodyC);
      bipedBody.addChild(bodyD);
      bipedBody.addChild(bodyE);
      bipedBody.addChild(bodyF);
      bipedBody.addChild(bodyG);
      bipedBody.addChild(bodyH);
      bipedBody.addChild(bodyI);
      bipedBody.addChild(bodyJ);
      bipedBody.addChild(bodyK);
      bipedLeftArm.addChild(leftArmA);
      bipedLeftArm.addChild(leftArmB);
      bipedLeftArm.addChild(leftArmC);
      bipedLeftArm.addChild(leftArmD);
      bipedRightArm.addChild(rightArmA);
      bipedRightArm.addChild(rightArmB);
      bipedRightArm.addChild(rightArmC);
      bipedRightArm.addChild(rightArmD);
      bipedLeftLeg.addChild(leftlega);
      bipedLeftLeg.addChild(leftlegb);
      bipedRightLeg.addChild(rightlega);
      bipedRightLeg.addChild(rightlegb);
      
  }
  
  @Override
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
  
  @Override
public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity x)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5,x );
  }

}
