package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.MHFCMobList;
import mhfc.net.common.core.MHFCMobList.MHFCEggInfo;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.IItemColored;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Iterator;

public class ItemSpawner extends Item implements IItemColored {

	public ItemSpawner() {
		super();
		setTranslationKey(ResourceInterface.item_mhfcspawnegg_name);
		setCreativeTab(MHFCMain.mhfctabs);
		setHasSubtypes(true);
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		String s = (I18n.format(this.getTranslationKey() + ".name")).trim();
		ResourceLocation s1 = MHFCMobList.getStringFromID(par1ItemStack.getItemDamage());

		if (s1 != null) {
			s = s + " " + I18n.format("entity." + EntityList.getTranslationName(s1) + ".name");
		}

		return s;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		MHFCEggInfo entityegginfo = MHFCMobList.registeredEggs().get(Integer.valueOf(par1ItemStack.getItemDamage()));
		return entityegginfo != null
				? (par2 == 0 ? entityegginfo.primaryColor : entityegginfo.secondaryColor)
				: 16777215;
	}

	private static Entity spawnCreature(World world, ItemStack stack, BlockPos pos) {
		int meta = stack.getItemDamage();

		ResourceLocation entityID = MHFCMobList.getStringFromID(meta);
		if (entityID == null) {
			return null;
		}
		EntityLiving entity = EntityLiving.class.cast(EntityList.createEntityByIDFromName(entityID, world));
		if (entity == null) {
			return null;
		}

		double x = pos.getX() + 0.5D, y = pos.getY(), z = pos.getZ() + 0.5D;
		entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
		entity.rotationYawHead = entity.rotationYaw;
		entity.renderYawOffset = entity.rotationYaw;
		entity.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), null);
		if (stack.hasDisplayName()) {
			entity.setCustomNameTag(stack.getDisplayName());
		}

		world.spawnEntity(entity);
		entity.playLivingSound();

		return entity;
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
		ItemStack stack = player.getHeldItem(hand);
		if (world.isRemote) {
			return EnumActionResult.SUCCESS;
		}
		if (!player.canPlayerEdit(pos.offset(facing), facing, stack)) {
			return EnumActionResult.FAIL;
		}
		Entity entity = spawnCreature(world, stack, pos.up());

		if (entity != null && !player.capabilities.isCreativeMode) {
			stack.setCount(stack.getCount() - 1);
		}
		return EnumActionResult.SUCCESS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (world.isRemote) {
			return new ActionResult<>(EnumActionResult.PASS, stack);
		}
		RayTraceResult rayTraceResult = rayTrace(world, player, true);

		if (rayTraceResult == null || rayTraceResult.typeOfHit == Type.BLOCK) {
			return new ActionResult<>(EnumActionResult.PASS, stack);
		}

		BlockPos hitBlockPos = rayTraceResult.getBlockPos();
		if (!(world.getBlockState(hitBlockPos).getBlock() instanceof BlockLiquid)) {
			return new ActionResult<>(EnumActionResult.PASS, stack);
		}
		if (!world.isBlockModifiable(player, hitBlockPos)
				|| !player.canPlayerEdit(hitBlockPos, rayTraceResult.sideHit, stack)) {
			return new ActionResult<>(EnumActionResult.FAIL, stack);
		}

		Entity entity = spawnCreature(world, stack, hitBlockPos);

		if (entity != null && player.capabilities.isCreativeMode) {
			stack.setCount(stack.getCount() - 1);
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		Iterator<MHFCEggInfo> iterator = MHFCMobList.registeredEggs().values().iterator();

		while (iterator.hasNext()) {
			MHFCEggInfo entityegginfo = iterator.next();
			items.add(new ItemStack(this, 1, entityegginfo.spawnedID));
		}
	}
}
