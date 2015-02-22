package mhfc.net.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mhfc.net.client.container.ContainerHunterBench;
import mhfc.net.client.gui.ClickableGuiList.GuiListItem;
import mhfc.net.client.gui.ClickableGuiList.GuiListStringItem;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.tile.TileHunterBench;
import mhfc.net.common.util.gui.MHFCGuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.ENTITY;

public class GuiHunterBench extends MHFCTabbedGui {

	static final int maxHeat = 500;

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

			GL11.glColor4f(1f, 1f, 1f, 1f);
			mc.getTextureManager().bindTexture(
					MHFCRegQuestVisual.QUEST_HUNTERBENCH_BACKGROUND);
			MHFCGuiUtil.drawTexturedBoxFromBorder(posX, posY, zLevel, xSize,
					ySize, 0, 0, 1f, 1f);

			updateListPositions();
			for (ClickableGuiList<?> list : clickableLists) {
				list.draw();
			}
			fontRendererObj.drawSplitString("Inventory", posX + 6, posY + 12,
					500, 0x404040);

			GuiHunterBench.this.startCrafting.visible = !bench.isWorking();
			GuiHunterBench.this.startCrafting.enabled = bench
					.canBeginCrafting();
			drawItemModelAndHeat(bench);
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
		public void updateTab(int pX, int pY) {
			updateListPositions();
		}

		@Override
		public void onClose() {
		}

		@Override
		public void onOpen() {
			bench.refreshState();
		}

		@Override
		public void handleMouseUp(int mouseX, int mouseY, int button) {
		}

		@Override
		public void handleMovement(int mouseX, int mouseY) {
		}

