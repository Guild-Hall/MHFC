package mhfc.net.common.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe.RecipeType;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.bench.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileHunterBench extends TileEntity
	implements
		IInventory,
		TileMHFCUpdateStream {

	public static final int outputSlot = 2;
	public static final int fuelSlot = 0;
	public static final int resultSlot = 1;

	private ItemStack fuelStack;
	private ItemStack resultStack;
	private ItemStack[] recipeStacks;
	private ItemStack[] inputStacks;
	private ItemStack outputStack;

	/**
	 * How hot the furnace currently is
	 */
	volatile private int heatStrength;
	/**
	 * How hot the fire produced by the currently burning item is
	 */
	volatile private int heatFromItem;
	/**
	 * How long the furnace stays hot
	 */
	volatile private int heatLength;
	/**
	 * How long the furnace stayed hot initially with the burning item
	 */
	private int heatLengthInit;
	/**
	 * How long the current item has been smelting
	 */
	volatile private int itemSmeltDuration;
	/**
	 * The selected recipe
	 */
	private EquipmentRecipe recipe;
	/**
	 * If the recipe is currently worked on
	 */
	volatile private boolean workingMHFCBench;

	public TileHunterBench() {
		recipeStacks = new ItemStack[7];
		inputStacks = new ItemStack[recipeStacks.length];
		workingMHFCBench = false;
		heatLengthInit = 1;
	}

	@Override
	public int getSizeInventory() {
		return recipeStacks.length + inputStacks.length + 3;
	}

	@Override
	public void updateEntity() {
		if (heatLength > 0) {
			--heatLength;
			heatStrength = getNewHeat(heatStrength, heatFromItem);
			if (TileHunterBench.this.workingMHFCBench && recipe != null
				&& heatStrength >= recipe.getRequiredHeat())
				++itemSmeltDuration;
		} else {
			if (fuelStack != null && TileHunterBench.this.workingMHFCBench) {
				heatLength = TileEntityFurnace.getItemBurnTime(fuelStack);
				heatLengthInit = heatLength;
				heatFromItem = getItemHeat(fuelStack);
				decrStackSize(fuelSlot, 1);
				if (!worldObj.isRemote)
					sendUpdateCraft();
			} else {
				heatStrength = getNewHeat(heatStrength, 0);
			}
		}
		if (recipe != null && itemSmeltDuration >= recipe.getDuration()) {
			finishRecipe();
		}
	}

	private void finishRecipe() {
		if (worldObj.isRemote) {
			return;
		}
		inputStacks = new ItemStack[inputStacks.length];
		outputStack = resultStack.copy();
		workingMHFCBench = false;
		itemSmeltDuration = 0;
		sendUpdateCraft();
	}

	/**
	 * Forces the client to end the crafting and put the stack into the output
	 * slot. Unused
	 * 
	 * @param stack
	 */
	@SideOnly(Side.CLIENT)
	public void forceEndCrafting(ItemStack stack) {
		outputStack = stack;
		inputStacks = new ItemStack[inputStacks.length];
		TileHunterBench.this.workingMHFCBench = false;
		itemSmeltDuration = 0;
	}

	public boolean isWorking() {
		return TileHunterBench.this.workingMHFCBench;
	}

	public void changeRecipe(EquipmentRecipe recipe) {
		if (worldObj.isRemote) {
			sendSetRecipe(recipe);
		}
		if (recipe != this.recipe) {
			cancelRecipe();
			setRecipe(recipe);
		}
	}

	protected void setRecipe(EquipmentRecipe recipe) {
		if (recipe == null) {
			recipeStacks = new ItemStack[7];
			this.recipe = null;
			resultStack = null;
		} else {
			resultStack = recipe.getRecipeOutput();
			this.recipe = recipe;
			setIngredients(recipe);
		}
	}

	public void setIngredients(EquipmentRecipe recipe) {
		this.recipeStacks = recipe.getRequirements(7);
	}

	public static int getItemHeat(ItemStack itemStack) {
		if (itemStack == null)
			return 0;
		if (itemStack.getItem() == Item.getItemById(327))
			return 1000;
		return 200;
	}

	private int getNewHeat(int heatOld, int heatAim) {
		int diff = heatAim - heatOld;
		// Some math to confuse the hell out of everyone reading this
		double change = Math.ceil(Math.log(Math.abs(diff) + 1.0));
		return heatOld + (int) (Math.ceil(change / 3.0) * Math.signum(diff));
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i < 0 || i >= getSizeInventory())
			return null;
		else if (i >= recipeStacks.length + 3)
			return inputStacks[i - 3 - recipeStacks.length];
		else if (i >= 3)
			return recipeStacks[i - 3];
		else if (i == outputSlot)
			return outputStack;
		else if (i == resultSlot)
			return resultStack;
		else
			return fuelStack;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		markDirty();
		if (j <= 0)
			return null;
		if (i > recipeStacks.length + 2) {
			ItemStack stack = inputStacks[i - recipeStacks.length - 3];
			if (stack == null)
				return null;
			cancelRecipe();
			if (j > stack.stackSize)
				j = stack.stackSize;
			if (j == stack.stackSize) {
				inputStacks[i - recipeStacks.length - 3] = null;
				return stack;
			}
			return stack.splitStack(j);
		}
		if (i > 2)
			return null; // You cant get anything from the recipe slots
		else if (i == outputSlot) {
			if (outputStack == null)
				return null;
			if (j > outputStack.stackSize)
				j = outputStack.stackSize;
			if (j == outputStack.stackSize) {
				ItemStack fuel = outputStack;
				outputStack = null;
				return fuel;
			}
			return outputStack.splitStack(j);
		} else if (i == resultSlot) {
			return null;
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

	/**
	 * Resets all working progress but leaves the recipe set
	 */
	public void cancelRecipe() {
		if (worldObj.isRemote)
			sendCancelRecipe();
		TileHunterBench.this.workingMHFCBench = false;
		itemSmeltDuration = 0;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (i < 0 || i >= getSizeInventory())
			return;
		if (i >= recipeStacks.length + 3)
			inputStacks[i - recipeStacks.length - 3] = itemstack;
		else if (i >= 3)
			recipeStacks[i - 3] = itemstack;
		else if (i == outputSlot) {
			outputStack = itemstack;
		} else if (i == resultSlot) {
			resultStack = itemstack;
		} else if (i == fuelSlot)
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
		return (i == fuelSlot && TileEntityFurnace.isItemFuel(itemstack))
			|| (i > recipeStacks.length + 2 && i < recipeStacks.length * 2 + 3);
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
	}

	@Override
	public void closeInventory() {
	}

	public boolean beginCrafting() {
		if (worldObj.isRemote) {
			sendBeginCraft();
		}
		if (recipe != null) {
			if (canBeginCrafting()) {
				TileHunterBench.this.workingMHFCBench = true;
			}
		}
		return TileHunterBench.this.workingMHFCBench;
	}

	@SideOnly(Side.CLIENT)
	private void sendBeginCraft() {
		PacketPipeline.networkPipe.sendToServer(new MessageBeginCrafting(this));
	}

	@SideOnly(Side.CLIENT)
	private void sendSetRecipe(EquipmentRecipe recipeToSend) {
		PacketPipeline.networkPipe.sendToServer(new MessageSetRecipe(this,
			recipeToSend));
	}

	@SideOnly(Side.CLIENT)
	private void sendCancelRecipe() {
		PacketPipeline.networkPipe.sendToServer(new MessageCancelRecipe(this));
	}

	@Override
	public void refreshState() {
		if (worldObj.isRemote) {
			PacketPipeline.networkPipe.sendToServer(
				new MessageBenchRefreshRequest(this));
		}
	}

	private void sendUpdateCraft() {
		PacketPipeline.networkPipe.sendToAll(new MessageCraftingUpdate(this));
	}

	public boolean canBeginCrafting() {
		return matchesInputOutput() && (outputStack == null);
	}

	protected boolean matchesInputOutput() {
		for (int i = 0; i < inputStacks.length; i++) {
			if (!ItemStack.areItemStacksEqual(inputStacks[i], recipeStacks[i]))
				return false;
		}
		return true;
	}

	public int getHeatStrength() {
		return heatStrength;
	}

	public int getBurningItemHeat() {
		return heatFromItem;
	}

	public int getHeatLength() {
		return heatLength;
	}

	public int getHeatLengthOriginal() {
		return heatLengthInit;
	}

	public int getItemSmeltDuration() {
		return itemSmeltDuration;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {
		super.readFromNBT(nbtTag);
		readCustomUpdate(nbtTag);

		NBTTagList items = nbtTag.getTagList("Items", 10);
		for (int a = 0; a < items.tagCount(); a++) {
			NBTTagCompound stack = items.getCompoundTagAt(a);
			byte id = stack.getByte("Slot");
			if (id >= 0 && id < getSizeInventory())
				setInventorySlotContents(id, ItemStack.loadItemStackFromNBT(
					stack));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTag) {
		super.writeToNBT(nbtTag);
		storeCustomUpdate(nbtTag);

		NBTTagList itemsStored = new NBTTagList();
		for (int a = 0; a < getSizeInventory(); a++) {
			ItemStack s = getStackInSlot(a);
			if (s != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) a);
				s.writeToNBT(tag);
				itemsStored.appendTag(tag);
			}
		}
		nbtTag.setTag("Items", itemsStored);
	}

	public EquipmentRecipe getRecipe() {
		return recipe;
	}

	private static final String heatStrID = "heatStrength";
	private static final String heatFromItemID = "heatFromItem";
	private static final String heatLengthID = "heatLength";
	private static final String heatLengthInitID = "heatLengthInit";
	private static final String itemSmeltDurationID = "itemSmeltDuration";
	private static final String workingID = "workingID";
	private static final String recipeTypeID = "recipeType";
	private static final String recipeIDID = "recipeID";

	@Override
	public void storeCustomUpdate(NBTTagCompound nbtTag) {
		nbtTag.setInteger(heatStrID, heatStrength);
		nbtTag.setInteger(heatFromItemID, heatFromItem);
		nbtTag.setInteger(heatLengthID, heatLength);
		nbtTag.setInteger(heatLengthInitID, heatLengthInit);
		nbtTag.setInteger(itemSmeltDurationID, itemSmeltDuration);
		nbtTag.setBoolean(workingID, TileHunterBench.this.workingMHFCBench);
		RecipeType type = recipe != null
			? recipe.getRecipeType()
			: RecipeType.MHFC;
		int recipeID = MHFCEquipementRecipeRegistry.getIDFor(recipe);
		nbtTag.setInteger(recipeTypeID, type.ordinal());
		nbtTag.setInteger(recipeIDID, recipeID);
	}

	@Override
	public void readCustomUpdate(NBTTagCompound nbtTag) {
		heatStrength = nbtTag.getInteger(heatStrID);
		heatFromItem = nbtTag.getInteger(heatFromItemID);
		heatLength = nbtTag.getInteger(heatLengthID);
		heatLengthInit = nbtTag.getInteger(heatLengthInitID);
		itemSmeltDuration = nbtTag.getInteger(itemSmeltDurationID);
		TileHunterBench.this.workingMHFCBench = nbtTag.getBoolean(workingID);
		int irecType = nbtTag.getInteger(recipeTypeID);
		int recId = nbtTag.getInteger(recipeIDID);
		RecipeType recType = RecipeType.values()[irecType];
		setRecipe(MHFCEquipementRecipeRegistry.getRecipeFor(recId, recType));
	}

}
