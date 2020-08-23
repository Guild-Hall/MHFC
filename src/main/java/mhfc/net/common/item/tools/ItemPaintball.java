package mhfc.net.common.item.tools;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.entity.projectile.ProjectilePaintball;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.tools.ItemPaintball.PaintballType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemPaintball extends AbstractSubTypedItem<PaintballType> {

	public static enum PaintballType implements SubTypedItem.SubTypeEnum<Item> {
		BLACK("black", ItemColor.BLACK), RED("red", ItemColor.RED), GREEN(
				"green", ItemColor.GREEN), BROWN("brown",
						ItemColor.BROWN), BLUE("blue", ItemColor.BLUE), PURPLE(
								"purple", ItemColor.PURPLE), CYAN("cyan",
										ItemColor.CYAN), SILVER("silver",
												ItemColor.SILVER), GRAY("gray",
														ItemColor.GRAY), PINK(
																"pink",
																ItemColor.PINK), LIME(
																		"lime",
																		ItemColor.LIME), YELLOW(
																				"yellow",
																				ItemColor.YELLOW), LIBLUE(
																						"light_blue",
																						ItemColor.LIBLUE), MAGNTA(
																								"magenta",
																								ItemColor.MAGNTA), ORANGE(
																										"orange",
																										ItemColor.ORANGE), WHITE(
																												"white",
																												ItemColor.WHITE);

		public final String name;
		public final ItemColor color;

		private PaintballType(String name, ItemColor color) {
			this.name = name;
			this.color = color;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Item getBaseItem() {
			return MHFCItemRegistry.getRegistry().armor_barroth_boots;
		}

		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	public ItemPaintball() {
		super(PaintballType.class);
		setUnlocalizedName(ResourceInterface.item_paintball_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(64);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn,
			EntityPlayer playerIn, EnumHand hand) {
		ItemStack stack = playerIn.getHeldItem(hand);
		if (!playerIn.capabilities.isCreativeMode) {
			stack.setCount(stack.getCount() - 1);
		}

		worldIn.playSound(playerIn, playerIn.getPosition(),
				SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F,
				0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote) {
			worldIn.spawnEntity(new ProjectilePaintball(worldIn,
					ItemColor.byMetadata(stack.getItemDamage()), playerIn));
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}
}
