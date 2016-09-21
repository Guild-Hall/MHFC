package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.Libraries;
import net.minecraft.item.Item;

public class ItemBombMaterial extends Item {
	public static final int COOLDOWN = 200; // 200 - cooldown in ticks. 1 sec -
											// 20 ticks.

	public ItemBombMaterial() {
		super();
		setUnlocalizedName(Libraries.item_bombmaterial_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	/*
	 * public ItemStack onItemRightClick(ItemStack itemstack, World world,
	 * EntityPlayer player) { if (!player.capabilities.isCreativeMode) {
	 * --itemstack.stackSize; }
	 *
	 * world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F /
	 * (itemRand.nextFloat() * 0.4F + 0.8F)); if (!world.isRemote) {
	 * world.spawnEntityInWorld(new EntityRathalosFireball(world, player, 2D,
	 * 2D, 2D)); }
	 *
	 * return itemstack; }
	 */
}
