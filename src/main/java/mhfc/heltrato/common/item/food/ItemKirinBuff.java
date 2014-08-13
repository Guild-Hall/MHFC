package mhfc.heltrato.common.item.food;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.Potion;

public class ItemKirinBuff extends ItemFood {

	public ItemKirinBuff(int healAmount, float modifier, boolean p_i45339_3_) {
		super(healAmount, modifier, p_i45339_3_);
		setUnlocalizedName(MHFCReference.armor_kirin_buff_name);
		setCreativeTab(MHFCMain.mhfctabs);
		setPotionEffect(Potion.resistance.id, 15, 4, 100);
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister
				.registerIcon(MHFCReference.item_kirinbuff_icon);
	}

}
