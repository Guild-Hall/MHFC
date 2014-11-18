package mhfc.net.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mhfc.net.client.container.ContainerHunterBench;
import mhfc.net.client.gui.ClickableGuiList.GuiListItem;
import mhfc.net.client.gui.ClickableGuiList.GuiListStringItem;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.core.registry.MHFCRegEquipmentRecipe;
import mhfc.net.common.core.registry.MHFCRegItem;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.tile.TileHunterBench;
import mhfc.net.common.util.gui.MHFCGuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.ENTITY;

public class GuiHunterBench extends MHFCTabbedGui {

	public class RecipeItem implements GuiListItem {
		String representation;

		public RecipeItem(EquipmentRecipe recipe) {
			representation = recipe.getRecipeOutput().getDisplayName();
		}

		@Override
		public String getRepresentationString() {
			return representation;
		}

	}

	protected abstract class BenchEntityTab implements IMHFCTab {
		protected TileHunterBench bench;

		protected List<ClickableGuiList<?>> clickableLists;

		/**
		 * 
		 * @param start
		 *            Start index of slots on this page, inclusive
		 * @param end
		 *            End index of slots on this page, exclusive
		 */
		public BenchEntityTab(TileHunterBench bench) {
			this.bench = bench;
			clickableLists = new ArrayList<ClickableGuiList<?>>();
		}

		@Override
		public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
				float partialTick) {

			updateListPositions();
			for (ClickableGuiList<?> list : clickableLists) {
				list.draw();
			}
			fontRendererObj.drawSplitString("Inventory", posX + 6, posY + 5,
					500, 0x404040);

			GuiHunterBench.this.startCrafting.visible = !bench.isWorking();
			GuiHunterBench.this.startCrafting.enabled = bench
					.canBeginCrafting();
			/*
			 * fontRendererObj.drawString(bench.isWorking() ? "Working" :
			 * "Sleeping", 50, 50, 0x404040);
			 * fontRendererObj.drawString(bench.getHeatFromItem() + "", 50, 60,
			 * 0x404040); fontRendererObj.drawString(bench.getHeatLength() + "",
			 * 50, 70, 0x404040);
			 * fontRendererObj.drawString(bench.getHeatStrength() + "", 50, 80,
			 * 0x404040);
			 * fontRendererObj.drawString(bench.getItemSmeltDuration() + "", 50,
			 * 90, 0x404040);
			 */
			drawItemModelAndHeat(new ItemStack(MHFCRegItem.mhfcitembhunter));
		};

		@Override
		public void handleClick(int relativeX, int relativeY, int button) {
			for (ClickableGuiList<?> list : clickableLists) {
				if (list.handleClick(relativeX - list.posX, relativeY
						- list.posY, button))
					listUpdated(list);
			}
		}

		protected void listUpdated(ClickableGuiList<?> list) {
		}

		protected void updateListPositions() {
		}

		@Override
		public boolean containsSlot(Slot slot) {
			if (slot.inventory == tileEntity
					|| (slot.inventory instanceof InventoryPlayer && slot.slotNumber > 51))
				return true;
			return false;
		}

		@Override
		public void updateScreen() {
			updateListPositions();
		}

		@Override
		public void onClose() {
			if (!bench.isWorking())
				bench.setRecipe(null);
		}

