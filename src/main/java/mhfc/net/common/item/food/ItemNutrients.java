package mhfc.net.common.item.food;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.IItemColored;
import mhfc.net.common.item.IItemVarianted;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.util.Assert;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

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
		setTranslationKey(ResourceInterface.item_nutrients_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(1);
		setHasSubtypes(true);
		setAlwaysEdible();
	}

	@Override
	public String getTranslationKey(ItemStack itemStack) {
		return super.getTranslationKey(itemStack) + "." + itemPerk.getSubType(itemStack).name;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		 if (this.isInCreativeTab(tab))
	        {
	            items.add(new ItemStack(this));
	        }
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

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		NutrientsSubType subType = itemPerk.getSubType(stack);
		switch (subType) {
		case NORMAL:
			tooltip.add("Adds 4 health points");
			tooltip.add("Duration:10 minutes");
			tooltip.add("\u00a79[Only Once]");
			break;
		case MEGA:
			tooltip.add("Adds 8 health points for 10 minutes [Only Once]");
			tooltip.add("Duration:10 minutes");
			tooltip.add("\u00a79[Only Once]");
			break;
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
