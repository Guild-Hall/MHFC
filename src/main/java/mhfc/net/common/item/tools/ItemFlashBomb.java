package mhfc.net.common.item.tools;

import mhfc.net.MHFCMain;
import mhfc.net.common.entity.fx.EntityFlashBomb;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.IItemColored;
import mhfc.net.common.item.ItemColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemFlashBomb extends Item implements IItemColored {

	public ItemFlashBomb() {
		setUnlocalizedName(ResourceInterface.item_flashbomb_name);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(2);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote) {
			EntityFlashBomb bomb = new EntityFlashBomb(world, player);
			bomb.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntity(bomb);
		}


		if (!player.capabilities.isCreativeMode) {
			stack.setCount(stack.getCount() - 1);
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return ItemColor.YELLOW.getRGB();
	}
}
