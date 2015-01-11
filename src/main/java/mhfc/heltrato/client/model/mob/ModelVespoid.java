package mhfc.heltrato.client.model.mob;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelVespoid extends ModelBase
{
  //fields
    ModelRenderer Base;
    ModelRenderer stinger;
    ModelRenderer joiner;
    ModelRenderer body;
    ModelRenderer head;
    ModelRenderer wing1;
    ModelRenderer wing2;
    ModelRenderer wing3;
    ModelRenderer wing4;
    ModelRenderer wing5;
    ModelRenderer wing6;
    private float speedflow = 1.0F;
    public ModelVespoid(float f1)
    {
    	textureWidth = 32;
    	textureHeight = 32;
    	speedflow = f1;
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
      Base.setRotationPoint(1.5F, 0F, 1.5F);
      Base.setTextureSize(64, 32);
      Base.mirror = true;
      setRotation(Base, -0.3346075F, 0F, 0F);
      stinger = new ModelRenderer(this, 12, 0);
      stinger.addBox(-1F, 0F, -1F, 1, 2, 1);
      stinger.setRotationPoint(1.4F, 5.5F, -0.5F);
      stinger.setTextureSize(64, 32);
      stinger.mirror = true;
      setRotation(stinger, -0.4461433F, -0.7063936F, 0.3717861F);
      joiner = new ModelRenderer(this, 12, 3);
      joiner.addBox(-0.5F, 0F, 0.5F, 1, 2, 1);
      joiner.setRotationPoint(1.5F, -1F, 0.5F);
      joiner.setTextureSize(64, 32);
      joiner.mirror = true;
      setRotation(joiner, 0F, 0F, 0F);
      body = new ModelRenderer(this, 16, 0);
      body.addBox(-1F, -2F, -1F, 2, 2, 2);
      body.setRotationPoint(1.5F, -1F, 1.5F);
      body.setTextureSize(64, 32);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      head = new ModelRenderer(this, 12, 6);
      head.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
      head.setRotationPoint(1.5F, -3F, 0.5F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      wing1 = new ModelRenderer(this, 0, 9);
      wing1.addBox(0F, 0F, 0F, 1, 0, 6);
      wing1.setRotationPoint(2F, -2F, 2F);
      wing1.setTextureSize(64, 32);
      wing1.mirror = true;
      setRotation(wing1, 0.669215F, 0.9294653F, 0F);
      wing2 = new ModelRenderer(this, 0, 9);
      wing2.addBox(0F, 0F, 0F, 1, 0, 6);
      wing2.setRotationPoint(0.5F, -2F, 1F);
      wing2.setTextureSize(64, 32);
      wing2.mirror = true;
      setRotation(wing2, 0.669215F, -0.9294576F, 0F);
      wing3 = new ModelRenderer(this, 0, 9);
      wing3.addBox(0F, 0F, 0F, 1, 0, 6);
      wing3.setRotationPoint(0.5F, -2F, 1F);
      wing3.setTextureSize(64, 32);
      wing3.mirror = true;
      setRotation(wing3, -0.6692116F, -0.9294576F, 0F);
      wing4 = new ModelRenderer(this, 0, 9);
      wing4.addBox(0F, 0F, 0F, 1, 0, 6);
      wing4.setRotationPoint(2F, -2F, 2F);
      wing4.setTextureSize(64, 32);
      wing4.mirror = true;
      setRotation(wing4, -0.6692116F, 0.9294653F, 0F);
      wing5 = new ModelRenderer(this, 0, 9);
      wing5.addBox(0F, 0F, 0F, 1, 0, 6);
      wing5.setRotationPoint(2F, -2F, 2F);
      wing5.setTextureSize(64, 32);
      wing5.mirror = true;
      setRotation(wing5, 0F, 0.9294653F, 0F);
      wing6 = new ModelRenderer(this, 0, 9);
      wing6.addBox(0F, 0F, 0F, 1, 0, 6);
      wing6.setRotationPoint(0.5F, -2F, 1F);
      wing6.setTextureSize(64, 32);
      wing6.mirror = true;
      setRotation(wing6, 0F, -0.9294576F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Base.render(f5);
    stinger.render(f5);
    joiner.render(f5);
    body.render(f5);
    head.render(f5);
    wing1.render(f5);
    wing2.render(f5);
    wing3.render(f5);
    wing4.render(f5);
    wing5.render(f5);
    wing6.render(f5);
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
 
    this.wing1.rotateAngleZ = MathHelper.cos(f2 * 1.3F * this.speedflow) * (float)Math.PI * 0.25F;
    this.wing2.rotateAngleZ = this.wing1.rotateAngleZ;
    this.wing3.rotateAngleZ =  this.wing1.rotateAngleZ;
    this.wing4.rotateAngleZ = -this.wing1.rotateAngleZ;
    this.wing5.rotateAngleZ = - this.wing1.rotateAngleZ;
    this.wing6.rotateAngleZ = - this.wing1.rotateAngleZ;
}
}
