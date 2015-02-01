package mhfc.net.common.tile;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCRegEquipmentRecipe;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.MessageBeginCrafting;
import mhfc.net.common.network.packet.MessageCancelRecipe;
import mhfc.net.common.network.packet.MessageCraftingUpdate;
import mhfc.net.common.network.packet.MessageSetRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	}

	@Override
	public int getSizeInventory() {
		return recipeStacks.length + inputStacks.length + 3;
	}

	@Override
	public synchronized void updateEntity() {
		if (heatLength > 0) {
			--heatLength;
			heatStrength = getNewHeat(heatStrength, heatFromItem);
			if (TileHunterBench.this.workingMHFCBench && recipe != null
					&& heatStrength >= recipe.getRequiredHeat())
				++itemSmeltDuration;
		} else {
			if (fuelStack != null && TileHunterBench.this.workingMHFCBench) {
				heatLength = TileEntityFurnace.getItemBurnTime(fuelStack);
				heatFromItem = getItemHeat(fuelStack);
				decrStackSize(fuelSlot, 1);
			} else {
				heatStrength = getNewHeat(heatStrength, 0);
			}
		}
		if (recipe != null && itemSmeltDuration >= recipe.getDuration()) {
			finishRecipe();
		}
	}

	private synchronized void finishRecipe() {
		if (worldObj.isRemote) {
			invalidate();
			return;
		}
		inputStacks = new ItemStack[inputStacks.length];
		outputStack = resultStack;
		workingMHFCBench = false;
		itemSmeltDuration = 0;
		sendEndCraft(resultStack);
	}

	/**
	 * Forces the client to end the crafting and put the stack into the output
	 * slot
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
		cancelRecipe();
		setRecipe(recipe);
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

	private static int getItemHeat(ItemStack itemStack) {
		// TODO Auto-generated method stub
		return 200;
	}

	private int getNewHeat(int heatOld, int heatAim) {
		int diff = heatAim - heatOld;
		// Some math to confuse the hell out of everyone reading this
		int change = (int) (Math.ceil(Math.log(Math.abs(diff) + 1.0)));
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
	public synchronized void cancelRecipe() {
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

	public synchronized boolean beginCrafting() {
		if (worldObj.isRemote) {
			MHFCMain.logger.info("Craft button pressed");
			sendBeginCraft();
			invalidate();
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

	private void sendEndCraft(ItemStack endStack) {
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

	public int getHeatFromItem() {
		return heatFromItem;
	}

	public int getHeatLength() {
		return heatLength;
	}

	public int getItemSmeltDuration() {
		return itemSmeltDuration;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {
		super.readFromNBT(nbtTag);
		heatStrength = nbtTag.getInteger("heatStrength");
		heatFromItem = nbtTag.getInteger("heatFromItem");
		heatLength = nbtTag.getInteger("heatLength");
		itemSmeltDuration = nbtTag.getInteger("itemSmeltDuration");
		TileHunterBench.this.workingMHFCBench = nbtTag.getBoolean("working");
		int recType = nbtTag.getInteger("recipeType");
		int recId = nbtTag.getInteger("recipeID");
		setRecipe(MHFCRegEquipmentRecipe.getRecipeFor(recId, recType));
		NBTTagList items = nbtTag.getTagList("Items", 10);
		for (int a = 0; a < items.tagCount(); a++) {
			NBTTagCompound stack = items.getCompoundTagAt(a);
			byte id = stack.getByte("Slot");
			if (id >= 0 && id < getSizeInventory())
				setInventorySlotContents(id,
						ItemStack.loadItemStackFromNBT(stack));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTag) {
		super.writeToNBT(nbtTag);
		nbtTag.setInteger("heatStrength", heatStrength);
		nbtTag.setInteger("heatFromItem", heatFromItem);
		nbtTag.setInteger("heatLength", heatLength);
		nbtTag.setInteger("itemSmeltDuration", itemSmeltDuration);
		nbtTag.setBoolean("working", TileHunterBench.this.workingMHFCBench);
		int typeID = MHFCRegEquipmentRecipe.getType(recipe);
		int recipeID = MHFCRegEquipmentRecipe.getIDFor(recipe, typeID);
		nbtTag.setInteger("recipeType", typeID);
		nbtTag.setInteger("recipeID", recipeID);
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

	@Override
	public void storeCustomUpdate(NBTTagCompound nbtTag) {
		nbtTag.setInteger("heatStrength", heatStrength);
		nbtTag.setInteger("heatFromItem", heatFromItem);
		nbtTag.setInteger("heatLength", heatLength);
		nbtTag.setInteger("itemSmeltDuration", itemSmeltDuration);
		nbtTag.setBoolean("working", TileHunterBench.this.workingMHFCBench);
		int typeID = MHFCRegEquipmentRecipe.getType(recipe);
		int recipeID = MHFCRegEquipmentRecipe.getIDFor(recipe, typeID);
		nbtTag.setInteger("recipeType", typeID);
		nbtTag.setInteger("recipeID", recipeID);
		MHFCMain.logger.info("Stored " + nbtTag.toString());
		MHFCMain.logger.info("w" + workingMHFCBench);
	}

	@Override
	public void readCustomUpdate(NBTTagCompound nbtTag) {
		heatStrength = nbtTag.getInteger("heatStrength");
		heatFromItem = nbtTag.getInteger("heatFromItem");
		heatLength = nbtTag.getInteger("heatLength");
		itemSmeltDuration = nbtTag.getInteger("itemSmeltDuration");
		TileHunterBench.this.workingMHFCBench = nbtTag.getBoolean("working");
		int recType = nbtTag.getInteger("recipeType");
		int recId = nbtTag.getInteger("recipeID");
		setRecipe(MHFCRegEquipmentRecipe.getRecipeFor(recId, recType));
		MHFCMain.logger.info("Received " + nbtTag.toString());
		MHFCMain.logger.info("hs" + heatStrength);
		MHFCMain.logger.info("hi" + heatFromItem);
		MHFCMain.logger.info("hl" + heatLength);
		MHFCMain.logger.info("d" + itemSmeltDuration);
		MHFCMain.logger.info("w" + workingMHFCBench);
	}

}
