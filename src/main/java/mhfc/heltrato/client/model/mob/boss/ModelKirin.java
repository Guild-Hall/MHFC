package mhfc.heltrato.client.model.mob.boss;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.client.model.MHFCAnimator;
import mhfc.heltrato.common.entity.mob.EntityKirin;
import mhfc.heltrato.common.interfaces.iMHFC;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelKirin extends ModelBase
{
	protected float animTick;
	public static final float PI = 3.141593F;
	private MHFCAnimator animator;
    ModelRenderer Body;
    ModelRenderer Neck;
    ModelRenderer NeckCore;
    ModelRenderer Head;
    ModelRenderer HeadFurA;
    ModelRenderer FaceFur;
    ModelRenderer Horn;
    ModelRenderer BodyMane;
    ModelRenderer Tail;
    ModelRenderer LFHint;
    ModelRenderer LFLeg;
    ModelRenderer LFFoot;
    ModelRenderer LFFootFur;
    ModelRenderer LBHint;
    ModelRenderer LBLeg;
    ModelRenderer LBFoot;
    ModelRenderer RFLeg;
    ModelRenderer RFHint;
    ModelRenderer RFFoot;
    ModelRenderer RFFootFur;
    ModelRenderer RBHint;
    ModelRenderer RBLeg;
    ModelRenderer RBFoot;
    ModelRenderer BodyManeHair;
    ModelRenderer HeadManeHairA;
    ModelRenderer HeadManeHairB;
    ModelRenderer Horn_tip;
  
  public ModelKirin()
  {
	  animator = new MHFCAnimator(this);
	  textureWidth = 128;
	  textureHeight = 128;
	  animTick = 0.0F;
	  Body = new ModelRenderer(this, 0, 98);
      Body.addBox(-5F, -5F, -10F, 10, 10, 20);
      Body.setRotationPoint(0F, 8F, 0F);
      Body.setTextureSize(64, 32);
      Body.mirror = true;
      setRotation(Body, 0F, 0F, 0F);
      Neck = new ModelRenderer(this, 0, 81);
      Neck.addBox(-2.5F, -14F, -8F, 5, 10, 5);
      Neck.setRotationPoint(0F, 8F, 0F);
      Neck.setTextureSize(64, 32);
      Neck.mirror = true;
      setRotation(Neck, 0.5205006F, 0F, 0F);
      NeckCore = new ModelRenderer(this, 115, 0);
      NeckCore.addBox(0F, -2F, -9F, 1, 1, 1);
      NeckCore.setRotationPoint(0F, -8F, 0F);
      NeckCore.setTextureSize(64, 32);
      NeckCore.mirror = true;
      setRotation(NeckCore, 0F, 0F, 0F);
      Head = new ModelRenderer(this, 0, 64);
      Head.addBox(-3.5F, -17F, -13F, 7, 5, 10);
      Head.setRotationPoint(0F, 8F, 0F);
      Head.setTextureSize(64, 32);
      Head.mirror = true;
      setRotation(Head, 0.5204921F, 0F, 0F);
      HeadFurA = new ModelRenderer(this, 0, 45);
      HeadFurA.addBox(-1.5F, -18.6F, -3F, 3, 12, 4);
      HeadFurA.setRotationPoint(0F, 10F, 0F);
      HeadFurA.setTextureSize(64, 32);
      HeadFurA.mirror = true;
      setRotation(HeadFurA, 0.5204921F, 0F, 0F);
      FaceFur = new ModelRenderer(this, 0, 31);
      FaceFur.addBox(-4F, -14F, -11F, 8, 2, 10);
      FaceFur.setRotationPoint(0F, 10F, 0F);
      FaceFur.setTextureSize(64, 32);
      FaceFur.mirror = true;
      setRotation(FaceFur, 0.5204921F, 0F, 0F);
      Horn = new ModelRenderer(this, 98, 12);
      Horn.addBox(-1F, -22F, -6F, 2, 4, 2);
      Horn.setRotationPoint(0F, 10F, 0F);
      Horn.setTextureSize(128, 128);
      Horn.mirror = true;
      setRotation(Horn, 0.5204921F, 0F, 0F);
      BodyMane = new ModelRenderer(this, 57, 0);
      BodyMane.addBox(-1.5F, -8F, -4F, 3, 2, 15);
      BodyMane.setRotationPoint(0F, 2F, 0F);
      BodyMane.setTextureSize(64, 32);
      BodyMane.mirror = true;
      setRotation(BodyMane, 0F, 0F, 0F);
      Tail = new ModelRenderer(this, 0, 16);
      Tail.addBox(-2F, -1F, 10F, 4, 8, 3);
      Tail.setRotationPoint(0F, 2F, 0F);
      Tail.setTextureSize(64, 32);
      Tail.mirror = true;
      setRotation(Tail, 0.4864202F, 0F, 0F);
      LFHint = new ModelRenderer(this, 30, 0);
      LFHint.addBox(0F, -3F, -2.5F, 1, 6, 5);
      LFHint.setRotationPoint(5F, 3F, -5F);
      LFHint.setTextureSize(64, 32);
      LFHint.mirror = true;
      setRotation(LFHint, 0F, 0F, 0F);
      LFLeg = new ModelRenderer(this, 116, 21);
      LFLeg.addBox(-2.2F, 1F, -1.5F, 3, 7, 3);
      LFLeg.setRotationPoint(0F, 0F, 0F);
      LFLeg.setTextureSize(64, 32);
      LFLeg.mirror = true;
      setRotation(LFLeg, 0F, 0F, 0F);
      LFFoot = new ModelRenderer(this, 95, 25);
      LFFoot.addBox(-2.2F, 8F, -1.5F, 3, 5, 3);
      LFFoot.setRotationPoint(0F, 0F, 0F);
      LFFoot.setTextureSize(64, 32);
      LFFoot.mirror = true;
      setRotation(LFFoot, 0F, 0F, 0F);
      LFFootFur = new ModelRenderer(this, 34, 51);
      LFFootFur.addBox(-1F, 4F, 8.5F, 1, 3, 2);
      LFFootFur.setRotationPoint(0F, 0F, 0F);
      LFFootFur.setTextureSize(64, 32);
      LFFootFur.mirror = true;
      setRotation(LFFootFur, -0.8900146F, 0F, 0F);
      LBHint = new ModelRenderer(this, 48, 21);
      LBHint.addBox(0F, -3F, -2.5F, 1, 6, 5);
      LBHint.setRotationPoint(5F, 3F, 6F);
      LBHint.setTextureSize(64, 32);
      LBHint.mirror = true;
      setRotation(LBHint, 0F, 0F, 0F);
      LBLeg = new ModelRenderer(this, 69, 22);
      LBLeg.addBox(-2F, 2F, -2.5F, 3, 3, 7);
      LBLeg.setRotationPoint(0F, 0F, 0F);
      LBLeg.setTextureSize(64, 32);
      LBLeg.mirror = true;
      setRotation(LBLeg, 0F, 0F, 0F);
      LBFoot = new ModelRenderer(this, 17, 49);
      LBFoot.addBox(-2F, 5F, 1.5F, 3, 8, 3);
      LBFoot.setRotationPoint(0F, 0F, 0F);
      LBFoot.setTextureSize(64, 32);
      LBFoot.mirror = true;
      setRotation(LBFoot, 0F, 0F, 0F);
      RFLeg = new ModelRenderer(this, 96, 52);
      RFLeg.addBox(-0.8F, 1F, -1.5F, 3, 7, 3);
      RFLeg.setRotationPoint(0F, 0F, 0F);
      RFLeg.setTextureSize(64, 32);
      RFLeg.mirror = true;
      setRotation(RFLeg, 0F, 0F, 0F);
      RFHint = new ModelRenderer(this, 47, 38);
      RFHint.addBox(-1F, -3F, -2.5F, 1, 6, 5);
      RFHint.setRotationPoint(-5F, 3F, -5F);
      RFHint.setTextureSize(64, 32);
      RFHint.mirror = true;
      setRotation(RFHint, 0F, 0F, 0F);
      RFFoot = new ModelRenderer(this, 49, 74);
      RFFoot.addBox(-0.8F, 8F, -1.5F, 3, 5, 3);
      RFFoot.setRotationPoint(0F, 0F, 0F);
      RFFoot.setTextureSize(64, 32);
      RFFoot.mirror = true;
      setRotation(RFFoot, 0F, 0F, 0F);
      RFFootFur = new ModelRenderer(this, 104, 0);
      RFFootFur.addBox(0F, 4F, 8.5F, 1, 3, 2);
      RFFootFur.setRotationPoint(0F, 0F, 0F);
      RFFootFur.setTextureSize(64, 32);
      RFFootFur.mirror = true;
      setRotation(RFFootFur, -0.8900146F, 0F, 0F);
      RBHint = new ModelRenderer(this, 18, 16);
      RBHint.addBox(-1F, -3F, -2.5F, 1, 6, 5);
      RBHint.setRotationPoint(-5F, 3F, 6F);
      RBHint.setTextureSize(64, 32);
      RBHint.mirror = true;
      setRotation(RBHint, 0F, 0F, 0F);
      RBLeg = new ModelRenderer(this, 39, 59);
      RBLeg.addBox(-1F, 2F, -2.5F, 3, 3, 7);
      RBLeg.setRotationPoint(0F, 0F, 0F);
      RBLeg.setTextureSize(64, 32);
      RBLeg.mirror = true;
      setRotation(RBLeg, 0F, 0F, 0F);
      RBFoot = new ModelRenderer(this, 0, 0);
      RBFoot.addBox(-1F, 5F, 1.5F, 3, 8, 3);
      RBFoot.setRotationPoint(0F, 0F, 0F);
      RBFoot.setTextureSize(64, 32);
      RBFoot.mirror = true;
      setRotation(RBFoot, 0F, 0F, 0F);
      BodyManeHair = new ModelRenderer(this, 89, 87);
      BodyManeHair.addBox(0F, -12.5F, -3F, 0, 7, 14);
      BodyManeHair.setRotationPoint(0F, 0F, 0F);
      BodyManeHair.setTextureSize(128, 128);
      BodyManeHair.mirror = true;
      setRotation(BodyManeHair, -0.1745329F, 0F, 0F);
      HeadManeHairA = new ModelRenderer(this, 96, 64);
      HeadManeHairA.addBox(0F, -25F, -8F, 0, 7, 13);
      HeadManeHairA.setRotationPoint(0F, 10F, 0F);
      HeadManeHairA.setTextureSize(128, 128);
      HeadManeHairA.mirror = true;
      setRotation(HeadManeHairA, 0.5747951F, 0F, 0F);
      HeadManeHairB = new ModelRenderer(this, 65, 64);
      HeadManeHairB.addBox(0F, -9F, -18F, 0, 7, 11);
      HeadManeHairB.setRotationPoint(0F, 10F, 0F);
      HeadManeHairB.setTextureSize(128, 128);
      HeadManeHairB.mirror = true;
      setRotation(HeadManeHairB, -0.8726646F, 0F, 0F);
      Horn_tip = new ModelRenderer(this, 98, 0);
      Horn_tip.addBox(-0.5F, -25.8F, -5.5F, 1, 4, 1);
      Horn_tip.setRotationPoint(0F, 10F, 0F);
      Horn_tip.setTextureSize(128, 128);
      Horn_tip.mirror = true;
      setRotation(Horn_tip, 0.5204921F, 0F, 0F);
      
      Body.addChild(LFHint);
      Body.addChild(BodyManeHair);
      Body.addChild(LBHint);
      Body.addChild(RBHint);
      Body.addChild(Tail);
      Body.addChild(BodyMane);
      
      Body.addChild(NeckCore);
      
      NeckCore.addChild(Neck);
      NeckCore.addChild(HeadFurA);
      NeckCore.addChild(FaceFur);
      NeckCore.addChild(Head);
      NeckCore.addChild(Horn);
      NeckCore.addChild(Horn_tip);
      NeckCore.addChild(HeadManeHairA);
      NeckCore.addChild(HeadManeHairB);
      LFHint.addChild(LFLeg);
      LFHint.addChild(LFFoot);
      LFFoot.addChild(LFFootFur);
      
      LBHint.addChild(LBLeg);
      LBHint.addChild(LBFoot);
      
      Body.addChild(RFHint);
      RFHint.addChild(RFLeg);
      RFHint.addChild(RFFoot);
      RFFoot.addChild(RFFootFur);
      
      RBHint.addChild(RBLeg);
      RBHint.addChild(RBFoot);
      
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
	animator.update((iMHFC)entity); 
	setAngles();
	EntityKirin mob = (EntityKirin)entity;
	animTick = MHFCMain.proxy.getPartialTick();
	animate((EntityKirin)entity,f,f1,f2,f3,f4,f5);
    Body.render(f5);
  }
  
 

private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setAngles(){
      setRotation(Body, 0F, 0F, 0F);
      setRotation(Neck, 0.5205006F, 0F, 0F);
      setRotation(NeckCore, 0F, 0F, 0F);
      setRotation(Head, 0.5204921F, 0F, 0F);
      setRotation(HeadFurA, 0.5204921F, 0F, 0F);
      setRotation(FaceFur, 0.5204921F, 0F, 0F);
      setRotation(Horn, 0.5204921F, 0F, 0F);
      setRotation(BodyMane, 0F, 0F, 0F);
      setRotation(Tail, 0.4864202F, 0F, 0F);
      setRotation(LFHint, 0F, 0F, 0F);
      setRotation(LFLeg, 0F, 0F, 0F);
      setRotation(LFFoot, 0F, 0F, 0F);
      setRotation(LFFootFur, -0.8900146F, 0F, 0F);
      setRotation(LBHint, 0F, 0F, 0F);
      setRotation(LBLeg, 0F, 0F, 0F);
      setRotation(LBFoot, 0F, 0F, 0F);
      setRotation(RFLeg, 0F, 0F, 0F);
      setRotation(RFHint, 0F, 0F, 0F);
      setRotation(RFFoot, 0F, 0F, 0F);
      setRotation(RFFootFur, -0.8900146F, 0F, 0F);
      setRotation(RBHint, 0F, 0F, 0F);
      setRotation(RBLeg, 0F, 0F, 0F);
      setRotation(RBFoot, 0F, 0F, 0F);
      setRotation(BodyManeHair, -0.1745329F, 0F, 0F);
      setRotation(HeadManeHairA, 0.5747951F, 0F, 0F);
      setRotation(HeadManeHairB, -0.8726646F, 0F, 0F);
      setRotation(Horn_tip, 0.5204921F, 0F, 0F);
  }
  
  private void animate(EntityKirin entity, float f, float f1, float f2, float f3, float f4, float f5) {
	  float walkAnim1 = (MathHelper.sin((f - 0.7F) * 0.4F) + 0.7F) * f1;
      float walkAnim2 = -(MathHelper.sin((f + 0.7F) * 0.4F) - 0.7F) * f1;
      float walkAnim = MathHelper.sin(f * 0.4F) * f1;
      float breatheAnim = MathHelper.cos(f2 * 0.11F);
      float tailBreathe = MathHelper.sin(f2 * 0.14F);
      float faceYaw = (f3 * 3.141593F) / 180F;
      float facePitch = (f4 * 3.141593F) / 180F;
      float neckAnimY = f3 / (540F / (float)PI);
      float neckAnimX = f4 / (540F / (float)PI);
      float footAnimX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
      float footAnimY = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
      
      NeckCore.rotateAngleX -= breatheAnim * 0.01F;
      NeckCore.rotateAngleY = neckAnimY;
      LFHint.rotateAngleX = walkAnim2 * 0.6f;
      LFFoot.rotateAngleX = walkAnim2 * 0.1f;
      RFFoot.rotateAngleX = walkAnim1 *0.1f;
      RFHint.rotateAngleX = walkAnim1 * 0.6f;
      LBHint.rotateAngleX = walkAnim1 *0.6f;
      RBHint.rotateAngleX = walkAnim2 * 0.6f;
      Tail.rotateAngleY -= tailBreathe * 0.06F;
      
      if(entity.currentAttackID == 1){
    	  animateBolt();
      }else if(animator.setAnim(2)){
    	  animateJump();
      }
      
  }

  	  private void animateBolt() {
  		  
  		  animator.setAnim( 1 );
  		  animator.startPhase( 10 );
  		  animator.rotate(Body, -PI/ 3F, 0F, 0F);
  		  animator.rotate(LBHint, PI/ 3F, 0F, 0F);
  		  animator.rotate(RBHint, PI/ 3F, 0F, 0F);
  		  animator.rotate(LFFoot, PI/25F, 0F, 0F);
  		  animator.rotate(RFFoot, PI/25F, 0F, 0F);
  		  animator.endPhase();
  		  animator.resetPhase( 10 );
  		  
  	  }
  	  
  	  private void animateJump() {
  		  animator.startPhase( 20 );
  		  animator.rotate(Body, -PI/ 2F, -PI/2F, 0F);
  		  animator.rotate(LBHint, PI/ 3F, 0F, 0F);
  		  animator.rotate(RBHint, PI/ 3F, 0F, 0F);
  		  animator.endPhase();
  		  animator.resetPhase( 20 );
  		  
  		  
  	  }
  

}
