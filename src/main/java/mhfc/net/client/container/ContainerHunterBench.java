package mhfc.net.client.container;

import mhfc.net.client.gui.slot.SlotHunterBench;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.crafting.MHFCCraftingManager;
import mhfc.net.common.tile.TileHunterBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerHunterBench extends Container {
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 5);
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;

	public ContainerHunterBench(
			InventoryPlayer par1InventoryPlayer,
			World par2World,
			TileHunterBench tileEntity,
			int x,
			int y,
			int z) {
		this.worldObj = par2World;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.addSlotToContainer(
				new SlotHunterBench(
						par1InventoryPlayer.player,
						this.craftMatrix,
						this.craftResult,
						0,
						124 + 8,
						35 + 1));
		int var6;
		int var7;

		for (var6 = 0; var6 < 5; ++var6) {
			for (var7 = 0; var7 < 3; ++var7) {
				this.addSlotToContainer(new Slot(this.craftMatrix, var7 + var6 * 3, 30 + var7 * 18, var6 * 18 + 15));
			}
		}

		for (var6 = 0; var6 < 3; ++var6) {
			for (var7 = 0; var7 < 9; ++var7) {
				this.addSlotToContainer(
						new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 158 + var7 * 18, var6 * 18 + 15));
			}
		}

		for (var6 = 0; var6 < 9; ++var6) {
			this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 158 + var6 * 18, 84));
		}

		for (var6 = 0; var6 < 7; ++var6) {
			Slot s = new Slot(tileEntity, var6 + 3, var6 * 18 + 229, 28) {
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

		for (var6 = 0; var6 < 7; ++var6) {
			this.addSlotToContainer(new Slot(tileEntity, var6 + 10, var6 * 18 + 229, 191) {
			});
		}

		this.addSlotToContainer(new Slot(tileEntity, TileHunterBench.resultSlot, 280, 146) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});

		this.addSlotToContainer(new Slot(tileEntity, TileHunterBench.fuelSlot, 354, 168));
		this.addSlotToContainer(new Slot(tileEntity, TileHunterBench.outputSlot, 335, 146));

		for (var6 = 0; var6 < 4; ++var6) {
			for (var7 = 0; var7 < 9; ++var7) {
				this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9, 7 + var6 * 18, var7 * 18 + 28));
			}
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory) {
		this.craftResult.setInventorySlotContents(
				0,
				MHFCCraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.worldObj
				.getBlock(this.posX, this.posY, this.posZ) != MHFCBlockRegistry.getRegistry().mhfcblockhunterbench
						? false
						: par1EntityPlayer.getDistanceSq(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D) <= 64.0D;
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);

		if (!this.worldObj.isRemote) {
			for (int i = 0; i < 15; ++i) {
				ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

				if (itemstack != null) {
					par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
				}
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if (var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if (par2 == 0) {
				if (!this.mergeItemStack(var5, 10, 46, true)) {
					return null;
				}

				var4.onSlotChange(var5, var3);
			} else if (par2 >= 10 && par2 < 37) {
				if (!this.mergeItemStack(var5, 37, 46, false)) {
					return null;
				}
			} else if (par2 >= 37 && par2 < 46) {
				if (!this.mergeItemStack(var5, 10, 37, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(var5, 10, 46, false)) {
				return null;
			}

			if (var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			if (var5.stackSize == var3.stackSize) {
				return null;
			}

			var4.onPickupFromSlot(par1EntityPlayer, var5);
		}

		return var3;
	}

	@Override
	public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot) {
		return par2Slot.inventory != this.craftResult && super.func_94530_a(par1ItemStack, par2Slot);
	}
}
