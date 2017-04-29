package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.materials.ItemSac.SacSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSac extends AbstractSubTypedItem<SacSubType> {
	public static enum SacSubType implements SubTypedItem.SubTypeEnum<Item> {
		SCREAMER("screamer", ResourceInterface.item_sac0_name, ItemColor.GRAY),
		POISON("poison", ResourceInterface.item_sac1_name, ItemColor.MAGNTA),
		TOXIN("toxin", ResourceInterface.item_sac2_name, ItemColor.MAGNTA),
		DEADLYPOISON("deadlypoison", ResourceInterface.item_sac3_name, ItemColor.MAGNTA),
		PARALYSIS("paralysis", ResourceInterface.item_sac4_name, ItemColor.LIME),
		SLEEP("sleep", ResourceInterface.item_sac5_name, ItemColor.CYAN),
		COMA("coma", ResourceInterface.item_sac6_name, ItemColor.CYAN),
		FLAME("flame", ResourceInterface.item_sac7_name, ItemColor.RED),
		INFERNO("inferno", ResourceInterface.item_sac8_name, ItemColor.RED),
		BLAZINGFIRE("blazingfire", ResourceInterface.item_sac9_name, ItemColor.RED),
		THUNDER("electro", ResourceInterface.item_sac10_name, ItemColor.YELLOW),
		ELECTRO("thunder", ResourceInterface.item_sac11_name, ItemColor.YELLOW),
		LIGHTNING("lightning", ResourceInterface.item_sac12_name, ItemColor.YELLOW);
		

		public final String registryName;
		public final String name;
		public final ItemColor color;

		private SacSubType(String registryName, String name, ItemColor color) {
			this.registryName = registryName;
			this.name = name;
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
			return MHFCItemRegistry.getRegistry().sac;
		}

		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	public ItemSac() {
		super(SacSubType.class);
		setUnlocalizedName(ResourceInterface.item_sac_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		this.setMaxStackSize(30);
	}

	@Override
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			List<String> par3List,
			boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}
}
