package mhfc.heltrato.client.render.weapon;

import org.lwjgl.opengl.GL11;

import mhfc.heltrato.client.model.weapon.ModelHKirinSpark;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class RenderHKirinSpark implements IItemRenderer{
	
	private ModelHKirinSpark weapon;
	
	public RenderHKirinSpark() {
		weapon = new ModelHKirinSpark();
	}

	 @Override
     public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
             switch (type) {

             case EQUIPPED: // render in third person
            	 	 float scale = 1.5f;
            	 	 GL11.glScalef(scale, scale, scale);
                     GL11.glPushMatrix(); //start gl rendering for this section
                     Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("mhfc:textures/weapons/(H)kirinspark.png"));
                     GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f);      //rotate 0 ° on X axis
                     GL11.glRotatef(-5F, 0.0f, 1.0f, 0.0f);    // rotate -5 ° on Y axis
                     GL11.glRotatef(-120F, 0.0f, 0.0f, 1.0f);  // rotate -150 ° on Z axis
                     GL11.glTranslatef(-0.4F, -0.6F, -0.1F);  //translate model to fit in the hand of the player
//the entity argument can/could be passed to as null.
                     weapon.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
                                     0.0625F);
                     GL11.glPopMatrix(); 
                     break;

             case EQUIPPED_FIRST_PERSON:

//rince and repeat the rendering. adjust axis' and translation as needed
                     GL11.glPushMatrix();
                     scale = 1.4f;
                     GL11.glScalef(scale, scale, scale);
                     Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("mhfc:textures/weapons/(H)kirinspark.png"));
                     GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f);
                     GL11.glRotatef(-5F, 0.0f, 1.0f, 0.0f);
                     GL11.glRotatef(-150F, 0.0f, 0.0f, 1.0f);
                     GL11.glTranslatef(-0.4F, -0.4F, -0.1F);
                     weapon.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
                                     0.0625F);
                     GL11.glPopMatrix();
                     break;

             case ENTITY:
                     GL11.glPushMatrix();
                     scale = 3F;
                     GL11.glScalef(scale, scale, scale);
                     Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("mhfc:textures/weapons/(H)kirinspark.png"));
                     GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
                     GL11.glRotatef(0F, 0.0f, 1.0f, 0.0f);
                     GL11.glRotatef(45F, 0.0f, 0.0f, 1.0f);
                     GL11.glTranslatef(-0.2F, -0.6F, 0F);
                     weapon.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
                                     0.0625F);
                     GL11.glPopMatrix();
                     break;

             case INVENTORY:
                     GL11.glPushMatrix();
                     scale = 1.2F;
                     GL11.glScalef(scale, scale, scale);
                     Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("mhfc:textures/weapons/(H)kirinspark.png"));

                     GL11.glRotatef(200F, 1.0f, 0.0f, 0.0f);
                     GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
                     GL11.glTranslatef(-0.0F, -0.3F, -0.0F);
//this is a method made by me in my model class to render only the modelparts, without an entity argument, because in your inventory, //the entity is always null.
                     weapon.render(0.0625F);
                     GL11.glPopMatrix();
                     break;

             default:
                     break;
             }
     }
	
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		  switch (type) {
          case INVENTORY:
                  return true;
          default:
                  break;
          }
          return false;

  }

}
