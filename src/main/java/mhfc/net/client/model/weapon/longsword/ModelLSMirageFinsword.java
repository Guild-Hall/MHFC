package mhfc.net.client.model.weapon.longsword;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLSMirageFinsword extends ModelBase
{
  //fields
    ModelRenderer middletip;
    ModelRenderer uppertipa;
    ModelRenderer lowertip;
    ModelRenderer handle;
    ModelRenderer sharpa;
    ModelRenderer sharpc;
    ModelRenderer sharpb;
    ModelRenderer sharpd;
    ModelRenderer uppertipb;
    ModelRenderer sharpce;
    ModelRenderer sharpcf;
    ModelRenderer sharpcg;
    ModelRenderer uppertipc;
  
  public ModelLSMirageFinsword()
  {
    textureWidth = 32;
    textureHeight = 32;
    
      middletip = new ModelRenderer(this, 0, 0);
      middletip.addBox(-0.5F, 0F, -0.5F, 2, 1, 2);
      middletip.setRotationPoint(0F, 17F, 0F);
      middletip.setTextureSize(32, 32);
      middletip.mirror = true;
      setRotation(middletip, 0F, 0F, 0F);
      uppertipa = new ModelRenderer(this, 0, 4);
      uppertipa.addBox(-0.5F, -2F, -0.5333334F, 1, 2, 2);
      uppertipa.setRotationPoint(2.5F, 13F, 0F);
      uppertipa.setTextureSize(32, 32);
      uppertipa.mirror = true;
      setRotation(uppertipa, 0F, 0F, 0.7853982F);
      lowertip = new ModelRenderer(this, 0, 28);
      lowertip.addBox(-0.5F, 0F, -0.5F, 2, 2, 2);
      lowertip.setRotationPoint(0F, 20F, 0F);
      lowertip.setTextureSize(32, 32);
      lowertip.mirror = true;
      setRotation(lowertip, 0F, 0F, 0F);
      handle = new ModelRenderer(this, 0, 9);
      handle.addBox(0F, 0F, 0F, 1, 9, 1);
      handle.setRotationPoint(0F, 14F, 0F);
      handle.setTextureSize(32, 32);
      handle.mirror = true;
      setRotation(handle, 0F, 0F, 0F);
      sharpa = new ModelRenderer(this, 15, 21);
      sharpa.addBox(0F, 0F, -0.5F, 2, 7, 3);
      sharpa.setRotationPoint(-0.5F, 5F, 1.5F);
      sharpa.setTextureSize(32, 32);
      sharpa.mirror = true;
      setRotation(sharpa, 0F, 1.570796F, 0F);
      sharpc = new ModelRenderer(this, 9, 0);
      sharpc.addBox(0F, -4F, 1F, 1, 4, 1);
      sharpc.setRotationPoint(-0.5F, -11F, 1F);
      sharpc.setTextureSize(32, 32);
      sharpc.mirror = true;
      setRotation(sharpc, -0.7853982F, 1.570796F, 0F);
      sharpb = new ModelRenderer(this, 16, 0);
      sharpb.addBox(0F, -5F, 0F, 1, 5, 2);
      sharpb.setRotationPoint(-0.5F, -6F, 1F);
      sharpb.setTextureSize(32, 32);
      sharpb.mirror = true;
      setRotation(sharpb, 0F, 1.570796F, 0F);
      sharpd = new ModelRenderer(this, 25, 0);
      sharpd.addBox(0F, 0F, -0.5F, 1, 11, 2);
      sharpd.setRotationPoint(0F, -6F, 1F);
      sharpd.setTextureSize(32, 32);
      sharpd.mirror = true;
      setRotation(sharpd, 0F, 1.570796F, 0F);
      uppertipb = new ModelRenderer(this, 5, 9);
      uppertipb.addBox(0F, 0F, -0.5F, 1, 4, 2);
      uppertipb.setRotationPoint(-2F, 14F, 0F);
      uppertipb.setTextureSize(32, 32);
      uppertipb.mirror = true;
      setRotation(uppertipb, 0F, 0F, -0.4014257F);
      sharpce = new ModelRenderer(this, 12, 9);
      sharpce.addBox(0F, -5.666667F, -0.6F, 1, 1, 2);
      sharpce.setRotationPoint(-0.5F, -11F, 1F);
      sharpce.setTextureSize(32, 32);
      sharpce.mirror = true;
      setRotation(sharpce, 0F, 1.570796F, 0F);
      sharpcf = new ModelRenderer(this, 19, 9);
      sharpcf.addBox(0F, -3F, 4F, 1, 3, 1);
      sharpcf.setRotationPoint(-0.5F, -11F, 1F);
      sharpcf.setTextureSize(32, 32);
      sharpcf.mirror = true;
      setRotation(sharpcf, 0.7853982F, 1.570796F, 0F);
      sharpcg = new ModelRenderer(this, 19, 14);
      sharpcg.addBox(0F, -5F, 0F, 1, 5, 1);
      sharpcg.setRotationPoint(-0.5F, -11F, 1F);
      sharpcg.setTextureSize(32, 32);
      sharpcg.mirror = true;
      setRotation(sharpcg, -0.7853982F, 1.570796F, 0F);
      uppertipc = new ModelRenderer(this, 0, 20);
      uppertipc.addBox(-0.5F, 0F, -0.5F, 5, 2, 2);
      uppertipc.setRotationPoint(-1.5F, 12F, 0F);
      uppertipc.setTextureSize(32, 32);
      uppertipc.mirror = true;
      setRotation(uppertipc, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    render(f5);

  }
  
  public void render(float f5){
	    middletip.renderWithRotation(f5);
	    uppertipa.renderWithRotation(f5);
	    lowertip.renderWithRotation(f5);
	    handle.renderWithRotation(f5);
	    sharpa.renderWithRotation(f5);
	    sharpc.renderWithRotation(f5);
	    sharpb.renderWithRotation(f5);
	    sharpd.renderWithRotation(f5);
	    uppertipb.renderWithRotation(f5);
	    sharpce.renderWithRotation(f5);
	    sharpcf.renderWithRotation(f5);
	    sharpcg.renderWithRotation(f5);
	    uppertipc.renderWithRotation(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity a)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5,a);
  }

}