		@Override
		public void handleMovementMouseDown(int mouseX, int mouseY, int button,
				long timeDiff) {
		}

	}

	protected class CraftArmorTab extends BenchEntityTab {
		ClickableGuiList<RecipeItem> armorRecipeList;
		ClickableGuiList<GuiListStringItem> armorTypeList;

		public CraftArmorTab(TileHunterBench bench) {
			super(bench);
			armorRecipeList = new ClickableGuiList<RecipeItem>(70, ySize - 24,
					20);
			armorTypeList = new ClickableGuiList<GuiListStringItem>(70,
					ySize - 24);
			armorTypeList.setAlignmentMid(true);
			this.clickableLists.add(armorTypeList);
			this.clickableLists.add(armorRecipeList);
			initializeTypeList();
			if (bench != null) {
				EquipmentRecipe rec = bench.getRecipe();
				int type = MHFCEquipementRecipeRegistry.getType(rec);
				int number = MHFCEquipementRecipeRegistry.getIDFor(rec, type);
				armorTypeList.setSelected(type);
				listUpdated(armorTypeList);
				armorRecipeList.setSelected(number);
			}
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
					fillRecipeList(MHFCEquipementRecipeRegistry
							.getRecipesFor(selected));
			} else if (list == armorRecipeList) {
				int typeSelected = armorTypeList.getSelected();
				EquipmentRecipe rec = MHFCEquipementRecipeRegistry
						.getRecipeFor(armorRecipeList.getSelected(),
								typeSelected);
				bench.changeRecipe(rec);
			}
		}

		@Override
		protected void updateListPositions() {
			armorRecipeList.setPosition(guiLeft + 153, guiTop + 12);
			armorTypeList.setPosition(guiLeft + 78, guiTop + 12);
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
			weaponRecipeList = new ClickableGuiList<RecipeItem>(70, ySize - 24,
					20);
			weaponTypeList = new ClickableGuiList<GuiListStringItem>(70,
					ySize - 24);
			this.clickableLists.add(weaponTypeList);
			this.clickableLists.add(weaponRecipeList);
			initializeTypeList();
			if (bench != null) {
				EquipmentRecipe rec = bench.getRecipe();
				int type = MHFCEquipementRecipeRegistry.getType(rec);
				int number = MHFCEquipementRecipeRegistry.getIDFor(rec, type);
				weaponTypeList.setSelected(type - 4);
				listUpdated(weaponTypeList);
				weaponRecipeList.setSelected(number);
			}
		}

		private void initializeTypeList() {
			String[] strings = {"Greatsword", "Longsword", "Hammer",
					"HuntingHorn", "Sword+Shield", "Dual Sword", "Lance",
					"Gunlance", "Bow", "SmBowgun", "LgBowgun"};
			for (int i = 0; i < strings.length; i++) {
				weaponTypeList.add(new GuiListStringItem(strings[i]));
			}
		}

		@Override
		protected void updateListPositions() {
			weaponRecipeList.setPosition(guiLeft + 153, guiTop + 12);
			weaponTypeList.setPosition(guiLeft + 78, guiTop + 12);
		}

		@Override
		protected void listUpdated(ClickableGuiList<?> list) {
			if (list == weaponTypeList) {
				weaponRecipeList.clear();
				int selected = weaponTypeList.getSelected();
				fillRecipeList(MHFCEquipementRecipeRegistry
						.getRecipesFor(selected + 4));
			} else if (list == weaponRecipeList) {
				int selected = weaponRecipeList.getSelected();
				EquipmentRecipe rec = MHFCEquipementRecipeRegistry
						.getRecipeFor(selected,
								weaponTypeList.getSelected() + 4);
				bench.changeRecipe(rec);
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
		}

	}

	protected class WeaponTreeTab implements IMHFCTab {

		private int mouseX = 0, mouseY = 0;
		private int baseX = 0, baseY = 0;

		@Override
		public void drawTab(int posX, int posY, int mousePosX, int mousePosY,
				float partialTick) {
			startCrafting.visible = false;
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			drawRect(guiLeft + 10, guiTop + 10, guiLeft + xSize - 10, guiTop
					+ ySize - 10, 0xFF101010);
			drawCenteredString(fontRendererObj, "Not yet implemented", guiLeft
					+ xSize / 2 + baseX, guiTop + ySize / 2 + baseY, 0xaaaaaa);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glPopMatrix();
		}

		@Override
		public void handleClick(int relativeX, int relativeY, int button) {
			mouseX = relativeX;
			mouseY = relativeY;
		}

		@Override
		public boolean containsSlot(Slot slot) {
			return false;
		}

		@Override
		public void updateTab(int pX, int pY) {
		}

		@Override
		public void onClose() {
		}

		@Override
		public void onOpen() {
		}

		@Override
		public void handleMovementMouseDown(int mouseX, int mouseY, int button,
				long timeDiff) {
			if (button == 0) {
				baseX += mouseX - this.mouseX;
				baseY += mouseY - this.mouseY;
			}
			this.mouseX = mouseX;
			this.mouseY = mouseY;
		}

		@Override
		public void handleMouseUp(int mouseX, int mouseY, int button) {
			if (button == 0) {
				baseX += mouseX - this.mouseX;
				baseY += mouseY - this.mouseY;
			}
			this.mouseX = mouseX;
			this.mouseY = mouseY;
		}

		@Override
		public void handleMovement(int mouseX, int mouseY) {
			this.mouseX = mouseX;
			this.mouseY = mouseY;
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
		this.ySize = 220;
		mc = Minecraft.getMinecraft();
		ScaledResolution s = new ScaledResolution(mc.gameSettings,
				mc.displayWidth, mc.displayHeight);
		width = s.getScaledWidth();
		height = s.getScaledHeight();
		this.guiLeft = (s.getScaledWidth() - this.xSize - tabWidth) / 2
				+ tabWidth;
		this.guiTop = (s.getScaledHeight() - this.ySize) / 2;
		tabNames = new String[]{"Armor", "Weapons", "Weapon tree"};
		this.tabList.add(new CraftArmorTab(tileEntity));
		this.tabList.add(new CraftWeaponTab(tileEntity));
		this.tabList.add(new WeaponTreeTab());
		int type = 0;
		if (tileEntity != null)
			type = MHFCEquipementRecipeRegistry.getType(tileEntity.getRecipe());
		setTab(type >= 4 ? 1 : 0);
		startCrafting = new GuiButton(0,
				guiLeft + 228 + (xSize - 228 - 60) / 2, guiTop + 166, 40, 20,
				"Craft") {
			@Override
			public void mouseReleased(int p_146118_1_, int p_146118_2_) {
				GuiHunterBench.this.tileEntity.beginCrafting();
			}
		};
	}

	private void drawItemModelAndHeat(TileHunterBench bench) {
		if (bench != null) {
			ItemStack itemToRender = bench
					.getStackInSlot(TileHunterBench.resultSlot);
			int itemType = MHFCEquipementRecipeRegistry.getType(bench
					.getRecipe());

			int rectX = guiLeft + 228, rectY = guiTop + 45;
			int rectW = 7 * 18 - 2, rectH = 96;
			int scale = new ScaledResolution(mc.gameSettings, mc.displayWidth,
					mc.displayHeight).getScaleFactor();

			drawItemModel(itemToRender, rectX, rectY, rectW, rectH, scale,
					itemType);
			drawBenchOverlay(bench, rectX, rectY, rectW);
		}
	}

	private void drawItemModel(ItemStack itemToRender, int rectX, int rectY,
			int rectW, int rectH, int guiScale, int itemType) {
		GL11.glPushMatrix();
		drawRect(rectX, rectY, rectX + rectW + 1, rectY + rectH, 0xFF000000);
		GL11.glScissor(rectX * guiScale, mc.displayHeight - rectY * guiScale,
				rectW * guiScale, rectH * guiScale);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glClearDepth(1.0f);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		if (itemToRender != null) {
			if (itemType < 4) {
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glTranslatef(rectX + rectW / 2, rectY + rectH / 2, 40F);
				// TODO switch the type and add displacement based on that
				GL11.glTranslatef(3f, -10f, 0F);
				GL11.glRotatef(-45f, 0.5f, 0.5f, 0.0f);
				float sc = rectH / 2;
				GL11.glScalef(sc, sc, -sc);
				ResourceLocation loc = RenderBiped.getArmorResource(
						mc.thePlayer, itemToRender, itemType, null);
				mc.getTextureManager().bindTexture(loc);
				ModelBiped model = ForgeHooksClient.getArmorModel(mc.thePlayer,
						itemToRender, itemType, null);
				if (model == null) {
				} else {
					// GL11.glCullFace(GL11.GL_FRONT_AND_BACK);
					model.render(mc.thePlayer, 0, 0, 0, 0, 0, 0.06125f);
					GL11.glFrontFace(GL11.GL_CW);
					model.render(mc.thePlayer, 0, 0, 0, 0, 0, 0.06125f);
					GL11.glFrontFace(GL11.GL_CCW);
				}
			} else {
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glTranslatef(rectX + rectW / 2, rectY + rectH / 2, 40F);
				GL11.glTranslatef(3f, -15f, 0F);
				GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(20f, -0.5f, 0.0f, 0.5f);
				float sc = rectH / 8;
				GL11.glScalef(sc, -sc, sc);

				IItemRenderer customRenderer = MinecraftForgeClient
						.getItemRenderer(itemToRender, ENTITY);
				if (customRenderer == null) {
				} else {
					customRenderer.renderItem(ItemRenderType.ENTITY,
							itemToRender, null,
							Minecraft.getMinecraft().thePlayer);
					GL11.glFrontFace(GL11.GL_CW);
					customRenderer.renderItem(ItemRenderType.ENTITY,
							itemToRender, null,
							Minecraft.getMinecraft().thePlayer);
					GL11.glFrontFace(GL11.GL_CCW);
				}
			}
		}
		GL11.glPopMatrix();
	}

	private void drawBenchOverlay(TileHunterBench bench, int rectX, int rectY,
			int rectW) {
		// Draw the background required heat indicator
		final int burnHeight = 96;
		final int completeWidth = 34;
		int heat;
		float burnTexVDiff;
		float burnTexV;
		int burnTexHeight;
		int burnTexY;

		// Draw the foreground current heat indicator
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(
				MHFCRegQuestVisual.HUNTER_BENCH_BURN_BACK);
		heat = Math.min(bench.getHeatStrength(), maxHeat);
		burnTexVDiff = (float) (heat) / maxHeat;
		burnTexV = 1.0f - burnTexVDiff;
		burnTexHeight = (int) (burnTexVDiff * burnHeight);
		burnTexY = rectY + burnHeight - burnTexHeight;
		MHFCGuiUtil.drawTexturedRectangle(rectX + rectW + 4, burnTexY, 10,
				burnTexHeight, 0.0f, burnTexV, 1.0f, burnTexVDiff);

		if (bench.getRecipe() != null) {
			GL11.glLineWidth(1f);
			heat = bench.getRecipe().getRequiredHeat();
			heat = Math.min(heat, maxHeat);
			burnTexVDiff = (float) (heat) / maxHeat;
			burnTexHeight = (int) (burnTexVDiff * burnHeight);
			burnTexY = rectY + burnHeight - burnTexHeight;
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glLineWidth(2.0f);
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor4f(0.4f, 0.4f, 0.4f, 1.0f);
			GL11.glVertex3f(rectX + rectW + 4, burnTexY + 0.7f, this.zLevel);
			GL11.glVertex3f(rectX + rectW + 14, burnTexY + 0.7f, this.zLevel);
			GL11.glEnd();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		// Draw heat target
		mc.getTextureManager().bindTexture(
				MHFCRegQuestVisual.HUNTER_BENCH_BURN_TARGET);
		heat = Math.min(TileHunterBench.getItemHeat(bench
				.getStackInSlot(TileHunterBench.fuelSlot)), maxHeat);
		if (heat > 0) {
			burnTexVDiff = (float) (heat) / maxHeat;
			burnTexHeight = (int) (burnTexVDiff * burnHeight);
			burnTexY = rectY + burnHeight - burnTexHeight;
			MHFCGuiUtil.drawTexturedBoxFromBorder(rectX + rectW + 4,
					burnTexY - 1, this.zLevel, 10, 3, 0);
		}

		// Draw front layer, the border
		mc.getTextureManager().bindTexture(
				MHFCRegQuestVisual.HUNTER_BENCH_BURN_FRONT);
		MHFCGuiUtil.drawTexturedBoxFromBorder(rectX + rectW + 4, rectY - 1,
				this.zLevel, 10, burnHeight + 1, 0);

		// draw the heat length indicator
		mc.getTextureManager().bindTexture(
				MHFCRegQuestVisual.HUNTER_BENCH_FUEL_DURATION);
		float remaining = bench.getHeatLength()
				/ (float) bench.getHeatLengthOriginal();
		if (Float.isInfinite(remaining)) {
			remaining = 0;
		}
		int remain = (int) Math.ceil(remaining * 14);
		remaining = remain / 17f;
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.addVertexWithUV(guiLeft + 353, guiTop + 159 - remain, this.zLevel,
				0f, 14f / 17 - remaining);
		t.addVertexWithUV(guiLeft + 353, guiTop + 159, this.zLevel, 0f,
				14f / 17);
		t.addVertexWithUV(guiLeft + 370, guiTop + 159, this.zLevel, 1f,
				14f / 17);
		t.addVertexWithUV(guiLeft + 370, guiTop + 159 - remain, this.zLevel,
				1f, 14f / 17 - remaining);
		t.draw();

		// draw the completition gauge
		if (bench.getRecipe() != null) {
			float completition = bench.getItemSmeltDuration()
					/ (float) bench.getRecipe().getDuration();
			int complete = (int) (completition * completeWidth);
			completition = complete / (float) completeWidth;
			mc.getTextureManager().bindTexture(
					MHFCRegQuestVisual.HUNTER_BENCH_COMPLETE);
			MHFCGuiUtil.drawTexturedBoxFromBorder(guiLeft + 298, guiTop + 145,
					this.zLevel, (int) (completition * completeWidth), 17, 0,
					0, completition, 1f);
		}
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
				MHFCRegQuestVisual.QUEST_BOARD_BACKGROUND);
		int posX = guiLeft;
		int posY = guiTop;
		MHFCGuiUtil.drawTexturedBoxFromBorder(posX, posY, this.zLevel,
				this.xSize, this.ySize, 0, 0, 1f, 1f);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		startCrafting.xPosition = guiLeft + 228 + (xSize - 228 - 60) / 2;
		startCrafting.yPosition = guiTop + 166;
	}

}
