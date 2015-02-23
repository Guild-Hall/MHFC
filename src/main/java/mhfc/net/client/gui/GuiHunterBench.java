package mhfc.net.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mhfc.net.client.container.ContainerHunterBench;
import mhfc.net.client.gui.ClickableGuiList.GuiListItem;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.item.ItemType;
import mhfc.net.common.item.ItemType.GeneralType;
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
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.ENTITY;

public class GuiHunterBench extends MHFCTabbedGui {

	static final int maxHeat = 500;
	static final int modelRectRelX = 228;
	static final int modelRectRelY = 45;
	static final int modelRectW = 7 * 18 - 2;
	static final int modelRectH = 96;

	public class TypeItem implements GuiListItem {
		ItemType type;

		public TypeItem(ItemType type) {
			this.type = type;
		}

		public ItemType getType() {
			return type;
		}

		@Override
		public String getRepresentationString() {
			String str = type.getNameString();
			return StatCollector.translateToLocal(str);
		}

	}

	public class RecipeItem implements GuiListItem {
		String representation;
		EquipmentRecipe recipe;

		public RecipeItem(EquipmentRecipe recipe) {
			this.recipe = recipe;
			representation = recipe.getRecipeOutput().getDisplayName();
		}

		public EquipmentRecipe getRecipe() {
			return recipe;
		}

		@Override
		public String getRepresentationString() {
			return representation;
		}

	}

	protected abstract class BenchEntityTab implements IMHFCTab {
		protected TileHunterBench bench;
		protected float modelRotX, modelRotY;
		protected int mouseLastX, mouseLastY;
		protected int mouseClickX, mouseClickY, mouseClickButton;
		protected float maxRotation = 50;

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
			resetModelRot();
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
				list.draw(posX, posY);
			}
			fontRendererObj.drawSplitString("Inventory", posX + 6, posY + 12,
					500, 0x404040);

