package mhfc.heltrato.client.gui;

import mhfc.heltrato.client.container.ContainerHunterBench;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiHunterBench extends GuiContainer
{

public GuiHunterBench(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
{
super(new ContainerHunterBench(par1InventoryPlayer, par2World, par3, par4, par5));
}

public static ResourceLocation BigCrafting = new ResourceLocation("mhfc:textures/gui/bench.png");


protected void drawGuiContainerForegroundLayer(int par1, int par2)
{
this.fontRendererObj.drawString("Hunter's Bench", 40 + 5, - 10 - 10, 4210752);
this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 50 + 14, this.ySize - 96 - 10 - 19, 4210752);
}


protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
{

this.mc.getTextureManager().bindTexture(BigCrafting);

this.ySize = 231;
this.xSize = 176;

int var5 = (this.width - this.xSize) / 2;
int var6 = (this.height - this.ySize) / 2;
int var7;

this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
}
}