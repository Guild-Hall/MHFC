package mhfc.net.common.tile;

import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileHunterBench extends TileEntity implements IInventory {

	public static final int outputSlot = 1;
	public static final int fuelSlot = 0;

	private ItemStack fuelStack;
	private ItemStack outputStack;
	private ItemStack[] stacks;

	/**
	 * How hot the furnace currently is
	 */
	private int heatStrength;
	/**
	 * How hot the fire produced by the currently burning item is
	 */
	private int heatFromItem;
	/**
	 * How long the furnace stays hot
	 */
	private int heatLength;
	/**
	 * How long the current item has been smelting
	 */
	private int itemSmeltDuration;
	/**
	 * The selected recipe
	 */
	private EquipmentRecipe recipe;
	/**
	 * If the recipe is currently worked on
	 */
	private boolean working;

	public TileHunterBench() {
		stacks = new ItemStack[8];
	}

	@Override
	public int getSizeInventory() {
		return stacks.length + 2;
	}

	@Override
	public void updateEntity() {
		if (heatLength > 0) {
			--heatLength;
			heatStrength = getNewHeat(heatStrength, heatFromItem);
			if (working && heatStrength >= recipe.getRequiredHeat())
				++itemSmeltDuration;
		} else {
			if (fuelStack != null && working) {
				heatLength = TileEntityFurnace.getItemBurnTime(fuelStack);
				heatStrength = getItemHeat(fuelStack);
			} else {
				heatStrength = getNewHeat(heatStrength, 0);
			}
		}
		if (recipe != null && itemSmeltDuration >= recipe.getDuration()) {
			outputStack = recipe.getRecipeOutput();
		}
	}

	public boolean isWorking() {
		return working;
	}

	public void setRecipe(EquipmentRecipe recipe) {
		if (recipe == null) {
			itemSmeltDuration = 0;
			this.recipe = null;
			this.working = false;
			return;
		}
		if (outputStack != null)
			return;
		this.recipe = recipe;
		setIngredients(recipe);
		working = true;
	}

	public void setIngredients(EquipmentRecipe recipe) {
		this.stacks = recipe.getRequirements(8);
	}

	private static int getItemHeat(ItemStack itemStack) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getNewHeat(int heatOld, int heatAim) {
		int diff = heatAim - heatOld;
		// Some math to confuse the hell out of everyone reading this
		int change = (int) (Math.ceil(Math.log(Math.abs(diff) + 1.0)) * Math
				.signum(diff));
		return heatOld + change;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i < 0 || i > stacks.length + 2)
			return null;
		if (i > 1)
			return stacks[i - 2];
		if (i == 1)
			return outputStack;
		else
			return fuelStack;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (i > 1)
			return null; // You cant get anything from the recipe slots
		// TODO If you take anything out, cancel the recipe
		if (i == outputSlot) {
			ItemStack output = outputStack;
			outputStack = null;
			return output;
		} else {
			if (fuelStack == null)
				return null;
			if (j > fuelStack.stackSize)
				j = fuelStack.stackSize;
			if (j == fuelStack.stackSize) {
				ItemStack fuel = fuelStack;
				fuelStack = null;
				return fuel;
			}
			return fuelStack.splitStack(j);
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (i < 0 || i > stacks.length + 2)
			return;
		if (i > 1)
			stacks[i - 2] = itemstack;
		if (i == 1)
			outputStack = itemstack;
		else
			fuelStack = itemstack;
	}

	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return getDistanceFrom(entityplayer.posX, entityplayer.posY,
				entityplayer.posZ) <= 64.0f;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return i == fuelSlot && TileEntityFurnace.isItemFuel(itemstack);
	}

	@Override
	public String getInventoryName() {
		return "container.hunterbench";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
	}

}
