package mhfc.net.common.item.tools;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.entity.projectile.EntityPaintball;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.tools.ItemPaintball.PaintballType;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPaintball extends AbstractSubTypedItem<PaintballType> {

	public static enum PaintballType implements SubTypedItem.SubTypeEnum<Item> {
		BLACK("black", ItemColor.BLACK),
		RED("red", ItemColor.RED),
		GREEN("green", ItemColor.GREEN),
		BROWN("brown", ItemColor.BROWN),
		BLUE("blue", ItemColor.BLUE),
		PURPLE("purple", ItemColor.PURPLE),
		CYAN("cyan", ItemColor.CYAN),
		SILVER("silver", ItemColor.SILVER),
		GRAY("gray", ItemColor.GRAY),
		PINK("pink", ItemColor.PINK),
		LIME("lime", ItemColor.LIME),
		YELLOW("yellow", ItemColor.YELLOW),
		LIBLUE("light_blue", ItemColor.LIBLUE),
		MAGNTA("magenta", ItemColor.MAGNTA),
		ORANGE("orange", ItemColor.ORANGE),
		WHITE("white", ItemColor.WHITE);

		public final String name;
		public final String texture;
		public final ItemColor color;

		private PaintballType(String name, ItemColor color) {
			this.name = name;
			this.texture = MHFCReference.base_monster_gem;
			this.color = color;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getTexPath() {
			return texture;
		}

		@Override
		public Item getBaseItem() {
			return MHFCItemRegistry.getRegistry().paintball;
		}

		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	public ItemPaintball() {
		super(PaintballType.class);
		setUnlocalizedName(MHFCReference.item_paintball_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(64);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			--stack.stackSize;
		}

		worldIn.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote) {
			worldIn.spawnEntityInWorld(
					new EntityPaintball(worldIn, ItemColor.byMetadata(stack.getItemDamage()), player));
		}
		return stack;
	}
}
