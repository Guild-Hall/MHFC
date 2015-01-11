package mhfc.heltrato.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileArmorStand extends TileEntity implements IInventory {
	
	private ItemStack[] standInventory;
	

	public TileArmorStand(){
		standInventory = new ItemStack[4];
	}
	
	public boolean canUpdate() {
		return false;
	}

	public int getSizeInventory() {
		return 0;
	}

	public ItemStack getStackInSlot(int var1) {
		return null;
	}

	public ItemStack decrStackSize(int var1, int var2) {
		return null;
	}

	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	public void setInventorySlotContents(int var1, ItemStack var2) {
		
	}

	public String getInventoryName() {
		return null;
	}

	public boolean hasCustomInventoryName() {
		return false;
	}

	public int getInventoryStackLimit() {
		return 0;
	}

	public boolean isUseableByPlayer(EntityPlayer var1) {
		return false;
	}

	public void openInventory() {
		
	}

	public void closeInventory() {
		
	}

	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return false;
	}
}
