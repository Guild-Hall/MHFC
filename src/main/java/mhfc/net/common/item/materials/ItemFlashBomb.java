package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.entity.projectile.EntityFlashBomb;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFlashBomb extends Item {

	public ItemFlashBomb() {
		setUnlocalizedName(MHFCReference.item_flashbomb_name);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(2);
	}
	
	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister
				.registerIcon(MHFCReference.item_flashbomb_icon);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
		EntityPlayer player) {
		if (world.isRemote)
			return itemStack;
		else {
			EntityFlashBomb bomb = new EntityFlashBomb(world, player);
			world.spawnEntityInWorld(bomb);

			if (!player.capabilities.isCreativeMode) {
				--itemStack.stackSize;
			}
			return itemStack;
		}
	}
}
