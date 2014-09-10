package mhfc.net.client.gui;

import mhfc.net.client.container.ContainerHunterBench;
import mhfc.net.common.tile.TileHunterBench;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class GuiHunterBench extends MHFCTabbedGui {
	protected class CraftArmorTab implements IMHFCTab {

		int startIndex, endIndex;

		TileHunterBench bench;

		/**
		 * 
		 * @param start
		 *            Start index of slots on this page, inclusive
		 * @param end
		 *            End index of slots on this page, exclusive
		 */
		public CraftArmorTab(int start, int end, TileHunterBench bench) {
			startIndex = start;
			endIndex = end;
			this.bench = bench;
		}

		@Override
		public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
				float partialTick) {
			fontRendererObj
					.drawString(
							StatCollector
									.translateToLocal(MHFCReference.gui_hunterbench_tab_armor_name),
							guiLeft + 5, guiTop + 4, 4210752);
			// TODO reposition Slots accordingly
		}

		@Override
		public void handleClick(int relativeX, int relativeY, int button) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean containsSlot(Slot slot) {
			if (slot.slotNumber >= startIndex && slot.slotNumber < endIndex) {
				return slot.slotNumber != bench.outputSlot || bench.isWorking();
			}
			return false;
		}

	}

	protected class CraftWeaponTab implements IMHFCTab {

		int startIndex, endIndex;

		TileHunterBench bench;

		/**
		 * 
		 * @param start
		 *            Start index of slots on this page, inclusive
		 * @param end
		 *            End index of slots on this page, exclusive
		 */
		public CraftWeaponTab(int start, int end, TileHunterBench bench) {
			startIndex = start;
			endIndex = end;
			this.bench = bench;
		}

		@Override
		public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
				float partialTick) {
			fontRendererObj
					.drawString(
							StatCollector
									.translateToLocal(MHFCReference.gui_hunterbench_tab_weapon_name),
							guiLeft + 5, guiTop + 4, 4210752);
		}

		@Override
		public void handleClick(int relativeX, int relativeY, int button) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean containsSlot(Slot slot) {
			if (slot.slotNumber >= startIndex && slot.slotNumber < endIndex) {
				return slot.slotNumber != bench.outputSlot || bench.isWorking();
			}
			return false;
		}

	}

	protected class WeaponTreeTab implements IMHFCTab {

		@Override
		public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
				float partialTick) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0, 0, 100f);
			drawRect(guiLeft + 10, guiTop + 10, guiLeft + xSize - 10, guiTop
					+ ySize - 10, 0xFF101010);
			GL11.glPopMatrix();
		}

		@Override
		public void handleClick(int relativeX, int relativeY, int button) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean containsSlot(Slot slot) {
			return false;
		}

	}

	public GuiHunterBench(InventoryPlayer par1InventoryPlayer, World par2World,
			TileHunterBench tileEntity, int x, int y, int z) {
		super(new ContainerHunterBench(par1InventoryPlayer, par2World,
				tileEntity, x, y, z), 3);
		this.xSize = 324;
		this.ySize = 165;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		tabNames = new String[]{"Armor", "Weapons", "Weapon tree"};
		this.tabList.add(new CraftArmorTab(51, 61, tileEntity));
		this.tabList.add(new CraftWeaponTab(51, 61, tileEntity));
		this.tabList.add(new WeaponTreeTab());
		setTab(0);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		Tessellator.instance.addTranslation(0, 0, 1f);
		this.mc.getTextureManager().bindTexture(
				new ResourceLocation(MHFCReference.gui_hunterbench_back_tex));
		int posX = (this.width - this.xSize) / 2;
		int posY = (this.height - this.ySize) / 2;

		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(posX, posY, this.zLevel, 0, 0);
		tess.addVertexWithUV(posX, posY + ySize, this.zLevel, 0, 1);
		tess.addVertexWithUV(posX + xSize, posY + ySize, this.zLevel, 1, 1);
		tess.addVertexWithUV(posX + xSize, posY, this.zLevel, 1, 0);
		tess.draw();

		Tessellator.instance.addTranslation(0, 0, -1f);
	}

}