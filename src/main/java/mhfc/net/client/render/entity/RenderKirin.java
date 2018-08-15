package mhfc.net.client.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLiving;

public class RenderKirin {

	
	
	
	
	  protected void renderElectricField(float x, float y, float z, EntityLiving entityliving)
	  {
	    GL11.glDisable(3553);
	    GL11.glDisable(2896);
	    Tessellator tessellator = Tessellator.getInstance();
	    VertexBuffer buffer = tessellator.getBuffer();
	    int color = 5592575;
	    GL11.glEnable(3042);
	    GL11.glBlendFunc(770, 1);
	    Random rnd = new Random();
	    rnd.setSeed(entityliving.getEntityId() + entityliving.ticksExisted);
	    int steps = 16;
	    GL11.glLineWidth(3.0F);
	    for (int a = 0; a < 5; a++)
	    {
	      steps = rnd.nextInt(26) + 5;
	      buffer.begin(3, DefaultVertexFormats.POSITION_TEX_NORMAL);
	     // buffer.startDrawing(3);
	      //buffer.
	      buffer.color(0.5F, 0.64F, 1.0F, 0.6F);
	      int startX = rnd.nextInt();
	      int startY = rnd.nextInt();
	      int startZ = rnd.nextInt();
	      for (int i = 0; i <= steps; i++)
	      {
	        float stepVariation = i / steps;
	        double dist = entityliving.width;
	        double boltSize = 80.0D;
	        double varX = Math.sin((i + startX) / boltSize * 3.141592653589793D * 2.0D) * dist + (rnd.nextDouble() - 0.5D) * 0.5D;
	        double varZ = Math.cos((i + startZ) / boltSize * 3.141592653589793D * 2.0D) * dist + (rnd.nextDouble() - 0.5D) * 0.5D;
	        double varY = Math.sin((entityliving.ticksExisted + i + startY) / boltSize * 3.141592653589793D) + rnd.nextDouble() + 1.8D;
	    	
	        buffer.pos(x + varX, y + varY, z + varZ).endVertex();;
	      }
	      tessellator.draw();
	    }
	    GL11.glDisable(3042);
	    GL11.glEnable(3553);
	    GL11.glEnable(2896);
	  }
	  
}
