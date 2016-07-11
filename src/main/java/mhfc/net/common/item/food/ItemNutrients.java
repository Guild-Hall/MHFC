package mhfc.net.common.item.food;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemNutrients extends ItemFood {
	public static enum NutrientsSubType implements SubTypedItem.SubTypeEnum<Item> {
		NORMAL(MHFCReference.item_normalnutrients_name, ItemColor.LIBLUE, 2, 50, new PotionEffect(21, 12000, 1, true)), //
		MEGA(MHFCReference.item_meganutrient_name, ItemColor.BLUE, 3, 70, new PotionEffect(21, 12000, 3, true));

		public final String name;
		public final String texture;
		public final int healAmount;
		public final float saturation;
		public final boolean isDogsFood = true;
		public final PotionEffect potion;
		public final ItemColor color;

		private NutrientsSubType(String name, ItemColor color, int healAmount, float modifier) {
			this(name, color, healAmount, modifier, null);
		}

		private NutrientsSubType(String name, ItemColor color, int healAmount, float modifier, PotionEffect effect) {
			this.name = name;
			this.texture = MHFCReference.base_tool_potion;
			this.healAmount = healAmount;
			this.saturation = modifier;
			// this.isDogsFood = isDogsFood;
			this.potion = effect;
			this.color = color;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getTexPath() {
			return this.texture;
		}

		@Override
		public Item getBaseItem() {
			return MHFCItemRegistry.getRegistry().nutrients;
		}

		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	private final SubTypedItem<Item, NutrientsSubType> itemPerk;

	public ItemNutrients() {
		super(0, 0, true);
		itemPerk = new SubTypedItem<>(NutrientsSubType.class);
		setUnlocalizedName(MHFCReference.item_nutrients_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName(itemStack) + "." + itemPerk.getSubType(itemStack).name;
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return itemPerk.getIcons()[meta];
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		itemPerk.registerIcons(iconRegister);
	}

	@Override
	public void getSubItems(Item base, CreativeTabs tab, List list) {
		itemPerk.getSubItems(base, list);
	}

	@Override
	public int func_150905_g(ItemStack itemStack) {
		return itemPerk.getSubType(itemStack).healAmount;
	}

	@Override
	public float func_150906_h(ItemStack itemStack) {
		return itemPerk.getSubType(itemStack).saturation;
	}

	@Override
	public boolean isWolfsFavoriteMeat() {
		// FIXME: itemPerk.getSubType(itemStack)....;
		return true;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List par3List, boolean par4) {
		NutrientsSubType subType = itemPerk.getSubType(itemStack);
		switch (subType) {
		case NORMAL:
			par3List.add("Adds 4 health points");
			par3List.add("Duration:10 minutes");
			par3List.add("\u00a79[Only Once]");
			break;
		case MEGA:
			par3List.add("Adds 8 health points for 10 minutes [Only Once]");
			par3List.add("Duration:10 minutes");
			par3List.add("\u00a79[Only Once]");
		}
	}

	@Override
	public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		NutrientsSubType subType = itemPerk.getSubType(stack);
		float health = player.getHealth();
		player.removePotionEffect(subType.potion.getPotionID());
		player.addPotionEffect(new PotionEffect(subType.potion));
		player.setHealth(health);
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return itemPerk.getSubType(stack).getColor().getRGB();
	}

}
