package mhfc.net.client.gui.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

public class SlotHunterBench extends Slot {
	private final IInventory craftMatrix;

	private EntityPlayer thePlayer;

	@SuppressWarnings("unused")
	private int amountCrafted;

	public SlotHunterBench(
			EntityPlayer player,
			IInventory craftMatrix,
			IInventory outputInventory,
			int outputSlot,
			int posX,
			int posY) {
		super(outputInventory, outputSlot, posX, posY);
		this.thePlayer = player;
		this.craftMatrix = craftMatrix;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	@Override
	protected void onCrafting(ItemStack stack, int amount) {
		this.amountCrafted += amount;
		this.onCrafting(stack);
	}

	@Override
	public ItemStack onTake(EntityPlayer player, ItemStack stack) {
		ItemCraftedEvent(player, stack, craftMatrix);
		this.onCrafting(stack);
		// FIXME: what is this here? I don't see the use at all
		for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i) {
			ItemStack matStack = this.craftMatrix.getStackInSlot(i);
			if (matStack == ItemStack.EMPTY) {
				continue;
			}

			this.craftMatrix.decrStackSize(i, 1);
			if (!matStack.getItem().hasContainerItem(matStack)) {
				continue;
			}
			ItemStack container = ForgeHooks.getContainerItem(matStack);

			if (container != ItemStack.EMPTY && !this.thePlayer.inventory.addItemStackToInventory(container)) {
				if (this.craftMatrix.getStackInSlot(i) == ItemStack.EMPTY) {
					this.craftMatrix.setInventorySlotContents(i, container);
				} else {
					this.thePlayer.dropItem(container, false);
				}
			}
		}
		return stack;
	}

	private void ItemCraftedEvent(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack, IInventory craftMatrix2) {

	}
}
