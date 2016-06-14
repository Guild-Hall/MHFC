package mhfc.net.common.item.materials;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBullet extends Item {
	public static enum BulletSubType implements SubTypedItem.SubTypeEnum<Item>{
		NORMALS		(MHFCReference.item_bulletnormal_name,MHFCReference.item_bulletnormal_icon), //
		PIERCES 	(MHFCReference.item_bulletpierce_name,MHFCReference.item_bulletpierce_icon), //
		CRAGS  		(MHFCReference.item_bulletcrag_name,MHFCReference.item_bulletcrag_icon), //
		FLAMES      (MHFCReference.item_bulletflame_name,MHFCReference.item_bulletflame_icon); //
		
		
		public final String name;
		public final String texture;
		
		private BulletSubType(String name, String texture) {
			this.name = name;
			this.texture = texture;
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
			return MHFCItemRegistry.mhfcitembullet;
		}
	}
	private final SubTypedItem<Item, BulletSubType> itemPerk;
	
	public ItemBullet() {
		itemPerk = new SubTypedItem<>(BulletSubType.class);
		setHasSubtypes(true);
		setUnlocalizedName(MHFCReference.item_bullet_basename);
		setCreativeTab(MHFCMain.mhfctabs);
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

	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item base, CreativeTabs tab,
			@SuppressWarnings("rawtypes") List list) {
		itemPerk.getSubItems(base, list);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		switch (itemPerk.getSubType(par1ItemStack)) {
			case FLAMES :
				par3List.add("Bullet use for Bowguns");
				par3List.add("Deals fire damage to monsters");
				break;
			default :
				par3List.add("Bullet use for Bowguns");
				break;
		}
	}
	

}
