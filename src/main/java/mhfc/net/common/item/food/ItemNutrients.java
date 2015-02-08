package mhfc.net.common.item.food;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemNutrients extends ItemFood {
	public static enum NutrientsSubType
			implements
				SubTypedItem.SubTypeEnum<Item> {
		NORMAL(MHFCReference.item_boostmeat_name,
				MHFCReference.item_boostmeat_icon, 2, 50, new PotionEffect(21,
						12000, 1, true)), //
		MEGA(MHFCReference.item_protectionmeat_name,
				MHFCReference.item_protectionmeat_icon, 3, 70,
				new PotionEffect(21, 12000, 3, true));

		public final String name;
		public final String texture;
		public final int healAmount;
		public final float saturation;
		public final boolean isDogsFood = true;
		public final PotionEffect potion;

		private NutrientsSubType(String name, String texture, int healAmount,
				float modifier) {
			this(name, texture, healAmount, modifier, null);
		}
		private NutrientsSubType(String name, String texture, int healAmount,
				float modifier, PotionEffect effect) {
			this.name = name;
			this.texture = texture;
			this.healAmount = healAmount;
			this.saturation = modifier;
			// this.isDogsFood = isDogsFood;
			this.potion = effect;
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
			return MHFCItemRegistry.mhfcfoodnutrients;
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
		return super.getUnlocalizedName(itemStack) + "."
				+ itemPerk.getSubType(itemStack).name;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		itemPerk.registerIcons(iconRegister);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item base, CreativeTabs tab,
			@SuppressWarnings("rawtypes") List list) {
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
		// FIXME: MeatSubType.values()[itemPerk.clumpedMeta(itemStack)]....;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		NutrientsSubType subType = itemPerk.getSubType(itemStack);
		switch (subType) {
			case NORMAL :
				par3List.add("Adds 4 health points");
				par3List.add("Duration:10 minutes");
				par3List.add("\u00a79[Only Once]");
				break;
			case MEGA :
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

}
