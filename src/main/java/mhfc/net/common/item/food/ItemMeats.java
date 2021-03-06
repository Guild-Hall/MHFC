package mhfc.net.common.item.food;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.IItemColored;
import mhfc.net.common.item.IItemVarianted;
import mhfc.net.common.item.ItemColor;
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

public class ItemMeats extends ItemFood implements IItemColored, IItemVarianted {
	public static enum MeatSubType implements SubTypedItem.SubTypeEnum<Item> {
		RAW("raw", ResourceInterface.item_rawmeat_name, ItemColor.RED, 2, 40),
		COOKED("cooked", ResourceInterface.item_cookedmeat_name, ItemColor.ORANGE, 3, 100),
		BOOST("boost", ResourceInterface.item_boostmeat_name, ItemColor.YELLOW, 3, 100) {
			@Override
			public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player) {
				if (world.rand.nextFloat() < 0.1f) {
					player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 250, 3));
				}
			}
		},
		PROTECTION("protection", ResourceInterface.item_protectionmeat_name, ItemColor.CYAN, 4, 125) {
			@Override
			public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player) {
				if (world.rand.nextFloat() < 0.1f) {
					player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 250, 3));
				}
			}
		},
		POISON("poison", ResourceInterface.item_poisonmeat_name, ItemColor.PURPLE, 3, 50) {
			@Override
			public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player) {
				if (world.rand.nextFloat() < 0.1f) {
					player.addPotionEffect(new PotionEffect(MobEffects.POISON, 180, 2));
				}
			}
		},
		SLOW("slow", ResourceInterface.item_slowmeat_name, ItemColor.GRAY, 3, 100) {
			@Override
			public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player) {
				if (world.rand.nextFloat() < 0.1f) {
					player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 400, 2));
				}
			}
		},
		HUNGER("hunger", ResourceInterface.item_hungermeat_name, ItemColor.LIME, 2, 30) {
			@Override
			public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player) {
				if (world.rand.nextFloat() < 0.1f) {
					player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 400, 2));
				}
			}
		},
		FIRE("fire", ResourceInterface.item_firemeat_name, ItemColor.RED, 4, 150) {
			@Override
			public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player) {
				if (world.rand.nextFloat() < 0.1f) {
					player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 600, 2));
				}
			}
		};

		public final String registryName;
		public final String name;
		public final int healAmount;
		public final float saturation;
		public final boolean isDogsFood = true;
		public final ItemColor color;

		private MeatSubType(String registryName, String name, ItemColor color, int healAmount, float modifier) {
			this.registryName = registryName;
			this.name = name;
			this.healAmount = healAmount;
			this.saturation = modifier;
			this.color = color;
			// this.isDogsFood = isDogsFood;
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
			return MHFCItemRegistry.getRegistry().meat;
		}

		public void onFoodEaten(
				ItemStack itemStack,
				World serverWorld,
				EntityPlayer player) {
			return; // No potion effect
		}

		@Override
		public ItemColor getColor() {
			return this.color;
		}
	}

	private final SubTypedItem<Item, MeatSubType> itemPerk;

	public ItemMeats() {
		super(0, 0, true);
		itemPerk = new SubTypedItem<>(MeatSubType.class);
		setUnlocalizedName(ResourceInterface.item_meat_basename);
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
	protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player) {
		if (world.isRemote) {
			return;
		}
		itemPerk.getSubType(itemStack).onFoodEaten(itemStack, world, player);
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return itemPerk.getSubType(stack).getColor().getRGB();
	}

}
