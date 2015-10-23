package mhfc.net.client.model.weapon.longsword;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLSSaber extends ModelBase
{
  //fields
    ModelRenderer middletip;
    ModelRenderer uppertip;
    ModelRenderer lowertip;
    ModelRenderer handle;
    ModelRenderer base;
    ModelRenderer sharpa;
    ModelRenderer sharpc;
    ModelRenderer sharpb;
    ModelRenderer basea;
    ModelRenderer baseb;
    ModelRenderer basec;
    ModelRenderer based;
    ModelRenderer basee;
    ModelRenderer basef;
    ModelRenderer baseg;
  
  public ModelLSSaber()
  {
    textureWidth = 32;
    textureHeight = 32;
    
      middletip = new ModelRenderer(this, 0, 24);
      middletip.addBox(-0.5F, 0F, -0.5F, 2, 1, 2);
      middletip.setRotationPoint(0F, 17F, 0F);
      middletip.setTextureSize(32, 32);
      middletip.mirror = true;
      setRotation(middletip, 0F, 0F, 0F);
      uppertip = new ModelRenderer(this, 0, 20);
      uppertip.addBox(-0.5F, 0F, -0.5F, 2, 1, 2);
      uppertip.setRotationPoint(0F, 13F, 0F);
      uppertip.setTextureSize(32, 32);
      uppertip.mirror = true;
      setRotation(uppertip, 0F, 0F, 0F);
      lowertip = new ModelRenderer(this, 0, 28);
      lowertip.addBox(-0.5F, 0F, -0.5F, 2, 2, 2);
      lowertip.setRotationPoint(0F, 20F, 0F);
      lowertip.setTextureSize(32, 32);
      lowertip.mirror = true;
      setRotation(lowertip, 0F, 0F, 0F);
      handle = new ModelRenderer(this, 9, 21);
      handle.addBox(0F, 0F, 0F, 1, 10, 1);
      handle.setRotationPoint(0F, 10F, 0F);
      handle.setTextureSize(32, 32);
      handle.mirror = true;
      setRotation(handle, 0F, 0F, 0F);
      base = new ModelRenderer(this, 0, 0);
      base.addBox(2F, 0F, -2.5F, 1, 2, 5);
      base.setRotationPoint(0.5F, 8F, 0.5F);
      base.setTextureSize(32, 32);
      base.mirror = true;
      setRotation(base, 0F, 0F, 0F);
      sharpa = new ModelRenderer(this, 26, 19);
      sharpa.addBox(0F, 0F, -0.5F, 1, 11, 2);
      sharpa.setRotationPoint(0F, -6F, 1F);
      sharpa.setTextureSize(32, 32);
      sharpa.mirror = true;
      setRotation(sharpa, 0F, 1.570796F, 0F);
      sharpc = new ModelRenderer(this, 13, 20);
      sharpc.addBox(0F, -9F, 0F, 1, 9, 2);
      sharpc.setRotationPoint(-0.1F, -16F, 1F);
      sharpc.setTextureSize(32, 32);
      sharpc.mirror = true;
      setRotation(sharpc, -0.0698132F, 1.570796F, 0F);
      sharpb = new ModelRenderer(this, 20, 20);
      sharpb.addBox(0F, -10F, 0F, 1, 10, 2);
      sharpb.setRotationPoint(-0.5F, -6F, 1F);
      sharpb.setTextureSize(32, 32);
      sharpb.mirror = true;
      setRotation(sharpb, -0.0349066F, 1.570796F, 0F);
      basea = new ModelRenderer(this, 0, 15);
      basea.addBox(-0.5F, 0F, -1F, 2, 1, 3);
      basea.setRotationPoint(0F, 5F, 1F);
      basea.setTextureSize(32, 32);
      basea.mirror = true;
      setRotation(basea, 0F, 1.570796F, 0F);
      baseb = new ModelRenderer(this, 2, 9);
      baseb.addBox(-1F, 0F, -1.5F, 3, 1, 4);
      baseb.setRotationPoint(0F, 6F, 1F);
      baseb.setTextureSize(32, 32);
      baseb.mirror = true;
      setRotation(baseb, 0F, 1.570796F, 0F);
      basec = new ModelRenderer(this, 12, 0);
      basec.addBox(-2F, 0F, -2F, 5, 3, 5);
      basec.setRotationPoint(0F, 7F, 1F);
      basec.setTextureSize(32, 32);
      basec.mirror = true;
      setRotation(basec, 0F, 1.570796F, 0F);
      based = new ModelRenderer(this, 20, 11);
      based.addBox(2F, 0F, -2.5F, 1, 2, 5);
      based.setRotationPoint(0.5F, 8F, 0.5F);
      based.setTextureSize(32, 32);
      based.mirror = true;
      setRotation(based, 0F, 1.570796F, 0F);
      basee = new ModelRenderer(this, 0, 0);
      basee.addBox(0F, 0F, -0.5F, 1, 4, 1);
      basee.setRotationPoint(-2.5F, 10F, 0.5F);
      basee.setTextureSize(32, 32);
      basee.mirror = true;
      setRotation(basee, 0F, 0F, -0.5235988F);
      basef = new ModelRenderer(this, 0, 0);
      basef.addBox(-3F, 0F, -2.5F, 1, 2, 5);
      basef.setRotationPoint(0.5F, 8F, 0.5F);
      basef.setTextureSize(32, 32);
      basef.mirror = true;
      setRotation(basef, 0F, 1.570796F, 0F);
      baseg = new ModelRenderer(this, 0, 0);
      baseg.addBox(-3F, 0F, -2.5F, 1, 2, 5);
      baseg.setRotationPoint(0.5F, 8F, 0.5F);
      baseg.setTextureSize(32, 32);
      baseg.mirror = true;
      setRotation(baseg, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    render(f5);
   
  }
  
  public void render(float f5){
	  middletip.renderWithRotation(f5);
	    uppertip.renderWithRotation(f5);
	    lowertip.renderWithRotation(f5);
	    handle.renderWithRotation(f5);
	    base.renderWithRotation(f5);
	    sharpa.renderWithRotation(f5);
	    sharpc.renderWithRotation(f5);
	    sharpb.renderWithRotation(f5);
	    basea.renderWithRotation(f5);
	    baseb.renderWithRotation(f5);
	    basec.renderWithRotation(f5);
	    based.renderWithRotation(f5);
	    basee.renderWithRotation(f5);
	    basef.renderWithRotation(f5);
	    baseg.renderWithRotation(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity a)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, a);
  }

}
