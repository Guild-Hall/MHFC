package mhfc.net.common.item.food;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.Potion;

public class ItemKirinBuff extends ItemFood {
	private static final int healAmount = 6;
	private static final int saturation = 100;
	private static final boolean isDogsFood = false;

	public ItemKirinBuff() {
		super(healAmount, saturation, isDogsFood);
		setUnlocalizedName(MHFCReference.armor_kirin_buff_name);
		setCreativeTab(MHFCMain.mhfctabs);
		setPotionEffect(Potion.resistance.id, 15, 4, 100);
	}

	@Override
	public void registerIcons(IIconRegister itemReg) {
		this.itemIcon = itemReg
				.registerIcon(MHFCReference.item_kirinbuff_icon);
	}

}
