package mhfc.net.client.gui;

import mhfc.net.client.container.ContainerHunterBench;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class GuiHunterBench extends MHFCTabbedGui {
	protected class CraftArmorTab implements IMHFCTab {

		int startIndex, endIndex;

		/**
		 * 
		 * @param start
		 *            Start index of slots on this page, inclusive
		 * @param end
		 *            End index of slots on this page, exclusive
		 */
		public CraftArmorTab(int start, int end) {
			startIndex = start;
			endIndex = end;
		}

		@Override
		public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
				float partialTick) {
			fontRendererObj
					.drawString(
							StatCollector
									.translateToLocal(MHFCReference.gui_hunterbench_tab_armor_name),
							guiLeft + 5, guiTop + 17, 4210752);
		}

		@Override
		public void handleClick(int relativeX, int relativeY, int button) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean containsSlot(Slot slot) {
			return slot.slotNumber >= startIndex && slot.slotNumber < endIndex;
		}

	}

	protected class CraftWeaponTab implements IMHFCTab {

		int startIndex, endIndex;

		/**
		 * 
		 * @param start
		 *            Start index of slots on this page, inclusive
		 * @param end
		 *            End index of slots on this page, exclusive
		 */
		public CraftWeaponTab(int start, int end) {
			startIndex = start;
			endIndex = end;
		}

		@Override
		public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
				float partialTick) {
			fontRendererObj
					.drawString(
							StatCollector
									.translateToLocal(MHFCReference.gui_hunterbench_tab_weapon_name),
							guiLeft + 5, guiTop + 17, 4210752);
		}

		@Override
		public void handleClick(int relativeX, int relativeY, int button) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean containsSlot(Slot slot) {
			return slot.slotNumber >= startIndex && slot.slotNumber < endIndex;
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
			int x, int y, int z) {
		super(
				new ContainerHunterBench(par1InventoryPlayer, par2World, x, y,
						z), 3);
		this.xSize = 324;
		this.ySize = 165;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		tabNames = new String[]{"Armor", "Weapons", "Weapon tree"};
		this.tabList.add(new CraftArmorTab(0, 16));
		this.tabList.add(new CraftWeaponTab(16, 52));
		this.tabList.add(new WeaponTreeTab());
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}