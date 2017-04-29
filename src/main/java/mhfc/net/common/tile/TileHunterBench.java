package mhfc.net.common.tile;

import java.util.Objects;

import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe.RecipeType;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.bench.MessageBeginCrafting;
import mhfc.net.common.network.message.bench.MessageBenchRefreshRequest;
import mhfc.net.common.network.message.bench.MessageCancelRecipe;
import mhfc.net.common.network.message.bench.MessageCraftingUpdate;
import mhfc.net.common.network.message.bench.MessageSetRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

public class TileHunterBench extends TileEntity implements ITickable, IInventory, TileMHFCUpdateStream {

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
		fuelStack = ItemStack.EMPTY;
		outputStack = ItemStack.EMPTY;
		resultStack = ItemStack.EMPTY;
		recipeStacks = new ItemStack[7];
		resetRecipeStacks();
		inputStacks = new ItemStack[recipeStacks.length];
		resetInputStacks();
		workingMHFCBench = false;
		heatLengthInit = 1;

	}

	private void resetRecipeStacks() {
		Arrays.fill(recipeStacks, ItemStack.EMPTY);
	}

	private void resetInputStacks() {
		Arrays.fill(inputStacks, ItemStack.EMPTY);
	}

	@Override
	public int getSizeInventory() {
		return recipeStacks.length + inputStacks.length + 3;
	}

	@Override
	public void update() {
		if (heatLength > 0) {
			--heatLength;
			heatStrength = getNewHeat(heatStrength, heatFromItem);
			if (TileHunterBench.this.workingMHFCBench && recipe != null && heatStrength >= recipe.getRequiredHeat()) {
				++itemSmeltDuration;
			}
		} else {
			if (fuelStack != ItemStack.EMPTY && TileHunterBench.this.workingMHFCBench) {
				heatLength = TileEntityFurnace.getItemBurnTime(fuelStack);
				heatLengthInit = heatLength;
				heatFromItem = getItemHeat(fuelStack);
				decrStackSize(fuelSlot, 1);
				if (!world.isRemote) {
					sendUpdateCraft();
				}
			} else {
				heatStrength = getNewHeat(heatStrength, 0);
			}
		}
		if (recipe != null && itemSmeltDuration >= recipe.getDuration()) {
			finishRecipe();
		}
	}

	private void finishRecipe() {
		if (world.isRemote) {
			return;
		}
		inputStacks = new ItemStack[inputStacks.length];
		outputStack = resultStack.copy();
		workingMHFCBench = false;
		itemSmeltDuration = 0;
		sendUpdateCraft();
	}

	/**
	 * Forces the client to end the crafting and put the stack into the output slot. Unused
	 *
	 * @param stack
	 */
	@SideOnly(Side.CLIENT)
	public void forceEndCrafting(ItemStack stack) {
		outputStack = stack;
		resetInputStacks();
		TileHunterBench.this.workingMHFCBench = false;
		itemSmeltDuration = 0;
	}

	public boolean isWorking() {
		return TileHunterBench.this.workingMHFCBench;
	}

	public void changeRecipe(EquipmentRecipe recipe) {
		if (world.isRemote) {
			sendSetRecipe(recipe);
		}
		if (recipe != this.recipe) {
			cancelRecipe();
			setRecipe(recipe);
		}
	}

	protected void setRecipe(EquipmentRecipe recipe) {
		if (recipe == null) {
			resetRecipeStacks();
			this.recipe = null;
			resultStack = ItemStack.EMPTY;
		} else {
			this.recipe = recipe;
			resultStack = recipe.getRecipeOutput();
			setIngredients(recipe);
		}
	}

	public void setIngredients(EquipmentRecipe recipe) {
		resetRecipeStacks();
		recipe.fillRequirements(this.recipeStacks);
	}

	public static int getItemHeat(ItemStack itemStack) {
		if (itemStack == ItemStack.EMPTY) {
			return 0;
		}
		if (itemStack.getItem() == Item.getItemById(327)) {
			return 1000;
		}
		return 200;
	}

	private int getNewHeat(int heatOld, int heatAim) {
		int diff = heatAim - heatOld;
		// Some math to confuse the hell out of everyone reading this
		double change = Math.ceil(Math.log(Math.abs(diff) + 1.0));
		return heatOld + (int) (Math.ceil(change / 3.0) * Math.signum(diff));
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		markDirty();
		if (count <= 0) {
			return ItemStack.EMPTY;
		}
		// FIXME: can do that better...
		if (slot > recipeStacks.length + 2) {
			ItemStack stack = inputStacks[slot - recipeStacks.length - 3];
			if (stack == ItemStack.EMPTY) {
				return stack;
			}
			cancelRecipe();
			if (count > stack.getCount()) {
				count = stack.getCount();
			}
			if (count == stack.getCount()) {
				inputStacks[slot - recipeStacks.length - 3] = ItemStack.EMPTY;
				return stack;
			}
			return stack.splitStack(count);
		}
		if (slot > 2) {
			return null; // You cant get anything from the recipe slots
		} else if (slot == outputSlot) {
			if (outputStack.isEmpty()) {
				return ItemStack.EMPTY;
			}
			if (count > outputStack.getCount()) {
				count = outputStack.getCount();
			}
			if (count == outputStack.getCount()) {
				ItemStack fuel = outputStack;
				outputStack = ItemStack.EMPTY;
				return fuel;
			}
			return outputStack.splitStack(count);
		} else if (slot == resultSlot) {
			return ItemStack.EMPTY;
		} else {
			if (fuelStack.isEmpty()) {
				return ItemStack.EMPTY;
			}
			if (count > fuelStack.getCount()) {
				count = fuelStack.getCount();
			}
			if (count == fuelStack.getCount()) {
				ItemStack fuel = fuelStack;
				fuelStack = ItemStack.EMPTY;
				return fuel;
			}
			return fuelStack.splitStack(count);
		}
	}

	/**
	 * Resets all working progress but leaves the recipe set
	 */
	public void cancelRecipe() {
		if (world.isRemote) {
			sendCancelRecipe();
		}
		TileHunterBench.this.workingMHFCBench = false;
		itemSmeltDuration = 0;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = getStackInSlot(index);
		setInventorySlotContents(index, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i < 0 || i >= getSizeInventory()) {
			return ItemStack.EMPTY;
		} else if (i >= recipeStacks.length + 3) {
			return inputStacks[i - 3 - recipeStacks.length];
		} else if (i >= 3) {
			return recipeStacks[i - 3];
		} else if (i == outputSlot) {
			return outputStack;
		} else if (i == resultSlot) {
			return resultStack;
		} else {
			return fuelStack;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		Objects.requireNonNull(itemstack);
		if (i < 0 || i >= getSizeInventory()) {
			return;
		}
		if (i >= recipeStacks.length + 3) {
			inputStacks[i - recipeStacks.length - 3] = itemstack;
		} else if (i >= 3) {
			recipeStacks[i - 3] = itemstack;
		} else if (i == outputSlot) {
			outputStack = itemstack;
		} else if (i == resultSlot) {
			resultStack = itemstack;
		} else if (i == fuelSlot) {
			fuelStack = itemstack;
		}
		markDirty();
	}

	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : inputStacks) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityplayer) {
		return getDistanceSq(entityplayer.posX, entityplayer.posY, entityplayer.posZ) <= 64.0f;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return (i == fuelSlot && TileEntityFurnace.isItemFuel(itemstack))
				|| (i > recipeStacks.length + 2 && i < recipeStacks.length * 2 + 3);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(getName());
	}

	public boolean beginCrafting() {
		if (world.isRemote) {
			sendBeginCraft();
		}
		if (canBeginCrafting()) {
			TileHunterBench.this.workingMHFCBench = true;
		}
		return TileHunterBench.this.workingMHFCBench;
	}

	@SideOnly(Side.CLIENT)
	private void sendBeginCraft() {
		PacketPipeline.networkPipe.sendToServer(new MessageBeginCrafting(this));
	}

	@SideOnly(Side.CLIENT)
	private void sendSetRecipe(EquipmentRecipe recipeToSend) {
		PacketPipeline.networkPipe.sendToServer(new MessageSetRecipe(this, recipeToSend));
	}

	@SideOnly(Side.CLIENT)
	private void sendCancelRecipe() {
		PacketPipeline.networkPipe.sendToServer(new MessageCancelRecipe(this));
	}

	@Override
	public void refreshState() {
		if (world.isRemote) {
			PacketPipeline.networkPipe.sendToServer(new MessageBenchRefreshRequest(this));
		}
	}

	private void sendUpdateCraft() {
		PacketPipeline.networkPipe.sendToAll(new MessageCraftingUpdate(this));
	}

	public boolean canBeginCrafting() {
		return (recipe != null) && matchesInputOutput() && (outputStack == ItemStack.EMPTY);
	}

	protected boolean matchesInputOutput() {
		for (int i = 0; i < inputStacks.length; i++) {
			if (!ItemStack.areItemStacksEqual(inputStacks[i], recipeStacks[i])) {
				return false;
			}
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
			if (id >= 0 && id < getSizeInventory()) {
				setInventorySlotContents(id, new ItemStack(stack));
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTag) {
		nbtTag = super.writeToNBT(nbtTag);
		storeCustomUpdate(nbtTag);

		NBTTagList itemsStored = new NBTTagList();
		for (int a = 0; a < getSizeInventory(); a++) {
			ItemStack s = getStackInSlot(a);
			if (s != ItemStack.EMPTY) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) a);
				s.writeToNBT(tag);
				itemsStored.appendTag(tag);
			}
		}
		nbtTag.setTag("Items", itemsStored);
		return nbtTag;
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
		RecipeType type = recipe != null ? recipe.getRecipeType() : RecipeType.MHFC;
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

	@Override
	public String getName() {
		return "container" + ResourceInterface.block_hunterbench_name;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public void clear() {
		for (int i = 0; i < getSizeInventory(); ++i) {
			removeStackFromSlot(i);
		}
	}

	@Override
	public int getField(int id) {
		throw new IllegalArgumentException("No such field: " + id);
	}

	@Override
	public void setField(int id, int value) {
		throw new IllegalArgumentException("No such field: " + id);
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

}
