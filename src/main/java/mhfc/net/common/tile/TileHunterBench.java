package mhfc.net.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileHunterBench extends TileEntity implements IInventory {

	public static final int outputSlot = 1;
	public static final int fuelSlot = 0;

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
	private int itemSmeltTime;

	protected ItemStack outputStack;

	public TileHunterBench() {
		stacks = new ItemStack[10];
	}

	@Override
	public int getSizeInventory() {
		return stacks.length;
	}

	@Override
	public void updateEntity() {
		if (heatLength > 0) {
			--heatLength;
			heatStrength = getNewHeat(heatStrength, heatFromItem);

			++itemSmeltTime;
		} else {
			if (stacks[fuelSlot] != null) {
				heatLength = TileEntityFurnace
						.getItemBurnTime(stacks[fuelSlot]);
				heatStrength = getItemHeat(stacks[fuelSlot]);
			}
		}
	}

	private static int getItemHeat(ItemStack itemStack) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getNewHeat(int heatOld, int heatAim) {
		int diff = heatAim - heatOld;
		int change = (int) (Math.ceil(Math.log(Math.abs(diff) + 1.0)) * Math
				.signum(diff));
		return heatOld + change;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i < 0 || i > stacks.length)
			return null;
		return stacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		// TODO
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		// TODO
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		// TODO

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
