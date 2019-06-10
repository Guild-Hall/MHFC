package mhfc.net.client.container;

import mhfc.net.common.tile.TileHunterBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerHunterBench extends Container {
	private final IInventory hunterInventory;

	public ContainerHunterBench(
			InventoryPlayer playerInventory,
			World world,
			TileHunterBench tileEntity,
			int x,
			int y,
			int z) {
		this.hunterInventory = playerInventory;

		for (int row = 0; row < 4; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9, 7 + row * 18, 28 + col * 18));
			}
		}

		this.addSlotToContainer(new Slot(tileEntity, TileHunterBench.resultSlot, 280, 146) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			@Override
			public boolean canTakeStack(EntityPlayer playerIn) {
				return false;
			}
		});

		this.addSlotToContainer(new Slot(tileEntity, TileHunterBench.fuelSlot, 354, 168));
		this.addSlotToContainer(new Slot(tileEntity, TileHunterBench.outputSlot, 335, 146));

		for (int i = 0; i < TileHunterBench.recipeLength; ++i) {
			Slot s = new Slot(tileEntity, TileHunterBench.recipeOffset + i, 229 + i * 18, 28) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return false;
				}

				@Override
				public boolean canTakeStack(EntityPlayer p) {
					return false;
				}
			};
			this.addSlotToContainer(s);
		}

		for (int i = 0; i < TileHunterBench.inputLength; ++i) {
			this.addSlotToContainer(new Slot(tileEntity, TileHunterBench.inputOffset + i, 229 + i * 18, 191));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < this.hunterInventory.getSizeInventory()) {
				if (!this.mergeItemStack(
						itemstack1,
						this.hunterInventory.getSizeInventory(),
						this.inventorySlots.size(),
						true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, this.hunterInventory.getSizeInventory(), false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
	//	return this.worldObj.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)).getBlock() != MHFCBlockRegistry.getRegistry().mhfcblockhunterbench? false	: par1EntityPlayer.getDistanceSq(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D) <= 64.0D;
		return false;
	}
}
