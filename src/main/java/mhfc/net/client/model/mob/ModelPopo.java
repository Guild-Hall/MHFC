package mhfc.net.client.model.mob;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;

public class ModelPopo extends ModelBase
{
	protected float field_78145_g = 8.0F;
	protected float field_78151_h = 4.0F;
  //fields
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer leg3;
    ModelRenderer leg4;
    ModelRenderer tusk3;
    ModelRenderer tusk2;
    ModelRenderer body2;
    ModelRenderer body3;
    ModelRenderer chest;
    ModelRenderer jaw;
    ModelRenderer nose;
    ModelRenderer tailsegment1;
    ModelRenderer tailsegment3;
    ModelRenderer tusk1;
    ModelRenderer lowerleg3;
    ModelRenderer lowerleg2;
    ModelRenderer lowerleg1;
    ModelRenderer tail_segment_2;
    ModelRenderer tusk4;
    ModelRenderer tusk_6;
    ModelRenderer tusk_5;
    ModelRenderer loweleg4;
  
  public ModelPopo()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      head = new ModelRenderer(this, 0, 107);
      head.addBox(-4F, -4F, -6F, 10, 10, 11);
      head.setRotationPoint(-1F, 1F, -11F);
      head.setTextureSize(128, 128);
      head.mirror = true;
      setRotation(head, 0.4014257F, 0F, 0F);
      body = new ModelRenderer(this, 42, 95);
      body.addBox(-6F, -10F, -7F, 14, 15, 18);
      body.setRotationPoint(-1F, 3F, -1F);
      body.setTextureSize(128, 128);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      leg1 = new ModelRenderer(this, 0, 82);
      leg1.addBox(-3F, 0F, -2F, 6, 12, 8);
      leg1.setRotationPoint(-9F, 1F, 13F);
      leg1.setTextureSize(128, 128);
      leg1.mirror = true;
      setRotation(leg1, -0.3490659F, 0F, 0F);
      leg2 = new ModelRenderer(this, 0, 82);
      leg2.addBox(-3F, 0F, -2F, 6, 12, 8);
      leg2.setRotationPoint(9F, 1F, 13F);
      leg2.setTextureSize(128, 128);
      leg2.mirror = true;
      setRotation(leg2, -0.3490659F, 0F, 0F);
      leg3 = new ModelRenderer(this, 0, 82);
      leg3.addBox(-3F, 0F, -3F, 6, 13, 8);
      leg3.setRotationPoint(-10F, -2F, -4F);
      leg3.setTextureSize(128, 128);
      leg3.mirror = true;
      setRotation(leg3, 0.2268928F, 0F, 0F);
      leg4 = new ModelRenderer(this, 0, 82);
      leg4.addBox(-3F, 0F, -3F, 6, 13, 8);
      leg4.setRotationPoint(10F, -4F, -4F);
      leg4.setTextureSize(128, 128);
      leg4.mirror = true;
      setRotation(leg4, 0.2268928F, 0F, 0F);
      tusk3 = new ModelRenderer(this, 22, 58);
      tusk3.addBox(-4F, -5F, -3F, 2, 12, 3);
      tusk3.setRotationPoint(-3F, 7F, -26.57778F);
      tusk3.setTextureSize(128, 128);
      tusk3.mirror = true;
      setRotation(tusk3, 0.5235988F, 0.3490659F, 0F);
      tusk2 = new ModelRenderer(this, 22, 58);
      tusk2.addBox(2.5F, 0F, 0F, 3, 13, 3);
      tusk2.setRotationPoint(1F, 15F, -27F);
      tusk2.setTextureSize(128, 128);
      tusk2.mirror = true;
      setRotation(tusk2, 1.867502F, -0.2094395F, 0F);
      body2 = new ModelRenderer(this, 67, 65);
      body2.addBox(-2F, -2F, 0F, 13, 11, 16);
      body2.setRotationPoint(-4.5F, 10F, 8F);
      body2.setTextureSize(128, 128);
      body2.mirror = true;
      setRotation(body2, 1.448623F, 0F, 0F);
      body3 = new ModelRenderer(this, 97, 48);
      body3.addBox(-3F, 0F, -1F, 10, 11, 5);
      body3.setRotationPoint(-2F, -2F, 19F);
      body3.setTextureSize(128, 128);
      body3.mirror = true;
      setRotation(body3, -0.2559816F, 0F, 0F);
      chest = new ModelRenderer(this, 66, 14);
      chest.addBox(0F, 0F, 0F, 8, 7, 23);
      chest.setRotationPoint(-4F, 8F, -8F);
      chest.setTextureSize(128, 128);
      chest.mirror = true;
      setRotation(chest, 0.1499882F, 0F, 0F);
      jaw = new ModelRenderer(this, 61, 46);
      jaw.addBox(0F, -4F, 0F, 8, 11, 6);
      jaw.setRotationPoint(-4F, 13F, -13F);
      jaw.setTextureSize(128, 128);
      jaw.mirror = true;
      setRotation(jaw, 1.919862F, 0F, 0F);
      nose = new ModelRenderer(this, 88, 0);
      nose.addBox(0F, 0F, 0F, 8, 10, 5);
      nose.setRotationPoint(-4F, -1F, -18F);
      nose.setTextureSize(128, 128);
      nose.mirror = true;
      setRotation(nose, -0.1709872F, 0F, 0F);
      tailsegment1 = new ModelRenderer(this, 41, 80);
      tailsegment1.addBox(0F, 0F, -3F, 6, 7, 6);
      tailsegment1.setRotationPoint(-3F, 1F, 24F);
      tailsegment1.setTextureSize(128, 128);
      tailsegment1.mirror = true;
      setRotation(tailsegment1, -0.2908882F, 0F, 0F);
      tailsegment3 = new ModelRenderer(this, 64, 0);
      tailsegment3.addBox(0F, 0F, 0F, 6, 0, 5);
      tailsegment3.setRotationPoint(-3F, 5F, 29F);
      tailsegment3.setTextureSize(128, 128);
      tailsegment3.mirror = true;
      setRotation(tailsegment3, -0.5701409F, 0F, 0F);
      tusk1 = new ModelRenderer(this, 22, 58);
      tusk1.addBox(-3F, 0F, 0F, 3, 13, 3);
      tusk1.setRotationPoint(-4F, 15F, -26F);
      tusk1.setTextureSize(128, 128);
      tusk1.mirror = true;
      setRotation(tusk1, 1.867502F, 0.2094395F, 0F);
      lowerleg3 = new ModelRenderer(this, 0, 16);
      lowerleg3.addBox(0F, 0F, 0F, 6, 18, 7);
      lowerleg3.setRotationPoint(-12F, 6F, -4F);
      lowerleg3.setTextureSize(128, 128);
      lowerleg3.mirror = true;
      setRotation(lowerleg3, -0.1919862F, 0F, 0F);
      lowerleg2 = new ModelRenderer(this, 0, 16);
      lowerleg2.addBox(0F, 0F, 0F, 6, 16, 6);
      lowerleg2.setRotationPoint(5F, 8F, 9F);
      lowerleg2.setTextureSize(128, 128);
      lowerleg2.mirror = true;
      setRotation(lowerleg2, 0.0872665F, 0F, 0F);
      lowerleg1 = new ModelRenderer(this, 0, 16);
      lowerleg1.addBox(0F, 0F, 0F, 6, 16, 6);
      lowerleg1.setRotationPoint(-11F, 8F, 9F);
      lowerleg1.setTextureSize(128, 128);
      lowerleg1.mirror = true;
      setRotation(lowerleg1, 0.0872665F, 0F, 0F);
      tail_segment_2 = new ModelRenderer(this, 0, 16);
      tail_segment_2.addBox(0F, 0F, 0F, 6, 2, 5);
      tail_segment_2.setRotationPoint(-3F, 3F, 26F);
      tail_segment_2.setTextureSize(128, 128);
      tail_segment_2.mirror = true;
      setRotation(tail_segment_2, -0.3781605F, 0F, 0F);
      tusk4 = new ModelRenderer(this, 22, 58);
      tusk4.addBox(1F, 0F, 0F, 2, 12, 3);
      tusk4.setRotationPoint(5F, 4F, -31F);
      tusk4.setTextureSize(128, 128);
      tusk4.mirror = true;
      setRotation(tusk4, 0.4363323F, -0.3665191F, 0F);
      tusk_6 = new ModelRenderer(this, 22, 58);
      tusk_6.addBox(0F, 0F, 0F, 2, 8, 3);
      tusk_6.setRotationPoint(5.5F, -4F, -29.5F);
      tusk_6.setTextureSize(128, 128);
      tusk_6.mirror = true;
      setRotation(tusk_6, -0.1047198F, -0.3490659F, 0F);
      tusk_5 = new ModelRenderer(this, 22, 58);
      tusk_5.addBox(-1F, 0F, 0F, 2, 8, 3);
      tusk_5.setRotationPoint(-7F, -4F, -29F);
      tusk_5.setTextureSize(128, 128);
      tusk_5.mirror = true;
      setRotation(tusk_5, -0.1047198F, 0.4014257F, 0F);
      loweleg4 = new ModelRenderer(this, 0, 16);
      loweleg4.addBox(0F, 0F, 0F, 6, 18, 7);
      loweleg4.setRotationPoint(6F, 6F, -3F);
      loweleg4.setTextureSize(128, 128);
      loweleg4.mirror = true;
      setRotation(loweleg4, -0.1919862F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    if(this.isChild){
    	float f6 = 2.0F;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, this.field_78145_g * f5, this.field_78151_h * f5);
        this.head.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
        GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
        body.render(f5);
        leg1.render(f5);
        leg2.render(f5);
        leg3.render(f5);
        leg4.render(f5);
        tusk3.render(f5);
        tusk2.render(f5);
        body2.render(f5);
        body3.render(f5);
        chest.render(f5);
        jaw.render(f5);
        nose.render(f5);
        tailsegment1.render(f5);
        tailsegment3.render(f5);
        tusk1.render(f5);
        lowerleg3.render(f5);
        lowerleg2.render(f5);
        lowerleg1.render(f5);
        tail_segment_2.render(f5);
        tusk4.render(f5);
        tusk_6.render(f5);
        tusk_5.render(f5);
        loweleg4.render(f5);
        GL11.glPopMatrix();
    }
    else
    head.render(f5);
    body.render(f5);
    leg1.render(f5);
    leg2.render(f5);
    leg3.render(f5);
    leg4.render(f5);
    tusk3.render(f5);
    tusk2.render(f5);
    body2.render(f5);
    body3.render(f5);
    chest.render(f5);
    jaw.render(f5);
    nose.render(f5);
    tailsegment1.render(f5);
    tailsegment3.render(f5);
    tusk1.render(f5);
    lowerleg3.render(f5);
    lowerleg2.render(f5);
    lowerleg1.render(f5);
    tail_segment_2.render(f5);
    tusk4.render(f5);
    tusk_6.render(f5);
    tusk_5.render(f5);
    loweleg4.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.lowerleg3.rotateAngleX = MathHelper.cos(f * 0.4F) * 1.0F * f1;
    this.loweleg4.rotateAngleX = MathHelper.cos(f * 0.4F + (float)Math.PI) * 1.0F * f1;
    this.lowerleg2.rotateAngleX = MathHelper.cos(f * 0.4F) * 1.0F * f1;
    this.lowerleg1.rotateAngleX = MathHelper.cos(f * 0.4F + (float)Math.PI) * 1.0F * f1;
    
  }

}

