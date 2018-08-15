package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.IItemColored;
import mhfc.net.common.item.ItemColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFirestone extends Item implements IItemColored {

	public ItemFirestone() {
		maxStackSize = 12;
		setUnlocalizedName(ResourceInterface.item_firestone_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public EnumActionResult onItemUse(
			EntityPlayer player,
			World world,
			BlockPos pos,
			EnumHand hand,
			EnumFacing facing,
			float hitX,
			float hitY,
			float hitZ) {
		pos = pos.offset(facing);
		ItemStack stack = player.getHeldItem(hand);
		if (!player.canPlayerEdit(pos, facing, stack)) {
			return EnumActionResult.FAIL;
		}
		if (world.isAirBlock(pos)) {
			return EnumActionResult.PASS;
		}
		world.playSound(
				pos.getX() + 0.5D,
				pos.getY(),
				pos.getZ() + 0.5D,
				SoundEvents.ITEM_FIRECHARGE_USE,
				SoundCategory.AMBIENT,
				1.0F,
				itemRand.nextFloat() * 0.4F + 0.8F,
				true);
		world.setBlockState(pos, Blocks.FIRE.getDefaultState());
		return EnumActionResult.SUCCESS;
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return ItemColor.RED.getRGB();
	}
}
