package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.materials.ItemSac.SacSubType;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSac extends AbstractSubTypedItem<SacSubType> {
	public static enum SacSubType implements SubTypedItem.SubTypeEnum<Item> {
		FIRE(MHFCReference.item_sac0_name, MHFCReference.base_monster_sac, ItemColor.RED);

		public final String name;
		public final String texture;
		public final ItemColor color;

		private SacSubType(String name, String texture, ItemColor color) {
			this.name = name;
			this.texture = texture;
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
			return MHFCItemRegistry.getRegistry().itemsac;
		}

		@Override
		public ItemColor getColor() {
			return color;
		}
	}

	public ItemSac() {
		super(SacSubType.class);
		setUnlocalizedName(MHFCReference.item_sac_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}
}
