package mhfc.heltrato.client.render.mob;

import java.util.List;
import java.util.Random;

import mhfc.heltrato.common.entity.mob.EntityPopo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPopo extends RenderLiving
{
	private float scale;
	private static final ResourceLocation texture = new ResourceLocation("mhfc:textures/mobs/popo.png");
	

    public RenderPopo(ModelBase par1ModelBase, float par2, float par3)
    {
        super(par1ModelBase, par2 * par3);
        this.scale = par3;
    }

    /**
     * Applies the scale to the transform matrix
     */
    protected void preRenderScale(EntityPopo par1, float par2)
    {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected ResourceLocation func_110870_a(EntityPopo par1)
    {
        return texture;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.preRenderScale((EntityPopo)par1EntityLivingBase, par2);
        super.preRenderCallback(par1EntityLivingBase, par2);
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.func_110870_a((EntityPopo)par1Entity);
    }
    

	
}