		@Override
		public void onOpen() {
			// TODO Auto-generated method stub

		}

	}

	protected class CraftArmorTab extends BenchEntityTab {
		ClickableGuiList<RecipeItem> armorRecipeList;
		ClickableGuiList<GuiListStringItem> armorTypeList;

		public CraftArmorTab(TileHunterBench bench) {
			super(bench);
			armorRecipeList = new ClickableGuiList<RecipeItem>(70, ySize - 10,
					20);
			armorTypeList = new ClickableGuiList<GuiListStringItem>(70,
					ySize - 10);
			armorTypeList.setAlignmentMid(true);
			this.clickableLists.add(armorTypeList);
			this.clickableLists.add(armorRecipeList);
			initializeTypeList();
			armorTypeList.setSelected(0);
			listUpdated(armorTypeList);
		}

		private void initializeTypeList() {
			String[] strings = {"Helmet", "Chestplate", "Waist", "Boots"};
			for (int i = 0; i < 4; i++) {
				armorTypeList.add(new GuiListStringItem(strings[i]));
			}
		}

		@Override
		protected void listUpdated(ClickableGuiList<?> list) {
			if (list == armorTypeList) {
				armorRecipeList.clear();
				int selected = armorTypeList.getSelected();
				if (selected < 4)
					fillRecipeList(MHFCRegEquipmentRecipe
							.getRecipesFor(selected));
			} else if (list == armorRecipeList) {
				int selected = armorTypeList.getSelected();
				EquipmentRecipe rec = MHFCRegEquipmentRecipe.getRecipeFor(
						armorRecipeList.getSelected(), selected);
				bench.setRecipe(rec);
			}
		}

		@Override
		protected void updateListPositions() {
			armorRecipeList.setPosition(guiLeft + 153, guiTop + 5);
			armorTypeList.setPosition(guiLeft + 78, guiTop + 5);
		}

		private void fillRecipeList(Set<EquipmentRecipe> correspondingRecipes) {
			if (correspondingRecipes == null) {
				return;
			}
			for (EquipmentRecipe rec : correspondingRecipes) {
				armorRecipeList.add(new RecipeItem(rec));
			}
		}

		@Override
		public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
				float partialTick) {
			super.drawTab(posX, posY, mousePosX, mousePosY, partialTick);
			/*
			 * fontRendererObj .drawString( StatCollector
			 * .translateToLocal(MHFCReference.gui_hunterbench_tab_armor_name),
			 * guiLeft + 5, guiTop + 4, 4210752);
			 */
		}
	}

	protected class CraftWeaponTab extends BenchEntityTab {

		ClickableGuiList<RecipeItem> weaponRecipeList;
		ClickableGuiList<GuiListStringItem> weaponTypeList;

		/**
		 * 
		 * @param start
		 *            Start index of slots on this page, inclusive
		 * @param end
		 *            End index of slots on this page, exclusive
		 */
		public CraftWeaponTab(TileHunterBench bench) {
			super(bench);
			weaponRecipeList = new ClickableGuiList<RecipeItem>(70, ySize - 10,
					20);
			weaponTypeList = new ClickableGuiList<GuiListStringItem>(70,
					ySize - 10);
			this.clickableLists.add(weaponTypeList);
			this.clickableLists.add(weaponRecipeList);
			initializeTypeList();
			weaponTypeList.setSelected(0);
			listUpdated(weaponTypeList);
		}

		private void initializeTypeList() {
			String[] strings = {"Greatsword\nLongsword", "Hammer\nHuntingHorn",
					"Sword+Shield\nDual Sword", "Lance\nGunlance",
					"Bow\nBowguns"};
			for (int i = 0; i < 5; i++) {
				weaponTypeList.add(new GuiListStringItem(strings[i]));
			}
		}

		@Override
		protected void updateListPositions() {
			weaponRecipeList.setPosition(guiLeft + 153, guiTop + 5);
			weaponTypeList.setPosition(guiLeft + 78, guiTop + 5);
		}

		@Override
		protected void listUpdated(ClickableGuiList<?> list) {
			if (list == weaponTypeList) {
				weaponRecipeList.clear();
				int selected = weaponTypeList.getSelected();
				fillRecipeList(MHFCRegEquipmentRecipe
						.getRecipesFor(selected * 2 + 4));
				fillRecipeList(MHFCRegEquipmentRecipe
						.getRecipesFor(selected * 2 + 5));
				if (selected == 4)
					fillRecipeList(MHFCRegEquipmentRecipe
							.getRecipesFor(selected * 2 + 6));
			}
		}

		private void fillRecipeList(Set<EquipmentRecipe> correspondingRecipes) {
			if (correspondingRecipes == null)
				return;
			for (EquipmentRecipe rec : correspondingRecipes) {
				weaponRecipeList.add(new RecipeItem(rec));
			}
		}

		@Override
		public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
				float partialTick) {
			super.drawTab(posX, posY, mousePosX, mousePosY, partialTick);
			/*
			 * fontRendererObj .drawString( StatCollector
			 * .translateToLocal(MHFCReference.gui_hunterbench_tab_weapon_name),
			 * guiLeft + 5, guiTop + 4, 4210752);
			 */
		}

	}

	protected class WeaponTreeTab implements IMHFCTab {

		@Override
		public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
				float partialTick) {
			GuiHunterBench.this.startCrafting.visible = false;
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			drawRect(guiLeft + 10, guiTop + 10, guiLeft + xSize - 10, guiTop
					+ ySize - 10, 0xFF101010);
			drawCenteredString(fontRendererObj, "Not yet implemented", guiLeft
					+ xSize / 2, guiTop + ySize / 2, 0xaaaaaa);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glPopMatrix();
		}

		@Override
		public void handleClick(int relativeX, int relativeY, int button) {

			// TODO Dragging should be handeled correctly

		}

		@Override
		public boolean containsSlot(Slot slot) {
			return false;
		}

		@Override
		public void updateScreen() {
		}

		@Override
		public void onClose() {
		}

		@Override
		public void onOpen() {
		}

	}

	public final GuiButton startCrafting;
	public final TileHunterBench tileEntity;

	public GuiHunterBench(InventoryPlayer par1InventoryPlayer, World par2World,
			TileHunterBench tileEntity, int x, int y, int z) {
		super(new ContainerHunterBench(par1InventoryPlayer, par2World,
				tileEntity, x, y, z), 3);
		this.tileEntity = tileEntity;
		this.xSize = 374;
		this.ySize = 182;
		mc = Minecraft.getMinecraft();
		ScaledResolution s = new ScaledResolution(mc, mc.displayWidth,
				mc.displayHeight);
		width = s.getScaledWidth();
		height = s.getScaledHeight();
		this.guiLeft = (s.getScaledWidth() - this.xSize - tabWidth) / 2
				+ tabWidth;
		this.guiTop = (s.getScaledHeight() - this.ySize) / 2;
		tabNames = new String[]{"Armor", "Weapons", "Weapon tree"};
		this.tabList.add(new CraftArmorTab(tileEntity));
		this.tabList.add(new CraftWeaponTab(tileEntity));
		this.tabList.add(new WeaponTreeTab());
		setTab(0);
		startCrafting = new GuiButton(0,
				guiLeft + 228 + (xSize - 228 - 60) / 2, guiTop + 139, 40, 20,
				"Craft") {
			@Override
			public void mouseReleased(int p_146118_1_, int p_146118_2_) {
				GuiHunterBench.this.tileEntity.beginCrafting();
			}
		};
	}

	private void drawItemModelAndHeat(ItemStack itemToRender) {

		int rectX = guiLeft + 228, rectY = guiTop + 23;
		int rectW = 7 * 18 - 2, rectH = 96;
		int scale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight)
				.getScaleFactor();
		GL11.glPushMatrix();
		drawRect(rectX, rectY, rectX + rectW, rectY + rectH, 0xFF000000);

		GL11.glScissor(rectX * scale, mc.displayHeight - rectY * scale, rectW
				* scale, rectH * scale);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glClearDepth(1.0f);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);

		IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(
				itemToRender, ENTITY);
		if (customRenderer == null) {
			System.out.println("no renderer");
			return;
		}
		customRenderer.renderItem(ItemRenderType.ENTITY, itemToRender, null,
				null);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();

		// Draw heat background
		// Draw heat indicator
		// Draw heat target
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		buttonList.add(startCrafting);
		super.initGui();
		this.guiLeft = (this.width - this.xSize - tabWidth) / 2 + tabWidth;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void drawTabBackgroundLayer() {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		this.mc.getTextureManager().bindTexture(
				MHFCRegQuestVisual.QUEST_STATUS_INVENTORY_BACKGROUND);
		int posX = guiLeft;
		int posY = guiTop;
		MHFCGuiUtil.drawTexturedBoxFromBorder(posX, posY, this.zLevel,
				this.xSize, this.ySize, 40, 40f / 256, 248f / 256, 166f / 256);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		startCrafting.xPosition = guiLeft + 228 + (xSize - 228 - 60) / 2;
		startCrafting.yPosition = guiTop + 139;
	}

}