			GuiHunterBench.this.startCrafting.visible = !bench.isWorking();
			GuiHunterBench.this.startCrafting.enabled = bench
					.canBeginCrafting();
			drawItemModelAndHeat(bench, modelRotX, modelRotY);
		};

		@Override
		public void handleClick(int relativeX, int relativeY, int button) {
			mouseClickX = relativeX;
			mouseClickY = relativeY;
			mouseLastX = relativeX;
			mouseLastY = relativeY;
			mouseClickButton = button;
			if (isInModelWindow(mouseClickX, mouseClickY)
					&& mouseClickButton == 1) {
				resetModelRot();
			}
			for (ClickableGuiList<?> list : clickableLists) {
				if (list.handleClick(relativeX - list.posX, relativeY
						- list.posY, button))
					listUpdated(list);
			}
		}

		private void resetModelRot() {
			modelRotX = -50f;
			modelRotY = 20f;
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
			if (isInModelWindow(mouseClickX, mouseClickY)
					&& mouseClickButton == 0) {
				modelRotX += (mouseX - mouseLastX);
				modelRotY += (mouseY - mouseLastY);
				if (Math.abs(modelRotY) > maxRotation) {
					modelRotY = maxRotation * Math.signum(modelRotY);
				}
			}
			mouseLastX = mouseX;
			mouseLastY = mouseY;
		}

	}

	protected class CraftArmorTab extends BenchEntityTab {
		ClickableGuiList<RecipeItem> armorRecipeList;
		ClickableGuiList<TypeItem> armorTypeList;

		public CraftArmorTab(TileHunterBench bench) {
			super(bench);
			armorRecipeList = new ClickableGuiList<RecipeItem>(70, ySize - 24,
					20);
			armorTypeList = new ClickableGuiList<TypeItem>(70, ySize - 24);
			armorTypeList.setAlignmentMid(true);
			this.clickableLists.add(armorTypeList);
			this.clickableLists.add(armorRecipeList);
			initializeTypeList();
			if (bench != null) {
				EquipmentRecipe rec = bench.getRecipe();
				ItemType type = MHFCEquipementRecipeRegistry.getType(rec);
				int number = MHFCEquipementRecipeRegistry.getIDFor(rec, type);
				armorTypeList.setSelected(type.getArmorOrdinal());
				listUpdated(armorTypeList);
				if (type.getGeneralType() == GeneralType.ARMOR)
					armorRecipeList.setSelected(number);
			}
		}

		private void initializeTypeList() {
			ItemType[] types = ItemType.armorTypes;
			for (int i = 0; i < types.length; i++) {
				armorTypeList.add(new TypeItem(types[i]));
			}
		}

		@Override
		protected void listUpdated(ClickableGuiList<?> list) {
			if (list == armorTypeList) {
				armorRecipeList.clear();
				ItemType typeOfSelection = getSelectedType();
				if (typeOfSelection.getGeneralType() == GeneralType.ARMOR)
					fillRecipeList(MHFCEquipementRecipeRegistry
							.getRecipesForType(typeOfSelection));
			} else if (list == armorRecipeList) {
				RecipeItem recI = armorRecipeList.getSelectedItem();
				bench.changeRecipe(recI == null ? null : recI.getRecipe());
			}
		}

		private ItemType getSelectedType() {
			TypeItem selectedItem = armorTypeList.getSelectedItem();
			return selectedItem == null ? ItemType.NO_OTHER : selectedItem
					.getType();
		}

		@Override
		protected void updateListPositions() {
			armorRecipeList.setPosition(153, 12);
			armorTypeList.setPosition(78, 12);
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
		ClickableGuiList<TypeItem> weaponTypeList;

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
			weaponTypeList = new ClickableGuiList<TypeItem>(70, ySize - 24);
			this.clickableLists.clear();
			this.clickableLists.add(weaponTypeList);
			this.clickableLists.add(weaponRecipeList);
			initializeTypeList();
			if (bench != null) {
				EquipmentRecipe rec = bench.getRecipe();
				ItemType type = MHFCEquipementRecipeRegistry.getType(rec);
				int number = MHFCEquipementRecipeRegistry.getIDFor(rec, type);
				weaponTypeList.setSelected(type.getWeaponOrdinal());
				listUpdated(weaponTypeList);
				if (type.getGeneralType() == GeneralType.WEAPON)
					weaponRecipeList.setSelected(number);
			}
		}

		private void initializeTypeList() {
			ItemType[] types = ItemType.weaponTypes;
			for (int i = 0; i < types.length; i++) {
				weaponTypeList.add(new TypeItem(types[i]));
			}
		}

		@Override
		protected void updateListPositions() {
			weaponRecipeList.setPosition(153, 12);
			weaponTypeList.setPosition(78, 12);
		}

		@Override
		protected void listUpdated(ClickableGuiList<?> list) {
			ItemType typeOfSelected = getSelectedType();
			if (list == weaponTypeList) {
				weaponRecipeList.clear();
				if (typeOfSelected.getGeneralType() == GeneralType.WEAPON)
					fillRecipeList(MHFCEquipementRecipeRegistry
							.getRecipesForType(typeOfSelected));
			} else if (list == weaponRecipeList) {
				RecipeItem recI = weaponRecipeList.getSelectedItem();
				bench.changeRecipe(recI == null ? null : recI.getRecipe());
			}
		}

		private ItemType getSelectedType() {
			TypeItem selectedItem = weaponTypeList.getSelectedItem();
			return selectedItem == null ? ItemType.NO_OTHER : selectedItem
					.getType();
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
		int type = -2;
		if (tileEntity != null)
			type = MHFCEquipementRecipeRegistry.getType(tileEntity.getRecipe())
					.getArmorOrdinal();
		setTab(type > -2 ? type > -1 ? 0 : 1 : 0); // TODO change the last index
													// to 2 once tree is
													// implemented
		startCrafting = new GuiButton(0,
				guiLeft + 228 + (xSize - 228 - 60) / 2, guiTop + 166, 40, 20,
				"Craft") {
			@Override
			public void mouseReleased(int p_146118_1_, int p_146118_2_) {
				GuiHunterBench.this.tileEntity.beginCrafting();
			}
		};
	}

	private void drawItemModelAndHeat(TileHunterBench bench, float modelRotX,
			float modelRotY) {
		if (bench != null) {
			ItemStack itemToRender = bench
					.getStackInSlot(TileHunterBench.resultSlot);
			ItemType itemType = MHFCEquipementRecipeRegistry.getType(bench
					.getRecipe());

			int rectX = guiLeft + modelRectRelX, rectY = guiTop + modelRectRelY;
			int scale = new ScaledResolution(mc.gameSettings, mc.displayWidth,
					mc.displayHeight).getScaleFactor();

			drawItemModel(itemToRender, rectX, rectY, modelRectW, modelRectH,
					scale, itemType, modelRotX, modelRotY);
			drawBenchOverlay(bench, rectX, rectY, modelRectW);
		}
	}

	private boolean isInModelWindow(int mouseClickRelX, int mouseClickRelY) {
		return (mouseClickRelX >= modelRectRelX //
				&& mouseClickRelX <= modelRectRelX + modelRectW)
				&& (mouseClickRelY >= modelRectRelY //
				&& mouseClickRelY <= modelRectRelY + modelRectH);
	}

	private void drawItemModel(ItemStack itemToRender, int rectX, int rectY,
			int rectW, int rectH, int guiScale, ItemType itemType,
			float modelRotX, float modelRotY) {
		modelRotX /= 2;
		modelRotY /= 4;
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
			if (itemType.getGeneralType() == GeneralType.ARMOR) {
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glTranslatef(rectX + rectW / 2, rectY + rectH / 2, 40F);
				int armorType = itemType.getArmorOrdinal();
				switch (armorType) {
					case 0 :
						GL11.glTranslatef(3f, 15f, 0F);
						break;
					case 1 :
						GL11.glTranslatef(3f, -15f, 0F);
						break;
					case 2 :
						GL11.glTranslatef(3f, -40f, 0F);
						break;
					case 3 :
						GL11.glTranslatef(3f, -55f, 0F);
						break;
				}
				GL11.glRotatef(modelRotX, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(-modelRotY, 1.0f, 0.0f, 0.0f);
				float sc = rectH / 2;
				GL11.glScalef(sc, sc, -sc);
				ResourceLocation loc = RenderBiped.getArmorResource(
						mc.thePlayer, itemToRender, armorType, null);
				mc.getTextureManager().bindTexture(loc);
				ModelBiped model = ForgeHooksClient.getArmorModel(mc.thePlayer,
						itemToRender, armorType, null);
				if (model == null) {
				} else {
					model.render(mc.thePlayer, 0, 0, 0, 0, 0, 0.06125f);
					GL11.glFrontFace(GL11.GL_CW);
					model.render(mc.thePlayer, 0, 0, 0, 0, 0, 0.06125f);
					GL11.glFrontFace(GL11.GL_CCW);
				}
			} else if (itemType.getGeneralType() == GeneralType.WEAPON) {
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glTranslatef(rectX + rectW / 2, rectY + rectH / 2, 40F);
				GL11.glTranslatef(3f, -15f, 0F);
				GL11.glRotatef(90F, 1.0f, 0.0f, 0.0f);
				modelRotY = Math.min(Math.abs(modelRotY), 30f)
						* Math.signum(modelRotY);
				GL11.glRotatef(modelRotX, 0.0f, 0.0f, -1.0f);
				GL11.glRotatef(modelRotY, 0.0f, -1.0f, 0.0f);
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
