package mhfc.net.client.gui;

import mhfc.net.client.container.ContainerHunterBench;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiHunterBench extends GuiContainer {

	public GuiHunterBench(InventoryPlayer par1InventoryPlayer, World par2World,
			int par3, int par4, int par5) {
		super(new ContainerHunterBench(par1InventoryPlayer, par2World, par3,
				par4, par5));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(StatCollector
				.translateToLocal(MHFCReference.gui_hunterbench_name), 40 + 5,
				-10 - 10, 4210752);
		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("container.inventory"), 50 + 14,
				this.ySize - 96 - 10 - 19, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {

		this.mc.getTextureManager().bindTexture(
				new ResourceLocation(MHFCReference.gui_hunterbench_back_tex));

		this.ySize = 231;
		this.xSize = 176;

		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;

		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
	}
}
