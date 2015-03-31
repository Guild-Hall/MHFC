package mhfc.net.client.model.weapon.longsword;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLSEagerCleaver extends ModelBase
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
    ModelRenderer base1;
    ModelRenderer base2;
    ModelRenderer base3;
  
  public ModelLSEagerCleaver()
  {
    textureWidth = 32;
    textureHeight = 32;
    
      middletip = new ModelRenderer(this, 0, 24);
      middletip.addBox(-0.5F, 0F, -0.5F, 2, 2, 2);
      middletip.setRotationPoint(0F, 16F, 0F);
      middletip.setTextureSize(32, 32);
      middletip.mirror = true;
      setRotation(middletip, 0F, 0F, 0F);
      uppertip = new ModelRenderer(this, 0, 20);
      uppertip.addBox(-0.5F, 0F, -0.5F, 2, 2, 2);
      uppertip.setRotationPoint(0F, 12F, 0F);
      uppertip.setTextureSize(32, 32);
      uppertip.mirror = true;
      setRotation(uppertip, 0F, 0F, 0F);
      lowertip = new ModelRenderer(this, 0, 28);
      lowertip.addBox(-0.5F, 0F, -0.5F, 2, 2, 2);
      lowertip.setRotationPoint(0F, 20F, 0F);
      lowertip.setTextureSize(32, 32);
      lowertip.mirror = true;
      setRotation(lowertip, 0F, 0F, 0F);
      handle = new ModelRenderer(this, 8, 21);
      handle.addBox(0F, 0F, 0F, 1, 10, 1);
      handle.setRotationPoint(0F, 10F, 0F);
      handle.setTextureSize(32, 32);
      handle.mirror = true;
      setRotation(handle, 0F, 0F, 0F);
      base = new ModelRenderer(this, 0, 0);
      base.addBox(0F, 0F, -1F, 3, 1, 3);
      base.setRotationPoint(0F, 9F, 2F);
      base.setTextureSize(32, 32);
      base.mirror = true;
      setRotation(base, 0F, 1.570796F, 0F);
      sharpa = new ModelRenderer(this, 26, 15);
      sharpa.addBox(0F, 0F, -0.5F, 1, 15, 2);
      sharpa.setRotationPoint(0F, -6F, 1F);
      sharpa.setTextureSize(32, 32);
      sharpa.mirror = true;
      setRotation(sharpa, 0F, 1.570796F, 0F);
      sharpc = new ModelRenderer(this, 14, 20);
      sharpc.addBox(0F, -10F, 0F, 1, 10, 2);
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
      base1 = new ModelRenderer(this, 0, 15);
      base1.addBox(0F, 0F, -1F, 1, 1, 2);
      base1.setRotationPoint(-2F, 9F, 1F);
      base1.setTextureSize(32, 32);
      base1.mirror = true;
      setRotation(base1, 0F, 1.570796F, 0F);
      base2 = new ModelRenderer(this, 0, 10);
      base2.addBox(0F, 0F, -2F, 1, 1, 2);
      base2.setRotationPoint(4F, 9F, 1F);
      base2.setTextureSize(32, 32);
      base2.mirror = true;
      setRotation(base2, 0F, 1.570796F, 0F);
      base3 = new ModelRenderer(this, 20, 0);
      base3.addBox(0F, 0F, 0F, 2, 1, 3);
      base3.setRotationPoint(-1F, 8F, 1.5F);
      base3.setTextureSize(32, 32);
      base3.mirror = true;
      setRotation(base3, 0F, 1.570796F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    render(f5);

  }
  
  public void render(float f5) {
	    middletip.renderWithRotation(f5);
	    uppertip.renderWithRotation(f5);
	    lowertip.renderWithRotation(f5);
	    handle.renderWithRotation(f5);
	    base.renderWithRotation(f5);
	    sharpa.renderWithRotation(f5);
	    sharpc.renderWithRotation(f5);
	    sharpb.renderWithRotation(f5);
	    base1.renderWithRotation(f5);
	    base2.renderWithRotation(f5);
	    base3.renderWithRotation(f5);	
}

private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5,e );
  }

}
