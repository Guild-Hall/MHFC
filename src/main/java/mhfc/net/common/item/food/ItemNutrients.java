package mhfc.net.common.item.food;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.IItemColored;
import mhfc.net.common.item.IItemVarianted;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.util.Assert;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemNutrients extends ItemFood implements IItemColored, IItemVarianted {
	public static enum NutrientsSubType implements SubTypedItem.SubTypeEnum<Item> {
		NORMAL("normal", ResourceInterface.item_normalnutrients_name, ItemColor.LIBLUE, 2, 50, new PotionEffect(
				MobEffects.HEALTH_BOOST,
				12000,
				1)),
		MEGA("mega", ResourceInterface.item_meganutrient_name, ItemColor.BLUE, 3, 70, new PotionEffect(
				MobEffects.HEALTH_BOOST,
				12000,
				3));

		public final String registryName;
		public final String name;
		public final int healAmount;
		public final float saturation;
		public final boolean isDogsFood = true;
		public final PotionEffect potion;
		public final ItemColor color;

		private NutrientsSubType(String registryName, String name, ItemColor color, int healAmount, float modifier) {
			this(registryName, name, color, healAmount, modifier, null);
		}

		private NutrientsSubType(
				String registryName,
				String name,
				ItemColor color,
				int healAmount,
				float modifier,
				PotionEffect effect) {
			this.registryName = registryName;
			this.name = name;
			this.healAmount = healAmount;
			this.saturation = modifier;
			// this.isDogsFood = isDogsFood;
			this.potion = effect;
			this.color = color;
		}

		@Override
		public String getName() {
			return this.registryName;
		}

		@Override
		public String getUnlocalizedName() {
			return this.name;
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
		setUnlocalizedName(ResourceInterface.item_nutrients_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(1);
		setHasSubtypes(true);
		setAlwaysEdible();
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName(itemStack) + "." + itemPerk.getSubType(itemStack).name;
	}

	@Override
	public void getSubItems(Item base, CreativeTabs tab, NonNullList<ItemStack> list) {
		itemPerk.getSubItems(base, list);
	}

	@Override
	public int getHealAmount(ItemStack stack) {
		return itemPerk.getSubType(stack).healAmount;
	}

	@Override
	public float getSaturationModifier(ItemStack stack) {
		return itemPerk.getSubType(stack).saturation;
	}

	@Override
	public List<String> getVariantNames() {
		return itemPerk.getVariants();
	}

	@Override
	public boolean isWolfsFavoriteMeat() {
		// FIXME: itemPerk.getSubType(itemStack)....;
		return true;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> par3List, boolean par4) {
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
		default:
			Assert.logUnreachable("Unexpected subtype {}", subType);
		}
	}

	@Override
	public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		NutrientsSubType subType = itemPerk.getSubType(stack);
		float health = player.getHealth();
		player.addPotionEffect(new PotionEffect(subType.potion));
		player.setHealth(health);
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return itemPerk.getSubType(stack).getColor().getRGB();
	}

}
